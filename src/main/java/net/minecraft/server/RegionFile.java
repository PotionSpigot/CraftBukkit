package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile
{
  private static final byte[] a = new byte['က'];
  private final File b;
  private RandomAccessFile c;
  private final int[] d = new int['Ѐ'];
  private final int[] e = new int['Ѐ'];
  private ArrayList f;
  private int g;
  private long h;
  
  public RegionFile(File file1) {
    this.b = file1;
    this.g = 0;
    try
    {
      if (file1.exists()) {
        this.h = file1.lastModified();
      }
      
      this.c = new RandomAccessFile(file1, "rw");
      

      if (this.c.length() < 4096L) {
        for (int i = 0; i < 1024; i++) {
          this.c.writeInt(0);
        }
        
        for (i = 0; i < 1024; i++) {
          this.c.writeInt(0);
        }
        
        this.g += 8192;
      }
      
      if ((this.c.length() & 0xFFF) != 0L) {
        for (int i = 0; i < (this.c.length() & 0xFFF); i++) {
          this.c.write(0);
        }
      }
      
      int i = (int)this.c.length() / 4096;
      this.f = new ArrayList(i);
      


      for (int j = 0; j < i; j++) {
        this.f.add(Boolean.valueOf(true));
      }
      
      this.f.set(0, Boolean.valueOf(false));
      this.f.set(1, Boolean.valueOf(false));
      this.c.seek(0L);
      


      for (j = 0; j < 1024; j++) {
        int k = this.c.readInt();
        this.d[j] = k;
        if ((k != 0) && ((k >> 8) + (k & 0xFF) <= this.f.size())) {
          for (int l = 0; l < (k & 0xFF); l++) {
            this.f.set((k >> 8) + l, Boolean.valueOf(false));
          }
        }
      }
      
      for (j = 0; j < 1024; j++) {
        int k = this.c.readInt();
        this.e[j] = k;
      }
    } catch (IOException ioexception) {
      ioexception.printStackTrace();
    }
  }
  
  public synchronized boolean chunkExists(int i, int j)
  {
    if (d(i, j)) {
      return false;
    }
    try {
      int k = e(i, j);
      
      if (k == 0) {
        return false;
      }
      int l = k >> 8;
      int i1 = k & 0xFF;
      
      if (l + i1 > this.f.size()) {
        return false;
      }
      
      this.c.seek(l * 4096);
      int j1 = this.c.readInt();
      
      if ((j1 > 4096 * i1) || (j1 <= 0)) {
        return false;
      }
      
      byte b0 = this.c.readByte();
      if ((b0 == 1) || (b0 == 2)) {
        return true;
      }
    }
    catch (IOException ioexception) {
      return false;
    }
    

    return false;
  }
  
  public synchronized DataInputStream a(int i, int j)
  {
    if (d(i, j)) {
      return null;
    }
    try {
      int k = e(i, j);
      
      if (k == 0) {
        return null;
      }
      int l = k >> 8;
      int i1 = k & 0xFF;
      
      if (l + i1 > this.f.size()) {
        return null;
      }
      this.c.seek(l * 4096);
      int j1 = this.c.readInt();
      
      if (j1 > 4096 * i1)
        return null;
      if (j1 <= 0) {
        return null;
      }
      byte b0 = this.c.readByte();
      

      if (b0 == 1) {
        byte[] abyte = new byte[j1 - 1];
        this.c.read(abyte);
        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte)))); }
      if (b0 == 2) {
        byte[] abyte = new byte[j1 - 1];
        this.c.read(abyte);
        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
      }
      return null;
    }
    catch (IOException ioexception) {}
    


    return null;
  }
  

  public DataOutputStream b(int i, int j)
  {
    return d(i, j) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(this, i, j)));
  }
  
  protected synchronized void a(int i, int j, byte[] abyte, int k) {
    try {
      int l = e(i, j);
      int i1 = l >> 8;
      int j1 = l & 0xFF;
      int k1 = (k + 5) / 4096 + 1;
      
      if (k1 >= 256) {
        return;
      }
      
      if ((i1 != 0) && (j1 == k1)) {
        a(i1, abyte, k);
      }
      else
      {
        for (int l1 = 0; l1 < j1; l1++) {
          this.f.set(i1 + l1, Boolean.valueOf(true));
        }
        
        l1 = this.f.indexOf(Boolean.valueOf(true));
        int i2 = 0;
        

        if (l1 != -1) {
          for (int j2 = l1; j2 < this.f.size(); j2++) {
            if (i2 != 0) {
              if (((Boolean)this.f.get(j2)).booleanValue()) {
                i2++;
              } else {
                i2 = 0;
              }
            } else if (((Boolean)this.f.get(j2)).booleanValue()) {
              l1 = j2;
              i2 = 1;
            }
            
            if (i2 >= k1) {
              break;
            }
          }
        }
        
        if (i2 >= k1) {
          i1 = l1;
          a(i, j, l1 << 8 | k1);
          
          for (int j2 = 0; j2 < k1; j2++) {
            this.f.set(i1 + j2, Boolean.valueOf(false));
          }
          
          a(i1, abyte, k);
        } else {
          this.c.seek(this.c.length());
          i1 = this.f.size();
          
          for (int j2 = 0; j2 < k1; j2++) {
            this.c.write(a);
            this.f.add(Boolean.valueOf(false));
          }
          
          this.g += 4096 * k1;
          a(i1, abyte, k);
          a(i, j, i1 << 8 | k1);
        }
      }
      
      b(i, j, (int)(MinecraftServer.ar() / 1000L));
    } catch (IOException ioexception) {
      ioexception.printStackTrace();
    }
  }
  
  private void a(int i, byte[] abyte, int j) throws IOException {
    this.c.seek(i * 4096);
    this.c.writeInt(j + 1);
    this.c.writeByte(2);
    this.c.write(abyte, 0, j);
  }
  
  private boolean d(int i, int j) {
    return (i < 0) || (i >= 32) || (j < 0) || (j >= 32);
  }
  
  private int e(int i, int j) {
    return this.d[(i + j * 32)];
  }
  
  public boolean c(int i, int j) {
    return e(i, j) != 0;
  }
  
  private void a(int i, int j, int k) throws IOException {
    this.d[(i + j * 32)] = k;
    this.c.seek((i + j * 32) * 4);
    this.c.writeInt(k);
  }
  
  private void b(int i, int j, int k) throws IOException {
    this.e[(i + j * 32)] = k;
    this.c.seek(4096 + (i + j * 32) * 4);
    this.c.writeInt(k);
  }
  
  public void c() throws IOException {
    if (this.c != null) {
      this.c.close();
    }
  }
}
