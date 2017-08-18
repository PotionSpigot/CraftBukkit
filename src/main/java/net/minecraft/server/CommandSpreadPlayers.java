package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.com.google.common.collect.Sets;





public class CommandSpreadPlayers
  extends CommandAbstract
{
  public String getCommand()
  {
    return "spreadplayers";
  }
  
  public int a()
  {
    return 2;
  }
  
  public String c(ICommandListener paramICommandListener)
  {
    return "commands.spreadplayers.usage";
  }
  
  public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 6) throw new ExceptionUsage("commands.spreadplayers.usage", new Object[0]);
    int i = 0;
    double d1 = a(paramICommandListener, NaN.0D, paramArrayOfString[(i++)]);
    double d2 = a(paramICommandListener, NaN.0D, paramArrayOfString[(i++)]);
    double d3 = a(paramICommandListener, paramArrayOfString[(i++)], 0.0D);
    double d4 = a(paramICommandListener, paramArrayOfString[(i++)], d3 + 1.0D);
    boolean bool = c(paramICommandListener, paramArrayOfString[(i++)]);
    
    ArrayList localArrayList = Lists.newArrayList();
    
    while (i < paramArrayOfString.length) {
      String str = paramArrayOfString[(i++)];
      Object localObject;
      if (PlayerSelector.isPattern(str)) {
        localObject = PlayerSelector.getPlayers(paramICommandListener, str);
        
        if ((localObject != null) && (localObject.length != 0)) {
          Collections.addAll(localArrayList, (Object[])localObject);
        } else {
          throw new ExceptionPlayerNotFound();
        }
      } else {
        localObject = MinecraftServer.getServer().getPlayerList().getPlayer(str);
        
        if (localObject != null) {
          localArrayList.add(localObject);
        } else {
          throw new ExceptionPlayerNotFound();
        }
      }
    }
    
    if (localArrayList.isEmpty()) {
      throw new ExceptionPlayerNotFound();
    }
    
    paramICommandListener.sendMessage(new ChatMessage("commands.spreadplayers.spreading." + (bool ? "teams" : "players"), new Object[] { Integer.valueOf(localArrayList.size()), Double.valueOf(d4), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3) }));
    
    a(paramICommandListener, localArrayList, new Location2D(d1, d2), d3, d4, ((EntityLiving)localArrayList.get(0)).world, bool);
  }
  
  private void a(ICommandListener paramICommandListener, List paramList, Location2D paramLocation2D, double paramDouble1, double paramDouble2, World paramWorld, boolean paramBoolean) {
    Random localRandom = new Random();
    double d1 = paramLocation2D.a - paramDouble2;
    double d2 = paramLocation2D.b - paramDouble2;
    double d3 = paramLocation2D.a + paramDouble2;
    double d4 = paramLocation2D.b + paramDouble2;
    
    Location2D[] arrayOfLocation2D = a(localRandom, paramBoolean ? a(paramList) : paramList.size(), d1, d2, d3, d4);
    int i = a(paramLocation2D, paramDouble1, paramWorld, localRandom, d1, d2, d3, d4, arrayOfLocation2D, paramBoolean);
    double d5 = a(paramList, paramWorld, arrayOfLocation2D, paramBoolean);
    
    a(paramICommandListener, this, "commands.spreadplayers.success." + (paramBoolean ? "teams" : "players"), new Object[] { Integer.valueOf(arrayOfLocation2D.length), Double.valueOf(paramLocation2D.a), Double.valueOf(paramLocation2D.b) });
    if (arrayOfLocation2D.length > 1) paramICommandListener.sendMessage(new ChatMessage("commands.spreadplayers.info." + (paramBoolean ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d5) }), Integer.valueOf(i) }));
  }
  
  private int a(List paramList) {
    HashSet localHashSet = Sets.newHashSet();
    
    for (EntityLiving localEntityLiving : paramList) {
      if ((localEntityLiving instanceof EntityHuman)) {
        localHashSet.add(localEntityLiving.getScoreboardTeam());
      } else {
        localHashSet.add(null);
      }
    }
    
    return localHashSet.size();
  }
  
  private int a(Location2D paramLocation2D, double paramDouble1, World paramWorld, Random paramRandom, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, Location2D[] paramArrayOfLocation2D, boolean paramBoolean) {
    int i = 1;
    
    double d1 = 3.4028234663852886E38D;
    
    for (int j = 0; (j < 10000) && (i != 0); j++) {
      i = 0;
      d1 = 3.4028234663852886E38D;
      int n;
      Location2D localLocation2D2; for (int k = 0; k < paramArrayOfLocation2D.length; k++) {
        Location2D localLocation2D1 = paramArrayOfLocation2D[k];
        n = 0;
        localLocation2D2 = new Location2D();
        
        for (int i1 = 0; i1 < paramArrayOfLocation2D.length; i1++) {
          if (k != i1) {
            Location2D localLocation2D3 = paramArrayOfLocation2D[i1];
            
            double d2 = localLocation2D1.a(localLocation2D3);
            d1 = Math.min(d2, d1);
            if (d2 < paramDouble1) {
              n++;
              localLocation2D2.a += localLocation2D3.a - localLocation2D1.a;
              localLocation2D2.b += localLocation2D3.b - localLocation2D1.b;
            }
          }
        }
        if (n > 0) {
          localLocation2D2.a /= n;
          localLocation2D2.b /= n;
          double d3 = localLocation2D2.b();
          
          if (d3 > 0.0D) {
            localLocation2D2.a();
            
            localLocation2D1.b(localLocation2D2);
          } else {
            localLocation2D1.a(paramRandom, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
          }
          
          i = 1;
        }
        
        if (localLocation2D1.a(paramDouble2, paramDouble3, paramDouble4, paramDouble5)) {
          i = 1;
        }
      }
      
      if (i == 0) {
        for (localLocation2D2 : paramArrayOfLocation2D) {
          if (!localLocation2D2.b(paramWorld)) {
            localLocation2D2.a(paramRandom, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
            i = 1;
          }
        }
      }
    }
    
    if (j >= 10000) {
      throw new CommandException("commands.spreadplayers.failure." + (paramBoolean ? "teams" : "players"), new Object[] { Integer.valueOf(paramArrayOfLocation2D.length), Double.valueOf(paramLocation2D.a), Double.valueOf(paramLocation2D.b), String.format("%.2f", new Object[] { Double.valueOf(d1) }) });
    }
    
    return j;
  }
  
  private double a(List paramList, World paramWorld, Location2D[] paramArrayOfLocation2D, boolean paramBoolean) {
    double d1 = 0.0D;
    int i = 0;
    HashMap localHashMap = Maps.newHashMap();
    
    for (int j = 0; j < paramList.size(); j++) {
      EntityLiving localEntityLiving = (EntityLiving)paramList.get(j);
      
      Location2D localLocation2D;
      if (paramBoolean) {
        Object localObject = (localEntityLiving instanceof EntityHuman) ? localEntityLiving.getScoreboardTeam() : null;
        
        if (!localHashMap.containsKey(localObject)) {
          localHashMap.put(localObject, paramArrayOfLocation2D[(i++)]);
        }
        
        localLocation2D = (Location2D)localHashMap.get(localObject);
      } else {
        localLocation2D = paramArrayOfLocation2D[(i++)];
      }
      
      localEntityLiving.enderTeleportTo(MathHelper.floor(localLocation2D.a) + 0.5F, localLocation2D.a(paramWorld), MathHelper.floor(localLocation2D.b) + 0.5D);
      
      double d2 = Double.MAX_VALUE;
      for (int k = 0; k < paramArrayOfLocation2D.length; k++)
        if (localLocation2D != paramArrayOfLocation2D[k])
        {
          double d3 = localLocation2D.a(paramArrayOfLocation2D[k]);
          d2 = Math.min(d3, d2);
        }
      d1 += d2;
    }
    
    d1 /= paramList.size();
    return d1;
  }
  
  private Location2D[] a(Random paramRandom, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    Location2D[] arrayOfLocation2D = new Location2D[paramInt];
    
    for (int i = 0; i < arrayOfLocation2D.length; i++) {
      Location2D localLocation2D = new Location2D();
      
      localLocation2D.a(paramRandom, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
      
      arrayOfLocation2D[i] = localLocation2D;
    }
    
    return arrayOfLocation2D;
  }
}
