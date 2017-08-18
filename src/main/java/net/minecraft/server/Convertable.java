package net.minecraft.server;

public abstract interface Convertable
{
  public abstract IDataManager a(String paramString, boolean paramBoolean);
  
  public abstract void d();
  
  public abstract boolean e(String paramString);
  
  public abstract boolean isConvertable(String paramString);
  
  public abstract boolean convert(String paramString, IProgressUpdate paramIProgressUpdate);
}
