package net.minecraft.server;

import java.util.List;







public class PathfinderGoalAvoidPlayer
  extends PathfinderGoal
{
  public final IEntitySelector a = new EntitySelectorViewable(this);
  
  private EntityCreature b;
  
  private double c;
  
  private double d;
  
  private Entity e;
  private float f;
  private PathEntity g;
  private Navigation h;
  private Class i;
  
  public PathfinderGoalAvoidPlayer(EntityCreature paramEntityCreature, Class paramClass, float paramFloat, double paramDouble1, double paramDouble2)
  {
    this.b = paramEntityCreature;
    this.i = paramClass;
    this.f = paramFloat;
    this.c = paramDouble1;
    this.d = paramDouble2;
    this.h = paramEntityCreature.getNavigation();
    a(1);
  }
  
  public boolean a()
  {
    if (this.i == EntityHuman.class) {
      if (((this.b instanceof EntityTameableAnimal)) && (((EntityTameableAnimal)this.b).isTamed())) return false;
      this.e = this.b.world.findNearbyPlayer(this.b, this.f);
      if (this.e == null) return false;
    } else {
      localObject = this.b.world.a(this.i, this.b.boundingBox.grow(this.f, 3.0D, this.f), this.a);
      if (((List)localObject).isEmpty()) return false;
      this.e = ((Entity)((List)localObject).get(0));
    }
    
    Object localObject = RandomPositionGenerator.b(this.b, 16, 7, Vec3D.a(this.e.locX, this.e.locY, this.e.locZ));
    if (localObject == null) return false;
    if (this.e.e(((Vec3D)localObject).a, ((Vec3D)localObject).b, ((Vec3D)localObject).c) < this.e.f(this.b)) return false;
    this.g = this.h.a(((Vec3D)localObject).a, ((Vec3D)localObject).b, ((Vec3D)localObject).c);
    if (this.g == null) return false;
    if (!this.g.b((Vec3D)localObject)) return false;
    return true;
  }
  
  public boolean b()
  {
    return !this.h.g();
  }
  
  public void c()
  {
    this.h.a(this.g, this.c);
  }
  
  public void d()
  {
    this.e = null;
  }
  
  public void e()
  {
    if (this.b.f(this.e) < 49.0D) this.b.getNavigation().a(this.d); else {
      this.b.getNavigation().a(this.c);
    }
  }
}
