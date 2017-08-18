package net.minecraft.server;

import java.util.List;
import java.util.Random;



























































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































public class WorldGenNetherPiece11
  extends WorldGenNetherPiece
{
  public WorldGenNetherPiece11() {}
  
  public WorldGenNetherPiece11(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 11, true);
  }
  

  public static WorldGenNetherPiece11 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece11(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    a(paramWorld, paramStructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    for (int i = 1; i <= 11; i += 2) {
      a(paramWorld, paramStructureBoundingBox, i, 10, 0, i, 11, 0, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
      a(paramWorld, paramStructureBoundingBox, i, 10, 12, i, 11, 12, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
      a(paramWorld, paramStructureBoundingBox, 0, 10, i, 0, 11, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
      a(paramWorld, paramStructureBoundingBox, 12, 10, i, 12, 11, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
      a(paramWorld, Blocks.NETHER_BRICK, 0, i, 13, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_BRICK, 0, i, 13, 12, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_BRICK, 0, 0, 13, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_BRICK, 0, 12, 13, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_FENCE, 0, i + 1, 13, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_FENCE, 0, i + 1, 13, 12, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, i + 1, paramStructureBoundingBox);
      a(paramWorld, Blocks.NETHER_FENCE, 0, 12, 13, i + 1, paramStructureBoundingBox);
    }
    a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 12, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_FENCE, 0, 12, 13, 0, paramStructureBoundingBox);
    

    for (i = 3; i <= 9; i += 2) {
      a(paramWorld, paramStructureBoundingBox, 1, 7, i, 1, 8, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
      a(paramWorld, paramStructureBoundingBox, 11, 7, i, 11, 8, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    }
    

    i = a(Blocks.NETHER_BRICK_STAIRS, 3);
    for (int j = 0; j <= 6; j++) {
      k = j + 4;
      for (m = 5; m <= 7; m++) {
        a(paramWorld, Blocks.NETHER_BRICK_STAIRS, i, m, 5 + j, k, paramStructureBoundingBox);
      }
      if ((k >= 5) && (k <= 8)) {
        a(paramWorld, paramStructureBoundingBox, 5, 5, k, 7, j + 4, k, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
      } else if ((k >= 9) && (k <= 10)) {
        a(paramWorld, paramStructureBoundingBox, 5, 8, k, 7, j + 4, k, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
      }
      if (j >= 1) {
        a(paramWorld, paramStructureBoundingBox, 5, 6 + j, k, 7, 9 + j, k, Blocks.AIR, Blocks.AIR, false);
      }
    }
    for (j = 5; j <= 7; j++) {
      a(paramWorld, Blocks.NETHER_BRICK_STAIRS, i, j, 12, 11, paramStructureBoundingBox);
    }
    a(paramWorld, paramStructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    j = a(Blocks.NETHER_BRICK_STAIRS, 0);
    int k = a(Blocks.NETHER_BRICK_STAIRS, 1);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 10, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 10, paramStructureBoundingBox);
    

    a(paramWorld, paramStructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND, Blocks.SOUL_SAND, false);
    a(paramWorld, paramStructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND, Blocks.SOUL_SAND, false);
    a(paramWorld, paramStructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART, Blocks.NETHER_WART, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART, Blocks.NETHER_WART, false);
    

    a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    int n;
    for (int m = 4; m <= 8; m++) {
      for (n = 0; n <= 2; n++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, m, -1, n, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, m, -1, 12 - n, paramStructureBoundingBox);
      }
    }
    for (m = 0; m <= 2; m++) {
      for (n = 4; n <= 8; n++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, m, -1, n, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, 12 - m, -1, n, paramStructureBoundingBox);
      }
    }
    
    return true;
  }
}
