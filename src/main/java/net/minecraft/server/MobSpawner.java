package net.minecraft.server;



class MobSpawner
  extends MobSpawnerAbstract
{
  MobSpawner(TileEntityMobSpawner paramTileEntityMobSpawner) {}
  


  public void a(int paramInt)
  {
    this.a.world.playBlockAction(this.a.x, this.a.y, this.a.z, Blocks.MOB_SPAWNER, paramInt, 0);
  }
  
  public World a()
  {
    return this.a.world;
  }
  
  public int b()
  {
    return this.a.x;
  }
  
  public int c()
  {
    return this.a.y;
  }
  
  public int d()
  {
    return this.a.z;
  }
  
  public void a(TileEntityMobSpawnerData paramTileEntityMobSpawnerData)
  {
    super.a(paramTileEntityMobSpawnerData);
    if (a() != null) a().notify(this.a.x, this.a.y, this.a.z);
  }
}
