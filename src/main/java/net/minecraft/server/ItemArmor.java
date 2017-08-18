package net.minecraft.server;

















public class ItemArmor
  extends Item
{
  private static final int[] m = { 11, 16, 15, 13 };
  


  private static final String[] n = { "leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay" };
  


  public static final String[] a = { "empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots" };
  


  private static final IDispenseBehavior o = new DispenseBehaviorArmor();
  















  public final int b;
  















  public final int c;
  














  public final int d;
  














  private final EnumArmorMaterial p;
  















  public ItemArmor(EnumArmorMaterial paramEnumArmorMaterial, int paramInt1, int paramInt2)
  {
    this.p = paramEnumArmorMaterial;
    this.b = paramInt2;
    this.d = paramInt1;
    this.c = paramEnumArmorMaterial.b(paramInt2);
    setMaxDurability(paramEnumArmorMaterial.a(paramInt2));
    this.maxStackSize = 1;
    a(CreativeModeTab.j);
    BlockDispenser.a.a(this, o);
  }
  
















  public int c()
  {
    return this.p.a();
  }
  
  public EnumArmorMaterial m_() {
    return this.p;
  }
  
  public boolean c_(ItemStack paramItemStack) {
    if (this.p != EnumArmorMaterial.CLOTH) return false;
    if (!paramItemStack.hasTag()) return false;
    if (!paramItemStack.getTag().hasKeyOfType("display", 10)) return false;
    if (!paramItemStack.getTag().getCompound("display").hasKeyOfType("color", 3)) { return false;
    }
    return true;
  }
  
  public int b(ItemStack paramItemStack) {
    if (this.p != EnumArmorMaterial.CLOTH) { return -1;
    }
    NBTTagCompound localNBTTagCompound1 = paramItemStack.getTag();
    if (localNBTTagCompound1 == null) return 10511680;
    NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1.getCompound("display");
    if (localNBTTagCompound2 == null) { return 10511680;
    }
    if (localNBTTagCompound2.hasKeyOfType("color", 3)) {
      return localNBTTagCompound2.getInt("color");
    }
    return 10511680;
  }
  








  public void c(ItemStack paramItemStack)
  {
    if (this.p != EnumArmorMaterial.CLOTH) return;
    NBTTagCompound localNBTTagCompound1 = paramItemStack.getTag();
    if (localNBTTagCompound1 == null) return;
    NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1.getCompound("display");
    if (localNBTTagCompound2.hasKey("color")) localNBTTagCompound2.remove("color");
  }
  
  public void b(ItemStack paramItemStack, int paramInt) {
    if (this.p != EnumArmorMaterial.CLOTH) { throw new UnsupportedOperationException("Can't dye non-leather!");
    }
    NBTTagCompound localNBTTagCompound1 = paramItemStack.getTag();
    
    if (localNBTTagCompound1 == null) {
      localNBTTagCompound1 = new NBTTagCompound();
      paramItemStack.setTag(localNBTTagCompound1);
    }
    
    NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1.getCompound("display");
    if (!localNBTTagCompound1.hasKeyOfType("display", 10)) localNBTTagCompound1.set("display", localNBTTagCompound2);
    localNBTTagCompound2.setInt("color", paramInt);
  }
  
  public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2)
  {
    if (this.p.b() == paramItemStack2.getItem()) {
      return true;
    }
    return super.a(paramItemStack1, paramItemStack2);
  }
  











  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    int i = EntityInsentient.b(paramItemStack) - 1;
    ItemStack localItemStack = paramEntityHuman.r(i);
    
    if (localItemStack == null) {
      paramEntityHuman.setEquipment(i, paramItemStack.cloneItemStack());
      paramItemStack.count = 0;
    }
    
    return paramItemStack;
  }
}
