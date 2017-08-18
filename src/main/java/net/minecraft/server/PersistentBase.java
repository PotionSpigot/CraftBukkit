package net.minecraft.server;

public abstract class PersistentBase
{
  public final String id;
  private boolean a;
  
  public PersistentBase(String paramString)
  {
    this.id = paramString;
  }
  
  public abstract void a(NBTTagCompound paramNBTTagCompound);
  
  public abstract void b(NBTTagCompound paramNBTTagCompound);
  
  public void c() {
    a(true);
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean d() {
    return this.a;
  }
}
