package net.minecraft.server;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;
import org.bukkit.craftbukkit.v1_7_R4.util.LongHashSet;
import org.bukkit.craftbukkit.v1_7_R4.util.LongObjectHashMap;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.spigotmc.SpigotWorldConfig;

public final class SpawnerCreature
{
  private LongObjectHashMap<Boolean> a = new LongObjectHashMap();
  

  protected static ChunkPosition getRandomPosition(World world, int i, int j)
  {
    Chunk chunk = world.getChunkAt(i, j);
    int k = i * 16 + world.random.nextInt(16);
    int l = j * 16 + world.random.nextInt(16);
    int i1 = world.random.nextInt(chunk == null ? world.S() : chunk.h() + 16 - 1);
    
    return new ChunkPosition(k, i1, l);
  }
  

  private int getEntityCount(WorldServer server, Class oClass)
  {
    int i = 0;
    for (Long coord : this.a.keySet())
    {
      int x = LongHash.msw(coord.longValue());
      int z = LongHash.lsw(coord.longValue());
      if ((!server.chunkProviderServer.unloadQueue.contains(coord.longValue())) && (server.isChunkLoaded(x, z)))
      {
        i += server.getChunkAt(x, z).entityCount.get(oClass);
      }
    }
    return i;
  }
  
  public int spawnEntities(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2)
  {
    if ((!flag) && (!flag1)) {
      return 0;
    }
    this.a.clear();
    



    for (int i = 0; i < worldserver.players.size(); i++) {
      EntityHuman entityhuman = (EntityHuman)worldserver.players.get(i);
      
      if (entityhuman.affectsSpawning)
      {

        int k = MathHelper.floor(entityhuman.locX / 16.0D);
        
        int j = MathHelper.floor(entityhuman.locZ / 16.0D);
        byte b0 = 8;
        
        b0 = worldserver.spigotConfig.mobSpawnRange;
        b0 = b0 > worldserver.spigotConfig.viewDistance ? (byte)worldserver.spigotConfig.viewDistance : b0;
        
        if ((entityhuman instanceof EntityPlayer)) {
          int viewDistance = ((EntityPlayer)entityhuman).viewDistance;
          b0 = b0 > viewDistance ? (byte)viewDistance : b0;
        }
        
        b0 = b0 > 8 ? 8 : b0;
        

        for (int l = -b0; l <= b0; l++) {
          for (int i1 = -b0; i1 <= b0; i1++) {
            boolean flag3 = (l == -b0) || (l == b0) || (i1 == -b0) || (i1 == b0);
            

            long chunkCoords = LongHash.toLong(l + k, i1 + j);
            
            if (!flag3) {
              this.a.put(chunkCoords, Boolean.valueOf(false));
            } else if (!this.a.containsKey(chunkCoords)) {
              this.a.put(chunkCoords, Boolean.valueOf(true));
            }
          }
        }
      }
    }
    
    i = 0;
    ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
    EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
    
    int j = aenumcreaturetype.length;
    
    for (int j1 = 0; j1 < j; j1++) {
      EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];
      

      int limit = enumcreaturetype.b();
      switch (enumcreaturetype) {
      case MONSTER: 
        limit = worldserver.getWorld().getMonsterSpawnLimit();
        break;
      case CREATURE: 
        limit = worldserver.getWorld().getAnimalSpawnLimit();
        break;
      case WATER_CREATURE: 
        limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
        break;
      case AMBIENT: 
        limit = worldserver.getWorld().getAmbientSpawnLimit();
      }
      
      
      if (limit != 0)
      {

        int mobcnt = 0;
        

        if (((!enumcreaturetype.d()) || (flag1)) && ((enumcreaturetype.d()) || (flag)) && ((!enumcreaturetype.e()) || (flag2)) && ((mobcnt = getEntityCount(worldserver, enumcreaturetype.a())) <= limit * this.a.size() / 256)) {
          Iterator iterator = this.a.keySet().iterator();
          
          int moblimit = limit * this.a.size() / 256 - mobcnt + 1;
          label1081:
          label1090: while ((iterator.hasNext()) && (moblimit > 0))
          {
            long key = ((Long)iterator.next()).longValue();
            
            if (!((Boolean)this.a.get(key)).booleanValue()) {
              ChunkPosition chunkposition = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
              
              int k1 = chunkposition.x;
              int l1 = chunkposition.y;
              int i2 = chunkposition.z;
              
              if ((!worldserver.getType(k1, l1, i2).r()) && (worldserver.getType(k1, l1, i2).getMaterial() == enumcreaturetype.c())) {
                int j2 = 0;
                int k2 = 0;
                for (;;) {
                  if (k2 >= 3) break label1090;
                  int l2 = k1;
                  int i3 = l1;
                  int j3 = i2;
                  byte b1 = 6;
                  BiomeMeta biomemeta = null;
                  GroupDataEntity groupdataentity = null;
                  int k3 = 0;
                  for (;;)
                  {
                    if (k3 >= 4)
                      break label1081;
                    l2 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                    i3 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
                    j3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                    if (a(enumcreaturetype, worldserver, l2, i3, j3)) {
                      float f = l2 + 0.5F;
                      float f1 = i3;
                      float f2 = j3 + 0.5F;
                      
                      if (worldserver.findNearbyPlayerWhoAffectsSpawning(f, f1, f2, 24.0D) == null) {
                        float f3 = f - chunkcoordinates.x;
                        float f4 = f1 - chunkcoordinates.y;
                        float f5 = f2 - chunkcoordinates.z;
                        float f6 = f3 * f3 + f4 * f4 + f5 * f5;
                        
                        if (f6 >= 576.0F) {
                          if (biomemeta == null) {
                            biomemeta = worldserver.a(enumcreaturetype, l2, i3, j3);
                            if (biomemeta == null) {
                              break label1081;
                            }
                          }
                          

                          try
                          {
                            entityinsentient = (EntityInsentient)biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldserver });
                          } catch (Exception exception) { EntityInsentient entityinsentient;
                            exception.printStackTrace();
                            return i;
                          }
                          EntityInsentient entityinsentient;
                          entityinsentient.setPositionRotation(f, f1, f2, worldserver.random.nextFloat() * 360.0F, 0.0F);
                          if (entityinsentient.canSpawn()) {
                            j2++;
                            
                            groupdataentity = entityinsentient.prepare(groupdataentity);
                            worldserver.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.NATURAL);
                            

                            moblimit--; if ((moblimit <= 0) || 
                            




                              (j2 >= entityinsentient.bB())) {
                              break;
                            }
                          }
                          
                          i += j2;
                        }
                      }
                    }
                    
                    k3++;
                  }
                  


                  k2++;
                }
              }
            }
          }
        }
      }
    }
    

    return i;
  }
  
  public static boolean a(EnumCreatureType enumcreaturetype, World world, int i, int j, int k)
  {
    if (enumcreaturetype.c() == Material.WATER)
      return (world.getType(i, j, k).getMaterial().isLiquid()) && (world.getType(i, j - 1, k).getMaterial().isLiquid()) && (!world.getType(i, j + 1, k).r());
    if (!World.a(world, i, j - 1, k)) {
      return false;
    }
    Block block = world.getType(i, j - 1, k);
    
    return (block != Blocks.BEDROCK) && (!world.getType(i, j, k).r()) && (!world.getType(i, j, k).getMaterial().isLiquid()) && (!world.getType(i, j + 1, k).r());
  }
  
  public static void a(World world, BiomeBase biomebase, int i, int j, int k, int l, Random random)
  {
    List list = biomebase.getMobs(EnumCreatureType.CREATURE);
    
    if (!list.isEmpty()) {
      while (random.nextFloat() < biomebase.g()) {
        BiomeMeta biomemeta = (BiomeMeta)WeightedRandom.a(world.random, list);
        GroupDataEntity groupdataentity = null;
        int i1 = biomemeta.c + random.nextInt(1 + biomemeta.d - biomemeta.c);
        int j1 = i + random.nextInt(k);
        int k1 = j + random.nextInt(l);
        int l1 = j1;
        int i2 = k1;
        
        for (int j2 = 0; j2 < i1; j2++) {
          boolean flag = false;
          
          for (int k2 = 0; (!flag) && (k2 < 4); k2++) {
            int l2 = world.i(j1, k1);
            
            if (a(EnumCreatureType.CREATURE, world, j1, l2, k1)) {
              float f = j1 + 0.5F;
              float f1 = l2;
              float f2 = k1 + 0.5F;
              

              try
              {
                entityinsentient = (EntityInsentient)biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
              } catch (Exception exception) { EntityInsentient entityinsentient;
                exception.printStackTrace();
                continue;
              }
              EntityInsentient entityinsentient;
              entityinsentient.setPositionRotation(f, f1, f2, random.nextFloat() * 360.0F, 0.0F);
              
              groupdataentity = entityinsentient.prepare(groupdataentity);
              world.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
              
              flag = true;
            }
            
            j1 += random.nextInt(5) - random.nextInt(5);
            
            for (k1 += random.nextInt(5) - random.nextInt(5); (j1 < i) || (j1 >= i + k) || (k1 < j) || (k1 >= j + k); k1 = i2 + random.nextInt(5) - random.nextInt(5)) {
              j1 = l1 + random.nextInt(5) - random.nextInt(5);
            }
          }
        }
      }
    }
  }
}
