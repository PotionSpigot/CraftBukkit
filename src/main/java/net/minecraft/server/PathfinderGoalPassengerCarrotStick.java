package net.minecraft.server;

import java.util.Random;









public class PathfinderGoalPassengerCarrotStick
  extends PathfinderGoal
{
  private final EntityInsentient a;
  private final float b;
  private float c;
  private boolean d;
  private int e;
  private int f;
  
  public PathfinderGoalPassengerCarrotStick(EntityInsentient paramEntityInsentient, float paramFloat)
  {
    this.a = paramEntityInsentient;
    this.b = paramFloat;
    a(7);
  }
  
  public void c()
  {
    this.c = 0.0F;
  }
  
  public void d()
  {
    this.d = false;
    this.c = 0.0F;
  }
  
  public boolean a()
  {
    return (this.a.isAlive()) && (this.a.passenger != null) && ((this.a.passenger instanceof EntityHuman)) && ((this.d) || (this.a.bE()));
  }
  
  public void e()
  {
    EntityHuman localEntityHuman = (EntityHuman)this.a.passenger;
    EntityCreature localEntityCreature = (EntityCreature)this.a;
    
    float f1 = MathHelper.g(localEntityHuman.yaw - this.a.yaw) * 0.5F;
    if (f1 > 5.0F) f1 = 5.0F;
    if (f1 < -5.0F) { f1 = -5.0F;
    }
    this.a.yaw = MathHelper.g(this.a.yaw + f1);
    if (this.c < this.b) this.c += (this.b - this.c) * 0.01F;
    if (this.c > this.b) { this.c = this.b;
    }
    int i = MathHelper.floor(this.a.locX);
    int j = MathHelper.floor(this.a.locY);
    int k = MathHelper.floor(this.a.locZ);
    float f2 = this.c;
    if (this.d) {
      if (this.e++ > this.f) {
        this.d = false;
      }
      f2 += f2 * 1.15F * MathHelper.sin(this.e / this.f * 3.1415927F);
    }
    
    float f3 = 0.91F;
    if (this.a.onGround) {
      f3 = this.a.world.getType(MathHelper.d(i), MathHelper.d(j) - 1, MathHelper.d(k)).frictionFactor * 0.91F;
    }
    float f4 = 0.16277136F / (f3 * f3 * f3);
    float f5 = MathHelper.sin(localEntityCreature.yaw * 3.1415927F / 180.0F);
    float f6 = MathHelper.cos(localEntityCreature.yaw * 3.1415927F / 180.0F);
    float f7 = localEntityCreature.bl() * f4;
    float f8 = Math.max(f2, 1.0F);
    f8 = f7 / f8;
    float f9 = f2 * f8;
    float f10 = -(f9 * f5);
    float f11 = f9 * f6;
    
    if (MathHelper.abs(f10) > MathHelper.abs(f11)) {
      if (f10 < 0.0F) f10 -= this.a.width / 2.0F;
      if (f10 > 0.0F) f10 += this.a.width / 2.0F;
      f11 = 0.0F;
    } else {
      f10 = 0.0F;
      if (f11 < 0.0F) f11 -= this.a.width / 2.0F;
      if (f11 > 0.0F) { f11 += this.a.width / 2.0F;
      }
    }
    int m = MathHelper.floor(this.a.locX + f10);
    int n = MathHelper.floor(this.a.locZ + f11);
    
    PathPoint localPathPoint = new PathPoint(MathHelper.d(this.a.width + 1.0F), MathHelper.d(this.a.length + localEntityHuman.length + 1.0F), MathHelper.d(this.a.width + 1.0F));
    Object localObject;
    if ((i != m) || (k != n))
    {

      localObject = this.a.world.getType(i, j, k);
      int i1 = (!a((Block)localObject)) && ((((Block)localObject).getMaterial() != Material.AIR) || (!a(this.a.world.getType(i, j - 1, k)))) ? 1 : 0;
      
      if ((i1 != 0) && (Pathfinder.a(this.a, m, j, n, localPathPoint, false, false, true) == 0) && (Pathfinder.a(this.a, i, j + 1, k, localPathPoint, false, false, true) == 1) && (Pathfinder.a(this.a, m, j + 1, n, localPathPoint, false, false, true) == 1))
      {
        localEntityCreature.getControllerJump().a();
      }
    }
    
    if ((!localEntityHuman.abilities.canInstantlyBuild) && (this.c >= this.b * 0.5F) && (this.a.aI().nextFloat() < 0.006F) && (!this.d)) {
      localObject = localEntityHuman.be();
      
      if ((localObject != null) && (((ItemStack)localObject).getItem() == Items.CARROT_STICK)) {
        ((ItemStack)localObject).damage(1, localEntityHuman);
        
        if (((ItemStack)localObject).count == 0) {
          ItemStack localItemStack = new ItemStack(Items.FISHING_ROD);
          localItemStack.setTag(((ItemStack)localObject).tag);
          localEntityHuman.inventory.items[localEntityHuman.inventory.itemInHandIndex] = localItemStack;
        }
      }
    }
    
    this.a.e(0.0F, f2);
  }
  
  private boolean a(Block paramBlock) {
    return (paramBlock.b() == 10) || ((paramBlock instanceof BlockStepAbstract));
  }
  
  public boolean f() {
    return this.d;
  }
  
  public void g() {
    this.d = true;
    this.e = 0;
    this.f = (this.a.aI().nextInt(841) + 140);
  }
  
  public boolean h() {
    return (!f()) && (this.c > this.b * 0.3F);
  }
}
