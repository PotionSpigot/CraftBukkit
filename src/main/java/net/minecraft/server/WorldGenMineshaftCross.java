package net.minecraft.server;

import java.util.List;
import java.util.Random;







































































































































































































































































































































































































































































public class WorldGenMineshaftCross
  extends StructurePiece
{
  private int a;
  private boolean b;
  
  public WorldGenMineshaftCross() {}
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setBoolean("tf", this.b);
    paramNBTTagCompound.setInt("D", this.a);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    this.b = paramNBTTagCompound.getBoolean("tf");
    this.a = paramNBTTagCompound.getInt("D");
  }
  
  public WorldGenMineshaftCross(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    this.a = paramInt2;
    this.f = paramStructureBoundingBox;
    this.b = (paramStructureBoundingBox.c() > 3);
  }
  
  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
    
    if (paramRandom.nextInt(4) == 0) {
      localStructureBoundingBox.e += 4;
    }
    
    switch (paramInt4) {
    case 2: 
      localStructureBoundingBox.a = (paramInt1 - 1);
      localStructureBoundingBox.d = (paramInt1 + 3);
      localStructureBoundingBox.c = (paramInt3 - 4);
      break;
    case 0: 
      localStructureBoundingBox.a = (paramInt1 - 1);
      localStructureBoundingBox.d = (paramInt1 + 3);
      localStructureBoundingBox.f = (paramInt3 + 4);
      break;
    case 1: 
      localStructureBoundingBox.a = (paramInt1 - 4);
      localStructureBoundingBox.c = (paramInt3 - 1);
      localStructureBoundingBox.f = (paramInt3 + 3);
      break;
    case 3: 
      localStructureBoundingBox.d = (paramInt1 + 4);
      localStructureBoundingBox.c = (paramInt3 - 1);
      localStructureBoundingBox.f = (paramInt3 + 3);
    }
    
    
    if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
      return null;
    }
    
    return localStructureBoundingBox;
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = d();
    

    switch (this.a) {
    case 2: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
      break;
    case 0: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
      break;
    case 1: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
      break;
    case 3: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
    }
    
    
    if (this.b) {
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b + 3 + 1, this.f.c - 1, 2, i);
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b + 3 + 1, this.f.c + 1, 1, i);
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b + 3 + 1, this.f.c + 1, 3, i);
      if (paramRandom.nextBoolean()) { WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b + 3 + 1, this.f.f + 1, 0, i);
      }
    }
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    if (this.b) {
      a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c, this.f.d - 1, this.f.b + 3 - 1, this.f.f, Blocks.AIR, Blocks.AIR, false);
      
      a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c + 1, this.f.d, this.f.b + 3 - 1, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
      
      a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.e - 2, this.f.c, this.f.d - 1, this.f.e, this.f.f, Blocks.AIR, Blocks.AIR, false);
      
      a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.e - 2, this.f.c + 1, this.f.d, this.f.e, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
      
      a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b + 3, this.f.c + 1, this.f.d - 1, this.f.b + 3, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
    }
    else {
      a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c, this.f.d - 1, this.f.e, this.f.f, Blocks.AIR, Blocks.AIR, false);
      a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c + 1, this.f.d, this.f.e, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
    }
    

    a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c + 1, this.f.a + 1, this.f.e, this.f.c + 1, Blocks.WOOD, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.f - 1, this.f.a + 1, this.f.e, this.f.f - 1, Blocks.WOOD, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, this.f.d - 1, this.f.b, this.f.c + 1, this.f.d - 1, this.f.e, this.f.c + 1, Blocks.WOOD, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, this.f.d - 1, this.f.b, this.f.f - 1, this.f.d - 1, this.f.e, this.f.f - 1, Blocks.WOOD, Blocks.AIR, false);
    


    for (int i = this.f.a; i <= this.f.d; i++) {
      for (int j = this.f.c; j <= this.f.f; j++) {
        if (a(paramWorld, i, this.f.b - 1, j, paramStructureBoundingBox).getMaterial() == Material.AIR) {
          a(paramWorld, Blocks.WOOD, 0, i, this.f.b - 1, j, paramStructureBoundingBox);
        }
      }
    }
    
    return true;
  }
}
