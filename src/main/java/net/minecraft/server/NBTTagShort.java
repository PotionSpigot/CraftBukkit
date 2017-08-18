package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;


public class NBTTagShort
  extends NBTNumber
{
  private short data;
  
  public NBTTagShort() {}
  
  public NBTTagShort(short paramShort)
  {
    this.data = paramShort;
  }
  
  void write(DataOutput paramDataOutput)
  {
    paramDataOutput.writeShort(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    paramNBTReadLimiter.a(16L);
    this.data = paramDataInput.readShort();
  }
  
  public byte getTypeId()
  {
    return 2;
  }
  
  public String toString()
  {
    return "" + this.data + "s";
  }
  
  public NBTBase clone()
  {
    return new NBTTagShort(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagShort localNBTTagShort = (NBTTagShort)paramObject;
      return this.data == localNBTTagShort.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data;
  }
  
  public long c()
  {
    return this.data;
  }
  
  public int d()
  {
    return this.data;
  }
  
  public short e()
  {
    return this.data;
  }
  
  public byte f()
  {
    return (byte)(this.data & 0xFF);
  }
  
  public double g()
  {
    return this.data;
  }
  
  public float h()
  {
    return this.data;
  }
}
