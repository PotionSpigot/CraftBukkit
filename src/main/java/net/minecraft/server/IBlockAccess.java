package net.minecraft.server;

public abstract interface IBlockAccess
{
  public abstract Block getType(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getData(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getBlockPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
