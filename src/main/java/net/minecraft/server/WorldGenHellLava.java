package net.minecraft.server;

import java.util.Random;



public class WorldGenHellLava
  extends WorldGenerator
{
  private Block a;
  private boolean b;
  
  public WorldGenHellLava(Block paramBlock, boolean paramBoolean)
  {
    this.a = paramBlock;
    this.b = paramBoolean;
  }
  
  public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.NETHERRACK) return false;
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial() != Material.AIR) && (paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.NETHERRACK)) { return false;
    }
    int i = 0;
    if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == Blocks.NETHERRACK) i++;
    if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == Blocks.NETHERRACK) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == Blocks.NETHERRACK) i++;
    if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == Blocks.NETHERRACK) i++;
    if (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) == Blocks.NETHERRACK) { i++;
    }
    int j = 0;
    if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) j++;
    if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) j++;
    if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) j++;
    if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) j++;
    if (paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3)) { j++;
    }
    if (((!this.b) && (i == 4) && (j == 1)) || (i == 5)) {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.a, 0, 2);
      paramWorld.d = true;
      this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
      paramWorld.d = false;
    }
    
    return true;
  }
}
