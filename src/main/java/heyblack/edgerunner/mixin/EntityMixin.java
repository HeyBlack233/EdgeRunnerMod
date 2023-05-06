package heyblack.edgerunner.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    @Shadow public Optional<BlockPos> field_44784;

    @Shadow private Vec3d pos;

    @Inject(method = "method_51703", at = @At("HEAD"), cancellable = true)
    public void getStandingPos(boolean bl, CallbackInfo ci)
    {
        if (bl)
        {
            int i = MathHelper.floor(this.pos.x);
            int j = MathHelper.floor(this.pos.y - 0.2f);
            int k = MathHelper.floor(this.pos.z);
            this.field_44784 = Optional.of(new BlockPos(i, j, k));
        }
        else if (this.field_44784.isPresent())
        {
            this.field_44784 = Optional.empty();
        }
        ci.cancel();
    }
}
