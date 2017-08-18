package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;


public class NBTTagLong
  extends NBTNumber
{
  private long data;
  
  NBTTagLong() {}
  
  public NBTTagLong(long paramLong)
  {
    this.data = paramLong;
  }
  
  void write(DataOutput paramDataOutput)
  {
    paramDataOutput.writeLong(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    paramNBTReadLimiter.a(64L);
    this.data = paramDataInput.readLong();
  }
  
  public byte getTypeId()
  {
    return 4;
  }
  
  public String toString()
  {
    return "" + this.data + "L";
  }
  
  public NBTBase clone()
  {
    return new NBTTagLong(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagLong localNBTTagLong = (NBTTagLong)paramObject;
      return this.data == localNBTTagLong.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
  }
  
  public long c()
  {
    return this.data;
  }
  
  public int d()
  {
    return (int)(this.data & 0xFFFFFFFFFFFFFFFF);
  }
  
  public short e()
  {
    return (short)(int)(this.data & 0xFFFF);
  }
  
  public byte f()
  {
    return (byte)(int)(this.data & 0xFF);
  }
  
  public double g()
  {
    return this.data;
  }
  
  public float h()
  {
    return (float)this.data;
  }
}
