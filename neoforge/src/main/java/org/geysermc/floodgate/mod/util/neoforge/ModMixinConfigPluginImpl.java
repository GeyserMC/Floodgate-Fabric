package org.geysermc.floodgate.mod.util.neoforge;

import net.neoforged.fml.loading.LoadingModList;

public class ModMixinConfigPluginImpl {
    private static boolean isGeyserLoaded() {
        return LoadingModList.get().getModFileById("geyser_neoforge") != null;
    }

    private static boolean applyProxyFix() {
        return false;
    }
}
