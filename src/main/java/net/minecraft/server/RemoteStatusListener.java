package net.minecraft.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RemoteStatusListener extends RemoteConnectionThread
{
  private long clearedTime;
  private int bindPort;
  private int serverPort;
  private int maxPlayers;
  private String localAddress;
  private String worldName;
  private DatagramSocket socket;
  private byte[] o = new byte['ִ'];
  
  private DatagramPacket p;
  private Map q;
  private String hostname;
  private String motd;
  private Map challenges;
  private long u;
  private RemoteStatusReply cachedReply;
  private long cacheTime;
  
  public RemoteStatusListener(IMinecraftServer paramIMinecraftServer)
  {
    super(paramIMinecraftServer, "Query Listener");
    
    this.bindPort = paramIMinecraftServer.a("query.port", 0);
    this.motd = paramIMinecraftServer.y();
    this.serverPort = paramIMinecraftServer.z();
    this.localAddress = paramIMinecraftServer.A();
    this.maxPlayers = paramIMinecraftServer.D();
    this.worldName = paramIMinecraftServer.O();
    

    this.cacheTime = 0L;
    
    this.hostname = "0.0.0.0";
    

    if ((0 == this.motd.length()) || (this.hostname.equals(this.motd)))
    {
      this.motd = "0.0.0.0";
      try {
        InetAddress localInetAddress = InetAddress.getLocalHost();
        this.hostname = localInetAddress.getHostAddress();
      } catch (UnknownHostException localUnknownHostException) {
        warning("Unable to determine local host IP, please set server-ip in '" + paramIMinecraftServer.b() + "' : " + localUnknownHostException.getMessage());
      }
    } else {
      this.hostname = this.motd;
    }
    

    if (0 == this.bindPort)
    {
      this.bindPort = this.serverPort;
      info("Setting default query port to " + this.bindPort);
      paramIMinecraftServer.a("query.port", Integer.valueOf(this.bindPort));
      paramIMinecraftServer.a("debug", Boolean.valueOf(false));
      paramIMinecraftServer.a();
    }
    
    this.q = new HashMap();
    this.cachedReply = new RemoteStatusReply(1460);
    this.challenges = new HashMap();
    this.u = new Date().getTime();
  }
  
  private void send(byte[] paramArrayOfByte, DatagramPacket paramDatagramPacket) {
    this.socket.send(new DatagramPacket(paramArrayOfByte, paramArrayOfByte.length, paramDatagramPacket.getSocketAddress()));
  }
  
  private boolean parsePacket(DatagramPacket paramDatagramPacket) {
    byte[] arrayOfByte = paramDatagramPacket.getData();
    int i = paramDatagramPacket.getLength();
    SocketAddress localSocketAddress = paramDatagramPacket.getSocketAddress();
    debug("Packet len " + i + " [" + localSocketAddress + "]");
    if ((3 > i) || (-2 != arrayOfByte[0]) || (-3 != arrayOfByte[1]))
    {
      debug("Invalid packet [" + localSocketAddress + "]");
      return false;
    }
    

    debug("Packet '" + StatusChallengeUtils.a(arrayOfByte[2]) + "' [" + localSocketAddress + "]");
    switch (arrayOfByte[2])
    {
    case 9: 
      createChallenge(paramDatagramPacket);
      debug("Challenge [" + localSocketAddress + "]");
      return true;
    

    case 0: 
      if (!hasChallenged(paramDatagramPacket).booleanValue()) {
        debug("Invalid challenge [" + localSocketAddress + "]");
        return false;
      }
      
      if (15 == i)
      {
        send(getFullReply(paramDatagramPacket), paramDatagramPacket);
        debug("Rules [" + localSocketAddress + "]");
      }
      else {
        RemoteStatusReply localRemoteStatusReply = new RemoteStatusReply(1460);
        localRemoteStatusReply.write(0);
        localRemoteStatusReply.write(getIdentityToken(paramDatagramPacket.getSocketAddress()));
        localRemoteStatusReply.write(this.localAddress);
        localRemoteStatusReply.write("SMP");
        localRemoteStatusReply.write(this.worldName);
        localRemoteStatusReply.write(Integer.toString(d()));
        localRemoteStatusReply.write(Integer.toString(this.maxPlayers));
        localRemoteStatusReply.write((short)this.serverPort);
        localRemoteStatusReply.write(this.hostname);
        
        send(localRemoteStatusReply.getBytes(), paramDatagramPacket);
        debug("Status [" + localSocketAddress + "]");
      }
      break;
    }
    return true;
  }
  
  private byte[] getFullReply(DatagramPacket paramDatagramPacket) {
    long l = MinecraftServer.ar();
    Object localObject2; if (l < this.cacheTime + 5000L)
    {
      localObject1 = this.cachedReply.getBytes();
      localObject2 = getIdentityToken(paramDatagramPacket.getSocketAddress());
      localObject1[1] = localObject2[0];
      localObject1[2] = localObject2[1];
      localObject1[3] = localObject2[2];
      localObject1[4] = localObject2[3];
      
      return (byte[])localObject1;
    }
    
    this.cacheTime = l;
    
    this.cachedReply.reset();
    this.cachedReply.write(0);
    this.cachedReply.write(getIdentityToken(paramDatagramPacket.getSocketAddress()));
    this.cachedReply.write("splitnum");
    this.cachedReply.write(128);
    this.cachedReply.write(0);
    

    this.cachedReply.write("hostname");
    this.cachedReply.write(this.localAddress);
    this.cachedReply.write("gametype");
    this.cachedReply.write("SMP");
    this.cachedReply.write("game_id");
    this.cachedReply.write("MINECRAFT");
    this.cachedReply.write("version");
    this.cachedReply.write(this.server.getVersion());
    this.cachedReply.write("plugins");
    this.cachedReply.write(this.server.getPlugins());
    this.cachedReply.write("map");
    this.cachedReply.write(this.worldName);
    this.cachedReply.write("numplayers");
    this.cachedReply.write("" + d());
    this.cachedReply.write("maxplayers");
    this.cachedReply.write("" + this.maxPlayers);
    this.cachedReply.write("hostport");
    this.cachedReply.write("" + this.serverPort);
    this.cachedReply.write("hostip");
    this.cachedReply.write(this.hostname);
    this.cachedReply.write(0);
    this.cachedReply.write(1);
    


    this.cachedReply.write("player_");
    this.cachedReply.write(0);
    
    Object localObject1 = this.server.getPlayers();
    for (String str : localObject1) {
      this.cachedReply.write(str);
    }
    this.cachedReply.write(0);
    
    return this.cachedReply.getBytes();
  }
  
  private byte[] getIdentityToken(SocketAddress paramSocketAddress) {
    return ((RemoteStatusChallenge)this.challenges.get(paramSocketAddress)).getIdentityToken();
  }
  
  private Boolean hasChallenged(DatagramPacket paramDatagramPacket) {
    SocketAddress localSocketAddress = paramDatagramPacket.getSocketAddress();
    if (!this.challenges.containsKey(localSocketAddress))
    {
      return Boolean.valueOf(false);
    }
    
    byte[] arrayOfByte = paramDatagramPacket.getData();
    if (((RemoteStatusChallenge)this.challenges.get(localSocketAddress)).getToken() != StatusChallengeUtils.c(arrayOfByte, 7, paramDatagramPacket.getLength()))
    {
      return Boolean.valueOf(false);
    }
    

    return Boolean.valueOf(true);
  }
  
  private void createChallenge(DatagramPacket paramDatagramPacket) {
    RemoteStatusChallenge localRemoteStatusChallenge = new RemoteStatusChallenge(this, paramDatagramPacket);
    this.challenges.put(paramDatagramPacket.getSocketAddress(), localRemoteStatusChallenge);
    
    send(localRemoteStatusChallenge.getChallengeResponse(), paramDatagramPacket);
  }
  
  private void cleanChallenges() {
    if (!this.running) {
      return;
    }
    
    long l = MinecraftServer.ar();
    if (l < this.clearedTime + 30000L) {
      return;
    }
    this.clearedTime = l;
    
    Iterator localIterator = this.challenges.entrySet().iterator();
    while (localIterator.hasNext()) {
      Entry localEntry = (Entry)localIterator.next();
      if (((RemoteStatusChallenge)localEntry.getValue()).isExpired(l).booleanValue()) {
        localIterator.remove();
      }
    }
  }
  
  public void run()
  {
    info("Query running on " + this.motd + ":" + this.bindPort);
    this.clearedTime = MinecraftServer.ar();
    this.p = new DatagramPacket(this.o, this.o.length);
    try
    {
      while (this.running) {
        try {
          this.socket.receive(this.p);
          

          cleanChallenges();
          

          parsePacket(this.p);
        }
        catch (java.net.SocketTimeoutException localSocketTimeoutException) {
          cleanChallenges();

        }
        catch (PortUnreachableException localPortUnreachableException) {}catch (java.io.IOException localIOException)
        {
          a(localIOException);
        }
      }
    } finally {
      e();
    }
  }
  
  public void a()
  {
    if (this.running) {
      return;
    }
    
    if ((0 >= this.bindPort) || (65535 < this.bindPort)) {
      warning("Invalid query port " + this.bindPort + " found in '" + this.server.b() + "' (queries disabled)");
      return;
    }
    
    if (g()) {
      super.a();
    }
  }
  
  private void a(Exception paramException) {
    if (!this.running) {
      return;
    }
    

    warning("Unexpected exception, buggy JRE? (" + paramException.toString() + ")");
    

    if (!g()) {
      error("Failed to recover from buggy JRE, shutting down!");
      this.running = false;
    }
  }
  
  private boolean g() {
    try {
      this.socket = new DatagramSocket(this.bindPort, InetAddress.getByName(this.motd));
      a(this.socket);
      this.socket.setSoTimeout(500);
      return true;
    } catch (SocketException localSocketException) {
      warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Socket): " + localSocketException.getMessage());
    } catch (UnknownHostException localUnknownHostException) {
      warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Unknown Host): " + localUnknownHostException.getMessage());
    } catch (Exception localException) {
      warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (E): " + localException.getMessage());
    }
    
    return false;
  }
}
