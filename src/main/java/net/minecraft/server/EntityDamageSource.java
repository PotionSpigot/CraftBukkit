package net.minecraft.server;




public class EntityDamageSource
  extends DamageSource
{
  protected Entity p;
  


  public EntityDamageSource(String paramString, Entity paramEntity)
  {
    super(paramString);
    this.p = paramEntity;
  }
  
  public Entity getEntity()
  {
    return this.p;
  }
  
  public IChatBaseComponent getLocalizedDeathMessage(EntityLiving paramEntityLiving)
  {
    Object localObject = (this.p instanceof EntityLiving) ? ((EntityLiving)this.p).be() : null;
    String str1 = "death.attack." + this.translationIndex;
    String str2 = str1 + ".item";
    
    if ((localObject != null) && (((ItemStack)localObject).hasName()) && (LocaleI18n.c(str2))) {
      return new ChatMessage(str2, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName(), ((ItemStack)localObject).E() });
    }
    return new ChatMessage(str1, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName() });
  }
  

  public boolean r()
  {
    return (this.p != null) && ((this.p instanceof EntityLiving)) && (!(this.p instanceof EntityHuman));
  }
}
