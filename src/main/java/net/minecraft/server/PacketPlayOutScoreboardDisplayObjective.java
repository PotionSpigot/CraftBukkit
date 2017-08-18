package net.minecraft.server;


public class PacketPlayOutScoreboardDisplayObjective
  extends Packet
{
  private int a;
  
  private String b;
  

  public PacketPlayOutScoreboardDisplayObjective() {}
  

  public PacketPlayOutScoreboardDisplayObjective(int paramInt, ScoreboardObjective paramScoreboardObjective)
  {
    this.a = paramInt;
    
    if (paramScoreboardObjective == null) {
      this.b = "";
    } else {
      this.b = paramScoreboardObjective.getName();
    }
  }
  
  public void a(PacketDataSerializer paramPacketDataSerializer)
  {
    this.a = paramPacketDataSerializer.readByte();
    this.b = paramPacketDataSerializer.c(16);
  }
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.writeByte(this.a);
    paramPacketDataSerializer.a(this.b);
  }
  
  public void a(PacketPlayOutListener paramPacketPlayOutListener)
  {
    paramPacketPlayOutListener.a(this);
  }
}
