package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;

public class ContainerBeacon extends Container
{
  private TileEntityBeacon a;
  private final SlotBeacon f;
  private int g;
  private int h;
  private int i;
  private CraftInventoryView bukkitEntity = null;
  private PlayerInventory player;
  
  public ContainerBeacon(PlayerInventory playerinventory, TileEntityBeacon tileentitybeacon)
  {
    this.player = playerinventory;
    this.a = tileentitybeacon;
    a(this.f = new SlotBeacon(this, tileentitybeacon, 0, 136, 110));
    byte b0 = 36;
    short short1 = 137;
    


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        a(new Slot(playerinventory, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
      }
    }
    
    for (i = 0; i < 9; i++) {
      a(new Slot(playerinventory, i, b0 + i * 18, 58 + short1));
    }
    
    this.g = tileentitybeacon.l();
    this.h = tileentitybeacon.j();
    this.i = tileentitybeacon.k();
  }
  
  public void addSlotListener(ICrafting icrafting) {
    super.addSlotListener(icrafting);
    icrafting.setContainerData(this, 0, this.g);
    icrafting.setContainerData(this, 1, this.h);
    icrafting.setContainerData(this, 2, this.i);
  }
  
  public TileEntityBeacon e() {
    return this.a;
  }
  
  public boolean a(EntityHuman entityhuman) {
    if (!this.checkReachable) return true;
    return this.a.a(entityhuman);
  }
  
  public ItemStack b(EntityHuman entityhuman, int i) {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.c.get(i);
    
    if ((slot != null) && (slot.hasItem())) {
      ItemStack itemstack1 = slot.getItem();
      
      itemstack = itemstack1.cloneItemStack();
      if (i == 0) {
        if (!a(itemstack1, 1, 37, true)) {
          return null;
        }
        
        slot.a(itemstack1, itemstack);
      } else if ((!this.f.hasItem()) && (this.f.isAllowed(itemstack1)) && (itemstack1.count == 1)) {
        if (!a(itemstack1, 0, 1, false)) {
          return null;
        }
      } else if ((i >= 1) && (i < 28)) {
        if (!a(itemstack1, 28, 37, false)) {
          return null;
        }
      } else if ((i >= 28) && (i < 37)) {
        if (!a(itemstack1, 1, 28, false)) {
          return null;
        }
      } else if (!a(itemstack1, 1, 37, false)) {
        return null;
      }
      
      if (itemstack1.count == 0) {
        slot.set((ItemStack)null);
      } else {
        slot.f();
      }
      
      if (itemstack1.count == itemstack.count) {
        return null;
      }
      
      slot.a(entityhuman, itemstack1);
    }
    
    return itemstack;
  }
  
  public CraftInventoryView getBukkitView()
  {
    if (this.bukkitEntity != null) {
      return this.bukkitEntity;
    }
    
    CraftInventory inventory = new org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBeacon(this.a);
    this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    return this.bukkitEntity;
  }
}
