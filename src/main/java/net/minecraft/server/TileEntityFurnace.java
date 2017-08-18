package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.plugin.PluginManager;

public class TileEntityFurnace extends TileEntity implements IWorldInventory
{
  private static final int[] k = { 0 };
  private static final int[] l = { 2, 1 };
  private static final int[] m = { 1 };
  private ItemStack[] items = new ItemStack[3];
  public int burnTime;
  public int ticksForCurrentFuel;
  public int cookTime;
  public double cookSpeedMultiplier = 1.0D;
  
  private String o;
  
  private int lastTick = MinecraftServer.currentTick;
  private int maxStack = 64;
  public List<HumanEntity> transaction = new java.util.ArrayList();
  
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
    return this.items.length;
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
        return itemstack;
      }
      ItemStack itemstack = this.items[i].a(j);
      if (this.items[i].count == 0) {
        this.items[i] = null;
      }
      
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
  
  public void setItem(int i, ItemStack itemstack)
  {
    this.items[i] = itemstack;
    if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
      itemstack.count = getMaxStackSize();
    }
  }
  
  public String getInventoryName() {
    return k_() ? this.o : "container.furnace";
  }
  
  public boolean k_() {
    return (this.o != null) && (this.o.length() > 0);
  }
  
  public void a(String s) {
    this.o = s;
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
    
    this.burnTime = nbttagcompound.getShort("BurnTime");
    this.cookTime = nbttagcompound.getShort("CookTime");
    this.ticksForCurrentFuel = fuelTime(this.items[1]);
    if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
      this.o = nbttagcompound.getString("CustomName");
    }
    

    if (nbttagcompound.hasKey("CookSpeedMultiplier")) {
      this.cookSpeedMultiplier = nbttagcompound.getDouble("CookSpeedMultiplier");
    }
  }
  
  public void b(NBTTagCompound nbttagcompound)
  {
    super.b(nbttagcompound);
    nbttagcompound.setShort("BurnTime", (short)this.burnTime);
    nbttagcompound.setShort("CookTime", (short)this.cookTime);
    nbttagcompound.setDouble("CookSpeedMultiplier", this.cookSpeedMultiplier);
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
      nbttagcompound.setString("CustomName", this.o);
    }
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public boolean isBurning() {
    return this.burnTime > 0;
  }
  
  public void h() {
    boolean flag = this.burnTime > 0;
    boolean flag1 = false;
    

    int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
    this.lastTick = MinecraftServer.currentTick;
    

    if ((isBurning()) && (canBurn())) {
      this.cookTime = ((int)(this.cookTime + elapsedTicks * this.cookSpeedMultiplier));
      if (this.cookTime >= 200) {
        this.cookTime %= 200;
        burn();
        flag1 = true;
      }
    } else {
      this.cookTime = 0;
    }
    

    if (this.burnTime > 0) {
      this.burnTime -= elapsedTicks;
    }
    
    if (!this.world.isStatic) {
      if ((this.burnTime != 0) || ((this.items[1] != null) && (this.items[0] != null)))
      {
        if ((this.burnTime <= 0) && (canBurn())) {
          CraftItemStack fuel = CraftItemStack.asCraftMirror(this.items[1]);
          
          FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), fuel, fuelTime(this.items[1]));
          this.world.getServer().getPluginManager().callEvent(furnaceBurnEvent);
          
          if (furnaceBurnEvent.isCancelled()) {
            return;
          }
          
          this.ticksForCurrentFuel = furnaceBurnEvent.getBurnTime();
          this.burnTime += this.ticksForCurrentFuel;
          if ((this.burnTime > 0) && (furnaceBurnEvent.isBurning()))
          {
            flag1 = true;
            if (this.items[1] != null) {
              this.items[1].count -= 1;
              if (this.items[1].count == 0) {
                Item item = this.items[1].getItem().t();
                
                this.items[1] = (item != null ? new ItemStack(item) : null);
              }
            }
          }
        }
      }
      













      if (flag != this.burnTime > 0) {
        flag1 = true;
        BlockFurnace.a(this.burnTime > 0, this.world, this.x, this.y, this.z);
      }
    }
    
    if (flag1) {
      update();
    }
  }
  
  private boolean canBurn() {
    if (this.items[0] == null) {
      return false;
    }
    ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
    

    return itemstack != null;
  }
  
  public void burn()
  {
    if (canBurn()) {
      ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
      

      CraftItemStack source = CraftItemStack.asCraftMirror(this.items[0]);
      org.bukkit.inventory.ItemStack result = CraftItemStack.asBukkitCopy(itemstack);
      
      FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), source, result);
      this.world.getServer().getPluginManager().callEvent(furnaceSmeltEvent);
      
      if (furnaceSmeltEvent.isCancelled()) {
        return;
      }
      
      result = furnaceSmeltEvent.getResult();
      itemstack = CraftItemStack.asNMSCopy(result);
      
      if (itemstack != null) {
        if (this.items[2] == null) {
          this.items[2] = itemstack;
        } else if (CraftItemStack.asCraftMirror(this.items[2]).isSimilar(result)) {
          this.items[2].count += itemstack.count;
        } else {
          return;
        }
      }
      

      this.items[0].count -= 1;
      if (this.items[0].count <= 0) {
        this.items[0] = null;
      }
    }
  }
  
  public static int fuelTime(ItemStack itemstack) {
    if (itemstack == null) {
      return 0;
    }
    Item item = itemstack.getItem();
    
    if (((item instanceof ItemBlock)) && (Block.a(item) != Blocks.AIR)) {
      Block block = Block.a(item);
      
      if (block == Blocks.WOOD_STEP) {
        return 150;
      }
      
      if (block.getMaterial() == Material.WOOD) {
        return 300;
      }
      
      if (block == Blocks.COAL_BLOCK) {
        return 16000;
      }
    }
    
    return item == Items.BLAZE_ROD ? 2400 : item == Item.getItemOf(Blocks.SAPLING) ? 100 : item == Items.LAVA_BUCKET ? 20000 : item == Items.COAL ? 1600 : item == Items.STICK ? 100 : ((item instanceof ItemHoe)) && (((ItemHoe)item).i().equals("WOOD")) ? 200 : ((item instanceof ItemSword)) && (((ItemSword)item).j().equals("WOOD")) ? 200 : ((item instanceof ItemTool)) && (((ItemTool)item).j().equals("WOOD")) ? 200 : 0;
  }
  
  public static boolean isFuel(ItemStack itemstack)
  {
    return fuelTime(itemstack) > 0;
  }
  
  public boolean a(EntityHuman entityhuman) {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
  
  public void startOpen() {}
  
  public void closeContainer() {}
  
  public boolean b(int i, ItemStack itemstack) {
    return i != 2;
  }
  
  public int[] getSlotsForFace(int i) {
    return i == 1 ? k : i == 0 ? l : m;
  }
  
  public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, int j) {
    return b(i, itemstack);
  }
  
  public boolean canTakeItemThroughFace(int i, ItemStack itemstack, int j) {
    return (j != 0) || (i != 1) || (itemstack.getItem() == Items.BUCKET);
  }
}
