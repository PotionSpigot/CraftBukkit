package net.minecraft.server;

public class ExceptionInvalidNumber extends CommandException {
  public ExceptionInvalidNumber() {
    this("commands.generic.num.invalid", new Object[0]);
  }
  
  public ExceptionInvalidNumber(String paramString, Object... paramVarArgs) {
    super(paramString, paramVarArgs);
  }
}
