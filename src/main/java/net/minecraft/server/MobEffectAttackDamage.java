package net.minecraft.server;

import org.github.paperspigot.PaperSpigotConfig;

public class MobEffectAttackDamage extends MobEffectList
{
  protected MobEffectAttackDamage(int i, boolean flag, int j) {
    super(i, flag, j);
  }
  
  public double a(int i, AttributeModifier attributemodifier)
  {
    return this.id == MobEffectList.WEAKNESS.id ? PaperSpigotConfig.weaknessEffectModifier * (i + 1) : PaperSpigotConfig.strengthEffectModifier * (i + 1);
  }
}
