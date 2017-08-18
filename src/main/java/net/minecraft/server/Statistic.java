package net.minecraft.server;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;




public class Statistic
{
  public final String name;
  private final IChatBaseComponent a;
  public boolean f;
  private final Counter b;
  private final IScoreboardCriteria c;
  private Class d;
  
  public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter)
  {
    this.name = paramString;
    this.a = paramIChatBaseComponent;
    this.b = paramCounter;
    this.c = new ScoreboardStatisticCriteria(this);
    
    IScoreboardCriteria.criteria.put(this.c.getName(), this.c);
  }
  
  public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
    this(paramString, paramIChatBaseComponent, g);
  }
  
  public Statistic i() {
    this.f = true;
    return this;
  }
  
  public Statistic h() {
    if (StatisticList.a.containsKey(this.name)) {
      throw new RuntimeException("Duplicate stat id: \"" + ((Statistic)StatisticList.a.get(this.name)).a + "\" and \"" + this.a + "\" at id " + this.name);
    }
    StatisticList.stats.add(this);
    StatisticList.a.put(this.name, this);
    
    return this;
  }
  
  public boolean d() {
    return false;
  }
  




  private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
  public static Counter g = new UnknownCounter();
  





  private static DecimalFormat l = new DecimalFormat("########0.00");
  public static Counter h = new TimeCounter();
  





















  public static Counter i = new DistancesCounter();
  














  public static Counter j = new DamageCounter();
  




  public IChatBaseComponent e()
  {
    IChatBaseComponent localIChatBaseComponent = this.a.f();
    localIChatBaseComponent.getChatModifier().setColor(EnumChatFormat.GRAY);
    localIChatBaseComponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_ACHIEVEMENT, new ChatComponentText(this.name)));
    return localIChatBaseComponent;
  }
  
  public IChatBaseComponent j() {
    IChatBaseComponent localIChatBaseComponent1 = e();
    IChatBaseComponent localIChatBaseComponent2 = new ChatComponentText("[").addSibling(localIChatBaseComponent1).a("]");
    localIChatBaseComponent2.setChatModifier(localIChatBaseComponent1.getChatModifier());
    return localIChatBaseComponent2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) return true;
    if ((paramObject == null) || (getClass() != paramObject.getClass())) { return false;
    }
    Statistic localStatistic = (Statistic)paramObject;
    
    return this.name.equals(localStatistic.name);
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
  
  public String toString()
  {
    return "Stat{id=" + this.name + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
  }
  





  public IScoreboardCriteria k()
  {
    return this.c;
  }
  
  public Class l() {
    return this.d;
  }
  
  public Statistic b(Class paramClass) {
    this.d = paramClass;
    return this;
  }
}
