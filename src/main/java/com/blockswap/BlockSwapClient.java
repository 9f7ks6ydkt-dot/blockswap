package com.blockswap;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.repository.PackRepository;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class BlockSwapClient implements ClientModInitializer {
    public static final String MOD_ID = "blockswap";
    private static final String PACK_NAME = "diamond_gold_swap";

    private static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        // Registers the built-in pack (resources/resourcepacks/diamond_gold_swap).
        // NORMAL = off by default, so everything looks vanilla until you press the key.
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container ->
                ResourceLoader.registerBuiltinPack(
                        Identifier.fromNamespaceAndPath(MOD_ID, PACK_NAME),
                        container,
                        PackActivationType.NORMAL));

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main"));

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.blockswap.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                toggle(client);
            }
        });
    }

    private static void toggle(Minecraft client) {
        PackRepository repo = client.getPackRepository();

        String packId = null;
        for (String id : repo.getAvailableIds()) {
            if (id.contains(PACK_NAME)) {
                packId = id;
                break;
            }
        }
        if (packId == null) {
            say(client, "Block Swap: pack not found");
            return;
        }

        List<String> selected = new ArrayList<>(repo.getSelectedIds());
        boolean turnOn = !selected.contains(packId);
        if (turnOn) {
            selected.add(packId);
        } else {
            selected.remove(packId);
        }

        repo.setSelected(selected);
        client.reloadResourcePacks();
        say(client, turnOn ? "Diamond <-> Gold swap: ON" : "Diamond <-> Gold swap: OFF");
    }

    private static void say(Minecraft client, String msg) {
        if (client.player != null) {
            client.player.displayClientMessage(Component.literal(msg), true);
        }
    }
}
