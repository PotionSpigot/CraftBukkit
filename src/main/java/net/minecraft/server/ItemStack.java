package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.util.com.google.common.collect.Multimap;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.PluginManager;

public final class ItemStack
{
  public static final java.text.DecimalFormat a = new java.text.DecimalFormat("#.###");
  public int count;
  public int c;
  private Item item;
  public NBTTagCompound tag;
  private int damage;
  private EntityItemFrame g;
  
  public ItemStack(Block block) {
    this(block, 1);
  }
  
  public ItemStack(Block block, int i) {
    this(block, i, 0);
  }
  
  public ItemStack(Block block, int i, int j) {
    this(Item.getItemOf(block), i, j);
  }
  
  public ItemStack(Item item) {
    this(item, 1);
  }
  
  public ItemStack(Item item, int i) {
    this(item, i, 0);
  }
  
  public ItemStack(Item item, int i, int j) {
    this.item = item;
    this.count = i;
    
    setData(j);
  }
  




  public static ItemStack createStack(NBTTagCompound nbttagcompound)
  {
    ItemStack itemstack = new ItemStack();
    
    itemstack.c(nbttagcompound);
    return itemstack.getItem() != null ? itemstack : null;
  }
  
  private ItemStack() {}
  
  public ItemStack a(int i) {
    ItemStack itemstack = new ItemStack(this.item, i, this.damage);
    
    if (this.tag != null) {
      itemstack.tag = ((NBTTagCompound)this.tag.clone());
    }
    
    this.count -= i;
    return itemstack;
  }
  
  public Item getItem() {
    return this.item;
  }
  
