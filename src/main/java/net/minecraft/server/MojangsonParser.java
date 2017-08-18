package net.minecraft.server;

import java.util.ArrayList;
import java.util.Stack;
import org.apache.logging.log4j.Logger;

public class MojangsonParser
{
  private static final Logger a = ;
  



  public static NBTBase parse(String paramString)
  {
    paramString = paramString.trim();
    int i = b(paramString);
    if (i != 1) {
      throw new MojangsonParseException("Encountered multiple top tags, only one expected");
    }
    
    MojangsonTypeParser localMojangsonTypeParser = null;
    if (paramString.startsWith("{")) {
      localMojangsonTypeParser = a("tag", paramString);
    } else {
      localMojangsonTypeParser = a(b(paramString, false), c(paramString, false));
    }
    
    return localMojangsonTypeParser.a();
  }
  
  static int b(String paramString)
  {
    int i = 0;
    int j = 0;
    Stack localStack = new Stack();
    
    int k = 0;
    while (k < paramString.length()) {
      char c = paramString.charAt(k);
      if (c == '"') {
        if ((k > 0) && (paramString.charAt(k - 1) == '\\')) {
          if (j == 0) {
            throw new MojangsonParseException("Illegal use of \\\": " + paramString);
          }
        } else {
          j = j == 0 ? 1 : 0;
        }
      } else if (j == 0) {
        if ((c == '{') || (c == '[')) {
          if (localStack.isEmpty()) {
            i++;
          }
          localStack.push(Character.valueOf(c));
        } else { if ((c == '}') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '{')))
            throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString);
          if ((c == ']') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '[')))
            throw new MojangsonParseException("Unbalanced square brackets []: " + paramString);
        }
      }
      k++;
    }
    if (j != 0) {
      throw new MojangsonParseException("Unbalanced quotation: " + paramString);
    }
    if (!localStack.isEmpty()) {
      throw new MojangsonParseException("Unbalanced brackets: " + paramString);
    }
    
    if ((i == 0) && (!paramString.isEmpty())) {
      return 1;
    }
    return i;
  }
  
  static MojangsonTypeParser a(String paramString1, String paramString2) {
    paramString2 = paramString2.trim();
    b(paramString2);
    Object localObject;
    String str1; String str2; String str3; char c; if (paramString2.startsWith("{")) {
      if (!paramString2.endsWith("}")) {
        throw new MojangsonParseException("Unable to locate ending bracket for: " + paramString2);
      }
      
      paramString2 = paramString2.substring(1, paramString2.length() - 1);
      
      localObject = new MojangsonCompoundParser(paramString1);
      while (paramString2.length() > 0) {
        str1 = a(paramString2, false);
        if (str1.length() > 0) {
          str2 = b(str1, false);
          str3 = c(str1, false);
          ((MojangsonCompoundParser)localObject).b.add(a(str2, str3));
          
          if (paramString2.length() < str1.length() + 1) break;
          c = paramString2.charAt(str1.length());
          if ((c != ',') && (c != '{') && (c != '}') && (c != '[') && (c != ']')) {
            throw new MojangsonParseException("Unexpected token '" + c + "' at: " + paramString2.substring(str1.length()));
          }
          paramString2 = paramString2.substring(str1.length() + 1);
        }
      }
      



      return (MojangsonTypeParser)localObject; }
    if ((paramString2.startsWith("[")) && (!paramString2.matches("\\[[-\\d|,\\s]+\\]"))) {
      if (!paramString2.endsWith("]")) {
        throw new MojangsonParseException("Unable to locate ending bracket for: " + paramString2);
      }
      
      paramString2 = paramString2.substring(1, paramString2.length() - 1);
      
      localObject = new MojangsonListParser(paramString1);
      while (paramString2.length() > 0) {
        str1 = a(paramString2, true);
        if (str1.length() > 0) {
          str2 = b(str1, true);
          str3 = c(str1, true);
          ((MojangsonListParser)localObject).b.add(a(str2, str3));
          
          if (paramString2.length() < str1.length() + 1) break;
          c = paramString2.charAt(str1.length());
          if ((c != ',') && (c != '{') && (c != '}') && (c != '[') && (c != ']')) {
            throw new MojangsonParseException("Unexpected token '" + c + "' at: " + paramString2.substring(str1.length()));
          }
          paramString2 = paramString2.substring(str1.length() + 1);

        }
        else
        {
          a.debug(paramString2);
        }
      }
      
      return (MojangsonTypeParser)localObject;
    }
    return new MojangsonPrimitiveParser(paramString1, paramString2);
  }
  
  private static String a(String paramString, boolean paramBoolean)
  {
    int i = a(paramString, ':');
    if ((i < 0) && (!paramBoolean)) {
      throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
    }
    int j = a(paramString, ',');
    if ((j >= 0) && (j < i) && (!paramBoolean)) {
      throw new MojangsonParseException("Name error at: " + paramString);
    }
    if ((paramBoolean) && ((i < 0) || (i > j))) {
      i = -1;
    }
    
    Stack localStack = new Stack();
    int k = i + 1;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    
    while (k < paramString.length()) {
      char c = paramString.charAt(k);
      
      if (c == '"') {
        if ((k > 0) && (paramString.charAt(k - 1) == '\\')) {
          if (m == 0) {
            throw new MojangsonParseException("Illegal use of \\\": " + paramString);
          }
        } else {
          m = m == 0 ? 1 : 0;
          if ((m != 0) && (i1 == 0)) {
            n = 1;
          }
          if (m == 0) {
            i2 = k;
          }
        }
      } else if (m == 0) {
        if ((c == '{') || (c == '[')) {
          localStack.push(Character.valueOf(c));
        } else { if ((c == '}') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '{')))
            throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString);
          if ((c == ']') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '[')))
            throw new MojangsonParseException("Unbalanced square brackets []: " + paramString);
          if ((c == ',') && 
            (localStack.isEmpty())) {
            return paramString.substring(0, k);
          }
        }
      }
      if (!Character.isWhitespace(c)) {
        if ((m == 0) && (n != 0) && (i2 != k)) {
          return paramString.substring(0, i2 + 1);
        }
        i1 = 1;
      }
      
      k++;
    }
    
    return paramString.substring(0, k);
  }
  
  private static String b(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      paramString = paramString.trim();
      if ((paramString.startsWith("{")) || (paramString.startsWith("["))) {
        return "";
      }
    }
    
    int i = paramString.indexOf(':');
    if (i < 0) {
      if (paramBoolean) {
        return "";
      }
      throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
    }
    return paramString.substring(0, i).trim();
  }
  
  private static String c(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      paramString = paramString.trim();
      if ((paramString.startsWith("{")) || (paramString.startsWith("["))) {
        return paramString;
      }
    }
    
    int i = paramString.indexOf(':');
    if (i < 0) {
      if (paramBoolean) {
        return paramString;
      }
      throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
    }
    return paramString.substring(i + 1).trim();
  }
  
  private static int a(String paramString, char paramChar) {
    int i = 0;
    int j = 0;
    while (i < paramString.length()) {
      char c = paramString.charAt(i);
      if (c == '"') {
        if ((i <= 0) || (paramString.charAt(i - 1) != '\\'))
        {
          j = j == 0 ? 1 : 0;
        }
      } else if (j == 0) {
        if (c == paramChar) {
          return i;
        }
        if ((c == '{') || (c == '[')) {
          return -1;
        }
      }
      i++;
    }
    return -1;
  }
}
