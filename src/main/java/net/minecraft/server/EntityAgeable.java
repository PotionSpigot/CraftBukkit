package net.minecraft.server;

import org.spigotmc.ProtocolData.IntByte;

public abstract class EntityAgeable extends EntityCreature { private float bp = -1.0F;
  private float bq;
  public boolean ageLocked = false;
  


  public void inactiveTick()
  {
    super.inactiveTick();
    if ((this.world.isStatic) || (this.ageLocked))
    {
      a(isBaby());
    }
    else {
      int i = getAge();
      
      if (i < 0)
      {
        i++;
        setAge(i);
      } else if (i > 0)
      {
        i--;
        setAge(i);
      }
    }
  }
  
  public EntityAgeable(World world)
  {
    super(world);
  }
  
  public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
  
  public boolean a(EntityHuman entityhuman) {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && (itemstack.getItem() == Items.MONSTER_EGG)) {
      if (!this.world.isStatic) {
        Class oclass = EntityTypes.a(itemstack.getData());
        
        if ((oclass != null) && (oclass.isAssignableFrom(getClass()))) {
          EntityAgeable entityageable = createChild(this);
          
          if (entityageable != null) {
            entityageable.setAge(41536);
            entityageable.setPositionRotation(this.locX, this.locY, this.locZ, 0.0F, 0.0F);
            this.world.addEntity(entityageable, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
            if (itemstack.hasName()) {
              entityageable.setCustomName(itemstack.getName());
            }
            
            if (!entityhuman.abilities.canInstantlyBuild) {
              itemstack.count -= 1;
              if (itemstack.count == 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
              }
            }
          }
        }
      }
      
      return true;
    }
    return false;
  }
  
  protected void c()
  {
    super.c();
    this.datawatcher.a(12, new ProtocolData.IntByte(0, (byte)0));
  }
  
  public int getAge() {
    return this.datawatcher.getIntByte(12).value;
  }
  
  public void a(int i) {
    int j = getAge();
    
    j += i * 20;
    if (j > 0) {
      j = 0;
    }
    
    setAge(j);
  }
  
  public void setAge(int i) {
    this.datawatcher.watch(12, new ProtocolData.IntByte(i, (byte)(i >= 6000 ? 1 : i < 0 ? -1 : 0)));
    a(isBaby());
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setInt("Age", getAge());
    nbttagcompound.setBoolean("AgeLocked", this.ageLocked);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    setAge(nbttagcompound.getInt("Age"));
    this.ageLocked = nbttagcompound.getBoolean("AgeLocked");
  }
  
  public void e() {
    super.e();
    if ((this.world.isStatic) || (this.ageLocked)) {
      a(isBaby());
    } else {
      int i = getAge();
      
      if (i < 0) {
        i++;
        setAge(i);
      } else if (i > 0) {
        i--;
        setAge(i);
      }
    }
  }
  
  public boolean isBaby() {
    return getAge() < 0;
  }
  
  public void a(boolean flag) {
    a(flag ? 0.5F : 1.0F);
  }
  
  protected final void a(float f, float f1) {
    boolean flag = this.bp > 0.0F;
    
    this.bp = f;
    this.bq = f1;
    if (!flag) {
      a(1.0F);
    }
  }
  
  protected final void a(float f) {
    super.a(this.bp * f, this.bq * f);
  }
}
