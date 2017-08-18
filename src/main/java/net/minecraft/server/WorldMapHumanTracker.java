package net.minecraft.server;

import org.bukkit.map.MapCursor;

public class WorldMapHumanTracker
{
  public final EntityHuman trackee;
  public int[] b;
  public int[] c;
  private int f;
  private int g;
  private byte[] h;
  public int d;
  private boolean i;
  final WorldMap worldMap;
  
  public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
    this.worldMap = worldmap;
    this.b = new int[''];
    this.c = new int[''];
    this.trackee = entityhuman;
    
    for (int i = 0; i < this.b.length; i++) {
      this.b[i] = 0;
      this.c[i] = 127;
    }
  }
  

  public byte[] a(ItemStack itemstack)
  {
    if (!this.i) {
      byte[] abyte = { 2, this.worldMap.scale };
      this.i = true;
      return abyte;
    }
    



    boolean custom = (this.worldMap.mapView.renderers.size() > 1) || (!(this.worldMap.mapView.renderers.get(0) instanceof org.bukkit.craftbukkit.v1_7_R4.map.CraftMapRenderer));
    org.bukkit.craftbukkit.v1_7_R4.map.RenderData render = custom ? this.worldMap.mapView.render((org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer)this.trackee.getBukkitEntity()) : null;
    
    if (--this.g < 0) {
      this.g = 4;
      byte[] abyte = new byte[(custom ? render.cursors.size() : this.worldMap.decorations.size()) * 3 + 1];
      abyte[0] = 1;
      int i = 0;
      



      for (java.util.Iterator iterator = custom ? render.cursors.iterator() : this.worldMap.decorations.values().iterator(); iterator.hasNext(); i++) {
        MapCursor cursor = custom ? (MapCursor)iterator.next() : null;
        if ((cursor == null) || (cursor.isVisible())) {
          WorldMapDecoration deco = custom ? null : (WorldMapDecoration)iterator.next();
          
          abyte[(i * 3 + 1)] = ((byte)((custom ? cursor.getRawType() : deco.type) << 4 | (custom ? cursor.getDirection() : deco.rotation) & 0xF));
          abyte[(i * 3 + 2)] = (custom ? cursor.getX() : deco.locX);
          abyte[(i * 3 + 3)] = (custom ? cursor.getY() : deco.locY);
        }
      }
      

      boolean flag = !itemstack.A();
      int j;
      if ((this.h != null) && (this.h.length == abyte.length)) {
        for (j = 0; j < abyte.length;)
          if (abyte[j] != this.h[j]) {
            flag = false;
          }
          else
          {
            j++; continue;
            





            flag = false;
          }
      }
      if (!flag) {
        this.h = abyte;
        return abyte;
      }
    }
    
    for (int k = 0; k < 1; k++) {
      int i = this.f++ * 11 % 128;
      if (this.b[i] >= 0) {
        int l = this.c[i] - this.b[i] + 1;
        
        int j = this.b[i];
        byte[] abyte1 = new byte[l + 3];
        
        abyte1[0] = 0;
        abyte1[1] = ((byte)i);
        abyte1[2] = ((byte)j);
        
        for (int i1 = 0; i1 < abyte1.length - 3; i1++) {
          abyte1[(i1 + 3)] = this.worldMap.colors[((i1 + j) * 128 + i)];
        }
        
        this.c[i] = -1;
        this.b[i] = -1;
        return abyte1;
      }
    }
    
    return null;
  }
}
