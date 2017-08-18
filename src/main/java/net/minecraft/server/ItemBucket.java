package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.github.paperspigot.PaperSpigotConfig;


public class ItemBucket
  extends Item
{
  private Block a;
  
  public ItemBucket(Block block)
  {
    if (((block == Blocks.LAVA) && (PaperSpigotConfig.stackableLavaBuckets)) || ((block == Blocks.WATER) && (PaperSpigotConfig.stackableWaterBuckets)))
    {
      this.maxStackSize = org.bukkit.Material.BUCKET.getMaxStackSize();
    } else {
      this.maxStackSize = 1;
    }
    
    this.a = block;
    a(CreativeModeTab.f);
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
    boolean flag = this.a == Blocks.AIR;
    MovingObjectPosition movingobjectposition = a(world, entityhuman, flag);
    
    if (movingobjectposition == null) {
      return itemstack;
    }
    if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
      int i = movingobjectposition.b;
      int j = movingobjectposition.c;
      int k = movingobjectposition.d;
      
      if (!world.a(entityhuman, i, j, k)) {
        return itemstack;
      }
      
      if (flag) {
        if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
          return itemstack;
        }
        
        Material material = world.getType(i, j, k).getMaterial();
        int l = world.getData(i, j, k);
        
        if ((material == Material.WATER) && (l == 0))
        {
          PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Items.WATER_BUCKET);
          
          if (event.isCancelled()) {
            return itemstack;
          }
          
          world.setAir(i, j, k);
          return a(itemstack, entityhuman, Items.WATER_BUCKET, event.getItemStack());
        }
        
        if ((material == Material.LAVA) && (l == 0))
        {
          PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Items.LAVA_BUCKET);
          
          if (event.isCancelled()) {
            return itemstack;
          }
          
          world.setAir(i, j, k);
          return a(itemstack, entityhuman, Items.LAVA_BUCKET, event.getItemStack());
        }
      } else {
        if (this.a == Blocks.AIR)
        {
          PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, i, j, k, movingobjectposition.face, itemstack);
          
          if (event.isCancelled()) {
            return itemstack;
          }
          
          return CraftItemStack.asNMSCopy(event.getItemStack());
        }
        
        int clickedX = i;int clickedY = j;int clickedZ = k;
        

        if (movingobjectposition.face == 0) {
          j--;
        }
        
        if (movingobjectposition.face == 1) {
          j++;
        }
        
        if (movingobjectposition.face == 2) {
          k--;
        }
        
        if (movingobjectposition.face == 3) {
          k++;
        }
        
        if (movingobjectposition.face == 4) {
          i--;
        }
        
        if (movingobjectposition.face == 5) {
          i++;
        }
        
        if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
          return itemstack;
        }
        

        PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.face, itemstack);
        
        if (event.isCancelled()) {
          return itemstack;
        }
        

        if ((a(world, i, j, k)) && (!entityhuman.abilities.canInstantlyBuild))
        {
          if (((this == Items.LAVA_BUCKET) && (PaperSpigotConfig.stackableLavaBuckets)) || ((this == Items.WATER_BUCKET) && (PaperSpigotConfig.stackableWaterBuckets)))
          {
            itemstack.count -= 1;
            if (itemstack.count <= 0) {
              return CraftItemStack.asNMSCopy(event.getItemStack());
            }
            if (!entityhuman.inventory.pickup(CraftItemStack.asNMSCopy(event.getItemStack()))) {
              entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()), false);
            }
            return itemstack;
          }
          
          return CraftItemStack.asNMSCopy(event.getItemStack());
        }
      }
    }
    
    return itemstack;
  }
  

  private ItemStack a(ItemStack itemstack, EntityHuman entityhuman, Item item, org.bukkit.inventory.ItemStack result)
  {
    if (entityhuman.abilities.canInstantlyBuild)
      return itemstack;
    if (--itemstack.count <= 0) {
      return CraftItemStack.asNMSCopy(result);
    }
    if (!entityhuman.inventory.pickup(CraftItemStack.asNMSCopy(result))) {
      entityhuman.drop(CraftItemStack.asNMSCopy(result), false);
    }
    
    return itemstack;
  }
  
  public boolean a(World world, int i, int j, int k)
  {
    if (this.a == Blocks.AIR) {
      return false;
    }
    Material material = world.getType(i, j, k).getMaterial();
    boolean flag = !material.isBuildable();
    
    if ((!world.isEmpty(i, j, k)) && (!flag)) {
      return false;
    }
    if ((world.worldProvider.f) && (this.a == Blocks.WATER)) {
      world.makeSound(i + 0.5F, j + 0.5F, k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
      
      for (int l = 0; l < 8; l++) {
        world.addParticle("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D);
      }
    } else {
      if ((!world.isStatic) && (flag) && (!material.isLiquid())) {
        world.setAir(i, j, k, true);
      }
      
      world.setTypeAndData(i, j, k, this.a, 0, 3);
    }
    
    return true;
  }
}
