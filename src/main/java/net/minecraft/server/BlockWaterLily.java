package net.minecraft.server;

import java.util.List;







public class BlockWaterLily
  extends BlockPlant
{
  protected BlockWaterLily()
  {
    float f1 = 0.5F;
    float f2 = 0.015625F;
    a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
    a(CreativeModeTab.c);
  }
  
  public int b()
  {
    return 23;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    if ((paramEntity == null) || (!(paramEntity instanceof EntityBoat))) {
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return AxisAlignedBB.a(paramInt1 + this.minX, paramInt2 + this.minY, paramInt3 + this.minZ, paramInt1 + this.maxX, paramInt2 + this.maxY, paramInt3 + this.maxZ);
  }
  















  protected boolean a(Block paramBlock)
  {
    return paramBlock == Blocks.STATIONARY_WATER;
  }
  
  public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 < 0) || (paramInt2 >= 256)) return false;
    return (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3).getMaterial() == Material.WATER) && (paramWorld.getData(paramInt1, paramInt2 - 1, paramInt3) == 0);
  }
}
