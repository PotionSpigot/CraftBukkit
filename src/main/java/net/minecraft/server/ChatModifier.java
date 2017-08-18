package net.minecraft.server;


public class ChatModifier
{
  private ChatModifier a;
  
  private EnumChatFormat b;
  
  private Boolean c;
  
  private Boolean d;
  
  private Boolean e;
  
  private Boolean f;
  private Boolean g;
  private ChatClickable h;
  private ChatHoverable i;
  
  public EnumChatFormat a()
  {
    return this.b == null ? n().a() : this.b;
  }
  
  public boolean b() {
    return this.c == null ? n().b() : this.c.booleanValue();
  }
  
  public boolean c() {
    return this.d == null ? n().c() : this.d.booleanValue();
  }
  
  public boolean d() {
    return this.f == null ? n().d() : this.f.booleanValue();
  }
  
  public boolean e() {
    return this.e == null ? n().e() : this.e.booleanValue();
  }
  
  public boolean f() {
    return this.g == null ? n().f() : this.g.booleanValue();
  }
  
  public boolean g() {
    return (this.c == null) && (this.d == null) && (this.f == null) && (this.e == null) && (this.g == null) && (this.b == null) && (this.h == null) && (this.i == null);
  }
  
  public ChatClickable h() {
    return this.h == null ? n().h() : this.h;
  }
  
  public ChatHoverable i() {
    return this.i == null ? n().i() : this.i;
  }
  
  public ChatModifier setColor(EnumChatFormat paramEnumChatFormat) {
    this.b = paramEnumChatFormat;
    return this;
  }
  
  public ChatModifier setBold(Boolean paramBoolean) {
    this.c = paramBoolean;
    return this;
  }
  
  public ChatModifier setItalic(Boolean paramBoolean) {
    this.d = paramBoolean;
    return this;
  }
  
  public ChatModifier setStrikethrough(Boolean paramBoolean) {
    this.f = paramBoolean;
    return this;
  }
  
  public ChatModifier setUnderline(Boolean paramBoolean) {
    this.e = paramBoolean;
    return this;
  }
  
  public ChatModifier setRandom(Boolean paramBoolean) {
    this.g = paramBoolean;
    return this;
  }
  
  public ChatModifier setChatClickable(ChatClickable paramChatClickable) {
    this.h = paramChatClickable;
    return this;
  }
  
  public ChatModifier a(ChatHoverable paramChatHoverable) {
    this.i = paramChatHoverable;
    return this;
  }
  
  public ChatModifier a(ChatModifier paramChatModifier) {
    this.a = paramChatModifier;
    return this;
  }
  























  private ChatModifier n()
  {
    return this.a == null ? j : this.a;
  }
  














  public String toString()
  {
    return "Style{hasParent=" + (this.a != null) + ", color=" + this.b + ", bold=" + this.c + ", italic=" + this.d + ", underlined=" + this.e + ", obfuscated=" + this.g + ", clickEvent=" + h() + ", hoverEvent=" + i() + '}';
  }
  









  public boolean equals(Object paramObject)
  {
    if (this == paramObject) return true;
    if ((paramObject instanceof ChatModifier))
    {
      ChatModifier localChatModifier = (ChatModifier)paramObject;
      
      return (b() == localChatModifier.b()) && (a() == localChatModifier.a()) && (c() == localChatModifier.c()) && (f() == localChatModifier.f()) && (d() == localChatModifier.d()) && (e() == localChatModifier.e()) && (h() != null ? h().equals(localChatModifier.h()) : localChatModifier.h() == null) && (i() != null ? i().equals(localChatModifier.i()) : localChatModifier.i() == null);
    }
    







    return false;
  }
  
  public int hashCode()
  {
    int k = this.b.hashCode();
    k = 31 * k + this.c.hashCode();
    k = 31 * k + this.d.hashCode();
    k = 31 * k + this.e.hashCode();
    k = 31 * k + this.f.hashCode();
    k = 31 * k + this.g.hashCode();
    k = 31 * k + this.h.hashCode();
    k = 31 * k + this.i.hashCode();
    return k;
  }
  
  private static final ChatModifier j = new ChatStyleRoot();
  






















  public ChatModifier clone()
  {
    ChatModifier localChatModifier = new ChatModifier();
    localChatModifier.c = this.c;
    localChatModifier.d = this.d;
    localChatModifier.f = this.f;
    localChatModifier.e = this.e;
    localChatModifier.g = this.g;
    localChatModifier.b = this.b;
    localChatModifier.h = this.h;
    localChatModifier.i = this.i;
    localChatModifier.a = this.a;
    return localChatModifier;
  }
  
  public ChatModifier m() {
    ChatModifier localChatModifier = new ChatModifier();
    
    localChatModifier.setBold(Boolean.valueOf(b()));
    localChatModifier.setItalic(Boolean.valueOf(c()));
    localChatModifier.setStrikethrough(Boolean.valueOf(d()));
    localChatModifier.setUnderline(Boolean.valueOf(e()));
    localChatModifier.setRandom(Boolean.valueOf(f()));
    localChatModifier.setColor(a());
    localChatModifier.setChatClickable(h());
    localChatModifier.a(i());
    
    return localChatModifier;
  }
}
