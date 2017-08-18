package net.minecraft.server;

import java.util.List;
import java.util.Random;























































































































































































































































































































































public class WorldGenNetherPiece3
  extends WorldGenNetherPiece
{
  public WorldGenNetherPiece3() {}
  
  public WorldGenNetherPiece3(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 3, false);
  }
  

  public static WorldGenNetherPiece3 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -3, 0, 5, 10, 19, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece3(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    for (int i = 0; i <= 4; i++) {
      for (int j = 0; j <= 2; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, 18 - j, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    
    return true;
  }
}
