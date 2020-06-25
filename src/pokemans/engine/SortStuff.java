/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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

// returns sort by descending max CP
class SortByMaxCP implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateMaxCP(b)).subtract(Maths.calculateMaxCP(a)).intValue();
  }
}

// sort by max CP times type effectiveness
class SortByMaxCPDamageTotal implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateCPTimesDamageForPoke(b)).subtract(Maths.calculateCPTimesDamageForPoke(a)).intValue();
  }
}

// sort by (max CP * type effectiveness) * move DPE
class SortByCPDTAndMoveDPE implements Comparator<Pokedex> {
  public int compare(Pokedex a, Pokedex b) {
    return (Maths.calculateCPTDAndMove(b)).subtract(Maths.calculateCPTDAndMove(a)).intValue();
  }
}