package net.minecraft.server;

import java.util.Random;

















abstract class WorldGenScatteredPiece
  extends StructurePiece
{
  protected int a;
  protected int b;
  protected int c;
  protected int d = -1;
  

  public WorldGenScatteredPiece() {}
  
  protected WorldGenScatteredPiece(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super(0);
    
    this.a = paramInt4;
    this.b = paramInt5;
    this.c = paramInt6;
    
    this.g = paramRandom.nextInt(4);
    
    switch (this.g) {
    case 0: 
    case 2: 
      this.f = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1 + paramInt4 - 1, paramInt2 + paramInt5 - 1, paramInt3 + paramInt6 - 1);
      break;
    default: 
      this.f = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1 + paramInt6 - 1, paramInt2 + paramInt5 - 1, paramInt3 + paramInt4 - 1);
    }
    
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setInt("Width", this.a);
    paramNBTTagCompound.setInt("Height", this.b);
    paramNBTTagCompound.setInt("Depth", this.c);
    paramNBTTagCompound.setInt("HPos", this.d);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    this.a = paramNBTTagCompound.getInt("Width");
    this.b = paramNBTTagCompound.getInt("Height");
    this.c = paramNBTTagCompound.getInt("Depth");
    this.d = paramNBTTagCompound.getInt("HPos");
  }
  
  protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt)
  {
    if (this.d >= 0) {
      return true;
    }
    
    int i = 0;
    int j = 0;
    for (int k = this.f.c; k <= this.f.f; k++) {
      for (int m = this.f.a; m <= this.f.d; m++) {
        if (paramStructureBoundingBox.b(m, 64, k)) {
          i += Math.max(paramWorld.i(m, k), paramWorld.worldProvider.getSeaLevel());
          j++;
        }
      }
    }
    
    if (j == 0) {
      return false;
    }
    this.d = (i / j);
    this.f.a(0, this.d - this.f.b + paramInt, 0);
    return true;
  }
}
