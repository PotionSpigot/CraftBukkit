package net.minecraft.server;

import java.util.Collection;

public class PortalCreator
{
  private final World a;
  private final int b;
  private final int c;
  private final int d;
  private int e = 0;
  private ChunkCoordinates f;
  private int g;
  private int h;
  Collection<org.bukkit.block.Block> blocks = new java.util.HashSet();
  
  public PortalCreator(World world, int i, int j, int k, int l) {
    this.a = world;
    this.b = l;
    this.d = BlockPortal.a[l][0];
    this.c = BlockPortal.a[l][1];
    
    for (int i1 = j; (j > i1 - 21) && (j > 0) && (a(world.getType(i, j - 1, k))); j--) {}
    


    int j1 = a(i, j, k, this.d) - 1;
    
    if (j1 >= 0) {
      this.f = new ChunkCoordinates(i + j1 * Direction.a[this.d], j, k + j1 * Direction.b[this.d]);
      this.h = a(this.f.x, this.f.y, this.f.z, this.c);
      if ((this.h < 2) || (this.h > 21)) {
        this.f = null;
        this.h = 0;
      }
    }
    
    if (this.f != null) {
      this.g = a();
    }
  }
  
  protected int a(int i, int j, int k, int l) {
    int i1 = Direction.a[l];
    int j1 = Direction.b[l];
    



    for (int k1 = 0; k1 < 22; k1++) {
      Block block = this.a.getType(i + i1 * k1, j, k + j1 * k1);
      if (!a(block)) {
        break;
      }
      
      Block block1 = this.a.getType(i + i1 * k1, j - 1, k + j1 * k1);
      
      if (block1 != Blocks.OBSIDIAN) {
        break;
      }
    }
    
    Block block = this.a.getType(i + i1 * k1, j, k + j1 * k1);
    return block == Blocks.OBSIDIAN ? k1 : 0;
  }
  
  protected int a()
  {
    this.blocks.clear();
    org.bukkit.World bworld = this.a.getWorld();
    






    for (this.g = 0; this.g < 21; this.g += 1) {
      int i = this.f.y + this.g;
      
      for (int j = 0; j < this.h; j++) {
        int k = this.f.x + j * Direction.a[BlockPortal.a[this.b][1]];
        int l = this.f.z + j * Direction.b[BlockPortal.a[this.b][1]];
        Block block = this.a.getType(k, i, l);
        
        if (!a(block)) {
          break label398;
        }
        
        if (block == Blocks.PORTAL) {
          this.e += 1;
        }
        
        if (j == 0) {
          block = this.a.getType(k + Direction.a[BlockPortal.a[this.b][0]], i, l + Direction.b[BlockPortal.a[this.b][0]]);
          if (block != Blocks.OBSIDIAN) {
            break label398;
          }
          
          this.blocks.add(bworld.getBlockAt(k + Direction.a[BlockPortal.a[this.b][0]], i, l + Direction.b[BlockPortal.a[this.b][0]]));

        }
        else if (j == this.h - 1) {
          block = this.a.getType(k + Direction.a[BlockPortal.a[this.b][1]], i, l + Direction.b[BlockPortal.a[this.b][1]]);
          if (block != Blocks.OBSIDIAN) {
            break label398;
          }
          
          this.blocks.add(bworld.getBlockAt(k + Direction.a[BlockPortal.a[this.b][1]], i, l + Direction.b[BlockPortal.a[this.b][1]]));
        }
      }
    }
    
    label398:
    
    for (int i = 0; i < this.h; i++) {
      int j = this.f.x + i * Direction.a[BlockPortal.a[this.b][1]];
      int k = this.f.y + this.g;
      int l = this.f.z + i * Direction.b[BlockPortal.a[this.b][1]];
      if (this.a.getType(j, k, l) != Blocks.OBSIDIAN) {
        this.g = 0;
        break;
      }
      
      this.blocks.add(bworld.getBlockAt(j, k, l));
    }
    


    if ((this.g <= 21) && (this.g >= 3)) {
      return this.g;
    }
    this.f = null;
    this.h = 0;
    this.g = 0;
    return 0;
  }
  
  protected boolean a(Block block)
  {
    return (block.material == Material.AIR) || (block == Blocks.FIRE) || (block == Blocks.PORTAL);
  }
  
  public boolean b() {
    return (this.f != null) && (this.h >= 2) && (this.h <= 21) && (this.g >= 3) && (this.g <= 21);
  }
  
  public boolean c()
  {
    org.bukkit.World bworld = this.a.getWorld();
    

    for (int i = 0; i < this.h; i++) {
      int j = this.f.x + Direction.a[this.c] * i;
      int k = this.f.z + Direction.b[this.c] * i;
      
      for (int l = 0; l < this.g; l++) {
        int i1 = this.f.y + l;
        
        this.blocks.add(bworld.getBlockAt(j, i1, k));
      }
    }
    
    org.bukkit.event.world.PortalCreateEvent event = new org.bukkit.event.world.PortalCreateEvent(this.blocks, bworld, org.bukkit.event.world.PortalCreateEvent.CreateReason.FIRE);
    this.a.getServer().getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      return false;
    }
    

    for (int i = 0; i < this.h; i++) {
      int j = this.f.x + Direction.a[this.c] * i;
      int k = this.f.z + Direction.b[this.c] * i;
      
      for (int l = 0; l < this.g; l++) {
        int i1 = this.f.y + l;
        
        this.a.setTypeAndData(j, i1, k, Blocks.PORTAL, this.b, 2);
      }
    }
    
    return true;
  }
  
  static int a(PortalCreator portalcreator) {
    return portalcreator.e;
  }
  
  static int b(PortalCreator portalcreator) {
    return portalcreator.h;
  }
  
  static int c(PortalCreator portalcreator) {
    return portalcreator.g;
  }
}
