package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
import org.bukkit.inventory.InventoryHolder;
import org.spigotmc.SpigotWorldConfig;

public class TileEntity
{
  public org.spigotmc.CustomTimingsHandler tickTimer = SpigotTimings.getTileEntityTimings(this);
  private static final org.apache.logging.log4j.Logger a = ;
  private static Map i = new HashMap();
  private static Map j = new HashMap();
  protected World world;
  public int x;
  public int y;
  public int z;
  protected boolean f;
  public int g = -1;
  
  public Block h;
  

  private void scheduleTick(int x0, int y0, int z0)
  {
    TileEntity tileEntity = this.world.getTileEntity(x0, y0, z0);
    if (((tileEntity instanceof TileEntityHopper)) && (tileEntity.world != null))
    {
      int i = BlockHopper.b(tileEntity.p());
      
      if ((tileEntity.x + Facing.b[i] == this.x) && (tileEntity.y + Facing.c[i] == this.y) && (tileEntity.z + Facing.d[i] == this.z)) {
        ((TileEntityHopper)tileEntity).makeTick();
      }
    }
  }
  

  public void scheduleTicks()
  {
    if ((this.world != null) && (this.world.spigotConfig.altHopperTicking))
    {
      scheduleTick(this.x, this.y + 1, this.z);
      
      for (int i = 2; i < 6; i++) {
        scheduleTick(this.x + Facing.b[i], this.y, this.z + Facing.d[i]);
      }
      
      TileEntity tileEntity = this.world.getTileEntity(this.x, this.y - 1, this.z);
      if (((tileEntity instanceof TileEntityHopper)) && (tileEntity.world != null)) {
        ((TileEntityHopper)tileEntity).makeTick();
      }
    }
  }
  

  private static int tileEntityCounter = 0;
  public boolean isAdded = false;
  public int tileId = tileEntityCounter++;
  



  private static void a(Class oclass, String s)
  {
    if (i.containsKey(s)) {
      throw new IllegalArgumentException("Duplicate id: " + s);
    }
    i.put(s, oclass);
    j.put(oclass, s);
  }
  
  public World getWorld()
  {
    return this.world;
  }
  
  public void a(World world) {
    this.world = world;
  }
  
  public boolean o() {
    return this.world != null;
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.x = nbttagcompound.getInt("x");
    this.y = nbttagcompound.getInt("y");
    this.z = nbttagcompound.getInt("z");
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    String s = (String)j.get(getClass());
    
    if (s == null) {
      throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
    }
    nbttagcompound.setString("id", s);
    nbttagcompound.setInt("x", this.x);
    nbttagcompound.setInt("y", this.y);
    nbttagcompound.setInt("z", this.z);
  }
  


  public static TileEntity c(NBTTagCompound nbttagcompound)
  {
    TileEntity tileentity = null;
    try
    {
      Class oclass = (Class)i.get(nbttagcompound.getString("id"));
      
      if (oclass != null) {
        tileentity = (TileEntity)oclass.newInstance();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    
    if (tileentity != null) {
      tileentity.a(nbttagcompound);
    } else {
      a.warn("Skipping BlockEntity with id " + nbttagcompound.getString("id"));
    }
    
    return tileentity;
  }
  
  public int p() {
    if (this.g == -1) {
      this.g = this.world.getData(this.x, this.y, this.z);
    }
    
    return this.g;
  }
  
  public void update() {
    if (this.world != null) {
      this.g = this.world.getData(this.x, this.y, this.z);
      this.world.b(this.x, this.y, this.z, this);
      if (q() != Blocks.AIR) {
        this.world.updateAdjacentComparators(this.x, this.y, this.z, q());
      }
      

      scheduleTicks();
    }
  }
  
  public Block q()
  {
    if (this.h == null) {
      this.h = this.world.getType(this.x, this.y, this.z);
    }
    
    return this.h;
  }
  
  public Packet getUpdatePacket() {
    return null;
  }
  
  public boolean r() {
    return this.f;
  }
  
  public void s() {
    this.f = true;
  }
  
  public void t() {
    this.f = false;
  }
  
  public boolean c(int i, int j) {
    return false;
  }
  
  public void u() {
    this.h = null;
    this.g = -1;
  }
  
  public void a(CrashReportSystemDetails crashreportsystemdetails) {
    crashreportsystemdetails.a("Name", new CrashReportTileEntityName(this));
    Block block = q();
    if (block != null) {
      CrashReportSystemDetails.a(crashreportsystemdetails, this.x, this.y, this.z, q(), p());
    }
    crashreportsystemdetails.a("Actual block type", new CrashReportTileEntityType(this));
    crashreportsystemdetails.a("Actual block data value", new CrashReportTileEntityData(this));
  }
  
  static Map v() {
    return j;
  }
  
  static {
    a(TileEntityFurnace.class, "Furnace");
    a(TileEntityChest.class, "Chest");
    a(TileEntityEnderChest.class, "EnderChest");
    a(TileEntityRecordPlayer.class, "RecordPlayer");
    a(TileEntityDispenser.class, "Trap");
    a(TileEntityDropper.class, "Dropper");
    a(TileEntitySign.class, "Sign");
    a(TileEntityMobSpawner.class, "MobSpawner");
    a(TileEntityNote.class, "Music");
    a(TileEntityPiston.class, "Piston");
    a(TileEntityBrewingStand.class, "Cauldron");
    a(TileEntityEnchantTable.class, "EnchantTable");
    a(TileEntityEnderPortal.class, "Airportal");
    a(TileEntityCommand.class, "Control");
    a(TileEntityBeacon.class, "Beacon");
    a(TileEntitySkull.class, "Skull");
    a(TileEntityLightDetector.class, "DLDetector");
    a(TileEntityHopper.class, "Hopper");
    a(TileEntityComparator.class, "Comparator");
    a(TileEntityFlowerPot.class, "FlowerPot");
  }
  

  public InventoryHolder getOwner()
  {
    org.bukkit.block.Block block = this.world.getWorld().getBlockAt(this.x, this.y, this.z);
    if (block == null) {
      org.bukkit.Bukkit.getLogger().log(Level.WARNING, "No block for owner at %s %d %d %d", new Object[] { this.world.getWorld(), Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
      return null;
    }
    
    BlockState state = block.getState();
    if ((state instanceof InventoryHolder)) return (InventoryHolder)state;
    return null;
  }
  
  public void h() {}
}
