package net.minecraft.server;


























































































































































class MojangsonPrimitiveParser
  extends MojangsonTypeParser
{
  protected String b;
  
























































































































































  public MojangsonPrimitiveParser(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public NBTBase a()
  {
    try
    {
      if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]"))
        return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length() - 1)));
      if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]"))
        return new NBTTagFloat(Float.parseFloat(this.b.substring(0, this.b.length() - 1)));
      if (this.b.matches("[-+]?[0-9]+[b|B]"))
        return new NBTTagByte(Byte.parseByte(this.b.substring(0, this.b.length() - 1)));
      if (this.b.matches("[-+]?[0-9]+[l|L]"))
        return new NBTTagLong(Long.parseLong(this.b.substring(0, this.b.length() - 1)));
      if (this.b.matches("[-+]?[0-9]+[s|S]"))
        return new NBTTagShort(Short.parseShort(this.b.substring(0, this.b.length() - 1)));
      if (this.b.matches("[-+]?[0-9]+"))
        return new NBTTagInt(Integer.parseInt(this.b.substring(0, this.b.length())));
      if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+"))
        return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length())));
      if ((this.b.equalsIgnoreCase("true")) || (this.b.equalsIgnoreCase("false"))) {
        return new NBTTagByte((byte)(Boolean.parseBoolean(this.b) ? 1 : 0));
      }
      if ((this.b.startsWith("[")) && (this.b.endsWith("]"))) {
        if (this.b.length() > 2) {
          String str = this.b.substring(1, this.b.length() - 1);
          String[] arrayOfString = str.split(",");
          try {
            if (arrayOfString.length <= 1) {
              return new NBTTagIntArray(new int[] { Integer.parseInt(str.trim()) });
            }
            

            int[] arrayOfInt = new int[arrayOfString.length];
            for (int i = 0; i < arrayOfString.length; i++) {
              arrayOfInt[i] = Integer.parseInt(arrayOfString[i].trim());
            }
            return new NBTTagIntArray(arrayOfInt);
          }
          catch (NumberFormatException localNumberFormatException2) {
            return new NBTTagString(this.b);
          }
        }
        return new NBTTagIntArray();
      }
      
      if ((this.b.startsWith("\"")) && (this.b.endsWith("\"")) && (this.b.length() > 2)) {
        this.b = this.b.substring(1, this.b.length() - 1);
      }
      
      this.b = this.b.replaceAll("\\\\\"", "\"");
      return new NBTTagString(this.b);
    }
    catch (NumberFormatException localNumberFormatException1) {
      this.b = this.b.replaceAll("\\\\\"", "\""); }
    return new NBTTagString(this.b);
  }
}
