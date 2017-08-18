package net.minecraft.server;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
import net.minecraft.util.com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import org.apache.logging.log4j.Logger;




































































final class GameProfileBanListEntryConverter
  implements ProfileLookupCallback
{
  GameProfileBanListEntryConverter(MinecraftServer paramMinecraftServer, Map paramMap, GameProfileBanList paramGameProfileBanList) {}
  
  public void onProfileLookupSucceeded(GameProfile paramGameProfile)
  {
    this.a.getUserCache().a(paramGameProfile);
    String[] arrayOfString = (String[])this.b.get(paramGameProfile.getName().toLowerCase(Locale.ROOT));
    if (arrayOfString == null) {
      NameReferencingFileConverter.a().warn("Could not convert user banlist entry for " + paramGameProfile.getName());
      throw new FileConversionException("Profile not in the conversionlist", null);
    }
    
    Date localDate1 = arrayOfString.length > 1 ? NameReferencingFileConverter.a(arrayOfString[1], null) : null;
    String str1 = arrayOfString.length > 2 ? arrayOfString[2] : null;
    Date localDate2 = arrayOfString.length > 3 ? NameReferencingFileConverter.a(arrayOfString[3], null) : null;
    String str2 = arrayOfString.length > 4 ? arrayOfString[4] : null;
    this.c.add(new GameProfileBanEntry(paramGameProfile, localDate1, str1, localDate2, str2));
  }
  
  public void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException)
  {
    NameReferencingFileConverter.a().warn("Could not lookup user banlist entry for " + paramGameProfile.getName(), paramException);
    if (!(paramException instanceof ProfileNotFoundException)) {
      throw new FileConversionException("Could not request user " + paramGameProfile.getName() + " from backend systems", paramException, null);
    }
  }
}
