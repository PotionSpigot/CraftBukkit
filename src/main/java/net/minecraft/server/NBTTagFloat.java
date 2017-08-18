package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;



public class NBTTagFloat
  extends NBTNumber
{
  private float data;
  
  NBTTagFloat() {}
  
  public NBTTagFloat(float paramFloat)
  {
    this.data = paramFloat;
  }
  
  void write(DataOutput paramDataOutput)
  {
    paramDataOutput.writeFloat(this.data);
  }
  
  void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter)
  {
    paramNBTReadLimiter.a(32L);
    this.data = paramDataInput.readFloat();
  }
  
  public byte getTypeId()
  {
    return 5;
  }
  
  public String toString()
  {
    return "" + this.data + "f";
  }
  
  public NBTBase clone()
  {
    return new NBTTagFloat(this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagFloat localNBTTagFloat = (NBTTagFloat)paramObject;
      return this.data == localNBTTagFloat.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ Float.floatToIntBits(this.data);
  }
  
  public long c()
  {
    return this.data;
  }
  
  public int d()
  {
    return MathHelper.d(this.data);
  }
  
  public short e()
  {
    return (short)(MathHelper.d(this.data) & 0xFFFF);
  }
  
  public byte f()
  {
    return (byte)(MathHelper.d(this.data) & 0xFF);
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
