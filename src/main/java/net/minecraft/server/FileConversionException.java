package net.minecraft.server;

class FileConversionException
  extends RuntimeException
{
  private FileConversionException(String s, Throwable throwable)
  {
    super(s, throwable);
  }
  
  private FileConversionException(String s) {
    super(s);
  }
  
  FileConversionException(String s, PredicateEmptyList predicateemptylist) {
    this(s);
  }
  
  FileConversionException(String s, Throwable throwable, PredicateEmptyList predicateemptylist) {
    this(s, throwable);
  }
}
