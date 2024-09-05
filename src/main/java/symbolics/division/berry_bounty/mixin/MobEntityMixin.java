package symbolics.division.berry_bounty.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import symbolics.division.berry_bounty.berry.SinisterBerry;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin implements SinisterBerry.SinisterMobEntity {
    private static final String EVIL_NBT = "berry_bounty:is_evil";
    private static final double EVIL_BASE_DAMAGE = 1;

    @Shadow private GoalSelector goalSelector;

    private boolean evil = false;

    @Override
    public void setEvil() {
        this.evil = true;
        if ((MobEntity)(Object)this instanceof PathAwareEntity pathAware) {
            goalSelector.clear(g -> g instanceof SinisterBerry.SinisterGoal);
            goalSelector.add(-1, new SinisterBerry.SinisterGoal(pathAware,1.0));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.evil) {
            nbt.putBoolean(EVIL_NBT, true);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(EVIL_NBT, NbtElement.BYTE_TYPE) && nbt.getBoolean(EVIL_NBT)) {
            this.setEvil();
        }
    }

    @WrapOperation(
            method = "tryAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D")
    )
    private double grantBaseDamageToEvilMobs(MobEntity entity, RegistryEntry<EntityAttribute> attribute, Operation<Double> op) {
        if (evil && !((MobEntity)(Object)this).getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            return EVIL_BASE_DAMAGE;
        }
        return op.call(entity, attribute);
    }

}
