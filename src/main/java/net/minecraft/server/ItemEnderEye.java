package net.minecraft.server;

import java.util.Random;




public class ItemEnderEye
  extends Item
{
  public ItemEnderEye()
  {
    a(CreativeModeTab.f);
  }
  

  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if ((paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) && (localBlock == Blocks.ENDER_PORTAL_FRAME) && (!BlockEnderPortalFrame.b(i))) {
      if (paramWorld.isStatic) return true;
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i + 4, 2);
      paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, Blocks.ENDER_PORTAL_FRAME);
      paramItemStack.count -= 1;
      
      for (int j = 0; j < 16; j++) {
        double d1 = paramInt1 + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
        double d2 = paramInt2 + 0.8125F;
        double d3 = paramInt3 + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        
        paramWorld.addParticle("smoke", d1, d2, d3, d4, d5, d6);
      }
      

      j = i & 0x3;
      

      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 1;
      int i2 = Direction.g[j];
      int i4; int i5; for (int i3 = -2; i3 <= 2; i3++) {
        i4 = paramInt1 + Direction.a[i2] * i3;
        i5 = paramInt3 + Direction.b[i2] * i3;
        
        if (paramWorld.getType(i4, paramInt2, i5) == Blocks.ENDER_PORTAL_FRAME) {
          if (!BlockEnderPortalFrame.b(paramWorld.getData(i4, paramInt2, i5))) {
            i1 = 0;
            break;
          }
          m = i3;
          if (n == 0) {
            k = i3;
            n = 1;
          }
        }
      }
      

      if ((i1 != 0) && (m == k + 2))
      {

        for (i3 = k; i3 <= m; i3++) {
          i4 = paramInt1 + Direction.a[i2] * i3;
          i5 = paramInt3 + Direction.b[i2] * i3;
          i4 += Direction.a[j] * 4;
          i5 += Direction.b[j] * 4;
          
          if ((paramWorld.getType(i4, paramInt2, i5) != Blocks.ENDER_PORTAL_FRAME) || (!BlockEnderPortalFrame.b(paramWorld.getData(i4, paramInt2, i5)))) {
            i1 = 0;
            break;
          }
        }
        int i6;
        for (i3 = k - 1; i3 <= m + 1; i3 += 4) {
          for (i4 = 1; i4 <= 3; i4++) {
            i5 = paramInt1 + Direction.a[i2] * i3;
            i6 = paramInt3 + Direction.b[i2] * i3;
            i5 += Direction.a[j] * i4;
            i6 += Direction.b[j] * i4;
            
            if ((paramWorld.getType(i5, paramInt2, i6) != Blocks.ENDER_PORTAL_FRAME) || (!BlockEnderPortalFrame.b(paramWorld.getData(i5, paramInt2, i6)))) {
              i1 = 0;
              break;
            }
          }
        }
        if (i1 != 0)
        {
          for (i3 = k; i3 <= m; i3++) {
            for (i4 = 1; i4 <= 3; i4++) {
              i5 = paramInt1 + Direction.a[i2] * i3;
              i6 = paramInt3 + Direction.b[i2] * i3;
              i5 += Direction.a[j] * i4;
              i6 += Direction.b[j] * i4;
              
              paramWorld.setTypeAndData(i5, paramInt2, i6, Blocks.ENDER_PORTAL, 0, 2);
            }
          }
        }
      }
      
      return true;
    }
    return false;
  }
  

  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, false);
    if ((localMovingObjectPosition != null) && (localMovingObjectPosition.type == EnumMovingObjectType.BLOCK) && 
      (paramWorld.getType(localMovingObjectPosition.b, localMovingObjectPosition.c, localMovingObjectPosition.d) == Blocks.ENDER_PORTAL_FRAME)) {
      return paramItemStack;
    }
    

    if (!paramWorld.isStatic) {
      ChunkPosition localChunkPosition = paramWorld.b("Stronghold", (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ);
      if (localChunkPosition != null) {
        EntityEnderSignal localEntityEnderSignal = new EntityEnderSignal(paramWorld, paramEntityHuman.locX, paramEntityHuman.locY + 1.62D - paramEntityHuman.height, paramEntityHuman.locZ);
        localEntityEnderSignal.a(localChunkPosition.x, localChunkPosition.y, localChunkPosition.z);
        paramWorld.addEntity(localEntityEnderSignal);
        
        paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
        paramWorld.a(null, 1002, (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ, 0);
        if (!paramEntityHuman.abilities.canInstantlyBuild) {
          paramItemStack.count -= 1;
        }
      }
    }
    return paramItemStack;
  }
}
