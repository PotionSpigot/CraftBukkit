package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class EntityMagmaCube extends EntitySlime
{
  public EntityMagmaCube(World paramWorld)
  {
    super(paramWorld);
    this.fireProof = true;
  }
  
  protected void aD()
  {
    super.aD();
    
    getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
  }
  
  public boolean canSpawn()
  {
    return (this.world.difficulty != EnumDifficulty.PEACEFUL) && (this.world.b(this.boundingBox)) && (this.world.getCubes(this, this.boundingBox).isEmpty()) && (!this.world.containsLiquid(this.boundingBox));
  }
  
  public int aV()
  {
    return getSize() * 3;
  }
  





  public float d(float paramFloat)
  {
    return 1.0F;
  }
  
  protected String bP()
  {
    return "flame";
  }
  
  protected EntitySlime bQ()
  {
    return new EntityMagmaCube(this.world);
  }
  
  protected Item getLoot()
  {
    return Items.MAGMA_CREAM;
  }
  
  protected void dropDeathLoot(boolean paramBoolean, int paramInt)
  {
    Item localItem = getLoot();
    if ((localItem != null) && (getSize() > 1)) {
      int i = this.random.nextInt(4) - 2;
      if (paramInt > 0) {
        i += this.random.nextInt(paramInt + 1);
      }
      for (int j = 0; j < i; j++) {
        a(localItem, 1);
      }
    }
  }
  
  public boolean isBurning()
  {
    return false;
  }
  
  protected int bR()
  {
    return super.bR() * 4;
  }
  
  protected void bS()
  {
    this.h *= 0.9F;
  }
  
  protected void bj()
  {
    this.motY = (0.42F + getSize() * 0.1F);
    this.al = true;
  }
  

  protected void b(float paramFloat) {}
  

  protected boolean bT()
  {
    return true;
  }
  
  protected int bU()
  {
    return super.bU() + 2;
  }
  
  protected String bV()
  {
    if (getSize() > 1) {
      return "mob.magmacube.big";
    }
    return "mob.magmacube.small";
  }
  

  public boolean P()
  {
    return false;
  }
  
  protected boolean bW()
  {
    return true;
  }
}
