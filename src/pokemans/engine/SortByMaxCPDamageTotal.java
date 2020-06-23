/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;

import java.util.Comparator;

// returns sort by descending max CP
class SortByMaxCPDamageTotal implements Comparator<Pokedex> {
    public int compare(Pokedex a, Pokedex b) {
        return (Maths.calculateCPTimesDamage(b)).subtract(Maths.calculateCPTimesDamage(a)).intValue();
    }
}
