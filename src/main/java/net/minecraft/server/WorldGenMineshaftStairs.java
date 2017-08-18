package net.minecraft.server;

import java.util.List;
import java.util.Random;


























































































































































































































































































































































































































































































































































































































public class WorldGenMineshaftStairs
  extends StructurePiece
{
  public WorldGenMineshaftStairs() {}
  
  public WorldGenMineshaftStairs(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  


  protected void a(NBTTagCompound paramNBTTagCompound) {}
  


  protected void b(NBTTagCompound paramNBTTagCompound) {}
  


  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2 - 5, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
    
    switch (paramInt4) {
    case 2: 
      localStructureBoundingBox.d = (paramInt1 + 2);
      localStructureBoundingBox.c = (paramInt3 - 8);
      break;
    case 0: 
      localStructureBoundingBox.d = (paramInt1 + 2);
      localStructureBoundingBox.f = (paramInt3 + 8);
      break;
    case 1: 
      localStructureBoundingBox.a = (paramInt1 - 8);
      localStructureBoundingBox.f = (paramInt3 + 2);
      break;
    case 3: 
      localStructureBoundingBox.d = (paramInt1 + 8);
      localStructureBoundingBox.f = (paramInt3 + 2);
    }
    
    
    if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
      return null;
    }
    
    return localStructureBoundingBox;
  }
  
  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = d();
    

    switch (this.g) {
    case 2: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b, this.f.c - 1, 2, i);
      break;
    case 0: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b, this.f.f + 1, 0, i);
      break;
    case 1: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c, 1, i);
      break;
    case 3: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c, 3, i);
    }
    
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.AIR, Blocks.AIR, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.AIR, Blocks.AIR, false);
    
    for (int i = 0; i < 5; i++) {
      a(paramWorld, paramStructureBoundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR, Blocks.AIR, false);
    }
    
    return true;
  }
}
