package net.minecraft.server;

public abstract interface ICommandDispatcher
{
  public abstract void a(ICommandListener paramICommandListener, ICommand paramICommand, int paramInt, String paramString, Object... paramVarArgs);
}
