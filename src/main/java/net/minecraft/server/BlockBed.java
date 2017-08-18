package net.minecraft.server;

import java.util.Random;

















public class BlockBed
  extends BlockDirectional
{
  public static final int[][] a = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
  













  public BlockBed()
  {
    super(Material.CLOTH);
    
    e();
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) { return true;
    }
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if (!b(i))
    {
      int j = l(i);
      paramInt1 += a[j][0];
      paramInt3 += a[j][1];
      if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != this) {
        return true;
      }
      i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    }
    
    if ((!paramWorld.worldProvider.e()) || (paramWorld.getBiome(paramInt1, paramInt3) == BiomeBase.HELL)) {
      double d1 = paramInt1 + 0.5D;
      double d2 = paramInt2 + 0.5D;
      double d3 = paramInt3 + 0.5D;
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      int k = l(i);
      paramInt1 += a[k][0];
      paramInt3 += a[k][1];
      if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this) {
        paramWorld.setAir(paramInt1, paramInt2, paramInt3);
        d1 = (d1 + paramInt1 + 0.5D) / 2.0D;
        d2 = (d2 + paramInt2 + 0.5D) / 2.0D;
        d3 = (d3 + paramInt3 + 0.5D) / 2.0D;
      }
      paramWorld.createExplosion(null, paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, 5.0F, true, true);
      return true;
    }
    
    if (c(i)) {
      localObject = null;
      for (EntityHuman localEntityHuman : paramWorld.players) {
        if (localEntityHuman.isSleeping()) {
          ChunkCoordinates localChunkCoordinates = localEntityHuman.bB;
          if ((localChunkCoordinates.x == paramInt1) && (localChunkCoordinates.y == paramInt2) && (localChunkCoordinates.z == paramInt3)) {
            localObject = localEntityHuman;
          }
        }
      }
      
      if (localObject == null) {
        a(paramWorld, paramInt1, paramInt2, paramInt3, false);
      } else {
        paramEntityHuman.b(new ChatMessage("tile.bed.occupied", new Object[0]));
        return true;
      }
    }
    
    Object localObject = paramEntityHuman.a(paramInt1, paramInt2, paramInt3);
    if (localObject == EnumBedResult.OK) {
      a(paramWorld, paramInt1, paramInt2, paramInt3, true);
      return true;
    }
    
    if (localObject == EnumBedResult.NOT_POSSIBLE_NOW) {
      paramEntityHuman.b(new ChatMessage("tile.bed.noSleep", new Object[0]));
    } else if (localObject == EnumBedResult.NOT_SAFE) {
      paramEntityHuman.b(new ChatMessage("tile.bed.notSafe", new Object[0]));
    }
    return true;
  }
  


























  public int b()
  {
    return 14;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public boolean c()
  {
    return false;
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    e();
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = l(i);
    
    if (b(i)) {
      if (paramWorld.getType(paramInt1 - a[j][0], paramInt2, paramInt3 - a[j][1]) != this) {
        paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      }
    }
    else if (paramWorld.getType(paramInt1 + a[j][0], paramInt2, paramInt3 + a[j][1]) != this) {
      paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      if (!paramWorld.isStatic) {
        b(paramWorld, paramInt1, paramInt2, paramInt3, i, 0);
      }
    }
  }
  

  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    if (b(paramInt1)) {
      return Item.getById(0);
    }
    return Items.BED;
  }
  
  private void e() {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
  }
  
  public static boolean b(int paramInt) {
    return (paramInt & 0x8) != 0;
  }
  
  public static boolean c(int paramInt) {
    return (paramInt & 0x4) != 0;
  }
  
  public static void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (paramBoolean) {
      i |= 0x4;
    } else {
      i &= 0xFFFFFFFB;
    }
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 4);
  }
  
  public static ChunkCoordinates a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = BlockDirectional.l(i);
    

    for (int k = 0; k <= 1; k++) {
      int m = paramInt1 - a[j][0] * k - 1;
      int n = paramInt3 - a[j][1] * k - 1;
      int i1 = m + 2;
      int i2 = n + 2;
      
      for (int i3 = m; i3 <= i1; i3++) {
        for (int i4 = n; i4 <= i2; i4++) {
          if ((World.a(paramWorld, i3, paramInt2 - 1, i4)) && (!paramWorld.getType(i3, paramInt2, i4).getMaterial().k()) && (!paramWorld.getType(i3, paramInt2 + 1, i4).getMaterial().k())) {
            if (paramInt4 > 0) {
              paramInt4--;
            }
            else {
              return new ChunkCoordinates(i3, paramInt2, i4);
            }
          }
        }
      }
    }
    return null;
  }
  
  public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    if (!b(paramInt4)) {
      super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, 0);
    }
  }
  
  public int h()
  {
    return 1;
  }
  





  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityHuman paramEntityHuman)
  {
    if ((paramEntityHuman.abilities.canInstantlyBuild) && 
      (b(paramInt4))) {
      int i = l(paramInt4);
      paramInt1 -= a[i][0];
      paramInt3 -= a[i][1];
      if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this) {
        paramWorld.setAir(paramInt1, paramInt2, paramInt3);
      }
    }
  }
}
