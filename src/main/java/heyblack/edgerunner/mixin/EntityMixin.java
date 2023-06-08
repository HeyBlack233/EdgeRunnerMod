package heyblack.edgerunner.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public Optional<BlockPos> supportingBlockPos;

    @Shadow private Vec3d pos;

    @Inject(method = "updateSupportingBlockPos", at = @At("HEAD"), cancellable = true)
    public void getStandingPos(boolean onGround, Vec3d movement, CallbackInfo ci)
    {
        if (onGround)
        {
            int i = MathHelper.floor(this.pos.x);
            int j = MathHelper.floor(this.pos.y - 0.2f);
            int k = MathHelper.floor(this.pos.z);
            this.supportingBlockPos = Optional.of(new BlockPos(i, j, k));
        }
        else if (this.supportingBlockPos.isPresent())
        {
            this.supportingBlockPos = Optional.empty();
        }
        ci.cancel();
    }
}
