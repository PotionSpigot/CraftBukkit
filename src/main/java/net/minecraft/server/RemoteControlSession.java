package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





public class RemoteControlSession
  extends RemoteConnectionThread
{
  private static final Logger h = ;
  

  private boolean i;
  

  private Socket j;
  
  private byte[] k = new byte['ִ'];
  private String l;
  
  RemoteControlSession(IMinecraftServer paramIMinecraftServer, Socket paramSocket) {
    super(paramIMinecraftServer, "RCON Client");
    this.j = paramSocket;
    try
    {
      this.j.setSoTimeout(0);
    } catch (Exception localException) {
      this.running = false;
    }
    
    this.l = paramIMinecraftServer.a("rcon.password", "");
    info("Rcon connection from: " + paramSocket.getInetAddress());
  }
  
  public void run()
  {
    try {
      while (this.running) {
        BufferedInputStream localBufferedInputStream = new BufferedInputStream(this.j.getInputStream());
        int m = localBufferedInputStream.read(this.k, 0, 1460);
        
        if (10 > m) {
          return;
        }
        
        int n = 0;
        int i1 = StatusChallengeUtils.b(this.k, 0, m);
        if (i1 != m - 4) {
          return;
        }
        
        n += 4;
        int i2 = StatusChallengeUtils.b(this.k, n, m);
        n += 4;
        
        int i3 = StatusChallengeUtils.b(this.k, n);
        n += 4;
        switch (i3) {
        case 3: 
          String str1 = StatusChallengeUtils.a(this.k, n, m);
          n += str1.length();
          if ((0 != str1.length()) && (str1.equals(this.l))) {
            this.i = true;
            a(i2, 2, "");
          } else {
            this.i = false;
            f();
          }
          break;
        case 2: 
          if (this.i) {
            String str2 = StatusChallengeUtils.a(this.k, n, m);
            try {
              a(i2, this.server.g(str2));
            } catch (Exception localException2) {
              a(i2, "Error executing: " + str2 + " (" + localException2.getMessage() + ")");
            }
          } else {
            f();
          }
          break;
        default: 
          a(i2, String.format("Unknown request %s", new Object[] { Integer.toHexString(i3) }));
        
        }
        
      }
    }
    catch (SocketTimeoutException localSocketTimeoutException) {}catch (IOException localIOException) {}catch (Exception localException1)
    {
      h.error("Exception whilst parsing RCON input", localException1);
    } finally {
      g();
    }
  }
  

  private void a(int paramInt1, int paramInt2, String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1248);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    localDataOutputStream.writeInt(Integer.reverseBytes(arrayOfByte.length + 10));
    localDataOutputStream.writeInt(Integer.reverseBytes(paramInt1));
    localDataOutputStream.writeInt(Integer.reverseBytes(paramInt2));
    localDataOutputStream.write(arrayOfByte);
    localDataOutputStream.write(0);
    localDataOutputStream.write(0);
    this.j.getOutputStream().write(localByteArrayOutputStream.toByteArray());
  }
  
  private void f() {
    a(-1, 2, "");
  }
  
  private void a(int paramInt, String paramString) {
    int m = paramString.length();
    for (;;)
    {
      int n = 4096 <= m ? 4096 : m;
      a(paramInt, 0, paramString.substring(0, n));
      paramString = paramString.substring(n);
      m = paramString.length();
      if (0 == m) {
        break;
      }
    }
  }
  





  private void g()
  {
    if (null == this.j) {
      return;
    }
    try
    {
      this.j.close();
    } catch (IOException localIOException) {
      warning("IO: " + localIOException.getMessage());
    }
    this.j = null;
  }
}
