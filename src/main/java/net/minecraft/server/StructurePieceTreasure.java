package net.minecraft.server;

import java.util.Random;


public class StructurePieceTreasure
  extends WeightedRandomChoice
{
  private ItemStack b;
  private int c;
  private int d;
  
  public StructurePieceTreasure(Item paramItem, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt4);
    this.b = new ItemStack(paramItem, 1, paramInt1);
    this.c = paramInt2;
    this.d = paramInt3;
  }
  
  public StructurePieceTreasure(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt3);
    this.b = paramItemStack;
    this.c = paramInt1;
    this.d = paramInt2;
  }
  
  public static void a(Random paramRandom, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, IInventory paramIInventory, int paramInt) {
    for (int i = 0; i < paramInt; i++) {
      StructurePieceTreasure localStructurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, paramArrayOfStructurePieceTreasure);
      int j = localStructurePieceTreasure.c + paramRandom.nextInt(localStructurePieceTreasure.d - localStructurePieceTreasure.c + 1);
      
      if (localStructurePieceTreasure.b.getMaxStackSize() >= j) {
        ItemStack localItemStack1 = localStructurePieceTreasure.b.cloneItemStack();
        localItemStack1.count = j;
        paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), localItemStack1);
      }
      else {
        for (int k = 0; k < j; k++) {
          ItemStack localItemStack2 = localStructurePieceTreasure.b.cloneItemStack();
          localItemStack2.count = 1;
          paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), localItemStack2);
        }
      }
    }
  }
  
  public static void a(Random paramRandom, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, TileEntityDispenser paramTileEntityDispenser, int paramInt) {
    for (int i = 0; i < paramInt; i++) {
      StructurePieceTreasure localStructurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, paramArrayOfStructurePieceTreasure);
      int j = localStructurePieceTreasure.c + paramRandom.nextInt(localStructurePieceTreasure.d - localStructurePieceTreasure.c + 1);
      
      if (localStructurePieceTreasure.b.getMaxStackSize() >= j) {
        ItemStack localItemStack1 = localStructurePieceTreasure.b.cloneItemStack();
        localItemStack1.count = j;
        paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), localItemStack1);
      }
      else {
        for (int k = 0; k < j; k++) {
          ItemStack localItemStack2 = localStructurePieceTreasure.b.cloneItemStack();
          localItemStack2.count = 1;
          paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), localItemStack2);
        }
      }
    }
  }
  
  public static StructurePieceTreasure[] a(StructurePieceTreasure[] paramArrayOfStructurePieceTreasure1, StructurePieceTreasure... paramVarArgs) {
    StructurePieceTreasure[] arrayOfStructurePieceTreasure1 = new StructurePieceTreasure[paramArrayOfStructurePieceTreasure1.length + paramVarArgs.length];
    int i = 0;
    
    for (int j = 0; j < paramArrayOfStructurePieceTreasure1.length; j++) {
      arrayOfStructurePieceTreasure1[(i++)] = paramArrayOfStructurePieceTreasure1[j];
    }
    
    for (StructurePieceTreasure localStructurePieceTreasure : paramVarArgs) {
      arrayOfStructurePieceTreasure1[(i++)] = localStructurePieceTreasure;
    }
    
    return arrayOfStructurePieceTreasure1;
  }
}
