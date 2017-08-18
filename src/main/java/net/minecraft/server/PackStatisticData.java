package net.minecraft.server;








































class PackStatisticData
{
  private final long a;
  






































  private final int b;
  





































  private final double c;
  






































  private PackStatisticData(long paramLong, int paramInt, double paramDouble)
  {
    this.a = paramLong;
    this.b = paramInt;
    this.c = paramDouble;
  }
  
  public PackStatisticData a(long paramLong) {
    return new PackStatisticData(paramLong + this.a, this.b + 1, (paramLong + this.a) / (this.b + 1));
  }
  
  public long a() {
    return this.a;
  }
  
  public int b() {
    return this.b;
  }
  




  public String toString()
  {
    return "{totalBytes=" + this.a + ", count=" + this.b + ", averageBytes=" + this.c + '}';
  }
}
