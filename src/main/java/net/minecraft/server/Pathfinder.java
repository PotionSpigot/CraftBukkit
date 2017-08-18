package net.minecraft.server;





public class Pathfinder
{
  private IBlockAccess a;
  


  private Path b = new Path();
  private IntHashMap c = new IntHashMap();
  
  private PathPoint[] d = new PathPoint[32];
  private boolean e;
  private boolean f;
  private boolean g;
  private boolean h;
  
  public Pathfinder(IBlockAccess paramIBlockAccess, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.a = paramIBlockAccess;
    this.e = paramBoolean1;
    this.f = paramBoolean2;
    this.g = paramBoolean3;
    this.h = paramBoolean4;
  }
  
  public PathEntity a(Entity paramEntity1, Entity paramEntity2, float paramFloat) {
    return a(paramEntity1, paramEntity2.locX, paramEntity2.boundingBox.b, paramEntity2.locZ, paramFloat);
  }
  
  public PathEntity a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    return a(paramEntity, paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, paramFloat);
  }
  
  private PathEntity a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
    this.b.a();
    this.c.c();
    
    boolean bool = this.g;
    int i = MathHelper.floor(paramEntity.boundingBox.b + 0.5D);
    if ((this.h) && (paramEntity.M())) {
      i = (int)paramEntity.boundingBox.b;
      localObject = this.a.getType(MathHelper.floor(paramEntity.locX), i, MathHelper.floor(paramEntity.locZ));
      while ((localObject == Blocks.WATER) || (localObject == Blocks.STATIONARY_WATER)) {
        i++;
        localObject = this.a.getType(MathHelper.floor(paramEntity.locX), i, MathHelper.floor(paramEntity.locZ));
      }
      bool = this.g;
      this.g = false;
    } else { i = MathHelper.floor(paramEntity.boundingBox.b + 0.5D);
    }
    Object localObject = a(MathHelper.floor(paramEntity.boundingBox.a), i, MathHelper.floor(paramEntity.boundingBox.c));
    PathPoint localPathPoint1 = a(MathHelper.floor(paramDouble1 - paramEntity.width / 2.0F), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3 - paramEntity.width / 2.0F));
    
    PathPoint localPathPoint2 = new PathPoint(MathHelper.d(paramEntity.width + 1.0F), MathHelper.d(paramEntity.length + 1.0F), MathHelper.d(paramEntity.width + 1.0F));
    PathEntity localPathEntity = a(paramEntity, (PathPoint)localObject, localPathPoint1, localPathPoint2, paramFloat);
    
    this.g = bool;
    return localPathEntity;
  }
  
  private PathEntity a(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat)
  {
    paramPathPoint1.e = 0.0F;
    paramPathPoint1.f = paramPathPoint1.b(paramPathPoint2);
    paramPathPoint1.g = paramPathPoint1.f;
    
    this.b.a();
    this.b.a(paramPathPoint1);
    
    Object localObject = paramPathPoint1;
    
    while (!this.b.e()) {
      PathPoint localPathPoint1 = this.b.c();
      
      if (localPathPoint1.equals(paramPathPoint2)) {
        return a(paramPathPoint1, paramPathPoint2);
      }
      
      if (localPathPoint1.b(paramPathPoint2) < ((PathPoint)localObject).b(paramPathPoint2)) {
        localObject = localPathPoint1;
      }
      localPathPoint1.i = true;
      
      int i = b(paramEntity, localPathPoint1, paramPathPoint3, paramPathPoint2, paramFloat);
      for (int j = 0; j < i; j++) {
        PathPoint localPathPoint2 = this.d[j];
        
        float f1 = localPathPoint1.e + localPathPoint1.b(localPathPoint2);
        if ((!localPathPoint2.a()) || (f1 < localPathPoint2.e)) {
          localPathPoint2.h = localPathPoint1;
          localPathPoint2.e = f1;
          localPathPoint2.f = localPathPoint2.b(paramPathPoint2);
          if (localPathPoint2.a()) {
            this.b.a(localPathPoint2, localPathPoint2.e + localPathPoint2.f);
          } else {
            localPathPoint2.g = (localPathPoint2.e + localPathPoint2.f);
            this.b.a(localPathPoint2);
          }
        }
      }
    }
    
    if (localObject == paramPathPoint1) return null;
    return a(paramPathPoint1, (PathPoint)localObject);
  }
  
  private int b(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat) {
    int i = 0;
    
    int j = 0;
    if (a(paramEntity, paramPathPoint1.a, paramPathPoint1.b + 1, paramPathPoint1.c, paramPathPoint2) == 1) { j = 1;
    }
    PathPoint localPathPoint1 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c + 1, paramPathPoint2, j);
    PathPoint localPathPoint2 = a(paramEntity, paramPathPoint1.a - 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, j);
    PathPoint localPathPoint3 = a(paramEntity, paramPathPoint1.a + 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, j);
    PathPoint localPathPoint4 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c - 1, paramPathPoint2, j);
    
    if ((localPathPoint1 != null) && (!localPathPoint1.i) && (localPathPoint1.a(paramPathPoint3) < paramFloat)) this.d[(i++)] = localPathPoint1;
    if ((localPathPoint2 != null) && (!localPathPoint2.i) && (localPathPoint2.a(paramPathPoint3) < paramFloat)) this.d[(i++)] = localPathPoint2;
    if ((localPathPoint3 != null) && (!localPathPoint3.i) && (localPathPoint3.a(paramPathPoint3) < paramFloat)) this.d[(i++)] = localPathPoint3;
    if ((localPathPoint4 != null) && (!localPathPoint4.i) && (localPathPoint4.a(paramPathPoint3) < paramFloat)) { this.d[(i++)] = localPathPoint4;
    }
    return i;
  }
  
  private PathPoint a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint, int paramInt4) {
    PathPoint localPathPoint = null;
    int i = a(paramEntity, paramInt1, paramInt2, paramInt3, paramPathPoint);
    if (i == 2) return a(paramInt1, paramInt2, paramInt3);
    if (i == 1) localPathPoint = a(paramInt1, paramInt2, paramInt3);
    if ((localPathPoint == null) && (paramInt4 > 0) && (i != -3) && (i != -4) && (a(paramEntity, paramInt1, paramInt2 + paramInt4, paramInt3, paramPathPoint) == 1)) {
      localPathPoint = a(paramInt1, paramInt2 + paramInt4, paramInt3);
      paramInt2 += paramInt4;
    }
    
    if (localPathPoint != null) {
      int j = 0;
      int k = 0;
      
      while (paramInt2 > 0) {
        k = a(paramEntity, paramInt1, paramInt2 - 1, paramInt3, paramPathPoint);
        if ((this.g) && (k == -1)) return null;
        if (k != 1) {
          break;
        }
        if (j++ >= paramEntity.ax()) return null;
        paramInt2--;
        if (paramInt2 > 0) { localPathPoint = a(paramInt1, paramInt2, paramInt3);
        }
      }
      if (k == -2) { return null;
      }
    }
    return localPathPoint;
  }
  
  private final PathPoint a(int paramInt1, int paramInt2, int paramInt3) {
    int i = PathPoint.a(paramInt1, paramInt2, paramInt3);
    PathPoint localPathPoint = (PathPoint)this.c.get(i);
    if (localPathPoint == null) {
      localPathPoint = new PathPoint(paramInt1, paramInt2, paramInt3);
      this.c.a(i, localPathPoint);
    }
    return localPathPoint;
  }
  







  public int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint)
  {
    return a(paramEntity, paramInt1, paramInt2, paramInt3, paramPathPoint, this.g, this.f, this.e);
  }
  
  public static int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    int i = 0;
    for (int j = paramInt1; j < paramInt1 + paramPathPoint.a; j++)
      for (int k = paramInt2; k < paramInt2 + paramPathPoint.b; k++)
        for (int m = paramInt3; m < paramInt3 + paramPathPoint.c; m++)
        {
          Block localBlock = paramEntity.world.getType(j, k, m);
          if (localBlock.getMaterial() != Material.AIR) {
            if (localBlock == Blocks.TRAP_DOOR) { i = 1;
            } else if ((localBlock == Blocks.WATER) || (localBlock == Blocks.STATIONARY_WATER)) {
              if (paramBoolean1) return -1;
              i = 1;
            } else if ((!paramBoolean3) && (localBlock == Blocks.WOODEN_DOOR)) {
              return 0;
            }
            int n = localBlock.b();
            
            if (paramEntity.world.getType(j, k, m).b() == 9) {
              int i1 = MathHelper.floor(paramEntity.locX);
              int i2 = MathHelper.floor(paramEntity.locY);
              int i3 = MathHelper.floor(paramEntity.locZ);
              if ((paramEntity.world.getType(i1, i2, i3).b() != 9) && (paramEntity.world.getType(i1, i2 - 1, i3).b() != 9))
              {


                return -3;
              }
              
            }
            else if ((!localBlock.b(paramEntity.world, j, k, m)) && (
              (!paramBoolean2) || (localBlock != Blocks.WOODEN_DOOR)))
            {
              if ((n == 11) || (localBlock == Blocks.FENCE_GATE) || (n == 32)) { return -3;
              }
              if (localBlock == Blocks.TRAP_DOOR) return -4;
              Material localMaterial = localBlock.getMaterial();
              if (localMaterial == Material.LAVA) {
                if (!paramEntity.P())
                  return -2;
              } else
                return 0;
            }
          }
        }
    return i != 0 ? 2 : 1;
  }
  
  private PathEntity a(PathPoint paramPathPoint1, PathPoint paramPathPoint2)
  {
    int i = 1;
    PathPoint localPathPoint = paramPathPoint2;
    while (localPathPoint.h != null) {
      i++;
      localPathPoint = localPathPoint.h;
    }
    
    PathPoint[] arrayOfPathPoint = new PathPoint[i];
    localPathPoint = paramPathPoint2;
    arrayOfPathPoint[(--i)] = localPathPoint;
    while (localPathPoint.h != null) {
      localPathPoint = localPathPoint.h;
      arrayOfPathPoint[(--i)] = localPathPoint;
    }
    return new PathEntity(arrayOfPathPoint);
  }
}
