package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.PluginManager;

public class TileEntityBrewingStand extends TileEntity implements IWorldInventory
{
  private static final int[] a = { 3 };
  private static final int[] i = { 0, 1, 2 };
  public ItemStack[] items = new ItemStack[4];
  public int brewTime;
  private int l;
  private Item m;
  private String n;
  private int lastTick = MinecraftServer.currentTick;
  



  public List<HumanEntity> transaction = new ArrayList();
  private int maxStack = 64;
  
  public void onOpen(CraftHumanEntity who) {
    this.transaction.add(who);
  }
  
  public void onClose(CraftHumanEntity who) {
    this.transaction.remove(who);
  }
  
  public List<HumanEntity> getViewers() {
    return this.transaction;
  }
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
  }
  
  public String getInventoryName()
  {
    return k_() ? this.n : "container.brewing";
  }
  
  public boolean k_() {
    return (this.n != null) && (this.n.length() > 0);
  }
  
  public void a(String s) {
    this.n = s;
  }
  
  public int getSize() {
    return this.items.length;
  }
  
  public void h()
  {
    int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
    this.lastTick = MinecraftServer.currentTick;
    
    if (this.brewTime > 0) {
      this.brewTime -= elapsedTicks;
      if (this.brewTime <= 0)
      {
        l();
        update();
      } else if (!k()) {
        this.brewTime = 0;
        update();
      } else if (this.m != this.items[3].getItem()) {
        this.brewTime = 0;
        update();
      }
    } else if (k()) {
      this.brewTime = 400;
      this.m = this.items[3].getItem();
    }
    
    int i = j();
    
    if (i != this.l) {
      this.l = i;
      this.world.setData(this.x, this.y, this.z, i, 2);
    }
    
    super.h();
  }
  
  public int i() {
    return this.brewTime;
  }
  
  private boolean k() {
    if ((this.items[3] != null) && (this.items[3].count > 0)) {
      ItemStack itemstack = this.items[3];
      
      if (!itemstack.getItem().m(itemstack)) {
        return false;
      }
      boolean flag = false;
      
      for (int i = 0; i < 3; i++) {
        if ((this.items[i] != null) && (this.items[i].getItem() == Items.POTION)) {
          int j = this.items[i].getData();
          int k = c(j, itemstack);
          
          if ((!ItemPotion.g(j)) && (ItemPotion.g(k))) {
            flag = true;
            break;
          }
          
          List list = Items.POTION.c(j);
          List list1 = Items.POTION.c(k);
          
          if (((j <= 0) || (list != list1)) && ((list == null) || ((!list.equals(list1)) && (list1 != null))) && (j != k)) {
            flag = true;
            break;
          }
        }
      }
      
      return flag;
    }
    
    return false;
  }
  
  private void l()
  {
    if (k()) {
      ItemStack itemstack = this.items[3];
      

      if (getOwner() != null) {
        BrewEvent event = new BrewEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), (org.bukkit.inventory.BrewerInventory)getOwner().getInventory());
        org.bukkit.Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
          return;
        }
      }
      

      for (int i = 0; i < 3; i++) {
        if ((this.items[i] != null) && (this.items[i].getItem() == Items.POTION)) {
          int j = this.items[i].getData();
          int k = c(j, itemstack);
          List list = Items.POTION.c(j);
          List list1 = Items.POTION.c(k);
          
          if (((j <= 0) || (list != list1)) && ((list == null) || ((!list.equals(list1)) && (list1 != null)))) {
            if (j != k) {
              this.items[i].setData(k);
            }
          } else if ((!ItemPotion.g(j)) && (ItemPotion.g(k))) {
            this.items[i].setData(k);
          }
        }
      }
      
      if (itemstack.getItem().u()) {
        this.items[3] = new ItemStack(itemstack.getItem().t());
      } else {
        this.items[3].count -= 1;
        if (this.items[3].count <= 0) {
          this.items[3] = null;
        }
      }
    }
  }
  
  private int c(int i, ItemStack itemstack) {
    return itemstack.getItem().m(itemstack) ? PotionBrewer.a(i, itemstack.getItem().i(itemstack)) : itemstack == null ? i : i;
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
    
    this.items = new ItemStack[getSize()];
    
    for (int i = 0; i < nbttaglist.size(); i++) {
      NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
      byte b0 = nbttagcompound1.getByte("Slot");
      
      if ((b0 >= 0) && (b0 < this.items.length)) {
        this.items[b0] = ItemStack.createStack(nbttagcompound1);
      }
    }
    
    this.brewTime = nbttagcompound.getShort("BrewTime");
    if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
      this.n = nbttagcompound.getString("CustomName");
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setShort("BrewTime", (short)this.brewTime);
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
      nbttagcompound.setString("CustomName", this.n);
    }
  }
  
  public ItemStack getItem(int i) {
    return (i >= 0) && (i < this.items.length) ? this.items[i] : null;
  }
  
  public ItemStack splitStack(int i, int j) {
    if ((i >= 0) && (i < this.items.length)) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public ItemStack splitWithoutUpdate(int i)
  {
    if ((i >= 0) && (i < this.items.length)) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public void setItem(int i, ItemStack itemstack)
  {
    if ((i >= 0) && (i < this.items.length)) {
      this.items[i] = itemstack;
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
    return (itemstack.getItem() == Items.POTION) || (itemstack.getItem() == Items.GLASS_BOTTLE) ? true : i == 3 ? itemstack.getItem().m(itemstack) : false;
  }
  
  public int j() {
    int i = 0;
    
    for (int j = 0; j < 3; j++) {
      if (this.items[j] != null) {
        i |= 1 << j;
      }
    }
    
    return i;
  }
  
  public int[] getSlotsForFace(int i) {
    return i == 1 ? a : i;
  }
  
  public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, int j) {
    return b(i, itemstack);
  }
  
  public boolean canTakeItemThroughFace(int i, ItemStack itemstack, int j) {
    return true;
  }
}
