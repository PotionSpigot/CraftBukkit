package net.minecraft.server;

public class ExceptionInvalidSyntax extends CommandException {
  public ExceptionInvalidSyntax() {
    this("commands.generic.snytax", new Object[0]);
  }
  
  public ExceptionInvalidSyntax(String paramString, Object... paramVarArgs) {
    super(paramString, paramVarArgs);
  }
}
