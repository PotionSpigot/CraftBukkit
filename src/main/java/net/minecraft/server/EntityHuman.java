package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftItem;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.spigotmc.ProtocolData.DualByte;
import org.spigotmc.SpigotWorldConfig;

public abstract class EntityHuman extends EntityLiving implements ICommandListener
{
  public PlayerInventory inventory = new PlayerInventory(this);
  private InventoryEnderChest enderChest = new InventoryEnderChest();
  public Container defaultContainer;
  public Container activeContainer;
  protected FoodMetaData foodData = new FoodMetaData(this);
  
  protected int bq;
  
  public float br;
  public float bs;
  public int bt;
  public double bu;
  public double bv;
  public double bw;
  public double bx;
  public double by;
  public double bz;
  public boolean sleeping;
  public boolean fauxSleeping;
  public String spawnWorld = "";
  public boolean affectsSpawning = true;
  public ChunkCoordinates bB;
  
  public org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity getBukkitEntity() {
    return (org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity)super.getBukkitEntity();
  }
  

  public int sleepTicks;
  
  public float bC;
  public float bD;
  private ChunkCoordinates c;
  private boolean d;
  private ChunkCoordinates e;
  public PlayerAbilities abilities = new PlayerAbilities();
  public int oldLevel = -1;
  public int expLevel;
  public int expTotal;
  public float exp;
  private ItemStack f;
  private int g;
  protected float bI = 0.1F;
  protected float bJ = 0.02F;
  
  private int h;
  private final GameProfile i;
  public EntityFishingHook hookedFish;
  public static double kbX = 0.97D;
  public static double kbY = 0.93D;
  public static double kbZ = 0.97D;
  
  public EntityHuman(World world, GameProfile gameprofile) {
    super(world);
    this.uniqueID = a(gameprofile);
    this.i = gameprofile;
    this.defaultContainer = new ContainerPlayer(this.inventory, !world.isStatic, this);
    this.activeContainer = this.defaultContainer;
    this.height = 1.62F;
    ChunkCoordinates chunkcoordinates = world.getSpawn();
    
    setPositionRotation(chunkcoordinates.x + 0.5D, chunkcoordinates.y + 1, chunkcoordinates.z + 0.5D, 0.0F, 0.0F);
    this.aZ = 180.0F;
    this.maxFireTicks = 20;
  }
  
  protected void aD() {
    super.aD();
    getAttributeMap().b(GenericAttributes.e).setValue(1.0D);
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(16, new ProtocolData.DualByte((byte)0, (byte)0));
    this.datawatcher.a(17, Float.valueOf(0.0F));
    this.datawatcher.a(18, Integer.valueOf(0));
    this.datawatcher.a(10, new org.spigotmc.ProtocolData.HiddenByte((byte)0));
  }
  
  public boolean by() {
    return this.f != null;
  }
  
  public void bA() {
    if (this.f != null) {
      this.f.b(this.world, this, this.g);
    }
    
    bB();
  }
  
  public void bB() {
    this.f = null;
    this.g = 0;
    if (!this.world.isStatic) {
      e(false);
    }
  }
  
  public boolean isBlocking() {
    return (by()) && (this.f.getItem().d(this.f) == EnumAnimation.BLOCK);
  }
  
  public void h() {
    if (this.f != null) {
      ItemStack itemstack = this.inventory.getItemInHand();
      
      if (itemstack == this.f) {
        if ((this.g <= 25) && (this.g % 4 == 0)) {
          c(itemstack, 5);
        }
        
        if ((--this.g == 0) && (!this.world.isStatic)) {
          p();
        }
      } else {
        bB();
      }
    }
    
    if (this.bt > 0) {
      this.bt -= 1;
    }
    
    if (isSleeping()) {
      this.sleepTicks += 1;
      if (this.sleepTicks > 100) {
        this.sleepTicks = 100;
      }
      
      if (!this.world.isStatic) {
        if (!j()) {
          a(true, true, false);
        } else if (this.world.w()) {
          a(false, true, true);
        }
      }
    } else if (this.sleepTicks > 0) {
      this.sleepTicks += 1;
      if (this.sleepTicks >= 110) {
        this.sleepTicks = 0;
      }
    }
    
    super.h();
    if ((!this.world.isStatic) && (this.activeContainer != null) && (!this.activeContainer.a(this))) {
      closeInventory();
      this.activeContainer = this.defaultContainer;
    }
    
    if ((isBurning()) && (this.abilities.isInvulnerable)) {
      extinguish();
    }
    
    this.bu = this.bx;
    this.bv = this.by;
    this.bw = this.bz;
    double d0 = this.locX - this.bx;
    double d1 = this.locY - this.by;
    double d2 = this.locZ - this.bz;
    double d3 = 10.0D;
    
    if (d0 > d3) {
      this.bu = (this.bx = this.locX);
    }
    
    if (d2 > d3) {
      this.bw = (this.bz = this.locZ);
    }
    
    if (d1 > d3) {
      this.bv = (this.by = this.locY);
    }
    
    if (d0 < -d3) {
      this.bu = (this.bx = this.locX);
    }
    
    if (d2 < -d3) {
      this.bw = (this.bz = this.locZ);
    }
    
    if (d1 < -d3) {
      this.bv = (this.by = this.locY);
    }
    
    this.bx += d0 * 0.25D;
    this.bz += d2 * 0.25D;
    this.by += d1 * 0.25D;
    if (this.vehicle == null) {
      this.e = null;
    }
    
    if (!this.world.isStatic) {
      this.foodData.a(this);
      a(StatisticList.g, 1);
    }
  }
  
  public int D() {
    return this.abilities.isInvulnerable ? 0 : 80;
  }
  
  protected String H() {
    return "game.player.swim";
  }
  
