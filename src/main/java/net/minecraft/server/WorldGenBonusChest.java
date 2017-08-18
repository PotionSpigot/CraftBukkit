package net.minecraft.server;

import java.util.Random;






public class WorldGenBonusChest
  extends WorldGenerator
{
  private final StructurePieceTreasure[] a;
  private final int b;
  
  public WorldGenBonusChest(StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt)
  {
    this.a = paramArrayOfStructurePieceTreasure;
    this.b = paramInt;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    Block localBlock;
    while ((((localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3)).getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) && (paramInt2 > 1)) {
      paramInt2--;
    }
    if (paramInt2 < 1) {
      return false;
    }
    paramInt2++;
    
    for (int i = 0; i < 4; i++) {
      int j = paramInt1 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int k = paramInt2 + paramRandom.nextInt(3) - paramRandom.nextInt(3);
      int m = paramInt3 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      if ((paramWorld.isEmpty(j, k, m)) && (World.a(paramWorld, j, k - 1, m))) {
        paramWorld.setTypeAndData(j, k, m, Blocks.CHEST, 0, 2);
        TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(j, k, m);
        if ((localTileEntityChest != null) && 
          (localTileEntityChest != null)) { StructurePieceTreasure.a(paramRandom, this.a, localTileEntityChest, this.b);
        }
        if ((paramWorld.isEmpty(j - 1, k, m)) && (World.a(paramWorld, j - 1, k - 1, m))) {
          paramWorld.setTypeAndData(j - 1, k, m, Blocks.TORCH, 0, 2);
        }
        if ((paramWorld.isEmpty(j + 1, k, m)) && (World.a(paramWorld, j - 1, k - 1, m))) {
          paramWorld.setTypeAndData(j + 1, k, m, Blocks.TORCH, 0, 2);
        }
        if ((paramWorld.isEmpty(j, k, m - 1)) && (World.a(paramWorld, j - 1, k - 1, m))) {
          paramWorld.setTypeAndData(j, k, m - 1, Blocks.TORCH, 0, 2);
        }
        if ((paramWorld.isEmpty(j, k, m + 1)) && (World.a(paramWorld, j - 1, k - 1, m))) {
          paramWorld.setTypeAndData(j, k, m + 1, Blocks.TORCH, 0, 2);
        }
        return true;
      }
    }
    
    return false;
  }
}
