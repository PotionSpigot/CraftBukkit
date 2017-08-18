package net.minecraft.server;




public class EntityCaveSpider
  extends EntitySpider
{
  public EntityCaveSpider(World paramWorld)
  {
    super(paramWorld);
    a(0.7F, 0.5F);
  }
  
  protected void aD()
  {
    super.aD();
    
    getAttributeInstance(GenericAttributes.maxHealth).setValue(12.0D);
  }
  
  public boolean n(Entity paramEntity)
  {
    if (super.n(paramEntity))
    {
      if ((paramEntity instanceof EntityLiving)) {
        int i = 0;
        if (this.world.difficulty == EnumDifficulty.NORMAL) {
          i = 7;
        } else if (this.world.difficulty == EnumDifficulty.HARD) {
          i = 15;
        }
        
        if (i > 0) {
          ((EntityLiving)paramEntity).addEffect(new MobEffect(MobEffectList.POISON.id, i * 20, 0));
        }
      }
      
      return true;
    }
    return false;
  }
  

  public GroupDataEntity prepare(GroupDataEntity paramGroupDataEntity)
  {
    return paramGroupDataEntity;
  }
}
