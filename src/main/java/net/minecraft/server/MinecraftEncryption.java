package net.minecraft.server;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MinecraftEncryption
{
  public static KeyPair b()
  {
    try
    {
      KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
      localKeyPairGenerator.initialize(1024);
      
      return localKeyPairGenerator.generateKeyPair();
    } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
      localNoSuchAlgorithmException.printStackTrace();
      
      System.err.println("Key pair generation failed!"); }
    return null;
  }
  
  public static byte[] a(String paramString, PublicKey paramPublicKey, SecretKey paramSecretKey) {
    try {
      return a("SHA-1", new byte[][] { paramString.getBytes("ISO_8859_1"), paramSecretKey.getEncoded(), paramPublicKey.getEncoded() });


    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {

      localUnsupportedEncodingException.printStackTrace();
    }
    
    return null;
  }
  
  private static byte[] a(String paramString, byte[]... paramVarArgs) {
    try {
      MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
      for (byte[] arrayOfByte1 : paramVarArgs) {
        localMessageDigest.update(arrayOfByte1);
      }
      return localMessageDigest.digest();
    } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
      localNoSuchAlgorithmException.printStackTrace();
    }
    
    return null;
  }
  
  public static PublicKey a(byte[] paramArrayOfByte) {
    try {
      X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(paramArrayOfByte);
      KeyFactory localKeyFactory = KeyFactory.getInstance("RSA");
      return localKeyFactory.generatePublic(localX509EncodedKeySpec);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}catch (InvalidKeySpecException localInvalidKeySpecException) {}
    
    System.err.println("Public key reconstitute failed!");
    return null;
  }
  
  public static SecretKey a(PrivateKey paramPrivateKey, byte[] paramArrayOfByte) {
    return new SecretKeySpec(b(paramPrivateKey, paramArrayOfByte), "AES");
  }
  



  public static byte[] b(Key paramKey, byte[] paramArrayOfByte)
  {
    return a(2, paramKey, paramArrayOfByte);
  }
  
  private static byte[] a(int paramInt, Key paramKey, byte[] paramArrayOfByte) {
    try {
      return a(paramInt, paramKey.getAlgorithm(), paramKey).doFinal(paramArrayOfByte);
    } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
      localIllegalBlockSizeException.printStackTrace();
    } catch (BadPaddingException localBadPaddingException) {
      localBadPaddingException.printStackTrace();
    }
    System.err.println("Cipher data failed!");
    return null;
  }
  
  private static Cipher a(int paramInt, String paramString, Key paramKey) {
    try {
      Cipher localCipher = Cipher.getInstance(paramString);
      localCipher.init(paramInt, paramKey);
      return localCipher;
    } catch (InvalidKeyException localInvalidKeyException) {
      localInvalidKeyException.printStackTrace();
    } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
      localNoSuchAlgorithmException.printStackTrace();
    } catch (NoSuchPaddingException localNoSuchPaddingException) {
      localNoSuchPaddingException.printStackTrace();
    }
    System.err.println("Cipher creation failed!");
    return null;
  }
  
  public static Cipher a(int paramInt, Key paramKey) {
    try {
      Cipher localCipher = Cipher.getInstance("AES/CFB8/NoPadding");
      localCipher.init(paramInt, paramKey, new IvParameterSpec(paramKey.getEncoded()));
      return localCipher;
    } catch (GeneralSecurityException localGeneralSecurityException) {
      throw new RuntimeException(localGeneralSecurityException);
    }
  }
}
