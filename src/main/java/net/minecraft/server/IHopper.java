package net.minecraft.server;

public abstract interface IHopper
  extends IInventory
{
  public abstract World getWorld();
  
  public abstract double x();
  
  public abstract double aD();
  
  public abstract double aE();
}
