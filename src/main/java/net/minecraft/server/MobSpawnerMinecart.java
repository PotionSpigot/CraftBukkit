package net.minecraft.server;




class MobSpawnerMinecart
  extends MobSpawnerAbstract
{
  MobSpawnerMinecart(EntityMinecartMobSpawner paramEntityMinecartMobSpawner) {}
  



  public void a(int paramInt)
  {
    this.a.world.broadcastEntityEffect(this.a, (byte)paramInt);
  }
  
  public World a()
  {
    return this.a.world;
  }
  
  public int b()
  {
    return MathHelper.floor(this.a.locX);
  }
  
  public int c()
  {
    return MathHelper.floor(this.a.locY);
  }
  
  public int d()
  {
    return MathHelper.floor(this.a.locZ);
  }
}
