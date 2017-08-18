package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;


public class ContainerBrewingStand
  extends Container
{
  private TileEntityBrewingStand brewingStand;
  private final Slot f;
  private int g;
  private CraftInventoryView bukkitEntity = null;
  private PlayerInventory player;
  
  public ContainerBrewingStand(PlayerInventory playerinventory, TileEntityBrewingStand tileentitybrewingstand)
  {
    this.player = playerinventory;
    this.brewingStand = tileentitybrewingstand;
    a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 0, 56, 46));
    a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 1, 79, 53));
    a(new SlotPotionBottle(playerinventory.player, tileentitybrewingstand, 2, 102, 46));
    this.f = a(new SlotBrewing(this, tileentitybrewingstand, 3, 79, 17));
    


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
    
    for (i = 0; i < 9; i++) {
      a(new Slot(playerinventory, i, 8 + i * 18, 142));
    }
  }
  
  public void addSlotListener(ICrafting icrafting) {
    super.addSlotListener(icrafting);
    icrafting.setContainerData(this, 0, this.brewingStand.i());
  }
  
  public void b() {
    super.b();
    
    for (int i = 0; i < this.listeners.size(); i++) {
      ICrafting icrafting = (ICrafting)this.listeners.get(i);
      
      if (this.g != this.brewingStand.i()) {
        icrafting.setContainerData(this, 0, this.brewingStand.i());
      }
    }
    
    this.g = this.brewingStand.i();
  }
  
  public boolean a(EntityHuman entityhuman) {
    if (!this.checkReachable) return true;
    return this.brewingStand.a(entityhuman);
  }
  
  public ItemStack b(EntityHuman entityhuman, int i) {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.c.get(i);
    
    if ((slot != null) && (slot.hasItem())) {
      ItemStack itemstack1 = slot.getItem();
      
      itemstack = itemstack1.cloneItemStack();
      if (((i < 0) || (i > 2)) && (i != 3)) {
        if ((!this.f.hasItem()) && (this.f.isAllowed(itemstack1))) {
          if (!a(itemstack1, 3, 4, false)) {
            return null;
          }
        } else if (SlotPotionBottle.b_(itemstack)) {
          if (!a(itemstack1, 0, 3, false)) {
            return null;
          }
        } else if ((i >= 4) && (i < 31)) {
          if (!a(itemstack1, 31, 40, false)) {
            return null;
          }
        } else if ((i >= 31) && (i < 40)) {
          if (!a(itemstack1, 4, 31, false)) {
            return null;
          }
        } else if (!a(itemstack1, 4, 40, false)) {
          return null;
        }
      } else {
        if (!a(itemstack1, 4, 40, true)) {
          return null;
        }
        
        slot.a(itemstack1, itemstack);
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
    
    CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.brewingStand);
    this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    return this.bukkitEntity;
  }
}
