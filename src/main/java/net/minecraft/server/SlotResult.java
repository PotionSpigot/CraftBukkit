package net.minecraft.server;


public class SlotResult
  extends Slot
{
  private final IInventory a;
  
  private EntityHuman b;
  
  private int c;
  
  public SlotResult(EntityHuman paramEntityHuman, IInventory paramIInventory1, IInventory paramIInventory2, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory2, paramInt1, paramInt2, paramInt3);
    this.b = paramEntityHuman;
    this.a = paramIInventory1;
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    return false;
  }
  
  public ItemStack a(int paramInt)
  {
    if (hasItem()) {
      this.c += Math.min(paramInt, getItem().count);
    }
    return super.a(paramInt);
  }
  
  protected void a(ItemStack paramItemStack, int paramInt)
  {
    this.c += paramInt;
    b(paramItemStack);
  }
  
  protected void b(ItemStack paramItemStack)
  {
    paramItemStack.a(this.b.world, this.b, this.c);
    this.c = 0;
    
    if (paramItemStack.getItem() == Item.getItemOf(Blocks.WORKBENCH)) this.b.a(AchievementList.h, 1);
    if ((paramItemStack.getItem() instanceof ItemPickaxe)) this.b.a(AchievementList.i, 1);
    if (paramItemStack.getItem() == Item.getItemOf(Blocks.FURNACE)) this.b.a(AchievementList.j, 1);
    if ((paramItemStack.getItem() instanceof ItemHoe)) this.b.a(AchievementList.l, 1);
    if (paramItemStack.getItem() == Items.BREAD) this.b.a(AchievementList.m, 1);
    if (paramItemStack.getItem() == Items.CAKE) this.b.a(AchievementList.n, 1);
    if (((paramItemStack.getItem() instanceof ItemPickaxe)) && (((ItemPickaxe)paramItemStack.getItem()).i() != EnumToolMaterial.WOOD)) this.b.a(AchievementList.o, 1);
    if ((paramItemStack.getItem() instanceof ItemSword)) this.b.a(AchievementList.r, 1);
    if (paramItemStack.getItem() == Item.getItemOf(Blocks.ENCHANTMENT_TABLE)) this.b.a(AchievementList.E, 1);
    if (paramItemStack.getItem() == Item.getItemOf(Blocks.BOOKSHELF)) this.b.a(AchievementList.G, 1);
  }
  
  public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
  {
    b(paramItemStack);
    
    for (int i = 0; i < this.a.getSize(); i++) {
      ItemStack localItemStack1 = this.a.getItem(i);
      if (localItemStack1 != null) {
        this.a.splitStack(i, 1);
        
        if (localItemStack1.getItem().u()) {
          ItemStack localItemStack2 = new ItemStack(localItemStack1.getItem().t());
          

          if ((!localItemStack1.getItem().l(localItemStack1)) || (!this.b.inventory.pickup(localItemStack2)))
          {



            if (this.a.getItem(i) == null) {
              this.a.setItem(i, localItemStack2);
            }
            else {
              this.b.drop(localItemStack2, false);
            }
          }
        }
      }
    }
  }
}
