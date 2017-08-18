package net.minecraft.server;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.PluginManager;

public abstract class Entity
{
  private static final int CURRENT_LEVEL = 2;
  private static int entityCount;
  private int id;
  public double j;
  public boolean k;
  public Entity passenger;
  public Entity vehicle;
  public boolean attachedToPlayer;
  public World world;
  public double lastX;
  public double lastY;
  public double lastZ;
  public double locX;
  public double locY;
  public double locZ;
  public double motX;
  public double motY;
  public double motZ;
  
  static boolean isLevelAtLeast(NBTTagCompound tag, int level)
  {
    return (tag.hasKey("Bukkit.updateLevel")) && (tag.getInt("Bukkit.updateLevel") >= level);
  }
  
  public void retrack() {
    EntityTracker entityTracker = ((WorldServer)this.world).getTracker();
    entityTracker.untrackEntity(this);
    entityTracker.track(this);
  }
  

  public float yaw;
  public float pitch;
  public float lastYaw;
  public float lastPitch;
  public final AxisAlignedBB boundingBox;
  public boolean onGround;
  public boolean positionChanged;
  public boolean F;
  public boolean G;
  public boolean velocityChanged;
  protected boolean I;
  public boolean J;
  public boolean dead;
  public float height;
  public float width;
  public float length;
  public float O;
  public float P;
  public float Q;
  public float fallDistance;
  private int d;
  public double S;
  public double T;
  public double U;
  public float V;
  public float W;
  public boolean X;
  public float Y;
  public float Z;
  protected Random random;
  public int ticksLived;
  public int maxFireTicks;
  public int fireTicks;
  public boolean inWater;
  public int noDamageTicks;
  private boolean justCreated;
  protected boolean fireProof;
  protected DataWatcher datawatcher;
  private double g;
  private double h;
  public boolean ag;
  public int ah;
  public int ai;
  public int aj;
  public boolean ak;
  public boolean al;
  public int portalCooldown;
  protected boolean an;
  protected int ao;
  public int dimension;
  protected int aq;
  private boolean invulnerable;
  public UUID uniqueID;
  public EnumEntitySize as;
  public boolean valid;
  public org.bukkit.projectiles.ProjectileSource projectileSource;
  public boolean isAddedToChunk()
  {
    return this.ag;
  }
  













  public boolean inUnloadedChunk = false;
  public boolean loadChunks = false;
  

  public org.spigotmc.CustomTimingsHandler tickTimer = org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.getEntityTimings(this);
  public final byte activationType = org.spigotmc.ActivationRange.initializeEntityActivationType(this);
  public final boolean defaultActivationState;
  public long activatedTick = -2147483648L;
  public boolean fromMobSpawner;
  
  public void inactiveTick() {}
  
  public int getId() {
    return this.id;
  }
  
  public void d(int i) {
    this.id = i;
  }
  
  public Entity(World world) {
    this.id = (entityCount++);
    this.j = 1.0D;
    this.boundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    this.J = true;
    this.width = 0.6F;
    this.length = 1.8F;
    this.d = 1;
    this.random = new Random();
    this.maxFireTicks = 1;
    this.justCreated = true;
    this.uniqueID = new UUID(this.random.nextLong(), this.random.nextLong());
    this.as = EnumEntitySize.SIZE_2;
    this.world = world;
    setPosition(0.0D, 0.0D, 0.0D);
    if (world != null) {
      this.dimension = world.worldProvider.dimension;
      
      this.defaultActivationState = org.spigotmc.ActivationRange.initializeEntityActivationState(this, world.spigotConfig);
    } else {
      this.defaultActivationState = false;
    }
    

    this.datawatcher = new DataWatcher(this);
    this.datawatcher.a(0, Byte.valueOf((byte)0));
    this.datawatcher.a(1, Short.valueOf((short)300));
    c();
  }
  
  protected abstract void c();
  
  public DataWatcher getDataWatcher() {
    return this.datawatcher;
  }
  
  public boolean equals(Object object) {
    return ((Entity)object).id == this.id;
  }
  
  public int hashCode() {
    return this.id;
  }
  
  public void die() {
    this.dead = true;
  }
  

  protected void a(float f, float f1)
  {
    if ((f != this.width) || (f1 != this.length)) {
      float f2 = this.width;
      this.width = f;
      this.length = f1;
      this.boundingBox.d = (this.boundingBox.a + this.width);
      this.boundingBox.f = (this.boundingBox.c + this.width);
      this.boundingBox.e = (this.boundingBox.b + this.length);
      if ((this.width > f2) && (!this.justCreated) && (!this.world.isStatic)) {
        move(f2 - this.width, 0.0D, f2 - this.width);
      }
    }
    
    float f2 = f % 2.0F;
    if (f2 < 0.375D) {
      this.as = EnumEntitySize.SIZE_1;
    } else if (f2 < 0.75D) {
      this.as = EnumEntitySize.SIZE_2;
    } else if (f2 < 1.0D) {
      this.as = EnumEntitySize.SIZE_3;
    } else if (f2 < 1.375D) {
      this.as = EnumEntitySize.SIZE_4;
    } else if (f2 < 1.75D) {
      this.as = EnumEntitySize.SIZE_5;
    } else {
      this.as = EnumEntitySize.SIZE_6;
    }
  }
  
