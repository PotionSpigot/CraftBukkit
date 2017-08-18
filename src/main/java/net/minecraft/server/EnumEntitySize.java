package net.minecraft.server;



























































































































public enum EnumEntitySize
{
  public int a(double paramDouble)
  {
    double d = paramDouble - (MathHelper.floor(paramDouble) + 0.5D);
    
    switch (EntitySizes.a[ordinal()]) {
    case 1: 
      if (d < 0.0D ? d < -0.3125D : d < 0.3125D) {
        return MathHelper.f(paramDouble * 32.0D);
      }
      
      return MathHelper.floor(paramDouble * 32.0D);
    case 2: 
      if (d < 0.0D ? d < -0.3125D : d < 0.3125D) {
        return MathHelper.floor(paramDouble * 32.0D);
      }
      
      return MathHelper.f(paramDouble * 32.0D);
    case 3: 
      if (d > 0.0D) {
        return MathHelper.floor(paramDouble * 32.0D);
      }
      
      return MathHelper.f(paramDouble * 32.0D);
    case 4: 
      if (d < 0.0D ? d < -0.1875D : d < 0.1875D) {
        return MathHelper.f(paramDouble * 32.0D);
      }
      
      return MathHelper.floor(paramDouble * 32.0D);
    case 5: 
      if (d < 0.0D ? d < -0.1875D : d < 0.1875D) {
        return MathHelper.floor(paramDouble * 32.0D);
      }
      
      return MathHelper.f(paramDouble * 32.0D);
    }
    
    if (d > 0.0D) {
      return MathHelper.f(paramDouble * 32.0D);
    }
    
    return MathHelper.floor(paramDouble * 32.0D);
  }
}
