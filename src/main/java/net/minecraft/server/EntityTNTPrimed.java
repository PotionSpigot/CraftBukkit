package net.minecraft.server;

import org.bukkit.Location;

public class EntityTNTPrimed extends Entity
{
  public int fuseTicks;
  private EntityLiving source;
  public float yield = 4.0F;
  public boolean isIncendiary = false;
  public Location sourceLoc;
  
  public EntityTNTPrimed(World world)
  {
    this(null, world);
  }
  
  public EntityTNTPrimed(Location loc, World world) {
    super(world);
    this.sourceLoc = loc;
    
    this.k = true;
    a(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
    this.loadChunks = world.paperSpigotConfig.loadUnloadedTNTEntities;
  }
  
  public EntityTNTPrimed(Location loc, World world, double d0, double d1, double d2, EntityLiving entityliving)
  {
    this(loc, world);
    
    setPosition(d0, d1, d2);
    

    this.motX = 0.0D;
    this.motY = 0.20000000298023224D;
    this.motZ = 0.0D;
    this.fuseTicks = 80;
    this.lastX = d0;
    this.lastY = d1;
    this.lastZ = d2;
    this.source = entityliving;
  }
  
  protected void c() {}
  
  protected boolean g_() {
    return false;
  }
  
  public boolean R() {
    return !this.dead;
  }
  
  public void h() {
    if (this.world.spigotConfig.currentPrimedTnt++ > this.world.spigotConfig.maxTntTicksPerTick) return;
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.motY -= 0.03999999910593033D;
    move(this.motX, this.motY, this.motZ);
    
    if ((this.inUnloadedChunk) && (this.world.paperSpigotConfig.removeUnloadedTNTEntities)) {
      die();
      this.fuseTicks = 2;
    }
    
    this.motX *= 0.9800000190734863D;
    this.motY *= 0.9800000190734863D;
    this.motZ *= 0.9800000190734863D;
    if (this.onGround) {
      this.motX *= 0.699999988079071D;
      this.motZ *= 0.699999988079071D;
      this.motY *= -0.5D;
    }
    
    if (this.fuseTicks-- <= 0)
    {
      if (!this.world.isStatic) {
        explode();
      }
      die();
    }
    else {
      this.world.addParticle("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
    }
  }
  


  private void explode()
  {
    ChunkProviderServer chunkProviderServer = (ChunkProviderServer)this.world.chunkProvider;
    boolean forceChunkLoad = chunkProviderServer.forceChunkLoad;
    if (this.world.paperSpigotConfig.loadUnloadedTNTEntities) {
      chunkProviderServer.forceChunkLoad = true;
    }
    
    org.bukkit.craftbukkit.v1_7_R4.CraftServer server = this.world.getServer();
    
    org.bukkit.event.entity.ExplosionPrimeEvent event = new org.bukkit.event.entity.ExplosionPrimeEvent((org.bukkit.entity.Explosive)org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity.getEntity(server, this));
    server.getPluginManager().callEvent(event);
    
    if (!event.isCancelled())
    {
      this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
    }
    

    if (this.world.paperSpigotConfig.loadUnloadedTNTEntities) {
      chunkProviderServer.forceChunkLoad = forceChunkLoad;
    }
  }
  
  protected void b(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setByte("Fuse", (byte)this.fuseTicks);
    
    if (this.sourceLoc != null) {
      nbttagcompound.setInt("SourceLoc_x", this.sourceLoc.getBlockX());
      nbttagcompound.setInt("SourceLoc_y", this.sourceLoc.getBlockY());
      nbttagcompound.setInt("SourceLoc_z", this.sourceLoc.getBlockZ());
    }
  }
  
  protected void a(NBTTagCompound nbttagcompound)
  {
    this.fuseTicks = nbttagcompound.getByte("Fuse");
    
    if (nbttagcompound.hasKey("SourceLoc_x")) {
      int srcX = nbttagcompound.getInt("SourceLoc_x");
      int srcY = nbttagcompound.getInt("SourceLoc_y");
      int srcZ = nbttagcompound.getInt("SourceLoc_z");
      this.sourceLoc = new Location(this.world.getWorld(), srcX, srcY, srcZ);
    }
  }
  
  public EntityLiving getSource()
  {
    return this.source;
  }
}
