package net.minecraft.server;

import net.minecraft.util.io.netty.util.concurrent.Future;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;

































































































class PlayerConnectionFuture
  implements GenericFutureListener
{
  PlayerConnectionFuture(PlayerConnection paramPlayerConnection, ChatComponentText paramChatComponentText) {}
  
  public void operationComplete(Future paramFuture)
  {
    this.b.networkManager.close(this.a);
  }
}
