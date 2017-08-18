package net.minecraft.server;

import java.util.List;
import java.util.Random;




























































































































































































































































































































































































































































































































































































































































































































































































public class WorldGenNetherPiece12
  extends WorldGenNetherPiece
{
  private boolean b;
  
  public WorldGenNetherPiece12() {}
  
  public WorldGenNetherPiece12(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
  }
  

  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    
    this.b = paramNBTTagCompound.getBoolean("Mob");
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    
    paramNBTTagCompound.setBoolean("Mob", this.b);
  }
  
  public static WorldGenNetherPiece12 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 8, 9, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece12(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.AIR, Blocks.AIR, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    a(paramWorld, paramStructureBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
    
    a(paramWorld, Blocks.NETHER_FENCE, 0, 1, 6, 3, paramStructureBoundingBox);
    a(paramWorld, Blocks.NETHER_FENCE, 0, 5, 6, 3, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 0, 6, 3, 0, 6, 8, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 6, 6, 3, 6, 6, 8, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 1, 6, 8, 5, 7, 8, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    a(paramWorld, paramStructureBoundingBox, 2, 8, 8, 4, 8, 8, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
    int j;
    if (!this.b) {
      i = a(5);j = a(3, 5);int k = b(3, 5);
      if (paramStructureBoundingBox.b(j, i, k)) {
        this.b = true;
        paramWorld.setTypeAndData(j, i, k, Blocks.MOB_SPAWNER, 0, 2);
        TileEntityMobSpawner localTileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(j, i, k);
        if (localTileEntityMobSpawner != null) { localTileEntityMobSpawner.getSpawner().setMobName("Blaze");
        }
      }
    }
    
    for (int i = 0; i <= 6; i++) {
      for (j = 0; j <= 6; j++) {
        b(paramWorld, Blocks.NETHER_BRICK, 0, i, -1, j, paramStructureBoundingBox);
      }
    }
    
    return true;
  }
}