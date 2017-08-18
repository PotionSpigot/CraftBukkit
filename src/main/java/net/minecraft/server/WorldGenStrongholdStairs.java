package net.minecraft.server;

import java.util.List;
import java.util.Random;




























































































































































































































































































































































































































































































































































































public class WorldGenStrongholdStairs
  extends WorldGenStrongholdPiece
{
  private boolean a;
  private boolean b;
  
  public WorldGenStrongholdStairs() {}
  
  public WorldGenStrongholdStairs(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.d = a(paramRandom);
    this.f = paramStructureBoundingBox;
    
    this.a = (paramRandom.nextInt(2) == 0);
    this.b = (paramRandom.nextInt(2) == 0);
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setBoolean("Left", this.a);
    paramNBTTagCompound.setBoolean("Right", this.b);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a = paramNBTTagCompound.getBoolean("Left");
    this.b = paramNBTTagCompound.getBoolean("Right");
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
    if (this.a) b((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2);
    if (this.b) { c((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2);
    }
  }
  
  public static WorldGenStrongholdStairs a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdStairs(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.c());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
    
    a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 1, Blocks.TORCH, 0);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 1, Blocks.TORCH, 0);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 5, Blocks.TORCH, 0);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 5, Blocks.TORCH, 0);
    
    if (this.a) {
      a(paramWorld, paramStructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.AIR, Blocks.AIR, false);
    }
    if (this.b) {
      a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.AIR, Blocks.AIR, false);
    }
    
    return true;
  }
}
