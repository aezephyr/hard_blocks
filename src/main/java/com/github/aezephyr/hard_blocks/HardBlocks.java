package com.github.aezephyr.hard_blocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.server.command.CommandManager;

import java.util.HashSet;

public class HardBlocks implements ModInitializer {

    public static final String MOD_ID = "hard_blocks";
    public static HashSet<Block> HARD_BLOCKS;

    @Override
    public void onInitialize() {

        HARD_BLOCKS = Config.loadHardBlocks();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
                    dispatcher.register(CommandManager.literal("reloadhardblocks")
                            .requires(source -> source.hasPermissionLevel(1))
                            .executes(context -> {
                                HARD_BLOCKS = Config.loadHardBlocks();
                                return 1;
                            })
                    );
                }
        );

        //System.out.println("hard_blocks onInitialize()");
    }
}
