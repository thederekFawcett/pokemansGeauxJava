/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package pokemans.engine;

import pokemans.core.Pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FetchBasedOnStats {

  static final ArrayList<Pokedex> pokesSuperEffective = new ArrayList<>();

  static void createListOfSuperEffectivePokes() {
    // iterate through each poke
    for (Pokedex poke : Pokedex.values()) {
      if (!Collections.disjoint(
              FetchBasedOnTypes.typesSuperEffective, Arrays.asList(poke.getType()))) {
        pokesSuperEffective.add(poke);
      }
    }
  }

  static List<Pokedex> createListSortedByStats() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max combined stats
    pokeList.sort(new SortByCombinedStats());

    return pokeList.subList(0, 20);
  }

  static List<Pokedex> createListSortedByMaxCP() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max cp
    pokeList.sort(new SortByMaxCP());

    // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);

    return trimmedPokeList.subList(0, 20);
  }

  public static List<Pokedex> createListSortedByMaxCPAndDamage() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max cp
    pokeList.sort(new SortByMaxCPDamageTotal());

    // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);

    return trimmedPokeList.subList(0, 20);
  }

  public static List<Pokedex> createListSortedByMaxCPTDAndMoveDPE() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max cp
    pokeList.sort(new SortByCPDTAndMoveDPE());

    // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);

    return trimmedPokeList.subList(0, 20);
  }

  public static int combineStats(Pokedex poke) {
    return (poke.getPokeAttack() + poke.getPokeDefense() + poke.getPokeStamina());
  }

  public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
    // Create a new ArrayList
    ArrayList<T> newList = new ArrayList<>();

    // Traverse through the first list
    for (T element : list) {
      // If this element is not present in newList, add it
      if (!newList.contains(element)) {
        newList.add(element);
      }
    }
    return newList;
  }
}
