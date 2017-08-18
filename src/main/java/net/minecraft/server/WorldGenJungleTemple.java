package net.minecraft.server;

import java.util.Random;

































































































































































































































































































































































public class WorldGenJungleTemple
  extends WorldGenScatteredPiece
{
  private boolean e;
  private boolean i;
  private boolean j;
  private boolean k;
  private static final StructurePieceTreasure[] l = { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
  












  private static final StructurePieceTreasure[] m = { new StructurePieceTreasure(Items.ARROW, 0, 2, 7, 30) };
  



  public WorldGenJungleTemple() {}
  


  public WorldGenJungleTemple(Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramRandom, paramInt1, 64, paramInt2, 12, 10, 15);
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    paramNBTTagCompound.setBoolean("placedMainChest", this.e);
    paramNBTTagCompound.setBoolean("placedHiddenChest", this.i);
    paramNBTTagCompound.setBoolean("placedTrap1", this.j);
    paramNBTTagCompound.setBoolean("placedTrap2", this.k);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.e = paramNBTTagCompound.getBoolean("placedMainChest");
    this.i = paramNBTTagCompound.getBoolean("placedHiddenChest");
    this.j = paramNBTTagCompound.getBoolean("placedTrap1");
    this.k = paramNBTTagCompound.getBoolean("placedTrap2");
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (!a(paramWorld, paramStructureBoundingBox, 0)) {
      return false;
    }
    
    int i1 = a(Blocks.COBBLESTONE_STAIRS, 3);
    int i2 = a(Blocks.COBBLESTONE_STAIRS, 2);
    int i3 = a(Blocks.COBBLESTONE_STAIRS, 0);
    int i4 = a(Blocks.COBBLESTONE_STAIRS, 1);
    

    a(paramWorld, paramStructureBoundingBox, 0, -4, 0, this.a - 1, 0, this.c - 1, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 9, 2, 2, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 2, 1, 12, 9, 2, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 2, 1, 3, 2, 2, 11, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 9, 1, 3, 9, 2, 11, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 1, 3, 1, 10, 6, 1, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 1, 3, 13, 10, 6, 13, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 1, 3, 2, 1, 6, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 10, 3, 2, 10, 6, 12, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 2, 3, 2, 9, 3, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 2, 6, 2, 9, 6, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 3, 7, 3, 8, 7, 11, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 4, 8, 4, 7, 8, 10, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 3, 1, 3, 8, 2, 11);
    a(paramWorld, paramStructureBoundingBox, 4, 3, 6, 7, 3, 9);
    a(paramWorld, paramStructureBoundingBox, 2, 4, 2, 9, 5, 12);
    a(paramWorld, paramStructureBoundingBox, 4, 6, 5, 7, 6, 9);
    a(paramWorld, paramStructureBoundingBox, 5, 7, 6, 6, 7, 8);
    

    a(paramWorld, paramStructureBoundingBox, 5, 1, 2, 6, 2, 2);
    a(paramWorld, paramStructureBoundingBox, 5, 2, 12, 6, 2, 12);
    a(paramWorld, paramStructureBoundingBox, 5, 5, 1, 6, 5, 1);
    a(paramWorld, paramStructureBoundingBox, 5, 5, 13, 6, 5, 13);
    a(paramWorld, Blocks.AIR, 0, 1, 5, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.AIR, 0, 10, 5, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.AIR, 0, 1, 5, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.AIR, 0, 10, 5, 9, paramStructureBoundingBox);
    

    for (int i5 = 0; i5 <= 14; i5 += 14) {
      a(paramWorld, paramStructureBoundingBox, 2, 4, i5, 2, 5, i5, false, paramRandom, n);
      a(paramWorld, paramStructureBoundingBox, 4, 4, i5, 4, 5, i5, false, paramRandom, n);
      a(paramWorld, paramStructureBoundingBox, 7, 4, i5, 7, 5, i5, false, paramRandom, n);
      a(paramWorld, paramStructureBoundingBox, 9, 4, i5, 9, 5, i5, false, paramRandom, n);
    }
    a(paramWorld, paramStructureBoundingBox, 5, 6, 0, 6, 6, 0, false, paramRandom, n);
    for (i5 = 0; i5 <= 11; i5 += 11) {
      for (int i6 = 2; i6 <= 12; i6 += 2) {
        a(paramWorld, paramStructureBoundingBox, i5, 4, i6, i5, 5, i6, false, paramRandom, n);
      }
      a(paramWorld, paramStructureBoundingBox, i5, 6, 5, i5, 6, 5, false, paramRandom, n);
      a(paramWorld, paramStructureBoundingBox, i5, 6, 9, i5, 6, 9, false, paramRandom, n);
    }
    a(paramWorld, paramStructureBoundingBox, 2, 7, 2, 2, 9, 2, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 9, 7, 2, 9, 9, 2, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 2, 7, 12, 2, 9, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 9, 7, 12, 9, 9, 12, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 4, 9, 4, 4, 9, 4, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 7, 9, 4, 7, 9, 4, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 4, 9, 10, 4, 9, 10, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 7, 9, 10, 7, 9, 10, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 5, 9, 7, 6, 9, 7, false, paramRandom, n);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 5, 9, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 6, 9, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i2, 5, 9, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i2, 6, 9, 8, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 4, 0, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 5, 0, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 6, 0, 0, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 7, 0, 0, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 4, 1, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 4, 2, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 4, 3, 10, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 7, 1, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 7, 2, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i1, 7, 3, 10, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 9, 4, 1, 9, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 7, 1, 9, 7, 1, 9, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 10, 7, 2, 10, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 5, 4, 5, 6, 4, 5, false, paramRandom, n);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i3, 4, 4, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.COBBLESTONE_STAIRS, i4, 7, 4, 5, paramStructureBoundingBox);
    

    for (i5 = 0; i5 < 4; i5++) {
      a(paramWorld, Blocks.COBBLESTONE_STAIRS, i2, 5, 0 - i5, 6 + i5, paramStructureBoundingBox);
      a(paramWorld, Blocks.COBBLESTONE_STAIRS, i2, 6, 0 - i5, 6 + i5, paramStructureBoundingBox);
      a(paramWorld, paramStructureBoundingBox, 5, 0 - i5, 7 + i5, 6, 0 - i5, 9 + i5);
    }
    

    a(paramWorld, paramStructureBoundingBox, 1, -3, 12, 10, -1, 13);
    a(paramWorld, paramStructureBoundingBox, 1, -3, 1, 3, -1, 13);
    a(paramWorld, paramStructureBoundingBox, 1, -3, 1, 9, -1, 5);
    for (i5 = 1; i5 <= 13; i5 += 2) {
      a(paramWorld, paramStructureBoundingBox, 1, -3, i5, 1, -2, i5, false, paramRandom, n);
    }
    for (i5 = 2; i5 <= 12; i5 += 2) {
      a(paramWorld, paramStructureBoundingBox, 1, -1, i5, 3, -1, i5, false, paramRandom, n);
    }
    a(paramWorld, paramStructureBoundingBox, 2, -2, 1, 5, -2, 1, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 7, -2, 1, 9, -2, 1, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 6, -3, 1, 6, -3, 1, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 6, -1, 1, 6, -1, 1, false, paramRandom, n);
    

    a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 3) | 0x4, 1, -3, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 1) | 0x4, 4, -3, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE, 4, 2, -3, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE, 4, 3, -3, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 7, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 1, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 4, -3, 1, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 3, -3, 1, paramStructureBoundingBox);
    if (!this.j) {
      this.j = a(paramWorld, paramStructureBoundingBox, paramRandom, 3, -2, 1, 2, m, 2);
    }
    a(paramWorld, Blocks.VINE, 15, 3, -2, 2, paramStructureBoundingBox);
    

    a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 2) | 0x4, 7, -3, 1, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 0) | 0x4, 7, -3, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -3, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -3, 6, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -3, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 9, -3, 4, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -2, 4, paramStructureBoundingBox);
    if (!this.k) {
      this.k = a(paramWorld, paramStructureBoundingBox, paramRandom, 9, -2, 3, 4, m, 2);
    }
    a(paramWorld, Blocks.VINE, 15, 8, -1, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.VINE, 15, 8, -2, 3, paramStructureBoundingBox);
    if (!this.e) {
      this.e = a(paramWorld, paramStructureBoundingBox, paramRandom, 8, -3, 3, StructurePieceTreasure.a(l, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(5));
    }
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 9, -3, 2, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 8, -3, 1, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 4, -3, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 5, -2, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 5, -1, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 6, -3, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 7, -2, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 7, -1, 5, paramStructureBoundingBox);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 8, -3, 5, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 9, -1, 1, 9, -1, 5, false, paramRandom, n);
    

    a(paramWorld, paramStructureBoundingBox, 8, -3, 8, 10, -1, 10);
    a(paramWorld, Blocks.SMOOTH_BRICK, 3, 8, -2, 11, paramStructureBoundingBox);
    a(paramWorld, Blocks.SMOOTH_BRICK, 3, 9, -2, 11, paramStructureBoundingBox);
    a(paramWorld, Blocks.SMOOTH_BRICK, 3, 10, -2, 11, paramStructureBoundingBox);
    a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 8, -2, 12, paramStructureBoundingBox);
    a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 9, -2, 12, paramStructureBoundingBox);
    a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 10, -2, 12, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 8, -3, 8, 8, -3, 10, false, paramRandom, n);
    a(paramWorld, paramStructureBoundingBox, 10, -3, 8, 10, -3, 10, false, paramRandom, n);
    a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 10, -2, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -2, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -2, 10, paramStructureBoundingBox);
    a(paramWorld, Blocks.REDSTONE_WIRE, 0, 10, -1, 9, paramStructureBoundingBox);
    a(paramWorld, Blocks.PISTON_STICKY, 1, 9, -2, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.PISTON_STICKY, a(Blocks.PISTON_STICKY, 4), 10, -2, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.PISTON_STICKY, a(Blocks.PISTON_STICKY, 4), 10, -1, 8, paramStructureBoundingBox);
    a(paramWorld, Blocks.DIODE_OFF, a(Blocks.DIODE_OFF, 2), 10, -2, 10, paramStructureBoundingBox);
    if (!this.i) {
      this.i = a(paramWorld, paramStructureBoundingBox, paramRandom, 9, -3, 10, StructurePieceTreasure.a(l, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(5));
    }
    


    return true;
  }
  











  private static WorldGenJungleTemplePiece n = new WorldGenJungleTemplePiece(null);
}