  protected String O() {
    return "game.player.swim.splash";
  }
  
  public int ai() {
    return 10;
  }
  
  public void makeSound(String s, float f, float f1) {
    this.world.a(this, s, f, f1);
  }
  
  protected void c(ItemStack itemstack, int i) {
    if (itemstack.o() == EnumAnimation.DRINK) {
      makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
    
    if (itemstack.o() == EnumAnimation.EAT) {
      for (int j = 0; j < i; j++) {
        Vec3D vec3d = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
        
        vec3d.a(-this.pitch * 3.1415927F / 180.0F);
        vec3d.b(-this.yaw * 3.1415927F / 180.0F);
        Vec3D vec3d1 = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.3D, -this.random.nextFloat() * 0.6D - 0.3D, 0.6D);
        
        vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
        vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
        vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
        String s = "iconcrack_" + Item.getId(itemstack.getItem());
        
        if (itemstack.usesData()) {
          s = s + "_" + itemstack.getData();
        }
        
        this.world.addParticle(s, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c);
      }
      
      makeSound("random.eat", 0.5F + 0.5F * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }
  }
  
  protected void p() {
    if (this.f != null) {
      c(this.f, 16);
      int i = this.f.count;
      

      org.bukkit.inventory.ItemStack craftItem = CraftItemStack.asBukkitCopy(this.f);
      PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player)getBukkitEntity(), craftItem);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (event.isCancelled())
      {
        if ((this instanceof EntityPlayer)) {
          ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.f));
          
          ((EntityPlayer)this).getBukkitEntity().updateInventory();
          ((EntityPlayer)this).getBukkitEntity().updateScaledHealth();
        }
        
