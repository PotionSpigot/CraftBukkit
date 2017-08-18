package net.minecraft.server;

public class TileEntityComparator
  extends TileEntity
{
  private int a;
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setInt("OutputSignal", this.a);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.a = paramNBTTagCompound.getInt("OutputSignal");
  }
  
  public int a() {
    return this.a;
  }
  
  public void a(int paramInt) {
    this.a = paramInt;
  }
}
