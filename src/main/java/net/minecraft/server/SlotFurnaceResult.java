package net.minecraft.server;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class SlotFurnaceResult extends Slot
{
  private EntityHuman a;
  private int b;
  
  public SlotFurnaceResult(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k)
  {
    super(iinventory, i, j, k);
    this.a = entityhuman;
  }
  
  public boolean isAllowed(ItemStack itemstack) {
    return false;
  }
  
  public ItemStack a(int i) {
    if (hasItem()) {
      this.b += Math.min(i, getItem().count);
    }
    
    return super.a(i);
  }
  
  public void a(EntityHuman entityhuman, ItemStack itemstack) {
    b(itemstack);
    super.a(entityhuman, itemstack);
  }
  
  protected void a(ItemStack itemstack, int i) {
    this.b += i;
    b(itemstack);
  }
  
  protected void b(ItemStack itemstack) {
    itemstack.a(this.a.world, this.a, this.b);
    if (!this.a.world.isStatic) {
      int i = this.b;
      float f = RecipesFurnace.getInstance().b(itemstack);
      

      if (f == 0.0F) {
        i = 0;
      } else if (f < 1.0F) {
        int j = MathHelper.d(i * f);
        if ((j < MathHelper.f(i * f)) && ((float)Math.random() < i * f - j)) {
          j++;
        }
        
        i = j;
      }
      

      Player player = (Player)this.a.getBukkitEntity();
      TileEntityFurnace furnace = (TileEntityFurnace)this.inventory;
      Block block = this.a.world.getWorld().getBlockAt(furnace.x, furnace.y, furnace.z);
      
      FurnaceExtractEvent event = new FurnaceExtractEvent(player, block, org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getMaterial(itemstack.getItem()), itemstack.count, i);
      this.a.world.getServer().getPluginManager().callEvent(event);
      
      i = event.getExpToDrop();
      

      while (i > 0) {
        int j = EntityExperienceOrb.getOrbValue(i);
        i -= j;
        this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5D, this.a.locZ + 0.5D, j));
      }
    }
    
    this.b = 0;
    if (itemstack.getItem() == Items.IRON_INGOT) {
      this.a.a(AchievementList.k, 1);
    }
    
    if (itemstack.getItem() == Items.COOKED_FISH) {
      this.a.a(AchievementList.p, 1);
    }
  }
}
