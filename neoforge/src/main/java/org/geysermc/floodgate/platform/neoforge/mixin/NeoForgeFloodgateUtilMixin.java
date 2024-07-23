package org.geysermc.floodgate.platform.neoforge.mixin;

import org.geysermc.floodgate.core.util.Utils;
import org.geysermc.floodgate.mod.FloodgateMod;
import org.geysermc.floodgate.platform.neoforge.NeoForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Mixins into Floodgate's {@link Utils} class to modify how resources are loaded from the jar.
 * This must be done due to mod platforms sharing a classloader across mods - this leads to Floodgate
 * loading Geyser's language files, as they're not prefixed to avoid conflicts.
 * To resolve this, this mixin replaces those calls with the platform-appropriate methods to load files.
 */
@Mixin(value = Utils.class, remap = false)
public class NeoForgeFloodgateUtilMixin {

    @Inject(method = "getGeneratedClassesForAnnotation(Ljava/lang/Class;)Ljava/util/Set;",
            at = @At(value = "INVOKE", target = "Lorg/geysermc/floodgate/core/util/Utils;getGeneratedClassesForAnnotation(Ljava/lang/String;)Ljava/util/Set;"),
            locals = LocalCapture.CAPTURE_FAILSOFT, remap = false, cancellable = true)
    private static void floodgate$getAnnotatedClasses(Class<? extends Annotation> annotationClass, CallbackInfoReturnable<Set<Class<?>>> cir) {
        cir.setReturnValue(((NeoForgeMod) FloodgateMod.INSTANCE).getAnnotatedClasses(annotationClass));
    }
}