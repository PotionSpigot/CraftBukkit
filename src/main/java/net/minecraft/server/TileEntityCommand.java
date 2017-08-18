package net.minecraft.server;









public class TileEntityCommand
  extends TileEntity
{
  private final CommandBlockListenerAbstract a = new TileEntityCommandListener(this);
  




































  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a.a(paramNBTTagCompound);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.a.b(paramNBTTagCompound);
  }
  
  public Packet getUpdatePacket()
  {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    b(localNBTTagCompound);
    return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 2, localNBTTagCompound);
  }
  
  public CommandBlockListenerAbstract getCommandBlock() {
    return this.a;
  }
}
