package net.minecraft.server;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.minecraft.util.com.mojang.util.QueueLogAppender;





































































































class ServerGuiThreadRunnable
  implements Runnable
{
  ServerGuiThreadRunnable(ServerGUI paramServerGUI, JTextArea paramJTextArea, JScrollPane paramJScrollPane) {}
  
  public void run()
  {
    String str;
    while ((str = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
      this.c.a(this.a, this.b, str);
    }
  }
}
