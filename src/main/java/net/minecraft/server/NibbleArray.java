package net.minecraft.server;

public class NibbleArray
{
  public final byte[] a;
  private final int b;
  private final int c;
  
  public NibbleArray(int i, int j) {
    this.a = new byte[i >> 1];
    this.b = j;
    this.c = (j + 4);
  }
  
  public NibbleArray(byte[] abyte, int i) {
    this.a = abyte;
    this.b = i;
    this.c = (i + 4);
  }
  
  public int a(int i, int j, int k) {
    int l = j << this.c | k << this.b | i;
    int i1 = l >> 1;
    int j1 = l & 0x1;
    
    return j1 == 0 ? this.a[i1] & 0xF : this.a[i1] >> 4 & 0xF;
  }
  
  public void a(int i, int j, int k, int l) {
    int i1 = j << this.c | k << this.b | i;
    int j1 = i1 >> 1;
    int k1 = i1 & 0x1;
    
    if (k1 == 0) {
      this.a[j1] = ((byte)(this.a[j1] & 0xF0 | l & 0xF));
    } else {
      this.a[j1] = ((byte)(this.a[j1] & 0xF | (l & 0xF) << 4));
    }
  }
}
