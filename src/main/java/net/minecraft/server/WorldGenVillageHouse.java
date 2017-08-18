package net.minecraft.server;

import java.util.List;
import java.util.Random;













































































































































































































































































































































































































































































































































































































































































public class WorldGenVillageHouse
  extends WorldGenVillagePiece
{
  private boolean a;
  
  public WorldGenVillageHouse() {}
  
  public WorldGenVillageHouse(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramWorldGenVillageStartPiece, paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
    this.a = paramRandom.nextBoolean();
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setBoolean("Terrace", this.a);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a = paramNBTTagCompound.getBoolean("Terrace");
  }
  
  public static WorldGenVillageHouse a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 6, 5, paramInt4);
    
    if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
      return null;
    }
    
    return new WorldGenVillageHouse(paramWorldGenVillageStartPiece, paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.k < 0) {
      this.k = b(paramWorld, paramStructureBoundingBox);
      if (this.k < 0) {
        return true;
      }
      this.f.a(0, this.k - this.f.e + 6 - 1, 0);
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.LOG, Blocks.LOG, false);
    a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.WOOD, Blocks.WOOD, false);
    

    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 0, 3, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 4, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.WOOD, Blocks.WOOD, false);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.WOOD, Blocks.WOOD, false);
    a(paramWorld, paramStructureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.WOOD, Blocks.WOOD, false);
    a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 2, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 2, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.WOOD, 0, 1, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 1, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 1, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 2, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 3, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 3, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.WOOD, 0, 3, 1, 0, paramStructureBoundingBox);
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR)) {
      a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.AIR, Blocks.AIR, false);
    

    if (this.a) {
      a(paramWorld, Blocks.FENCE, 0, 0, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 1, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 2, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 3, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 4, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 0, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 1, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 2, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 3, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 4, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 4, 5, 1, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 4, 5, 2, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 4, 5, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 0, 5, 1, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 0, 5, 2, paramStructureBoundingBox);
      a(paramWorld, Blocks.FENCE, 0, 0, 5, 3, paramStructureBoundingBox);
    }
    

    if (this.a) {
      i = a(Blocks.LADDER, 3);
      a(paramWorld, Blocks.LADDER, i, 3, 1, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.LADDER, i, 3, 2, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.LADDER, i, 3, 3, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.LADDER, i, 3, 4, 3, paramStructureBoundingBox);
    }
    

    a(paramWorld, Blocks.TORCH, 0, 2, 3, 1, paramStructureBoundingBox);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        b(paramWorld, j, 6, i, paramStructureBoundingBox);
        b(paramWorld, Blocks.COBBLESTONE, 0, j, -1, i, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 1, 1, 2, 1);
    
    return true;
  }
}
