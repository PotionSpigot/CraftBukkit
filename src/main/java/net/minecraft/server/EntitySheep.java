package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.plugin.PluginManager;

public class EntitySheep extends EntityAnimal
{
  private final InventoryCrafting bq = new InventoryCrafting(new ContainerSheepBreed(this), 2, 1);
  public static final float[][] bp = { { 1.0F, 1.0F, 1.0F }, { 0.85F, 0.5F, 0.2F }, { 0.7F, 0.3F, 0.85F }, { 0.4F, 0.6F, 0.85F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.5F, 0.65F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.5F, 0.6F }, { 0.5F, 0.25F, 0.7F }, { 0.2F, 0.3F, 0.7F }, { 0.4F, 0.3F, 0.2F }, { 0.4F, 0.5F, 0.2F }, { 0.6F, 0.2F, 0.2F }, { 0.1F, 0.1F, 0.1F } };
  private int br;
  private PathfinderGoalEatTile bs = new PathfinderGoalEatTile(this);
  
  public EntitySheep(World world) {
    super(world);
    a(0.9F, 1.3F);
    getNavigation().a(true);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
    this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
    this.goalSelector.a(5, this.bs);
    this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
    this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    this.bq.setItem(0, new ItemStack(Items.INK_SACK, 1, 0));
    this.bq.setItem(1, new ItemStack(Items.INK_SACK, 1, 0));
    this.bq.resultInventory = new InventoryCraftResult();
  }
  
  protected boolean bk() {
    return true;
  }
  
  protected void bn() {
    this.br = this.bs.f();
    super.bn();
  }
  
  public void e() {
    if (this.world.isStatic) {
      this.br = Math.max(0, this.br - 1);
    }
    
    super.e();
  }
  
  protected void aD() {
    super.aD();
    getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
    getAttributeInstance(GenericAttributes.d).setValue(0.23000000417232513D);
  }
  
  protected void c() {
    super.c();
    this.datawatcher.a(16, new Byte((byte)0));
  }
  
  protected void dropDeathLoot(boolean flag, int i) {
    if (!isSheared()) {
      a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor()), 0.0F);
    }
  }
  
  protected Item getLoot() {
    return Item.getItemOf(Blocks.WOOL);
  }
  
  public boolean a(EntityHuman entityhuman) {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && (itemstack.getItem() == Items.SHEARS) && (!isSheared()) && (!isBaby())) {
      if (!this.world.isStatic)
      {
        PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), getBukkitEntity());
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (event.isCancelled()) {
          return false;
        }
        

        setSheared(true);
        int i = 1 + this.random.nextInt(3);
        
        for (int j = 0; j < i; j++) {
          EntityItem entityitem = a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor()), 1.0F);
          
          entityitem.motY += this.random.nextFloat() * 0.05F;
          entityitem.motX += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
          entityitem.motZ += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        }
      }
      
      itemstack.damage(1, entityhuman);
      makeSound("mob.sheep.shear", 1.0F, 1.0F);
    }
    
    return super.a(entityhuman);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setBoolean("Sheared", isSheared());
    nbttagcompound.setByte("Color", (byte)getColor());
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    setSheared(nbttagcompound.getBoolean("Sheared"));
    setColor(nbttagcompound.getByte("Color"));
  }
  
  protected String t() {
    return "mob.sheep.say";
  }
  
  protected String aT() {
    return "mob.sheep.say";
  }
  
  protected String aU() {
    return "mob.sheep.say";
  }
  
  protected void a(int i, int j, int k, Block block) {
    makeSound("mob.sheep.step", 0.15F, 1.0F);
  }
  
  public int getColor() {
    return this.datawatcher.getByte(16) & 0xF;
  }
  
  public void setColor(int i) {
    byte b0 = this.datawatcher.getByte(16);
    
    this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xF0 | i & 0xF)));
  }
  
  public boolean isSheared() {
    return (this.datawatcher.getByte(16) & 0x10) != 0;
  }
  
  public void setSheared(boolean flag) {
    byte b0 = this.datawatcher.getByte(16);
    
    if (flag) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x10)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
    }
  }
  
  public static int a(Random random) {
    int i = random.nextInt(100);
    
    return random.nextInt(500) == 0 ? 6 : i < 18 ? 12 : i < 15 ? 8 : i < 10 ? 7 : i < 5 ? 15 : 0;
  }
  
  public EntitySheep b(EntityAgeable entityageable) {
    EntitySheep entitysheep = (EntitySheep)entityageable;
    EntitySheep entitysheep1 = new EntitySheep(this.world);
    int i = a(this, entitysheep);
    
    entitysheep1.setColor(15 - i);
    return entitysheep1;
  }
  
  public void p()
  {
    SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((Sheep)getBukkitEntity());
    this.world.getServer().getPluginManager().callEvent(event);
    
    if (!event.isCancelled()) {
      setSheared(false);
    }
    

    if (isBaby()) {
      a(60);
    }
  }
  
  public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
    groupdataentity = super.prepare(groupdataentity);
    setColor(a(this.world.random));
    return groupdataentity;
  }
  
  private int a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
    int i = b(entityanimal);
    int j = b(entityanimal1);
    
    this.bq.getItem(0).setData(i);
    this.bq.getItem(1).setData(j);
    ItemStack itemstack = CraftingManager.getInstance().craft(this.bq, ((EntitySheep)entityanimal).world);
    int k;
    int k;
    if ((itemstack != null) && (itemstack.getItem() == Items.INK_SACK)) {
      k = itemstack.getData();
    } else {
      k = this.world.random.nextBoolean() ? i : j;
    }
    
    return k;
  }
  
  private int b(EntityAnimal entityanimal) {
    return 15 - ((EntitySheep)entityanimal).getColor();
  }
  
  public EntityAgeable createChild(EntityAgeable entityageable) {
    return b(entityageable);
  }
}
