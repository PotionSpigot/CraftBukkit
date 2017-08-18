package net.minecraft.server;

import java.util.Iterator;

public class CommandDispatcher extends CommandHandler implements ICommandDispatcher
{
  public CommandDispatcher() {
    a(new CommandTime());
    a(new CommandGamemode());
    a(new CommandDifficulty());
    a(new CommandGamemodeDefault());
    a(new CommandKill());
    a(new CommandToggleDownfall());
    a(new CommandWeather());
    a(new CommandXp());
    a(new CommandTp());
    a(new CommandGive());
    a(new CommandEffect());
    a(new CommandEnchant());
    a(new CommandMe());
    a(new CommandSeed());
    a(new CommandHelp());
    a(new CommandDebug());
    a(new CommandTell());
    a(new CommandSay());
    a(new CommandSpawnpoint());
    a(new CommandSetWorldSpawn());
    a(new CommandGamerule());
    a(new CommandClear());
    a(new CommandTestFor());
    a(new CommandSpreadPlayers());
    a(new CommandPlaySound());
    a(new CommandScoreboard());
    a(new CommandAchievement());
    a(new CommandSummon());
    a(new CommandSetBlock());
    a(new CommandTestForBlock());
    a(new CommandTellRaw());
    if (MinecraftServer.getServer().X()) {
      a(new CommandOp());
      a(new CommandDeop());
      a(new CommandStop());
      a(new CommandSaveAll());
      a(new CommandSaveOff());
      a(new CommandSaveOn());
      a(new CommandBanIp());
      a(new CommandPardonIP());
      a(new CommandBan());
      a(new CommandBanList());
      a(new CommandPardon());
      a(new CommandKick());
      a(new CommandList());
      a(new CommandWhitelist());
      a(new CommandIdleTimeout());
      a(new CommandNetstat());
    } else {
      a(new CommandPublish());
    }
    
    CommandAbstract.a(this);
  }
  
  public void a(ICommandListener icommandlistener, ICommand icommand, int i, String s, Object... aobject) {
    boolean flag = true;
    
    if (((icommandlistener instanceof CommandBlockListenerAbstract)) && (!MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("commandBlockOutput"))) {
      flag = false;
    }
    
    ChatMessage chatmessage = new ChatMessage("chat.type.admin", new Object[] { icommandlistener.getName(), new ChatMessage(s, aobject) });
    
    chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY);
    chatmessage.getChatModifier().setItalic(Boolean.valueOf(true));
    if (flag) {
      Iterator iterator = MinecraftServer.getServer().getPlayerList().players.iterator();
      
      while (iterator.hasNext()) {
        EntityHuman entityhuman = (EntityHuman)iterator.next();
        
        if ((entityhuman != icommandlistener) && (MinecraftServer.getServer().getPlayerList().isOp(entityhuman.getProfile())) && (icommand.canUse(entityhuman)) && ((!(icommandlistener instanceof RemoteControlCommandListener)) || (MinecraftServer.getServer().m()))) {
          entityhuman.sendMessage(chatmessage);
        }
      }
    }
    
    if ((icommandlistener != MinecraftServer.getServer()) && (!org.spigotmc.SpigotConfig.silentCommandBlocks)) {
      MinecraftServer.getServer().sendMessage(chatmessage);
    }
    
    if ((i & 0x1) != 1) {
      icommandlistener.sendMessage(new ChatMessage(s, aobject));
    }
  }
}
