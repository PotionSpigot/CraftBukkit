package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.gson.TypeAdapter;
import net.minecraft.util.com.google.gson.stream.JsonReader;
import net.minecraft.util.com.google.gson.stream.JsonToken;
import net.minecraft.util.com.google.gson.stream.JsonWriter;





















class ChatTypeAdapter
  extends TypeAdapter
{
  ChatTypeAdapter(ChatTypeAdapterFactory paramChatTypeAdapterFactory, Map paramMap) {}
  
  public void write(JsonWriter paramJsonWriter, Object paramObject)
  {
    if (paramObject == null) {
      paramJsonWriter.nullValue();
    } else {
      paramJsonWriter.value(ChatTypeAdapterFactory.a(this.b, paramObject));
    }
  }
  
  public Object read(JsonReader paramJsonReader)
  {
    if (paramJsonReader.peek() == JsonToken.NULL) {
      paramJsonReader.nextNull();
      return null;
    }
    return this.a.get(paramJsonReader.nextString());
  }
}
