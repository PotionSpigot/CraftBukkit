package net.minecraft.server;


public class CraftingStatistic
  extends Statistic
{
  private final Item a;
  
  public CraftingStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Item paramItem)
  {
    super(paramString, paramIChatBaseComponent);
    this.a = paramItem;
  }
}
