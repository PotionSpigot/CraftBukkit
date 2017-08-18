package net.minecraft.server;

import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

class ThreadCommandReader extends Thread
{
  final DedicatedServer server;
  
  ThreadCommandReader(DedicatedServer dedicatedserver, String s)
  {
    super(s);
    this.server = dedicatedserver;
  }
  
  public void run()
  {
    if (!Main.useConsole) {
      return;
    }
    

    ConsoleReader bufferedreader = this.server.reader;
    

    try
    {
      while ((!this.server.isStopped()) && (this.server.isRunning())) { String s;
        String s; if (Main.useJline) {
          s = bufferedreader.readLine(">", null);
        } else {
          s = bufferedreader.readLine();
        }
        if (s != null) {
          this.server.issueCommand(s, this.server);
        }
      }
    }
    catch (IOException ioexception) {
      DedicatedServer.aF().error("Exception handling console input", ioexception);
    }
  }
}
