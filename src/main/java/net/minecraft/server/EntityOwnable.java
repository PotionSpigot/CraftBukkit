package net.minecraft.server;

public abstract interface EntityOwnable
{
  public abstract String getOwnerUUID();
  
  public abstract Entity getOwner();
}
