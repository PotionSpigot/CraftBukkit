package net.minecraft.server;

import java.util.ArrayList;





























































































































































































































































































class MojangsonListParser
  extends MojangsonTypeParser
{
  protected ArrayList b = new ArrayList();
  
  public MojangsonListParser(String paramString) {
    this.a = paramString;
  }
  
  public NBTBase a()
  {
    NBTTagList localNBTTagList = new NBTTagList();
    
    for (MojangsonTypeParser localMojangsonTypeParser : this.b) {
      localNBTTagList.add(localMojangsonTypeParser.a());
    }
    
    return localNBTTagList;
  }
}
