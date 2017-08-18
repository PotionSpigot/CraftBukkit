package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

public class EntityLeash extends EntityHanging
{
  public EntityLeash(World world)
  {
    super(world);
  }
  
  public EntityLeash(World world, int i, int j, int k) {
    super(world, i, j, k, 0);
    setPosition(i + 0.5D, j + 0.5D, k + 0.5D);
  }
  
  protected void c() {
    super.c();
  }
  
  public void setDirection(int i) {}
  
  public int f() {
    return 9;
  }
  
  public int i() {
    return 9;
  }
  
  public void b(Entity entity) {}
  
  public boolean d(NBTTagCompound nbttagcompound) {
    return false;
  }
  
  public void b(NBTTagCompound nbttagcompound) {}
  
  public void a(NBTTagCompound nbttagcompound) {}
  
  public boolean c(EntityHuman entityhuman) {
    ItemStack itemstack = entityhuman.be();
    boolean flag = false;
    




    if ((itemstack != null) && (itemstack.getItem() == Items.LEASH) && (!this.world.isStatic)) {
      double d0 = 7.0D;
      List list = this.world.a(EntityInsentient.class, AxisAlignedBB.a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
      if (list != null) {
        Iterator iterator = list.iterator();
        
        while (iterator.hasNext()) {
          EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
          if ((entityinsentient.bN()) && (entityinsentient.getLeashHolder() == entityhuman))
          {
            if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, this, entityhuman).isCancelled()) {
              ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, entityinsentient, entityinsentient.getLeashHolder()));
            }
            else
            {
              entityinsentient.setLeashHolder(this, true);
              flag = true;
            }
          }
        }
      }
    }
    if ((!this.world.isStatic) && (!flag))
    {

      boolean die = true;
      

      double d0 = 7.0D;
      List list = this.world.a(EntityInsentient.class, AxisAlignedBB.a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
      if (list != null) {
        Iterator iterator = list.iterator();
        
        while (iterator.hasNext()) {
          EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
          if ((entityinsentient.bN()) && (entityinsentient.getLeashHolder() == this))
          {
            if (CraftEventFactory.callPlayerUnleashEntityEvent(entityinsentient, entityhuman).isCancelled()) {
              die = false;
            }
            else {
              entityinsentient.unleash(true, !entityhuman.abilities.canInstantlyBuild);
            }
          }
        }
      }
      

      if (die) {
        die();
      }
    }
    

    return true;
  }
  
  public boolean survives() {
    return this.world.getType(this.x, this.y, this.z).b() == 11;
  }
  
  public static EntityLeash a(World world, int i, int j, int k) {
    EntityLeash entityleash = new EntityLeash(world, i, j, k);
    
    entityleash.attachedToPlayer = true;
    world.addEntity(entityleash);
    return entityleash;
  }
  
  public static EntityLeash b(World world, int i, int j, int k) {
    List list = world.a(EntityLeash.class, AxisAlignedBB.a(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D));
    
    if (list != null) {
      Iterator iterator = list.iterator();
      
      while (iterator.hasNext()) {
        EntityLeash entityleash = (EntityLeash)iterator.next();
        
        if ((entityleash.x == i) && (entityleash.y == j) && (entityleash.z == k)) {
          return entityleash;
        }
      }
    }
    
    return null;
  }
}
