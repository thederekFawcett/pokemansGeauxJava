/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
