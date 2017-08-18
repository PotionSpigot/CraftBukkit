package net.minecraft.server;





public class ItemBed
  extends Item
{
  public ItemBed()
  {
    a(CreativeModeTab.c);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) { return true;
    }
    if (paramInt4 != 1) {
      return false;
    }
    

    paramInt2++;
    
    BlockBed localBlockBed = (BlockBed)Blocks.BED;
    
    int i = MathHelper.floor(paramEntityHuman.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    int j = 0;
    int k = 0;
    
    if (i == 0) k = 1;
    if (i == 1) j = -1;
    if (i == 2) k = -1;
    if (i == 3) { j = 1;
    }
    if ((!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) || (!paramEntityHuman.a(paramInt1 + j, paramInt2, paramInt3 + k, paramInt4, paramItemStack))) { return false;
    }
    if ((paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) && (paramWorld.isEmpty(paramInt1 + j, paramInt2, paramInt3 + k)) && (World.a(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) && (World.a(paramWorld, paramInt1 + j, paramInt2 - 1, paramInt3 + k)))
    {
      paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, localBlockBed, i, 3);
      
      if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == localBlockBed) {
        paramWorld.setTypeAndData(paramInt1 + j, paramInt2, paramInt3 + k, localBlockBed, i + 8, 3);
      }
      
      paramItemStack.count -= 1;
      return true;
    }
    
    return false;
  }
}
