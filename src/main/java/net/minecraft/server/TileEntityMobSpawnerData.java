package net.minecraft.server;

























































































public class TileEntityMobSpawnerData
  extends WeightedRandomChoice
{
  public final NBTTagCompound b;
  























































































  public final String c;
  























































































  public TileEntityMobSpawnerData(MobSpawnerAbstract paramMobSpawnerAbstract, NBTTagCompound paramNBTTagCompound)
  {
    super(paramNBTTagCompound.getInt("Weight"));
    
    NBTTagCompound localNBTTagCompound = paramNBTTagCompound.getCompound("Properties");
    String str = paramNBTTagCompound.getString("Type");
    
    if (str.equals("Minecart")) {
      if (localNBTTagCompound != null) {
        switch (localNBTTagCompound.getInt("Type")) {
        case 1: 
          str = "MinecartChest";
          break;
        case 2: 
          str = "MinecartFurnace";
          break;
        case 0: 
          str = "MinecartRideable";
        }
        
      } else {
        str = "MinecartRideable";
      }
    }
    
    this.b = localNBTTagCompound;
    this.c = str;
  }
  
  public TileEntityMobSpawnerData(MobSpawnerAbstract paramMobSpawnerAbstract, NBTTagCompound paramNBTTagCompound, String paramString) {
    super(1);
    
    if (paramString.equals("Minecart")) {
      if (paramNBTTagCompound != null) {
        switch (paramNBTTagCompound.getInt("Type")) {
        case 1: 
          paramString = "MinecartChest";
          break;
        case 2: 
          paramString = "MinecartFurnace";
          break;
        case 0: 
          paramString = "MinecartRideable";
        }
        
      } else {
        paramString = "MinecartRideable";
      }
    }
    
    this.b = paramNBTTagCompound;
    this.c = paramString;
  }
  
  public NBTTagCompound a() {
    NBTTagCompound localNBTTagCompound = new NBTTagCompound();
    
    localNBTTagCompound.set("Properties", this.b);
    localNBTTagCompound.setString("Type", this.c);
    localNBTTagCompound.setInt("Weight", this.a);
    
    return localNBTTagCompound;
  }
}
