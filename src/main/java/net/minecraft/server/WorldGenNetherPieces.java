package net.minecraft.server;

import java.util.List;
import java.util.Random;












public class WorldGenNetherPieces
{
  public static void a()
  {
    WorldGenFactory.a(WorldGenNetherPiece1.class, "NeBCr");
    WorldGenFactory.a(WorldGenNetherPiece2.class, "NeBEF");
    WorldGenFactory.a(WorldGenNetherPiece3.class, "NeBS");
    WorldGenFactory.a(WorldGenNetherPiece4.class, "NeCCS");
    WorldGenFactory.a(WorldGenNetherPiece5.class, "NeCTB");
    WorldGenFactory.a(WorldGenNetherPiece6.class, "NeCE");
    WorldGenFactory.a(WorldGenNetherPiece7.class, "NeSCSC");
    WorldGenFactory.a(WorldGenNetherPiece8.class, "NeSCLT");
    WorldGenFactory.a(WorldGenNetherPiece9.class, "NeSC");
    WorldGenFactory.a(WorldGenNetherPiece10.class, "NeSCRT");
    WorldGenFactory.a(WorldGenNetherPiece11.class, "NeCSR");
    WorldGenFactory.a(WorldGenNetherPiece12.class, "NeMT");
    WorldGenFactory.a(WorldGenNetherPiece13.class, "NeRC");
    WorldGenFactory.a(WorldGenNetherPiece16.class, "NeSR");
    WorldGenFactory.a(WorldGenNetherPiece15.class, "NeStart");
  }
  




























  private static final WorldGenNetherPieceWeight[] a = { new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece16.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 5, 1) };
  






  private static final WorldGenNetherPieceWeight[] b = { new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 5, 2) };
  









  private static WorldGenNetherPiece b(WorldGenNetherPieceWeight paramWorldGenNetherPieceWeight, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Class localClass = paramWorldGenNetherPieceWeight.a;
    Object localObject = null;
    
    if (localClass == WorldGenNetherPiece3.class) {
      localObject = WorldGenNetherPiece3.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece1.class) {
      localObject = WorldGenNetherPiece1.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece13.class) {
      localObject = WorldGenNetherPiece13.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece16.class) {
      localObject = WorldGenNetherPiece16.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece12.class) {
      localObject = WorldGenNetherPiece12.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece6.class) {
      localObject = WorldGenNetherPiece6.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece9.class) {
      localObject = WorldGenNetherPiece9.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece10.class) {
      localObject = WorldGenNetherPiece10.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece8.class) {
      localObject = WorldGenNetherPiece8.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece4.class) {
      localObject = WorldGenNetherPiece4.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece5.class) {
      localObject = WorldGenNetherPiece5.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece7.class) {
      localObject = WorldGenNetherPiece7.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece11.class) {
      localObject = WorldGenNetherPiece11.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    return (WorldGenNetherPiece)localObject;
  }
}
