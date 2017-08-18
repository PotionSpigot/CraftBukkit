package net.minecraft.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;




































































public class WorldGenVillageStart
  extends StructureStart
{
  private boolean c;
  
  public WorldGenVillageStart() {}
  
  public WorldGenVillageStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2);
    
    List localList1 = WorldGenVillagePieces.a(paramRandom, paramInt3);
    
    WorldGenVillageStartPiece localWorldGenVillageStartPiece = new WorldGenVillageStartPiece(paramWorld.getWorldChunkManager(), 0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2, localList1, paramInt3);
    this.a.add(localWorldGenVillageStartPiece);
    localWorldGenVillageStartPiece.a(localWorldGenVillageStartPiece, this.a, paramRandom);
    
    List localList2 = localWorldGenVillageStartPiece.j;
    List localList3 = localWorldGenVillageStartPiece.i;
    while ((!localList2.isEmpty()) || (!localList3.isEmpty()))
    {

      if (localList2.isEmpty()) {
        i = paramRandom.nextInt(localList3.size());
        localObject = (StructurePiece)localList3.remove(i);
        ((StructurePiece)localObject).a(localWorldGenVillageStartPiece, this.a, paramRandom);
      } else {
        i = paramRandom.nextInt(localList2.size());
        localObject = (StructurePiece)localList2.remove(i);
        ((StructurePiece)localObject).a(localWorldGenVillageStartPiece, this.a, paramRandom);
      }
    }
    
    c();
    
    int i = 0;
    for (Object localObject = this.a.iterator(); ((Iterator)localObject).hasNext();) { StructurePiece localStructurePiece = (StructurePiece)((Iterator)localObject).next();
      if (!(localStructurePiece instanceof WorldGenVillageRoadPiece)) {
        i++;
      }
    }
    this.c = (i > 2);
  }
  
  public boolean d()
  {
    return this.c;
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    
    paramNBTTagCompound.setBoolean("Valid", this.c);
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.c = paramNBTTagCompound.getBoolean("Valid");
  }
}
