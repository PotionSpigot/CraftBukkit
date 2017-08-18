package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;










































































































































































































































































































public class WorldGenNetherPiece15
  extends WorldGenNetherPiece1
{
  public WorldGenNetherPieceWeight b;
  public List c;
  public List d;
  public ArrayList e = new ArrayList();
  

  public WorldGenNetherPiece15() {}
  
  public WorldGenNetherPiece15(Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramRandom, paramInt1, paramInt2);
    
    this.c = new ArrayList();
    WorldGenNetherPieceWeight localWorldGenNetherPieceWeight; for (localWorldGenNetherPieceWeight : WorldGenNetherPieces.b()) {
      localWorldGenNetherPieceWeight.c = 0;
      this.c.add(localWorldGenNetherPieceWeight);
    }
    
    this.d = new ArrayList();
    for (localWorldGenNetherPieceWeight : WorldGenNetherPieces.c()) {
      localWorldGenNetherPieceWeight.c = 0;
      this.d.add(localWorldGenNetherPieceWeight);
    }
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
  }
}
