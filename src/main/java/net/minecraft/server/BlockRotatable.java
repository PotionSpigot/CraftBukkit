package net.minecraft.server;














public abstract class BlockRotatable
  extends Block
{
  protected BlockRotatable(Material paramMaterial)
  {
    super(paramMaterial);
  }
  
  public int b()
  {
    return 31;
  }
  
  public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5)
  {
    int i = paramInt5 & 0x3;
    int j = 0;
    
    switch (paramInt4) {
    case 2: 
    case 3: 
      j = 8;
      break;
    case 4: 
    case 5: 
      j = 4;
      break;
    case 0: 
    case 1: 
      j = 0;
    }
    
    
    return i | j;
  }
  






















  public int getDropData(int paramInt)
  {
    return paramInt & 0x3;
  }
  
  public int k(int paramInt) {
    return paramInt & 0x3;
  }
  
  protected ItemStack j(int paramInt)
  {
    return new ItemStack(Item.getItemOf(this), 1, k(paramInt));
  }
}
