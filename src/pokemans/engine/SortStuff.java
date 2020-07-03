/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.user.UserPokemon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

/////////////////////////////////////////////////////////////////////////////////////
// user poke sorting below

// returns sort by descending max CP
class SortByCP implements Comparator<UserPokemon> {
  public int compare(UserPokemon a, UserPokemon b) {
    return (b.getUserPokeCP()).subtract(a.getUserPokeCP()).intValue();
  }
}

// sort by max CP times type effectiveness
class SortByUserCPTDamage implements Comparator<UserPokemon> {
  public int compare(UserPokemon a, UserPokemon b) {
    return (Maths.calculateUserCPTimesDamage(b))
            .subtract(Maths.calculateUserCPTimesDamage(a))
            .intValue();
  }
}

// sort by max CP times type effectiveness
class SortByUserCPTDamageTMoveDPE implements Comparator<UserPokemon> {
  public int compare(UserPokemon a, UserPokemon b) {
    Object[] valuesA = Maths.calculateUserPokeHighestMoveValue(a);
    Object[] valuesB = Maths.calculateUserPokeHighestMoveValue(b);
    
    return (((BigDecimal) valuesB[0]).subtract((BigDecimal) valuesA[0])).intValue();
  }
}

class ValueComparator implements Comparator<BigDecimal> {
  Map<BigDecimal, UserPokemon> base;
  
  public ValueComparator(Map<BigDecimal, UserPokemon> base) {
    this.base = base;
  }
  
  // Note: this comparator imposes orderings that are inconsistent with equals.
  public int compare(BigDecimal a, BigDecimal b) {
    Object[] valuesA = Maths.calcHighestValueTimesStatProducts(base.get(a));
    Object[] valuesB = Maths.calcHighestValueTimesStatProducts(base.get(b));
    if ((((BigDecimal) valuesB[0]).compareTo((BigDecimal) valuesA[0])) < 0) {
      return -1;
    } else {
      return 1;
    } // returning 0 would merge keys
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
    Object[] valuesA = Maths.getHighestMoveValues(a);
    Object[] valuesB = Maths.getHighestMoveValues(b);
  
    return (((BigDecimal) valuesB[0]).subtract((BigDecimal) valuesA[0])).intValue();
  }
}
