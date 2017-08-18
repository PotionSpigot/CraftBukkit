package net.minecraft.server;

import java.util.List;
import java.util.Vector;
import javax.swing.JList;

public class PlayerListBox
  extends JList implements IUpdatePlayerListBox
{
  private MinecraftServer a;
  private int b;
  
  public PlayerListBox(MinecraftServer paramMinecraftServer)
  {
    this.a = paramMinecraftServer;
    paramMinecraftServer.a(this);
  }
  
  public void a()
  {
    if (this.b++ % 20 == 0) {
      Vector localVector = new Vector();
      for (int i = 0; i < this.a.getPlayerList().players.size(); i++) {
        localVector.add(((EntityPlayer)this.a.getPlayerList().players.get(i)).getName());
      }
      setListData(localVector);
    }
  }
}
