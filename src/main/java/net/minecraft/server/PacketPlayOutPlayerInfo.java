package net.minecraft.server;

import java.io.IOException;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;














public class PacketPlayOutPlayerInfo
  extends Packet
{
  private static final int ADD_PLAYER = 0;
  private static final int UPDATE_GAMEMODE = 1;
  private static final int UPDATE_LATENCY = 2;
  private static final int UPDATE_DISPLAY_NAME = 3;
  private static final int REMOVE_PLAYER = 4;
  private int action;
  private GameProfile player;
  private int gamemode;
  private int ping;
  private String username;
  
  public static PacketPlayOutPlayerInfo addPlayer(EntityPlayer player)
  {
    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
    packet.action = 0;
    packet.username = player.listName;
    packet.player = player.getProfile();
    packet.ping = player.ping;
    packet.gamemode = player.playerInteractManager.getGameMode().getId();
    return packet;
  }
  
  public static PacketPlayOutPlayerInfo updatePing(EntityPlayer player) {
    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
    packet.action = 2;
    packet.username = player.listName;
    packet.player = player.getProfile();
    packet.ping = player.ping;
    return packet;
  }
  
  public static PacketPlayOutPlayerInfo updateGamemode(EntityPlayer player) {
    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
    packet.action = 1;
    packet.username = player.listName;
    packet.player = player.getProfile();
    packet.gamemode = player.playerInteractManager.getGameMode().getId();
    return packet;
  }
  
  public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {
    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
    packet.action = 3;
    packet.username = player.listName;
    packet.player = player.getProfile();
    return packet;
  }
  
  public static PacketPlayOutPlayerInfo removePlayer(EntityPlayer player) {
    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
    packet.action = 4;
    packet.username = player.listName;
    packet.player = player.getProfile();
    return packet;
  }
  
  public void a(PacketDataSerializer packetdataserializer)
    throws IOException
  {}
  
  public void b(PacketDataSerializer packetdataserializer) throws IOException
  {
    String username = this.username;
    if ((packetdataserializer.version >= 47) && (this.action == 0) && (username != null) && (username.equals(this.player.getName()))) {
      username = null;
    }
    
    if (packetdataserializer.version >= 20)
    {
      packetdataserializer.b(this.action);
      packetdataserializer.b(1);
      packetdataserializer.writeUUID(this.player.getId());
      switch (this.action)
      {
      case 0: 
        packetdataserializer.a(this.player.getName());
        PropertyMap properties = this.player.getProperties();
        packetdataserializer.b(properties.size());
        for (Property property : properties.values())
        {
          packetdataserializer.a(property.getName());
          packetdataserializer.a(property.getValue());
          packetdataserializer.writeBoolean(property.hasSignature());
          if (property.hasSignature())
          {
            packetdataserializer.a(property.getSignature());
          }
        }
        packetdataserializer.b(this.gamemode);
        packetdataserializer.b(this.ping);
        packetdataserializer.writeBoolean(username != null);
        if (username != null)
        {
          packetdataserializer.a(ChatSerializer.a(org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage.fromString(username)[0]));
        }
        break;
      case 1: 
        packetdataserializer.b(this.gamemode);
        break;
      case 2: 
        packetdataserializer.b(this.ping);
        break;
      case 3: 
        packetdataserializer.writeBoolean(username != null);
        if (username != null)
        {
          packetdataserializer.a(ChatSerializer.a(org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage.fromString(username)[0]));
        }
        break;
      }
      
    }
    else
    {
      packetdataserializer.a(username);
      packetdataserializer.writeBoolean(this.action != 4);
      packetdataserializer.writeShort(this.ping);
    }
  }
  
  public void a(PacketPlayOutListener packetplayoutlistener) {
    packetplayoutlistener.a(this);
  }
  
  public void handle(PacketListener packetlistener) {
    a((PacketPlayOutListener)packetlistener);
  }
}
