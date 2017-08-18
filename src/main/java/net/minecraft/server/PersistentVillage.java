package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;







public class PersistentVillage
  extends PersistentBase
{
  private World world;
  private final List b = new ArrayList();
  private final List c = new ArrayList();
  private final List villages = new ArrayList();
  private int time;
  
  public PersistentVillage(String paramString) {
    super(paramString);
  }
  
  public PersistentVillage(World paramWorld) {
    super("villages");
    this.world = paramWorld;
    c();
  }
  
  public void a(World paramWorld) {
    this.world = paramWorld;
    
    for (Village localVillage : this.villages) {
      localVillage.a(paramWorld);
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3) {
    if (this.b.size() > 64) return;
    if (!d(paramInt1, paramInt2, paramInt3)) this.b.add(new ChunkCoordinates(paramInt1, paramInt2, paramInt3));
  }
  
  public void tick() {
    this.time += 1;
    for (Village localVillage : this.villages)
      localVillage.tick(this.time);
    e();
    f();
    g();
    
    if (this.time % 400 == 0) {
      c();
    }
  }
  
  private void e() {
    for (Iterator localIterator = this.villages.iterator(); localIterator.hasNext();) {
      Village localVillage = (Village)localIterator.next();
      if (localVillage.isAbandoned()) {
        localIterator.remove();
        c();
      }
    }
  }
  
  public List getVillages() {
    return this.villages;
  }
  
  public Village getClosestVillage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Object localObject = null;
    float f1 = Float.MAX_VALUE;
    for (Village localVillage : this.villages)
    {
      float f2 = localVillage.getCenter().e(paramInt1, paramInt2, paramInt3);
      if (f2 < f1)
      {
        float f3 = paramInt4 + localVillage.getSize();
        if (f2 <= f3 * f3)
        {
          localObject = localVillage;
          f1 = f2;
        } } }
    return (Village)localObject;
  }
  
  private void f() {
    if (this.b.isEmpty()) return;
    a((ChunkCoordinates)this.b.remove(0));
  }
  
  private void g()
  {
    for (int i = 0; i < this.c.size(); i++) {
      VillageDoor localVillageDoor = (VillageDoor)this.c.get(i);
      int j = 0;
      for (Object localObject = this.villages.iterator(); ((Iterator)localObject).hasNext();) { Village localVillage = (Village)((Iterator)localObject).next();
        int k = (int)localVillage.getCenter().e(localVillageDoor.locX, localVillageDoor.locY, localVillageDoor.locZ);
        int m = 32 + localVillage.getSize();
        if (k <= m * m) {
          localVillage.addDoor(localVillageDoor);
          j = 1;
        }
      }
      if (j == 0)
      {

        localObject = new Village(this.world);
        ((Village)localObject).addDoor(localVillageDoor);
        this.villages.add(localObject);
        c();
      } }
    this.c.clear();
  }
  
  private void a(ChunkCoordinates paramChunkCoordinates) {
    int i = 16;int j = 4;int k = 16;
    for (int m = paramChunkCoordinates.x - i; m < paramChunkCoordinates.x + i; m++) {
      for (int n = paramChunkCoordinates.y - j; n < paramChunkCoordinates.y + j; n++) {
        for (int i1 = paramChunkCoordinates.z - k; i1 < paramChunkCoordinates.z + k; i1++)
          if (e(m, n, i1))
          {
            VillageDoor localVillageDoor = b(m, n, i1);
            if (localVillageDoor == null) c(m, n, i1); else
              localVillageDoor.addedTime = this.time;
          }
      }
    }
  }
  
  private VillageDoor b(int paramInt1, int paramInt2, int paramInt3) {
    Object localObject;
    for (Iterator localIterator = this.c.iterator(); localIterator.hasNext(); 
        return localObject)
    {
      localObject = (VillageDoor)localIterator.next();
      if ((((VillageDoor)localObject).locX != paramInt1) || (((VillageDoor)localObject).locZ != paramInt3) || (Math.abs(((VillageDoor)localObject).locY - paramInt2) > 1)) {} }
    for (localIterator = this.villages.iterator(); localIterator.hasNext();) { localObject = (Village)localIterator.next();
      VillageDoor localVillageDoor = ((Village)localObject).e(paramInt1, paramInt2, paramInt3);
      if (localVillageDoor != null) return localVillageDoor;
    }
    return null;
  }
  
  private void c(int paramInt1, int paramInt2, int paramInt3) {
    int i = ((BlockDoor)Blocks.WOODEN_DOOR).e(this.world, paramInt1, paramInt2, paramInt3);
    int j; int k; if ((i == 0) || (i == 2)) {
      j = 0;
      for (k = -5; k < 0; k++)
        if (this.world.i(paramInt1 + k, paramInt2, paramInt3)) j--;
      for (k = 1; k <= 5; k++)
        if (this.world.i(paramInt1 + k, paramInt2, paramInt3)) j++;
      if (j != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, j > 0 ? -2 : 2, 0, this.time));
    } else {
      j = 0;
      for (k = -5; k < 0; k++)
        if (this.world.i(paramInt1, paramInt2, paramInt3 + k)) j--;
      for (k = 1; k <= 5; k++)
        if (this.world.i(paramInt1, paramInt2, paramInt3 + k)) j++;
      if (j != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, 0, j > 0 ? -2 : 2, this.time));
    }
  }
  
  private boolean d(int paramInt1, int paramInt2, int paramInt3) {
    for (Iterator localIterator = this.b.iterator(); localIterator.hasNext(); 
        return true)
    {
      ChunkCoordinates localChunkCoordinates = (ChunkCoordinates)localIterator.next();
      if ((localChunkCoordinates.x != paramInt1) || (localChunkCoordinates.y != paramInt2) || (localChunkCoordinates.z != paramInt3)) {} }
    return false;
  }
  
  private boolean e(int paramInt1, int paramInt2, int paramInt3) {
    return this.world.getType(paramInt1, paramInt2, paramInt3) == Blocks.WOODEN_DOOR;
  }
  

  public void a(NBTTagCompound paramNBTTagCompound)
  {
    this.time = paramNBTTagCompound.getInt("Tick");
    
    NBTTagList localNBTTagList = paramNBTTagCompound.getList("Villages", 10);
    for (int i = 0; i < localNBTTagList.size(); i++) {
      NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
      Village localVillage = new Village();
      localVillage.a(localNBTTagCompound);
      this.villages.add(localVillage);
    }
  }
  

  public void b(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setInt("Tick", this.time);
    NBTTagList localNBTTagList = new NBTTagList();
    for (Village localVillage : this.villages) {
      NBTTagCompound localNBTTagCompound = new NBTTagCompound();
      localVillage.b(localNBTTagCompound);
      localNBTTagList.add(localNBTTagCompound);
    }
    paramNBTTagCompound.set("Villages", localNBTTagList);
  }
}
