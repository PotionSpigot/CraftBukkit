package net.minecraft.server;

import java.util.List;
import java.util.Random;























public class BlockCauldron
  extends Block
{
  public BlockCauldron()
  {
    super(Material.ORE);
  }
  

























  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity)
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    float f = 0.125F;
    a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
    
    g();
  }
  
  public void g()
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public boolean c()
  {
    return false;
  }
  
  public int b()
  {
    return 24;
  }
  
  public boolean d()
  {
    return false;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    int i = b(paramWorld.getData(paramInt1, paramInt2, paramInt3));
    float f = paramInt2 + (6.0F + 3 * i) / 16.0F;
    
    if ((!paramWorld.isStatic) && (paramEntity.isBurning()) && (i > 0) && (paramEntity.boundingBox.b <= f)) {
      paramEntity.extinguish();
      
      a(paramWorld, paramInt1, paramInt2, paramInt3, i - 1);
    }
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramWorld.isStatic) {
      return true;
    }
    
    ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
    if (localItemStack == null) {
      return true;
    }
    
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = b(i);
    
    if (localItemStack.getItem() == Items.WATER_BUCKET) {
      if (j < 3) {
        if (!paramEntityHuman.abilities.canInstantlyBuild) {
          paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, new ItemStack(Items.BUCKET));
        }
        
        a(paramWorld, paramInt1, paramInt2, paramInt3, 3);
      }
      return true; }
    Object localObject; if (localItemStack.getItem() == Items.GLASS_BOTTLE) {
      if (j > 0) {
        if (!paramEntityHuman.abilities.canInstantlyBuild) {
          localObject = new ItemStack(Items.POTION, 1, 0);
          if (!paramEntityHuman.inventory.pickup((ItemStack)localObject)) {
            paramWorld.addEntity(new EntityItem(paramWorld, paramInt1 + 0.5D, paramInt2 + 1.5D, paramInt3 + 0.5D, (ItemStack)localObject));
          } else if ((paramEntityHuman instanceof EntityPlayer)) {
            ((EntityPlayer)paramEntityHuman).updateInventory(paramEntityHuman.defaultContainer);
          }
          
          localItemStack.count -= 1;
          if (localItemStack.count <= 0) {
            paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
          }
        }
        
        a(paramWorld, paramInt1, paramInt2, paramInt3, j - 1);
      }
    } else if ((j > 0) && ((localItemStack.getItem() instanceof ItemArmor)) && (((ItemArmor)localItemStack.getItem()).m_() == EnumArmorMaterial.CLOTH)) {
      localObject = (ItemArmor)localItemStack.getItem();
      ((ItemArmor)localObject).c(localItemStack);
      a(paramWorld, paramInt1, paramInt2, paramInt3, j - 1);
      return true;
    }
    
    return false;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramWorld.setData(paramInt1, paramInt2, paramInt3, MathHelper.a(paramInt4, 0, 3), 2);
    paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, this);
  }
  
  public void l(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.random.nextInt(20) != 1) { return;
    }
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if (i < 3) {
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i + 1, 2);
    }
  }
  
  public Item getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Items.CAULDRON;
  }
  





  public boolean isComplexRedstone()
  {
    return true;
  }
  
  public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    return b(i);
  }
  
  public static int b(int paramInt) {
    return paramInt;
  }
}
