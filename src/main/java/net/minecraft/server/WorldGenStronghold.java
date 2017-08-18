package net.minecraft.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;




public class WorldGenStronghold
  extends StructureGenerator
{
  private List e;
  private boolean f;
  private ChunkCoordIntPair[] g = new ChunkCoordIntPair[3];
  private double h = 32.0D;
  private int i = 3;
  
  public WorldGenStronghold()
  {
    this.e = new ArrayList();
    for (BiomeBase localBiomeBase : BiomeBase.getBiomes()) {
      if ((localBiomeBase != null) && (localBiomeBase.am > 0.0F)) {
        this.e.add(localBiomeBase);
      }
    }
  }
  
  public WorldGenStronghold(Map paramMap) {
    this();
    for (Entry localEntry : paramMap.entrySet()) {
      if (((String)localEntry.getKey()).equals("distance")) {
        this.h = MathHelper.a((String)localEntry.getValue(), this.h, 1.0D);
      } else if (((String)localEntry.getKey()).equals("count")) {
        this.g = new ChunkCoordIntPair[MathHelper.a((String)localEntry.getValue(), this.g.length, 1)];
      } else if (((String)localEntry.getKey()).equals("spread")) {
        this.i = MathHelper.a((String)localEntry.getValue(), this.i, 1);
      }
    }
  }
  
  public String a()
  {
    return "Stronghold";
  }
  
  protected boolean a(int paramInt1, int paramInt2)
  {
    Object localObject1;
    if (!this.f) {
      localObject1 = new Random();
      
      ((Random)localObject1).setSeed(this.c.getSeed());
      
      double d1 = ((Random)localObject1).nextDouble() * 3.141592653589793D * 2.0D;
      int j = 1;
      
      for (int k = 0; k < this.g.length; k++) {
        double d2 = (1.25D * j + ((Random)localObject1).nextDouble()) * (this.h * j);
        int m = (int)Math.round(Math.cos(d1) * d2);
        int n = (int)Math.round(Math.sin(d1) * d2);
        
        ChunkPosition localChunkPosition = this.c.getWorldChunkManager().a((m << 4) + 8, (n << 4) + 8, 112, this.e, (Random)localObject1);
        if (localChunkPosition != null) {
          m = localChunkPosition.x >> 4;
          n = localChunkPosition.z >> 4;
        }
        
        this.g[k] = new ChunkCoordIntPair(m, n);
        
        d1 += 6.283185307179586D * j / this.i;
        
        if (k == this.i) {
          j += 2 + ((Random)localObject1).nextInt(5);
          this.i += 1 + ((Random)localObject1).nextInt(2);
        }
      }
      
      this.f = true;
    }
    for (Object localObject2 : this.g) {
      if ((paramInt1 == ((ChunkCoordIntPair)localObject2).x) && (paramInt2 == ((ChunkCoordIntPair)localObject2).z)) {
        return true;
      }
    }
    return false;
  }
  
  protected List o_()
  {
    ArrayList localArrayList = new ArrayList();
    for (ChunkCoordIntPair localChunkCoordIntPair : this.g) {
      if (localChunkCoordIntPair != null) {
        localArrayList.add(localChunkCoordIntPair.a(64));
      }
    }
    return localArrayList;
  }
  
  protected StructureStart b(int paramInt1, int paramInt2)
  {
    WorldGenStronghold2Start localWorldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
    
    while ((localWorldGenStronghold2Start.b().isEmpty()) || (((WorldGenStrongholdStart)localWorldGenStronghold2Start.b().get(0)).b == null))
    {
      localWorldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
    }
    
    return localWorldGenStronghold2Start;
  }
}
