package symbolics.division.berry_bounty.berry.spicy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import symbolics.division.berry_bounty.berry.sinister.SinisterBerry;

import java.util.EnumSet;

/**
 * Eating this berry sets the user on fire for 2 seconds, which should deal one heart of damage overall
 *  Fire resistance/protection or being in water should help against this
 *
 * This berry also negates freezing, which can be useful in powdered snow
 */
public class SpicyBerry extends Item {
    public SpicyBerry(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.setOnFireFor(2);
        return super.finishUsing(stack, world, user);
    }
}
