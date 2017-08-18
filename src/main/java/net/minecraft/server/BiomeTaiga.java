package net.minecraft.server;

import java.util.List;
import java.util.Random;




public class BiomeTaiga
  extends BiomeBase
{
  private static final WorldGenTaiga1 aC = new WorldGenTaiga1();
  private static final WorldGenTaiga2 aD = new WorldGenTaiga2(false);
  private static final WorldGenMegaTree aE = new WorldGenMegaTree(false, false);
  private static final WorldGenMegaTree aF = new WorldGenMegaTree(false, true);
  private static final WorldGenTaigaStructure aG = new WorldGenTaigaStructure(Blocks.MOSSY_COBBLESTONE, 0);
  

  private int aH;
  


  public BiomeTaiga(int paramInt1, int paramInt2)
  {
    super(paramInt1);
    this.aH = paramInt2;
    
    this.at.add(new BiomeMeta(EntityWolf.class, 8, 4, 4));
    
    this.ar.x = 10;
    if ((paramInt2 == 1) || (paramInt2 == 2)) {
      this.ar.z = 7;
      this.ar.A = 1;
      this.ar.B = 3;
    } else {
      this.ar.z = 1;
      this.ar.B = 1;
    }
  }
  

  public WorldGenTreeAbstract a(Random paramRandom)
  {
    if (((this.aH == 1) || (this.aH == 2)) && (paramRandom.nextInt(3) == 0)) {
      if ((this.aH == 2) || (paramRandom.nextInt(13) == 0)) {
        return aF;
      }
      return aE;
    }
    if (paramRandom.nextInt(3) == 0) {
      return aC;
    }
    return aD;
  }
  
  public WorldGenerator b(Random paramRandom)
  {
    if (paramRandom.nextInt(5) > 0) {
      return new WorldGenGrass(Blocks.LONG_GRASS, 2);
    }
    return new WorldGenGrass(Blocks.LONG_GRASS, 1); }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) { int j;
    int k;
    int m;
    if ((this.aH == 1) || (this.aH == 2)) {
      i = paramRandom.nextInt(3);
      for (j = 0; j < i; j++) {
        k = paramInt1 + paramRandom.nextInt(16) + 8;
        m = paramInt2 + paramRandom.nextInt(16) + 8;
        int n = paramWorld.getHighestBlockYAt(k, m);
        aG.generate(paramWorld, paramRandom, k, n, m);
      }
    }
    
    ae.a(3);
    for (int i = 0; i < 7; i++) {
      j = paramInt1 + paramRandom.nextInt(16) + 8;
      k = paramInt2 + paramRandom.nextInt(16) + 8;
      m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(j, k) + 32);
      ae.generate(paramWorld, paramRandom, j, m, k);
    }
    
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
  }
  
  public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfByte, int paramInt1, int paramInt2, double paramDouble)
  {
    if ((this.aH == 1) || (this.aH == 2)) {
      this.ai = Blocks.GRASS;
      this.aj = 0;
      this.ak = Blocks.DIRT;
      if (paramDouble > 1.75D) {
        this.ai = Blocks.DIRT;
        this.aj = 1;
      } else if (paramDouble > -0.95D) {
        this.ai = Blocks.DIRT;
        this.aj = 2;
      }
    }
    b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfByte, paramInt1, paramInt2, paramDouble);
  }
  
  protected BiomeBase k()
  {
    if (this.id == BiomeBase.MEGA_TAIGA.id) {
      return new BiomeTaiga(this.id + 128, 2).a(5858897, true).a("Mega Spruce Taiga").a(5159473).a(0.25F, 0.8F).a(new BiomeTemperature(this.am, this.an));
    }
    return super.k();
  }
}
