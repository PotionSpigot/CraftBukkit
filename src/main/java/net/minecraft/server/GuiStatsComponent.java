package net.minecraft.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;

public class GuiStatsComponent
  extends JComponent
{
  private static final DecimalFormat a = new DecimalFormat("########0.000");
  

  private int[] b = new int['Ā'];
  private int c;
  private String[] d = new String[11];
  private final MinecraftServer e;
  
  public GuiStatsComponent(MinecraftServer paramMinecraftServer) {
    this.e = paramMinecraftServer;
    setPreferredSize(new Dimension(456, 246));
    setMinimumSize(new Dimension(456, 246));
    setMaximumSize(new Dimension(456, 246));
    new Timer(500, new GuiStatsListener(this)).start();
    




    setBackground(Color.BLACK);
  }
  
  private void a() {
    long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.gc();
    this.d[0] = ("Memory use: " + l / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)");
    this.d[1] = ("Avg tick: " + a.format(a(this.e.g) * 1.0E-6D) + " ms");
    repaint();
  }
  
  private double a(long[] paramArrayOfLong) {
    long l = 0L;
    for (int i = 0; i < paramArrayOfLong.length; i++) {
      l += paramArrayOfLong[i];
    }
    return l / paramArrayOfLong.length;
  }
  
  public void paint(Graphics paramGraphics)
  {
    paramGraphics.setColor(new Color(16777215));
    paramGraphics.fillRect(0, 0, 456, 246);
    
    for (int i = 0; i < 256; i++) {
      int j = this.b[(i + this.c & 0xFF)];
      paramGraphics.setColor(new Color(j + 28 << 16));
      paramGraphics.fillRect(i, 100 - j, 1, j);
    }
    paramGraphics.setColor(Color.BLACK);
    for (i = 0; i < this.d.length; i++) {
      String str = this.d[i];
      if (str != null) paramGraphics.drawString(str, 32, 116 + i * 16);
    }
  }
}
