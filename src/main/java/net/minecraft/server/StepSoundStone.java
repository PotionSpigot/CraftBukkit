package net.minecraft.server;

















































































































































final class StepSoundStone
  extends StepSound
{
  StepSoundStone(String paramString, float paramFloat1, float paramFloat2)
  {
    super(paramString, paramFloat1, paramFloat2);
  }
  
  public String getBreakSound() { return "dig.glass"; }
  

  public String getPlaceSound()
  {
    return "step.stone";
  }
}
