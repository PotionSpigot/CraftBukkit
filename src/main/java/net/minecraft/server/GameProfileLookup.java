package net.minecraft.server;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;






































final class GameProfileLookup
  implements ProfileLookupCallback
{
  GameProfileLookup(GameProfile[] paramArrayOfGameProfile) {}
  
  public void onProfileLookupSucceeded(GameProfile paramGameProfile)
  {
    this.a[0] = paramGameProfile;
  }
  
  public void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException)
  {
    this.a[0] = null;
  }
}
