package net.minecraft.server;

public abstract interface IPlayerFileData
{
  public abstract void save(EntityHuman paramEntityHuman);
  
  public abstract NBTTagCompound load(EntityHuman paramEntityHuman);
  
  public abstract String[] getSeenPlayers();
}
