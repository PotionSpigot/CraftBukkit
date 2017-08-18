package net.minecraft.server;

public class MobEffectHealthBoost
  extends MobEffectList
{
  public MobEffectHealthBoost(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    super(paramInt1, paramBoolean, paramInt2);
  }
  
  public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
  {
    super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
    if (paramEntityLiving.getHealth() > paramEntityLiving.getMaxHealth()) {
      paramEntityLiving.setHealth(paramEntityLiving.getMaxHealth());
    }
  }
}
