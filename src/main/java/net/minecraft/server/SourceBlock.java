package net.minecraft.server;


public class SourceBlock
  implements ISourceBlock
{
  private final World a;
  
  private final int b;
  private final int c;
  private final int d;
  
  public SourceBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    this.a = paramWorld;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramInt3;
  }
  
  public World k()
  {
    return this.a;
  }
  
  public double getX()
  {
    return this.b + 0.5D;
  }
  
  public double getY()
  {
    return this.c + 0.5D;
  }
  
  public double getZ()
  {
    return this.d + 0.5D;
  }
  
  public int getBlockX()
  {
    return this.b;
  }
  
  public int getBlockY()
  {
    return this.c;
  }
  
  public int getBlockZ()
  {
    return this.d;
  }
  





  public int h()
  {
    return this.a.getData(this.b, this.c, this.d);
  }
  





  public TileEntity getTileEntity()
  {
    return this.a.getTileEntity(this.b, this.c, this.d);
  }
}
