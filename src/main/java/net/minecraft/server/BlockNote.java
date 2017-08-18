package net.minecraft.server;




public class BlockNote
  extends BlockContainer
{
  public BlockNote()
  {
    super(Material.WOOD);
    a(CreativeModeTab.d);
  }
  

  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock)
  {
    boolean bool = paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3);
    TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntityNote != null) && (localTileEntityNote.i != bool)) {
      if (bool) {
        localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
      }
      localTileEntityNote.i = bool;
    }
  }
  

  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) return true;
    TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityNote != null) {
      localTileEntityNote.a();
      localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
    }
    return true;
  }
  
  public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    if (paramWorld.isStatic) return;
    TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityNote != null) localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public TileEntity a(World paramWorld, int paramInt)
  {
    return new TileEntityNote();
  }
  
  public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    float f = (float)Math.pow(2.0D, (paramInt5 - 12) / 12.0D);
    
    String str = "harp";
    if (paramInt4 == 1) str = "bd";
    if (paramInt4 == 2) str = "snare";
    if (paramInt4 == 3) str = "hat";
    if (paramInt4 == 4) { str = "bassattack";
    }
    paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "note." + str, 3.0F, f);
    paramWorld.addParticle("note", paramInt1 + 0.5D, paramInt2 + 1.2D, paramInt3 + 0.5D, paramInt5 / 24.0D, 0.0D, 0.0D);
    return true;
  }
}
