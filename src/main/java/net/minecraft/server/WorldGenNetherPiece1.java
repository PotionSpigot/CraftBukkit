package net.minecraft.server;

import java.util.List;
import java.util.Random;























































































































































































































































































































































































































































































































public class WorldGenNetherPiece1
  extends WorldGenNetherPiece
{
  public WorldGenNetherPiece1() {}
  
  public WorldGenNetherPiece1(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  
  protected WorldGenNetherPiece1(Random paramRandom, int paramInt1, int paramInt2)
  {
    super(0);
    
    this.g = paramRandom.nextInt(4);
    
    switch (this.g) {
    case 0: 
    case 2: 
      this.f = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
      break;
    default: 
      this.f = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
    }
    
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 8, 3, false);
    b((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
    c((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
  }
  

  public static WorldGenNetherPiece1 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -8, -3, 0, 19, 10, 19, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece1(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR, Blocks.AIR, false);
    
    a(paramWorld, paramStructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    int j; for (int i = 7; i <= 11; i++) {
      for (j = 0; j <= 2; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, 18 - j, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    for (i = 0; i <= 2; i++) {
      for (j = 7; j <= 11; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Blocks.NETHER_BRICK, 0, 18 - i, -1, j, paramStructureBoundingBox);
      }
    }
    
    return true;
  }
}
