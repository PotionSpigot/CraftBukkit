package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;

public class InventoryHorseChest extends InventorySubcontainer
{
  public InventoryHorseChest(String s, int i)
  {
    super(s, false, i);
  }
  

  public List<HumanEntity> transaction = new java.util.ArrayList();
  private EntityHorse horse;
  private int maxStack = 64;
  
  public InventoryHorseChest(String s, int i, EntityHorse horse) {
    this(s, i);
    this.horse = horse;
  }
  
  public ItemStack[] getContents()
  {
    return this.items;
  }
  
  public void onOpen(CraftHumanEntity who)
  {
    this.transaction.add(who);
  }
  
  public void onClose(CraftHumanEntity who)
  {
    this.transaction.remove(who);
  }
  
  public List<HumanEntity> getViewers()
  {
    return this.transaction;
  }
  
  public org.bukkit.inventory.InventoryHolder getOwner()
  {
    return (Horse)this.horse.getBukkitEntity();
  }
  
  public void setMaxStackSize(int size)
  {
    this.maxStack = size;
  }
  
  public int getMaxStackSize()
  {
    return this.maxStack;
  }
}
