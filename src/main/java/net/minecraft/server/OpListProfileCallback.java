package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
import net.minecraft.util.com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import org.apache.logging.log4j.Logger;




























































































































































final class OpListProfileCallback
  implements ProfileLookupCallback
{
  OpListProfileCallback(MinecraftServer paramMinecraftServer, OpList paramOpList) {}
  
  public void onProfileLookupSucceeded(GameProfile paramGameProfile)
  {
    this.a.getUserCache().a(paramGameProfile);
    this.b.add(new OpListEntry(paramGameProfile, this.a.l()));
  }
  
  public void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException)
  {
    NameReferencingFileConverter.a().warn("Could not lookup oplist entry for " + paramGameProfile.getName(), paramException);
    if (!(paramException instanceof ProfileNotFoundException)) {
      throw new FileConversionException("Could not request user " + paramGameProfile.getName() + " from backend systems", paramException, null);
    }
  }
}
