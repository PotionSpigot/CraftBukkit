package net.minecraft.server;

public class Position implements IPosition {
  protected final double a;
  protected final double b;
  protected final double c;
  
  public Position(double paramDouble1, double paramDouble2, double paramDouble3) {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
  }
  
  public double getX()
  {
    return this.a;
  }
  
  public double getY()
  {
    return this.b;
  }
  
  public double getZ()
  {
    return this.c;
  }
}
