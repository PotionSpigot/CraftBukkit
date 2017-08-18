package net.minecraft.server;

import java.util.List;







public class CommandGive
  extends CommandAbstract
{
  public String getCommand()
  {
    return "give";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.give.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 2) {
      throw new ExceptionUsage("commands.give.usage", new Object[0]);
    }
    
    EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[0]);
    
    Item localItem = f(paramICommandListener, paramArrayOfString[1]);
    int i = 1;
    int j = 0;
    
    if (paramArrayOfString.length >= 3) {
      i = a(paramICommandListener, paramArrayOfString[2], 1, 64);
    }
    
    if (paramArrayOfString.length >= 4) {
      j = a(paramICommandListener, paramArrayOfString[3]);
    }
    
    ItemStack localItemStack = new ItemStack(localItem, i, j);
    
    if (paramArrayOfString.length >= 5) {
      localObject = a(paramICommandListener, paramArrayOfString, 4).c();
      try {
        NBTBase localNBTBase = MojangsonParser.parse((String)localObject);
        if ((localNBTBase instanceof NBTTagCompound)) {
          localItemStack.setTag((NBTTagCompound)localNBTBase);
        } else {
          a(paramICommandListener, this, "commands.give.tagError", new Object[] { "Not a valid tag" });
          return;
        }
      } catch (MojangsonParseException localMojangsonParseException) {
        a(paramICommandListener, this, "commands.give.tagError", new Object[] { localMojangsonParseException.getMessage() });
        return;
      }
    }
    
    Object localObject = localEntityPlayer.drop(localItemStack, false);
    ((EntityItem)localObject).pickupDelay = 0;
    ((EntityItem)localObject).a(localEntityPlayer.getName());
    
    a(paramICommandListener, this, "commands.give.success", new Object[] { localItemStack.E(), Integer.valueOf(i), localEntityPlayer.getName() });
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, d());
    }
    if (paramArrayOfString.length == 2) {
      return a(paramArrayOfString, Item.REGISTRY.keySet());
    }
    
    return null;
  }
  
  protected String[] d() {
    return MinecraftServer.getServer().getPlayers();
  }
  
  public boolean isListStart(String[] paramArrayOfString, int paramInt)
  {
    return paramInt == 0;
  }
}