  protected void b(float f, float f1)
  {
    if (Float.isNaN(f)) {
      f = 0.0F;
    }
    
    if ((f == Float.POSITIVE_INFINITY) || (f == Float.NEGATIVE_INFINITY)) {
      if ((this instanceof EntityPlayer)) {
        this.world.getServer().getLogger().warning(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
        ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
      }
      f = 0.0F;
    }
    

    if (Float.isNaN(f1)) {
      f1 = 0.0F;
    }
    
    if ((f1 == Float.POSITIVE_INFINITY) || (f1 == Float.NEGATIVE_INFINITY)) {
      if ((this instanceof EntityPlayer)) {
        this.world.getServer().getLogger().warning(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
        ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
      }
      f1 = 0.0F;
    }
    

    this.yaw = (f % 360.0F);
    this.pitch = (f1 % 360.0F);
  }
  
  public void setPosition(double d0, double d1, double d2) {
    this.locX = d0;
    this.locY = d1;
    this.locZ = d2;
    float f = this.width / 2.0F;
    float f1 = this.length;
    
    this.boundingBox.b(d0 - f, d1 - this.height + this.V, d2 - f, d0 + f, d1 - this.height + this.V + f1, d2 + f);
  }
  
  public void h() {
    C();
  }
  
  public void C() {
    this.world.methodProfiler.a("entityBaseTick");
    if ((this.vehicle != null) && (this.vehicle.dead)) {
      this.vehicle = null;
    }
    
    this.O = this.P;
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.lastPitch = this.pitch;
    this.lastYaw = this.yaw;
    

    if ((!this.world.isStatic) && ((this.world instanceof WorldServer))) {
      this.world.methodProfiler.a("portal");
      MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
      
      int i = D();
      if (this.an)
      {
        if ((this.vehicle == null) && (this.ao++ >= i)) {
          this.ao = i;
          this.portalCooldown = ai();
          byte b0;
          byte b0;
          if (this.world.worldProvider.dimension == -1) {
            b0 = 0;
          } else {
            b0 = -1;
          }
          
          b(b0);
        }
        
        this.an = false;
      }
      else {
        if (this.ao > 0) {
          this.ao -= 4;
        }
        
        if (this.ao < 0) {
          this.ao = 0;
        }
      }
      
      if (this.portalCooldown > 0) {
        this.portalCooldown -= 1;
      }
      
      this.world.methodProfiler.b();
    }
    
    if ((isSprinting()) && (!M())) {
      int j = MathHelper.floor(this.locX);
      
      int i = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
      int k = MathHelper.floor(this.locZ);
      Block block = this.world.getType(j, i, k);
      
      if (block.getMaterial() != Material.AIR) {
        this.world.addParticle("blockcrack_" + Block.getId(block) + "_" + this.world.getData(j, i, k), this.locX + (this.random.nextFloat() - 0.5D) * this.width, this.boundingBox.b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, -this.motX * 4.0D, 1.5D, -this.motZ * 4.0D);
      }
    }
    
    N();
    if (this.world.isStatic) {
      this.fireTicks = 0;
    } else if (this.fireTicks > 0) {
      if (this.fireProof) {
        this.fireTicks -= 4;
        if (this.fireTicks < 0) {
          this.fireTicks = 0;
        }
      } else {
        if (this.fireTicks % 20 == 0) {
          damageEntity(DamageSource.BURN, 1.0F);
        }
        
        this.fireTicks -= 1;
      }
    }
    
    if (P()) {
      E();
      this.fallDistance *= 0.5F;
    }
    
    if ((this.locY < -64.0D) || (paperNetherCheck())) {
      G();
    }
    
    if (!this.world.isStatic) {
      a(0, this.fireTicks > 0);
    }
    
    this.justCreated = false;
    this.world.methodProfiler.b();
  }
  
  public int D() {
    return 0;
  }
  
  protected void E() {
    if (!this.fireProof) {
      damageEntity(DamageSource.LAVA, 4.0F);
      

      if ((this instanceof EntityLiving)) {
        if (this.fireTicks <= 0)
        {

          org.bukkit.block.Block damager = null;
          org.bukkit.entity.Entity damagee = getBukkitEntity();
          EntityCombustEvent combustEvent = new org.bukkit.event.entity.EntityCombustByBlockEvent(damager, damagee, 15);
          this.world.getServer().getPluginManager().callEvent(combustEvent);
          
          if (!combustEvent.isCancelled()) {
            setOnFire(combustEvent.getDuration());
          }
        }
        else {
          setOnFire(15);
        }
        return;
      }
      

      setOnFire(15);
    }
  }
  
  public void setOnFire(int i) {
    int j = i * 20;
    
    j = EnchantmentProtection.a(this, j);
    if (this.fireTicks < j) {
      this.fireTicks = j;
    }
  }
  
  public void extinguish() {
    this.fireTicks = 0;
  }
  
  protected void G() {
    die();
  }
  
  public boolean c(double d0, double d1, double d2) {
    AxisAlignedBB axisalignedbb = this.boundingBox.c(d0, d1, d2);
    List list = this.world.getCubes(this, axisalignedbb);
    
    return list.isEmpty();
  }
  


  public void loadChunks()
  {
    for (int cx = (int)this.locX >> 4; cx <= (int)(this.locX + this.motX) >> 4; cx++) {
      for (int cz = (int)this.locZ >> 4; cz <= (int)(this.locZ + this.motZ) >> 4; cz++) {
        this.world.chunkProvider.getChunkAt(cx, cz);
      }
    }
  }
  
  public void move(double d0, double d1, double d2) {
    if (this.loadChunks) { loadChunks();
    }
    try
    {
      I();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
      
      a(crashreportsystemdetails);
      throw new ReportedException(crashreport);
    }
    
    if ((d0 == 0.0D) && (d1 == 0.0D) && (d2 == 0.0D) && (this.vehicle == null) && (this.passenger == null)) {
      return;
    }
    
    org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.entityMoveTimer.startTiming();
    if (this.X) {
      this.boundingBox.d(d0, d1, d2);
      this.locX = ((this.boundingBox.a + this.boundingBox.d) / 2.0D);
      this.locY = (this.boundingBox.b + this.height - this.V);
      this.locZ = ((this.boundingBox.c + this.boundingBox.f) / 2.0D);
    } else {
      this.world.methodProfiler.a("move");
      this.V *= 0.4F;
      double d3 = this.locX;
      double d4 = this.locY;
      double d5 = this.locZ;
      
      if (this.I) {
        this.I = false;
        d0 *= 0.25D;
        d1 *= 0.05000000074505806D;
        d2 *= 0.25D;
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
      }
      
      double d6 = d0;
      double d7 = d1;
      double d8 = d2;
      AxisAlignedBB axisalignedbb = this.boundingBox.clone();
      boolean flag = (this.onGround) && (isSneaking()) && ((this instanceof EntityHuman));
      
      if (flag)
      {

        for (double d9 = 0.05D; (d0 != 0.0D) && (this.world.getCubes(this, this.boundingBox.c(d0, -1.0D, 0.0D)).isEmpty()); d6 = d0) {
          if ((d0 < d9) && (d0 >= -d9)) {
            d0 = 0.0D;
          } else if (d0 > 0.0D) {
            d0 -= d9;
          } else {
            d0 += d9;
          }
        }
        for (; 
            (d2 != 0.0D) && (this.world.getCubes(this, this.boundingBox.c(0.0D, -1.0D, d2)).isEmpty()); d8 = d2) {
          if ((d2 < d9) && (d2 >= -d9)) {
            d2 = 0.0D;
          } else if (d2 > 0.0D) {
            d2 -= d9;
          } else {
            d2 += d9;
          }
        }
        
        while ((d0 != 0.0D) && (d2 != 0.0D) && (this.world.getCubes(this, this.boundingBox.c(d0, -1.0D, d2)).isEmpty())) {
          if ((d0 < d9) && (d0 >= -d9)) {
            d0 = 0.0D;
          } else if (d0 > 0.0D) {
            d0 -= d9;
          } else {
            d0 += d9;
          }
          
          if ((d2 < d9) && (d2 >= -d9)) {
            d2 = 0.0D;
          } else if (d2 > 0.0D) {
            d2 -= d9;
          } else {
            d2 += d9;
          }
          
          d6 = d0;
          d8 = d2;
        }
      }
      
      List list = this.world.getCubes(this, this.boundingBox.a(d0, d1, d2));
      
      for (int i = 0; i < list.size(); i++) {
        d1 = ((AxisAlignedBB)list.get(i)).b(this.boundingBox, d1);
      }
      
      this.boundingBox.d(0.0D, d1, 0.0D);
      if ((!this.J) && (d7 != d1)) {
        d2 = 0.0D;
        d1 = 0.0D;
        d0 = 0.0D;
      }
      
      boolean flag1 = (this.onGround) || ((d7 != d1) && (d7 < 0.0D));
      


      for (int j = 0; j < list.size(); j++) {
        d0 = ((AxisAlignedBB)list.get(j)).a(this.boundingBox, d0);
      }
      
      this.boundingBox.d(d0, 0.0D, 0.0D);
      if ((!this.J) && (d6 != d0)) {
        d2 = 0.0D;
        d1 = 0.0D;
        d0 = 0.0D;
      }
      
      for (j = 0; j < list.size(); j++) {
        d2 = ((AxisAlignedBB)list.get(j)).c(this.boundingBox, d2);
      }
      
      this.boundingBox.d(0.0D, 0.0D, d2);
      if ((!this.J) && (d8 != d2)) {
        d2 = 0.0D;
        d1 = 0.0D;
        d0 = 0.0D;
      }
      





      if ((this.W > 0.0F) && (flag1) && ((flag) || (this.V < 0.05F)) && ((d6 != d0) || (d8 != d2))) {
        double d10 = d0;
        double d11 = d1;
        double d12 = d2;
        d0 = d6;
        d1 = this.W;
        d2 = d8;
        AxisAlignedBB axisalignedbb1 = this.boundingBox.clone();
        
        this.boundingBox.d(axisalignedbb);
        list = this.world.getCubes(this, this.boundingBox.a(d6, d1, d8));
        
        for (int k = 0; k < list.size(); k++) {
          d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
        }
        
        this.boundingBox.d(0.0D, d1, 0.0D);
        if ((!this.J) && (d7 != d1)) {
          d2 = 0.0D;
          d1 = 0.0D;
          d0 = 0.0D;
        }
        
        for (k = 0; k < list.size(); k++) {
          d0 = ((AxisAlignedBB)list.get(k)).a(this.boundingBox, d0);
        }
        
        this.boundingBox.d(d0, 0.0D, 0.0D);
        if ((!this.J) && (d6 != d0)) {
          d2 = 0.0D;
          d1 = 0.0D;
          d0 = 0.0D;
        }
        
        for (k = 0; k < list.size(); k++) {
          d2 = ((AxisAlignedBB)list.get(k)).c(this.boundingBox, d2);
        }
        
        this.boundingBox.d(0.0D, 0.0D, d2);
        if ((!this.J) && (d8 != d2)) {
          d2 = 0.0D;
          d1 = 0.0D;
          d0 = 0.0D;
        }
        
        if ((!this.J) && (d7 != d1)) {
          d2 = 0.0D;
          d1 = 0.0D;
          d0 = 0.0D;
        } else {
          d1 = -this.W;
          
          for (k = 0; k < list.size(); k++) {
            d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
          }
          
          this.boundingBox.d(0.0D, d1, 0.0D);
        }
        
        if (d10 * d10 + d12 * d12 >= d0 * d0 + d2 * d2) {
          d0 = d10;
          d1 = d11;
          d2 = d12;
          this.boundingBox.d(axisalignedbb1);
        }
      }
      
      this.world.methodProfiler.b();
      this.world.methodProfiler.a("rest");
      this.locX = ((this.boundingBox.a + this.boundingBox.d) / 2.0D);
      this.locY = (this.boundingBox.b + this.height - this.V);
      this.locZ = ((this.boundingBox.c + this.boundingBox.f) / 2.0D);
      this.positionChanged = ((d6 != d0) || (d8 != d2));
      this.F = (d7 != d1);
      this.onGround = ((d7 != d1) && (d7 < 0.0D));
      this.G = ((this.positionChanged) || (this.F));
      a(d1, this.onGround);
      if (d6 != d0) {
        this.motX = 0.0D;
      }
      
      if (d7 != d1) {
        this.motY = 0.0D;
      }
      
      if (d8 != d2) {
        this.motZ = 0.0D;
      }
      
      double d10 = this.locX - d3;
      double d11 = this.locY - d4;
      double d12 = this.locZ - d5;
      

      if ((this.positionChanged) && ((getBukkitEntity() instanceof Vehicle))) {
        Vehicle vehicle = (Vehicle)getBukkitEntity();
        org.bukkit.block.Block block = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY - this.height), MathHelper.floor(this.locZ));
        
        if (d6 > d0) {
          block = block.getRelative(org.bukkit.block.BlockFace.EAST);
        } else if (d6 < d0) {
          block = block.getRelative(org.bukkit.block.BlockFace.WEST);
        } else if (d8 > d2) {
          block = block.getRelative(org.bukkit.block.BlockFace.SOUTH);
        } else if (d8 < d2) {
          block = block.getRelative(org.bukkit.block.BlockFace.NORTH);
        }
        
        org.bukkit.event.vehicle.VehicleBlockCollisionEvent event = new org.bukkit.event.vehicle.VehicleBlockCollisionEvent(vehicle, block);
        this.world.getServer().getPluginManager().callEvent(event);
      }
      

      if ((g_()) && (!flag) && (this.vehicle == null)) {
        int l = MathHelper.floor(this.locX);
        
        int k = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
        int i1 = MathHelper.floor(this.locZ);
        Block block = this.world.getType(l, k, i1);
        int j1 = this.world.getType(l, k - 1, i1).b();
        
        if ((j1 == 11) || (j1 == 32) || (j1 == 21)) {
          block = this.world.getType(l, k - 1, i1);
        }
        
        if (block != Blocks.LADDER) {
          d11 = 0.0D;
        }
        
        this.P = ((float)(this.P + MathHelper.sqrt(d10 * d10 + d12 * d12) * 0.6D));
        this.Q = ((float)(this.Q + MathHelper.sqrt(d10 * d10 + d11 * d11 + d12 * d12) * 0.6D));
        if ((this.Q > this.d) && (block.getMaterial() != Material.AIR)) {
          this.d = ((int)this.Q + 1);
          if (M()) {
            float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;
            
            if (f > 1.0F) {
              f = 1.0F;
            }
            
            makeSound(H(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
          }
          
          a(l, k, i1, block);
          block.b(this.world, l, k, i1, this);
        }
      }
      













      boolean flag2 = L();
      
      if (this.world.e(this.boundingBox.shrink(0.001D, 0.001D, 0.001D))) {
        burn(1.0F);
        if (!flag2) {
          this.fireTicks += 1;
          
          if (this.fireTicks <= 0) {
            EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity(), 8);
            this.world.getServer().getPluginManager().callEvent(event);
            
            if (!event.isCancelled()) {
              setOnFire(event.getDuration());
            }
          }
          else {
            setOnFire(8);
          }
        }
      } else if (this.fireTicks <= 0) {
        this.fireTicks = (-this.maxFireTicks);
      }
      
      if ((flag2) && (this.fireTicks > 0)) {
        makeSound("random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        this.fireTicks = (-this.maxFireTicks);
      }
      
      this.world.methodProfiler.b();
    }
    org.bukkit.craftbukkit.v1_7_R4.SpigotTimings.entityMoveTimer.stopTiming();
  }
  
  protected String H() {
    return "game.neutral.swim";
  }
  
  protected void I() {
    int i = MathHelper.floor(this.boundingBox.a + 0.001D);
    int j = MathHelper.floor(this.boundingBox.b + 0.001D);
    int k = MathHelper.floor(this.boundingBox.c + 0.001D);
    int l = MathHelper.floor(this.boundingBox.d - 0.001D);
    int i1 = MathHelper.floor(this.boundingBox.e - 0.001D);
    int j1 = MathHelper.floor(this.boundingBox.f - 0.001D);
    
    if (this.world.b(i, j, k, l, i1, j1)) {
      for (int k1 = i; k1 <= l; k1++) {
        for (int l1 = j; l1 <= i1; l1++) {
          for (int i2 = k; i2 <= j1; i2++) {
            Block block = this.world.getType(k1, l1, i2);
            try
            {
              block.a(this.world, k1, l1, i2, this);
            } catch (Throwable throwable) {
              CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
              CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");
              
              CrashReportSystemDetails.a(crashreportsystemdetails, k1, l1, i2, block, this.world.getData(k1, l1, i2));
              throw new ReportedException(crashreport);
            }
          }
        }
      }
    }
  }
  
  protected void a(int i, int j, int k, Block block) {
    StepSound stepsound = block.stepSound;
    
    if (this.world.getType(i, j + 1, k) == Blocks.SNOW) {
      stepsound = Blocks.SNOW.stepSound;
      makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
    } else if (!block.getMaterial().isLiquid()) {
      makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
    }
  }
  
  public void makeSound(String s, float f, float f1) {
    this.world.makeSound(this, s, f, f1);
  }
  
  protected boolean g_() {
    return true;
  }
  
  protected void a(double d0, boolean flag) {
    if (flag) {
      if (this.fallDistance > 0.0F) {
        b(this.fallDistance);
        this.fallDistance = 0.0F;
      }
    } else if (d0 < 0.0D) {
      this.fallDistance = ((float)(this.fallDistance - d0));
    }
  }
  
  public AxisAlignedBB J() {
    return null;
  }
  
  protected void burn(float i) {
    if (!this.fireProof) {
      damageEntity(DamageSource.FIRE, i);
    }
  }
  
  public final boolean isFireproof() {
    return this.fireProof;
  }
  
  protected void b(float f) {
    if (this.passenger != null) {
      this.passenger.b(f);
    }
  }
  
  public boolean L() {
    return (this.inWater) || (this.world.isRainingAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))) || (this.world.isRainingAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY + this.length), MathHelper.floor(this.locZ)));
  }
  
  public boolean M() {
    return this.inWater;
  }
  
  public boolean N() {
    if (this.world.a(this.boundingBox.grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D, 0.001D, 0.001D), Material.WATER, this)) {
      if ((!this.inWater) && (!this.justCreated)) {
        float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;
        
        if (f > 1.0F) {
          f = 1.0F;
        }
        
        makeSound(O(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        float f1 = MathHelper.floor(this.boundingBox.b);
        




        for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
          float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
          float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
          this.world.addParticle("bubble", this.locX + f2, f1 + 1.0F, this.locZ + f3, this.motX, this.motY - this.random.nextFloat() * 0.2F, this.motZ);
        }
        
        for (i = 0; i < 1.0F + this.width * 20.0F; i++) {
          float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
          float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
          this.world.addParticle("splash", this.locX + f2, f1 + 1.0F, this.locZ + f3, this.motX, this.motY, this.motZ);
        }
      }
      
      this.fallDistance = 0.0F;
      this.inWater = true;
      this.fireTicks = 0;
    } else {
      this.inWater = false;
    }
    
    return this.inWater;
  }
  
  protected String O() {
    return "game.neutral.swim.splash";
  }
  
  public boolean a(Material material) {
    double d0 = this.locY + getHeadHeight();
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.d(MathHelper.floor(d0));
    int k = MathHelper.floor(this.locZ);
    Block block = this.world.getType(i, j, k);
    
    if (block.getMaterial() == material) {
      float f = BlockFluids.b(this.world.getData(i, j, k)) - 0.11111111F;
      float f1 = j + 1 - f;
      
      return d0 < f1;
    }
    return false;
  }
  
  public float getHeadHeight()
  {
    return 0.0F;
  }
  
  public boolean P() {
    return this.world.a(this.boundingBox.grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
  }
  
  public void a(float f, float f1, float f2) {
    float f3 = f * f + f1 * f1;
    
    if (f3 >= 1.0E-4F) {
      f3 = MathHelper.c(f3);
      if (f3 < 1.0F) {
        f3 = 1.0F;
      }
      
      f3 = f2 / f3;
      f *= f3;
      f1 *= f3;
      float f4 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
      float f5 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
      
      this.motX += f * f5 - f1 * f4;
      this.motZ += f1 * f5 + f * f4;
    }
  }
  
  public float d(float f) {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locZ);
    
    if (this.world.isLoaded(i, 0, j)) {
      double d0 = (this.boundingBox.e - this.boundingBox.b) * 0.66D;
      int k = MathHelper.floor(this.locY - this.height + d0);
      
      return this.world.n(i, k, j);
    }
    return 0.0F;
  }
  

  public void spawnIn(World world)
  {
    if (world == null) {
      die();
      this.world = ((CraftWorld)org.bukkit.Bukkit.getServer().getWorlds().get(0)).getHandle();
      return;
    }
    

    this.world = world;
  }
  
  public void setLocation(double d0, double d1, double d2, float f, float f1) {
    this.lastX = (this.locX = d0);
    this.lastY = (this.locY = d1);
    this.lastZ = (this.locZ = d2);
    this.lastYaw = (this.yaw = f);
    this.lastPitch = (this.pitch = f1);
    this.V = 0.0F;
    double d3 = this.lastYaw - f;
    
    if (d3 < -180.0D) {
      this.lastYaw += 360.0F;
    }
    
    if (d3 >= 180.0D) {
      this.lastYaw -= 360.0F;
    }
    
    setPosition(this.locX, this.locY, this.locZ);
    b(f, f1);
  }
  
  public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
    this.S = (this.lastX = this.locX = d0);
    this.T = (this.lastY = this.locY = d1 + this.height);
    this.U = (this.lastZ = this.locZ = d2);
    this.yaw = f;
    this.pitch = f1;
    setPosition(this.locX, this.locY, this.locZ);
  }
  
  public float e(Entity entity) {
    float f = (float)(this.locX - entity.locX);
    float f1 = (float)(this.locY - entity.locY);
    float f2 = (float)(this.locZ - entity.locZ);
    
    return MathHelper.c(f * f + f1 * f1 + f2 * f2);
  }
  
  public double e(double d0, double d1, double d2) {
    double d3 = this.locX - d0;
    double d4 = this.locY - d1;
    double d5 = this.locZ - d2;
    
    return d3 * d3 + d4 * d4 + d5 * d5;
  }
  
  public double f(double d0, double d1, double d2) {
    double d3 = this.locX - d0;
    double d4 = this.locY - d1;
    double d5 = this.locZ - d2;
    
    return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
  }
  
  public double f(Entity entity) {
    double d0 = this.locX - entity.locX;
    double d1 = this.locY - entity.locY;
    double d2 = this.locZ - entity.locZ;
    
    return d0 * d0 + d1 * d1 + d2 * d2;
  }
  
  public void b_(EntityHuman entityhuman) {}
  
  int numCollisions = 0;
  
  public void collide(Entity entity) { if ((entity.passenger != this) && (entity.vehicle != this)) {
      double d0 = entity.locX - this.locX;
      double d1 = entity.locZ - this.locZ;
      double d2 = MathHelper.a(d0, d1);
      
      if (d2 >= 0.009999999776482582D) {
        d2 = MathHelper.sqrt(d2);
        d0 /= d2;
        d1 /= d2;
        double d3 = 1.0D / d2;
        
        if (d3 > 1.0D) {
          d3 = 1.0D;
        }
        
        d0 *= d3;
        d1 *= d3;
        d0 *= 0.05000000074505806D;
        d1 *= 0.05000000074505806D;
        d0 *= (1.0F - this.Y);
        d1 *= (1.0F - this.Y);
        g(-d0, 0.0D, -d1);
        entity.g(d0, 0.0D, d1);
      }
    }
  }
  
  public void g(double d0, double d1, double d2) {
    this.motX += d0;
    this.motY += d1;
    this.motZ += d2;
    this.al = true;
  }
  
  protected void Q() {
    this.velocityChanged = true;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable()) {
      return false;
    }
    Q();
    return false;
  }
  
  public boolean R()
  {
    return false;
  }
  
  public boolean S() {
    return false;
  }
  
  public void b(Entity entity, int i) {}
  
  public boolean c(NBTTagCompound nbttagcompound) {
    String s = W();
    
    if ((!this.dead) && (s != null)) {
      nbttagcompound.setString("id", s);
      e(nbttagcompound);
      return true;
    }
    return false;
  }
  
  public boolean d(NBTTagCompound nbttagcompound)
  {
    String s = W();
    
    if ((!this.dead) && (s != null) && (this.passenger == null)) {
      nbttagcompound.setString("id", s);
      e(nbttagcompound);
      return true;
    }
    return false;
  }
  
  public void e(NBTTagCompound nbttagcompound)
  {
    try {
      nbttagcompound.set("Pos", a(new double[] { this.locX, this.locY + this.V, this.locZ }));
      nbttagcompound.set("Motion", a(new double[] { this.motX, this.motY, this.motZ }));
      


      if (Float.isNaN(this.yaw)) {
        this.yaw = 0.0F;
      }
      
      if (Float.isNaN(this.pitch)) {
        this.pitch = 0.0F;
      }
      

      nbttagcompound.set("Rotation", a(new float[] { this.yaw, this.pitch }));
      nbttagcompound.setFloat("FallDistance", this.fallDistance);
      nbttagcompound.setShort("Fire", (short)this.fireTicks);
      nbttagcompound.setShort("Air", (short)getAirTicks());
      nbttagcompound.setBoolean("OnGround", this.onGround);
      nbttagcompound.setInt("Dimension", this.dimension);
      nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
      nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
      nbttagcompound.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
      nbttagcompound.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
      
      nbttagcompound.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
      nbttagcompound.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
      nbttagcompound.setInt("Bukkit.updateLevel", 2);
      nbttagcompound.setInt("Spigot.ticksLived", this.ticksLived);
      
      b(nbttagcompound);
      if (this.vehicle != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
        if (this.vehicle.c(nbttagcompound1)) {
          nbttagcompound.set("Riding", nbttagcompound1);
        }
      }
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
      
      a(crashreportsystemdetails);
      throw new ReportedException(crashreport);
    }
  }
  
  public void f(NBTTagCompound nbttagcompound) {
    try {
      NBTTagList nbttaglist = nbttagcompound.getList("Pos", 6);
      NBTTagList nbttaglist1 = nbttagcompound.getList("Motion", 6);
      NBTTagList nbttaglist2 = nbttagcompound.getList("Rotation", 5);
      
      this.motX = nbttaglist1.d(0);
      this.motY = nbttaglist1.d(1);
      this.motZ = nbttaglist1.d(2);
      













      this.lastX = (this.S = this.locX = nbttaglist.d(0));
      this.lastY = (this.T = this.locY = nbttaglist.d(1));
      this.lastZ = (this.U = this.locZ = nbttaglist.d(2));
      this.lastYaw = (this.yaw = nbttaglist2.e(0));
      this.lastPitch = (this.pitch = nbttaglist2.e(1));
      this.fallDistance = nbttagcompound.getFloat("FallDistance");
      this.fireTicks = nbttagcompound.getShort("Fire");
      setAirTicks(nbttagcompound.getShort("Air"));
      this.onGround = nbttagcompound.getBoolean("OnGround");
      this.dimension = nbttagcompound.getInt("Dimension");
      this.invulnerable = nbttagcompound.getBoolean("Invulnerable");
      this.portalCooldown = nbttagcompound.getInt("PortalCooldown");
      if ((nbttagcompound.hasKeyOfType("UUIDMost", 4)) && (nbttagcompound.hasKeyOfType("UUIDLeast", 4))) {
        this.uniqueID = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
      }
      
      setPosition(this.locX, this.locY, this.locZ);
      b(this.yaw, this.pitch);
      a(nbttagcompound);
      if (V()) {
        setPosition(this.locX, this.locY, this.locZ);
      }
      

      if ((this instanceof EntityLiving)) {
        EntityLiving entity = (EntityLiving)this;
        
        this.ticksLived = nbttagcompound.getInt("Spigot.ticksLived");
        

        if (((entity instanceof EntityTameableAnimal)) && (!isLevelAtLeast(nbttagcompound, 2)) && (!nbttagcompound.getBoolean("PersistenceRequired"))) {
          EntityInsentient entityinsentient = (EntityInsentient)entity;
          entityinsentient.persistent = (!entityinsentient.isTypeNotPersistent());
        }
      }
      


      if (!(getBukkitEntity() instanceof Vehicle)) {
        if (Math.abs(this.motX) > 10.0D) {
          this.motX = 0.0D;
        }
        
        if (Math.abs(this.motY) > 10.0D) {
          this.motY = 0.0D;
        }
        
        if (Math.abs(this.motZ) > 10.0D) {
          this.motZ = 0.0D;
        }
      }
      


      if ((this instanceof EntityPlayer)) {
        org.bukkit.Server server = org.bukkit.Bukkit.getServer();
        org.bukkit.World bworld = null;
        

        String worldName = nbttagcompound.getString("World");
        
        if ((nbttagcompound.hasKey("WorldUUIDMost")) && (nbttagcompound.hasKey("WorldUUIDLeast"))) {
          UUID uid = new UUID(nbttagcompound.getLong("WorldUUIDMost"), nbttagcompound.getLong("WorldUUIDLeast"));
          bworld = server.getWorld(uid);
        } else {
          bworld = server.getWorld(worldName);
        }
        
        if (bworld == null) {
          EntityPlayer entityPlayer = (EntityPlayer)this;
          bworld = ((CraftServer)server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
        }
        
        spawnIn(bworld == null ? null : ((CraftWorld)bworld).getHandle());
      }
    }
    catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
      
      a(crashreportsystemdetails);
      throw new ReportedException(crashreport);
    }
  }
  
  protected boolean V() {
    return true;
  }
  
  protected final String W() {
    return EntityTypes.b(this);
  }
  
  protected abstract void a(NBTTagCompound paramNBTTagCompound);
  
  protected abstract void b(NBTTagCompound paramNBTTagCompound);
  
  public void X() {}
  
  protected NBTTagList a(double... adouble) {
    NBTTagList nbttaglist = new NBTTagList();
    double[] adouble1 = adouble;
    int i = adouble.length;
    
    for (int j = 0; j < i; j++) {
      double d0 = adouble1[j];
      
      nbttaglist.add(new NBTTagDouble(d0));
    }
    
    return nbttaglist;
  }
  
  protected NBTTagList a(float... afloat) {
    NBTTagList nbttaglist = new NBTTagList();
    float[] afloat1 = afloat;
    int i = afloat.length;
    
    for (int j = 0; j < i; j++) {
      float f = afloat1[j];
      
      nbttaglist.add(new NBTTagFloat(f));
    }
    
    return nbttaglist;
  }
  
  public EntityItem a(Item item, int i) {
    return a(item, i, 0.0F);
  }
  
  public EntityItem a(Item item, int i, float f) {
    return a(new ItemStack(item, i, 0), f);
  }
  
  public EntityItem a(ItemStack itemstack, float f) {
    if ((itemstack.count != 0) && (itemstack.getItem() != null))
    {
      if (((this instanceof EntityLiving)) && (((EntityLiving)this).drops != null)) {
        ((EntityLiving)this).drops.add(org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack.asBukkitCopy(itemstack));
        return null;
      }
      

      EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + f, this.locZ, itemstack);
      
      entityitem.pickupDelay = 10;
      this.world.addEntity(entityitem);
      return entityitem;
    }
    return null;
  }
  
  public boolean isAlive()
  {
    return !this.dead;
  }
  
  public boolean inBlock() {
    for (int i = 0; i < 8; i++) {
      float f = ((i >> 0) % 2 - 0.5F) * this.width * 0.8F;
      float f1 = ((i >> 1) % 2 - 0.5F) * 0.1F;
      float f2 = ((i >> 2) % 2 - 0.5F) * this.width * 0.8F;
      int j = MathHelper.floor(this.locX + f);
      int k = MathHelper.floor(this.locY + getHeadHeight() + f1);
      int l = MathHelper.floor(this.locZ + f2);
      
      if (this.world.getType(j, k, l).r()) {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean c(EntityHuman entityhuman) {
    return false;
  }
  
  public AxisAlignedBB h(Entity entity) {
    return null;
  }
  
  public void ab() {
    if (this.vehicle.dead) {
      this.vehicle = null;
    } else {
      this.motX = 0.0D;
      this.motY = 0.0D;
      this.motZ = 0.0D;
      h();
      if (this.vehicle != null) {
        this.vehicle.ac();
        this.h += this.vehicle.yaw - this.vehicle.lastYaw;
        
        for (this.g += this.vehicle.pitch - this.vehicle.lastPitch; this.h >= 180.0D; this.h -= 360.0D) {}
        


        while (this.h < -180.0D) {
          this.h += 360.0D;
        }
        
        while (this.g >= 180.0D) {
          this.g -= 360.0D;
        }
        
        while (this.g < -180.0D) {
          this.g += 360.0D;
        }
        
        double d0 = this.h * 0.5D;
        double d1 = this.g * 0.5D;
        float f = 10.0F;
        
        if (d0 > f) {
          d0 = f;
        }
        
        if (d0 < -f) {
          d0 = -f;
        }
        
        if (d1 > f) {
          d1 = f;
        }
        
        if (d1 < -f) {
          d1 = -f;
        }
        
        this.h -= d0;
        this.g -= d1;
      }
    }
  }
  
  public void ac() {
    if (this.passenger != null) {
      this.passenger.setPosition(this.locX, this.locY + ae() + this.passenger.ad(), this.locZ);
    }
  }
  
  public double ad() {
    return this.height;
  }
  
  public double ae() {
    return this.length * 0.75D;
  }
  
  public void mount(Entity entity)
  {
    setPassengerOf(entity);
  }
  

  public CraftEntity getBukkitEntity()
  {
    if (this.bukkitEntity == null) {
      this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
    }
    return this.bukkitEntity;
  }
  

  protected CraftEntity bukkitEntity;
  public void setPassengerOf(Entity entity)
  {
    Entity originalVehicle = this.vehicle;
    Entity originalPassenger = this.vehicle == null ? null : this.vehicle.passenger;
    PluginManager pluginManager = org.bukkit.Bukkit.getPluginManager();
    getBukkitEntity();
    
    this.g = 0.0D;
    this.h = 0.0D;
    if (entity == null) {
      if (this.vehicle != null)
      {
        Location exit = new Location(this.world.getWorld(), this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
        if (((this.bukkitEntity instanceof org.bukkit.entity.LivingEntity)) && ((this.vehicle.getBukkitEntity() instanceof Vehicle))) {
          VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (org.bukkit.entity.LivingEntity)this.bukkitEntity, exit);
          pluginManager.callEvent(event);
          
          if ((event.isCancelled()) || (this.vehicle != originalVehicle)) {
            return;
          }
        }
        
        pluginManager.callEvent(new org.spigotmc.event.entity.EntityDismountEvent(getBukkitEntity(), this.vehicle.getBukkitEntity()));
        
        setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
        this.vehicle.passenger = null;
      }
      
      this.vehicle = null;
    }
    else {
      if (((this.bukkitEntity instanceof org.bukkit.entity.LivingEntity)) && ((entity.getBukkitEntity() instanceof Vehicle)) && (entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4)))
      {
        VehicleExitEvent exitEvent = null;
        if ((this.vehicle != null) && ((this.vehicle.getBukkitEntity() instanceof Vehicle))) {
          Location exit = new Location(this.world.getWorld(), this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
          exitEvent = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (org.bukkit.entity.LivingEntity)this.bukkitEntity, exit);
          pluginManager.callEvent(exitEvent);
          
          if ((exitEvent.isCancelled()) || (this.vehicle != originalVehicle) || ((this.vehicle != null) && (this.vehicle.passenger != originalPassenger))) {
            return;
          }
        }
        
        org.bukkit.event.vehicle.VehicleEnterEvent event = new org.bukkit.event.vehicle.VehicleEnterEvent((Vehicle)entity.getBukkitEntity(), this.bukkitEntity);
        pluginManager.callEvent(event);
        

        if ((event.isCancelled()) || (this.vehicle != originalVehicle) || ((this.vehicle != null) && (this.vehicle.passenger != originalPassenger)))
        {
          if ((exitEvent != null) && (this.vehicle == originalVehicle) && (this.vehicle != null) && (this.vehicle.passenger == originalPassenger)) {
            Location exit = exitEvent.getExitLocation();
            setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
            this.vehicle.passenger = null;
            this.vehicle = null;
          }
          return;
        }
      }
      

      if (entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4))
      {
        org.spigotmc.event.entity.EntityMountEvent event = new org.spigotmc.event.entity.EntityMountEvent(getBukkitEntity(), entity.getBukkitEntity());
        pluginManager.callEvent(event);
        if (event.isCancelled())
        {
          return;
        }
      }
      

      if (this.vehicle != null) {
        this.vehicle.passenger = null;
      }
      
      if (entity != null) {
        for (Entity entity1 = entity.vehicle; entity1 != null; entity1 = entity1.vehicle) {
          if (entity1 == this) {
            return;
          }
        }
      }
      
      this.vehicle = entity;
      entity.passenger = this;
    }
  }
  
  public float af() {
    return 0.1F;
  }
  
  public Vec3D ag() {
    return null;
  }
  
  public void ah() {
    if (this.portalCooldown > 0) {
      this.portalCooldown = ai();
    } else {
      double d0 = this.lastX - this.locX;
      double d1 = this.lastZ - this.locZ;
      
      if ((!this.world.isStatic) && (!this.an)) {
        this.aq = Direction.a(d0, d1);
      }
      
      this.an = true;
    }
  }
  
  public int ai() {
    return 300;
  }
  
  public ItemStack[] getEquipment() {
    return null;
  }
  
  public void setEquipment(int i, ItemStack itemstack) {}
  
  public boolean isBurning() {
    boolean flag = (this.world != null) && (this.world.isStatic);
    
    return (!this.fireProof) && ((this.fireTicks > 0) || ((flag) && (g(0))));
  }
  
  public boolean am() {
    return this.vehicle != null;
  }
  
  public boolean isSneaking() {
    return g(1);
  }
  
  public void setSneaking(boolean flag) {
    a(1, flag);
  }
  
  public boolean isSprinting() {
    return g(3);
  }
  
  public void setSprinting(boolean flag) {
    a(3, flag);
  }
  
  public boolean isInvisible() {
    return g(5);
  }
  
  public void setInvisible(boolean flag) {
    a(5, flag);
  }
  
  public void e(boolean flag) {
    a(4, flag);
  }
  
  protected boolean g(int i) {
    return (this.datawatcher.getByte(0) & 1 << i) != 0;
  }
  
  protected void a(int i, boolean flag) {
    byte b0 = this.datawatcher.getByte(0);
    
    if (flag) {
      this.datawatcher.watch(0, Byte.valueOf((byte)(b0 | 1 << i)));
    } else {
      this.datawatcher.watch(0, Byte.valueOf((byte)(b0 & (1 << i ^ 0xFFFFFFFF))));
    }
  }
  
  public int getAirTicks() {
    return this.datawatcher.getShort(1);
  }
  
  public void setAirTicks(int i) {
    this.datawatcher.watch(1, Short.valueOf((short)i));
  }
  
  public void a(EntityLightning entitylightning)
  {
    org.bukkit.entity.Entity thisBukkitEntity = getBukkitEntity();
    org.bukkit.entity.Entity stormBukkitEntity = entitylightning.getBukkitEntity();
    PluginManager pluginManager = org.bukkit.Bukkit.getPluginManager();
    
    if ((thisBukkitEntity instanceof org.bukkit.entity.Hanging)) {
      org.bukkit.event.hanging.HangingBreakByEntityEvent hangingEvent = new org.bukkit.event.hanging.HangingBreakByEntityEvent((org.bukkit.entity.Hanging)thisBukkitEntity, stormBukkitEntity);
      org.bukkit.event.painting.PaintingBreakByEntityEvent paintingEvent = null;
      
      if ((thisBukkitEntity instanceof org.bukkit.entity.Painting)) {
        paintingEvent = new org.bukkit.event.painting.PaintingBreakByEntityEvent((org.bukkit.entity.Painting)thisBukkitEntity, stormBukkitEntity);
      }
      
      pluginManager.callEvent(hangingEvent);
      
      if (paintingEvent != null) {
        paintingEvent.setCancelled(hangingEvent.isCancelled());
        pluginManager.callEvent(paintingEvent);
      }
      
      if ((hangingEvent.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
        return;
      }
    }
    
    if (this.fireProof) {
      return;
    }
    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = entitylightning;
    if (!damageEntity(DamageSource.FIRE, 5.0F)) {
      org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = null;
      return;
    }
    

    this.fireTicks += 1;
    if (this.fireTicks == 0)
    {
      org.bukkit.event.entity.EntityCombustByEntityEvent entityCombustEvent = new org.bukkit.event.entity.EntityCombustByEntityEvent(stormBukkitEntity, thisBukkitEntity, 8);
      pluginManager.callEvent(entityCombustEvent);
      if (!entityCombustEvent.isCancelled()) {
        setOnFire(entityCombustEvent.getDuration());
      }
    }
  }
  
  public void a(EntityLiving entityliving) {}
  
  protected boolean j(double d0, double d1, double d2)
  {
    int i = MathHelper.floor(d0);
    int j = MathHelper.floor(d1);
    int k = MathHelper.floor(d2);
    double d3 = d0 - i;
    double d4 = d1 - j;
    double d5 = d2 - k;
    List list = this.world.a(this.boundingBox);
    
    if ((list.isEmpty()) && (!this.world.q(i, j, k))) {
      return false;
    }
    boolean flag = !this.world.q(i - 1, j, k);
    boolean flag1 = !this.world.q(i + 1, j, k);
    boolean flag2 = !this.world.q(i, j - 1, k);
    boolean flag3 = !this.world.q(i, j + 1, k);
    boolean flag4 = !this.world.q(i, j, k - 1);
    boolean flag5 = !this.world.q(i, j, k + 1);
    byte b0 = 3;
    double d6 = 9999.0D;
    
    if ((flag) && (d3 < d6)) {
      d6 = d3;
      b0 = 0;
    }
    
    if ((flag1) && (1.0D - d3 < d6)) {
      d6 = 1.0D - d3;
      b0 = 1;
    }
    
    if ((flag3) && (1.0D - d4 < d6)) {
      d6 = 1.0D - d4;
      b0 = 3;
    }
    
    if ((flag4) && (d5 < d6)) {
      d6 = d5;
      b0 = 4;
    }
    
    if ((flag5) && (1.0D - d5 < d6)) {
      d6 = 1.0D - d5;
      b0 = 5;
    }
    
    float f = this.random.nextFloat() * 0.2F + 0.1F;
    
    if (b0 == 0) {
      this.motX = (-f);
    }
    
    if (b0 == 1) {
      this.motX = f;
    }
    
    if (b0 == 2) {
      this.motY = (-f);
    }
    
    if (b0 == 3) {
      this.motY = f;
    }
    
    if (b0 == 4) {
      this.motZ = (-f);
    }
    
    if (b0 == 5) {
      this.motZ = f;
    }
    
    return true;
  }
  
  public void as()
  {
    this.I = true;
    this.fallDistance = 0.0F;
  }
  
  public String getName() {
    String s = EntityTypes.b(this);
    
    if (s == null) {
      s = "generic";
    }
    
    return LocaleI18n.get("entity." + s + ".name");
  }
  
  public Entity[] at() {
    return null;
  }
  
  public boolean i(Entity entity) {
    return this == entity;
  }
  
  public float getHeadRotation() {
    return 0.0F;
  }
  
  public boolean av() {
    return true;
  }
  
  public boolean j(Entity entity) {
    return false;
  }
  
  public String toString() {
    return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.id), this.world == null ? "~NULL~" : this.world.getWorldData().getName(), Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) });
  }
  
  public boolean isInvulnerable() {
    return this.invulnerable;
  }
  
  public void k(Entity entity) {
    setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
  }
  
  public void a(Entity entity, boolean flag) {
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    
    entity.e(nbttagcompound);
    f(nbttagcompound);
    this.portalCooldown = entity.portalCooldown;
    this.aq = entity.aq;
  }
  
  public void b(int i) {
    if ((!this.world.isStatic) && (!this.dead)) {
      this.world.methodProfiler.a("changeDimension");
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      

      WorldServer exitWorld = null;
      if (this.dimension < 10)
      {
        for (WorldServer world : minecraftserver.worlds) {
          if (world.dimension == i) {
            exitWorld = world;
          }
        }
      }
      
      Location enter = getBukkitEntity().getLocation();
      Location exit = exitWorld != null ? minecraftserver.getPlayerList().calculateTarget(enter, minecraftserver.getWorldServer(i)) : null;
      boolean useTravelAgent = (exitWorld != null) && ((this.dimension != 1) || (exitWorld.dimension != 1));
      
      org.bukkit.TravelAgent agent = exit != null ? (org.bukkit.TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : org.bukkit.craftbukkit.v1_7_R4.CraftTravelAgent.DEFAULT;
      EntityPortalEvent event = new EntityPortalEvent(getBukkitEntity(), enter, exit, agent);
      event.useTravelAgent(useTravelAgent);
      event.getEntity().getServer().getPluginManager().callEvent(event);
      if ((event.isCancelled()) || (event.getTo() == null) || (event.getTo().getWorld() == null) || (!isAlive())) {
        return;
      }
      exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
      teleportTo(exit, true);
    }
  }
  
  public void teleportTo(Location exit, boolean portal)
  {
    WorldServer worldserver = ((CraftWorld)getBukkitEntity().getLocation().getWorld()).getHandle();
    WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
    int i = worldserver1.dimension;
    

    this.dimension = i;
    






    this.world.kill(this);
    this.dead = false;
    this.world.methodProfiler.a("reposition");
    

    boolean before = worldserver1.chunkProviderServer.forceChunkLoad;
    worldserver1.chunkProviderServer.forceChunkLoad = true;
    
    worldserver1.chunkProviderServer.forceChunkLoad = before;
    
    this.world.methodProfiler.c("reloading");
    Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);
    
    if (entity != null) {
      entity.a(this, true);
      
      exit.getBlock();
      entity.setLocation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
      








      worldserver1.addEntity(entity);
      
      getBukkitEntity().setHandle(entity);
      entity.bukkitEntity = getBukkitEntity();
    }
    

    this.dead = true;
    this.world.methodProfiler.b();
    worldserver.i();
    worldserver1.i();
    this.world.methodProfiler.b();
  }
  
  public float a(Explosion explosion, World world, int i, int j, int k, Block block)
  {
    return block.a(this);
  }
  
  public boolean a(Explosion explosion, World world, int i, int j, int k, Block block, float f) {
    return true;
  }
  
  public int ax() {
    return 3;
  }
  
  public int ay() {
    return this.aq;
  }
  
  public boolean az() {
    return false;
  }
  
  public void a(CrashReportSystemDetails crashreportsystemdetails) {
    crashreportsystemdetails.a("Entity Type", new CrashReportEntityType(this));
    crashreportsystemdetails.a("Entity ID", Integer.valueOf(this.id));
    crashreportsystemdetails.a("Entity Name", new CrashReportEntityName(this));
    crashreportsystemdetails.a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) }));
    crashreportsystemdetails.a("Entity's Block location", CrashReportSystemDetails.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)));
    crashreportsystemdetails.a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motX), Double.valueOf(this.motY), Double.valueOf(this.motZ) }));
  }
  
  public UUID getUniqueID() {
    return this.uniqueID;
  }
  
  public boolean aC() {
    return true;
  }
  
  public IChatBaseComponent getScoreboardDisplayName() {
    return new ChatComponentText(getName());
  }
  

  public void i(int i) {}
  

  private boolean paperNetherCheck()
  {
    return (this.world.paperSpigotConfig.netherVoidTopDamage) && (this.world.getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) && (this.locY >= 128.0D);
  }
}
