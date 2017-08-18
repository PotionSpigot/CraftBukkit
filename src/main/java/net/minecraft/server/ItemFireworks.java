package net.minecraft.server;




























public class ItemFireworks
  extends Item
{
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!paramWorld.isStatic)
    {

      EntityFireworks localEntityFireworks = new EntityFireworks(paramWorld, paramInt1 + paramFloat1, paramInt2 + paramFloat2, paramInt3 + paramFloat3, paramItemStack);
      paramWorld.addEntity(localEntityFireworks);
      
      if (!paramEntityHuman.abilities.canInstantlyBuild) {
        paramItemStack.count -= 1;
      }
      return true;
    }
    
    return false;
  }
}
