package net.minecraft.server;

import java.util.Random;

public class WorldGenPackedIce2
  extends WorldGenerator
{
  public boolean generate(World world, Random random, int i, int j, int k)
  {
    while ((world.isEmpty(i, j, k)) && (j > 2)) {
      j--;
    }
    
    if (world.getType(i, j, k) != Blocks.SNOW_BLOCK) {
      return false;
    }
    j += random.nextInt(4);
    int l = random.nextInt(4) + 7;
    int i1 = l / 4 + random.nextInt(2);
    
    if ((i1 > 1) && (random.nextInt(60) == 0)) {
      j += 10 + random.nextInt(30);
    }
    




    for (int j1 = 0; j1 < l; j1++) {
      float f = (1.0F - j1 / l) * i1;
      
      int k1 = MathHelper.f(f);
      
      for (int l1 = -k1; l1 <= k1; l1++) {
        float f1 = MathHelper.a(l1) - 0.25F;
        
        for (int i2 = -k1; i2 <= k1; i2++) {
          float f2 = MathHelper.a(i2) - 0.25F;
          
          if (((l1 == 0) && (i2 == 0)) || ((f1 * f1 + f2 * f2 <= f * f) && (((l1 != -k1) && (l1 != k1) && (i2 != -k1) && (i2 != k1)) || (random.nextFloat() <= 0.75F)))) {
            Block block = world.getType(i + l1, j + j1, k + i2);
            
            if ((block.getMaterial() == Material.AIR) || (block == Blocks.DIRT) || (block == Blocks.SNOW_BLOCK) || (block == Blocks.ICE)) {
              world.setTypeUpdate(i + l1, j + j1, k + i2, Blocks.PACKED_ICE);
            }
            
            if ((j1 != 0) && (k1 > 1)) {
              block = world.getType(i + l1, j - j1, k + i2);
              if ((block.getMaterial() == Material.AIR) || (block == Blocks.DIRT) || (block == Blocks.SNOW_BLOCK) || (block == Blocks.ICE)) {
                world.setTypeUpdate(i + l1, j - j1, k + i2, Blocks.PACKED_ICE);
              }
            }
          }
        }
      }
    }
    
    j1 = i1 - 1;
    if (j1 < 0) {
      j1 = 0;
    } else if (j1 > 1) {
      j1 = 1;
    }
    
    for (int j2 = -j1; j2 <= j1; j2++) {
      int k1 = -j1;
      
      while (k1 <= j1) {
        int l1 = j - 1;
        int k2 = 50;
        
        if ((Math.abs(j2) == 1) && (Math.abs(k1) == 1)) {
          k2 = random.nextInt(5);
        }
        

        while (l1 > 50) {
          Block block1 = world.getType(i + j2, l1, k + k1);
          
          if ((block1.getMaterial() != Material.AIR) && (block1 != Blocks.DIRT) && (block1 != Blocks.SNOW_BLOCK) && (block1 != Blocks.ICE) && (block1 != Blocks.PACKED_ICE)) break;
          world.setTypeUpdate(i + j2, l1, k + k1, Blocks.PACKED_ICE);
          l1--;
          k2--;
          if (k2 <= 0) {
            l1 -= random.nextInt(5) + 1;
            k2 = random.nextInt(5);
          }
        }
        


        k1++;
      }
    }
    


    return true;
  }
}
