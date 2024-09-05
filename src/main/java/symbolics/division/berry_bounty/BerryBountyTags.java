package symbolics.division.berry_bounty;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class BerryBountyTags {
    public static final TagKey<Item> MAGICAL_BERRIES = TagKey.of(RegistryKeys.ITEM, BerryBounty.id("magical_berries"));
}
