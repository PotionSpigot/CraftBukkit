package net.minecraft.server;

import java.util.List;







public class CommandEnchant
  extends CommandAbstract
{
  public String getCommand()
  {
    return "enchant";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.enchant.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 2) {
      EntityPlayer localEntityPlayer = d(paramICommandListener, paramArrayOfString[0]);
      
      int i = a(paramICommandListener, paramArrayOfString[1], 0, Enchantment.byId.length - 1);
      int j = 1;
      
      ItemStack localItemStack = localEntityPlayer.bF();
      if (localItemStack == null) {
        throw new CommandException("commands.enchant.noItem", new Object[0]);
      }
      
      Enchantment localEnchantment1 = Enchantment.byId[i];
      if (localEnchantment1 == null) {
        throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[] { Integer.valueOf(i) });
      }
      
      if (!localEnchantment1.canEnchant(localItemStack)) {
        throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
      }
      
      if (paramArrayOfString.length >= 3) {
        j = a(paramICommandListener, paramArrayOfString[2], localEnchantment1.getStartLevel(), localEnchantment1.getMaxLevel());
      }
      
      if (localItemStack.hasTag()) {
        NBTTagList localNBTTagList = localItemStack.getEnchantments();
        if (localNBTTagList != null) {
          for (int k = 0; k < localNBTTagList.size(); k++) {
            int m = localNBTTagList.get(k).getShort("id");
            
            if (Enchantment.byId[m] != null) {
              Enchantment localEnchantment2 = Enchantment.byId[m];
              if (!localEnchantment2.a(localEnchantment1)) {
                throw new CommandException("commands.enchant.cantCombine", new Object[] { localEnchantment1.c(j), localEnchantment2.c(localNBTTagList.get(k).getShort("lvl")) });
              }
            }
          }
        }
      }
      

      localItemStack.addEnchantment(localEnchantment1, j);
      
      a(paramICommandListener, this, "commands.enchant.success", new Object[0]);
      return;
    }
    
    throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, d());
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
