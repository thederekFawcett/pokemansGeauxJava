/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;

import java.util.Comparator;

// returns sort by descending max CP
class SortByCombinedStats implements Comparator<Pokedex> {
    public int compare(Pokedex a, Pokedex b) {
        return (FetchBasedOnStats.combineStats(b) - FetchBasedOnStats.combineStats(a));
    }
}
