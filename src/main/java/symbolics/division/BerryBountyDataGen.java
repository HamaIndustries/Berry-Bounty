package symbolics.division;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.registry.RegistryWrapper;
import symbolics.division.registry.BBItems;

import java.util.concurrent.CompletableFuture;

public class BerryBountyDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		FabricDataGenerator.Pack pack = gen.createPack();
		pack.addProvider(BBTagGenerator::new);
		pack.addProvider(BBModelGenerator::new);
	}

	private static final class BBTagGenerator extends FabricTagProvider.ItemTagProvider {
		public BBTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			getOrCreateTagBuilder(BerryBountyTags.MAGICAL_BERRIES)
					.add(BBItems.RED_BERRY)
					.add(BBItems.BLUE_BERRY)
					.add(BBItems.YELLOW_BERRY);
		}
	}

	private static final class BBModelGenerator extends FabricModelProvider {
		public BBModelGenerator(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockGen) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemGen) {
			itemGen.register(BBItems.RED_BERRY, Models.GENERATED);
			itemGen.register(BBItems.BLUE_BERRY, Models.GENERATED);
			itemGen.register(BBItems.YELLOW_BERRY, Models.GENERATED);
		}
	}
}
