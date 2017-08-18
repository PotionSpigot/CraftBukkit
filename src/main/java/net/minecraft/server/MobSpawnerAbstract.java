package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.github.paperspigot.PaperSpigotWorldConfig;
import org.spigotmc.SpigotWorldConfig;

public abstract class MobSpawnerAbstract
{
  public int spawnDelay = 20;
  private String mobName = "Pig";
  private List mobs;
  private TileEntityMobSpawnerData spawnData;
  public double c;
  public double d;
  private int minSpawnDelay = 200;
  private int maxSpawnDelay = 800;
  private int spawnCount = 4;
  private Entity j;
  private int maxNearbyEntities = 6;
  private int requiredPlayerRange = 16;
  private int spawnRange = 4;
  private int tickDelay = 0;
  

  public String getMobName()
  {
    if (i() == null) {
      if (this.mobName.equals("Minecart")) {
        this.mobName = "MinecartRideable";
      }
      
      return this.mobName;
    }
    return i().c;
  }
  
  public void setMobName(String s)
  {
    this.mobName = s;
  }
  
  public boolean f() {
    return a().findNearbyPlayerWhoAffectsSpawning(b() + 0.5D, c() + 0.5D, d() + 0.5D, this.requiredPlayerRange) != null;
  }
  
  public void g()
  {
    if ((this.spawnDelay > 0) && (--this.tickDelay > 0)) return;
    this.tickDelay = a().paperSpigotConfig.mobSpawnerTickRate;
    
    if (f())
    {

      if (a().isStatic) {
        double d1 = b() + a().random.nextFloat();
        double d2 = c() + a().random.nextFloat();
        
        double d0 = d() + a().random.nextFloat();
        a().addParticle("smoke", d1, d2, d0, 0.0D, 0.0D, 0.0D);
        a().addParticle("flame", d1, d2, d0, 0.0D, 0.0D, 0.0D);
        if (this.spawnDelay > 0) {
          this.spawnDelay -= this.tickDelay;
        }
        
        this.d = this.c;
        this.c = ((this.c + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D);
      } else {
        if (this.spawnDelay < -this.tickDelay) {
          j();
        }
        
        if (this.spawnDelay > 0) {
          this.spawnDelay -= this.tickDelay;
          return;
        }
        
        boolean flag = false;
        for (int i = 0; i < this.spawnCount; i++) {
          Entity entity = EntityTypes.createEntityByName(getMobName(), a());
          
          if (entity == null) {
            return;
          }
          
          int j = a().a(entity.getClass(), AxisAlignedBB.a(b(), c(), d(), b() + 1, c() + 1, d() + 1).grow(this.spawnRange * 2, 4.0D, this.spawnRange * 2)).size();
          
          if (j >= this.maxNearbyEntities) {
            j();
            return;
          }
          
          double d0 = b() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange;
          double d3 = c() + a().random.nextInt(3) - 1;
          double d4 = d() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange;
          EntityInsentient entityinsentient = (entity instanceof EntityInsentient) ? (EntityInsentient)entity : null;
          
          entity.setPositionRotation(d0, d3, d4, a().random.nextFloat() * 360.0F, 0.0F);
          if ((entityinsentient == null) || (entityinsentient.canSpawn())) {
            a(entity);
            a().triggerEffect(2004, b(), c(), d(), 0);
            if (entityinsentient != null) {
              entityinsentient.s();
            }
            
            flag = true;
          }
        }
        
        if (flag) {
          j();
        }
      }
    }
  }
  
  public Entity a(Entity entity) {
    if (i() != null) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      
      entity.d(nbttagcompound);
      Iterator iterator = i().b.c().iterator();
      
      while (iterator.hasNext()) {
        String s = (String)iterator.next();
        NBTBase nbtbase = i().b.get(s);
        
        nbttagcompound.set(s, nbtbase.clone());
      }
      
      entity.f(nbttagcompound);
      if (entity.world != null)
      {
        SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, b(), c(), d());
        if (!event.isCancelled()) {
          entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
          
          if (entity.world.spigotConfig.nerfSpawnerMobs)
          {
            entity.fromMobSpawner = true;
          }
        }
      }
      

      NBTTagCompound nbttagcompound1;
      

      for (Entity entity1 = entity; nbttagcompound.hasKeyOfType("Riding", 10); nbttagcompound = nbttagcompound1) {
        nbttagcompound1 = nbttagcompound.getCompound("Riding");
        Entity entity2 = EntityTypes.createEntityByName(nbttagcompound1.getString("id"), entity.world);
        
        if (entity2 != null) {
          NBTTagCompound nbttagcompound2 = new NBTTagCompound();
          
          entity2.d(nbttagcompound2);
          Iterator iterator1 = nbttagcompound1.c().iterator();
          
          while (iterator1.hasNext()) {
            String s1 = (String)iterator1.next();
            NBTBase nbtbase1 = nbttagcompound1.get(s1);
            
            nbttagcompound2.set(s1, nbtbase1.clone());
          }
          
          entity2.f(nbttagcompound2);
          entity2.setPositionRotation(entity1.locX, entity1.locY, entity1.locZ, entity1.yaw, entity1.pitch);
          
          SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity2, b(), c(), d());
          if (!event.isCancelled())
          {

            if (entity.world != null) {
              entity.world.addEntity(entity2, CreatureSpawnEvent.SpawnReason.SPAWNER);
            }
            
            entity1.mount(entity2);
          }
        } else {
          entity1 = entity2;
        }
      } } else if (((entity instanceof EntityLiving)) && (entity.world != null)) {
      ((EntityInsentient)entity).prepare((GroupDataEntity)null);
      
      SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, b(), c(), d());
      if (!event.isCancelled()) {
        a().addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
        
        if (entity.world.spigotConfig.nerfSpawnerMobs)
        {
          entity.fromMobSpawner = true;
        }
      }
    }
    


    return entity;
  }
  
  private void j() {
    if (this.maxSpawnDelay <= this.minSpawnDelay) {
      this.spawnDelay = this.minSpawnDelay;
    } else {
      int i = this.maxSpawnDelay - this.minSpawnDelay;
      
      this.spawnDelay = (this.minSpawnDelay + a().random.nextInt(i));
    }
    
    if ((this.mobs != null) && (this.mobs.size() > 0)) {
      a((TileEntityMobSpawnerData)WeightedRandom.a(a().random, this.mobs));
    }
    
    a(1);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.mobName = nbttagcompound.getString("EntityId");
    this.spawnDelay = nbttagcompound.getShort("Delay");
    if (nbttagcompound.hasKeyOfType("SpawnPotentials", 9)) {
      this.mobs = new ArrayList();
      NBTTagList nbttaglist = nbttagcompound.getList("SpawnPotentials", 10);
      
      for (int i = 0; i < nbttaglist.size(); i++) {
        this.mobs.add(new TileEntityMobSpawnerData(this, nbttaglist.get(i)));
      }
    } else {
      this.mobs = null;
    }
    
    if (nbttagcompound.hasKeyOfType("SpawnData", 10)) {
      a(new TileEntityMobSpawnerData(this, nbttagcompound.getCompound("SpawnData"), this.mobName));
    } else {
      a((TileEntityMobSpawnerData)null);
    }
    
    if (nbttagcompound.hasKeyOfType("MinSpawnDelay", 99)) {
      this.minSpawnDelay = nbttagcompound.getShort("MinSpawnDelay");
      this.maxSpawnDelay = nbttagcompound.getShort("MaxSpawnDelay");
      this.spawnCount = nbttagcompound.getShort("SpawnCount");
    }
    
    if (nbttagcompound.hasKeyOfType("MaxNearbyEntities", 99)) {
      this.maxNearbyEntities = nbttagcompound.getShort("MaxNearbyEntities");
      this.requiredPlayerRange = nbttagcompound.getShort("RequiredPlayerRange");
    }
    
    if (nbttagcompound.hasKeyOfType("SpawnRange", 99)) {
      this.spawnRange = nbttagcompound.getShort("SpawnRange");
    }
    
    if ((a() != null) && (a().isStatic)) {
      this.j = null;
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setString("EntityId", getMobName());
    nbttagcompound.setShort("Delay", (short)this.spawnDelay);
    nbttagcompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
    nbttagcompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
    nbttagcompound.setShort("SpawnCount", (short)this.spawnCount);
    nbttagcompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
    nbttagcompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
    nbttagcompound.setShort("SpawnRange", (short)this.spawnRange);
    if (i() != null) {
      nbttagcompound.set("SpawnData", i().b.clone());
    }
    
    if ((i() != null) || ((this.mobs != null) && (this.mobs.size() > 0))) {
      NBTTagList nbttaglist = new NBTTagList();
      
      if ((this.mobs != null) && (this.mobs.size() > 0)) {
        Iterator iterator = this.mobs.iterator();
        
        while (iterator.hasNext()) {
          TileEntityMobSpawnerData tileentitymobspawnerdata = (TileEntityMobSpawnerData)iterator.next();
          
          nbttaglist.add(tileentitymobspawnerdata.a());
        }
      } else {
        nbttaglist.add(i().a());
      }
      
      nbttagcompound.set("SpawnPotentials", nbttaglist);
    }
  }
  
  public boolean b(int i) {
    if ((i == 1) && (a().isStatic)) {
      this.spawnDelay = this.minSpawnDelay;
      return true;
    }
    return false;
  }
  
  public TileEntityMobSpawnerData i()
  {
    return this.spawnData;
  }
  
  public void a(TileEntityMobSpawnerData tileentitymobspawnerdata) {
    this.spawnData = tileentitymobspawnerdata;
  }
  
  public abstract void a(int paramInt);
  
  public abstract World a();
  
  public abstract int b();
  
  public abstract int c();
  
  public abstract int d();
}
