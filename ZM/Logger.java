package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.io.PrintStream;
import java.util.Date;

public class Logger {
   public static String kLogFile = "CodeWorlds.log";
   
   private static Logger singleton;  // Singleton Logger object
   
   // Return the singleton Logger
   public static Logger getLogger() {
      if (singleton == null)
         singleton = new Logger();   // Lazy creation of singleton
      return singleton;
   }

   private PrintStream out;
   
   public Logger() {
      try {
         out = new PrintStream(kLogFile);
      }
      catch (Exception err) {
         out = System.err;
      }
   }
   
   public void log(String format, Object... args) {
      out.printf("%tc: ", new Date());
      out.printf(format, args);
   }
}
