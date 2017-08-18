package net.minecraft.server;

public class GenLayerZoomFuzzy extends GenLayerZoom {
  public GenLayerZoomFuzzy(long paramLong, GenLayer paramGenLayer) {
    super(paramLong, paramGenLayer);
  }
  
  protected int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return a(new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
  }
}
