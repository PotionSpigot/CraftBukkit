package net.minecraft.server;

import java.util.UUID;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;





public final class GameProfileSerializer
{
  public static GameProfile deserialize(NBTTagCompound paramNBTTagCompound)
  {
    String str1 = null;
    String str2 = null;
    
    if (paramNBTTagCompound.hasKeyOfType("Name", 8)) {
      str1 = paramNBTTagCompound.getString("Name");
    }
    if (paramNBTTagCompound.hasKeyOfType("Id", 8)) {
      str2 = paramNBTTagCompound.getString("Id");
    }
    
    if ((!UtilColor.b(str1)) || (!UtilColor.b(str2))) {
      UUID localUUID;
      try {
        localUUID = UUID.fromString(str2);
      } catch (Throwable localThrowable) {
        localUUID = null;
      }
      GameProfile localGameProfile = new GameProfile(localUUID, str1);
      NBTTagCompound localNBTTagCompound1;
      if (paramNBTTagCompound.hasKeyOfType("Properties", 10)) {
        localNBTTagCompound1 = paramNBTTagCompound.getCompound("Properties");
        
        for (String str3 : localNBTTagCompound1.c()) {
          NBTTagList localNBTTagList = localNBTTagCompound1.getList(str3, 10);
          for (int i = 0; i < localNBTTagList.size(); i++) {
            NBTTagCompound localNBTTagCompound2 = localNBTTagList.get(i);
            String str4 = localNBTTagCompound2.getString("Value");
            
            if (localNBTTagCompound2.hasKeyOfType("Signature", 8)) {
              localGameProfile.getProperties().put(str3, new Property(str3, str4, localNBTTagCompound2.getString("Signature")));
            } else {
              localGameProfile.getProperties().put(str3, new Property(str3, str4));
            }
          }
        }
      }
      
      return localGameProfile;
    }
    return null;
  }
  
  public static void serialize(NBTTagCompound paramNBTTagCompound, GameProfile paramGameProfile)
  {
    if (!UtilColor.b(paramGameProfile.getName())) {
      paramNBTTagCompound.setString("Name", paramGameProfile.getName());
    }
    if (paramGameProfile.getId() != null) {
      paramNBTTagCompound.setString("Id", paramGameProfile.getId().toString());
    }
    if (!paramGameProfile.getProperties().isEmpty()) {
      NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
      for (String str : paramGameProfile.getProperties().keySet()) {
        NBTTagList localNBTTagList = new NBTTagList();
        for (Property localProperty : paramGameProfile.getProperties().get(str)) {
          NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
          localNBTTagCompound2.setString("Value", localProperty.getValue());
          if (localProperty.hasSignature()) {
            localNBTTagCompound2.setString("Signature", localProperty.getSignature());
          }
          localNBTTagList.add(localNBTTagCompound2);
        }
        localNBTTagCompound1.set(str, localNBTTagList);
      }
      paramNBTTagCompound.set("Properties", localNBTTagCompound1);
    }
  }
}
