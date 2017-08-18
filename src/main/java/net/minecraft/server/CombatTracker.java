package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;












public class CombatTracker
{
  private final List a = new ArrayList();
  private final EntityLiving b;
  private int c;
  private int d;
  private int e;
  private boolean f;
  private boolean g;
  private String h;
  
  public CombatTracker(EntityLiving paramEntityLiving) {
    this.b = paramEntityLiving;
  }
  
  public void a() {
    j();
    
    if (this.b.h_()) {
      Block localBlock = this.b.world.getType(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.boundingBox.b), MathHelper.floor(this.b.locZ));
      
      if (localBlock == Blocks.LADDER) {
        this.h = "ladder";
      } else if (localBlock == Blocks.VINE) {
        this.h = "vines";
      }
    } else if (this.b.M()) {
      this.h = "water";
    }
  }
  
  public void a(DamageSource paramDamageSource, float paramFloat1, float paramFloat2) {
    g();
    a();
    
    CombatEntry localCombatEntry = new CombatEntry(paramDamageSource, this.b.ticksLived, paramFloat1, paramFloat2, this.h, this.b.fallDistance);
    
    this.a.add(localCombatEntry);
    this.c = this.b.ticksLived;
    this.g = true;
    
    if ((localCombatEntry.f()) && (!this.f) && (this.b.isAlive())) {
      this.f = true;
      this.d = this.b.ticksLived;
      this.e = this.d;
      this.b.bu();
    }
  }
  
  public IChatBaseComponent b() {
    if (this.a.size() == 0) { return new ChatMessage("death.attack.generic", new Object[] { this.b.getScoreboardDisplayName() });
    }
    CombatEntry localCombatEntry1 = i();
    CombatEntry localCombatEntry2 = (CombatEntry)this.a.get(this.a.size() - 1);
    
    IChatBaseComponent localIChatBaseComponent1 = localCombatEntry2.h();
    Entity localEntity1 = localCombatEntry2.a().getEntity();
    Object localObject1;
    if ((localCombatEntry1 != null) && (localCombatEntry2.a() == DamageSource.FALL)) {
      IChatBaseComponent localIChatBaseComponent2 = localCombatEntry1.h();
      
      if ((localCombatEntry1.a() == DamageSource.FALL) || (localCombatEntry1.a() == DamageSource.OUT_OF_WORLD)) {
        localObject1 = new ChatMessage("death.fell.accident." + a(localCombatEntry1), new Object[] { this.b.getScoreboardDisplayName() }); } else { Entity localEntity2;
        if ((localIChatBaseComponent2 != null) && ((localIChatBaseComponent1 == null) || (!localIChatBaseComponent2.equals(localIChatBaseComponent1)))) {
          localEntity2 = localCombatEntry1.a().getEntity();
          Object localObject2 = (localEntity2 instanceof EntityLiving) ? ((EntityLiving)localEntity2).be() : null;
          
          if ((localObject2 != null) && (((ItemStack)localObject2).hasName())) {
            localObject1 = new ChatMessage("death.fell.assist.item", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent2, ((ItemStack)localObject2).E() });
          } else {
            localObject1 = new ChatMessage("death.fell.assist", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent2 });
          }
        } else if (localIChatBaseComponent1 != null) {
          localEntity2 = (localEntity1 instanceof EntityLiving) ? ((EntityLiving)localEntity1).be() : null;
          if ((localEntity2 != null) && (localEntity2.hasName())) {
            localObject1 = new ChatMessage("death.fell.finish.item", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent1, localEntity2.E() });
          } else {
            localObject1 = new ChatMessage("death.fell.finish", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent1 });
          }
        } else {
          localObject1 = new ChatMessage("death.fell.killer", new Object[] { this.b.getScoreboardDisplayName() });
        }
      }
    } else { localObject1 = localCombatEntry2.a().getLocalizedDeathMessage(this.b);
    }
    
    return (IChatBaseComponent)localObject1;
  }
  
  public EntityLiving c() {
    EntityLiving localEntityLiving = null;
    EntityHuman localEntityHuman = null;
    float f1 = 0.0F;
    float f2 = 0.0F;
    
    for (CombatEntry localCombatEntry : this.a) {
      if (((localCombatEntry.a().getEntity() instanceof EntityHuman)) && ((localEntityHuman == null) || (localCombatEntry.c() > f2))) {
        f2 = localCombatEntry.c();
        localEntityHuman = (EntityHuman)localCombatEntry.a().getEntity();
      }
      
      if (((localCombatEntry.a().getEntity() instanceof EntityLiving)) && ((localEntityLiving == null) || (localCombatEntry.c() > f1))) {
        f1 = localCombatEntry.c();
        localEntityLiving = (EntityLiving)localCombatEntry.a().getEntity();
      }
    }
    
    if ((localEntityHuman != null) && (f2 >= f1 / 3.0F)) {
      return localEntityHuman;
    }
    return localEntityLiving;
  }
  
  private CombatEntry i()
  {
    Object localObject1 = null;
    Object localObject2 = null;
    int i = 0;
    float f1 = 0.0F;
    
    for (int j = 0; j < this.a.size(); j++) {
      CombatEntry localCombatEntry = (CombatEntry)this.a.get(j);
      Object localObject3 = j > 0 ? (CombatEntry)this.a.get(j - 1) : null;
      
      if (((localCombatEntry.a() == DamageSource.FALL) || (localCombatEntry.a() == DamageSource.OUT_OF_WORLD)) && (localCombatEntry.i() > 0.0F) && ((localObject1 == null) || (localCombatEntry.i() > f1))) {
        if (j > 0) {
          localObject1 = localObject3;
        } else {
          localObject1 = localCombatEntry;
        }
        f1 = localCombatEntry.i();
      }
      
      if ((localCombatEntry.g() != null) && ((localObject2 == null) || (localCombatEntry.c() > i))) {
        localObject2 = localCombatEntry;
      }
    }
    
    if ((f1 > 5.0F) && (localObject1 != null))
      return (CombatEntry)localObject1;
    if ((i > 5) && (localObject2 != null)) {
      return (CombatEntry)localObject2;
    }
    return null;
  }
  
  private String a(CombatEntry paramCombatEntry)
  {
    return paramCombatEntry.g() == null ? "generic" : paramCombatEntry.g();
  }
  


















  private void j()
  {
    this.h = null;
  }
  
  public void g() {
    int i = this.f ? 300 : 100;
    
    if ((this.g) && ((!this.b.isAlive()) || (this.b.ticksLived - this.c > i))) {
      boolean bool = this.f;
      this.g = false;
      this.f = false;
      this.e = this.b.ticksLived;
      
      if (bool) {
        this.b.bv();
      }
      this.a.clear();
    }
  }
}
