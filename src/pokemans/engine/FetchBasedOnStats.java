/************************************************
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 ************************************************/

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.math.BigDecimal;
import java.util.ArrayList;

public class FetchBasedOnStats {

    static void againstTypes(ArrayList<Type> typesSuperEffective, ArrayList<BigDecimal> damageSuperEffective) {

        int statProduct;

        //System.out.println(typesSuperEffective);

        for (Pokedex pokes : Pokedex.values()) {
            //System.out.println(pokes.getPokeName() + ": " + pokes.getType().toString().replace
            //      ("[", "").replace("]", ""));
            if (pokes.getType().length == 1) {

                //System.out.println("# of types: " + pokes.getType().length);
                //pokes.getType().

                if (typesSuperEffective.toString().replace
                        ("[", "").replace("]", "").
                        contains(pokes.getType().toString().replace
                                ("[", "").replace("]", ""))) {

                    statProduct = pokes.getPokeAttack() + pokes.getPokeDefense() + pokes.getPokeStamina();
                    System.out.println(pokes.getPokeName() + ": " + statProduct);
                }
            }
        }
    }
}
