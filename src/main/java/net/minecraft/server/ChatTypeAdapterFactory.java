package net.minecraft.server;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.TypeAdapter;
import net.minecraft.util.com.google.gson.TypeAdapterFactory;
import net.minecraft.util.com.google.gson.reflect.TypeToken;








public class ChatTypeAdapterFactory
  implements TypeAdapterFactory
{
  public TypeAdapter create(Gson paramGson, TypeToken paramTypeToken)
  {
    Class localClass = paramTypeToken.getRawType();
    if (!localClass.isEnum()) {
      return null;
    }
    
    HashMap localHashMap = new HashMap();
    for (Object localObject : localClass.getEnumConstants()) {
      localHashMap.put(a(localObject), localObject);
    }
    
    return new ChatTypeAdapter(this, localHashMap);
  }
  


















  private String a(Object paramObject)
  {
    if ((paramObject instanceof Enum)) return ((Enum)paramObject).name().toLowerCase(Locale.US);
    return paramObject.toString().toLowerCase(Locale.US);
  }
}
