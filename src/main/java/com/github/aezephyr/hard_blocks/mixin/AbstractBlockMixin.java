package com.github.aezephyr.hard_blocks.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.github.aezephyr.hard_blocks.HardBlocks;

//import java.util.Iterator;

//Credits: https://github.com/LordDeatHunter/BedrockBlocks

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void changeHardness(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {

        // these lines are copied from
        float hardness = state.getHardness(world, pos);
        if (hardness == -1.0F) {
            ci.setReturnValue(0.0F);
        }
        float speed = player.getBlockBreakingSpeed(state);
        float harvest_value = (float) (player.canHarvest(state) ? 30 : 100);

        if (!HardBlocks.HARD_BLOCKS.contains(state.getBlock())) {
            // the block isn't one we deal with. return the default value.
            ci.setReturnValue(speed / hardness / harvest_value);
        }  else {

            //
            // adjust the speed that the block breaks by a proportion of how many similar neighbors it has
            int neighbor_count = 0;

            // can I use iterateOutwards()?
            // for (Iterator i = (Iterator) pos.iterateOutwards(pos, 1, 1, 1); i.hasNext();) {
            //    System.out.println("hard_blocks AbstractBlockMixin iterateOutwards");
            //};

            // if the block above this one is of the same material then increment a counter.
            if (world.getBlockState(pos.up()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            // repeat for the block below,
            if (world.getBlockState(pos.down()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            // to the north, etc...
            if (world.getBlockState(pos.north()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            if (world.getBlockState(pos.south()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            if (world.getBlockState(pos.east()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            if (world.getBlockState(pos.west()).getBlock() == state.getBlock()) {
                neighbor_count++;
            }
            // System.out.println("This block matches " + c + " neighbors.");

            int[] gradient = new int[]{ 1, 4, 16, 64, 256, 1024 };

            for (int i = 0; i < gradient.length; i++) {
                // System.out.println("gradient[" + i + "] = " + gradient[i]);

                if (gradient[i] != 0) {
                    if (neighbor_count == i) {
                        speed = speed / gradient[i];
                    }
                }
            }

            ci.setReturnValue(speed / (harvest_value * hardness));
        }
    }
}
