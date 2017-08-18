package net.minecraft.server;

import java.util.Comparator;






















































public class DistanceComparator
  implements Comparator
{
  private final Entity a;
  
  public DistanceComparator(Entity paramEntity)
  {
    this.a = paramEntity;
  }
  
  public int a(Entity paramEntity1, Entity paramEntity2)
  {
    double d1 = this.a.f(paramEntity1);
    double d2 = this.a.f(paramEntity2);
    if (d1 < d2) return -1;
    if (d1 > d2) return 1;
    return 0;
  }
}
