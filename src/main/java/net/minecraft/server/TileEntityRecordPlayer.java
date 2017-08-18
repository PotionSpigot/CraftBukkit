package net.minecraft.server;

public class TileEntityRecordPlayer
  extends TileEntity
{
  private ItemStack record;
  
  public void a(NBTTagCompound nbttagcompound)
  {
    super.a(nbttagcompound);
    if (nbttagcompound.hasKeyOfType("RecordItem", 10)) {
      setRecord(ItemStack.createStack(nbttagcompound.getCompound("RecordItem")));
    } else if (nbttagcompound.getInt("Record") > 0) {
      setRecord(new ItemStack(Item.getById(nbttagcompound.getInt("Record")), 1, 0));
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    if (getRecord() != null) {
      nbttagcompound.set("RecordItem", getRecord().save(new NBTTagCompound()));
      nbttagcompound.setInt("Record", Item.getId(getRecord().getItem()));
    }
  }
  
  public ItemStack getRecord() {
    return this.record;
  }
  
  public void setRecord(ItemStack itemstack)
  {
    if (itemstack != null) {
      itemstack.count = 1;
    }
    

    this.record = itemstack;
    update();
  }
}
