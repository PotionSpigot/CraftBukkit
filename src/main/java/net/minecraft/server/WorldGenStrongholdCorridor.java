package net.minecraft.server;

import java.util.List;
import java.util.Random;
























































































































































































































































































































































public class WorldGenStrongholdCorridor
  extends WorldGenStrongholdPiece
{
  private int a;
  
  public WorldGenStrongholdCorridor() {}
  
  public WorldGenStrongholdCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
    this.a = ((paramInt2 == 2) || (paramInt2 == 0) ? paramStructureBoundingBox.d() : paramStructureBoundingBox.b());
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setInt("Steps", this.a);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a = paramNBTTagCompound.getInt("Steps");
  }
  
  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = 3;
    
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 4, paramInt4);
    
    StructurePiece localStructurePiece = StructurePiece.a(paramList, localStructureBoundingBox);
    if (localStructurePiece == null)
    {
      return null;
    }
    
    if (localStructurePiece.c().b == localStructureBoundingBox.b)
    {
      for (int j = 3; j >= 1; j--) {
        localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, j - 1, paramInt4);
        if (!localStructurePiece.c().a(localStructureBoundingBox))
        {

          return StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, j, paramInt4);
        }
      }
    }
    
    return null;
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    for (int i = 0; i < this.a; i++)
    {
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, 0, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 0, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 0, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 0, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, 0, i, paramStructureBoundingBox);
      
      for (int j = 1; j <= 3; j++) {
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, j, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.AIR, 0, 1, j, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.AIR, 0, 2, j, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.AIR, 0, 3, j, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, j, i, paramStructureBoundingBox);
      }
      
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, 4, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 4, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 4, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 4, i, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, 4, i, paramStructureBoundingBox);
    }
    
    return true;
  }
}
