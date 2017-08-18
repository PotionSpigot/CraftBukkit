package net.minecraft.server;

import java.util.Random;


public class PossibleFishingResult
  extends WeightedRandomChoice
{
  private final ItemStack b;
  private float c;
  private boolean d;
  
  public PossibleFishingResult(ItemStack paramItemStack, int paramInt)
  {
    super(paramInt);
    this.b = paramItemStack;
  }
  
  public ItemStack a(Random paramRandom) {
    ItemStack localItemStack = this.b.cloneItemStack();
    
    if (this.c > 0.0F) {
      int i = (int)(this.c * this.b.l());
      int j = localItemStack.l() - paramRandom.nextInt(paramRandom.nextInt(i) + 1);
      if (j > i) j = i;
      if (j < 1) j = 1;
      localItemStack.setData(j);
    }
    
    if (this.d) {
      EnchantmentManager.a(paramRandom, localItemStack, 30);
    }
    
    return localItemStack;
  }
  
  public PossibleFishingResult a(float paramFloat) {
    this.c = paramFloat;
    return this;
  }
  
  public PossibleFishingResult a() {
    this.d = true;
    return this;
  }
}
