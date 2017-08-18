package net.minecraft.server;



public class DemoPlayerInteractManager
  extends PlayerInteractManager
{
  private boolean c;
  

  private boolean d;
  
  private int e;
  
  private int f;
  

  public DemoPlayerInteractManager(World paramWorld)
  {
    super(paramWorld);
  }
  
  public void a()
  {
    super.a();
    this.f += 1;
    
    long l1 = this.world.getTime();
    long l2 = l1 / 24000L + 1L;
    
    if ((!this.c) && (this.f > 20)) {
      this.c = true;
      this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 0.0F));
    }
    
    this.d = (l1 > 120500L);
    if (this.d) {
      this.e += 1;
    }
    
    if (l1 % 24000L == 500L) {
      if (l2 <= 6L) {
        this.player.sendMessage(new ChatMessage("demo.day." + l2, new Object[0]));
      }
    } else if (l2 == 1L) {
      if (l1 == 100L) {
        this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 101.0F));
      } else if (l1 == 175L) {
        this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 102.0F));
      } else if (l1 == 250L) {
        this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 103.0F));
      }
    } else if ((l2 == 5L) && 
      (l1 % 24000L == 22000L)) {
      this.player.sendMessage(new ChatMessage("demo.day.warning", new Object[0]));
    }
  }
  
  private void e()
  {
    if (this.e > 100) {
      this.player.sendMessage(new ChatMessage("demo.reminder", new Object[0]));
      this.e = 0;
    }
  }
  
  public void dig(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.d) {
      e();
      return;
    }
    super.dig(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.d) {
      return;
    }
    super.a(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean breakBlock(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.d) {
      return false;
    }
    return super.breakBlock(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean useItem(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack)
  {
    if (this.d) {
      e();
      return false;
    }
    return super.useItem(paramEntityHuman, paramWorld, paramItemStack);
  }
  
  public boolean interact(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.d) {
      e();
      return false;
    }
    return super.interact(paramEntityHuman, paramWorld, paramItemStack, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
  }
}
