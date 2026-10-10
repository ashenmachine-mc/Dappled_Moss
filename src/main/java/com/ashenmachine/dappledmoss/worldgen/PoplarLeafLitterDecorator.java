package com.ashenmachine.dappledmoss.worldgen;

import com.ashenmachine.dappledmoss.DappledMoss;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public final class PoplarLeafLitterDecorator extends TreeDecorator {
	public static final MapCodec<PoplarLeafLitterDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(decorator -> decorator.block),
			Codec.intRange(1, 512).optionalFieldOf("tries", 96).forGetter(decorator -> decorator.tries),
			Codec.intRange(1, 8).optionalFieldOf("radius", 8).forGetter(decorator -> decorator.radius),
			Codec.intRange(0, 8).optionalFieldOf("height", 1).forGetter(decorator -> decorator.height)
	).apply(instance, PoplarLeafLitterDecorator::new));
	public static final TreeDecoratorType<PoplarLeafLitterDecorator> TYPE = Registry.register(
			BuiltInRegistries.TREE_DECORATOR_TYPE,
			Identifier.fromNamespaceAndPath(DappledMoss.MOD_ID, "poplar_leaf_litter"),
			new TreeDecoratorType<>(CODEC)
	);

	public static TreeDecoratorType<PoplarLeafLitterDecorator> initialize() {
		return TYPE;
	}

	private final Block block;
	private final int tries;
	private final int radius;
	private final int height;

	private PoplarLeafLitterDecorator(Block block, int tries, int radius, int height) {
		this.block = block;
		this.tries = tries;
		this.radius = radius;
		this.height = height;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return TYPE;
	}

	@Override
	public void place(Context context) {
		List<BlockPos> bases = TreeFeature.getLowestTrunkOrRootOfTree(context);
		if (bases.isEmpty()) {
			return;
		}

		int baseY = bases.getFirst().getY();
		int minX = bases.stream().mapToInt(BlockPos::getX).min().orElseThrow();
		int maxX = bases.stream().mapToInt(BlockPos::getX).max().orElseThrow();
		int minZ = bases.stream().mapToInt(BlockPos::getZ).min().orElseThrow();
		int maxZ = bases.stream().mapToInt(BlockPos::getZ).max().orElseThrow();
		double originX = (minX + maxX) / 2.0;
		double originZ = (minZ + maxZ) / 2.0;
		RandomSource random = context.random();

		for (int attempt = 0; attempt < tries; attempt++) {
			int x = random.nextIntBetweenInclusive((int) Math.floor(originX) - radius, (int) Math.ceil(originX) + radius);
			int z = random.nextIntBetweenInclusive((int) Math.floor(originZ) - radius, (int) Math.ceil(originZ) + radius);
			double dx = x - originX;
			double dz = z - originZ;
			double distance = Math.sqrt(dx * dx + dz * dz);
			if (distance > radius) {
				continue;
			}

			double chance = 0.9 - 0.55 * distance / radius;
			if (random.nextFloat() >= chance) {
				continue;
			}

			int y = baseY + random.nextIntBetweenInclusive(-height, height);
			BlockPos supportPos = new BlockPos(x, y, z);
			BlockPos litterPos = supportPos.above();
			if (!context.level().isStateAtPosition(litterPos, state -> state.isAir() || state.is(Blocks.VINE))) {
				continue;
			}
			if (!context.checkBlock(supportPos, BlockState::isSolidRender)) {
				continue;
			}
			if (context.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, supportPos).getY() > litterPos.getY()) {
				continue;
			}

			BlockState state = block.defaultBlockState()
					.setValue(BlockStateProperties.SEGMENT_AMOUNT, random.nextIntBetweenInclusive(1, 4))
					.setValue(LeafLitterBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
			context.setBlock(litterPos, state);
		}
	}
}