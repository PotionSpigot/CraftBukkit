package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;






public class ItemRecord
  extends Item
{
  private static final Map b = new HashMap();
  public final String a;
  
  protected ItemRecord(String paramString)
  {
    this.a = paramString;
    this.maxStackSize = 1;
    a(CreativeModeTab.f);
    
    b.put(paramString, this);
  }
  





  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if ((paramWorld.getType(paramInt1, paramInt2, paramInt3) == Blocks.JUKEBOX) && (paramWorld.getData(paramInt1, paramInt2, paramInt3) == 0)) {
      if (paramWorld.isStatic) { return true;
      }
      ((BlockJukeBox)Blocks.JUKEBOX).b(paramWorld, paramInt1, paramInt2, paramInt3, paramItemStack);
      paramWorld.a(null, 1005, paramInt1, paramInt2, paramInt3, Item.getId(this));
      paramItemStack.count -= 1;
      return true;
    }
    return false;
  }
  









  public EnumItemRarity f(ItemStack paramItemStack)
  {
    return EnumItemRarity.RARE;
  }
}
