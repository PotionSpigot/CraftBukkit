package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;

public class ItemMapEmpty extends ItemWorldMapBase {
  protected ItemMapEmpty() { a(CreativeModeTab.f); }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
  {
    World worldMain = (World)world.getServer().getServer().worlds.get(0);
    ItemStack itemstack1 = new ItemStack(Items.MAP, 1, worldMain.b("map"));
    String s = "map_" + itemstack1.getData();
    WorldMap worldmap = new WorldMap(s);
    
    worldMain.a(s, worldmap);
    worldmap.scale = 0;
    int i = 128 * (1 << worldmap.scale);
    
    worldmap.centerX = ((int)(Math.round(entityhuman.locX / i) * i));
    worldmap.centerZ = ((int)(Math.round(entityhuman.locZ / i) * i));
    worldmap.map = ((byte)((WorldServer)world).dimension);
    worldmap.c();
    
    org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEvent(new org.bukkit.event.server.MapInitializeEvent(worldmap.mapView));
    
    itemstack.count -= 1;
    if (itemstack.count <= 0) {
      return itemstack1;
    }
    if (!entityhuman.inventory.pickup(itemstack1.cloneItemStack())) {
      entityhuman.drop(itemstack1, false);
    }
    
    return itemstack;
  }
}
