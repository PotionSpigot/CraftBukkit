package net.minecraft.server;

import java.util.List;
import java.util.Random;




























































































































































































































































































































































































































































































































































































































public class WorldGenNetherPiece13
  extends WorldGenNetherPiece
{
  public WorldGenNetherPiece13() {}
  
  public WorldGenNetherPiece13(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 2, 0, false);
    b((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
    c((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
  }
  

  public static WorldGenNetherPiece13 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 9, 7, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece13(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    a(paramWorld, paramStructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    

    for (int i = 0; i <= 6; i++) {
      for (int j = 0; j <= 6; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
      }
    }
    
    return true;
  }
}
