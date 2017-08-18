package net.minecraft.server;


class ClientCommandOrdinalWrapper
{
  static final int[] a = new int[EnumClientCommand.values().length];
  
  static {
    try {
      a[EnumClientCommand.PERFORM_RESPAWN.ordinal()] = 1;
    }
    catch (NoSuchFieldError localNoSuchFieldError1) {}
    
    try
    {
      a[EnumClientCommand.REQUEST_STATS.ordinal()] = 2;
    }
    catch (NoSuchFieldError localNoSuchFieldError2) {}
    
    try
    {
      a[EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
    }
    catch (NoSuchFieldError localNoSuchFieldError3) {}
  }
}
