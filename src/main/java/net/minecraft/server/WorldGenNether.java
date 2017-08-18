package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WorldGenNether
  extends StructureGenerator
{
  private List e = new ArrayList();
  

  public WorldGenNether()
  {
    this.e.add(new BiomeMeta(EntityBlaze.class, 10, 2, 3));
    this.e.add(new BiomeMeta(EntityPigZombie.class, 5, 4, 4));
    this.e.add(new BiomeMeta(EntitySkeleton.class, 10, 4, 4));
    this.e.add(new BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
  }
  
  public String a()
  {
    return "Fortress";
  }
  
  public List b()
  {
    return this.e;
  }
  

  protected boolean a(int paramInt1, int paramInt2)
  {
    int i = paramInt1 >> 4;
    int j = paramInt2 >> 4;
    
    this.b.setSeed(i ^ j << 4 ^ this.c.getSeed());
    this.b.nextInt();
    
    if (this.b.nextInt(3) != 0) {
      return false;
    }
    if (paramInt1 != (i << 4) + 4 + this.b.nextInt(8)) {
      return false;
    }
    if (paramInt2 != (j << 4) + 4 + this.b.nextInt(8)) {
      return false;
    }
    return true;
  }
  
  protected StructureStart b(int paramInt1, int paramInt2)
  {
    return new WorldGenNetherStart(this.c, this.b, paramInt1, paramInt2);
  }
}
