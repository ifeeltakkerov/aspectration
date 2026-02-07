package dev.ifeeltakker.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.ifeeltakker.AspectRation;

@Mixin(GameRenderer.class)
public abstract class AspectRationMixin {

    @Inject(
            method = "getBasicProjectionMatrix",
            at = @At("RETURN"),
            cancellable = true
    )
    private void overrideProjection(double fov, CallbackInfoReturnable<Matrix4f> cir) {
        MinecraftClient client = MinecraftClient.getInstance();

        int width = client.getWindow().getFramebufferWidth();
        int height = client.getWindow().getFramebufferHeight();
        float windowAspect = (float) width / (float) height;

        float fovRad = (float) Math.toRadians(fov);

        if (AspectRation.enabled) {
            cir.setReturnValue(new Matrix4f().setPerspective(fovRad, AspectRation.aspectRatio, 0.05f, 10000f));
        } else {
            cir.setReturnValue(new Matrix4f().setPerspective(fovRad, windowAspect, 0.05f, 10000f));
        }
    }
}