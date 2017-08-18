package net.minecraft.server;

import java.util.List;








public class CommandSetBlock
  extends CommandAbstract
{
  public String getCommand()
  {
    return "setblock";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.setblock.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 4)
    {
      int i = paramICommandListener.getChunkCoordinates().x;
      int j = paramICommandListener.getChunkCoordinates().y;
      int k = paramICommandListener.getChunkCoordinates().z;
      i = MathHelper.floor(a(paramICommandListener, i, paramArrayOfString[0]));
      j = MathHelper.floor(a(paramICommandListener, j, paramArrayOfString[1]));
      k = MathHelper.floor(a(paramICommandListener, k, paramArrayOfString[2]));
      
      Block localBlock = CommandAbstract.g(paramICommandListener, paramArrayOfString[3]);
      
      int m = 0;
      if (paramArrayOfString.length >= 5) {
        m = a(paramICommandListener, paramArrayOfString[4], 0, 15);
      }
      
      World localWorld = paramICommandListener.getWorld();
      if (!localWorld.isLoaded(i, j, k)) {
        throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
      }
      
      NBTTagCompound localNBTTagCompound = new NBTTagCompound();
      int n = 0;
      Object localObject; if ((paramArrayOfString.length >= 7) && (localBlock.isTileEntity())) {
        localObject = a(paramICommandListener, paramArrayOfString, 6).c();
        try {
          NBTBase localNBTBase = MojangsonParser.parse((String)localObject);
          if ((localNBTBase instanceof NBTTagCompound)) {
            localNBTTagCompound = (NBTTagCompound)localNBTBase;
            n = 1;
          } else {
            throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
          }
        } catch (MojangsonParseException localMojangsonParseException) {
          throw new CommandException("commands.setblock.tagError", new Object[] { localMojangsonParseException.getMessage() });
        }
      }
      
      if (paramArrayOfString.length >= 6) {
        if (paramArrayOfString[5].equals("destroy")) {
          localWorld.setAir(i, j, k, true);
        } else if ((paramArrayOfString[5].equals("keep")) && 
          (!localWorld.isEmpty(i, j, k))) {
          throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
      }
      

      if (!localWorld.setTypeAndData(i, j, k, localBlock, m, 3)) {
        throw new CommandException("commands.setblock.noChange", new Object[0]);
      }
      
      if (n != 0) {
        localObject = localWorld.getTileEntity(i, j, k);
        if (localObject != null)
        {
          localNBTTagCompound.setInt("x", i);
          localNBTTagCompound.setInt("y", j);
          localNBTTagCompound.setInt("z", k);
          
          ((TileEntity)localObject).a(localNBTTagCompound);
        }
      }
      a(paramICommandListener, this, "commands.setblock.success", new Object[0]);
      return;
    }
    
    throw new ExceptionUsage("commands.setblock.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 4) {
      return a(paramArrayOfString, Block.REGISTRY.keySet());
    }
    if (paramArrayOfString.length == 6) {
      return a(paramArrayOfString, new String[] { "replace", "destroy", "keep" });
    }
    
    return null;
  }
}
