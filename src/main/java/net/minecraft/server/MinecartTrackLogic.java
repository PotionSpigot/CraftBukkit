package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;











public class MinecartTrackLogic
{
  private World b;
  private int c;
  private int d;
  private int e;
  private final boolean f;
  private List g = new ArrayList();
  
  public MinecartTrackLogic(BlockMinecartTrackAbstract paramBlockMinecartTrackAbstract, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    this.b = paramWorld;
    this.c = paramInt1;
    this.d = paramInt2;
    this.e = paramInt3;
    
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (((BlockMinecartTrackAbstract)localBlock).a) {
      this.f = true;
      i &= 0xFFFFFFF7;
    } else {
      this.f = false;
    }
    a(i);
  }
  
  private void a(int paramInt)
  {
    this.g.clear();
    if (paramInt == 0) {
      this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
      this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
    } else if (paramInt == 1) {
      this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
    } else if (paramInt == 2) {
      this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c + 1, this.d + 1, this.e));
    } else if (paramInt == 3) {
      this.g.add(new ChunkPosition(this.c - 1, this.d + 1, this.e));
      this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
    } else if (paramInt == 4) {
      this.g.add(new ChunkPosition(this.c, this.d + 1, this.e - 1));
      this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
    } else if (paramInt == 5) {
      this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
      this.g.add(new ChunkPosition(this.c, this.d + 1, this.e + 1));
    } else if (paramInt == 6) {
      this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
    } else if (paramInt == 7) {
      this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
    } else if (paramInt == 8) {
      this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
    } else if (paramInt == 9) {
      this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
      this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
    }
  }
  
  private void b() {
    for (int i = 0; i < this.g.size(); i++) {
      MinecartTrackLogic localMinecartTrackLogic = a((ChunkPosition)this.g.get(i));
      if ((localMinecartTrackLogic == null) || (!localMinecartTrackLogic.a(this))) {
        this.g.remove(i--);
      } else {
        this.g.set(i, new ChunkPosition(localMinecartTrackLogic.c, localMinecartTrackLogic.d, localMinecartTrackLogic.e));
      }
    }
  }
  
  private boolean a(int paramInt1, int paramInt2, int paramInt3) {
    if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2, paramInt3)) return true;
    if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2 + 1, paramInt3)) return true;
    if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2 - 1, paramInt3)) return true;
    return false;
  }
  
  private MinecartTrackLogic a(ChunkPosition paramChunkPosition) {
    if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z);
    if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z);
    if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z);
    return null;
  }
  
  private boolean a(MinecartTrackLogic paramMinecartTrackLogic) {
    for (int i = 0; i < this.g.size(); i++) {
      ChunkPosition localChunkPosition = (ChunkPosition)this.g.get(i);
      if ((localChunkPosition.x == paramMinecartTrackLogic.c) && (localChunkPosition.z == paramMinecartTrackLogic.e)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean b(int paramInt1, int paramInt2, int paramInt3) {
    for (int i = 0; i < this.g.size(); i++) {
      ChunkPosition localChunkPosition = (ChunkPosition)this.g.get(i);
      if ((localChunkPosition.x == paramInt1) && (localChunkPosition.z == paramInt3)) {
        return true;
      }
    }
    return false;
  }
  
  protected int a() {
    int i = 0;
    
    if (a(this.c, this.d, this.e - 1)) i++;
    if (a(this.c, this.d, this.e + 1)) i++;
    if (a(this.c - 1, this.d, this.e)) i++;
    if (a(this.c + 1, this.d, this.e)) { i++;
    }
    return i;
  }
  
  private boolean b(MinecartTrackLogic paramMinecartTrackLogic) {
    if (a(paramMinecartTrackLogic)) return true;
    if (this.g.size() == 2) {
      return false;
    }
    if (this.g.isEmpty()) {
      return true;
    }
    
    return true;
  }
  
  private void c(MinecartTrackLogic paramMinecartTrackLogic) {
    this.g.add(new ChunkPosition(paramMinecartTrackLogic.c, paramMinecartTrackLogic.d, paramMinecartTrackLogic.e));
    
    boolean bool1 = b(this.c, this.d, this.e - 1);
    boolean bool2 = b(this.c, this.d, this.e + 1);
    boolean bool3 = b(this.c - 1, this.d, this.e);
    boolean bool4 = b(this.c + 1, this.d, this.e);
    
    int i = -1;
    
    if ((bool1) || (bool2)) i = 0;
    if ((bool3) || (bool4)) i = 1;
    if (!this.f) {
      if ((bool2) && (bool4) && (!bool1) && (!bool3)) i = 6;
      if ((bool2) && (bool3) && (!bool1) && (!bool4)) i = 7;
      if ((bool1) && (bool3) && (!bool2) && (!bool4)) i = 8;
      if ((bool1) && (bool4) && (!bool2) && (!bool3)) i = 9;
    }
    if (i == 0) {
      if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e - 1)) i = 4;
      if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e + 1)) i = 5;
    }
    if (i == 1) {
      if (BlockMinecartTrackAbstract.b_(this.b, this.c + 1, this.d + 1, this.e)) i = 2;
      if (BlockMinecartTrackAbstract.b_(this.b, this.c - 1, this.d + 1, this.e)) { i = 3;
      }
    }
    if (i < 0) { i = 0;
    }
    int j = i;
    if (this.f) {
      j = this.b.getData(this.c, this.d, this.e) & 0x8 | i;
    }
    
    this.b.setData(this.c, this.d, this.e, j, 3);
  }
  
  private boolean c(int paramInt1, int paramInt2, int paramInt3) {
    MinecartTrackLogic localMinecartTrackLogic = a(new ChunkPosition(paramInt1, paramInt2, paramInt3));
    if (localMinecartTrackLogic == null) return false;
    localMinecartTrackLogic.b();
    return localMinecartTrackLogic.b(this);
  }
  
  public void a(boolean paramBoolean1, boolean paramBoolean2) {
    boolean bool1 = c(this.c, this.d, this.e - 1);
    boolean bool2 = c(this.c, this.d, this.e + 1);
    boolean bool3 = c(this.c - 1, this.d, this.e);
    boolean bool4 = c(this.c + 1, this.d, this.e);
    
    int i = -1;
    
    if (((bool1) || (bool2)) && (!bool3) && (!bool4)) i = 0;
    if (((bool3) || (bool4)) && (!bool1) && (!bool2)) { i = 1;
    }
    if (!this.f) {
      if ((bool2) && (bool4) && (!bool1) && (!bool3)) i = 6;
      if ((bool2) && (bool3) && (!bool1) && (!bool4)) i = 7;
      if ((bool1) && (bool3) && (!bool2) && (!bool4)) i = 8;
      if ((bool1) && (bool4) && (!bool2) && (!bool3)) i = 9;
    }
    if (i == -1) {
      if ((bool1) || (bool2)) i = 0;
      if ((bool3) || (bool4)) { i = 1;
      }
      if (!this.f) {
        if (paramBoolean1) {
          if ((bool2) && (bool4)) i = 6;
          if ((bool3) && (bool2)) i = 7;
          if ((bool4) && (bool1)) i = 9;
          if ((bool1) && (bool3)) i = 8;
        } else {
          if ((bool1) && (bool3)) i = 8;
          if ((bool4) && (bool1)) i = 9;
          if ((bool3) && (bool2)) i = 7;
          if ((bool2) && (bool4)) { i = 6;
          }
        }
      }
    }
    if (i == 0) {
      if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e - 1)) i = 4;
      if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e + 1)) i = 5;
    }
    if (i == 1) {
      if (BlockMinecartTrackAbstract.b_(this.b, this.c + 1, this.d + 1, this.e)) i = 2;
      if (BlockMinecartTrackAbstract.b_(this.b, this.c - 1, this.d + 1, this.e)) { i = 3;
      }
    }
    if (i < 0) { i = 0;
    }
    a(i);
    
    int j = i;
    if (this.f) {
      j = this.b.getData(this.c, this.d, this.e) & 0x8 | i;
    }
    
    if ((paramBoolean2) || (this.b.getData(this.c, this.d, this.e) != j)) {
      this.b.setData(this.c, this.d, this.e, j, 3);
      for (int k = 0; k < this.g.size(); k++) {
        MinecartTrackLogic localMinecartTrackLogic = a((ChunkPosition)this.g.get(k));
        if (localMinecartTrackLogic != null) {
          localMinecartTrackLogic.b();
          
          if (localMinecartTrackLogic.b(this)) {
            localMinecartTrackLogic.c(this);
          }
        }
      }
    }
  }
}
