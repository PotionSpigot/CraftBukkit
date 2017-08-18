package net.minecraft.server;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;
import org.spigotmc.ProtocolData.DualInt;

public abstract class EntityMinecartAbstract extends Entity
{
  private boolean a;
  private String b;
  private static final int[][][] matrix = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
  
  private int d;
  
  private double e;
  private double f;
  private double g;
  private double h;
  private double i;
  public boolean slowWhenEmpty = true;
  private double derailedX = 0.5D;
  private double derailedY = 0.5D;
  private double derailedZ = 0.5D;
  private double flyingX = 0.95D;
  private double flyingY = 0.95D;
  private double flyingZ = 0.95D;
  public double maxSpeed = 0.4D;
  
  public EntityMinecartAbstract(World world)
  {
    super(world);
    this.k = true;
    a(0.98F, 0.7F);
    this.height = (this.length / 2.0F);
  }
  
  public static EntityMinecartAbstract a(World world, double d0, double d1, double d2, int i) {
    switch (i) {
    case 1: 
      return new EntityMinecartChest(world, d0, d1, d2);
    
    case 2: 
      return new EntityMinecartFurnace(world, d0, d1, d2);
    
    case 3: 
      return new EntityMinecartTNT(world, d0, d1, d2);
    
    case 4: 
      return new EntityMinecartMobSpawner(world, d0, d1, d2);
    
    case 5: 
      return new EntityMinecartHopper(world, d0, d1, d2);
    
    case 6: 
      return new EntityMinecartCommandBlock(world, d0, d1, d2);
    }
    
    return new EntityMinecartRideable(world, d0, d1, d2);
  }
  
  protected boolean g_()
  {
    return false;
  }
  
  protected void c() {
    this.datawatcher.a(17, new Integer(0));
    this.datawatcher.a(18, new Integer(1));
    this.datawatcher.a(19, new Float(0.0F));
    this.datawatcher.a(20, new ProtocolData.DualInt(0, 0));
    this.datawatcher.a(21, new Integer(6));
    this.datawatcher.a(22, Byte.valueOf((byte)0));
  }
  
  public AxisAlignedBB h(Entity entity) {
    return entity.S() ? entity.boundingBox : null;
  }
  
  public AxisAlignedBB J() {
    return null;
  }
  
  public boolean S() {
    return true;
  }
  
