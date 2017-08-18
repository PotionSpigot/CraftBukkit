package net.minecraft.server;

import java.util.Arrays;




public class ChunkSection
{
  private int yPos;
  private int nonEmptyBlockCount;
  private int tickingBlockCount;
  private byte[] blockIds;
  private NibbleArray extBlockIds;
  private NibbleArray blockData;
  private NibbleArray emittedLight;
  private NibbleArray skyLight;
  private int compactId;
  private byte compactExtId;
  private byte compactData;
  private byte compactEmitted;
  private byte compactSky;
  boolean isDirty;
  private static NibbleArray[] compactPregen = new NibbleArray[16];
  
  static { for (int i = 0; i < 16; i++) {
      compactPregen[i] = expandCompactNibble((byte)i);
    }
  }
  
  private static NibbleArray expandCompactNibble(byte value) {
    byte[] data = new byte['ࠀ'];
    Arrays.fill(data, (byte)(value | value << 4));
    return new NibbleArray(data, 4);
  }
  
  private boolean canBeCompact(byte[] array) {
    byte value = array[0];
    for (int i = 1; i < array.length; i++) {
      if (value != array[i]) {
        return false;
      }
    }
    
    return true;
  }
  
  public ChunkSection(int i, boolean flag)
  {
    this.yPos = i;
    







    if (!flag) {
      this.compactSky = -1;
    }
  }
  

  public ChunkSection(int y, boolean flag, byte[] blkIds, byte[] extBlkIds)
  {
    this.yPos = y;
    setIdArray(blkIds);
    if (extBlkIds != null) {
      setExtendedIdArray(new NibbleArray(extBlkIds, 4));
    }
    if (!flag) {
      this.compactSky = -1;
    }
    recalcBlockCounts();
  }
  

  public Block getTypeId(int i, int j, int k)
  {
    if (this.blockIds == null) {
      int id = this.compactId;
      if (this.extBlockIds == null) {
        id |= this.compactExtId << 8;
      } else {
        id |= this.extBlockIds.a(i, j, k) << 8;
      }
      
      return Block.getById(id);
    }
    

    int l = this.blockIds[(j << 8 | k << 4 | i)] & 0xFF;
    
    if (this.extBlockIds != null) {
      l |= this.extBlockIds.a(i, j, k) << 8;
    }
    
    return Block.getById(l);
  }
  
  public void setTypeId(int i, int j, int k, Block block)
  {
    Block block1 = getTypeId(i, j, k);
    if (block == block1) {
      return;
    }
    

    if (block1 != Blocks.AIR) {
      this.nonEmptyBlockCount -= 1;
      if (block1.isTicking()) {
        this.tickingBlockCount -= 1;
      }
    }
    
    if (block != Blocks.AIR) {
      this.nonEmptyBlockCount += 1;
      if (block.isTicking()) {
        this.tickingBlockCount += 1;
      }
    }
    
    int i1 = Block.getId(block);
    

    if (this.blockIds == null) {
      this.blockIds = new byte['က'];
      Arrays.fill(this.blockIds, (byte)(this.compactId & 0xFF));
    }
    

    this.blockIds[(j << 8 | k << 4 | i)] = ((byte)(i1 & 0xFF));
    if (i1 > 255) {
      if (this.extBlockIds == null) {
        this.extBlockIds = expandCompactNibble(this.compactExtId);
      }
      
      this.extBlockIds.a(i, j, k, (i1 & 0xF00) >> 8);
    } else if (this.extBlockIds != null) {
      this.extBlockIds.a(i, j, k, 0);
    }
    
    this.isDirty = true;
  }
  
  public int getData(int i, int j, int k)
  {
    if (this.blockData == null) {
      return this.compactData;
    }
    
    return this.blockData.a(i, j, k);
  }
  
  public void setData(int i, int j, int k, int l)
  {
    if (this.blockData == null) {
      if (this.compactData == l) {
        return;
      }
      this.blockData = expandCompactNibble(this.compactData);
    }
    
    this.blockData.a(i, j, k, l);
    this.isDirty = true;
  }
  
  public boolean isEmpty() {
    return this.nonEmptyBlockCount == 0;
  }
  
  public boolean shouldTick() {
    return this.tickingBlockCount > 0;
  }
  
  public int getYPosition() {
    return this.yPos;
  }
  
