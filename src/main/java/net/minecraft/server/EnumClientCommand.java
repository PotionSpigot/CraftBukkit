package net.minecraft.server;




public enum EnumClientCommand
{
  private final int d;
  


  private static final EnumClientCommand[] e;
  


  private EnumClientCommand(int paramInt1)
  {
    this.d = paramInt1;
  }
  
  static
  {
    e = new EnumClientCommand[values().length];
    





    for (EnumClientCommand localEnumClientCommand : values()) {
      e[localEnumClientCommand.d] = localEnumClientCommand;
    }
  }
}
