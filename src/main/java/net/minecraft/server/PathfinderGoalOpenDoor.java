package net.minecraft.server;

public class PathfinderGoalOpenDoor extends PathfinderGoalDoorInteract
{
  boolean i;
  int j;
  
  public PathfinderGoalOpenDoor(EntityInsentient paramEntityInsentient, boolean paramBoolean)
  {
    super(paramEntityInsentient);
    this.a = paramEntityInsentient;
    this.i = paramBoolean;
  }
  
  public boolean b()
  {
    return (this.i) && (this.j > 0) && (super.b());
  }
  
  public void c()
  {
    this.j = 20;
    this.e.setDoor(this.a.world, this.b, this.c, this.d, true);
  }
  
  public void d()
  {
    if (this.i) {
      this.e.setDoor(this.a.world, this.b, this.c, this.d, false);
    }
  }
  
  public void e()
  {
    this.j -= 1;
    super.e();
  }
}
