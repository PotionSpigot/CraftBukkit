package net.minecraft.server;

import com.google.common.base.Joiner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.command.VanillaCommandWrapper;

public abstract class CommandBlockListenerAbstract implements ICommandListener
{
  private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
  private int b;
  private boolean c = true;
  private IChatBaseComponent d = null;
  public String e = "";
  private String f = "@";
  
  protected org.bukkit.command.CommandSender sender;
  
  public int g()
  {
    return this.b;
  }
  
  public IChatBaseComponent h() {
    return this.d;
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    nbttagcompound.setString("Command", this.e);
    nbttagcompound.setInt("SuccessCount", this.b);
    nbttagcompound.setString("CustomName", this.f);
    if (this.d != null) {
      nbttagcompound.setString("LastOutput", ChatSerializer.a(this.d));
    }
    
    nbttagcompound.setBoolean("TrackOutput", this.c);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    this.e = nbttagcompound.getString("Command");
    this.b = nbttagcompound.getInt("SuccessCount");
    if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
      this.f = nbttagcompound.getString("CustomName");
    }
    
    if (nbttagcompound.hasKeyOfType("LastOutput", 8)) {
      this.d = ChatSerializer.a(nbttagcompound.getString("LastOutput"));
    }
    
    if (nbttagcompound.hasKeyOfType("TrackOutput", 1)) {
      this.c = nbttagcompound.getBoolean("TrackOutput");
    }
  }
  
  public boolean a(int i, String s) {
    return i <= 2;
  }
  
  public void setCommand(String s) {
    this.e = s;
  }
  
  public String getCommand() {
    return this.e;
  }
  
  public void a(World world) {
    if (world.isStatic) {
      this.b = 0;
    }
    
    MinecraftServer minecraftserver = MinecraftServer.getServer();
    
    if ((minecraftserver != null) && (minecraftserver.getEnableCommandBlock()))
    {
      SimpleCommandMap commandMap = minecraftserver.server.getCommandMap();
      Joiner joiner = Joiner.on(" ");
      String command = this.e;
      if (this.e.startsWith("/")) {
        command = this.e.substring(1);
      }
      String[] args = command.split(" ");
      ArrayList<String[]> commands = new ArrayList();
      

      if ((args[0].equalsIgnoreCase("stop")) || (args[0].equalsIgnoreCase("kick")) || (args[0].equalsIgnoreCase("op")) || 
        (args[0].equalsIgnoreCase("deop")) || (args[0].equalsIgnoreCase("ban")) || (args[0].equalsIgnoreCase("ban-ip")) || 
        (args[0].equalsIgnoreCase("pardon")) || (args[0].equalsIgnoreCase("pardon-ip")) || (args[0].equalsIgnoreCase("reload"))) {
        this.b = 0;
        return;
      }
      

      if (getWorld().players.isEmpty()) {
        this.b = 0;
        return;
      }
      

      if (minecraftserver.server.getCommandBlockOverride(args[0])) {
        Command commandBlockCommand = commandMap.getCommand("minecraft:" + args[0]);
        if ((commandBlockCommand instanceof VanillaCommandWrapper)) {
          this.b = ((VanillaCommandWrapper)commandBlockCommand).dispatchVanillaCommandBlock(this, this.e);
          return;
        }
      }
      

      Command commandBlockCommand = commandMap.getCommand(args[0]);
      if ((commandBlockCommand instanceof VanillaCommandWrapper)) {
        this.b = ((VanillaCommandWrapper)commandBlockCommand).dispatchVanillaCommandBlock(this, this.e);
        return;
      }
      


      if (commandMap.getCommand(args[0]) == null) {
        this.b = 0;
        return;
      }
      

      if (args[0].equalsIgnoreCase("testfor")) {
        if (args.length < 2) {
          this.b = 0;
          return;
        }
        
        EntityPlayer[] players = PlayerSelector.getPlayers(this, args[1]);
        
        if ((players != null) && (players.length > 0)) {
          this.b = players.length;
          return;
        }
        EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(args[1]);
        if (player == null) {
          this.b = 0;
          return;
        }
        this.b = 1;
        return;
      }
      


      commands.add(args);
      

      ArrayList<String[]> newCommands = new ArrayList();
      for (int i = 0; i < args.length; i++) {
        if (PlayerSelector.isPattern(args[i])) {
          for (int j = 0; j < commands.size(); j++) {
            newCommands.addAll(buildCommands((String[])commands.get(j), i));
          }
          ArrayList<String[]> temp = commands;
          commands = newCommands;
          newCommands = temp;
          newCommands.clear();
        }
      }
      
      int completed = 0;
      

      for (int i = 0; i < commands.size(); i++) {
        try {
          if (commandMap.dispatch(this.sender, joiner.join(java.util.Arrays.asList((Object[])commands.get(i))))) {
            completed++;
          }
        } catch (Throwable exception) {
          if ((this instanceof TileEntityCommandListener)) {
            TileEntityCommandListener listener = (TileEntityCommandListener)this;
            MinecraftServer.getLogger().log(Level.WARN, String.format("CommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf(listener.getChunkCoordinates().x), Integer.valueOf(listener.getChunkCoordinates().y), Integer.valueOf(listener.getChunkCoordinates().z) }), exception);
          } else if ((this instanceof EntityMinecartCommandBlockListener)) {
            EntityMinecartCommandBlockListener listener = (EntityMinecartCommandBlockListener)this;
            MinecraftServer.getLogger().log(Level.WARN, String.format("MinecartCommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf(listener.getChunkCoordinates().x), Integer.valueOf(listener.getChunkCoordinates().y), Integer.valueOf(listener.getChunkCoordinates().z) }), exception);
          } else {
            MinecraftServer.getLogger().log(Level.WARN, String.format("Unknown CommandBlock failed to handle command", new Object[0]), exception);
          }
        }
      }
      
      this.b = completed;
    }
    else {
      this.b = 0;
    }
  }
  
  private ArrayList<String[]> buildCommands(String[] args, int pos)
  {
    ArrayList<String[]> commands = new ArrayList();
    EntityPlayer[] players = PlayerSelector.getPlayers(this, args[pos]);
    if (players != null) {
      for (EntityPlayer player : players) {
        if (player.world == getWorld())
        {

          String[] command = (String[])args.clone();
          command[pos] = player.getName();
          commands.add(command);
        }
      }
    }
    return commands;
  }
  
  public String getName()
  {
    return this.f;
  }
  
  public IChatBaseComponent getScoreboardDisplayName() {
    return new ChatComponentText(getName());
  }
  
  public void setName(String s) {
    this.f = s;
  }
  
  public void sendMessage(IChatBaseComponent ichatbasecomponent) {
    if ((this.c) && (getWorld() != null) && (!getWorld().isStatic)) {
      this.d = new ChatComponentText("[" + a.format(new java.util.Date()) + "] ").addSibling(ichatbasecomponent);
      e();
    }
  }
  
  public abstract void e();
  
  public void b(IChatBaseComponent ichatbasecomponent) {
    this.d = ichatbasecomponent;
  }
}
