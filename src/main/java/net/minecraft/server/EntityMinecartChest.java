package net.minecraft.server;




public class EntityMinecartChest
  extends EntityMinecartContainer
{
  public EntityMinecartChest(World paramWorld)
  {
    super(paramWorld);
  }
  
  public EntityMinecartChest(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
    super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
  }
  
  public void a(DamageSource paramDamageSource)
  {
    super.a(paramDamageSource);
    
    a(Item.getItemOf(Blocks.CHEST), 1, 0.0F);
  }
  
  public int getSize()
  {
    return 27;
  }
  
  public int m()
  {
    return 1;
  }
  
  public Block o()
  {
    return Blocks.CHEST;
  }
  
  public int s()
  {
    return 8;
  }
}
