package net.minecraft.server;

import java.util.List;
import java.util.Random;












































































































































































































































































































































































































































































































































































































































































































































































































































































































































































public class WorldGenStrongholdRoomCrossing
  extends WorldGenStrongholdPiece
{
  private static final StructurePieceTreasure[] b = { new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1) };
  




  protected int a;
  





  public WorldGenStrongholdRoomCrossing() {}
  





  public WorldGenStrongholdRoomCrossing(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.d = a(paramRandom);
    this.f = paramStructureBoundingBox;
    this.a = paramRandom.nextInt(5);
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setInt("Type", this.a);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a = paramNBTTagCompound.getInt("Type");
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 4, 1);
    b((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
    c((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
  }
  

  public static WorldGenStrongholdRoomCrossing a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 11, 7, 11, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdRoomCrossing(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 10, 6, 10, true, paramRandom, WorldGenStrongholdPieces.c());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 1, 0);
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.AIR, Blocks.AIR, false);
    int i;
    switch (this.a)
    {
    default: 
      break;
    case 0: 
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 2, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 3, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.TORCH, 0, 4, 3, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.TORCH, 0, 6, 3, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.TORCH, 0, 5, 3, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.TORCH, 0, 5, 3, 6, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 4, 1, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 4, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 4, 1, 6, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 6, 1, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 6, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 6, 1, 6, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 5, 1, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.STEP, 0, 5, 1, 6, paramStructureBoundingBox);
      break;
    case 1: 
      for (i = 0; i < 5; i++) {
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 1, 3 + i, paramStructureBoundingBox);
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 7, 1, 3 + i, paramStructureBoundingBox);
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3 + i, 1, 3, paramStructureBoundingBox);
        a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3 + i, 1, 7, paramStructureBoundingBox);
      }
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 2, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 3, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.WATER, 0, 5, 4, 5, paramStructureBoundingBox);
      break;
    case 2: 
      for (i = 1; i <= 9; i++) {
        a(paramWorld, Blocks.COBBLESTONE, 0, 1, 3, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.COBBLESTONE, 0, 9, 3, i, paramStructureBoundingBox);
      }
      for (i = 1; i <= 9; i++) {
        a(paramWorld, Blocks.COBBLESTONE, 0, i, 3, 1, paramStructureBoundingBox);
        a(paramWorld, Blocks.COBBLESTONE, 0, i, 3, 9, paramStructureBoundingBox);
      }
      a(paramWorld, Blocks.COBBLESTONE, 0, 5, 1, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 5, 1, 6, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 5, 3, 4, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 5, 3, 6, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 6, 1, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 5, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE, 0, 6, 3, 5, paramStructureBoundingBox);
      for (i = 1; i <= 3; i++) {
        a(paramWorld, Blocks.COBBLESTONE, 0, 4, i, 4, paramStructureBoundingBox);
        a(paramWorld, Blocks.COBBLESTONE, 0, 6, i, 4, paramStructureBoundingBox);
        a(paramWorld, Blocks.COBBLESTONE, 0, 4, i, 6, paramStructureBoundingBox);
        a(paramWorld, Blocks.COBBLESTONE, 0, 6, i, 6, paramStructureBoundingBox);
      }
      a(paramWorld, Blocks.TORCH, 0, 5, 3, 5, paramStructureBoundingBox);
      for (i = 2; i <= 8; i++) {
        a(paramWorld, Blocks.WOOD, 0, 2, 3, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.WOOD, 0, 3, 3, i, paramStructureBoundingBox);
        if ((i <= 3) || (i >= 7)) {
          a(paramWorld, Blocks.WOOD, 0, 4, 3, i, paramStructureBoundingBox);
          a(paramWorld, Blocks.WOOD, 0, 5, 3, i, paramStructureBoundingBox);
          a(paramWorld, Blocks.WOOD, 0, 6, 3, i, paramStructureBoundingBox);
        }
        a(paramWorld, Blocks.WOOD, 0, 7, 3, i, paramStructureBoundingBox);
        a(paramWorld, Blocks.WOOD, 0, 8, 3, i, paramStructureBoundingBox);
      }
      a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 1, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 2, 3, paramStructureBoundingBox);
      a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 3, 3, paramStructureBoundingBox);
      
      a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 4, 8, StructurePieceTreasure.a(b, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 1 + paramRandom.nextInt(4));
    }
    
    
    return true;
  }
}
