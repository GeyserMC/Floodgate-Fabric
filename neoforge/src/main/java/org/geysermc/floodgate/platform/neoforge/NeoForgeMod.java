package org.geysermc.floodgate.platform.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.GameShuttingDownEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.floodgate.core.module.PluginMessageModule;
import org.geysermc.floodgate.core.module.ServerCommonModule;
import org.geysermc.floodgate.mod.FloodgateMod;
import org.geysermc.floodgate.mod.util.ModTemplateReader;
import org.geysermc.floodgate.platform.neoforge.module.NeoForgeCommandModule;
import org.geysermc.floodgate.platform.neoforge.module.NeoForgePlatformModule;
import org.geysermc.floodgate.platform.neoforge.pluginmessage.NeoForgePluginMessageRegistration;

import java.nio.file.Path;

@Mod("floodgate")
public final class NeoForgeMod extends FloodgateMod {

    private final ModContainer container;

    public NeoForgeMod(IEventBus modEventBus, ModContainer container) {
        this.container = container;
        init(
            new ServerCommonModule(
                FMLPaths.CONFIGDIR.get().resolve("floodgate"),
                new ModTemplateReader()
            ),
            new NeoForgePlatformModule(),
            new NeoForgeCommandModule()
        );

        modEventBus.addListener(this::onRegisterPackets);
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        if (FMLLoader.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(this::onClientStop);
        } else {
            NeoForge.EVENT_BUS.addListener(this::onServerStop);
        }
    }

    private void onServerStarted(ServerStartedEvent event) {
        this.enable(event.getServer());
    }

    private void onClientStop(GameShuttingDownEvent ignored) {
        this.disable();
    }

    private void onServerStop(ServerStoppingEvent ignored) {
        this.disable();
    }

    private void onRegisterPackets(final RegisterPayloadHandlersEvent event) {
        NeoForgePluginMessageRegistration.setRegistrar(event.registrar("floodgate").optional());
        enable(new PluginMessageModule());
    }

    @Override
    public @Nullable Path resourcePath(String file) {
        return container.getModInfo().getOwningFile().getFile().findResource(file);
    }
}
