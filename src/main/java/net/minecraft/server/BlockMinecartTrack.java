package net.minecraft.server;




public class BlockMinecartTrack
  extends BlockMinecartTrackAbstract
{
  protected BlockMinecartTrack()
  {
    super(false);
  }
  















  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Block paramBlock)
  {
    if ((paramBlock.isPowerSource()) && 
      (new MinecartTrackLogic(this, paramWorld, paramInt1, paramInt2, paramInt3).a() == 3)) {
      a(paramWorld, paramInt1, paramInt2, paramInt3, false);
    }
  }
}
