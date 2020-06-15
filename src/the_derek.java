/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

import pokemans.engine.TypeInteractive;
import pokemans.utilities.read.UtilityUpdateFromJSON;

public class the_derek {

    public static void main(String[] args) {

        System.out.println("\n\t*the_derek wuz here*\n");

        // update program files from PoGo Game Master
        UtilityUpdateFromJSON.main(args);

        // enter type(s) to see what is useful against
        TypeInteractive.main(args);

        System.exit(0);
    }
}
