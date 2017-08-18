package net.minecraft.server;

import java.util.Random;







public class PathfinderGoalMoveIndoors
  extends PathfinderGoal
{
  private int d = -1; private int c = -1;
  private VillageDoor b;
  
  public PathfinderGoalMoveIndoors(EntityCreature paramEntityCreature) { this.a = paramEntityCreature;
    a(1);
  }
  
  private EntityCreature a;
  public boolean a() {
    int i = MathHelper.floor(this.a.locX);
    int j = MathHelper.floor(this.a.locY);
    int k = MathHelper.floor(this.a.locZ);
    if (((this.a.world.w()) && (!this.a.world.Q()) && (this.a.world.getBiome(i, k).e())) || (this.a.world.worldProvider.g)) return false;
    if (this.a.aI().nextInt(50) != 0) return false;
    if ((this.c != -1) && (this.a.e(this.c, this.a.locY, this.d) < 4.0D)) return false;
    Village localVillage = this.a.world.villages.getClosestVillage(i, j, k, 14);
    if (localVillage == null) return false;
    this.b = localVillage.c(i, j, k);
    return this.b != null;
  }
  
  public boolean b()
  {
    return !this.a.getNavigation().g();
  }
  
  public void c()
  {
    this.c = -1;
    if (this.a.e(this.b.getIndoorsX(), this.b.locY, this.b.getIndoorsZ()) > 256.0D) {
      Vec3D localVec3D = RandomPositionGenerator.a(this.a, 14, 3, Vec3D.a(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D));
      if (localVec3D != null) this.a.getNavigation().a(localVec3D.a, localVec3D.b, localVec3D.c, 1.0D);
    } else { this.a.getNavigation().a(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D, 1.0D);
    }
  }
  
  public void d() {
    this.c = this.b.getIndoorsX();
    this.d = this.b.getIndoorsZ();
    this.b = null;
  }
}
