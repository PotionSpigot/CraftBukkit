package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;


public class NBTTagInt
  extends NBTNumber
{
  private int data;
  
  NBTTagInt() {}
  
  public NBTTagInt(int paramInt)
  {
    this.data = paramInt;
  }
  
  void write(DataOutput paramDataOutput)
  {
    paramDataOutput.writeInt(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    paramNBTReadLimiter.a(32L);
    this.data = paramDataInput.readInt();
  }
  
  public byte getTypeId()
  {
    return 3;
  }
  
  public String toString()
  {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagInt(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagInt localNBTTagInt = (NBTTagInt)paramObject;
      return this.data == localNBTTagInt.data;
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
    return (short)(this.data & 0xFFFF);
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
