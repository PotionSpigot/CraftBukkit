package net.minecraft.server;

public class CommandException extends RuntimeException {
  private Object[] a;
  
  public CommandException(String paramString, Object... paramVarArgs) {
    super(paramString);
    
    this.a = paramVarArgs;
  }
  
  public Object[] getArgs() {
    return this.a;
  }
}
