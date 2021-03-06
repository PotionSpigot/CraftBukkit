package net.minecraft.server;

import java.util.List;
import java.util.Random;
















































































































































































































































































































































































































































































































































































































































































public class WorldGenStrongholdChestCorridor
  extends WorldGenStrongholdPiece
{
  private static final StructurePieceTreasure[] a = { new StructurePieceTreasure(Items.ENDER_PEARL, 0, 1, 1, 10), new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
  









  private boolean b;
  









  public WorldGenStrongholdChestCorridor() {}
  









  public WorldGenStrongholdChestCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.d = a(paramRandom);
    this.f = paramStructureBoundingBox;
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setBoolean("Chest", this.b);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.b = paramNBTTagCompound.getBoolean("Chest");
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
  }
  

  public static WorldGenStrongholdChestCorridor a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdChestCorridor(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.c());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
    

    a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.SMOOTH_BRICK, Blocks.SMOOTH_BRICK, false);
    a(paramWorld, Blocks.STEP, 5, 3, 1, 1, paramStructureBoundingBox);
    a(paramWorld, Blocks.STEP, 5, 3, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.STEP, 5, 3, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.STEP, 5, 3, 2, 4, paramStructureBoundingBox);
    for (int i = 2; i <= 4; i++) {
      a(paramWorld, Blocks.STEP, 5, 2, 1, i, paramStructureBoundingBox);
    }
    
    if (!this.b) {
      i = a(2);
      int j = a(3, 3);int k = b(3, 3);
      if (paramStructureBoundingBox.b(j, i, k)) {
        this.b = true;
        a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 2, 3, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(2));
      }
    }
    
    return true;
  }
}
