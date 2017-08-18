package net.minecraft.server;

import java.io.DataOutputStream;

public class RemoteStatusReply {
  private java.io.ByteArrayOutputStream buffer;
  private DataOutputStream stream;
  
  public RemoteStatusReply(int paramInt) {
    this.buffer = new java.io.ByteArrayOutputStream(paramInt);
    this.stream = new DataOutputStream(this.buffer);
  }
  
  public void write(byte[] paramArrayOfByte) {
    this.stream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void write(String paramString) {
    this.stream.writeBytes(paramString);
    this.stream.write(0);
  }
  
  public void write(int paramInt) {
    this.stream.write(paramInt);
  }
  
  public void write(short paramShort)
  {
    this.stream.writeShort(Short.reverseBytes(paramShort));
  }
  







  public byte[] getBytes()
  {
    return this.buffer.toByteArray();
  }
  
  public void reset() {
    this.buffer.reset();
  }
}
