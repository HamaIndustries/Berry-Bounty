package symbolics.division.berry_bounty.berry.sinister;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.UUID;

/**
 * Feeding this berry to a mob turns this mob *evil*.
 *
 * Evil mobs will act normally for one minute after being fed or loaded. After,
 * whenever the player is not looking, they will approach the player steadily.
 *
 * Otherwise, they will
 */
public class SinisterBerry extends Item {
    public SinisterBerry(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof SinisterMobEntity evil && entity instanceof PathAwareEntity) {
            if (!user.getWorld().isClient) {
                evil.setEvil();
            }
            if (!user.isCreative()) {
                stack.setCount(stack.getCount()-1);
            }
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public static class SinisterGoal extends Goal {
        protected static final TargetPredicate TARGET_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(90).ignoreVisibility();
        protected final PathAwareEntity mob;
        protected final TargetPredicate predicate;
        protected PlayerEntity closestPlayer;
        protected double speed;
        protected int cooldown;

        public SinisterGoal(PathAwareEntity entity, double speed) {
            this.mob = entity;
            this.predicate = TARGET_PREDICATE.copy().setPredicate(this::isSinisterTarget);
            this.speed = speed;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        protected boolean isLookingAway(LivingEntity entity) {
            return entity.getRotationVector().dotProduct(this.mob.getPos().subtract(entity.getPos())) < 0;
        }

        protected boolean isSinisterTarget(LivingEntity entity) {
            return //!entity.getUuid().equals(this.enablerID)&&
                    isLookingAway(entity);
        }

        @Override
        public boolean canStart() {
            this.closestPlayer = this.mob.getWorld().getClosestPlayer(this.predicate, this.mob);
            return this.closestPlayer != null;
        }

        @Override
        public boolean shouldContinue() {
            return canStart();
        }

        @Override
        public void stop() {
            this.mob.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.mob.getLookControl().lookAt(this.closestPlayer);
            this.cooldown = Math.max(this.cooldown - 1, 0);
            if (this.mob.distanceTo(this.closestPlayer) < 2 && this.cooldown == 0) {
                this.mob.tryAttack(this.closestPlayer);
                this.cooldown = 20;
            }
            if (this.mob.distanceTo(this.closestPlayer) < 0.4) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);
            }
        }
    }

    public interface SinisterMobEntity {
        GoalSelector getGoalSelectorSinestrally();
        void setEvil();
    }
}
