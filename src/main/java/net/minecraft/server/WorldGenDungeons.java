package net.minecraft.server;

import java.io.PrintStream;
import java.util.Random;








public class WorldGenDungeons
  extends WorldGenerator
{
  private static final StructurePieceTreasure[] a = { new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 4, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 1, 10), new StructurePieceTreasure(Items.WHEAT, 0, 1, 4, 10), new StructurePieceTreasure(Items.SULPHUR, 0, 1, 4, 10), new StructurePieceTreasure(Items.STRING, 0, 1, 4, 10), new StructurePieceTreasure(Items.BUCKET, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.REDSTONE, 0, 1, 4, 10), new StructurePieceTreasure(Items.RECORD_1, 0, 1, 1, 10), new StructurePieceTreasure(Items.RECORD_2, 0, 1, 1, 10), new StructurePieceTreasure(Items.NAME_TAG, 0, 1, 1, 10), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 2), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 5), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
  

















  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 3;
    int j = paramRandom.nextInt(2) + 2;
    int k = paramRandom.nextInt(2) + 2;
    
    int m = 0;
    int i1; int i2; for (int n = paramInt1 - j - 1; n <= paramInt1 + j + 1; n++) {
      for (i1 = paramInt2 - 1; i1 <= paramInt2 + i + 1; i1++) {
        for (i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; i2++) {
          Material localMaterial = paramWorld.getType(n, i1, i2).getMaterial();
          if ((i1 == paramInt2 - 1) && (!localMaterial.isBuildable())) return false;
          if ((i1 == paramInt2 + i + 1) && (!localMaterial.isBuildable())) { return false;
          }
          if (((n == paramInt1 - j - 1) || (n == paramInt1 + j + 1) || (i2 == paramInt3 - k - 1) || (i2 == paramInt3 + k + 1)) && 
            (i1 == paramInt2) && (paramWorld.isEmpty(n, i1, i2)) && (paramWorld.isEmpty(n, i1 + 1, i2))) {
            m++;
          }
        }
      }
    }
    


    if ((m < 1) || (m > 5)) { return false;
    }
    for (n = paramInt1 - j - 1; n <= paramInt1 + j + 1; n++) {
      for (i1 = paramInt2 + i; i1 >= paramInt2 - 1; i1--) {
        for (i2 = paramInt3 - k - 1; i2 <= paramInt3 + k + 1; i2++)
        {
          if ((n == paramInt1 - j - 1) || (i1 == paramInt2 - 1) || (i2 == paramInt3 - k - 1) || (n == paramInt1 + j + 1) || (i1 == paramInt2 + i + 1) || (i2 == paramInt3 + k + 1)) {
            if ((i1 >= 0) && (!paramWorld.getType(n, i1 - 1, i2).getMaterial().isBuildable())) {
              paramWorld.setAir(n, i1, i2);
            } else if (paramWorld.getType(n, i1, i2).getMaterial().isBuildable()) {
              if ((i1 == paramInt2 - 1) && (paramRandom.nextInt(4) != 0)) {
                paramWorld.setTypeAndData(n, i1, i2, Blocks.MOSSY_COBBLESTONE, 0, 2);
              } else {
                paramWorld.setTypeAndData(n, i1, i2, Blocks.COBBLESTONE, 0, 2);
              }
            }
          } else {
            paramWorld.setAir(n, i1, i2);
          }
        }
      }
    }
    
    for (n = 0; n < 2; n++) {
      for (i1 = 0; i1 < 3; i1++) {
        i2 = paramInt1 + paramRandom.nextInt(j * 2 + 1) - j;
        int i3 = paramInt2;
        int i4 = paramInt3 + paramRandom.nextInt(k * 2 + 1) - k;
        if (paramWorld.isEmpty(i2, i3, i4))
        {
          int i5 = 0;
          if (paramWorld.getType(i2 - 1, i3, i4).getMaterial().isBuildable()) i5++;
          if (paramWorld.getType(i2 + 1, i3, i4).getMaterial().isBuildable()) i5++;
          if (paramWorld.getType(i2, i3, i4 - 1).getMaterial().isBuildable()) i5++;
          if (paramWorld.getType(i2, i3, i4 + 1).getMaterial().isBuildable()) { i5++;
          }
          if (i5 == 1)
          {
            paramWorld.setTypeAndData(i2, i3, i4, Blocks.CHEST, 0, 2);
            
            StructurePieceTreasure[] arrayOfStructurePieceTreasure = StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) });
            
            TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(i2, i3, i4);
            if (localTileEntityChest == null) break;
            StructurePieceTreasure.a(paramRandom, arrayOfStructurePieceTreasure, localTileEntityChest, 8); break;
          }
        }
      }
    }
    

    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.MOB_SPAWNER, 0, 2);
    TileEntityMobSpawner localTileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityMobSpawner != null) {
      localTileEntityMobSpawner.getSpawner().setMobName(a(paramRandom));
    } else {
      System.err.println("Failed to fetch mob spawner entity at (" + paramInt1 + ", " + paramInt2 + ", " + paramInt3 + ")");
    }
    
    return true;
  }
  
  private String a(Random paramRandom) {
    int i = paramRandom.nextInt(4);
    if (i == 0) return "Skeleton";
    if (i == 1) return "Zombie";
    if (i == 2) return "Zombie";
    if (i == 3) return "Spider";
    return "";
  }
}
