package net.minecraft.server;

import java.util.List;
import java.util.Random;



























































































































































































































































































































































































































































































































































































































































































































































































































































































public class WorldGenNetherPiece6
  extends WorldGenNetherPiece
{
  public WorldGenNetherPiece6() {}
  
  public WorldGenNetherPiece6(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
  }
  

  public static WorldGenNetherPiece6 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece6(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
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
    

    a(paramWorld, paramStructureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    

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
    

    a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    for (i = 4; i <= 8; i++) {
      for (j = 0; j <= 2; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, 12 - j, paramStructureBoundingBox);
      }
    }
    for (i = 0; i <= 2; i++) {
      for (j = 4; j <= 8; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, 12 - i, -1, j, paramStructureBoundingBox);
      }
    }
    

    a(paramWorld, paramStructureBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, Blocks.NETHER_BRICK, 0, 6, 0, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.LAVA, 0, 6, 5, 6, paramStructureBoundingBox);
    
    i = a(6, 6);
    int j = a(5);
    int k = b(6, 6);
    if (paramStructureBoundingBox.b(i, j, k)) {
      paramWorld.d = true;
      Blocks.LAVA.a(paramWorld, i, j, k, paramRandom);
      paramWorld.d = false;
    }
    
    return true;
  }
}
