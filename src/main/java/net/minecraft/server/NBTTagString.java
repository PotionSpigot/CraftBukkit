package net.minecraft.server;

import java.io.DataInput;

public class NBTTagString extends NBTBase
{
  private String data;
  
  public NBTTagString() {
    this.data = "";
  }
  
  public NBTTagString(String paramString) {
    this.data = paramString;
    if (paramString == null) throw new IllegalArgumentException("Empty string not allowed");
  }
  
  void write(java.io.DataOutput paramDataOutput)
  {
    paramDataOutput.writeUTF(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    this.data = paramDataInput.readUTF();
    paramNBTReadLimiter.a(16 * this.data.length());
  }
  
  public byte getTypeId()
  {
    return 8;
  }
  
  public String toString()
  {
    return "\"" + this.data + "\"";
  }
  
  public NBTBase clone()
  {
    return new NBTTagString(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagString localNBTTagString = (NBTTagString)paramObject;
      return ((this.data == null) && (localNBTTagString.data == null)) || ((this.data != null) && (this.data.equals(localNBTTagString.data)));
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data.hashCode();
  }
  
  public String a_()
  {
    return this.data;
  }
}
