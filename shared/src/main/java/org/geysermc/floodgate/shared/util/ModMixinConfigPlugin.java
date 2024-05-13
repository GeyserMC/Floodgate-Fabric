package org.geysermc.floodgate.shared.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ModMixinConfigPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("org.geysermc.floodgate.mixin.ClientIntentionPacketMixin")) {
            return applyProxyFix();
        }
        if (mixinClassName.equals("org.geysermc.floodgate.mixin.GeyserModInjectorMixin")) {
            return isGeyserLoaded();
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @ExpectPlatform
    private static boolean isGeyserLoaded() {
        throw new IllegalStateException("isGeyserLoaded is not implemented!");
    }

    @ExpectPlatform
    private static boolean applyProxyFix() {
        throw new IllegalStateException("applyProxyFix is not implemented!");
    }
}