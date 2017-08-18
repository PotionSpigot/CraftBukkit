package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Location;
import org.github.paperspigot.PaperSpigotWorldConfig;

public class EntityFallingBlock extends Entity
{
  public Block id;
  public int data;
  public int ticksLived;
  public boolean dropItem;
  private boolean f;
  private boolean hurtEntities;
  private int fallHurtMax;
  private float fallHurtAmount;
  public NBTTagCompound tileEntityData;
  public Location sourceLoc;
  
  public EntityFallingBlock(World world)
  {
    this(null, world);
  }
  
  public EntityFallingBlock(Location loc, World world) {
    super(world);
    this.sourceLoc = loc;
    
    this.dropItem = true;
    this.fallHurtMax = 40;
    this.fallHurtAmount = 2.0F;
    this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks;
  }
  
  public EntityFallingBlock(Location loc, World world, double d0, double d1, double d2, Block block)
  {
    this(loc, world, d0, d1, d2, block, 0);
  }
  
  public EntityFallingBlock(Location loc, World world, double d0, double d1, double d2, Block block, int i) {
    super(world);
    this.sourceLoc = loc;
    
    this.dropItem = true;
    this.fallHurtMax = 40;
    this.fallHurtAmount = 2.0F;
    this.id = block;
    this.data = i;
    this.k = true;
    a(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
    setPosition(d0, d1, d2);
    this.motX = 0.0D;
    this.motY = 0.0D;
    this.motZ = 0.0D;
    this.lastX = d0;
    this.lastY = d1;
    this.lastZ = d2;
    this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks;
  }
  
  protected boolean g_() {
    return false;
  }
  
  protected void c() {}
  
  public boolean R() {
    return !this.dead;
  }
  
  public void h() {
    if (this.id.getMaterial() == Material.AIR) {
      die();
    } else {
      this.lastX = this.locX;
      this.lastY = this.locY;
      this.lastZ = this.locZ;
      this.ticksLived += 1;
      this.motY -= 0.03999999910593033D;
      move(this.motX, this.motY, this.motZ);
      
      if ((this.inUnloadedChunk) && (this.world.paperSpigotConfig.removeUnloadedFallingBlocks)) {
        die();
      }
      


      if ((this.world.paperSpigotConfig.fallingBlockHeightNerf != 0.0D) && (this.locY > this.world.paperSpigotConfig.fallingBlockHeightNerf)) {
        if (this.dropItem) {
          a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
        }
        
        die();
      }
      

      this.motX *= 0.9800000190734863D;
      this.motY *= 0.9800000190734863D;
      this.motZ *= 0.9800000190734863D;
      if (!this.world.isStatic) {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locY);
        int k = MathHelper.floor(this.locZ);
        
        if (this.ticksLived == 1)
        {
          if ((this.ticksLived != 1) || (this.world.getType(i, j, k) != this.id) || (this.world.getData(i, j, k) != this.data) || (org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, Blocks.AIR, 0).isCancelled())) {
            die();
            return;
          }
          
          this.world.setAir(i, j, k);
          this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
        }
        
        if (this.onGround) {
          this.motX *= 0.699999988079071D;
          this.motZ *= 0.699999988079071D;
          this.motY *= -0.5D;
          if (this.world.getType(i, j, k) != Blocks.PISTON_MOVING) {
            die();
            
            if ((!this.f) && (this.world.mayPlace(this.id, i, j, k, true, 1, (Entity)null, (ItemStack)null)) && (!BlockFalling.canFall(this.world, i, j - 1, k)) && (i >= -30000000) && (k >= -30000000) && (i < 30000000) && (k < 30000000) && (j > 0) && (j < 256) && ((this.world.getType(i, j, k) != this.id) || (this.world.getData(i, j, k) != this.data))) {
              if (org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, this.id, this.data).isCancelled()) {
                return;
              }
              this.world.setTypeAndData(i, j, k, this.id, this.data, 3);
              
              this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
              
              if ((this.id instanceof BlockFalling)) {
                ((BlockFalling)this.id).a(this.world, i, j, k, this.data);
              }
              
              if ((this.tileEntityData != null) && ((this.id instanceof IContainer))) {
                TileEntity tileentity = this.world.getTileEntity(i, j, k);
                
                if (tileentity != null) {
                  NBTTagCompound nbttagcompound = new NBTTagCompound();
                  
                  tileentity.b(nbttagcompound);
                  Iterator iterator = this.tileEntityData.c().iterator();
                  
                  while (iterator.hasNext()) {
                    String s = (String)iterator.next();
                    NBTBase nbtbase = this.tileEntityData.get(s);
                    
                    if ((!s.equals("x")) && (!s.equals("y")) && (!s.equals("z"))) {
                      nbttagcompound.set(s, nbtbase.clone());
                    }
                  }
                  
                  tileentity.a(nbttagcompound);
                  tileentity.update();
                }
              }
            } else if ((this.dropItem) && (!this.f)) {
              a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
            }
          }
        } else if (((this.ticksLived > 100) && (!this.world.isStatic) && ((j < 1) || (j > 256))) || (this.ticksLived > 600)) {
          if (this.dropItem) {
            a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
          }
          
          die();
        }
      }
    }
  }
  
  protected void b(float f) {
    if (this.hurtEntities) {
      int i = MathHelper.f(f - 1.0F);
      
      if (i > 0) {
        ArrayList arraylist = new ArrayList(this.world.getEntities(this, this.boundingBox));
        boolean flag = this.id == Blocks.ANVIL;
        DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
        Iterator iterator = arraylist.iterator();
        
        while (iterator.hasNext()) {
          Entity entity = (Entity)iterator.next();
          
          org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = this;
          entity.damageEntity(damagesource, Math.min(MathHelper.d(i * this.fallHurtAmount), this.fallHurtMax));
          org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory.entityDamage = null;
        }
        
        if ((flag) && (this.random.nextFloat() < 0.05000000074505806D + i * 0.05D)) {
          int j = this.data >> 2;
          int k = this.data & 0x3;
          
          j++;
          if (j > 2) {
            this.f = true;
          } else {
            this.data = (k | j << 2);
          }
        }
      }
    }
  }
  
  protected void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setByte("Tile", (byte)Block.getId(this.id));
    nbttagcompound.setInt("TileID", Block.getId(this.id));
    nbttagcompound.setByte("Data", (byte)this.data);
    nbttagcompound.setByte("Time", (byte)this.ticksLived);
    nbttagcompound.setBoolean("DropItem", this.dropItem);
    nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
    nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
    nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
    if (this.tileEntityData != null) {
      nbttagcompound.set("TileEntityData", this.tileEntityData);
    }
    
    if (this.sourceLoc != null) {
      nbttagcompound.setInt("SourceLoc_x", this.sourceLoc.getBlockX());
      nbttagcompound.setInt("SourceLoc_y", this.sourceLoc.getBlockY());
      nbttagcompound.setInt("SourceLoc_z", this.sourceLoc.getBlockZ());
    }
  }
  
  protected void a(NBTTagCompound nbttagcompound)
  {
    if (nbttagcompound.hasKeyOfType("TileID", 99)) {
      this.id = Block.getById(nbttagcompound.getInt("TileID"));
    } else {
      this.id = Block.getById(nbttagcompound.getByte("Tile") & 0xFF);
    }
    
    this.data = (nbttagcompound.getByte("Data") & 0xFF);
    this.ticksLived = (nbttagcompound.getByte("Time") & 0xFF);
    if (nbttagcompound.hasKeyOfType("HurtEntities", 99)) {
      this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
      this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
      this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
    } else if (this.id == Blocks.ANVIL) {
      this.hurtEntities = true;
    }
    
    if (nbttagcompound.hasKeyOfType("DropItem", 99)) {
      this.dropItem = nbttagcompound.getBoolean("DropItem");
    }
    
    if (nbttagcompound.hasKeyOfType("TileEntityData", 10)) {
      this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
    }
    
    if (this.id.getMaterial() == Material.AIR) {
      this.id = Blocks.SAND;
    }
    
    if (nbttagcompound.hasKey("SourceLoc_x")) {
      int srcX = nbttagcompound.getInt("SourceLoc_x");
      int srcY = nbttagcompound.getInt("SourceLoc_y");
      int srcZ = nbttagcompound.getInt("SourceLoc_z");
      this.sourceLoc = new Location(this.world.getWorld(), srcX, srcY, srcZ);
    }
  }
  
  public void a(boolean flag)
  {
    this.hurtEntities = flag;
  }
  
  public void a(CrashReportSystemDetails crashreportsystemdetails) {
    super.a(crashreportsystemdetails);
    crashreportsystemdetails.a("Immitating block ID", Integer.valueOf(Block.getId(this.id)));
    crashreportsystemdetails.a("Immitating block data", Integer.valueOf(this.data));
  }
  
  public Block f() {
    return this.id;
  }
}
