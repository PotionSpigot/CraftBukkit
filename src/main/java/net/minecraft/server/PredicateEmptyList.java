package net.minecraft.server;

import net.minecraft.util.com.google.common.base.Predicate;













































final class PredicateEmptyList
  implements Predicate
{
  public boolean a(String paramString)
  {
    return !UtilColor.b(paramString);
  }
}
