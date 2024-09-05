package symbolics.division.berry_bounty.berry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BasicBerry extends Item {
    public BasicBerry(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.setVelocity(0, 5, 0);
        return super.finishUsing(stack, world, user);
    }
}
