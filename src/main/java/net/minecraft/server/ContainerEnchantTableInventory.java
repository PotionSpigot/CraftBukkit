package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class ContainerEnchantTableInventory
  extends InventorySubcontainer
{
  final ContainerEnchantTable enchantTable;
  public List<HumanEntity> transaction = new ArrayList();
  public Player player;
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
  
  public InventoryHolder getOwner() {
    return this.player;
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
  }
  
  ContainerEnchantTableInventory(ContainerEnchantTable containerenchanttable, String s, boolean flag, int i)
  {
    super(s, flag, i);
    this.enchantTable = containerenchanttable;
    setMaxStackSize(1);
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public void update() {
    super.update();
    this.enchantTable.a(this);
  }
}
