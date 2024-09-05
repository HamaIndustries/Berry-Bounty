package symbolics.division.berry_bounty.registry;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import symbolics.division.berry_bounty.BerryBounty;
import symbolics.division.berry_bounty.berry.SinisterBerry;
import symbolics.division.berry_bounty.berry.SpicyBerry;

import java.util.ArrayList;
import java.util.List;

public final class BBItems {
    private static final List<Item> allItems = new ArrayList<>();

    public static final Item RED_BERRY = basicBerry("red_berry");
    public static final Item BLUE_BERRY = basicBerry("blue_berry");
    public static final Item YELLOW_BERRY = basicBerry("yellow_berry");

    public static final Item SINISTER_BERRY = item("sinister_berry", new SinisterBerry(new Item.Settings().food(FoodComponents.SWEET_BERRIES)));
    public static final Item SPICY_BERRY = item("spicy_berry", new SpicyBerry(new Item.Settings().food(FoodComponents.SWEET_BERRIES)));

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), BerryBounty.id("item_group"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(RED_BERRY::getDefaultStack)
            .displayName(Text.translatable("itemGroup." + BerryBounty.MOD_ID))
            .build();

    private static Item basicBerry(String name) {
        return item(name, new Item(new Item.Settings().food(FoodComponents.SWEET_BERRIES)));
    }

    private static Item item(String name, Item item) {
        Registry.register(Registries.ITEM, BerryBounty.id(name), item);
        allItems.add(item);
        return item;
    }

    public static List<Item> getAllItems() { return ImmutableList.copyOf(allItems); }

    public static void init() {}
}
