package net.minecraft.server;

import java.util.Random;










public class BlockRedstoneComparator
  extends BlockDiodeAbstract
  implements IContainer
{
  public BlockRedstoneComparator(boolean paramBoolean)
  {
    super(paramBoolean);
    this.isTileEntity = true;
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.REDSTONE_COMPARATOR;
  }
  





  protected int b(int paramInt)
  {
    return 2;
  }
  
  protected BlockDiodeAbstract e()
  {
    return Blocks.REDSTONE_COMPARATOR_ON;
  }
  
  protected BlockDiodeAbstract i()
  {
    return Blocks.REDSTONE_COMPARATOR_OFF;
  }
  
  public int b()
  {
    return 37;
  }
  




















  protected boolean c(int paramInt)
  {
    return (this.a) || ((paramInt & 0x8) != 0);
  }
  
  protected int f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return e(paramIBlockAccess, paramInt1, paramInt2, paramInt3).a();
  }
  
  private int j(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!d(paramInt4)) {
      return h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return Math.max(h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4) - h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4), 0);
  }
  
  public boolean d(int paramInt)
  {
    return (paramInt & 0x4) == 4;
  }
  
  protected boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
    if (i >= 15) return true;
    if (i == 0) { return false;
    }
    int j = h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
    if (j == 0) { return true;
    }
    return i >= j;
  }
  
  protected int h(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = super.h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
    
    int j = l(paramInt4);
    int k = paramInt1 + Direction.a[j];
    int m = paramInt3 + Direction.b[j];
    Block localBlock = paramWorld.getType(k, paramInt2, m);
    
    if (localBlock.isComplexRedstone()) {
      i = localBlock.g(paramWorld, k, paramInt2, m, Direction.f[j]);
    } else if ((i < 15) && (localBlock.r())) {
      k += Direction.a[j];
      m += Direction.b[j];
      
      localBlock = paramWorld.getType(k, paramInt2, m);
      if (localBlock.isComplexRedstone()) {
        i = localBlock.g(paramWorld, k, paramInt2, m, Direction.f[j]);
      }
    }
    
    return i;
  }
  
  public TileEntityComparator e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    return (TileEntityComparator)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    boolean bool = this.a | (i & 0x8) != 0;
    int j = !d(i) ? 1 : 0;
    int k = j != 0 ? 4 : 0;
    k |= (bool ? 8 : 0);
    
    paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "random.click", 0.3F, j != 0 ? 0.55F : 0.5F);
    
    paramWorld.setData(paramInt1, paramInt2, paramInt3, k | i & 0x3, 2);
    c(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.random);
    return true;
  }
  
  protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    if (!paramWorld.a(paramInt1, paramInt2, paramInt3, this)) {
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
      int j = j(paramWorld, paramInt1, paramInt2, paramInt3, i);
      int k = e(paramWorld, paramInt1, paramInt2, paramInt3).a();
      
      if ((j != k) || (c(i) != a(paramWorld, paramInt1, paramInt2, paramInt3, i)))
      {
        if (i(paramWorld, paramInt1, paramInt2, paramInt3, i)) {
          paramWorld.a(paramInt1, paramInt2, paramInt3, this, b(0), -1);
        } else {
          paramWorld.a(paramInt1, paramInt2, paramInt3, this, b(0), 0);
        }
      }
    }
  }
  
  private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = j(paramWorld, paramInt1, paramInt2, paramInt3, i);
    int k = e(paramWorld, paramInt1, paramInt2, paramInt3).a();
    e(paramWorld, paramInt1, paramInt2, paramInt3).a(j);
    
    if ((k != j) || (!d(i))) {
      boolean bool = a(paramWorld, paramInt1, paramInt2, paramInt3, i);
      int m = (this.a) || ((i & 0x8) != 0) ? 1 : 0;
      if ((m != 0) && (!bool)) {
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFF7, 2);
      } else if ((m == 0) && (bool)) {
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x8, 2);
      }
      e(paramWorld, paramInt1, paramInt2, paramInt3);
    }
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
  {
    if (this.a)
    {
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, i(), i | 0x8, 4);
    }
    c(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
    paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, a(paramWorld, 0));
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
  {
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
    paramWorld.p(paramInt1, paramInt2, paramInt3);
    
    e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntity != null) {
      return localTileEntity.c(paramInt4, paramInt5);
    }
    return false;
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityComparator();
  }
}
