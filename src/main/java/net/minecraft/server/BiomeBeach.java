package net.minecraft.server;

import java.util.List;

public class BiomeBeach extends BiomeBase
{
  public BiomeBeach(int paramInt) {
    super(paramInt);
    

    this.at.clear();
    this.ai = Blocks.SAND;
    this.ak = Blocks.SAND;
    
    this.ar.x = 64537;
    this.ar.A = 0;
    this.ar.C = 0;
    this.ar.D = 0;
  }
}
