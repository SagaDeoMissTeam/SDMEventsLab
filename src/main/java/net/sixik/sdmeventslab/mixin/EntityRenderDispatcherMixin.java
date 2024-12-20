package net.sixik.sdmeventslab.mixin;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

//    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
//    public <E extends Entity> void sdm$render(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci) {
//        RenderSystem.setShaderColor(1f,0,0,1f);
//    }
//
//    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
//    public <E extends Entity> void sdm$renderR(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci) {
//        RenderSystem.setShaderColor(1f,1f,1f,1f);
//    }
}
