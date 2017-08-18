package net.minecraft.server;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Logger;





































































class PacketStatisticsTracker
{
  private AtomicReference[] a = new AtomicReference[100];
  
  public PacketStatisticsTracker() {
    for (int i = 0; i < 100; i++) {
      this.a[i] = new AtomicReference(new PackStatisticData(0L, 0, 0.0D, null));
    }
  }
  
  public void a(int paramInt, long paramLong) {
    try {
      if ((paramInt < 0) || (paramInt >= 100)) {
        return;
      }
      PackStatisticData localPackStatisticData1;
      PackStatisticData localPackStatisticData2;
      do {
        localPackStatisticData1 = (PackStatisticData)this.a[paramInt].get();
        localPackStatisticData2 = localPackStatisticData1.a(paramLong);
      }
      while (!this.a[paramInt].compareAndSet(localPackStatisticData1, localPackStatisticData2));
    }
    catch (Exception localException) {
      if (NetworkStatistics.i().isDebugEnabled()) {
        NetworkStatistics.i().debug(NetworkStatistics.j(), "NetStat failed with packetId: " + paramInt, localException);
      }
    }
  }
  
  public long a() {
    long l = 0L;
    for (int i = 0; i < 100; i++) {
      l += ((PackStatisticData)this.a[i].get()).a();
    }
    return l;
  }
  
  public long b() {
    long l = 0L;
    for (int i = 0; i < 100; i++) {
      l += ((PackStatisticData)this.a[i].get()).b();
    }
    return l;
  }
  
  public PacketStatistics c() {
    int i = -1;
    Object localObject = new PackStatisticData(-1L, -1, 0.0D, null);
    for (int j = 0; j < 100; j++) {
      PackStatisticData localPackStatisticData = (PackStatisticData)this.a[j].get();
      if (PackStatisticData.a(localPackStatisticData) > PackStatisticData.a((PackStatisticData)localObject)) {
        i = j;
        localObject = localPackStatisticData;
      }
    }
    return new PacketStatistics(i, (PackStatisticData)localObject);
  }
  
  public PacketStatistics d() {
    int i = -1;
    Object localObject = new PackStatisticData(-1L, -1, 0.0D, null);
    for (int j = 0; j < 100; j++) {
      PackStatisticData localPackStatisticData = (PackStatisticData)this.a[j].get();
      if (PackStatisticData.b(localPackStatisticData) > PackStatisticData.b((PackStatisticData)localObject)) {
        i = j;
        localObject = localPackStatisticData;
      }
    }
    return new PacketStatistics(i, (PackStatisticData)localObject);
  }
  
  public PacketStatistics a(int paramInt) {
    if ((paramInt < 0) || (paramInt >= 100)) {
      return null;
    }
    
    return new PacketStatistics(paramInt, (PackStatisticData)this.a[paramInt].get());
  }
}
