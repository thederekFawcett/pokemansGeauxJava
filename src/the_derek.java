/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

import pokemans.engine.TypeInteractive;
import pokemans.user.UserInfo;
import pokemans.utilities.read.UtilityUpdateFromJSONMiner;

public class the_derek {

  public static void main(String[] args) {
    
      System.out.println("\n\t*the_derek wuz here*\n");
    
      // update program files from PoGo Game Master
      // UtilityUpdateFromJSONDev.main(args);
      UtilityUpdateFromJSONMiner.main(args);
    
      // read user CSV
      UserInfo.importUserCSV();
    
      // enter type(s) to see what is useful against
      TypeInteractive.main(args);
    
      System.exit(0);
  }
}
