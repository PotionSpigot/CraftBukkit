package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.spigotmc.SpigotWorldConfig;

public class EntityItem extends Entity
{
  private static final org.apache.logging.log4j.Logger d = ;
  public int age;
  public int pickupDelay;
  private int e;
  private String f;
  private String g;
  public float c;
  private int lastTick = MinecraftServer.currentTick;
  
  public EntityItem(World world, double d0, double d1, double d2) {
    super(world);
    this.e = 5;
    this.c = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    a(0.25F, 0.25F);
    this.height = (this.length / 2.0F);
    setPosition(d0, d1, d2);
    this.yaw = ((float)(Math.random() * 360.0D));
    this.motX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    this.motY = 0.20000000298023224D;
    this.motZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
  }
  
  public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
    this(world, d0, d1, d2);
    
    if ((itemstack == null) || (itemstack.getItem() == null)) {
      return;
    }
    
    setItemStack(itemstack);
  }
  
  protected boolean g_() {
    return false;
  }
  
  public EntityItem(World world) {
    super(world);
    this.e = 5;
    this.c = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    a(0.25F, 0.25F);
    this.height = (this.length / 2.0F);
  }
  
  protected void c() {
    getDataWatcher().add(10, 5);
  }
  
  public void h() {
    if (getItemStack() == null) {
      die();
    } else {
      super.h();
      
      int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
      this.pickupDelay -= elapsedTicks;
      this.age += elapsedTicks;
      this.lastTick = MinecraftServer.currentTick;
      

      this.lastX = this.locX;
      this.lastY = this.locY;
      this.lastZ = this.locZ;
      this.motY -= 0.03999999910593033D;
      this.X = j(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
      move(this.motX, this.motY, this.motZ);
      boolean flag = ((int)this.lastX != (int)this.locX) || ((int)this.lastY != (int)this.locY) || ((int)this.lastZ != (int)this.locZ);
      
      if ((flag) || (this.ticksLived % 25 == 0)) {
        if (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)).getMaterial() == Material.LAVA) {
          this.motY = 0.20000000298023224D;
          this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
          this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
          makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }
        
        if (!this.world.isStatic) {
          k();
        }
      }
      
      float f = 0.98F;
      
      if (this.onGround) {
        f = this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ)).frictionFactor * 0.98F;
      }
      
      this.motX *= f;
      this.motY *= 0.9800000190734863D;
      this.motZ *= f;
      if (this.onGround) {
        this.motY *= -0.5D;
      }
      

      if (this.world.spigotConfig.altHopperTicking) {
        int xi = MathHelper.floor(this.boundingBox.a);
        int yi = MathHelper.floor(this.boundingBox.b) - 1;
        int zi = MathHelper.floor(this.boundingBox.c);
        int xf = MathHelper.floor(this.boundingBox.d);
        int yf = MathHelper.floor(this.boundingBox.e) - 1;
        int zf = MathHelper.floor(this.boundingBox.f);
        for (int a = xi; a <= xf; a++) {
          for (int c = zi; c <= zf; c++) {
            for (int b = yi; b <= yf; b++) {
              TileEntity tileEntity = this.world.getTileEntity(a, b, c);
              if ((tileEntity instanceof TileEntityHopper)) {
                ((TileEntityHopper)tileEntity).makeTick();
              }
            }
          }
        }
      }
      


      if ((!this.world.isStatic) && (this.age >= this.world.spigotConfig.itemDespawnRate))
      {
        if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
          this.age = 0;
          return;
        }
        
        die();
      }
    }
  }
  


  public void inactiveTick()
  {
    int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
    this.pickupDelay -= elapsedTicks;
    this.age += elapsedTicks;
    this.lastTick = MinecraftServer.currentTick;
    

    if ((!this.world.isStatic) && (this.age >= this.world.spigotConfig.itemDespawnRate))
    {
      if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
        this.age = 0;
        return;
      }
      
      die();
    }
  }
  

  private void k()
  {
    double radius = this.world.spigotConfig.itemMerge;
    Iterator iterator = this.world.a(EntityItem.class, this.boundingBox.grow(radius, radius, radius)).iterator();
    

    while (iterator.hasNext()) {
      EntityItem entityitem = (EntityItem)iterator.next();
      
      a(entityitem);
    }
  }
  
  public boolean a(EntityItem entityitem) {
    if (entityitem == this)
      return false;
    if ((entityitem.isAlive()) && (isAlive())) {
      ItemStack itemstack = getItemStack();
      ItemStack itemstack1 = entityitem.getItemStack();
      
      if (itemstack1.getItem() != itemstack.getItem())
        return false;
      if ((itemstack1.hasTag() ^ itemstack.hasTag()))
        return false;
      if ((itemstack1.hasTag()) && (!itemstack1.getTag().equals(itemstack.getTag())))
        return false;
      if (itemstack1.getItem() == null)
        return false;
      if ((itemstack1.getItem().n()) && (itemstack1.getData() != itemstack.getData()))
        return false;
      if (itemstack1.count < itemstack.count)
        return entityitem.a(this);
      if (itemstack1.count + itemstack.count > itemstack1.getMaxStackSize()) {
        return false;
      }
      
      itemstack.count += itemstack1.count;
      this.pickupDelay = Math.max(entityitem.pickupDelay, this.pickupDelay);
      this.age = Math.min(entityitem.age, this.age);
      setItemStack(itemstack);
      entityitem.die();
      
      return true;
    }
    
    return false;
  }
  
  public void e()
  {
    this.age = 4800;
  }
  
  public boolean N() {
    return this.world.a(this.boundingBox, Material.WATER, this);
  }
  
  protected void burn(int i) {
    damageEntity(DamageSource.FIRE, i);
  }
  
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (isInvulnerable())
      return false;
    if ((getItemStack() != null) && (getItemStack().getItem() == Items.NETHER_STAR) && (damagesource.isExplosion())) {
      return false;
    }
    Q();
    this.e = ((int)(this.e - f));
    if (this.e <= 0) {
      die();
    }
    
    return false;
  }
  
  public void b(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setShort("Health", (short)(byte)this.e);
    nbttagcompound.setShort("Age", (short)this.age);
    if (j() != null) {
      nbttagcompound.setString("Thrower", this.f);
    }
    
    if (i() != null) {
      nbttagcompound.setString("Owner", this.g);
    }
    
    if (getItemStack() != null) {
      nbttagcompound.set("Item", getItemStack().save(new NBTTagCompound()));
    }
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.e = (nbttagcompound.getShort("Health") & 0xFF);
    this.age = nbttagcompound.getShort("Age");
    if (nbttagcompound.hasKey("Owner")) {
      this.g = nbttagcompound.getString("Owner");
    }
    
    if (nbttagcompound.hasKey("Thrower")) {
      this.f = nbttagcompound.getString("Thrower");
    }
    
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
    

    if (nbttagcompound1 != null) {
      ItemStack itemstack = ItemStack.createStack(nbttagcompound1);
      if (itemstack != null) {
        setItemStack(itemstack);
      } else {
        die();
      }
    } else {
      die();
    }
    
    if (getItemStack() == null) {
      die();
    }
  }
  
  public void b_(EntityHuman entityhuman) {
    if (!this.world.isStatic) {
      ItemStack itemstack = getItemStack();
      int i = itemstack.count;
      

      int canHold = entityhuman.inventory.canHold(itemstack);
      int remaining = itemstack.count - canHold;
      
      if ((this.pickupDelay <= 0) && (canHold > 0)) {
        itemstack.count = canHold;
        PlayerPickupItemEvent event = new PlayerPickupItemEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), (org.bukkit.entity.Item)getBukkitEntity(), remaining);
        
        this.world.getServer().getPluginManager().callEvent(event);
        itemstack.count = (canHold + remaining);
        
        if (event.isCancelled()) {
          return;
        }
        

        this.pickupDelay = 0;
      }
      

      if ((this.pickupDelay == 0) && ((this.g == null) || (6000 - this.age <= 200) || (this.g.equals(entityhuman.getName()))) && (entityhuman.inventory.pickup(itemstack))) {
        if (itemstack.getItem() == Item.getItemOf(Blocks.LOG)) {
          entityhuman.a(AchievementList.g);
        }
        
        if (itemstack.getItem() == Item.getItemOf(Blocks.LOG2)) {
          entityhuman.a(AchievementList.g);
        }
        
        if (itemstack.getItem() == Items.LEATHER) {
          entityhuman.a(AchievementList.t);
        }
        
        if (itemstack.getItem() == Items.DIAMOND) {
          entityhuman.a(AchievementList.w);
        }
        
        if (itemstack.getItem() == Items.BLAZE_ROD) {
          entityhuman.a(AchievementList.A);
        }
        
        if ((itemstack.getItem() == Items.DIAMOND) && (j() != null)) {
          EntityHuman entityhuman1 = this.world.a(j());
          
          if ((entityhuman1 != null) && (entityhuman1 != entityhuman)) {
            entityhuman1.a(AchievementList.x);
          }
        }
        
        this.world.makeSound(entityhuman, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        entityhuman.receive(this, i);
        if (itemstack.count <= 0) {
          die();
        }
      }
    }
  }
  
  public String getName() {
    return LocaleI18n.get("item." + getItemStack().a());
  }
  
  public boolean av() {
    return false;
  }
  
  public void b(int i) {
    super.b(i);
    if (!this.world.isStatic) {
      k();
    }
  }
  
  public ItemStack getItemStack() {
    ItemStack itemstack = getDataWatcher().getItemStack(10);
    
    return itemstack == null ? new ItemStack(Blocks.STONE) : itemstack;
  }
  
  public void setItemStack(ItemStack itemstack) {
    getDataWatcher().watch(10, itemstack);
    getDataWatcher().update(10);
  }
  
  public String i() {
    return this.g;
  }
  
  public void a(String s) {
    this.g = s;
  }
  
  public String j() {
    return this.f;
  }
  
  public void b(String s) {
    this.f = s;
  }
}
