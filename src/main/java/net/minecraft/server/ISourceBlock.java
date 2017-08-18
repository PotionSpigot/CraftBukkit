package net.minecraft.server;

public abstract interface ISourceBlock
  extends ILocationSource
{
  public abstract double getX();
  
  public abstract double getY();
  
  public abstract double getZ();
  
  public abstract int getBlockX();
  
  public abstract int getBlockY();
  
  public abstract int getBlockZ();
  
  public abstract int h();
  
  public abstract TileEntity getTileEntity();
}