        return;
      }
      

      if (!craftItem.equals(event.getItem())) {
        CraftItemStack.asNMSCopy(event.getItem()).b(this.world, this);
        

        if ((this instanceof EntityPlayer)) {
          ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.f));
        }
        return;
      }
      

      ItemStack itemstack = this.f.b(this.world, this);
      
      if ((itemstack != this.f) || ((itemstack != null) && (itemstack.count != i))) {
        this.inventory.items[this.inventory.itemInHandIndex] = itemstack;
        if (itemstack.count == 0) {
          this.inventory.items[this.inventory.itemInHandIndex] = null;
        }
      }
      
      bB();
    }
  }
  
  protected boolean bh() {
    return (getHealth() <= 0.0F) || (isSleeping());
  }
  
  public void closeInventory()
  {
    this.activeContainer = this.defaultContainer;
  }
  
  public void mount(Entity entity)
  {
    setPassengerOf(entity);
  }
  
  public void setPassengerOf(Entity entity)
  {
    if ((this.vehicle != null) && (entity == null)) {
      this.world.getServer().getPluginManager().callEvent(new org.spigotmc.event.entity.EntityDismountEvent(getBukkitEntity(), this.vehicle.getBukkitEntity()));
      
      Entity originalVehicle = this.vehicle;
      











      super.setPassengerOf(entity);
      if ((!this.world.isStatic) && (this.vehicle == null)) {
        m(originalVehicle);
      }
    }
    else {
      super.setPassengerOf(entity);
    }
  }
  
  public void ab() {
    if ((!this.world.isStatic) && (isSneaking())) {
      mount((Entity)null);
      setSneaking(false);
    } else {
      double d0 = this.locX;
      double d1 = this.locY;
      double d2 = this.locZ;
      float f = this.yaw;
      float f1 = this.pitch;
      
      super.ab();
      this.br = this.bs;
      this.bs = 0.0F;
      l(this.locX - d0, this.locY - d1, this.locZ - d2);
      if ((this.vehicle instanceof EntityPig)) {
        this.pitch = f1;
        this.yaw = f;
        this.aM = ((EntityPig)this.vehicle).aM;
      }
    }
  }
  
  protected void bq() {
    super.bq();
    bb();
  }
  
  public void e() {
    if (this.bq > 0) {
      this.bq -= 1;
    }
    
    if ((this.world.difficulty == EnumDifficulty.PEACEFUL) && (getHealth() < getMaxHealth()) && (this.world.getGameRules().getBoolean("naturalRegeneration")) && (this.ticksLived % 20 * 12 == 0))
    {
      heal(1.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.REGEN);
    }
    
    this.inventory.k();
    this.br = this.bs;
    super.e();
    AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
    
    if (!this.world.isStatic) {
      attributeinstance.setValue(this.abilities.b());
    }
    
    this.aQ = this.bJ;
    if (isSprinting()) {
      this.aQ = ((float)(this.aQ + this.bJ * 0.3D));
    }
    
    i((float)attributeinstance.getValue());
    float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    
    float f1 = (float)org.bukkit.craftbukkit.v1_7_R4.TrigMath.atan(-this.motY * 0.20000000298023224D) * 15.0F;
    
    if (f > 0.1F) {
      f = 0.1F;
    }
    
    if ((!this.onGround) || (getHealth() <= 0.0F)) {
      f = 0.0F;
    }
    
    if ((this.onGround) || (getHealth() <= 0.0F)) {
      f1 = 0.0F;
    }
    
    this.bs += (f - this.bs) * 0.4F;
    this.aJ += (f1 - this.aJ) * 0.8F;
    if (getHealth() > 0.0F) {
      AxisAlignedBB axisalignedbb = null;
      
      if ((this.vehicle != null) && (!this.vehicle.dead)) {
        axisalignedbb = this.boundingBox.a(this.vehicle.boundingBox).grow(1.0D, 0.0D, 1.0D);
      } else {
        axisalignedbb = this.boundingBox.grow(1.0D, 0.5D, 1.0D);
      }
      
      List list = this.world.getEntities(this, axisalignedbb);
      
      if ((list != null) && (S())) {
        for (int i = 0; i < list.size(); i++) {
          Entity entity = (Entity)list.get(i);
          
          if (!entity.dead) {
            d(entity);
          }
        }
      }
    }
  }
  
  private void d(Entity entity) {
    entity.b_(this);
  }
  
  public int getScore() {
    return this.datawatcher.getInt(18);
  }
  
  public void setScore(int i) {
    this.datawatcher.watch(18, Integer.valueOf(i));
  }
  
  public void addScore(int i) {
    int j = getScore();
    
    this.datawatcher.watch(18, Integer.valueOf(j + i));
  }
  
  public void die(DamageSource damagesource) {
    super.die(damagesource);
    a(0.2F, 0.2F);
    setPosition(this.locX, this.locY, this.locZ);
    this.motY = 0.10000000149011612D;
    if (getName().equals("Notch")) {
      a(new ItemStack(Items.APPLE, 1), true, false);
    }
    
    if (!this.world.getGameRules().getBoolean("keepInventory")) {
      this.inventory.m();
    }
    
    if (damagesource != null) {
      this.motX = (-MathHelper.cos((this.az + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
      this.motZ = (-MathHelper.sin((this.az + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
    } else {
      this.motX = (this.motZ = 0.0D);
    }
    
    this.height = 0.1F;
    a(StatisticList.v, 1);
  }
  
  protected String aT() {
    return "game.player.hurt";
  }
  
  protected String aU() {
    return "game.player.die";
  }
  
  public void b(Entity entity, int i) {
    addScore(i);
    
    java.util.Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, getName(), new java.util.ArrayList());
    
    if ((entity instanceof EntityHuman)) {
      a(StatisticList.y, 1);
      
      this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, getName(), collection);
    } else {
      a(StatisticList.w, 1);
    }
    
    Iterator iterator = collection.iterator();
    
    while (iterator.hasNext()) {
      ScoreboardScore scoreboardscore = (ScoreboardScore)iterator.next();
      
      scoreboardscore.incrementScore();
    }
  }
  
  public EntityItem a(boolean flag)
  {
    return a(this.inventory.splitStack(this.inventory.itemInHandIndex, (flag) && (this.inventory.getItemInHand() != null) ? this.inventory.getItemInHand().count : 1), false, true);
  }
  
  public EntityItem drop(ItemStack itemstack, boolean flag) {
    return a(itemstack, false, false);
  }
  
  public EntityItem a(ItemStack itemstack, boolean flag, boolean flag1) {
    if (itemstack == null)
      return null;
    if (itemstack.count == 0) {
      return null;
    }
    EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY - 0.30000001192092896D + getHeadHeight(), this.locZ, itemstack);
    
    entityitem.pickupDelay = 40;
    if (flag1) {
      entityitem.b(getName());
    }
    
    float f = 0.1F;
    

    if (flag) {
      float f1 = this.random.nextFloat() * 0.5F;
      float f2 = this.random.nextFloat() * 3.1415927F * 2.0F;
      
      entityitem.motX = (-MathHelper.sin(f2) * f1);
      entityitem.motZ = (MathHelper.cos(f2) * f1);
      entityitem.motY = 0.20000000298023224D;
    } else {
      f = 0.3F;
      entityitem.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
      entityitem.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
      entityitem.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f + 0.1F);
      f = 0.02F;
      float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
      f *= this.random.nextFloat();
      entityitem.motX += Math.cos(f1) * f;
      entityitem.motY += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
      entityitem.motZ += Math.sin(f1) * f;
    }
    

    Player player = (Player)getBukkitEntity();
    CraftItem drop = new CraftItem(this.world.getServer(), entityitem);
    
    PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
    this.world.getServer().getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      org.bukkit.inventory.ItemStack cur = player.getInventory().getItemInHand();
      if ((flag1) && ((cur == null) || (cur.getAmount() == 0)))
      {
        player.getInventory().setItemInHand(drop.getItemStack());
      } else if ((flag1) && (cur.isSimilar(drop.getItemStack())) && (drop.getItemStack().getAmount() == 1))
      {
        cur.setAmount(cur.getAmount() + 1);
        player.getInventory().setItemInHand(cur);
      }
      else {
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { drop.getItemStack() });
      }
      return null;
    }
    

    a(entityitem);
    a(StatisticList.s, 1);
    return entityitem;
  }
  
  protected void a(EntityItem entityitem)
  {
    this.world.addEntity(entityitem);
  }
  
  public float a(Block block, boolean flag) {
    float f = this.inventory.a(block);
    
    if (f > 1.0F) {
      int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
      ItemStack itemstack = this.inventory.getItemInHand();
      
      if ((i > 0) && (itemstack != null)) {
        float f1 = i * i + 1;
        
        if ((!itemstack.b(block)) && (f <= 1.0F)) {
          f += f1 * 0.08F;
        } else {
          f += f1;
        }
      }
    }
    
    if (hasEffect(MobEffectList.FASTER_DIG)) {
      f *= (1.0F + (getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F);
    }
    
    if (hasEffect(MobEffectList.SLOWER_DIG)) {
      f *= (1.0F - (getEffect(MobEffectList.SLOWER_DIG).getAmplifier() + 1) * 0.2F);
    }
    
    if ((a(Material.WATER)) && (!EnchantmentManager.hasWaterWorkerEnchantment(this))) {
      f /= 5.0F;
    }
    
    if (!this.onGround) {
      f /= 5.0F;
    }
    
    return f;
  }
  
  public boolean a(Block block) {
    return this.inventory.b(block);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    this.uniqueID = a(this.i);
    NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);
    
    this.inventory.b(nbttaglist);
    this.inventory.itemInHandIndex = nbttagcompound.getInt("SelectedItemSlot");
    this.sleeping = nbttagcompound.getBoolean("Sleeping");
    this.sleepTicks = nbttagcompound.getShort("SleepTimer");
    this.exp = nbttagcompound.getFloat("XpP");
    this.expLevel = nbttagcompound.getInt("XpLevel");
    this.expTotal = nbttagcompound.getInt("XpTotal");
    setScore(nbttagcompound.getInt("Score"));
    if (this.sleeping) {
      this.bB = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
      a(true, true, false);
    }
    

    this.spawnWorld = nbttagcompound.getString("SpawnWorld");
    if ("".equals(this.spawnWorld)) {
      this.spawnWorld = ((org.bukkit.World)this.world.getServer().getWorlds().get(0)).getName();
    }
    

    if ((nbttagcompound.hasKeyOfType("SpawnX", 99)) && (nbttagcompound.hasKeyOfType("SpawnY", 99)) && (nbttagcompound.hasKeyOfType("SpawnZ", 99))) {
      this.c = new ChunkCoordinates(nbttagcompound.getInt("SpawnX"), nbttagcompound.getInt("SpawnY"), nbttagcompound.getInt("SpawnZ"));
      this.d = nbttagcompound.getBoolean("SpawnForced");
    }
    
    this.foodData.a(nbttagcompound);
    this.abilities.b(nbttagcompound);
    if (nbttagcompound.hasKeyOfType("EnderItems", 9)) {
      NBTTagList nbttaglist1 = nbttagcompound.getList("EnderItems", 10);
      
      this.enderChest.a(nbttaglist1);
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.set("Inventory", this.inventory.a(new NBTTagList()));
    nbttagcompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
    nbttagcompound.setBoolean("Sleeping", this.sleeping);
    nbttagcompound.setShort("SleepTimer", (short)this.sleepTicks);
    nbttagcompound.setFloat("XpP", this.exp);
    nbttagcompound.setInt("XpLevel", this.expLevel);
    nbttagcompound.setInt("XpTotal", this.expTotal);
    nbttagcompound.setInt("Score", getScore());
    if (this.c != null) {
      nbttagcompound.setInt("SpawnX", this.c.x);
      nbttagcompound.setInt("SpawnY", this.c.y);
      nbttagcompound.setInt("SpawnZ", this.c.z);
      nbttagcompound.setBoolean("SpawnForced", this.d);
      nbttagcompound.setString("SpawnWorld", this.spawnWorld);
    }
    
    this.foodData.b(nbttagcompound);
    this.abilities.a(nbttagcompound);
    nbttagcompound.set("EnderItems", this.enderChest.h());
  }
  
  public void openContainer(IInventory iinventory) {}
  
  public void openHopper(TileEntityHopper tileentityhopper) {}
  
  public void openMinecartHopper(EntityMinecartHopper entityminecarthopper) {}
  
  public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory) {}
  
  public void startEnchanting(int i, int j, int k, String s) {}
  
  public void openAnvil(int i, int j, int k) {}
  
  public void startCrafting(int i, int j, int k) {}
  
  public float getHeadHeight() {
    return 0.12F;
  }
  
  protected void e_() {
    this.height = 1.62F;
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable())
      return false;
    if ((this.abilities.isInvulnerable) && (!damagesource.ignoresInvulnerability())) {
      return false;
    }
    this.aU = 0;
    if (getHealth() <= 0.0F) {
      return false;
    }
    if ((isSleeping()) && (!this.world.isStatic)) {
      a(true, true, false);
    }
    
    if (damagesource.r()) {
      if (this.world.difficulty == EnumDifficulty.PEACEFUL) {
        return false;
      }
      
      if (this.world.difficulty == EnumDifficulty.EASY) {
        f = f / 2.0F + 1.0F;
      }
      
      if (this.world.difficulty == EnumDifficulty.HARD) {
        f = f * 3.0F / 2.0F;
      }
    }
    



    Entity entity = damagesource.getEntity();
    
    if (((entity instanceof EntityArrow)) && (((EntityArrow)entity).shooter != null)) {
      entity = ((EntityArrow)entity).shooter;
    }
    
    a(StatisticList.u, Math.round(f * 10.0F));
    return super.damageEntity(damagesource, f);
  }
  


  public boolean a(EntityHuman entityhuman)
  {
    Team team;
    

    if ((entityhuman instanceof EntityPlayer)) {
      EntityPlayer thatPlayer = (EntityPlayer)entityhuman;
      Team team = thatPlayer.getBukkitEntity().getScoreboard().getPlayerTeam(thatPlayer.getBukkitEntity());
      if ((team == null) || (team.allowFriendlyFire())) {
        return true;
      }
    }
    else {
      org.bukkit.OfflinePlayer thisPlayer = entityhuman.world.getServer().getOfflinePlayer(entityhuman.getName());
      team = entityhuman.world.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(thisPlayer);
      if ((team == null) || (team.allowFriendlyFire())) {
        return true;
      }
    }
    
    if ((this instanceof EntityPlayer)) {
      return !team.hasPlayer(((EntityPlayer)this).getBukkitEntity());
    }
    return !team.hasPlayer(this.world.getServer().getOfflinePlayer(getName()));
  }
  
  protected void damageArmor(float f)
  {
    this.inventory.a(f);
  }
  
  public int aV() {
    return this.inventory.l();
  }
  
  public float bE() {
    int i = 0;
    ItemStack[] aitemstack = this.inventory.armor;
    int j = aitemstack.length;
    
    for (int k = 0; k < j; k++) {
      ItemStack itemstack = aitemstack[k];
      
      if (itemstack != null) {
        i++;
      }
    }
    
    return i / this.inventory.armor.length;
  }
  

  protected boolean d(DamageSource damagesource, float f)
  {
    return super.d(damagesource, f);
  }
  



  public void openFurnace(TileEntityFurnace tileentityfurnace) {}
  



  public void openDispenser(TileEntityDispenser tileentitydispenser) {}
  



  public void a(TileEntity tileentity) {}
  


  public void a(CommandBlockListenerAbstract commandblocklistenerabstract) {}
  


  public void openBrewingStand(TileEntityBrewingStand tileentitybrewingstand) {}
  


  public void openBeacon(TileEntityBeacon tileentitybeacon) {}
  


  public void openTrade(IMerchant imerchant, String s) {}
  


  public void b(ItemStack itemstack) {}
  


  public boolean q(Entity entity)
  {
    ItemStack itemstack = bF();
    ItemStack itemstack1 = itemstack != null ? itemstack.cloneItemStack() : null;
    
    if (!entity.c(this)) {
      if ((itemstack != null) && ((entity instanceof EntityLiving))) {
        if (this.abilities.canInstantlyBuild) {
          itemstack = itemstack1;
        }
        
        if (itemstack.a(this, (EntityLiving)entity))
        {
          if ((itemstack.count == 0) && (!this.abilities.canInstantlyBuild)) {
            bG();
          }
          
          return true;
        }
      }
      
      return false;
    }
    if ((itemstack != null) && (itemstack == bF())) {
      if ((itemstack.count <= 0) && (!this.abilities.canInstantlyBuild)) {
        bG();
      } else if ((itemstack.count < itemstack1.count) && (this.abilities.canInstantlyBuild)) {
        itemstack.count = itemstack1.count;
      }
    }
    
    return true;
  }
  
  public ItemStack bF()
  {
    return this.inventory.getItemInHand();
  }
  
  public void bG() {
    this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack)null);
  }
  
  public double ad() {
    return this.height - 0.5F;
  }
  
  public void attack(Entity entity) {
    if ((entity.av()) && (!entity.j(this))) {
      float f = (float)getAttributeInstance(GenericAttributes.e).getValue();
      int i = 0;
      float f1 = 0.0F;
      
      if ((entity instanceof EntityLiving)) {
        f1 = EnchantmentManager.a(this, (EntityLiving)entity);
        i += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving)entity);
      }
      
      if (isSprinting()) {
        i++;
      }
      
      if ((f > 0.0F) || (f1 > 0.0F)) {
        boolean flag = (!this.world.paperSpigotConfig.disablePlayerCrits) && (this.fallDistance > 0.0F) && (!this.onGround) && (!h_()) && (!M()) && (!hasEffect(MobEffectList.BLINDNESS)) && (this.vehicle == null) && ((entity instanceof EntityLiving));
        
        if ((flag) && (f > 0.0F)) {
          f *= 1.5F;
        }
        
        f += f1;
        boolean flag1 = false;
        int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
        
        if (((entity instanceof EntityLiving)) && (j > 0) && (!entity.isBurning())) {
          EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 1);
          Bukkit.getPluginManager().callEvent(combustEvent);
          
          if (!combustEvent.isCancelled()) {
            flag1 = true;
            entity.setOnFire(combustEvent.getDuration());
          }
        }
        
        double d0 = entity.motX;
        double d1 = entity.motY;
        double d2 = entity.motZ;
        
        boolean flag2 = entity.damageEntity(DamageSource.playerAttack(this), f);
        
        if (flag2) {
          if (i > 0) {
            entity.g(-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * i * 0.5F * kbX, 0.0D, MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * i * 0.5F * kbZ);
            this.motX *= 0.6D;
            this.motZ *= 0.6D;
            setSprinting(false);
          }
          
          if (((entity instanceof EntityPlayer)) && (entity.velocityChanged)) {
            entity.motY *= kbY;
            
            boolean cancelled = false;
            
            Player player = (Player)entity.getBukkitEntity();
            Vector velocity = new Vector(d0, d1, d2);
            
            PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
            
            this.world.getServer().getPluginManager().callEvent(event);
            
            if (event.isCancelled()) {
              cancelled = true;
            } else if (!velocity.equals(event.getVelocity())) {
              player.setVelocity(event.getVelocity());
            }
            
            if (!cancelled) {
              ((EntityPlayer)entity).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(entity));
              entity.velocityChanged = false;
              entity.motX = d0;
              entity.motY = d1;
              entity.motZ = d2;
            }
          }
          
          if (flag) {
            b(entity);
          }
          
          if (f1 > 0.0F) {
            c(entity);
          }
          
          if (f >= 18.0F) {
            b(AchievementList.F);
          }
          
          p(entity);
          
          if ((entity instanceof EntityLiving)) {
            EnchantmentManager.a((EntityLiving)entity, this);
          }
          
          EnchantmentManager.b(this, entity);
          ItemStack itemstack = bZ();
          Object object = entity;
          
          if ((entity instanceof EntityComplexPart)) {
            IComplex icomplex = ((EntityComplexPart)entity).owner;
            
            if ((icomplex instanceof EntityLiving)) {
              object = (EntityLiving)icomplex;
            }
          }
          
          if ((itemstack != null) && ((object instanceof EntityLiving))) {
            itemstack.a((EntityLiving)object, this);
            
            if (itemstack.count == 0) {
              ca();
            }
          }
          
          if ((entity instanceof EntityLiving)) {
            a(StatisticList.w, Math.round(f * 10.0F));
            
            if (j > 0) {
              EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), j * 4);
              Bukkit.getPluginManager().callEvent(combustEvent);
              
              if (!combustEvent.isCancelled()) {
                entity.setOnFire(combustEvent.getDuration());
              }
            }
          }
          
          applyExhaustion(this.world.spigotConfig.combatExhaustion);
        } else if (flag1) {
          entity.extinguish();
        }
      }
    }
  }
  
  public void p(Entity entity) {
    if ((entity instanceof EntityLiving)) {
      this.lastDamager = ((EntityLiving)entity);
    } else {
      this.lastDamager = null;
    }
    
    this.bl = this.ticksLived;
  }
  
  public ItemStack bZ() {
    return this.inventory.getItemInHand();
  }
  
  public void b(Statistic statistic) {
    a(statistic, 1);
  }
  
  public void ca() {
    this.inventory.setItem(this.inventory.itemInHandIndex, null);
  }
  
  public void b(Entity entity) {}
  
  public void c(Entity entity) {}
  
  public void die() {
    super.die();
    this.defaultContainer.b(this);
    if (this.activeContainer != null) {
      this.activeContainer.b(this);
    }
  }
  
  public boolean inBlock() {
    return (!this.sleeping) && (super.inBlock());
  }
  
  public GameProfile getProfile() {
    return this.i;
  }
  
  public EnumBedResult a(int i, int j, int k) {
    if (!this.world.isStatic) {
      if ((isSleeping()) || (!isAlive())) {
        return EnumBedResult.OTHER_PROBLEM;
      }
      
      if (!this.world.worldProvider.d()) {
        return EnumBedResult.NOT_POSSIBLE_HERE;
      }
      
      if (this.world.w()) {
        return EnumBedResult.NOT_POSSIBLE_NOW;
      }
      
      if ((Math.abs(this.locX - i) > 3.0D) || (Math.abs(this.locY - j) > 2.0D) || (Math.abs(this.locZ - k) > 3.0D)) {
        return EnumBedResult.TOO_FAR_AWAY;
      }
      
      double d0 = 8.0D;
      double d1 = 5.0D;
      List list = this.world.a(EntityMonster.class, AxisAlignedBB.a(i - d0, j - d1, k - d0, i + d0, j + d1, k + d0));
      
      if (!list.isEmpty()) {
        return EnumBedResult.NOT_SAFE;
      }
    }
    
    if (am()) {
      mount((Entity)null);
    }
    

    if ((getBukkitEntity() instanceof Player)) {
      Player player = (Player)getBukkitEntity();
      org.bukkit.block.Block bed = this.world.getWorld().getBlockAt(i, j, k);
      
      org.bukkit.event.player.PlayerBedEnterEvent event = new org.bukkit.event.player.PlayerBedEnterEvent(player, bed);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (event.isCancelled()) {
        return EnumBedResult.OTHER_PROBLEM;
      }
    }
    

    a(0.2F, 0.2F);
    this.height = 0.2F;
    if (this.world.isLoaded(i, j, k)) {
      int l = this.world.getData(i, j, k);
      int i1 = BlockBed.l(l);
      float f = 0.5F;
      float f1 = 0.5F;
      
      switch (i1) {
      case 0: 
        f1 = 0.9F;
        break;
      
      case 1: 
        f = 0.1F;
        break;
      
      case 2: 
        f1 = 0.1F;
        break;
      
      case 3: 
        f = 0.9F;
      }
      
      w(i1);
      setPosition(i + f, j + 0.9375F, k + f1);
    } else {
      setPosition(i + 0.5F, j + 0.9375F, k + 0.5F);
    }
    
    this.sleeping = true;
    this.sleepTicks = 0;
    this.bB = new ChunkCoordinates(i, j, k);
    this.motX = (this.motZ = this.motY = 0.0D);
    if (!this.world.isStatic) {
      this.world.everyoneSleeping();
    }
    
    return EnumBedResult.OK;
  }
  
  private void w(int i) {
    this.bC = 0.0F;
    this.bD = 0.0F;
    switch (i) {
    case 0: 
      this.bD = -1.8F;
      break;
    
    case 1: 
      this.bC = 1.8F;
      break;
    
    case 2: 
      this.bD = 1.8F;
      break;
    
    case 3: 
      this.bC = -1.8F;
    }
  }
  
  public void a(boolean flag, boolean flag1, boolean flag2) {
    a(0.6F, 1.8F);
    e_();
    ChunkCoordinates chunkcoordinates = this.bB;
    ChunkCoordinates chunkcoordinates1 = this.bB;
    
    if ((chunkcoordinates != null) && (this.world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Blocks.BED)) {
      BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, false);
      chunkcoordinates1 = BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
      if (chunkcoordinates1 == null) {
        chunkcoordinates1 = new ChunkCoordinates(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
      }
      
      setPosition(chunkcoordinates1.x + 0.5F, chunkcoordinates1.y + this.height + 0.1F, chunkcoordinates1.z + 0.5F);
    }
    
    this.sleeping = false;
    if ((!this.world.isStatic) && (flag1)) {
      this.world.everyoneSleeping();
    }
    

    if ((getBukkitEntity() instanceof Player)) {
      Player player = (Player)getBukkitEntity();
      org.bukkit.block.Block bed;
      org.bukkit.block.Block bed;
      if (chunkcoordinates != null) {
        bed = this.world.getWorld().getBlockAt(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
      } else {
        bed = this.world.getWorld().getBlockAt(player.getLocation());
      }
      
      org.bukkit.event.player.PlayerBedLeaveEvent event = new org.bukkit.event.player.PlayerBedLeaveEvent(player, bed);
      this.world.getServer().getPluginManager().callEvent(event);
    }
    

    if (flag) {
      this.sleepTicks = 0;
    } else {
      this.sleepTicks = 100;
    }
    
    if (flag2) {
      setRespawnPosition(this.bB, false);
    }
  }
  
  private boolean j() {
    return this.world.getType(this.bB.x, this.bB.y, this.bB.z) == Blocks.BED;
  }
  
  public static ChunkCoordinates getBed(World world, ChunkCoordinates chunkcoordinates, boolean flag) {
    IChunkProvider ichunkprovider = world.L();
    
    ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z - 3 >> 4);
    ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z - 3 >> 4);
    ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z + 3 >> 4);
    ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z + 3 >> 4);
    if (world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Blocks.BED) {
      ChunkCoordinates chunkcoordinates1 = BlockBed.a(world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
      
      return chunkcoordinates1;
    }
    Material material = world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z).getMaterial();
    Material material1 = world.getType(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z).getMaterial();
    boolean flag1 = (!material.isBuildable()) && (!material.isLiquid());
    boolean flag2 = (!material1.isBuildable()) && (!material1.isLiquid());
    
    return (flag) && (flag1) && (flag2) ? chunkcoordinates : null;
  }
  
  public boolean isSleeping()
  {
    return this.sleeping;
  }
  
  public boolean isDeeplySleeping() {
    return (this.sleeping) && (this.sleepTicks >= 100);
  }
  
  protected void b(int i, boolean flag, int version)
  {
    ProtocolData.DualByte db = this.datawatcher.getDualByte(16);
    byte b0 = version >= 16 ? db.value2 : db.value;
    if (flag) {
      b0 = (byte)(b0 | 1 << i);
    } else {
      b0 = (byte)(b0 & (1 << i ^ 0xFFFFFFFF));
    }
    if (version >= 16) {
      db.value2 = b0;
    } else {
      db.value = b0;
    }
    this.datawatcher.watch(16, db);
  }
  
  public void b(IChatBaseComponent ichatbasecomponent) {}
  
  public ChunkCoordinates getBed()
  {
    return this.c;
  }
  
  public boolean isRespawnForced() {
    return this.d;
  }
  
  public void setRespawnPosition(ChunkCoordinates chunkcoordinates, boolean flag) {
    if (chunkcoordinates != null) {
      this.c = new ChunkCoordinates(chunkcoordinates);
      this.d = flag;
      this.spawnWorld = this.world.worldData.getName();
    } else {
      this.c = null;
      this.d = false;
      this.spawnWorld = "";
    }
  }
  
  public void a(Statistic statistic) {
    a(statistic, 1);
  }
  
  public void a(Statistic statistic, int i) {}
  
  public void bj() {
    super.bj();
    a(StatisticList.r, 1);
    if (isSprinting()) {
      applyExhaustion(this.world.spigotConfig.sprintExhaustion);
    } else {
      applyExhaustion(this.world.spigotConfig.walkExhaustion);
    }
  }
  
  public void e(float f, float f1) {
    double d0 = this.locX;
    double d1 = this.locY;
    double d2 = this.locZ;
    
    if ((this.abilities.isFlying) && (this.vehicle == null)) {
      double d3 = this.motY;
      float f2 = this.aQ;
      
      this.aQ = this.abilities.a();
      super.e(f, f1);
      this.motY = (d3 * 0.6D);
      this.aQ = f2;
    } else {
      super.e(f, f1);
    }
    
    checkMovement(this.locX - d0, this.locY - d1, this.locZ - d2);
  }
  
  public float bl() {
    return (float)getAttributeInstance(GenericAttributes.d).getValue();
  }
  
  public void checkMovement(double d0, double d1, double d2) {
    if (this.vehicle == null)
    {

      if (a(Material.WATER)) {
        int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
        if (i > 0) {
          a(StatisticList.m, i);
          applyExhaustion(this.world.paperSpigotConfig.playerSwimmingExhaustion * i * 0.01F);
        }
      } else if (M()) {
        int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
        if (i > 0) {
          a(StatisticList.i, i);
          applyExhaustion(this.world.paperSpigotConfig.playerSwimmingExhaustion * i * 0.01F);
        }
      } else if (h_()) {
        if (d1 > 0.0D) {
          a(StatisticList.k, (int)Math.round(d1 * 100.0D));
        }
      } else if (this.onGround) {
        int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
        if (i > 0) {
          a(StatisticList.h, i);
          if (isSprinting()) {
            applyExhaustion(0.099999994F * i * 0.01F);
          } else {
            applyExhaustion(0.01F * i * 0.01F);
          }
        }
      } else {
        int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
        if (i > 25) {
          a(StatisticList.l, i);
        }
      }
    }
  }
  
  private void l(double d0, double d1, double d2) {
    if (this.vehicle != null) {
      int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
      
      if (i > 0) {
        if ((this.vehicle instanceof EntityMinecartAbstract)) {
          a(StatisticList.n, i);
          if (this.e == null) {
            this.e = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
          } else if (this.e.e(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000000.0D) {
            a(AchievementList.q, 1);
          }
        } else if ((this.vehicle instanceof EntityBoat)) {
          a(StatisticList.o, i);
        } else if ((this.vehicle instanceof EntityPig)) {
          a(StatisticList.p, i);
        } else if ((this.vehicle instanceof EntityHorse)) {
          a(StatisticList.q, i);
        }
      }
    }
  }
  
  protected void b(float f) {
    if (!this.abilities.canFly) {
      if (f >= 2.0F) {
        a(StatisticList.j, (int)Math.round(f * 100.0D));
      }
      
      super.b(f);
    }
  }
  
  protected String o(int i) {
    return i > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
  }
  
  public void a(EntityLiving entityliving) {
    if ((entityliving instanceof IMonster)) {
      a(AchievementList.s);
    }
    
    int i = EntityTypes.a(entityliving);
    MonsterEggInfo monsteregginfo = (MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(i));
    
    if (monsteregginfo != null) {
      a(monsteregginfo.killEntityStatistic, 1);
    }
  }
  
  public void as() {
    if (!this.abilities.isFlying) {
      super.as();
    }
  }
  
  public ItemStack r(int i) {
    return this.inventory.d(i);
  }
  
  public void giveExp(int i) {
    addScore(i);
    int j = Integer.MAX_VALUE - this.expTotal;
    
    if (i > j) {
      i = j;
    }
    
    this.exp += i / getExpToLevel();
    
    for (this.expTotal += i; this.exp >= 1.0F; this.exp /= getExpToLevel()) {
      this.exp = ((this.exp - 1.0F) * getExpToLevel());
      levelDown(1);
    }
  }
  
  public void levelDown(int i) {
    this.expLevel += i;
    if (this.expLevel < 0) {
      this.expLevel = 0;
      this.exp = 0.0F;
      this.expTotal = 0;
    }
    
    if ((i > 0) && (this.expLevel % 5 == 0) && (this.h < this.ticksLived - 100.0F)) {
      float f = this.expLevel > 30 ? 1.0F : this.expLevel / 30.0F;
      
      this.world.makeSound(this, "random.levelup", f * 0.75F, 1.0F);
      this.h = this.ticksLived;
    }
  }
  
  public int getExpToLevel() {
    return this.expLevel >= 15 ? 17 + (this.expLevel - 15) * 3 : this.expLevel >= 30 ? 62 + (this.expLevel - 30) * 7 : 17;
  }
  
  public void applyExhaustion(float f) {
    if ((!this.abilities.isInvulnerable) && 
      (!this.world.isStatic)) {
      this.foodData.a(f);
    }
  }
  
  public FoodMetaData getFoodData()
  {
    return this.foodData;
  }
  
  public boolean g(boolean flag) {
    return ((flag) || (this.foodData.c())) && (!this.abilities.isInvulnerable);
  }
  
  public boolean bR() {
    return (getHealth() > 0.0F) && (getHealth() < getMaxHealth());
  }
  
  public void a(ItemStack itemstack, int i) {
    if (itemstack != this.f) {
      this.f = itemstack;
      this.g = i;
      if (!this.world.isStatic) {
        e(true);
      }
    }
  }
  
  public boolean d(int i, int j, int k) {
    if (this.abilities.mayBuild) {
      return true;
    }
    Block block = this.world.getType(i, j, k);
    
    if (block.getMaterial() != Material.AIR) {
      if (block.getMaterial().q()) {
        return true;
      }
      
      if (bF() != null) {
        ItemStack itemstack = bF();
        
        if ((itemstack.b(block)) || (itemstack.a(block) > 1.0F)) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean a(int i, int j, int k, int l, ItemStack itemstack)
  {
    return this.abilities.mayBuild;
  }
  
  protected int getExpValue(EntityHuman entityhuman) {
    if (this.world.getGameRules().getBoolean("keepInventory")) {
      return 0;
    }
    int i = this.expLevel * 7;
    
    return i > 100 ? 100 : i;
  }
  
  protected boolean alwaysGivesExp()
  {
    return true;
  }
  
  public void copyTo(EntityHuman entityhuman, boolean flag) {
    if (flag) {
      this.inventory.b(entityhuman.inventory);
      setHealth(entityhuman.getHealth());
      this.foodData = entityhuman.foodData;
      this.expLevel = entityhuman.expLevel;
      this.expTotal = entityhuman.expTotal;
      this.exp = entityhuman.exp;
      setScore(entityhuman.getScore());
      this.aq = entityhuman.aq;
    } else if (this.world.getGameRules().getBoolean("keepInventory")) {
      this.inventory.b(entityhuman.inventory);
      this.expLevel = entityhuman.expLevel;
      this.expTotal = entityhuman.expTotal;
      this.exp = entityhuman.exp;
      setScore(entityhuman.getScore());
    }
    
    this.enderChest = entityhuman.enderChest;
  }
  
  protected boolean g_() {
    return !this.abilities.isFlying;
  }
  
  public void updateAbilities() {}
  
  public void a(EnumGamemode enumgamemode) {}
  
  public String getName() {
    return this.i.getName();
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public InventoryEnderChest getEnderChest() {
    return this.enderChest;
  }
  
  public ItemStack getEquipment(int i) {
    return i == 0 ? this.inventory.getItemInHand() : this.inventory.armor[(i - 1)];
  }
  
  public ItemStack be() {
    return this.inventory.getItemInHand();
  }
  
  public void setEquipment(int i, ItemStack itemstack) {
    ItemStack previous = this.inventory.armor[i];
    if (!net.minecraft.util.com.google.common.base.Objects.equal(itemstack, previous))
    {
      if (previous != null) {
        previous = previous.cloneItemStack();
      }
      this.inventory.armor[i] = itemstack;
      Bukkit.getPluginManager().callEvent(new org.bukkit.event.inventory.EquipmentSetEvent(getBukkitEntity(), i, CraftItemStack.asBukkitCopy(itemstack), CraftItemStack.asBukkitCopy(previous)));
    }
  }
  
  public ItemStack[] getEquipment() {
    return this.inventory.armor;
  }
  
  public boolean aC() {
    return !this.abilities.isFlying;
  }
  
  public Scoreboard getScoreboard() {
    return this.world.getScoreboard();
  }
  
  public ScoreboardTeamBase getScoreboardTeam() {
    return getScoreboard().getPlayerTeam(getName());
  }
  
  public IChatBaseComponent getScoreboardDisplayName()
  {
    ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(getScoreboardTeam(), getName()));
    
    chatcomponenttext.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/msg " + getName() + " "));
    return chatcomponenttext;
  }
  
  public void setAbsorptionHearts(float f) {
    if (f < 0.0F) {
      f = 0.0F;
    }
    
    getDataWatcher().watch(17, Float.valueOf(f));
  }
  
  public float getAbsorptionHearts() {
    return getDataWatcher().getFloat(17);
  }
  
  public static UUID a(GameProfile gameprofile) {
    UUID uuid = gameprofile.getId();
    
    if (uuid == null) {
      uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(net.minecraft.util.com.google.common.base.Charsets.UTF_8));
    }
    
    return uuid;
  }
}
