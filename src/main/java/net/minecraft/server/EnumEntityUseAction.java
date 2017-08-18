package net.minecraft.server;




public enum EnumEntityUseAction
{
  private static final EnumEntityUseAction[] c;
  


  private final int d;
  



  private EnumEntityUseAction(int paramInt1)
  {
    this.d = paramInt1;
  }
  
  static
  {
    c = new EnumEntityUseAction[values().length];
    







    for (EnumEntityUseAction localEnumEntityUseAction : values()) {
      c[localEnumEntityUseAction.d] = localEnumEntityUseAction;
    }
  }
}
