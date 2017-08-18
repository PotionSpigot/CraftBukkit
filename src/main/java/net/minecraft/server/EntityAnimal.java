package net.minecraft.server;

import java.util.List;
import java.util.Random;











public abstract class EntityAnimal
  extends EntityAgeable
  implements IAnimal
{
  private int love;
  private int bq;
  private EntityHuman br;
  
  public EntityAnimal(World paramWorld)
  {
    super(paramWorld);
  }
  
  protected void bp()
  {
    if (getAge() != 0) this.love = 0;
    super.bp();
  }
  
  public void e()
  {
    super.e();
    
    if (getAge() != 0) { this.love = 0;
    }
    if (this.love > 0) {
      this.love -= 1;
      String str = "heart";
      if (this.love % 10 == 0) {
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        double d3 = this.random.nextGaussian() * 0.02D;
        this.world.addParticle(str, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
      }
    } else {
      this.bq = 0;
    }
  }
  
  protected void a(Entity paramEntity, float paramFloat) {
    Object localObject;
    if ((paramEntity instanceof EntityHuman)) {
      if (paramFloat < 3.0F) {
        double d1 = paramEntity.locX - this.locX;
        double d2 = paramEntity.locZ - this.locZ;
        this.yaw = ((float)(Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F);
        
        this.bn = true;
      }
      
      localObject = (EntityHuman)paramEntity;
      if ((((EntityHuman)localObject).bF() == null) || (!c(((EntityHuman)localObject).bF()))) {
        this.target = null;
      }
    } else if ((paramEntity instanceof EntityAnimal)) {
      localObject = (EntityAnimal)paramEntity;
      if ((getAge() > 0) && (((EntityAnimal)localObject).getAge() < 0)) {
        if (paramFloat < 2.5D) {
          this.bn = true;
        }
      } else if ((this.love > 0) && (((EntityAnimal)localObject).love > 0)) {
        if (((EntityAnimal)localObject).target == null) { ((EntityAnimal)localObject).target = this;
        }
        if ((((EntityAnimal)localObject).target == this) && (paramFloat < 3.5D)) {
          localObject.love += 1;
          this.love += 1;
          this.bq += 1;
          if (this.bq % 4 == 0) {
            this.world.addParticle("heart", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D);
          }
          
          if (this.bq == 60) b((EntityAnimal)paramEntity);
        } else { this.bq = 0;
        }
      } else { this.bq = 0;
        this.target = null;
      }
    }
  }
  
  private void b(EntityAnimal paramEntityAnimal) {
    EntityAgeable localEntityAgeable = createChild(paramEntityAnimal);
    if (localEntityAgeable != null) {
      if ((this.br == null) && (paramEntityAnimal.cd() != null)) {
        this.br = paramEntityAnimal.cd();
      }
      
      if (this.br != null) {
        this.br.a(StatisticList.x);
        
        if ((this instanceof EntityCow)) {
          this.br.a(AchievementList.H);
        }
      }
      
      setAge(6000);
      paramEntityAnimal.setAge(6000);
      this.love = 0;
      this.bq = 0;
      this.target = null;
      paramEntityAnimal.target = null;
      paramEntityAnimal.bq = 0;
      paramEntityAnimal.love = 0;
      localEntityAgeable.setAge(41536);
      localEntityAgeable.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      for (int i = 0; i < 7; i++) {
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        double d3 = this.random.nextGaussian() * 0.02D;
        this.world.addParticle("heart", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
      }
      this.world.addEntity(localEntityAgeable);
    }
  }
  
  public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
  {
    if (isInvulnerable()) return false;
    this.bo = 60;
    
    if (!bk()) {
      AttributeInstance localAttributeInstance = getAttributeInstance(GenericAttributes.d);
      if (localAttributeInstance.a(h) == null) {
        localAttributeInstance.a(i);
      }
    }
    
    this.target = null;
    this.love = 0;
    return super.damageEntity(paramDamageSource, paramFloat);
  }
  
  public float a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.world.getType(paramInt1, paramInt2 - 1, paramInt3) == Blocks.GRASS) return 10.0F;
    return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F;
  }
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setInt("InLove", this.love);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.love = paramNBTTagCompound.getInt("InLove");
  }
  
  protected Entity findTarget()
  {
    if (this.bo > 0) { return null;
    }
    float f = 8.0F;
    List localList; int i; Object localObject; if (this.love > 0) {
      localList = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityAnimal)localList.get(i);
        if ((localObject != this) && (((EntityAnimal)localObject).love > 0)) {
          return (Entity)localObject;
        }
      }
    }
    else if (getAge() == 0) {
      localList = this.world.a(EntityHuman.class, this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityHuman)localList.get(i);
        if ((((EntityHuman)localObject).bF() != null) && (c(((EntityHuman)localObject).bF()))) {
          return (Entity)localObject;
        }
      }
    } else if (getAge() > 0) {
      localList = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityAnimal)localList.get(i);
        if ((localObject != this) && (((EntityAnimal)localObject).getAge() < 0)) {
          return (Entity)localObject;
        }
      }
    }
    
    return null;
  }
  
  public boolean canSpawn()
  {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.boundingBox.b);
    int k = MathHelper.floor(this.locZ);
    return (this.world.getType(i, j - 1, k) == Blocks.GRASS) && (this.world.j(i, j, k) > 8) && (super.canSpawn());
  }
  
  public int q()
  {
    return 120;
  }
  
  protected boolean isTypeNotPersistent()
  {
    return false;
  }
  
  protected int getExpValue(EntityHuman paramEntityHuman)
  {
    return 1 + this.world.random.nextInt(3);
  }
  
  public boolean c(ItemStack paramItemStack) {
    return paramItemStack.getItem() == Items.WHEAT;
  }
  
  public boolean a(EntityHuman paramEntityHuman)
  {
    ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
    if ((localItemStack != null) && (c(localItemStack)) && (getAge() == 0) && (this.love <= 0)) {
      if (!paramEntityHuman.abilities.canInstantlyBuild) {
        localItemStack.count -= 1;
        if (localItemStack.count <= 0) {
          paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
        }
      }
      f(paramEntityHuman);
      return true;
    }
    return super.a(paramEntityHuman);
  }
  
  public void f(EntityHuman paramEntityHuman) {
    this.love = 600;
    this.br = paramEntityHuman;
    
    this.target = null;
    this.world.broadcastEntityEffect(this, (byte)18);
  }
  
  public EntityHuman cd() {
    return this.br;
  }
  
  public boolean ce() {
    return this.love > 0;
  }
  
  public void cf() {
    this.love = 0;
  }
  
  public boolean mate(EntityAnimal paramEntityAnimal) {
    if (paramEntityAnimal == this) return false;
    if (paramEntityAnimal.getClass() != getClass()) return false;
    return (ce()) && (paramEntityAnimal.ce());
  }
}