  public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2)
  {
    int data = getData();
    int count = this.count;
    
    if (!(getItem() instanceof ItemBucket)) {
      world.captureBlockStates = true;
      
      if (((getItem() instanceof ItemDye)) && (getData() == 15)) {
        Block block = world.getType(i, j, k);
        if ((block == Blocks.SAPLING) || ((block instanceof BlockMushroom))) {
          world.captureTreeGeneration = true;
        }
      }
    }
    boolean flag = getItem().interactWith(this, entityhuman, world, i, j, k, l, f, f1, f2);
    int newData = getData();
    int newCount = this.count;
    this.count = count;
    setData(data);
    world.captureBlockStates = false;
    List<BlockState> blocks; if ((flag) && (world.captureTreeGeneration) && (world.capturedBlockStates.size() > 0)) {
      world.captureTreeGeneration = false;
      Location location = new Location(world.getWorld(), i, j, k);
      org.bukkit.TreeType treeType = BlockSapling.treeType;
      BlockSapling.treeType = null;
      blocks = (List)world.capturedBlockStates.clone();
      world.capturedBlockStates.clear();
      StructureGrowEvent event = null;
      if (treeType != null) {
        event = new StructureGrowEvent(location, treeType, false, (Player)entityhuman.getBukkitEntity(), blocks);
        Bukkit.getPluginManager().callEvent(event);
      }
      if ((event == null) || (!event.isCancelled()))
      {
        if ((this.count == count) && (getData() == data)) {
          setData(newData);
          this.count = newCount;
        }
        for (BlockState blockstate : blocks) {
          blockstate.update(true);
        }
      }
      
      return flag;
    }
    world.captureTreeGeneration = false;
    
    if (flag) {
      BlockPlaceEvent placeEvent = null;
      List<BlockState> blocks = (List)world.capturedBlockStates.clone();
      world.capturedBlockStates.clear();
      if (blocks.size() > 1) {
        placeEvent = CraftEventFactory.callBlockMultiPlaceEvent(world, entityhuman, blocks, i, j, k);
      } else if (blocks.size() == 1) {
        placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, (BlockState)blocks.get(0), i, j, k);
      }
      
      if ((placeEvent != null) && ((placeEvent.isCancelled()) || (!placeEvent.canBuild()))) {
        flag = false;
        
        for (BlockState blockstate : blocks) {
          blockstate.update(true, false);
        }
      }
      else {
        if ((this.count == count) && (getData() == data)) {
          setData(newData);
          this.count = newCount;
        }
        for (BlockState blockstate : blocks) {
          int x = blockstate.getX();
          int y = blockstate.getY();
          int z = blockstate.getZ();
          int updateFlag = ((org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState)blockstate).getFlag();
          org.bukkit.Material mat = blockstate.getType();
          Block oldBlock = CraftMagicNumbers.getBlock(mat);
          Block block = world.getType(x, y, z);
          
          if ((block != null) && (!(block instanceof BlockContainer))) {
            block.onPlace(world, x, y, z);
          }
          
          world.notifyAndUpdatePhysics(x, y, z, null, oldBlock, block, updateFlag);
        }
        entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
      }
    }
    world.capturedBlockStates.clear();
    

    return flag;
  }
  
  public float a(Block block) {
    return getItem().getDestroySpeed(this, block);
  }
  
  public ItemStack a(World world, EntityHuman entityhuman) {
    return getItem().a(this, world, entityhuman);
  }
  
  public ItemStack b(World world, EntityHuman entityhuman) {
    return getItem().b(this, world, entityhuman);
  }
  
  public NBTTagCompound save(NBTTagCompound nbttagcompound) {
    nbttagcompound.setShort("id", (short)Item.getId(this.item));
    nbttagcompound.setByte("Count", (byte)this.count);
    nbttagcompound.setShort("Damage", (short)this.damage);
    if (this.tag != null) {
      nbttagcompound.set("tag", this.tag.clone());
    }
    
    return nbttagcompound;
  }
  
  public void c(NBTTagCompound nbttagcompound) {
    this.item = Item.getById(nbttagcompound.getShort("id"));
    this.count = nbttagcompound.getByte("Count");
    





    setData(nbttagcompound.getShort("Damage"));
    

    if (nbttagcompound.hasKeyOfType("tag", 10))
    {
      this.tag = ((NBTTagCompound)nbttagcompound.getCompound("tag").clone());
      validateSkullSkin();
    }
  }
  

  public void validateSkullSkin()
  {
    if ((this.item == Items.SKULL) && (getData() == 3))
    {
      String owner;
      if (this.tag.hasKeyOfType("SkullOwner", 8))
      {
        owner = this.tag.getString("SkullOwner"); } else { String owner;
        if (this.tag.hasKeyOfType("SkullOwner", 10))
        {
          GameProfile profile = GameProfileSerializer.deserialize(this.tag.getCompound("SkullOwner"));
          if ((profile == null) || (!profile.getProperties().isEmpty()))
          {
            return;
          }
          
          owner = profile.getName();
        }
        else {
          return;
        }
      }
      String owner;
      final String finalOwner = owner;
      TileEntitySkull.executor.execute(new Runnable()
      {

        public void run()
        {

          final GameProfile profile = (GameProfile)TileEntitySkull.skinCache.getUnchecked(finalOwner.toLowerCase());
          if (profile != null)
          {
            MinecraftServer.getServer().processQueue.add(new Runnable()
            {

              public void run()
              {
                NBTTagCompound nbtProfile = new NBTTagCompound();
                GameProfileSerializer.serialize(nbtProfile, profile);
                ItemStack.this.tag.set("SkullOwner", nbtProfile);
              }
            });
          }
        }
      });
    }
  }
  
  public int getMaxStackSize()
  {
    return getItem().getMaxStackSize();
  }
  
  public boolean isStackable() {
    return (getMaxStackSize() > 1) && ((!g()) || (!i()));
  }
  
  public boolean g()
  {
    if (this.item.getMaxDurability() <= 0)
    {
      return false;
    }
    return (!hasTag()) || (!getTag().getBoolean("Unbreakable"));
  }
  
  public boolean usesData()
  {
    return this.item.n();
  }
  
  public boolean i() {
    return (g()) && (this.damage > 0);
  }
  
  public int j() {
    return this.damage;
  }
  
  public int getData() {
    return this.damage;
  }
  

  public void setData(int i)
  {
    if (i == 32767) {
      this.damage = i;
      return;
    }
    

    if (CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) != Blocks.AIR)
    {
      if ((!usesData()) && (!getItem().usesDurability())) {
        i = 0;
      }
    }
    

    if ((CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) == Blocks.DOUBLE_PLANT) && ((i > 5) || (i < 0))) {
      i = 0;
    }
    

    this.damage = i;
    if (this.damage < -1) {
      this.damage = 0;
    }
  }
  
  public int l() {
    return this.item.getMaxDurability();
  }
  
  public boolean isDamaged(int i, Random random)
  {
    return isDamaged(i, random, null);
  }
  
  public boolean isDamaged(int i, Random random, EntityLiving entityliving)
  {
    if (!g()) {
      return false;
    }
    if (i > 0) {
      int j = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
      int k = 0;
      
      for (int l = 0; (j > 0) && (l < i); l++) {
        if (EnchantmentDurability.a(this, j, random)) {
          k++;
        }
      }
      
      i -= k;
      
      if ((entityliving instanceof EntityPlayer)) {
        CraftItemStack item = CraftItemStack.asCraftMirror(this);
        PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player)entityliving.getBukkitEntity(), item, i);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        i = event.getDamage();
      }
      
      if (i <= 0) {
        return false;
      }
    }
    
    this.damage += i;
    return this.damage > l();
  }
  
  public void damage(int i, EntityLiving entityliving)
  {
    if (((!(entityliving instanceof EntityHuman)) || (!((EntityHuman)entityliving).abilities.canInstantlyBuild)) && 
      (g()) && 
      (isDamaged(i, entityliving.aI(), entityliving))) {
      entityliving.a(this);
      this.count -= 1;
      if ((entityliving instanceof EntityHuman)) {
        EntityHuman entityhuman = (EntityHuman)entityliving;
        
        entityhuman.a(StatisticList.BREAK_ITEM_COUNT[Item.getId(this.item)], 1);
        if ((this.count == 0) && ((getItem() instanceof ItemBow))) {
          entityhuman.bG();
        }
      }
      
      if (this.count < 0) {
        this.count = 0;
      }
      

      if ((this.count == 0) && ((entityliving instanceof EntityHuman))) {
        CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)entityliving, this);
      }
      

      this.damage = 0;
    }
  }
  

  public void a(EntityLiving entityliving, EntityHuman entityhuman)
  {
    boolean flag = this.item.a(this, entityliving, entityhuman);
    
    if (flag) {
      entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
    }
  }
  
  public void a(World world, Block block, int i, int j, int k, EntityHuman entityhuman) {
    boolean flag = this.item.a(this, world, block, i, j, k, entityhuman);
    
    if (flag) {
      entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
    }
  }
  
  public boolean b(Block block) {
    return this.item.canDestroySpecialBlock(block);
  }
  
  public boolean a(EntityHuman entityhuman, EntityLiving entityliving) {
    return this.item.a(this, entityhuman, entityliving);
  }
  
  public ItemStack cloneItemStack() {
    ItemStack itemstack = new ItemStack(this.item, this.count, this.damage);
    
    if (this.tag != null) {
      itemstack.tag = ((NBTTagCompound)this.tag.clone());
    }
    
    return itemstack;
  }
  
  public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
    return (itemstack == null) && (itemstack1 == null);
  }
  
  public static boolean matches(ItemStack itemstack, ItemStack itemstack1) {
    return (itemstack == null) && (itemstack1 == null);
  }
  
  private boolean d(ItemStack itemstack) {
    return this.count == itemstack.count;
  }
  
  public boolean doMaterialsMatch(ItemStack itemstack) {
    return (this.item == itemstack.item) && (this.damage == itemstack.damage);
  }
  
  public String a() {
    return this.item.a(this);
  }
  
  public static ItemStack b(ItemStack itemstack) {
    return itemstack == null ? null : itemstack.cloneItemStack();
  }
  
  public String toString() {
    return this.count + "x" + this.item.getName() + "@" + this.damage;
  }
  
  public void a(World world, Entity entity, int i, boolean flag) {
    if (this.c > 0) {
      this.c -= 1;
    }
    
    this.item.a(this, world, entity, i, flag);
  }
  
  public void a(World world, EntityHuman entityhuman, int i) {
    entityhuman.a(StatisticList.CRAFT_BLOCK_COUNT[Item.getId(this.item)], i);
    this.item.d(this, world, entityhuman);
  }
  
  public int n() {
    return getItem().d_(this);
  }
  
  public EnumAnimation o() {
    return getItem().d(this);
  }
  
  public void b(World world, EntityHuman entityhuman, int i) {
    getItem().a(this, world, entityhuman, i);
  }
  
  public boolean hasTag() {
    return this.tag != null;
  }
  
  public NBTTagCompound getTag() {
    return this.tag;
  }
  
  public NBTTagList getEnchantments() {
    return this.tag == null ? null : this.tag.getList("ench", 10);
  }
  
  public void setTag(NBTTagCompound nbttagcompound) {
    this.tag = nbttagcompound;
    validateSkullSkin();
  }
  
  public String getName() {
    String s = getItem().n(this);
    
    if ((this.tag != null) && (this.tag.hasKeyOfType("display", 10))) {
      NBTTagCompound nbttagcompound = this.tag.getCompound("display");
      
      if (nbttagcompound.hasKeyOfType("Name", 8)) {
        s = nbttagcompound.getString("Name");
      }
    }
    
    return s;
  }
  
  public ItemStack c(String s) {
    if (this.tag == null) {
      this.tag = new NBTTagCompound();
    }
    
    if (!this.tag.hasKeyOfType("display", 10)) {
      this.tag.set("display", new NBTTagCompound());
    }
    
    this.tag.getCompound("display").setString("Name", s);
    return this;
  }
  
  public void t() {
    if ((this.tag != null) && 
      (this.tag.hasKeyOfType("display", 10))) {
      NBTTagCompound nbttagcompound = this.tag.getCompound("display");
      
      nbttagcompound.remove("Name");
      if (nbttagcompound.isEmpty()) {
        this.tag.remove("display");
        if (this.tag.isEmpty()) {
          setTag((NBTTagCompound)null);
        }
      }
    }
  }
  
  public boolean hasName()
  {
    return !this.tag.hasKeyOfType("display", 10) ? false : this.tag == null ? false : this.tag.getCompound("display").hasKeyOfType("Name", 8);
  }
  
  public EnumItemRarity w() {
    return getItem().f(this);
  }
  
  public boolean x() {
    return getItem().e_(this);
  }
  
  public void addEnchantment(Enchantment enchantment, int i) {
    if (this.tag == null) {
      setTag(new NBTTagCompound());
    }
    
    if (!this.tag.hasKeyOfType("ench", 9)) {
      this.tag.set("ench", new NBTTagList());
    }
    
    NBTTagList nbttaglist = this.tag.getList("ench", 10);
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    
    nbttagcompound.setShort("id", (short)enchantment.id);
    nbttagcompound.setShort("lvl", (short)(byte)i);
    nbttaglist.add(nbttagcompound);
  }
  
  public boolean hasEnchantments() {
    return (this.tag != null) && (this.tag.hasKeyOfType("ench", 9));
  }
  
  public void a(String s, NBTBase nbtbase) {
    if (this.tag == null) {
      setTag(new NBTTagCompound());
    }
    
    this.tag.set(s, nbtbase);
  }
  
  public boolean z() {
    return getItem().v();
  }
  
  public boolean A() {
    return this.g != null;
  }
  
  public void a(EntityItemFrame entityitemframe) {
    this.g = entityitemframe;
  }
  
  public EntityItemFrame B() {
    return this.g;
  }
  
  public int getRepairCost() {
    return (hasTag()) && (this.tag.hasKeyOfType("RepairCost", 3)) ? this.tag.getInt("RepairCost") : 0;
  }
  
  public void setRepairCost(int i) {
    if (!hasTag()) {
      this.tag = new NBTTagCompound();
    }
    
    this.tag.setInt("RepairCost", i);
  }
  
  public Multimap D()
  {
    Object object;
    if ((hasTag()) && (this.tag.hasKeyOfType("AttributeModifiers", 9))) {
      Object object = net.minecraft.util.com.google.common.collect.HashMultimap.create();
      NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);
      
      for (int i = 0; i < nbttaglist.size(); i++) {
        NBTTagCompound nbttagcompound = nbttaglist.get(i);
        AttributeModifier attributemodifier = GenericAttributes.a(nbttagcompound);
        
        if ((attributemodifier.a().getLeastSignificantBits() != 0L) && (attributemodifier.a().getMostSignificantBits() != 0L)) {
          ((Multimap)object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
        }
      }
    } else {
      object = getItem().k();
    }
    
    return (Multimap)object;
  }
  
  public void setItem(Item item) {
    this.item = item;
    setData(getData());
  }
  
  public IChatBaseComponent E() {
    IChatBaseComponent ichatbasecomponent = new ChatComponentText("[").a(getName()).a("]");
    
    if (this.item != null) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      
      save(nbttagcompound);
      ichatbasecomponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
      ichatbasecomponent.getChatModifier().setColor(w().e);
    }
    
    return ichatbasecomponent;
  }
}
