package net.minecraft.server;


public class PersistentStructure
  extends PersistentBase
{
  private NBTTagCompound a;
  

  public PersistentStructure(String paramString)
  {
    super(paramString);
    this.a = new NBTTagCompound();
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    this.a = paramNBTTagCompound.getCompound("Features");
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.set("Features", this.a);
  }
  



  public void a(NBTTagCompound paramNBTTagCompound, int paramInt1, int paramInt2)
  {
    this.a.set(b(paramInt1, paramInt2), paramNBTTagCompound);
  }
  
  public static String b(int paramInt1, int paramInt2) {
    return "[" + paramInt1 + "," + paramInt2 + "]";
  }
  
  public NBTTagCompound a() {
    return this.a;
  }
}
