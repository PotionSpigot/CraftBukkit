package net.minecraft.server;

public class MobEffectAbsorption
  extends MobEffectList
{
  protected MobEffectAbsorption(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    super(paramInt1, paramBoolean, paramInt2);
  }
  
  public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
  {
    paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() - 4 * (paramInt + 1));
    super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
  }
  
  public void b(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
  {
    paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() + 4 * (paramInt + 1));
    super.b(paramEntityLiving, paramAttributeMapBase, paramInt);
  }
}
