package net.minecraft.server;

public abstract interface PacketListener
{
  public abstract void a(IChatBaseComponent paramIChatBaseComponent);
  
  public abstract void a(EnumProtocol paramEnumProtocol1, EnumProtocol paramEnumProtocol2);
  
  public abstract void a();
}
