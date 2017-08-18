package net.minecraft.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;






























































public class WorldGenMineshaftRoom
  extends StructurePiece
{
  private List a = new LinkedList();
  

  public WorldGenMineshaftRoom() {}
  
  public WorldGenMineshaftRoom(int paramInt1, Random paramRandom, int paramInt2, int paramInt3)
  {
    super(paramInt1);
    
    this.f = new StructureBoundingBox(paramInt2, 50, paramInt3, paramInt2 + 7 + paramRandom.nextInt(6), 54 + paramRandom.nextInt(6), paramInt3 + 7 + paramRandom.nextInt(6));
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = d();
    


    int j = this.f.c() - 3 - 1;
    if (j <= 0) {
      j = 1;
    }
    

    int k = 0;
    StructurePiece localStructurePiece; StructureBoundingBox localStructureBoundingBox; while (k < this.f.b()) {
      k += paramRandom.nextInt(this.f.b());
      if (k + 3 > this.f.b()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + k, this.f.b + paramRandom.nextInt(j) + 1, this.f.c - 1, 2, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.c();
        this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.f.c, localStructureBoundingBox.d, localStructureBoundingBox.e, this.f.c + 1));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.f.b()) {
      k += paramRandom.nextInt(this.f.b());
      if (k + 3 > this.f.b()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + k, this.f.b + paramRandom.nextInt(j) + 1, this.f.f + 1, 0, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.c();
        this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.f.f - 1, localStructureBoundingBox.d, localStructureBoundingBox.e, this.f.f));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.f.d()) {
      k += paramRandom.nextInt(this.f.d());
      if (k + 3 > this.f.d()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b + paramRandom.nextInt(j) + 1, this.f.c + k, 1, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.c();
        this.a.add(new StructureBoundingBox(this.f.a, localStructureBoundingBox.b, localStructureBoundingBox.c, this.f.a + 1, localStructureBoundingBox.e, localStructureBoundingBox.f));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.f.d()) {
      k += paramRandom.nextInt(this.f.d());
      if (k + 3 > this.f.d()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b + paramRandom.nextInt(j) + 1, this.f.c + k, 3, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.c();
        this.a.add(new StructureBoundingBox(this.f.d - 1, localStructureBoundingBox.b, localStructureBoundingBox.c, this.f.d, localStructureBoundingBox.e, localStructureBoundingBox.f));
      }
      k += 4;
    }
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c, this.f.d, this.f.b, this.f.f, Blocks.DIRT, Blocks.AIR, true);
    

    a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b + 1, this.f.c, this.f.d, Math.min(this.f.b + 3, this.f.e), this.f.f, Blocks.AIR, Blocks.AIR, false);
    
    for (StructureBoundingBox localStructureBoundingBox : this.a) {
      a(paramWorld, paramStructureBoundingBox, localStructureBoundingBox.a, localStructureBoundingBox.e - 2, localStructureBoundingBox.c, localStructureBoundingBox.d, localStructureBoundingBox.e, localStructureBoundingBox.f, Blocks.AIR, Blocks.AIR, false);
    }
    
    a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b + 4, this.f.c, this.f.d, this.f.e, this.f.f, Blocks.AIR, false);
    
    return true;
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    NBTTagList localNBTTagList = new NBTTagList();
    for (StructureBoundingBox localStructureBoundingBox : this.a) {
      localNBTTagList.add(localStructureBoundingBox.h());
    }
    paramNBTTagCompound.set("Entrances", localNBTTagList);
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    NBTTagList localNBTTagList = paramNBTTagCompound.getList("Entrances", 11);
    for (int i = 0; i < localNBTTagList.size(); i++) {
      this.a.add(new StructureBoundingBox(localNBTTagList.c(i)));
    }
  }
}
