package net.minecraft.server;


public class WorldGenFlatLayerInfo
{
  private Block a;
  private int b = 1;
  private int c;
  private int d;
  
  public WorldGenFlatLayerInfo(int paramInt, Block paramBlock) {
    this.b = paramInt;
    this.a = paramBlock;
  }
  
  public WorldGenFlatLayerInfo(int paramInt1, Block paramBlock, int paramInt2) {
    this(paramInt1, paramBlock);
    this.c = paramInt2;
  }
  
  public int a() {
    return this.b;
  }
  



  public Block b()
  {
    return this.a;
  }
  



  public int c()
  {
    return this.c;
  }
  



  public int d()
  {
    return this.d;
  }
  
  public void c(int paramInt) {
    this.d = paramInt;
  }
  
  public String toString()
  {
    String str = Integer.toString(Block.getId(this.a));
    
    if (this.b > 1) {
      str = this.b + "x" + str;
    }
    if (this.c > 0) {
      str = str + ":" + this.c;
    }
    
    return str;
  }
}