  public void setSkyLight(int i, int j, int k, int l)
  {
    if (this.skyLight == null) {
      if (this.compactSky == l) {
        return;
      }
      this.skyLight = expandCompactNibble(this.compactSky);
    }
    
    this.skyLight.a(i, j, k, l);
    this.isDirty = true;
  }
  
  public int getSkyLight(int i, int j, int k)
  {
    if (this.skyLight == null) {
      return this.compactSky;
    }
    
    return this.skyLight.a(i, j, k);
  }
  
  public void setEmittedLight(int i, int j, int k, int l)
  {
    if (this.emittedLight == null) {
      if (this.compactEmitted == l) {
        return;
      }
      this.emittedLight = expandCompactNibble(this.compactEmitted);
    }
    
    this.emittedLight.a(i, j, k, l);
    this.isDirty = true;
  }
  
  public int getEmittedLight(int i, int j, int k)
  {
    if (this.emittedLight == null) {
      return this.compactEmitted;
    }
    
    return this.emittedLight.a(i, j, k);
  }
  
  public void recalcBlockCounts()
  {
    int cntNonEmpty = 0;
    int cntTicking = 0;
    int id;
    byte[] ext; int off; int off2; byte[] blkIds; byte[] ext; int off; int off2; if (this.blockIds == null) {
      id = this.compactId;
      if (this.extBlockIds == null) {
        id |= this.compactExtId << 8;
        if (id > 0) {
          Block block = Block.getById(id);
          if (block == null) {
            this.compactId = 0;
            this.compactExtId = 0;
          } else {
            cntNonEmpty = 4096;
            if (block.isTicking()) {
              cntTicking = 4096;
            }
          }
        }
      } else {
        ext = this.extBlockIds.a;
        off = 0; for (off2 = 0; off < 4096;) {
          byte extid = ext[off2];
          int l = id & 0xFF | (extid & 0xF) << 8;
          if (l > 0) {
            Block block = Block.getById(l);
            if (block == null) {
              this.compactId = 0; int 
                tmp152_150 = off2; byte[] tmp152_148 = ext;tmp152_148[tmp152_150] = ((byte)(tmp152_148[tmp152_150] & 0xF0));
            } else {
              cntNonEmpty++;
              if (block.isTicking()) {
                cntTicking++;
              }
            }
          }
          off++;
          l = id & 0xFF | (extid & 0xF0) << 4;
          if (l > 0) {
            Block block = Block.getById(l);
            if (block == null) {
              this.compactId = 0; int 
                tmp222_220 = off2; byte[] tmp222_218 = ext;tmp222_218[tmp222_220] = ((byte)(tmp222_218[tmp222_220] & 0xF));
            } else {
              cntNonEmpty++;
              if (block.isTicking()) {
                cntTicking++;
              }
            }
          }
          off++;
          off2++;
        }
      }
    } else {
      blkIds = this.blockIds;
      if (this.extBlockIds == null) {
        for (int off = 0; off < blkIds.length; off++) {
          int l = blkIds[off] & 0xFF;
          if (l > 0) {
            if (Block.getById(l) == null) {
              blkIds[off] = 0;
            } else {
              cntNonEmpty++;
              if (Block.getById(l).isTicking()) {
                cntTicking++;
              }
            }
          }
        }
      } else {
        ext = this.extBlockIds.a;
        off = 0; for (off2 = 0; off < blkIds.length;) {
          byte extid = ext[off2];
          int l = blkIds[off] & 0xFF | (extid & 0xF) << 8;
          if (l > 0) {
            if (Block.getById(l) == null) {
              blkIds[off] = 0; int 
                tmp407_405 = off2; byte[] tmp407_403 = ext;tmp407_403[tmp407_405] = ((byte)(tmp407_403[tmp407_405] & 0xF0));
            } else {
              cntNonEmpty++;
              if (Block.getById(l).isTicking()) {
                cntTicking++;
              }
            }
          }
          off++;
          l = blkIds[off] & 0xFF | (extid & 0xF0) << 4;
          if (l > 0) {
            if (Block.getById(l) == null) {
              blkIds[off] = 0; int 
                tmp479_477 = off2; byte[] tmp479_475 = ext;tmp479_475[tmp479_477] = ((byte)(tmp479_475[tmp479_477] & 0xF));
            } else {
              cntNonEmpty++;
              if (Block.getById(l).isTicking()) {
                cntTicking++;
              }
            }
          }
          off++;
          off2++;
        }
      }
    }
    this.nonEmptyBlockCount = cntNonEmpty;
    this.tickingBlockCount = cntTicking;
  }
  
