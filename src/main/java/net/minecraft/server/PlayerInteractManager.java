package net.minecraft.server;

import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractManager
{
  public World world;
  public EntityPlayer player;
  private EnumGamemode gamemode;
  private boolean d;
  private int lastDigTick;
  private int f;
  private int g;
  private int h;
  private int currentTick;
  private boolean j;
  private int k;
  private int l;
  private int m;
  private int n;
  private int o;
  
  public PlayerInteractManager(World world)
  {
    this.gamemode = EnumGamemode.NONE;
    this.o = -1;
    this.world = world;
  }
  
  public void setGameMode(EnumGamemode enumgamemode) {
    this.gamemode = enumgamemode;
    enumgamemode.a(this.player.abilities);
    this.player.updateAbilities();
  }
  
  public EnumGamemode getGameMode() {
    return this.gamemode;
  }
  
  public boolean isCreative() {
    return this.gamemode.d();
  }
  
  public void b(EnumGamemode enumgamemode) {
    if (this.gamemode == EnumGamemode.NONE) {
      this.gamemode = enumgamemode;
    }
    
    setGameMode(this.gamemode);
  }
  
  public void a() {
    this.currentTick = MinecraftServer.currentTick;
    


    if (this.j) {
      int j = this.currentTick - this.n;
      Block block = this.world.getType(this.k, this.l, this.m);
      
      if (block.getMaterial() == Material.AIR) {
        this.j = false;
      } else {
        float f = block.getDamage(this.player, this.player.world, this.k, this.l, this.m) * (j + 1);
        int i = (int)(f * 10.0F);
        if (i != this.o) {
          this.world.d(this.player.getId(), this.k, this.l, this.m, i);
          this.o = i;
        }
        
        if (f >= 1.0F) {
          this.j = false;
          breakBlock(this.k, this.l, this.m);
        }
      }
    } else if (this.d) {
      Block block1 = this.world.getType(this.f, this.g, this.h);
      
      if (block1.getMaterial() == Material.AIR) {
        this.world.d(this.player.getId(), this.f, this.g, this.h, -1);
        this.o = -1;
        this.d = false;
      } else {
        int k = this.currentTick - this.lastDigTick;
        
        float f = block1.getDamage(this.player, this.player.world, this.f, this.g, this.h) * (k + 1);
        int i = (int)(f * 10.0F);
        if (i != this.o) {
          this.world.d(this.player.getId(), this.f, this.g, this.h, i);
          this.o = i;
        }
      }
    }
  }
  
  public void dig(int i, int j, int k, int l)
  {
    PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, l, this.player.inventory.getItemInHand());
    if ((!this.gamemode.isAdventure()) || (this.player.d(i, j, k))) {
      if (event.isCancelled())
      {
        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
        
        TileEntity tileentity = this.world.getTileEntity(i, j, k);
        if (tileentity != null) {
          this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
        }
        return;
      }
      
      if (isCreative()) {
        if (!this.world.douseFire((EntityHuman)null, i, j, k, l)) {
          breakBlock(i, j, k);
        }
      }
      else {
        this.lastDigTick = this.currentTick;
        float f = 1.0F;
        Block block = this.world.getType(i, j, k);
        
        if (event.useInteractedBlock() == Event.Result.DENY)
        {
          if (block == Blocks.WOODEN_DOOR)
          {
            boolean bottom = (this.world.getData(i, j, k) & 0x8) == 0;
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j + (bottom ? 1 : -1), k, this.world));
          } else if (block == Blocks.TRAP_DOOR) {
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
          }
        } else if (block.getMaterial() != Material.AIR) {
          block.attack(this.world, i, j, k, this.player);
          f = block.getDamage(this.player, this.player.world, i, j, k);
          
          this.world.douseFire((EntityHuman)null, i, j, k, l);
        }
        
        if (event.useItemInHand() == Event.Result.DENY)
        {
          if (f > 1.0F) {
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
          }
          return;
        }
        BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, i, j, k, this.player.inventory.getItemInHand(), f >= 1.0F);
        
        if (blockEvent.isCancelled())
        {
          this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
          return;
        }
        
        if (blockEvent.getInstaBreak()) {
          f = 2.0F;
        }
        

        if ((block.getMaterial() != Material.AIR) && (f >= 1.0F)) {
          breakBlock(i, j, k);
        } else {
          this.d = true;
          this.f = i;
          this.g = j;
          this.h = k;
          int i1 = (int)(f * 10.0F);
          
          this.world.d(this.player.getId(), i, j, k, i1);
          this.o = i1;
        }
      }
      this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
    }
  }
  
  public void a(int i, int j, int k) {
    if ((i == this.f) && (j == this.g) && (k == this.h)) {
      this.currentTick = MinecraftServer.currentTick;
      int l = this.currentTick - this.lastDigTick;
      Block block = this.world.getType(i, j, k);
      
      if (block.getMaterial() != Material.AIR) {
        float f = block.getDamage(this.player, this.player.world, i, j, k) * (l + 1);
        
        if (f >= 0.7F) {
          this.d = false;
          this.world.d(this.player.getId(), i, j, k, -1);
          breakBlock(i, j, k);
        } else if (!this.j) {
          this.d = false;
          this.j = true;
          this.k = i;
          this.l = j;
          this.m = k;
          this.n = this.lastDigTick;
        }
      }
    }
    else {
      this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
    }
  }
  
  public void c(int i, int j, int k)
  {
    this.d = false;
    this.world.d(this.player.getId(), this.f, this.g, this.h, -1);
  }
  
  private boolean d(int i, int j, int k) {
    Block block = this.world.getType(i, j, k);
    int l = this.world.getData(i, j, k);
    
    block.a(this.world, i, j, k, l, this.player);
    boolean flag = this.world.setAir(i, j, k);
    
    if (flag) {
      block.postBreak(this.world, i, j, k, l);
    }
    
    return flag;
  }
  
  public boolean breakBlock(int i, int j, int k)
  {
    BlockBreakEvent event = null;
    
    if ((this.player instanceof EntityPlayer)) {
      org.bukkit.block.Block block = this.world.getWorld().getBlockAt(i, j, k);
      

      if (this.world.getTileEntity(i, j, k) == null) {
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(i, j, k, this.world);
        packet.block = Blocks.AIR;
        packet.data = 0;
        this.player.playerConnection.sendPacket(packet);
      }
      
      event = new BlockBreakEvent(block, this.player.getBukkitEntity());
      

      event.setCancelled((this.gamemode.isAdventure()) && (!this.player.d(i, j, k)));
      

      event.setCancelled((event.isCancelled()) || ((this.gamemode.d()) && (this.player.be() != null) && ((this.player.be().getItem() instanceof ItemSword))));
      

      Block nmsBlock = this.world.getType(i, j, k);
      
      if ((nmsBlock != null) && (!event.isCancelled()) && (!isCreative()) && (this.player.a(nmsBlock)))
      {
        if ((!nmsBlock.E()) || (!EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
          int data = block.getData();
          int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
          
          event.setExpToDrop(nmsBlock.getExpDrop(this.world, data, bonusLevel));
        }
      }
      
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (event.isCancelled())
      {
        this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
        
        TileEntity tileentity = this.world.getTileEntity(i, j, k);
        if (tileentity != null) {
          this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
        }
        return false;
      }
    }
    






    Block block = this.world.getType(i, j, k);
    if (block == Blocks.AIR) return false;
    int l = this.world.getData(i, j, k);
    

    if ((block == Blocks.SKULL) && (!isCreative())) {
      block.dropNaturally(this.world, i, j, k, l, 1.0F, 0);
      return d(i, j, k);
    }
    

    this.world.a(this.player, 2001, i, j, k, Block.getId(block) + (this.world.getData(i, j, k) << 12));
    boolean flag = d(i, j, k);
    
    if (isCreative()) {
      this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
    } else {
      ItemStack itemstack = this.player.bF();
      boolean flag1 = this.player.a(block);
      
      if (itemstack != null) {
        itemstack.a(this.world, block, i, j, k, this.player);
        if (itemstack.count == 0) {
          this.player.bG();
        }
      }
      
      if ((flag) && (flag1)) {
        block.a(this.world, this.player, i, j, k, l);
      }
    }
    

    if ((flag) && (event != null)) {
      block.dropExperience(this.world, i, j, k, event.getExpToDrop());
    }
    

    return flag;
  }
  
  public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack)
  {
    int i = itemstack.count;
    int j = itemstack.getData();
    ItemStack itemstack1 = itemstack.a(world, entityhuman);
    

    if ((itemstack1 != null) && (itemstack1.getItem() == Items.WRITTEN_BOOK))
    {
      this.player.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", new byte[0]));
    }
    

    if ((itemstack1 == itemstack) && ((itemstack1 == null) || ((itemstack1.count == i) && (itemstack1.n() <= 0) && (itemstack1.getData() == j)))) {
      return false;
    }
    entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
    if (isCreative()) {
      itemstack1.count = i;
      if (itemstack1.g()) {
        itemstack1.setData(j);
      }
    }
    
    if (itemstack1.count == 0) {
      entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
    }
    
    if (!entityhuman.by()) {
      ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
    }
    
    return true;
  }
  

















  public boolean interact(EntityHuman entityhuman, World world, ItemStack itemstack, int i, int j, int k, int l, float f, float f1, float f2)
  {
    Block block = world.getType(i, j, k);
    boolean result = false;
    if (block != Blocks.AIR) {
      PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
      if (event.useInteractedBlock() == Event.Result.DENY)
      {
        if (block == Blocks.WOODEN_DOOR) {
          boolean bottom = (world.getData(i, j, k) & 0x8) == 0;
          ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j + (bottom ? 1 : -1), k, world));
        }
        result = event.useItemInHand() != Event.Result.ALLOW;
      } else if ((!entityhuman.isSneaking()) || (itemstack == null)) {
        result = block.interact(world, i, j, k, entityhuman, l, f, f1, f2);
      }
      
      if ((itemstack != null) && (!result)) {
        int j1 = itemstack.getData();
        int k1 = itemstack.count;
        
        result = itemstack.placeItem(entityhuman, world, i, j, k, l, f, f1, f2);
        

        if (isCreative()) {
          itemstack.setData(j1);
          itemstack.count = k1;
        }
      }
      

      if ((itemstack != null) && (((!result) && (event.useItemInHand() != Event.Result.DENY)) || (event.useItemInHand() == Event.Result.ALLOW))) {
        useItem(entityhuman, world, itemstack);
      }
    }
    return result;
  }
  
  public void a(WorldServer worldserver)
  {
    this.world = worldserver;
  }
}
