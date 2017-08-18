package net.minecraft.server;

import java.util.List;















public class CommandTestForBlock
  extends CommandAbstract
{
  public String getCommand()
  {
    return "testforblock";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.testforblock.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 4) {
      int i = paramICommandListener.getChunkCoordinates().x;
      int j = paramICommandListener.getChunkCoordinates().y;
      int k = paramICommandListener.getChunkCoordinates().z;
      i = MathHelper.floor(a(paramICommandListener, i, paramArrayOfString[0]));
      j = MathHelper.floor(a(paramICommandListener, j, paramArrayOfString[1]));
      k = MathHelper.floor(a(paramICommandListener, k, paramArrayOfString[2]));
      
      Block localBlock = Block.b(paramArrayOfString[3]);
      if (localBlock == null) {
        throw new ExceptionInvalidNumber("commands.setblock.notFound", new Object[] { paramArrayOfString[3] });
      }
      
      int m = -1;
      if (paramArrayOfString.length >= 5) {
        m = a(paramICommandListener, paramArrayOfString[4], -1, 15);
      }
      
      World localWorld = paramICommandListener.getWorld();
      if (!localWorld.isLoaded(i, j, k)) {
        throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
      }
      
      NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
      int n = 0;
      if ((paramArrayOfString.length >= 6) && (localBlock.isTileEntity())) {
        localObject = a(paramICommandListener, paramArrayOfString, 5).c();
        try {
          NBTBase localNBTBase = MojangsonParser.parse((String)localObject);
          if ((localNBTBase instanceof NBTTagCompound)) {
            localNBTTagCompound1 = (NBTTagCompound)localNBTBase;
            n = 1;
          } else {
            throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
          }
        } catch (MojangsonParseException localMojangsonParseException) {
          throw new CommandException("commands.setblock.tagError", new Object[] { localMojangsonParseException.getMessage() });
        }
      }
      
      Object localObject = localWorld.getType(i, j, k);
      if (localObject != localBlock) {
        throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), ((Block)localObject).getName(), localBlock.getName() });
      }
      
      if (m > -1) {
        int i1 = localWorld.getData(i, j, k);
        if (i1 != m) {
          throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(i1), Integer.valueOf(m) });
        }
      }
      
      if (n != 0) {
        TileEntity localTileEntity = localWorld.getTileEntity(i, j, k);
        if (localTileEntity == null) {
          throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
        }
        NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
        localTileEntity.b(localNBTTagCompound2);
        
        if (!a(localNBTTagCompound1, localNBTTagCompound2)) {
          throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
        }
      }
      
      paramICommandListener.sendMessage(new ChatMessage("commands.testforblock.success", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
      
      return;
    }
    
    throw new ExceptionUsage("commands.testforblock.usage", new Object[0]);
  }
  
  public boolean a(NBTBase paramNBTBase1, NBTBase paramNBTBase2)
  {
    if (paramNBTBase1 == paramNBTBase2) return true;
    if (paramNBTBase1 == null) return true;
    if (paramNBTBase2 == null) return false;
    if (!paramNBTBase1.getClass().equals(paramNBTBase2.getClass())) { return false;
    }
    if ((paramNBTBase1 instanceof NBTTagCompound)) {
      NBTTagCompound localNBTTagCompound1 = (NBTTagCompound)paramNBTBase1;
      NBTTagCompound localNBTTagCompound2 = (NBTTagCompound)paramNBTBase2;
      
      for (String str : localNBTTagCompound1.c()) {
        NBTBase localNBTBase = localNBTTagCompound1.get(str);
        if (!a(localNBTBase, localNBTTagCompound2.get(str))) { return false;
        }
      }
      return true;
    }
    return paramNBTBase1.equals(paramNBTBase2);
  }
  

  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 4) {
      return a(paramArrayOfString, Block.REGISTRY.keySet());
    }
    
    return null;
  }
}
