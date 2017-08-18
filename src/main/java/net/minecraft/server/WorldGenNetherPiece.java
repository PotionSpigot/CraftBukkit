package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


















































































































abstract class WorldGenNetherPiece
  extends StructurePiece
{
  protected static final StructurePieceTreasure[] a = { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 5), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 5), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 15), new StructurePieceTreasure(Items.GOLD_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLD_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.FLINT_AND_STEEL, 0, 1, 1, 5), new StructurePieceTreasure(Items.NETHER_STALK, 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 8), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 5), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 3) };
  







  public WorldGenNetherPiece() {}
  







  protected WorldGenNetherPiece(int paramInt)
  {
    super(paramInt);
  }
  

  protected void b(NBTTagCompound paramNBTTagCompound) {}
  

  protected void a(NBTTagCompound paramNBTTagCompound) {}
  

  private int a(List paramList)
  {
    int i = 0;
    int j = 0;
    for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : paramList) {
      if ((localWorldGenNetherPieceWeight.d > 0) && (localWorldGenNetherPieceWeight.c < localWorldGenNetherPieceWeight.d)) {
        i = 1;
      }
      j += localWorldGenNetherPieceWeight.b;
    }
    return i != 0 ? j : -1;
  }
  

  private WorldGenNetherPiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList1, List paramList2, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i = a(paramList1);
    int j = (i > 0) && (paramInt5 <= 30) ? 1 : 0;
    
    int k = 0;
    int m; while ((k < 5) && (j != 0)) {
      k++;
      
      m = paramRandom.nextInt(i);
      for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : paramList1) {
        m -= localWorldGenNetherPieceWeight.b;
        if (m < 0)
        {
          if ((!localWorldGenNetherPieceWeight.a(paramInt5)) || ((localWorldGenNetherPieceWeight == paramWorldGenNetherPiece15.b) && (!localWorldGenNetherPieceWeight.e))) {
            break;
          }
          
          WorldGenNetherPiece localWorldGenNetherPiece = WorldGenNetherPieces.a(localWorldGenNetherPieceWeight, paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
          if (localWorldGenNetherPiece != null) {
            localWorldGenNetherPieceWeight.c += 1;
            paramWorldGenNetherPiece15.b = localWorldGenNetherPieceWeight;
            
            if (!localWorldGenNetherPieceWeight.a()) {
              paramList1.remove(localWorldGenNetherPieceWeight);
            }
            return localWorldGenNetherPiece;
          }
        }
      }
    }
    return WorldGenNetherPiece2.a(paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  private StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
    if ((Math.abs(paramInt1 - paramWorldGenNetherPiece15.c().a) > 112) || (Math.abs(paramInt3 - paramWorldGenNetherPiece15.c().c) > 112)) {
      return WorldGenNetherPiece2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    List localList = paramWorldGenNetherPiece15.c;
    if (paramBoolean) {
      localList = paramWorldGenNetherPiece15.d;
    }
    WorldGenNetherPiece localWorldGenNetherPiece = a(paramWorldGenNetherPiece15, localList, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
    if (localWorldGenNetherPiece != null) {
      paramList.add(localWorldGenNetherPiece);
      paramWorldGenNetherPiece15.e.add(localWorldGenNetherPiece);
    }
    return localWorldGenNetherPiece;
  }
  
  protected StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.g) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.c - 1, this.g, d(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.f + 1, this.g, d(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d(), paramBoolean);
    }
    return null;
  }
  
  protected StructurePiece b(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.g) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d(), paramBoolean);
    }
    return null;
  }
  
  protected StructurePiece c(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.g) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d(), paramBoolean);
    }
    return null;
  }
  
  protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
    return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
  }
}
