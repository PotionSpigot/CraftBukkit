package net.minecraft.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class PacketPlayInChat extends Packet
{
  private String message;
  
  public PacketPlayInChat() {}
  
  public PacketPlayInChat(String s) {
    if (s.length() > 100) {
      s = s.substring(0, 100);
    }
    
    this.message = s;
  }
  
  public void a(PacketDataSerializer packetdataserializer) throws java.io.IOException {
    this.message = packetdataserializer.c(100);
  }
  
  public void b(PacketDataSerializer packetdataserializer) throws java.io.IOException {
    packetdataserializer.a(this.message);
  }
  
  public void a(PacketPlayInListener packetplayinlistener) {
    packetplayinlistener.a(this);
  }
  
  public String b() {
    return String.format("message='%s'", new Object[] { this.message });
  }
  
  public String c() {
    return this.message;
  }
  

  public boolean a()
  {
    return !this.message.startsWith("/");
  }
  


  private static final java.util.concurrent.ExecutorService executors = java.util.concurrent.Executors.newCachedThreadPool(new ThreadFactoryBuilder()
    .setDaemon(true).setNameFormat("Async Chat Thread - #%d").build());
  
  public void handle(final PacketListener packetlistener) {
    if (a())
    {
      executors.submit(new Runnable()
      {

        public void run()
        {

          PacketPlayInChat.this.a((PacketPlayInListener)packetlistener);
        }
      });
      return;
    }
    
    a((PacketPlayInListener)packetlistener);
  }
}
