package net.minecraft.server;














































class ThreadSleepForever
  extends Thread
{
  ThreadSleepForever(DedicatedServer paramDedicatedServer, String paramString)
  {
    super(paramString);
    
    setDaemon(true);
    start();
  }
  
  public void run()
  {
    try {
      for (;;) {
        Thread.sleep(2147483647L);
      }
    }
    catch (InterruptedException localInterruptedException) {}
  }
}
