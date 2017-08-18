package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;


public class TileEntityDispenser
  extends TileEntity
  implements IInventory
{
  private ItemStack[] items = new ItemStack[9];
  private Random j = new Random();
  
  protected String a;
  
  public List<HumanEntity> transaction = new ArrayList();
  private int maxStack = 64;
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public void onOpen(CraftHumanEntity who) {
    this.transaction.add(who);
  }
  
  public void onClose(CraftHumanEntity who) {
    this.transaction.remove(who);
  }
  
  public List<HumanEntity> getViewers() {
    return this.transaction;
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
  }
  


  public int getSize()
  {
    return 9;
  }
  
  public ItemStack getItem(int i) {
    return this.items[i];
  }
  
  public ItemStack splitStack(int i, int j) {
    if (this.items[i] != null)
    {

      if (this.items[i].count <= j) {
        ItemStack itemstack = this.items[i];
        this.items[i] = null;
        update();
        return itemstack;
      }
      ItemStack itemstack = this.items[i].a(j);
      if (this.items[i].count == 0) {
        this.items[i] = null;
      }
      
      update();
      return itemstack;
    }
    
    return null;
  }
  
  public ItemStack splitWithoutUpdate(int i)
  {
    if (this.items[i] != null) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public int i()
  {
    int i = -1;
    int j = 1;
    
    for (int k = 0; k < this.items.length; k++) {
      if ((this.items[k] != null) && (this.j.nextInt(j++) == 0) && 
        (this.items[k].count != 0)) {
        i = k;
      }
    }
    
    return i;
  }
  
  public void setItem(int i, ItemStack itemstack) {
    this.items[i] = itemstack;
    if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
      itemstack.count = getMaxStackSize();
    }
    
    update();
  }
  
  public int addItem(ItemStack itemstack) {
    for (int i = 0; i < this.items.length; i++) {
      if ((this.items[i] == null) || (this.items[i].getItem() == null)) {
        setItem(i, itemstack);
        return i;
      }
    }
    
    return -1;
  }
  
  public String getInventoryName() {
    return k_() ? this.a : "container.dispenser";
  }
  
  public void a(String s) {
    this.a = s;
  }
  
  public boolean k_() {
    return this.a != null;
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
    
    this.items = new ItemStack[getSize()];
    
    for (int i = 0; i < nbttaglist.size(); i++) {
      NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
      int j = nbttagcompound1.getByte("Slot") & 0xFF;
      
      if ((j >= 0) && (j < this.items.length)) {
        this.items[j] = ItemStack.createStack(nbttagcompound1);
      }
    }
    
    if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
      this.a = nbttagcompound.getString("CustomName");
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    NBTTagList nbttaglist = new NBTTagList();
    
    for (int i = 0; i < this.items.length; i++) {
      if (this.items[i] != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
        nbttagcompound1.setByte("Slot", (byte)i);
        this.items[i].save(nbttagcompound1);
        nbttaglist.add(nbttagcompound1);
      }
    }
    
    nbttagcompound.set("Items", nbttaglist);
    if (k_()) {
      nbttagcompound.setString("CustomName", this.a);
    }
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public boolean a(EntityHuman entityhuman) {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
  
  public void startOpen() {}
  
  public void closeContainer() {}
  
  public boolean b(int i, ItemStack itemstack) {
    return true;
  }
}
