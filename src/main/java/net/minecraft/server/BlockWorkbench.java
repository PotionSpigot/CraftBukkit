package net.minecraft.server;









public class BlockWorkbench
  extends Block
{
  protected BlockWorkbench()
  {
    super(Material.WOOD);
    a(CreativeModeTab.c);
  }
  















  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) {
      return true;
    }
    paramEntityHuman.startCrafting(paramInt1, paramInt2, paramInt3);
    return true;
  }
}
