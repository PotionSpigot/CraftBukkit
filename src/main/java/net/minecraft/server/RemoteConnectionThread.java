package net.minecraft.server;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class RemoteConnectionThread implements Runnable
{
  private static final AtomicInteger h = new AtomicInteger(0);
  protected boolean running;
  protected IMinecraftServer server;
  protected final String c;
  protected Thread thread;
  protected int e = 5;
  protected List f = new java.util.ArrayList();
  protected List g = new java.util.ArrayList();
  
  protected RemoteConnectionThread(IMinecraftServer paramIMinecraftServer, String paramString) {
    this.server = paramIMinecraftServer;
    this.c = paramString;
    if (this.server.isDebugging()) {
      warning("Debugging is enabled, performance maybe reduced!");
    }
  }
  


  public synchronized void a()
  {
    this.thread = new Thread(this, this.c + " #" + h.incrementAndGet());
    this.thread.start();
    this.running = true;
  }
  


































  public boolean c()
  {
    return this.running;
  }
  
  protected void debug(String paramString) {
    this.server.i(paramString);
  }
  
  protected void info(String paramString) {
    this.server.info(paramString);
  }
  
  protected void warning(String paramString) {
    this.server.warning(paramString);
  }
  
  protected void error(String paramString) {
    this.server.h(paramString);
  }
  
  protected int d() {
    return this.server.C();
  }
  
  protected void a(DatagramSocket paramDatagramSocket) {
    debug("registerSocket: " + paramDatagramSocket);
    this.f.add(paramDatagramSocket);
  }
  








  protected boolean a(DatagramSocket paramDatagramSocket, boolean paramBoolean)
  {
    debug("closeSocket: " + paramDatagramSocket);
    if (null == paramDatagramSocket) {
      return false;
    }
    
    boolean bool = false;
    if (!paramDatagramSocket.isClosed()) {
      paramDatagramSocket.close();
      bool = true;
    }
    
    if (paramBoolean) {
      this.f.remove(paramDatagramSocket);
    }
    
    return bool;
  }
  
  protected boolean b(ServerSocket paramServerSocket) {
    return a(paramServerSocket, true);
  }
  
  protected boolean a(ServerSocket paramServerSocket, boolean paramBoolean) {
    debug("closeSocket: " + paramServerSocket);
    if (null == paramServerSocket) {
      return false;
    }
    
    boolean bool = false;
    try {
      if (!paramServerSocket.isClosed()) {
        paramServerSocket.close();
        bool = true;
      }
    } catch (java.io.IOException localIOException) {
      warning("IO: " + localIOException.getMessage());
    }
    
    if (paramBoolean) {
      this.g.remove(paramServerSocket);
    }
    
    return bool;
  }
  
  protected void e() {
    a(false);
  }
  
  protected void a(boolean paramBoolean) {
    int i = 0;
    for (Iterator localIterator = this.f.iterator(); localIterator.hasNext();) { localObject = (DatagramSocket)localIterator.next();
      if (a((DatagramSocket)localObject, false))
        i++;
    }
    Object localObject;
    this.f.clear();
    
    for (localIterator = this.g.iterator(); localIterator.hasNext();) { localObject = (ServerSocket)localIterator.next();
      if (a((ServerSocket)localObject, false)) {
        i++;
      }
    }
    this.g.clear();
    
    if ((paramBoolean) && (0 < i)) {
      warning("Force closed " + i + " sockets");
    }
  }
}
