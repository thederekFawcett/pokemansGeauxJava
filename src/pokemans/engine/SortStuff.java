/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.user.UserPokemon;

import java.util.Comparator;

/////////////////////////////////////////////////////////////////////////////////////
// user poke sorting below

// returns sort by descending max CP
class SortByCP implements Comparator<UserPokemon> {
  public int compare(UserPokemon a, UserPokemon b) {
    return (b.getUserPokeCP()).subtract(a.getUserPokeCP()).intValue();
  }
}

// sort by max CP times type effectiveness
class SortByUserCPDamageTotal implements Comparator<UserPokemon> {
  public int compare(UserPokemon a, UserPokemon b) {
    return (Maths.calculateUserCPTimesDamage(b))
            .subtract(Maths.calculateUserCPTimesDamage(a))
            .intValue();
  }
}

/////////////////////////////////////////////////////////////////////////////////////
// pokedex sorting below

// returns sort by descending max CP
class SortByCombinedStats implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (FetchBasedOnStats.combineStats(b) - FetchBasedOnStats.combineStats(a));
  }
}

// returns sort by descending max CP
class SortByMaxCP implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateMaxCP(b)).subtract(Maths.calculateMaxCP(a)).intValue();
  }
}

// sort by max CP times type effectiveness
class SortByMaxCPDamageTotal implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateCPTimesDamageForPoke(b))
            .subtract(Maths.calculateCPTimesDamageForPoke(a))
            .intValue();
  }
}

// sort by (max CP * type effectiveness) * move DPE
class SortByCPDTAndMoveDPE implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateCPTDAndMove(b)).subtract(Maths.calculateCPTDAndMove(a)).intValue();
  }
}
