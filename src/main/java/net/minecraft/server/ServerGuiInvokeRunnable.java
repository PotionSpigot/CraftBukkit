package net.minecraft.server;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;























































































































class ServerGuiInvokeRunnable
  implements Runnable
{
  ServerGuiInvokeRunnable(ServerGUI paramServerGUI, JTextArea paramJTextArea, JScrollPane paramJScrollPane, String paramString) {}
  
  public void run()
  {
    this.d.a(this.a, this.b, this.c);
  }
}