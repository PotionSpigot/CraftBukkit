package net.minecraft.server;

















public class EntityMinecartCommandBlock
  extends EntityMinecartAbstract
{
  private final CommandBlockListenerAbstract a = new EntityMinecartCommandBlockListener(this);
  



























  private int b = 0;
  
  public EntityMinecartCommandBlock(World paramWorld) {
    super(paramWorld);
  }
  
  public EntityMinecartCommandBlock(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
    super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
  }
  
  protected void c()
  {
    super.c();
    getDataWatcher().a(23, "");
    getDataWatcher().a(24, "");
  }
  
  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.a.b(paramNBTTagCompound);
    getDataWatcher().watch(23, getCommandBlock().getCommand());
    getDataWatcher().watch(24, ChatSerializer.a(getCommandBlock().h()));
  }
  
  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    this.a.a(paramNBTTagCompound);
  }
  
  public int m()
  {
    return 6;
  }
  
  public Block o()
  {
    return Blocks.COMMAND;
  }
  
  public CommandBlockListenerAbstract getCommandBlock() {
    return this.a;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if ((paramBoolean) && 
      (this.ticksLived - this.b >= 4)) {
      getCommandBlock().a(this.world);
      this.b = this.ticksLived;
    }
  }
  

  public boolean c(EntityHuman paramEntityHuman)
  {
    if (this.world.isStatic) {
      paramEntityHuman.a(getCommandBlock());
    }
    
    return super.c(paramEntityHuman);
  }
  
  public void i(int paramInt)
  {
    super.i(paramInt);
    
    if (paramInt == 24) {
      try {
        this.a.b(ChatSerializer.a(getDataWatcher().getString(24)));
      } catch (Throwable localThrowable) {}
    } else if (paramInt == 23) {
      this.a.setCommand(getDataWatcher().getString(23));
    }
  }
}
