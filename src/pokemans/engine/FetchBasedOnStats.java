/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.math.BigDecimal;
import java.util.*;

public class FetchBasedOnStats {

    static final ArrayList<Pokedex> pokesSuperEffective = new ArrayList<>();
    static final Map<Integer, Pokedex> unsortMap = new HashMap<>();
    //static final SortedMap<Integer, String> sortedAscending = new TreeMap<Integer, String>();
    static final Map<Integer, Pokedex> sortedReverseObject = new TreeMap<Integer, Pokedex>(Collections.reverseOrder());

    static void againstTypes(ArrayList<Type> typesSuperEffective, ArrayList<BigDecimal> damageSuperEffective) {

        int statProduct;

        for (Pokedex pokes : Pokedex.values()) {
            if (!Collections.disjoint(typesSuperEffective, Arrays.asList(pokes.getType()))) {
                statProduct = combineStats(pokes);
                pokesSuperEffective.add(pokes);
                unsortMap.put(statProduct, pokes);
            }
        }
        sortedReverseObject.putAll(unsortMap);

        ArrayList<Pokedex> valueList = new ArrayList<>(sortedReverseObject.values());
        List<Pokedex> mySubList = valueList.subList(0, 20);

        for (Pokedex pokedex : mySubList) {
            System.out.println(pokedex.getPokeName() + ": " +
                    (pokedex.getPokeAttack() + pokedex.getPokeDefense() + pokedex.getPokeStamina()));
        }
    }

    static private String listToString(Type[] type) {
        return Arrays.toString(type).replace("[", "").replace("]", "");
    }

    private static int combineStats(Pokedex poke) {
        return (poke.getPokeAttack() + poke.getPokeDefense() + poke.getPokeStamina());
    }
}
