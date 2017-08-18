package net.minecraft.server;

import java.util.Random;








public class PathfinderGoalJumpOnBlock
  extends PathfinderGoal
{
  private final EntityOcelot a;
  private final double b;
  private int c;
  private int d;
  private int e;
  private int f;
  private int g;
  private int h;
  
  public PathfinderGoalJumpOnBlock(EntityOcelot paramEntityOcelot, double paramDouble)
  {
    this.a = paramEntityOcelot;
    this.b = paramDouble;
    a(5);
  }
  
  public boolean a()
  {
    return (this.a.isTamed()) && (!this.a.isSitting()) && (this.a.aI().nextDouble() <= 0.006500000134110451D) && (f());
  }
  
  public boolean b()
  {
    return (this.c <= this.e) && (this.d <= 60) && (a(this.a.world, this.f, this.g, this.h));
  }
  
  public void c()
  {
    this.a.getNavigation().a(this.f + 0.5D, this.g + 1, this.h + 0.5D, this.b);
    this.c = 0;
    this.d = 0;
    this.e = (this.a.aI().nextInt(this.a.aI().nextInt(1200) + 1200) + 1200);
    this.a.getGoalSit().setSitting(false);
  }
  
  public void d()
  {
    this.a.setSitting(false);
  }
  
  public void e()
  {
    this.c += 1;
    this.a.getGoalSit().setSitting(false);
    if (this.a.e(this.f, this.g + 1, this.h) > 1.0D) {
      this.a.setSitting(false);
      this.a.getNavigation().a(this.f + 0.5D, this.g + 1, this.h + 0.5D, this.b);
      this.d += 1;
    } else if (!this.a.isSitting()) {
      this.a.setSitting(true);
    } else {
      this.d -= 1;
    }
  }
  
  private boolean f() {
    int i = (int)this.a.locY;
    double d1 = 2.147483647E9D;
    
    for (int j = (int)this.a.locX - 8; j < this.a.locX + 8.0D; j++) {
      for (int k = (int)this.a.locZ - 8; k < this.a.locZ + 8.0D; k++) {
        if ((a(this.a.world, j, i, k)) && (this.a.world.isEmpty(j, i + 1, k))) {
          double d2 = this.a.e(j, i, k);
          
          if (d2 < d1) {
            this.f = j;
            this.g = i;
            this.h = k;
            d1 = d2;
          }
        }
      }
    }
    
    return d1 < 2.147483647E9D;
  }
  
  private boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if (localBlock == Blocks.CHEST) {
      TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      
      if (localTileEntityChest.o < 1)
        return true;
    } else {
      if (localBlock == Blocks.BURNING_FURNACE)
        return true;
      if ((localBlock == Blocks.BED) && (!BlockBed.b(i))) {
        return true;
      }
    }
    return false;
  }
}
