package net.minecraft.server;

import java.util.List;
import java.util.Set;





public class CommandSummon
  extends CommandAbstract
{
  public String getCommand()
  {
    return "summon";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.summon.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length >= 1)
    {
      String str = paramArrayOfString[0];
      double d1 = paramICommandListener.getChunkCoordinates().x + 0.5D;
      double d2 = paramICommandListener.getChunkCoordinates().y;
      double d3 = paramICommandListener.getChunkCoordinates().z + 0.5D;
      
      if (paramArrayOfString.length >= 4) {
        d1 = a(paramICommandListener, d1, paramArrayOfString[1]);
        d2 = a(paramICommandListener, d2, paramArrayOfString[2]);
        d3 = a(paramICommandListener, d3, paramArrayOfString[3]);
      }
      
      World localWorld = paramICommandListener.getWorld();
      if (!localWorld.isLoaded((int)d1, (int)d2, (int)d3)) {
        a(paramICommandListener, this, "commands.summon.outOfWorld", new Object[0]);
        return;
      }
      
      NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
      int i = 0;
      if (paramArrayOfString.length >= 5) {
        localObject1 = a(paramICommandListener, paramArrayOfString, 4);
        try {
          NBTBase localNBTBase = MojangsonParser.parse(((IChatBaseComponent)localObject1).c());
          if ((localNBTBase instanceof NBTTagCompound)) {
            localNBTTagCompound1 = (NBTTagCompound)localNBTBase;
            i = 1;
          } else {
            a(paramICommandListener, this, "commands.summon.tagError", new Object[] { "Not a valid tag" });
            return;
          }
        } catch (MojangsonParseException localMojangsonParseException) {
          a(paramICommandListener, this, "commands.summon.tagError", new Object[] { localMojangsonParseException.getMessage() });
          return;
        }
      }
      localNBTTagCompound1.setString("id", str);
      
      Object localObject1 = EntityTypes.a(localNBTTagCompound1, localWorld);
      if (localObject1 != null) {
        ((Entity)localObject1).setPositionRotation(d1, d2, d3, ((Entity)localObject1).yaw, ((Entity)localObject1).pitch);
        if (i == 0)
        {
          if ((localObject1 instanceof EntityInsentient)) {
            ((EntityInsentient)localObject1).prepare(null);
          }
        }
        localWorld.addEntity((Entity)localObject1);
        

        Object localObject2 = localObject1;
        NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1;
        while ((localObject2 != null) && (localNBTTagCompound2.hasKeyOfType("Riding", 10))) {
          Entity localEntity = EntityTypes.a(localNBTTagCompound2.getCompound("Riding"), localWorld);
          if (localEntity != null) {
            localEntity.setPositionRotation(d1, d2, d3, localEntity.yaw, localEntity.pitch);
            localWorld.addEntity(localEntity);
            ((Entity)localObject2).mount(localEntity);
          }
          localObject2 = localEntity;
          localNBTTagCompound2 = localNBTTagCompound2.getCompound("Riding");
        }
        a(paramICommandListener, this, "commands.summon.success", new Object[0]);
        return;
      }
      a(paramICommandListener, this, "commands.summon.failed", new Object[0]);
      return;
    }
    

    throw new ExceptionUsage("commands.summon.usage", new Object[0]);
  }
  
  public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1) {
      return a(paramArrayOfString, d());
    }
    
    return null;
  }
  
  protected String[] d() {
    return (String[])EntityTypes.b().toArray(new String[0]);
  }
}
