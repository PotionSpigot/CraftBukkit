package net.minecraft.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;













































































































public class WorldGenStronghold2Start
  extends StructureStart
{
  public WorldGenStronghold2Start() {}
  
  public WorldGenStronghold2Start(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
    
    WorldGenStrongholdPieces.b();
    
    WorldGenStrongholdStart localWorldGenStrongholdStart = new WorldGenStrongholdStart(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
    this.a.add(localWorldGenStrongholdStart);
    localWorldGenStrongholdStart.a(localWorldGenStrongholdStart, this.a, paramRandom);
    
    List localList = localWorldGenStrongholdStart.c;
    while (!localList.isEmpty()) {
      int i = paramRandom.nextInt(localList.size());
      StructurePiece localStructurePiece = (StructurePiece)localList.remove(i);
      localStructurePiece.a(localWorldGenStrongholdStart, this.a, paramRandom);
    }
    
    c();
    a(paramWorld, paramRandom, 10);
  }
}
