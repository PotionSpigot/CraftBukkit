package net.minecraft.server;

import java.util.List;
import java.util.Random;

























































































































































































































































































































































































































































































































































































































































































































































































































public class WorldGenVillageTemple
  extends WorldGenVillagePiece
{
  public WorldGenVillageTemple() {}
  
  public WorldGenVillageTemple(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramWorldGenVillageStartPiece, paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  
  public static WorldGenVillageTemple a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 12, 9, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenVillageTemple(paramWorldGenVillageStartPiece, paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.k < 0) {
      this.k = b(paramWorld, paramStructureBoundingBox);
      if (this.k < 0) {
        return true;
      }
      this.f.a(0, this.k - this.f.e + 12 - 1, 0);
    }
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 11, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 11, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 2, 11, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 2, 11, 4, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.COBBLESTONE, 0, 1, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 1, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 2, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 3, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 3, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 1, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 3, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 1), 1, 2, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 0), 3, 2, 7, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 6, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 7, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 6, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 7, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 6, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 7, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 6, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 7, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 3, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 3, 8, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.TORCH, 0, 2, 4, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.TORCH, 0, 1, 4, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.TORCH, 0, 3, 4, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.TORCH, 0, 2, 4, 5, paramStructureBoundingBox);
    

    int i = a(Blocks.LADDER, 4);
    for (int j = 1; j <= 9; j++) {
      a(paramWorld, Blocks.LADDER, i, 3, j, 3, paramStructureBoundingBox);
    }
    

    a(paramWorld, Blocks.AIR, 0, 2, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.AIR, 0, 2, 2, 0, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, a(Blocks.WOODEN_DOOR, 1));
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR)) {
      a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    
    for (j = 0; j < 9; j++) {
      for (int k = 0; k < 5; k++) {
        b(paramWorld, k, 12, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.COBBLESTONE, 0, k, -1, j, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 1);
    
    return true;
  }
  
  protected int b(int paramInt)
  {
    return 2;
  }
}
