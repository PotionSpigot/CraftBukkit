package net.minecraft.server;



public class TileEntityFlowerPot
  extends TileEntity
{
  private Item a;
  
  private int i;
  

  public TileEntityFlowerPot() {}
  

  public TileEntityFlowerPot(Item paramItem, int paramInt)
  {
    this.a = paramItem;
    this.i = paramInt;
  }
  

  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setInt("Item", Item.getId(this.a));
    paramNBTTagCompound.setInt("Data", this.i);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.a = Item.getById(paramNBTTagCompound.getInt("Item"));
    this.i = paramNBTTagCompound.getInt("Data");
  }
  
  public Packet getUpdatePacket()
  {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    b(localNBTTagCompound);
    return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 5, localNBTTagCompound);
  }
  
  public void a(Item paramItem, int paramInt) {
    this.a = paramItem;
    this.i = paramInt;
  }
  
  public Item a() {
    return this.a;
  }
  
  public int b() {
    return this.i;
  }
}
