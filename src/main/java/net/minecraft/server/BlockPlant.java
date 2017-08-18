package net.minecraft.server;

import java.util.Random;



public class BlockPlant
  extends Block
{
  protected BlockPlant(Material paramMaterial)
  {
    super(paramMaterial);
    a(true);
    float f = 0.2F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
    a(CreativeModeTab.c);
  }
  
  protected BlockPlant() {
    this(Material.PLANT);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return (super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) && (a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3)));
  }
  
  protected boolean a(Block paramBlock) {
    return (paramBlock == Blocks.GRASS) || (paramBlock == Blocks.DIRT) || (paramBlock == Blocks.SOIL);
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
  {
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  protected void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (!j(paramWorld, paramInt1, paramInt2, paramInt3)) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, getById(0), 0, 2);
    }
  }
  
  public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3));
  }
  
  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }
  




  public boolean c()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public int b()
  {
    return 1;
  }
}
