package net.minecraft.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;




public class NetworkStatistics
{
  private static final Logger a = ;
  private static final Marker b = MarkerManager.getMarker("NETSTAT_MARKER", NetworkManager.c);
  private PacketStatisticsTracker c;
  private PacketStatisticsTracker d;
  
  public NetworkStatistics() { this.c = new PacketStatisticsTracker();
    this.d = new PacketStatisticsTracker();
  }
  
  public void a(int paramInt, long paramLong) {
    this.c.a(paramInt, paramLong);
  }
  
  public void b(int paramInt, long paramLong) {
    this.d.a(paramInt, paramLong);
  }
  
  public long a() {
    return this.c.a();
  }
  
  public long b() {
    return this.d.a();
  }
  
  public long c() {
    return this.c.b();
  }
  
  public long d() {
    return this.d.b();
  }
  
  public PacketStatistics e() {
    return this.c.c();
  }
  
  public PacketStatistics f() {
    return this.c.d();
  }
  
  public PacketStatistics g() {
    return this.d.c();
  }
  
  public PacketStatistics h() {
    return this.d.d();
  }
  
  public PacketStatistics a(int paramInt) {
    return this.c.a(paramInt);
  }
  
  public PacketStatistics b(int paramInt) {
    return this.d.a(paramInt);
  }
}
