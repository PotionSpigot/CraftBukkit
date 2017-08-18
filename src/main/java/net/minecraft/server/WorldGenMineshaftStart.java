package net.minecraft.server;

import java.util.LinkedList;
import java.util.Random;




public class WorldGenMineshaftStart
  extends StructureStart
{
  public WorldGenMineshaftStart() {}
  
  public WorldGenMineshaftStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
    
    WorldGenMineshaftRoom localWorldGenMineshaftRoom = new WorldGenMineshaftRoom(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
    this.a.add(localWorldGenMineshaftRoom);
    localWorldGenMineshaftRoom.a(localWorldGenMineshaftRoom, this.a, paramRandom);
    
    c();
    a(paramWorld, paramRandom, 10);
  }
}
