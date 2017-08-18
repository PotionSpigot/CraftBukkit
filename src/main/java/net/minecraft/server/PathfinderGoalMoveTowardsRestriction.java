package net.minecraft.server;


public class PathfinderGoalMoveTowardsRestriction
  extends PathfinderGoal
{
  private EntityCreature a;
  
  private double b;
  private double c;
  private double d;
  private double e;
  
  public PathfinderGoalMoveTowardsRestriction(EntityCreature paramEntityCreature, double paramDouble)
  {
    this.a = paramEntityCreature;
    this.e = paramDouble;
    a(1);
  }
  
  public boolean a()
  {
    if (this.a.bU()) return false;
    ChunkCoordinates localChunkCoordinates = this.a.bV();
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.a(localChunkCoordinates.x, localChunkCoordinates.y, localChunkCoordinates.z));
    if (localVec3D == null) return false;
    this.b = localVec3D.a;
    this.c = localVec3D.b;
    this.d = localVec3D.c;
    return true;
  }
  
  public boolean b()
  {
    return !this.a.getNavigation().g();
  }
  
  public void c()
  {
    this.a.getNavigation().a(this.b, this.c, this.d, this.e);
  }
}
