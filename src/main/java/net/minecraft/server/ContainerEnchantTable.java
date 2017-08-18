package net.minecraft.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryEnchanting;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.plugin.PluginManager;

public class ContainerEnchantTable extends Container
{
  public ContainerEnchantTableInventory enchantSlots = new ContainerEnchantTableInventory(this, "Enchant", true, 1);
  private World world;
  private int x;
  private int y;
  private int z;
  private Random l = new Random();
  public long f;
  public int[] costs = new int[3];
  
  private CraftInventoryView bukkitEntity = null;
  private Player player;
  
  public ContainerEnchantTable(PlayerInventory playerinventory, World world, int i, int j, int k)
  {
    this.world = world;
    this.x = i;
    this.y = j;
    this.z = k;
    a(new SlotEnchant(this, this.enchantSlots, 0, 25, 47));
    


    for (int l = 0; l < 3; l++) {
      for (int i1 = 0; i1 < 9; i1++) {
        a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
      }
    }
    
    for (l = 0; l < 9; l++) {
      a(new Slot(playerinventory, l, 8 + l * 18, 142));
    }
    

    this.player = ((Player)playerinventory.player.getBukkitEntity());
    this.enchantSlots.player = this.player;
  }
  
  public void addSlotListener(ICrafting icrafting)
  {
    super.addSlotListener(icrafting);
    icrafting.setContainerData(this, 0, this.costs[0]);
    icrafting.setContainerData(this, 1, this.costs[1]);
    icrafting.setContainerData(this, 2, this.costs[2]);
  }
  
  public void b() {
    super.b();
    
    for (int i = 0; i < this.listeners.size(); i++) {
      ICrafting icrafting = (ICrafting)this.listeners.get(i);
      
      icrafting.setContainerData(this, 0, this.costs[0]);
      icrafting.setContainerData(this, 1, this.costs[1]);
      icrafting.setContainerData(this, 2, this.costs[2]);
    }
  }
  
  public void a(IInventory iinventory) {
    if (iinventory == this.enchantSlots) {
      ItemStack itemstack = iinventory.getItem(0);
      

      if (itemstack != null) {
        this.f = this.l.nextLong();
        if (!this.world.isStatic) {
          int i = 0;
          


          for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
              if (((j != 0) || (k != 0)) && (this.world.isEmpty(this.x + k, this.y, this.z + j)) && (this.world.isEmpty(this.x + k, this.y + 1, this.z + j))) {
                if (this.world.getType(this.x + k * 2, this.y, this.z + j * 2) == Blocks.BOOKSHELF) {
                  i++;
                }
                
                if (this.world.getType(this.x + k * 2, this.y + 1, this.z + j * 2) == Blocks.BOOKSHELF) {
                  i++;
                }
                
                if ((k != 0) && (j != 0)) {
                  if (this.world.getType(this.x + k * 2, this.y, this.z + j) == Blocks.BOOKSHELF) {
                    i++;
                  }
                  
                  if (this.world.getType(this.x + k * 2, this.y + 1, this.z + j) == Blocks.BOOKSHELF) {
                    i++;
                  }
                  
                  if (this.world.getType(this.x + k, this.y, this.z + j * 2) == Blocks.BOOKSHELF) {
                    i++;
                  }
                  
                  if (this.world.getType(this.x + k, this.y + 1, this.z + j * 2) == Blocks.BOOKSHELF) {
                    i++;
                  }
                }
              }
            }
          }
          
          for (j = 0; j < 3; j++) {
            this.costs[j] = EnchantmentManager.a(this.l, j, i, itemstack);
          }
          

          CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
          PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player, getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs, i);
          event.setCancelled(!itemstack.x());
          this.world.getServer().getPluginManager().callEvent(event);
          
          if (event.isCancelled()) {
            for (i = 0; i < 3; i++) {
              this.costs[i] = 0;
            }
            return;
          }
          

          b();
        }
      } else {
        for (int i = 0; i < 3; i++) {
          this.costs[i] = 0;
        }
      }
    }
  }
  
  public boolean a(EntityHuman entityhuman, int i) {
    ItemStack itemstack = this.enchantSlots.getItem(0);
    
    if ((this.costs[i] > 0) && (itemstack != null) && ((entityhuman.expLevel >= this.costs[i]) || (entityhuman.abilities.canInstantlyBuild))) {
      if (!this.world.isStatic) {
        List list = EnchantmentManager.b(this.l, itemstack, this.costs[i]);
        
        if (list == null) {
          list = new java.util.ArrayList();
        }
        

        boolean flag = itemstack.getItem() == Items.BOOK;
        
        if (list != null)
        {
          Map<org.bukkit.enchantments.Enchantment, Integer> enchants = new HashMap();
          for (Object obj : list) {
            EnchantmentInstance instance = (EnchantmentInstance)obj;
            enchants.put(org.bukkit.enchantments.Enchantment.getById(instance.enchantment.id), Integer.valueOf(instance.level));
          }
          CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
          
          EnchantItemEvent event = new EnchantItemEvent((Player)entityhuman.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs[i], enchants, i);
          this.world.getServer().getPluginManager().callEvent(event);
          
          int level = event.getExpLevelCost();
          if ((event.isCancelled()) || ((level > entityhuman.expLevel) && (!entityhuman.abilities.canInstantlyBuild)) || (event.getEnchantsToAdd().isEmpty())) {
            return false;
          }
          
          if (flag) {
            itemstack.setItem(Items.ENCHANTED_BOOK);
          }
          
          for (Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
            try {
              if (flag) {
                int enchantId = ((org.bukkit.enchantments.Enchantment)entry.getKey()).getId();
                if (Enchantment.byId[enchantId] == null) {
                  continue;
                }
                
                EnchantmentInstance enchantment = new EnchantmentInstance(enchantId, ((Integer)entry.getValue()).intValue());
                Items.ENCHANTED_BOOK.a(itemstack, enchantment);
              } else {
                item.addUnsafeEnchantment((org.bukkit.enchantments.Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException) {}
          }
          

          entityhuman.levelDown(-level);
          

          a(this.enchantSlots);
        }
      }
      
      return true;
    }
    return false;
  }
  
  public void b(EntityHuman entityhuman)
  {
    super.b(entityhuman);
    if (!this.world.isStatic) {
      ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(0);
      
      if (itemstack != null) {
        entityhuman.drop(itemstack, false);
      }
    }
  }
  
  public boolean a(EntityHuman entityhuman) {
    if (!this.checkReachable) return true;
    return this.world.getType(this.x, this.y, this.z) == Blocks.ENCHANTMENT_TABLE;
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
      } else {
        if ((((Slot)this.c.get(0)).hasItem()) || (!((Slot)this.c.get(0)).isAllowed(itemstack1))) {
          return null;
        }
        
        if ((itemstack1.hasTag()) && (itemstack1.count == 1)) {
          ((Slot)this.c.get(0)).set(itemstack1.cloneItemStack());
          itemstack1.count = 0;
        } else if (itemstack1.count >= 1)
        {
          ItemStack clone = itemstack1.cloneItemStack();
          clone.count = 1;
          ((Slot)this.c.get(0)).set(clone);
          
          itemstack1.count -= 1;
        }
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
    
    CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.enchantSlots);
    this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
    return this.bukkitEntity;
  }
}
