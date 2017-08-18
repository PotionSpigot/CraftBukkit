package net.minecraft.server;

public class TileEntityDropper extends TileEntityDispenser
{
  public String getInventoryName() {
    return k_() ? this.a : "container.dropper";
  }
}
