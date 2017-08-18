package net.minecraft.server;

import java.util.Random;

public class EntityItemFrame extends EntityHanging { private float e = 1.0F;
  
  public EntityItemFrame(World world) {
    super(world);
  }
  
  public EntityItemFrame(World world, int i, int j, int k, int l) {
    super(world, i, j, k, l);
    setDirection(l);
  }
  
  protected void c() {
    getDataWatcher().add(2, 5);
    getDataWatcher().a(3, Byte.valueOf((byte)0));
    
    getDataWatcher().add(8, 5);
    getDataWatcher().a(9, Byte.valueOf((byte)0));
  }
  
  public boolean damageEntity(DamageSource damagesource, float f)
  {
    if (isInvulnerable())
      return false;
    if (getItem() != null) {
      if (!this.world.isStatic)
      {
        if ((org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f, false)) || (this.dead)) {
          return true;
        }
        

        b(damagesource.getEntity(), false);
        setItem((ItemStack)null);
      }
      
      return true;
    }
    return super.damageEntity(damagesource, f);
  }
  
  public int f()
  {
    return 9;
  }
  
  public int i() {
    return 9;
  }
  
  public void b(Entity entity) {
    b(entity, true);
  }
  
  public void b(Entity entity, boolean flag) {
    ItemStack itemstack = getItem();
    
    if ((entity instanceof EntityHuman)) {
      EntityHuman entityhuman = (EntityHuman)entity;
      
      if (entityhuman.abilities.canInstantlyBuild) {
        b(itemstack);
        return;
      }
    }
    
    if (flag) {
      a(new ItemStack(Items.ITEM_FRAME), 0.0F);
    }
    
    if ((itemstack != null) && (this.random.nextFloat() < this.e)) {
      itemstack = itemstack.cloneItemStack();
      b(itemstack);
      a(itemstack, 0.0F);
    }
  }
  
  private void b(ItemStack itemstack) {
    if (itemstack != null) {
      if (itemstack.getItem() == Items.MAP) {
        WorldMap worldmap = ((ItemWorldMap)itemstack.getItem()).getSavedMap(itemstack, this.world);
        
        worldmap.decorations.remove("frame-" + getId());
      }
      
      itemstack.a((EntityItemFrame)null);
    }
  }
  
  public ItemStack getItem() {
    return getDataWatcher().getItemStack(2);
  }
  
  public void setItem(ItemStack itemstack) {
    if (itemstack != null) {
      itemstack = itemstack.cloneItemStack();
      itemstack.count = 1;
      itemstack.a(this);
    }
    
    getDataWatcher().watch(2, itemstack);
    getDataWatcher().update(2);
    
    getDataWatcher().watch(8, itemstack);
    getDataWatcher().update(8);
  }
  
  public int getRotation()
  {
    return getDataWatcher().getByte(3);
  }
  
  public void setRotation(int i) {
    getDataWatcher().watch(3, Byte.valueOf((byte)(i % 4)));
    getDataWatcher().watch(9, Byte.valueOf((byte)(i % 4 * 2)));
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    if (getItem() != null) {
      nbttagcompound.set("Item", getItem().save(new NBTTagCompound()));
      nbttagcompound.setByte("ItemRotation", (byte)getRotation());
      nbttagcompound.setFloat("ItemDropChance", this.e);
    }
    
    super.b(nbttagcompound);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
    
    if ((nbttagcompound1 != null) && (!nbttagcompound1.isEmpty())) {
      setItem(ItemStack.createStack(nbttagcompound1));
      setRotation(nbttagcompound.getByte("ItemRotation"));
      if (nbttagcompound.hasKeyOfType("ItemDropChance", 99)) {
        this.e = nbttagcompound.getFloat("ItemDropChance");
      }
    }
    
    super.a(nbttagcompound);
  }
  
  public boolean c(EntityHuman entityhuman) {
    if (getItem() == null) {
      ItemStack itemstack = entityhuman.be();
      
      if ((itemstack != null) && (!this.world.isStatic)) {
        setItem(itemstack);
        if (!entityhuman.abilities.canInstantlyBuild) if (--itemstack.count <= 0) {
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
          }
      }
    } else if (!this.world.isStatic) {
      setRotation(getRotation() + 1);
    }
    
    return true;
  }
}
