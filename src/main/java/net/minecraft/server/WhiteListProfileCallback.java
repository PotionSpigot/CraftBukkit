package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
import net.minecraft.util.com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import org.apache.logging.log4j.Logger;






































































































































































































final class WhiteListProfileCallback
  implements ProfileLookupCallback
{
  WhiteListProfileCallback(MinecraftServer paramMinecraftServer, WhiteList paramWhiteList) {}
  
  public void onProfileLookupSucceeded(GameProfile paramGameProfile)
  {
    this.a.getUserCache().a(paramGameProfile);
    this.b.add(new WhiteListEntry(paramGameProfile));
  }
  
  public void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException)
  {
    NameReferencingFileConverter.a().warn("Could not lookup user whitelist entry for " + paramGameProfile.getName(), paramException);
    if (!(paramException instanceof ProfileNotFoundException)) {
      throw new FileConversionException("Could not request user " + paramGameProfile.getName() + " from backend systems", paramException, null);
    }
  }
}
