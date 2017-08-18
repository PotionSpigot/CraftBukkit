package net.minecraft.server;

import java.util.List;
import java.util.Random;













public class BlockEnderPortalFrame
  extends Block
{
  public BlockEnderPortalFrame()
  {
    super(Material.STONE);
  }
  






















  public boolean c()
  {
    return false;
  }
  
  public int b()
  {
    return 26;
  }
  
  public void g()
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (b(i)) {
      a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    }
    g();
  }
  
  public static boolean b(int paramInt) {
    return (paramInt & 0x4) != 0;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return null;
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    int i = ((MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
  }
  
  public boolean isComplexRedstone()
  {
    return true;
  }
  
  public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if (b(i)) {
      return 15;
    }
    return 0;
  }
}