  public void old_recalcBlockCounts()
  {
    this.nonEmptyBlockCount = 0;
    this.tickingBlockCount = 0;
    
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 16; j++) {
        for (int k = 0; k < 16; k++) {
          Block block = getTypeId(i, j, k);
          
          if (block != Blocks.AIR) {
            this.nonEmptyBlockCount += 1;
            if (block.isTicking()) {
              this.tickingBlockCount += 1;
            }
          }
        }
      }
    }
  }
  
  public byte[] getIdArray()
  {
    if (this.blockIds == null) {
      byte[] ids = new byte['က'];
      Arrays.fill(ids, (byte)(this.compactId & 0xFF));
      return ids;
    }
    
    return this.blockIds;
  }
  
  public NibbleArray getExtendedIdArray()
  {
    if ((this.extBlockIds == null) && (this.compactExtId != 0)) {
      return compactPregen[this.compactExtId];
    }
    
    return this.extBlockIds;
  }
  
  public NibbleArray getDataArray()
  {
    if (this.blockData == null) {
      return compactPregen[this.compactData];
    }
    
    return this.blockData;
  }
  
  public NibbleArray getEmittedLightArray()
  {
    if (this.emittedLight == null) {
      return compactPregen[this.compactEmitted];
    }
    
    return this.emittedLight;
  }
  
  public NibbleArray getSkyLightArray()
  {
    if ((this.skyLight == null) && (this.compactSky != -1)) {
      return compactPregen[this.compactSky];
    }
    
    return this.skyLight;
  }
  
  public void setIdArray(byte[] abyte)
  {
    if (abyte == null) {
      this.compactId = 0;
      this.blockIds = null;
      return; }
    if (canBeCompact(abyte)) {
      this.compactId = (abyte[0] & 0xFF);
      return;
    }
    
    this.blockIds = validateByteArray(abyte);
  }
  
  public void setExtendedIdArray(NibbleArray nibblearray)
  {
    if (nibblearray == null) {
      this.compactExtId = 0;
      this.extBlockIds = null;
      return; }
    if (canBeCompact(nibblearray.a)) {
      this.compactExtId = ((byte)(nibblearray.a(0, 0, 0) & 0xF));
      return;
    }
    
    this.extBlockIds = validateNibbleArray(nibblearray);
  }
  
  public void setDataArray(NibbleArray nibblearray)
  {
    if (nibblearray == null) {
      this.compactData = 0;
      this.blockData = null;
      return; }
    if (canBeCompact(nibblearray.a)) {
      this.compactData = ((byte)(nibblearray.a(0, 0, 0) & 0xF));
      return;
    }
    
    this.blockData = validateNibbleArray(nibblearray);
  }
  
  public void setEmittedLightArray(NibbleArray nibblearray)
  {
    if (nibblearray == null) {
      this.compactEmitted = 0;
      this.emittedLight = null;
      return; }
    if (canBeCompact(nibblearray.a)) {
      this.compactEmitted = ((byte)(nibblearray.a(0, 0, 0) & 0xF));
      return;
    }
    
    this.emittedLight = validateNibbleArray(nibblearray);
  }
  
  public void setSkyLightArray(NibbleArray nibblearray)
  {
    if (nibblearray == null) {
      this.compactSky = -1;
      this.skyLight = null;
      return; }
    if (canBeCompact(nibblearray.a)) {
      this.compactSky = ((byte)(nibblearray.a(0, 0, 0) & 0xF));
      return;
    }
    
    this.skyLight = validateNibbleArray(nibblearray);
  }
  
  private NibbleArray validateNibbleArray(NibbleArray nibbleArray)
  {
    if ((nibbleArray != null) && (nibbleArray.a.length < 2048)) {
      byte[] newArray = new byte['ࠀ'];
      System.arraycopy(nibbleArray.a, 0, newArray, 0, nibbleArray.a.length);
      nibbleArray = new NibbleArray(newArray, 4);
    }
    
    return nibbleArray;
  }
  
  private byte[] validateByteArray(byte[] byteArray) {
    if ((byteArray != null) && (byteArray.length < 4096)) {
      byte[] newArray = new byte['က'];
      System.arraycopy(byteArray, 0, newArray, 0, byteArray.length);
      byteArray = newArray;
    }
    
    return byteArray;
  }
}
