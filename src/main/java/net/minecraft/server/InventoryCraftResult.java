package net.minecraft.server;

import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCraftResult implements IInventory
{
  private ItemStack[] items = new ItemStack[1];
  

  private int maxStack = 64;
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public InventoryHolder getOwner() {
    return null;
  }
  
  public void onOpen(CraftHumanEntity who) {}
  
  public void onClose(CraftHumanEntity who) {}
  
  public java.util.List<HumanEntity> getViewers() { return new ArrayList(); }
  
  public void setMaxStackSize(int size)
  {
    this.maxStack = size;
  }
  


  public int getSize()
  {
    return 1;
  }
  
  public ItemStack getItem(int i) {
    return this.items[0];
  }
  
  public String getInventoryName() {
    return "Result";
  }
  
  public boolean k_() {
    return false;
  }
  
  public ItemStack splitStack(int i, int j) {
    if (this.items[0] != null) {
      ItemStack itemstack = this.items[0];
      
      this.items[0] = null;
      return itemstack;
    }
    return null;
  }
  
  public ItemStack splitWithoutUpdate(int i)
  {
    if (this.items[0] != null) {
      ItemStack itemstack = this.items[0];
      
      this.items[0] = null;
      return itemstack;
    }
    return null;
  }
  
  public void setItem(int i, ItemStack itemstack)
  {
    this.items[0] = itemstack;
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public void update() {}
  
  public boolean a(EntityHuman entityhuman) {
    return true;
  }
  
  public void startOpen() {}
  
  public void closeContainer() {}
  
  public boolean b(int i, ItemStack itemstack) {
    return true;
  }
}
