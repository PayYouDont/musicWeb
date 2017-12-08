 package music.util;
 
 import java.io.UnsupportedEncodingException;
 import java.math.BigDecimal;
 import java.util.UUID;
import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class StringUtil
 {
   private static final Log log = LogFactory.getLog(StringUtil.class);
   
   public static boolean isEmpty(String source)
   {
     return (source == null) || (source.isEmpty());
   }
   
   public static boolean isNotEmpty(String source)
   {
     return !isEmpty(source);
   }
   
   public static String transformUtf8(String str)
   {
     if (isEmpty(str)) {
       return "";
     }
     String restr = str;
     try
     {
       return new String(str.getBytes("ISO8859-1"), "utf-8");
     }
     catch (UnsupportedEncodingException e)
     {
       log.error(e);
     }
     return restr;
   }
   
   public static String getUUID()
   {
     String s = UUID.randomUUID().toString();
     
     return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
   }
   
   public static String transformNull(String str)
   {
     return str == null ? "" : str;
   }
   public static Object parseOriginalType(String str)
   {
     if (("true".equals(str)) || ("false".equals(str))) {
       return Boolean.valueOf(str);
     }
     if (isNumeric(str)) {
       return new BigDecimal(str);
     }
     return str;
   }
   
   public static boolean isNumeric(String str)
   {
     return str.matches("\\d+|\\d+.\\d+");
   }
 }






