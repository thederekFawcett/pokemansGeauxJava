/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.user.UserPokemon;

import java.math.BigDecimal;
import java.util.*;

public class FetchBasedOnStats {

  static final ArrayList<Pokedex> pokesSuperEffective = new ArrayList<>();
  static final List<UserPokemon> userBestPokes = new ArrayList<>();

  /////////////////////////////////////////////////////////////////////////////////////
  // user poke listing below
    
    static void createListOfSuperEffectiveUserPokes(List<UserPokemon> pokes) {
    // iterate through each poke
    for (UserPokemon poke : pokes) {
        if (!Collections.disjoint(
                FetchBasedOnTypes.typesSuperEffective, Arrays.asList(poke.getUserPokeType()))) {
            userBestPokes.add(poke);
        }
    }
  }
    
    static List<UserPokemon> createListOfAllUserPokesSortedByCP(List<UserPokemon> userPokes) {
        
        // sort by descending max cp
    userPokes.sort(new SortByCP());
    if (userPokes.size() <= 24) {
      return userPokes;
    } else {
      return userPokes.subList(0, 24);
    }
  }
    
    public static List<UserPokemon> createUserListSortedByMaxCPTimesDamageEffectiveness(
            List<UserPokemon> userPokes) {
        // sort by descending max cp
        userPokes.sort(new SortByUserCPTDamage());
        
        if (userPokes.size() <= 24) {
            return userPokes;
        } else {
            return userPokes.subList(0, 24);
        }
    }
    
    public static List<UserPokemon> createUserListSortedByMaxCPTDAndMoveDPE(
            List<UserPokemon> userPokes) {
        // sort by descending value
        userPokes.sort(new SortByUserCPTDamageTMoveDPE());
        
        if (userPokes.size() <= 24) {
            return userPokes;
        } else {
            return userPokes.subList(0, 24);
        }
    }
    
    // creates list sorted by very large numerical value
    public static List<UserPokemon> createUserListSortedByTotalValues(List<UserPokemon> userPokes) {
        HashMap<BigDecimal, UserPokemon> hello = new HashMap<BigDecimal, UserPokemon>();
        ValueComparator bvc = new ValueComparator(hello);
        TreeMap<BigDecimal, UserPokemon> sorted = new TreeMap<BigDecimal, UserPokemon>(bvc);
        for (UserPokemon poke : userPokes) {
            Object[] values = Maths.calcHighestValueTimesStatProducts(poke);
            BigDecimal pokeValue = (BigDecimal) values[0];
            hello.put(pokeValue, poke);
        }
        sorted.putAll(hello);
        List<UserPokemon> sortedList = new ArrayList<>(sorted.values());
        if (sortedList.size() <= 24) {
            return sortedList;
        } else {
            return sortedList.subList(0, 24);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    // pokedex listing below
    
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

    return pokeList.subList(0, 24);
  }

  static List<Pokedex> createListSortedByMaxCP() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max cp
    pokeList.sort(new SortByMaxCP());

    // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);
    
      return trimmedPokeList.subList(0, 24);
  }
    
    public static List<Pokedex> createListSortedByMaxCPTimesDamageEffectiveness() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;
        
        // sort by descending max cp
    pokeList.sort(new SortByMaxCPDamageTotal());
        
        // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);
        
        return trimmedPokeList.subList(0, 24);
  }

  public static List<Pokedex> createListSortedByMaxCPTDAndMoveDPE() {
    final ArrayList<Pokedex> pokeList = pokesSuperEffective;

    // sort by descending max cp
    pokeList.sort(new SortByCPDTAndMoveDPE());
    
      // remove duplicates
    ArrayList<Pokedex> trimmedPokeList = removeDuplicates(pokeList);
    
      return trimmedPokeList.subList(0, 24);
  }
    
    public static int combineStats(Pokedex poke) {
    return (poke.getPokeAttack() + poke.getPokeDefense() + poke.getPokeStamina());
  }
    
    /////////////////////////////////////////////////////////////////////////////////////
  // misc below
    
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