  public EntityMinecartAbstract(World world, double d0, double d1, double d2) {
    this(world);
    setPosition(d0, d1, d2);
    this.motX = 0.0D;
    this.motY = 0.0D;
    this.motZ = 0.0D;
    this.lastX = d0;
    this.lastY = d1;
    this.lastZ = d2;
    
    this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle)getBukkitEntity()));
  }
  
  public double ae() {
    return this.length * 0.0D - 0.30000001192092896D;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if ((!this.world.isStatic) && (!this.dead)) {
      if (isInvulnerable()) {
        return false;
      }
      
      Vehicle vehicle = (Vehicle)getBukkitEntity();
      org.bukkit.entity.Entity passenger = damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity();
      
      VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, f);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (event.isCancelled()) {
        return true;
      }
      
      f = (float)event.getDamage();
      

      j(-l());
      c(10);
      Q();
      setDamage(getDamage() + f * 10.0F);
      boolean flag = ((damagesource.getEntity() instanceof EntityHuman)) && (((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
      
      if ((flag) || (getDamage() > 40.0F)) {
        if (this.passenger != null) {
          this.passenger.mount(this);
        }
        

        VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
        this.world.getServer().getPluginManager().callEvent(destroyEvent);
        
        if (destroyEvent.isCancelled()) {
          setDamage(40.0F);
          return true;
        }
        

        if ((flag) && (!k_())) {
          die();
        } else {
          a(damagesource);
        }
      }
      
      return true;
    }
    
    return true;
  }
  
  public void a(DamageSource damagesource)
  {
    die();
    ItemStack itemstack = new ItemStack(Items.MINECART, 1);
    
    if (this.b != null) {
      itemstack.c(this.b);
    }
    
    a(itemstack, 0.0F);
  }
  
  public boolean R() {
    return !this.dead;
  }
  
  public void die() {
    super.die();
  }
  
  public void h()
  {
    double prevX = this.locX;
    double prevY = this.locY;
    double prevZ = this.locZ;
    float prevYaw = this.yaw;
    float prevPitch = this.pitch;
    

    if (getType() > 0) {
      c(getType() - 1);
    }
    
    if (getDamage() > 0.0F) {
      setDamage(getDamage() - 1.0F);
    }
    
    if (this.locY < -64.0D) {
      G();
    }
    


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
    
    if (this.world.isStatic) {
      if (this.d > 0) {
        double d0 = this.locX + (this.e - this.locX) / this.d;
        double d1 = this.locY + (this.f - this.locY) / this.d;
        double d2 = this.locZ + (this.g - this.locZ) / this.d;
        double d3 = MathHelper.g(this.h - this.yaw);
        
        this.yaw = ((float)(this.yaw + d3 / this.d));
        this.pitch = ((float)(this.pitch + (this.i - this.pitch) / this.d));
        this.d -= 1;
        setPosition(d0, d1, d2);
        b(this.yaw, this.pitch);
      } else {
        setPosition(this.locX, this.locY, this.locZ);
        b(this.yaw, this.pitch);
      }
    } else {
      this.lastX = this.locX;
      this.lastY = this.locY;
      this.lastZ = this.locZ;
      this.motY -= 0.03999999910593033D;
      int j = MathHelper.floor(this.locX);
      
      int i = MathHelper.floor(this.locY);
      int k = MathHelper.floor(this.locZ);
      
      if (BlockMinecartTrackAbstract.b_(this.world, j, i - 1, k)) {
        i--;
      }
      
      double d4 = this.maxSpeed;
      double d5 = 0.0078125D;
      Block block = this.world.getType(j, i, k);
      
      if (BlockMinecartTrackAbstract.a(block)) {
        int l = this.world.getData(j, i, k);
        
        a(j, i, k, d4, d5, block, l);
        if (block == Blocks.ACTIVATOR_RAIL) {
          a(j, i, k, (l & 0x8) != 0);
        }
      } else {
        b(d4);
      }
      
      I();
      this.pitch = 0.0F;
      double d6 = this.lastX - this.locX;
      double d7 = this.lastZ - this.locZ;
      
      if (d6 * d6 + d7 * d7 > 0.001D) {
        this.yaw = ((float)(Math.atan2(d7, d6) * 180.0D / 3.141592653589793D));
        if (this.a) {
          this.yaw += 180.0F;
        }
      }
      
      double d8 = MathHelper.g(this.yaw - this.lastYaw);
      
      if ((d8 < -170.0D) || (d8 >= 170.0D)) {
        this.yaw += 180.0F;
        this.a = (!this.a);
      }
      
      b(this.yaw, this.pitch);
      

      org.bukkit.World bworld = this.world.getWorld();
      Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
      Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      Vehicle vehicle = (Vehicle)getBukkitEntity();
      
      this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));
      
      if (!from.equals(to)) {
        this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleMoveEvent(vehicle, from, to));
      }
      

      List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
      
      if ((list != null) && (!list.isEmpty())) {
        for (int i1 = 0; i1 < list.size(); i1++) {
          Entity entity = (Entity)list.get(i1);
          
          if ((entity != this.passenger) && (entity.S()) && ((entity instanceof EntityMinecartAbstract))) {
            entity.collide(this);
          }
        }
      }
      
      if ((this.passenger != null) && (this.passenger.dead)) {
        if (this.passenger.vehicle == this) {
          this.passenger.vehicle = null;
        }
        
        this.passenger = null;
      }
      

      if ((this.world.spigotConfig.altHopperTicking) && ((this instanceof EntityMinecartContainer))) {
        int xi = MathHelper.floor(this.boundingBox.a) - 1;
        int yi = MathHelper.floor(this.boundingBox.b) - 1;
        int zi = MathHelper.floor(this.boundingBox.c) - 1;
        int xf = MathHelper.floor(this.boundingBox.d) + 1;
        int yf = MathHelper.floor(this.boundingBox.e) + 1;
        int zf = MathHelper.floor(this.boundingBox.f) + 1;
        for (int a = xi; a <= xf; a++) {
          for (int b = yi; b <= yf; b++) {
            for (int c = zi; c <= zf; c++) {
              TileEntity tileEntity = this.world.getTileEntity(a, b, c);
              if ((tileEntity instanceof TileEntityHopper)) {
                ((TileEntityHopper)tileEntity).makeTick();
              }
            }
          }
        }
      }
    }
  }
  
  public void a(int i, int j, int k, boolean flag) {}
  
  protected void b(double d0)
  {
    if (this.motX < -d0) {
      this.motX = (-d0);
    }
    
    if (this.motX > d0) {
      this.motX = d0;
    }
    
    if (this.motZ < -d0) {
      this.motZ = (-d0);
    }
    
    if (this.motZ > d0) {
      this.motZ = d0;
    }
    
    if (this.onGround)
    {
      this.motX *= this.derailedX;
      this.motY *= this.derailedY;
      this.motZ *= this.derailedZ;
    }
    

    move(this.motX, this.motY, this.motZ);
    if (!this.onGround)
    {
      this.motX *= this.flyingX;
      this.motY *= this.flyingY;
      this.motZ *= this.flyingZ;
    }
  }
  
  protected void a(int i, int j, int k, double d0, double d1, Block block, int l)
  {
    this.fallDistance = 0.0F;
    Vec3D vec3d = a(this.locX, this.locY, this.locZ);
    
    this.locY = j;
    boolean flag = false;
    boolean flag1 = false;
    
    if (block == Blocks.GOLDEN_RAIL) {
      flag = (l & 0x8) != 0;
      flag1 = !flag;
    }
    
    if (((BlockMinecartTrackAbstract)block).e()) {
      l &= 0x7;
    }
    
    if ((l >= 2) && (l <= 5)) {
      this.locY = (j + 1);
    }
    
    if (l == 2) {
      this.motX -= d1;
    }
    
    if (l == 3) {
      this.motX += d1;
    }
    
    if (l == 4) {
      this.motZ += d1;
    }
    
    if (l == 5) {
      this.motZ -= d1;
    }
    
    int[][] aint = matrix[l];
    double d2 = aint[1][0] - aint[0][0];
    double d3 = aint[1][2] - aint[0][2];
    double d4 = Math.sqrt(d2 * d2 + d3 * d3);
    double d5 = this.motX * d2 + this.motZ * d3;
    
    if (d5 < 0.0D) {
      d2 = -d2;
      d3 = -d3;
    }
    
    double d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    
    if (d6 > 2.0D) {
      d6 = 2.0D;
    }
    
    this.motX = (d6 * d2 / d4);
    this.motZ = (d6 * d3 / d4);
    




    if ((this.passenger != null) && ((this.passenger instanceof EntityLiving))) {
      double d7 = ((EntityLiving)this.passenger).be;
      if (d7 > 0.0D) {
        double d8 = -Math.sin(this.passenger.yaw * 3.1415927F / 180.0F);
        double d9 = Math.cos(this.passenger.yaw * 3.1415927F / 180.0F);
        double d10 = this.motX * this.motX + this.motZ * this.motZ;
        if (d10 < 0.01D) {
          this.motX += d8 * 0.1D;
          this.motZ += d9 * 0.1D;
          flag1 = false;
        }
      }
    }
    
    if (flag1) {
      double d7 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      if (d7 < 0.03D) {
        this.motX *= 0.0D;
        this.motY *= 0.0D;
        this.motZ *= 0.0D;
      } else {
        this.motX *= 0.5D;
        this.motY *= 0.0D;
        this.motZ *= 0.5D;
      }
    }
    
    double d7 = 0.0D;
    double d8 = i + 0.5D + aint[0][0] * 0.5D;
    double d9 = k + 0.5D + aint[0][2] * 0.5D;
    double d10 = i + 0.5D + aint[1][0] * 0.5D;
    double d11 = k + 0.5D + aint[1][2] * 0.5D;
    
    d2 = d10 - d8;
    d3 = d11 - d9;
    


    if (d2 == 0.0D) {
      this.locX = (i + 0.5D);
      d7 = this.locZ - k;
    } else if (d3 == 0.0D) {
      this.locZ = (k + 0.5D);
      d7 = this.locX - i;
    } else {
      double d12 = this.locX - d8;
      double d13 = this.locZ - d9;
      d7 = (d12 * d2 + d13 * d3) * 2.0D;
    }
    
    this.locX = (d8 + d2 * d7);
    this.locZ = (d9 + d3 * d7);
    setPosition(this.locX, this.locY + this.height, this.locZ);
    double d12 = this.motX;
    double d13 = this.motZ;
    if (this.passenger != null) {
      d12 *= 0.75D;
      d13 *= 0.75D;
    }
    
    if (d12 < -d0) {
      d12 = -d0;
    }
    
    if (d12 > d0) {
      d12 = d0;
    }
    
    if (d13 < -d0) {
      d13 = -d0;
    }
    
    if (d13 > d0) {
      d13 = d0;
    }
    
    move(d12, 0.0D, d13);
    if ((aint[0][1] != 0) && (MathHelper.floor(this.locX) - i == aint[0][0]) && (MathHelper.floor(this.locZ) - k == aint[0][2])) {
      setPosition(this.locX, this.locY + aint[0][1], this.locZ);
    } else if ((aint[1][1] != 0) && (MathHelper.floor(this.locX) - i == aint[1][0]) && (MathHelper.floor(this.locZ) - k == aint[1][2])) {
      setPosition(this.locX, this.locY + aint[1][1], this.locZ);
    }
    
    i();
    Vec3D vec3d1 = a(this.locX, this.locY, this.locZ);
    
    if ((vec3d1 != null) && (vec3d != null)) {
      double d14 = (vec3d.b - vec3d1.b) * 0.05D;
      
      d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      if (d6 > 0.0D) {
        this.motX = (this.motX / d6 * (d6 + d14));
        this.motZ = (this.motZ / d6 * (d6 + d14));
      }
      
      setPosition(this.locX, vec3d1.b, this.locZ);
    }
    
    int i1 = MathHelper.floor(this.locX);
    int j1 = MathHelper.floor(this.locZ);
    
    if ((i1 != i) || (j1 != k)) {
      d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      this.motX = (d6 * (i1 - i));
      this.motZ = (d6 * (j1 - k));
    }
    
    if (flag) {
      double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      
      if (d15 > 0.01D) {
        double d16 = 0.06D;
        
        this.motX += this.motX / d15 * d16;
        this.motZ += this.motZ / d15 * d16;
      } else if (l == 1) {
        if (this.world.getType(i - 1, j, k).r()) {
          this.motX = 0.02D;
        } else if (this.world.getType(i + 1, j, k).r()) {
          this.motX = -0.02D;
        }
      } else if (l == 0) {
        if (this.world.getType(i, j, k - 1).r()) {
          this.motZ = 0.02D;
        } else if (this.world.getType(i, j, k + 1).r()) {
          this.motZ = -0.02D;
        }
      }
    }
  }
  
  protected void i() {
    if ((this.passenger != null) || (!this.slowWhenEmpty)) {
      this.motX *= 0.996999979019165D;
      this.motY *= 0.0D;
      this.motZ *= 0.996999979019165D;
    } else {
      this.motX *= 0.9599999785423279D;
      this.motY *= 0.0D;
      this.motZ *= 0.9599999785423279D;
    }
  }
  
  public Vec3D a(double d0, double d1, double d2) {
    int i = MathHelper.floor(d0);
    int j = MathHelper.floor(d1);
    int k = MathHelper.floor(d2);
    
    if (BlockMinecartTrackAbstract.b_(this.world, i, j - 1, k)) {
      j--;
    }
    
    Block block = this.world.getType(i, j, k);
    
    if (BlockMinecartTrackAbstract.a(block)) {
      int l = this.world.getData(i, j, k);
      
      d1 = j;
      if (((BlockMinecartTrackAbstract)block).e()) {
        l &= 0x7;
      }
      
      if ((l >= 2) && (l <= 5)) {
        d1 = j + 1;
      }
      
      int[][] aint = matrix[l];
      double d3 = 0.0D;
      double d4 = i + 0.5D + aint[0][0] * 0.5D;
      double d5 = j + 0.5D + aint[0][1] * 0.5D;
      double d6 = k + 0.5D + aint[0][2] * 0.5D;
      double d7 = i + 0.5D + aint[1][0] * 0.5D;
      double d8 = j + 0.5D + aint[1][1] * 0.5D;
      double d9 = k + 0.5D + aint[1][2] * 0.5D;
      double d10 = d7 - d4;
      double d11 = (d8 - d5) * 2.0D;
      double d12 = d9 - d6;
      
      if (d10 == 0.0D) {
        d0 = i + 0.5D;
        d3 = d2 - k;
      } else if (d12 == 0.0D) {
        d2 = k + 0.5D;
        d3 = d0 - i;
      } else {
        double d13 = d0 - d4;
        double d14 = d2 - d6;
        
        d3 = (d13 * d10 + d14 * d12) * 2.0D;
      }
      
      d0 = d4 + d10 * d3;
      d1 = d5 + d11 * d3;
      d2 = d6 + d12 * d3;
      if (d11 < 0.0D) {
        d1 += 1.0D;
      }
      
      if (d11 > 0.0D) {
        d1 += 0.5D;
      }
      
      return Vec3D.a(d0, d1, d2);
    }
    return null;
  }
  
  protected void a(NBTTagCompound nbttagcompound)
  {
    if (nbttagcompound.getBoolean("CustomDisplayTile")) {
      k(nbttagcompound.getInt("DisplayTile"));
      l(nbttagcompound.getInt("DisplayData"));
      m(nbttagcompound.getInt("DisplayOffset"));
    }
    
    if ((nbttagcompound.hasKeyOfType("CustomName", 8)) && (nbttagcompound.getString("CustomName").length() > 0)) {
      this.b = nbttagcompound.getString("CustomName");
    }
  }
  
  protected void b(NBTTagCompound nbttagcompound) {
    if (t()) {
      nbttagcompound.setBoolean("CustomDisplayTile", true);
      nbttagcompound.setInt("DisplayTile", n().getMaterial() == Material.AIR ? 0 : Block.getId(n()));
      nbttagcompound.setInt("DisplayData", p());
      nbttagcompound.setInt("DisplayOffset", r());
    }
    
    if ((this.b != null) && (this.b.length() > 0)) {
      nbttagcompound.setString("CustomName", this.b);
    }
  }
  
  public void collide(Entity entity) {
    if ((!this.world.isStatic) && 
      (entity != this.passenger))
    {
      Vehicle vehicle = (Vehicle)getBukkitEntity();
      org.bukkit.entity.Entity hitEntity = entity == null ? null : entity.getBukkitEntity();
      
      VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
      this.world.getServer().getPluginManager().callEvent(collisionEvent);
      
      if (collisionEvent.isCancelled()) {
        return;
      }
      

      if (((entity instanceof EntityLiving)) && (!(entity instanceof EntityHuman)) && (!(entity instanceof EntityIronGolem)) && (m() == 0) && (this.motX * this.motX + this.motZ * this.motZ > 0.01D) && (this.passenger == null) && (entity.vehicle == null)) {
        entity.mount(this);
      }
      
      double d0 = entity.locX - this.locX;
      double d1 = entity.locZ - this.locZ;
      double d2 = d0 * d0 + d1 * d1;
      

      if ((d2 >= 9.999999747378752E-5D) && (!collisionEvent.isCollisionCancelled())) {
        d2 = MathHelper.sqrt(d2);
        d0 /= d2;
        d1 /= d2;
        double d3 = 1.0D / d2;
        
        if (d3 > 1.0D) {
          d3 = 1.0D;
        }
        
        d0 *= d3;
        d1 *= d3;
        d0 *= 0.10000000149011612D;
        d1 *= 0.10000000149011612D;
        d0 *= (1.0F - this.Y);
        d1 *= (1.0F - this.Y);
        d0 *= 0.5D;
        d1 *= 0.5D;
        if ((entity instanceof EntityMinecartAbstract)) {
          double d4 = entity.locX - this.locX;
          double d5 = entity.locZ - this.locZ;
          Vec3D vec3d = Vec3D.a(d4, 0.0D, d5).a();
          Vec3D vec3d1 = Vec3D.a(MathHelper.cos(this.yaw * 3.1415927F / 180.0F), 0.0D, MathHelper.sin(this.yaw * 3.1415927F / 180.0F)).a();
          double d6 = Math.abs(vec3d.b(vec3d1));
          
          if (d6 < 0.800000011920929D) {
            return;
          }
          
          double d7 = entity.motX + this.motX;
          double d8 = entity.motZ + this.motZ;
          
          if ((((EntityMinecartAbstract)entity).m() == 2) && (m() != 2)) {
            this.motX *= 0.20000000298023224D;
            this.motZ *= 0.20000000298023224D;
            g(entity.motX - d0, 0.0D, entity.motZ - d1);
            entity.motX *= 0.949999988079071D;
            entity.motZ *= 0.949999988079071D;
          } else if ((((EntityMinecartAbstract)entity).m() != 2) && (m() == 2)) {
            entity.motX *= 0.20000000298023224D;
            entity.motZ *= 0.20000000298023224D;
            entity.g(this.motX + d0, 0.0D, this.motZ + d1);
            this.motX *= 0.949999988079071D;
            this.motZ *= 0.949999988079071D;
          } else {
            d7 /= 2.0D;
            d8 /= 2.0D;
            this.motX *= 0.20000000298023224D;
            this.motZ *= 0.20000000298023224D;
            g(d7 - d0, 0.0D, d8 - d1);
            entity.motX *= 0.20000000298023224D;
            entity.motZ *= 0.20000000298023224D;
            entity.g(d7 + d0, 0.0D, d8 + d1);
          }
        } else {
          g(-d0, 0.0D, -d1);
          entity.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
        }
      }
    }
  }
  
  public void setDamage(float f)
  {
    this.datawatcher.watch(19, Float.valueOf(f));
  }
  
  public float getDamage() {
    return this.datawatcher.getFloat(19);
  }
  
  public void c(int i) {
    this.datawatcher.watch(17, Integer.valueOf(i));
  }
  
  public int getType() {
    return this.datawatcher.getInt(17);
  }
  
  public void j(int i) {
    this.datawatcher.watch(18, Integer.valueOf(i));
  }
  
  public int l() {
    return this.datawatcher.getInt(18);
  }
  
  public abstract int m();
  
  public Block n() {
    if (!t()) {
      return o();
    }
    int i = getDataWatcher().getInt(20) & 0xFFFF;
    
    return Block.getById(i);
  }
  
  public Block o()
  {
    return Blocks.AIR;
  }
  
  public int p() {
    return !t() ? q() : getDataWatcher().getInt(20) >> 16;
  }
  
  public int q() {
    return 0;
  }
  
  public int r() {
    return !t() ? s() : getDataWatcher().getInt(21);
  }
  
  public int s() {
    return 6;
  }
  
  public void k(int i)
  {
    ProtocolData.DualInt val = this.datawatcher.getDualInt(20);
    val.value = Integer.valueOf(i & 0xFFFF | p() << 16).intValue();
    val.value2 = Integer.valueOf(i & 0xFFFF | p() << 12).intValue();
    getDataWatcher().watch(20, val);
    
    a(true);
  }
  
  public void l(int i)
  {
    ProtocolData.DualInt val = this.datawatcher.getDualInt(20);
    val.value = Integer.valueOf(Block.getId(n()) & 0xFFFF | i << 16).intValue();
    val.value2 = Integer.valueOf(Block.getId(n()) & 0xFFFF | i << 12).intValue();
    getDataWatcher().watch(20, val);
    
    a(true);
  }
  
  public void m(int i) {
    getDataWatcher().watch(21, Integer.valueOf(i));
    a(true);
  }
  
  public boolean t() {
    return getDataWatcher().getByte(22) == 1;
  }
  
  public void a(boolean flag) {
    getDataWatcher().watch(22, Byte.valueOf((byte)(flag ? 1 : 0)));
  }
  
  public void a(String s) {
    this.b = s;
  }
  
  public String getName() {
    return this.b != null ? this.b : super.getName();
  }
  
  public boolean k_() {
    return this.b != null;
  }
  
  public String u() {
    return this.b;
  }
  
  public Vector getFlyingVelocityMod()
  {
    return new Vector(this.flyingX, this.flyingY, this.flyingZ);
  }
  
  public void setFlyingVelocityMod(Vector flying) {
    this.flyingX = flying.getX();
    this.flyingY = flying.getY();
    this.flyingZ = flying.getZ();
  }
  
  public Vector getDerailedVelocityMod() {
    return new Vector(this.derailedX, this.derailedY, this.derailedZ);
  }
  
  public void setDerailedVelocityMod(Vector derailed) {
    this.derailedX = derailed.getX();
    this.derailedY = derailed.getY();
    this.derailedZ = derailed.getZ();
  }
}
