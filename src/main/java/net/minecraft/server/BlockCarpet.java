package net.minecraft.server;











public class BlockCarpet
  extends Block
{
  protected BlockCarpet()
  {
    super(Material.WOOL);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    a(true);
    a(CreativeModeTab.c);
    b(0);
  }
  





  public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    float f = 0.0625F;
    return AxisAlignedBB.a(paramInt1 + this.minX, paramInt2 + this.minY, paramInt3 + this.minZ, paramInt1 + this.maxX, paramInt2 + i * f, paramInt3 + this.maxZ);
  }
  




  public boolean c()
  {
    return false;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public void g()
  {
    b(0);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
  }
  
  protected void b(int paramInt) {
    int i = 0;
    float f = 1 * (1 + i) / 16.0F;
    a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return (super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) && (j(paramWorld, paramInt1, paramInt2, paramInt3));
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  private boolean e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (!j(paramWorld, paramInt1, paramInt2, paramInt3)) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      return false;
    }
    return true;
  }
  
  public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return !paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3);
  }
  






  public int getDropData(int paramInt)
  {
    return paramInt;
  }
}
