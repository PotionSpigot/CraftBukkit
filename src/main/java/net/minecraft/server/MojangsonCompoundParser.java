package net.minecraft.server;

import java.util.ArrayList;










































































































































































































































































class MojangsonCompoundParser
  extends MojangsonTypeParser
{
  protected ArrayList b = new ArrayList();
  
  public MojangsonCompoundParser(String paramString) {
    this.a = paramString;
  }
  
  public NBTBase a()
  {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    
    for (MojangsonTypeParser localMojangsonTypeParser : this.b) {
      localNBTTagCompound.set(localMojangsonTypeParser.a, localMojangsonTypeParser.a());
    }
    
    return localNBTTagCompound;
  }
}
