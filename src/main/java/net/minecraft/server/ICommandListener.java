package net.minecraft.server;

public abstract interface ICommandListener
{
  public abstract String getName();
  
  public abstract IChatBaseComponent getScoreboardDisplayName();
  
  public abstract void sendMessage(IChatBaseComponent paramIChatBaseComponent);
  
  public abstract boolean a(int paramInt, String paramString);
  
  public abstract ChunkCoordinates getChunkCoordinates();
  
  public abstract World getWorld();
}
