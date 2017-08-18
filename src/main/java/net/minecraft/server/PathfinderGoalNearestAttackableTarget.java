package net.minecraft.server;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

public class PathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget
{
  private final Class a;
  private final int b;
  private final DistanceComparator e;
  private final IEntitySelector f;
  private WeakReference<EntityLiving> g = new WeakReference(null);
  
  public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag) {
    this(entitycreature, oclass, i, flag, false);
  }
  
  public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1) {
    this(entitycreature, oclass, i, flag, flag1, (IEntitySelector)null);
  }
  
  public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1, IEntitySelector ientityselector) {
    super(entitycreature, flag, flag1);
    this.a = oclass;
    this.b = i;
    this.e = new DistanceComparator(entitycreature);
    a(1);
    this.f = new EntitySelectorNearestAttackableTarget(this, ientityselector);
  }
  
  public boolean a() {
    if ((this.b > 0) && (this.c.aI().nextInt(this.b) != 0)) {
      return false;
    }
    double d0 = f();
    List list = this.c.world.a(this.a, this.c.boundingBox.grow(d0, 4.0D, d0), this.f);
    
    java.util.Collections.sort(list, this.e);
    if (list.isEmpty()) {
      return false;
    }
    this.g = new WeakReference((EntityLiving)list.get(0));
    return true;
  }
  

  public void c()
  {
    this.c.setGoalTarget((EntityLiving)this.g.get());
    super.c();
  }
}
