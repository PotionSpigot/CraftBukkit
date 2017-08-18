package net.minecraft.server;

public class BlockActionData {
  private int a;
  private int b;
  private int c;
  private Block d;
  private int e;
  private int f;
  
  public BlockActionData(int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4, int paramInt5) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.e = paramInt4;
    this.f = paramInt5;
    this.d = paramBlock;
  }
  
  public int a() {
    return this.a;
  }
  
  public int b() {
    return this.b;
  }
  
  public int c() {
    return this.c;
  }
  
  public int d() {
    return this.e;
  }
  
  public int e() {
    return this.f;
  }
  
  public Block f() {
    return this.d;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof BlockActionData)) {
      BlockActionData localBlockActionData = (BlockActionData)paramObject;
      return (this.a == localBlockActionData.a) && (this.b == localBlockActionData.b) && (this.c == localBlockActionData.c) && (this.e == localBlockActionData.e) && (this.f == localBlockActionData.f) && (this.d == localBlockActionData.d);
    }
    return false;
  }
  
  public String toString()
  {
    return "TE(" + this.a + "," + this.b + "," + this.c + ")," + this.e + "," + this.f + "," + this.d;
  }
}
