package net.minecraft.server;

import java.util.Random;



public class WorldGenLiquids
  extends WorldGenerator
{
  private Block a;
  
  public WorldGenLiquids(Block paramBlock)
  {
    this.a = paramBlock;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.STONE) return false;
    if (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) != Blocks.STONE) { return false;
    }
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial() != Material.AIR) && (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.STONE)) { return false;
    }
    int i = 0;
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == Blocks.STONE) i++;
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == Blocks.STONE) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == Blocks.STONE) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == Blocks.STONE) { i++;
    }
    int j = 0;
    if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) j++;
    if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) j++;
    if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) j++;
    if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) { j++;
    }
    if ((i == 3) && (j == 1)) {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.a, 0, 2);
      paramWorld.d = true;
      this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
      paramWorld.d = false;
    }
    
    return true;
  }
}
