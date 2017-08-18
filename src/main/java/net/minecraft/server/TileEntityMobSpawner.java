package net.minecraft.server;






public class TileEntityMobSpawner
  extends TileEntity
{
  private final MobSpawnerAbstract a = new MobSpawner(this);
  


































  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.a.a(paramNBTTagCompound);
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a.b(paramNBTTagCompound);
  }
  
  public void h()
  {
    this.a.g();
    super.h();
  }
  
  public Packet getUpdatePacket()
  {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    b(localNBTTagCompound);
    localNBTTagCompound.remove("SpawnPotentials");
    return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 1, localNBTTagCompound);
  }
  
  public boolean c(int paramInt1, int paramInt2)
  {
    if (this.a.b(paramInt1)) return true;
    return super.c(paramInt1, paramInt2);
  }
  
  public MobSpawnerAbstract getSpawner() {
    return this.a;
  }
}
