package net.minecraft.server;

import java.util.List;
import java.util.Random;

abstract class WorldGenVillagePiece extends StructurePiece
{
  protected int k = -1;
  private int a;
  private boolean b;
  
  public WorldGenVillagePiece() {}
  
  protected WorldGenVillagePiece(WorldGenVillageStartPiece worldgenvillagestartpiece, int i) {
    super(i);
    if (worldgenvillagestartpiece != null) {
      this.b = worldgenvillagestartpiece.b;
    }
  }
  
  protected void a(NBTTagCompound nbttagcompound) {
    nbttagcompound.setInt("HPos", this.k);
    nbttagcompound.setInt("VCount", this.a);
    nbttagcompound.setBoolean("Desert", this.b);
  }
  
  protected void b(NBTTagCompound nbttagcompound) {
    this.k = nbttagcompound.getInt("HPos");
    this.a = nbttagcompound.getInt("VCount");
    this.b = nbttagcompound.getBoolean("Desert");
  }
  
  protected StructurePiece a(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j) {
    switch (this.g) {
    case 0: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a - 1, this.f.b + i, this.f.c + j, 1, d());
    
    case 1: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.c - 1, 2, d());
    
    case 2: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a - 1, this.f.b + i, this.f.c + j, 1, d());
    
    case 3: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.c - 1, 2, d());
    }
    
    return null;
  }
  
  protected StructurePiece b(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j)
  {
    switch (this.g) {
    case 0: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.d + 1, this.f.b + i, this.f.c + j, 3, d());
    
    case 1: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.f + 1, 0, d());
    
    case 2: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.d + 1, this.f.b + i, this.f.c + j, 3, d());
    
    case 3: 
      return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.f + 1, 0, d());
    }
    
    return null;
  }
  
  protected int b(World world, StructureBoundingBox structureboundingbox)
  {
    int i = 0;
    int j = 0;
    
    for (int k = this.f.c; k <= this.f.f; k++) {
      for (int l = this.f.a; l <= this.f.d; l++) {
        if (structureboundingbox.b(l, 64, k)) {
          i += Math.max(world.i(l, k), world.worldProvider.getSeaLevel());
          j++;
        }
      }
    }
    
    if (j == 0) {
      return -1;
    }
    return i / j;
  }
  
  protected static boolean a(StructureBoundingBox structureboundingbox)
  {
    return (structureboundingbox != null) && (structureboundingbox.b > 10);
  }
  
  protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
    if (this.a < l) {
      for (int i1 = this.a; i1 < l; i1++) {
        int j1 = a(i + i1, k);
        int k1 = a(j);
        int l1 = b(i + i1, k);
        
        if (!structureboundingbox.b(j1, k1, l1)) {
          break;
        }
        
        this.a += 1;
        EntityVillager entityvillager = new EntityVillager(world, b(i1));
        
        entityvillager.setPositionRotation(j1 + 0.5D, k1, l1 + 0.5D, 0.0F, 0.0F);
        world.addEntity(entityvillager, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
      }
    }
  }
  
  protected int b(int i) {
    return 0;
  }
  
  protected Block b(Block block, int i) {
    if (this.b) {
      if ((block == Blocks.LOG) || (block == Blocks.LOG2)) {
        return Blocks.SANDSTONE;
      }
      
      if (block == Blocks.COBBLESTONE) {
        return Blocks.SANDSTONE;
      }
      
      if (block == Blocks.WOOD) {
        return Blocks.SANDSTONE;
      }
      
      if (block == Blocks.WOOD_STAIRS) {
        return Blocks.SANDSTONE_STAIRS;
      }
      
      if (block == Blocks.COBBLESTONE_STAIRS) {
        return Blocks.SANDSTONE_STAIRS;
      }
      
      if (block == Blocks.GRAVEL) {
        return Blocks.SANDSTONE;
      }
    }
    
    return block;
  }
  
  protected int c(Block block, int i) {
    if (this.b) {
      if ((block == Blocks.LOG) || (block == Blocks.LOG2)) {
        return 0;
      }
      
      if (block == Blocks.COBBLESTONE) {
        return 0;
      }
      
      if (block == Blocks.WOOD) {
        return 2;
      }
    }
    
    return i;
  }
  
  protected void a(World world, Block block, int i, int j, int k, int l, StructureBoundingBox structureboundingbox) {
    Block block1 = b(block, i);
    int i1 = c(block, i);
    
    super.a(world, block1, i1, j, k, l, structureboundingbox);
  }
  
  protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, Block block, Block block1, boolean flag) {
    Block block2 = b(block, 0);
    int k1 = c(block, 0);
    Block block3 = b(block1, 0);
    int l1 = c(block1, 0);
    
    super.a(world, structureboundingbox, i, j, k, l, i1, j1, block2, k1, block3, l1, flag);
  }
  
  protected void b(World world, Block block, int i, int j, int k, int l, StructureBoundingBox structureboundingbox) {
    Block block1 = b(block, i);
    int i1 = c(block, i);
    
    super.b(world, block1, i1, j, k, l, structureboundingbox);
  }
}
