package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;













public class ItemPotion
  extends Item
{
  private HashMap a = new HashMap();
  private static final Map b = new LinkedHashMap();
  



  public ItemPotion()
  {
    e(1);
    a(true);
    setMaxDurability(0);
    a(CreativeModeTab.k);
  }
  
  public List g(ItemStack paramItemStack) {
    if ((!paramItemStack.hasTag()) || (!paramItemStack.getTag().hasKeyOfType("CustomPotionEffects", 9))) {
      localObject = (List)this.a.get(Integer.valueOf(paramItemStack.getData()));
      
      if (localObject == null) {
        localObject = PotionBrewer.getEffects(paramItemStack.getData(), false);
        this.a.put(Integer.valueOf(paramItemStack.getData()), localObject);
      }
      
      return (List)localObject;
    }
    Object localObject = new ArrayList();
    NBTTagList localNBTTagList = paramItemStack.getTag().getList("CustomPotionEffects", 10);
    
    for (int i = 0; i < localNBTTagList.size(); i++) {
      NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
      MobEffect localMobEffect = MobEffect.b(localNBTTagCompound);
      if (localMobEffect != null) {
        ((List)localObject).add(localMobEffect);
      }
    }
    
    return (List)localObject;
  }
  
  public List c(int paramInt)
  {
    List localList = (List)this.a.get(Integer.valueOf(paramInt));
    if (localList == null) {
      localList = PotionBrewer.getEffects(paramInt, false);
      this.a.put(Integer.valueOf(paramInt), localList);
    }
    return localList;
  }
  
  public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!paramEntityHuman.abilities.canInstantlyBuild) { paramItemStack.count -= 1;
    }
    if (!paramWorld.isStatic) {
      List localList = g(paramItemStack);
      if (localList != null) {
        for (MobEffect localMobEffect : localList) {
          paramEntityHuman.addEffect(new MobEffect(localMobEffect));
        }
      }
    }
    if (!paramEntityHuman.abilities.canInstantlyBuild) {
      if (paramItemStack.count <= 0) {
        return new ItemStack(Items.GLASS_BOTTLE);
      }
      paramEntityHuman.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
    }
    

    return paramItemStack;
  }
  
  public int d_(ItemStack paramItemStack)
  {
    return 32;
  }
  
  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.DRINK;
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (g(paramItemStack.getData())) {
      if (!paramEntityHuman.abilities.canInstantlyBuild) paramItemStack.count -= 1;
      paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
      if (!paramWorld.isStatic) paramWorld.addEntity(new EntityPotion(paramWorld, paramEntityHuman, paramItemStack));
      return paramItemStack;
    }
    paramEntityHuman.a(paramItemStack, d_(paramItemStack));
    return paramItemStack;
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return false;
  }
  















  public static boolean g(int paramInt)
  {
    return (paramInt & 0x4000) != 0;
  }
  






























  public String n(ItemStack paramItemStack)
  {
    if (paramItemStack.getData() == 0) {
      return LocaleI18n.get("item.emptyPotion.name").trim();
    }
    
    String str1 = "";
    if (g(paramItemStack.getData())) {
      str1 = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
    }
    
    List localList = Items.POTION.g(paramItemStack);
    if ((localList != null) && (!localList.isEmpty())) {
      str2 = ((MobEffect)localList.get(0)).f();
      str2 = str2 + ".postfix";
      return str1 + LocaleI18n.get(str2).trim();
    }
    String str2 = PotionBrewer.c(paramItemStack.getData());
    return LocaleI18n.get(str2).trim() + " " + super.n(paramItemStack);
  }
}
