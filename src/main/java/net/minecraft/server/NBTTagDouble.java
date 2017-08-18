package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;



public class NBTTagDouble
  extends NBTNumber
{
  private double data;
  
  NBTTagDouble() {}
  
  public NBTTagDouble(double paramDouble)
  {
    this.data = paramDouble;
  }
  
  void write(DataOutput paramDataOutput)
  {
    paramDataOutput.writeDouble(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    paramNBTReadLimiter.a(64L);
    this.data = paramDataInput.readDouble();
  }
  
  public byte getTypeId()
  {
    return 6;
  }
  
  public String toString()
  {
    return "" + this.data + "d";
  }
  
  public NBTBase clone()
  {
    return new NBTTagDouble(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagDouble localNBTTagDouble = (NBTTagDouble)paramObject;
      return this.data == localNBTTagDouble.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.data);
    return super.hashCode() ^ (int)(l ^ l >>> 32);
  }
  
  public long c()
  {
    return Math.floor(this.data);
  }
  
  public int d()
  {
    return MathHelper.floor(this.data);
  }
  
  public short e()
  {
    return (short)(MathHelper.floor(this.data) & 0xFFFF);
  }
  
  public byte f()
  {
    return (byte)(MathHelper.floor(this.data) & 0xFF);
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
