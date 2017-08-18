package net.minecraft.server;

import java.util.List;

public abstract interface ICommand
  extends Comparable
{
  public abstract String getCommand();
  
  public abstract String c(ICommandListener paramICommandListener);
  
  public abstract List b();
  
  public abstract void execute(ICommandListener paramICommandListener, String[] paramArrayOfString);
  
  public abstract boolean canUse(ICommandListener paramICommandListener);
  
  public abstract List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString);
  
  public abstract boolean isListStart(String[] paramArrayOfString, int paramInt);
}
