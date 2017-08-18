package net.minecraft.server;

import java.util.List;
import java.util.Random;













































































































































































































abstract class WorldGenStrongholdPiece
  extends StructurePiece
{
  protected WorldGenStrongholdDoorType d = WorldGenStrongholdDoorType.a;
  

  public WorldGenStrongholdPiece() {}
  
  protected WorldGenStrongholdPiece(int paramInt)
  {
    super(paramInt);
  }
  




  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setString("EntryDoor", this.d.name());
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    this.d = WorldGenStrongholdDoorType.valueOf(paramNBTTagCompound.getString("EntryDoor"));
  }
  
  protected void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, WorldGenStrongholdDoorType paramWorldGenStrongholdDoorType, int paramInt1, int paramInt2, int paramInt3)
  {
    switch (WorldGenStrongholdPieceWeight3.a[paramWorldGenStrongholdDoorType.ordinal()]) {
    case 1: 
    default: 
      a(paramWorld, paramStructureBoundingBox, paramInt1, paramInt2, paramInt3, paramInt1 + 3 - 1, paramInt2 + 3 - 1, paramInt3, Blocks.AIR, Blocks.AIR, false);
      break;
    case 2: 
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.WOODEN_DOOR, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.WOODEN_DOOR, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      break;
    case 3: 
      a(paramWorld, Blocks.AIR, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.AIR, 0, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      break;
    case 4: 
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_DOOR_BLOCK, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.IRON_DOOR_BLOCK, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Blocks.STONE_BUTTON, a(Blocks.STONE_BUTTON, 4), paramInt1 + 2, paramInt2 + 1, paramInt3 + 1, paramStructureBoundingBox);
      a(paramWorld, Blocks.STONE_BUTTON, a(Blocks.STONE_BUTTON, 3), paramInt1 + 2, paramInt2 + 1, paramInt3 - 1, paramStructureBoundingBox);
    }
  }
  
  protected WorldGenStrongholdDoorType a(Random paramRandom)
  {
    int i = paramRandom.nextInt(5);
    switch (i) {
    case 0: 
    case 1: 
    default: 
      return WorldGenStrongholdDoorType.a;
    case 2: 
      return WorldGenStrongholdDoorType.b;
    case 3: 
      return WorldGenStrongholdDoorType.c;
    }
    return WorldGenStrongholdDoorType.d;
  }
  
  protected StructurePiece a(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2)
  {
    switch (this.g) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.c - 1, this.g, d());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.f + 1, this.g, d());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d());
    }
    return null;
  }
  
  protected StructurePiece b(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.g) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d());
    }
    return null;
  }
  
  protected StructurePiece c(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.g) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d());
    }
    return null;
  }
  
  protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
    return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
  }
}
