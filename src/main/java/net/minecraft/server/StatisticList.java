package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;





public class StatisticList
{
  protected static Map a = new HashMap();
  
  public static List stats = new ArrayList();
  public static List c = new ArrayList();
  public static List d = new ArrayList();
  public static List e = new ArrayList();
  
  public static Statistic f = new CounterStatistic("stat.leaveGame", new ChatMessage("stat.leaveGame", new Object[0])).i().h();
  
  public static Statistic g = new CounterStatistic("stat.playOneMinute", new ChatMessage("stat.playOneMinute", new Object[0]), Statistic.h).i().h();
  
  public static Statistic h = new CounterStatistic("stat.walkOneCm", new ChatMessage("stat.walkOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic i = new CounterStatistic("stat.swimOneCm", new ChatMessage("stat.swimOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic j = new CounterStatistic("stat.fallOneCm", new ChatMessage("stat.fallOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic k = new CounterStatistic("stat.climbOneCm", new ChatMessage("stat.climbOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic l = new CounterStatistic("stat.flyOneCm", new ChatMessage("stat.flyOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic m = new CounterStatistic("stat.diveOneCm", new ChatMessage("stat.diveOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic n = new CounterStatistic("stat.minecartOneCm", new ChatMessage("stat.minecartOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic o = new CounterStatistic("stat.boatOneCm", new ChatMessage("stat.boatOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic p = new CounterStatistic("stat.pigOneCm", new ChatMessage("stat.pigOneCm", new Object[0]), Statistic.i).i().h();
  public static Statistic q = new CounterStatistic("stat.horseOneCm", new ChatMessage("stat.horseOneCm", new Object[0]), Statistic.i).i().h();
  
  public static Statistic r = new CounterStatistic("stat.jump", new ChatMessage("stat.jump", new Object[0])).i().h();
  public static Statistic s = new CounterStatistic("stat.drop", new ChatMessage("stat.drop", new Object[0])).i().h();
  
  public static Statistic t = new CounterStatistic("stat.damageDealt", new ChatMessage("stat.damageDealt", new Object[0]), Statistic.j).h();
  public static Statistic u = new CounterStatistic("stat.damageTaken", new ChatMessage("stat.damageTaken", new Object[0]), Statistic.j).h();
  public static Statistic v = new CounterStatistic("stat.deaths", new ChatMessage("stat.deaths", new Object[0])).h();
  public static Statistic w = new CounterStatistic("stat.mobKills", new ChatMessage("stat.mobKills", new Object[0])).h();
  public static Statistic x = new CounterStatistic("stat.animalsBred", new ChatMessage("stat.animalsBred", new Object[0])).h();
  public static Statistic y = new CounterStatistic("stat.playerKills", new ChatMessage("stat.playerKills", new Object[0])).h();
  public static Statistic z = new CounterStatistic("stat.fishCaught", new ChatMessage("stat.fishCaught", new Object[0])).h();
  public static Statistic A = new CounterStatistic("stat.junkFished", new ChatMessage("stat.junkFished", new Object[0])).h();
  public static Statistic B = new CounterStatistic("stat.treasureFished", new ChatMessage("stat.treasureFished", new Object[0])).h();
  
  public static final Statistic[] MINE_BLOCK_COUNT = new Statistic['က'];
  public static final Statistic[] CRAFT_BLOCK_COUNT = new Statistic['紀'];
  public static final Statistic[] USE_ITEM_COUNT = new Statistic['紀'];
  public static final Statistic[] BREAK_ITEM_COUNT = new Statistic['紀'];
  
  public static void a() {
    c();
    d();
    e();
    b();
    
    AchievementList.a();
    EntityTypes.a();
  }
  
  private static void b() {
    HashSet localHashSet = new HashSet();
    
    for (Iterator localIterator = CraftingManager.getInstance().getRecipes().iterator(); localIterator.hasNext();) { localObject = (IRecipe)localIterator.next();
      if (((IRecipe)localObject).b() != null)
        localHashSet.add(((IRecipe)localObject).b().getItem()); }
    Object localObject;
    for (localIterator = RecipesFurnace.getInstance().getRecipes().values().iterator(); localIterator.hasNext();) { localObject = (ItemStack)localIterator.next();
      localHashSet.add(((ItemStack)localObject).getItem());
    }
    
    for (localIterator = localHashSet.iterator(); localIterator.hasNext();) { localObject = (Item)localIterator.next();
      if (localObject != null)
      {
        int i1 = Item.getId((Item)localObject);
        CRAFT_BLOCK_COUNT[i1] = new CraftingStatistic("stat.craftItem." + i1, new ChatMessage("stat.craftItem", new Object[] { new ItemStack((Item)localObject).E() }), (Item)localObject).h();
      }
    }
    a(CRAFT_BLOCK_COUNT);
  }
  
  private static void c() {
    for (Block localBlock : Block.REGISTRY) {
      if (Item.getItemOf(localBlock) != null)
      {
        int i1 = Block.getId(localBlock);
        if (localBlock.G()) {
          MINE_BLOCK_COUNT[i1] = new CraftingStatistic("stat.mineBlock." + i1, new ChatMessage("stat.mineBlock", new Object[] { new ItemStack(localBlock).E() }), Item.getItemOf(localBlock)).h();
          e.add((CraftingStatistic)MINE_BLOCK_COUNT[i1]);
        }
      }
    }
    a(MINE_BLOCK_COUNT);
  }
  
  private static void d() {
    for (Item localItem : Item.REGISTRY) {
      if (localItem != null)
      {
        int i1 = Item.getId(localItem);
        
        USE_ITEM_COUNT[i1] = new CraftingStatistic("stat.useItem." + i1, new ChatMessage("stat.useItem", new Object[] { new ItemStack(localItem).E() }), localItem).h();
        
        if (!(localItem instanceof ItemBlock)) {
          d.add((CraftingStatistic)USE_ITEM_COUNT[i1]);
        }
      }
    }
    a(USE_ITEM_COUNT);
  }
  
  private static void e() {
    for (Item localItem : Item.REGISTRY) {
      if (localItem != null)
      {
        int i1 = Item.getId(localItem);
        
        if (localItem.usesDurability()) {
          BREAK_ITEM_COUNT[i1] = new CraftingStatistic("stat.breakItem." + i1, new ChatMessage("stat.breakItem", new Object[] { new ItemStack(localItem).E() }), localItem).h();
        }
      }
    }
    a(BREAK_ITEM_COUNT);
  }
  
  private static void a(Statistic[] paramArrayOfStatistic) {
    a(paramArrayOfStatistic, Blocks.STATIONARY_WATER, Blocks.WATER);
    a(paramArrayOfStatistic, Blocks.STATIONARY_LAVA, Blocks.LAVA);
    
    a(paramArrayOfStatistic, Blocks.JACK_O_LANTERN, Blocks.PUMPKIN);
    a(paramArrayOfStatistic, Blocks.BURNING_FURNACE, Blocks.FURNACE);
    a(paramArrayOfStatistic, Blocks.GLOWING_REDSTONE_ORE, Blocks.REDSTONE_ORE);
    
    a(paramArrayOfStatistic, Blocks.DIODE_ON, Blocks.DIODE_OFF);
    a(paramArrayOfStatistic, Blocks.REDSTONE_COMPARATOR_ON, Blocks.REDSTONE_COMPARATOR_OFF);
    a(paramArrayOfStatistic, Blocks.REDSTONE_TORCH_ON, Blocks.REDSTONE_TORCH_OFF);
    a(paramArrayOfStatistic, Blocks.REDSTONE_LAMP_ON, Blocks.REDSTONE_LAMP_OFF);
    
    a(paramArrayOfStatistic, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM);
    a(paramArrayOfStatistic, Blocks.DOUBLE_STEP, Blocks.STEP);
    a(paramArrayOfStatistic, Blocks.WOOD_DOUBLE_STEP, Blocks.WOOD_STEP);
    
    a(paramArrayOfStatistic, Blocks.GRASS, Blocks.DIRT);
    a(paramArrayOfStatistic, Blocks.SOIL, Blocks.DIRT);
  }
  
  private static void a(Statistic[] paramArrayOfStatistic, Block paramBlock1, Block paramBlock2) {
    int i1 = Block.getId(paramBlock1);
    int i2 = Block.getId(paramBlock2);
    
    if ((paramArrayOfStatistic[i1] != null) && (paramArrayOfStatistic[i2] == null))
    {
      paramArrayOfStatistic[i2] = paramArrayOfStatistic[i1];
      return;
    }
    
    stats.remove(paramArrayOfStatistic[i1]);
    e.remove(paramArrayOfStatistic[i1]);
    c.remove(paramArrayOfStatistic[i1]);
    paramArrayOfStatistic[i1] = paramArrayOfStatistic[i2];
  }
  
  public static Statistic a(MonsterEggInfo paramMonsterEggInfo) {
    String str = EntityTypes.b(paramMonsterEggInfo.a);
    if (str == null) return null;
    return new Statistic("stat.killEntity." + str, new ChatMessage("stat.entityKill", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) })).h();
  }
  
  public static Statistic b(MonsterEggInfo paramMonsterEggInfo) {
    String str = EntityTypes.b(paramMonsterEggInfo.a);
    if (str == null) return null;
    return new Statistic("stat.entityKilledBy." + str, new ChatMessage("stat.entityKilledBy", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) })).h();
  }
  
  public static Statistic getStatistic(String paramString) {
    return (Statistic)a.get(paramString);
  }
}
