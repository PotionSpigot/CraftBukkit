package net.minecraft.server;

import java.util.List;
import java.util.Random;

































































































































































































public class WorldGenMineshaftCorridor
  extends StructurePiece
{
  private boolean a;
  private boolean b;
  private boolean c;
  private int d;
  
  public WorldGenMineshaftCorridor() {}
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setBoolean("hr", this.a);
    paramNBTTagCompound.setBoolean("sc", this.b);
    paramNBTTagCompound.setBoolean("hps", this.c);
    paramNBTTagCompound.setInt("Num", this.d);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    this.a = paramNBTTagCompound.getBoolean("hr");
    this.b = paramNBTTagCompound.getBoolean("sc");
    this.c = paramNBTTagCompound.getBoolean("hps");
    this.d = paramNBTTagCompound.getInt("Num");
  }
  
  public WorldGenMineshaftCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    this.g = paramInt2;
    this.f = paramStructureBoundingBox;
    this.a = (paramRandom.nextInt(3) == 0);
    this.b = ((!this.a) && (paramRandom.nextInt(23) == 0));
    
    if ((this.g == 2) || (this.g == 0)) {
      this.d = (paramStructureBoundingBox.d() / 5);
    } else {
      this.d = (paramStructureBoundingBox.b() / 5);
    }
  }
  
  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
    
    int i = paramRandom.nextInt(3) + 2;
    while (i > 0) {
      int j = i * 5;
      
      switch (paramInt4) {
      case 2: 
        localStructureBoundingBox.d = (paramInt1 + 2);
        localStructureBoundingBox.c = (paramInt3 - (j - 1));
        break;
      case 0: 
        localStructureBoundingBox.d = (paramInt1 + 2);
        localStructureBoundingBox.f = (paramInt3 + (j - 1));
        break;
      case 1: 
        localStructureBoundingBox.a = (paramInt1 - (j - 1));
        localStructureBoundingBox.f = (paramInt3 + 2);
        break;
      case 3: 
        localStructureBoundingBox.d = (paramInt1 + (j - 1));
        localStructureBoundingBox.f = (paramInt3 + 2);
      }
      
      
      if (StructurePiece.a(paramList, localStructureBoundingBox) == null) break;
      i--;
    }
    



    if (i > 0) {
      return localStructureBoundingBox;
    }
    
    return null;
  }
  
  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = d();
    int j = paramRandom.nextInt(4);
    switch (this.g) {
    case 2: 
      if (j <= 1) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, this.g, i);
      } else if (j == 2) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, 1, i);
      } else {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, 3, i);
      }
      break;
    case 0: 
      if (j <= 1) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, this.g, i);
      } else if (j == 2) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.f - 3, 1, i);
      } else {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.f - 3, 3, i);
      }
      break;
    case 1: 
      if (j <= 1) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, this.g, i);
      } else if (j == 2) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, 2, i);
      } else {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, 0, i);
      }
      break;
    case 3: 
      if (j <= 1) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b - 1 + paramRandom.nextInt(3), this.f.c, this.g, i);
      } else if (j == 2) {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d - 3, this.f.b - 1 + paramRandom.nextInt(3), this.f.c - 1, 2, i);
      } else {
        WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d - 3, this.f.b - 1 + paramRandom.nextInt(3), this.f.f + 1, 0, i);
      }
      
      break;
    }
    
    if (i < 8) { int k;
      int m; if ((this.g == 2) || (this.g == 0)) {
        for (k = this.f.c + 3; k + 3 <= this.f.f; k += 5) {
          m = paramRandom.nextInt(5);
          if (m == 0) {
            WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, k, 1, i + 1);
          } else if (m == 1) {
            WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, k, 3, i + 1);
          }
        }
      } else {
        for (k = this.f.a + 3; k + 3 <= this.f.d; k += 5) {
          m = paramRandom.nextInt(5);
          if (m == 0) {
            WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.f.b, this.f.c - 1, 2, i + 1);
          } else if (m == 1) {
            WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.f.b, this.f.f + 1, 0, i + 1);
          }
        }
      }
    }
  }
  
  protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt4)
  {
    int i = a(paramInt1, paramInt3);
    int j = a(paramInt2);
    int k = b(paramInt1, paramInt3);
    
    if ((paramStructureBoundingBox.b(i, j, k)) && 
      (paramWorld.getType(i, j, k).getMaterial() == Material.AIR)) {
      int m = paramRandom.nextBoolean() ? 1 : 0;
      paramWorld.setTypeAndData(i, j, k, Blocks.RAILS, a(Blocks.RAILS, m), 2);
      EntityMinecartChest localEntityMinecartChest = new EntityMinecartChest(paramWorld, i + 0.5F, j + 0.5F, k + 0.5F);
      StructurePieceTreasure.a(paramRandom, paramArrayOfStructurePieceTreasure, localEntityMinecartChest, paramInt4);
      paramWorld.addEntity(localEntityMinecartChest);
      return true;
    }
    

    return false;
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    
    int i = 0;
    int j = 2;
    int k = 0;
    int m = 2;
    int n = this.d * 5 - 1;
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 2, 1, n, Blocks.AIR, Blocks.AIR, false);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 0.8F, 0, 2, 0, 2, 2, n, Blocks.AIR, Blocks.AIR, false);
    
    if (this.b)
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.6F, 0, 0, 0, 2, 1, n, Blocks.WEB, Blocks.AIR, false);
    int i2;
    int i3;
    int i5;
    for (int i1 = 0; i1 < this.d; i1++)
    {
      i2 = 2 + i1 * 5;
      
      a(paramWorld, paramStructureBoundingBox, 0, 0, i2, 0, 1, i2, Blocks.FENCE, Blocks.AIR, false);
      a(paramWorld, paramStructureBoundingBox, 2, 0, i2, 2, 1, i2, Blocks.FENCE, Blocks.AIR, false);
      if (paramRandom.nextInt(4) == 0) {
        a(paramWorld, paramStructureBoundingBox, 0, 2, i2, 0, 2, i2, Blocks.WOOD, Blocks.AIR, false);
        a(paramWorld, paramStructureBoundingBox, 2, 2, i2, 2, 2, i2, Blocks.WOOD, Blocks.AIR, false);
      } else {
        a(paramWorld, paramStructureBoundingBox, 0, 2, i2, 2, 2, i2, Blocks.WOOD, Blocks.AIR, false);
      }
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, i2 - 1, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, i2 - 1, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, i2 + 1, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, i2 + 1, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, i2 - 2, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, i2 - 2, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, i2 + 2, Blocks.WEB, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, i2 + 2, Blocks.WEB, 0);
      
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, i2 - 1, Blocks.TORCH, 0);
      a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, i2 + 1, Blocks.TORCH, 0);
      
      if (paramRandom.nextInt(100) == 0) {
        a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 0, i2 - 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
      }
      if (paramRandom.nextInt(100) == 0) {
        a(paramWorld, paramStructureBoundingBox, paramRandom, 0, 0, i2 + 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
      }
      if ((this.b) && (!this.c)) {
        i3 = a(0);int i4 = i2 - 1 + paramRandom.nextInt(3);
        i5 = a(1, i4);
        i4 = b(1, i4);
        if (paramStructureBoundingBox.b(i5, i3, i4)) {
          this.c = true;
          paramWorld.setTypeAndData(i5, i3, i4, Blocks.MOB_SPAWNER, 0, 2);
          TileEntityMobSpawner localTileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(i5, i3, i4);
          if (localTileEntityMobSpawner != null) { localTileEntityMobSpawner.getSpawner().setMobName("CaveSpider");
          }
        }
      }
    }
    
    for (i1 = 0; i1 <= 2; i1++) {
      for (i2 = 0; i2 <= n; i2++) {
        i3 = -1;
        Block localBlock2 = a(paramWorld, i1, i3, i2, paramStructureBoundingBox);
        if (localBlock2.getMaterial() == Material.AIR) {
          i5 = -1;
          a(paramWorld, Blocks.WOOD, 0, i1, i5, i2, paramStructureBoundingBox);
        }
      }
    }
    
    if (this.a) {
      for (i1 = 0; i1 <= n; i1++) {
        Block localBlock1 = a(paramWorld, 1, -1, i1, paramStructureBoundingBox);
        if ((localBlock1.getMaterial() != Material.AIR) && (localBlock1.j())) {
          a(paramWorld, paramStructureBoundingBox, paramRandom, 0.7F, 1, 0, i1, Blocks.RAILS, a(Blocks.RAILS, 0));
        }
      }
    }
    
    return true;
  }
}
