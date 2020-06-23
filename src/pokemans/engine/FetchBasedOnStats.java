/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FetchBasedOnStats {

    static final ArrayList<Pokedex> pokesSuperEffective = new ArrayList<>();
    static final ArrayList<Pokedex> resultOfCPAndDamage = new ArrayList<>();

    static void createListOfSuperEffectivePokes() {
        // iterate through each poke
        for (Pokedex poke : Pokedex.values()) {
            if (!Collections.disjoint(FetchBasedOnTypes.typesSuperEffective, Arrays.asList(poke.getType()))) {
                pokesSuperEffective.add(poke);
            }
        }
    }

    static List<Pokedex> sortCombinedStats() {
        final ArrayList<Pokedex> combinedStatList = pokesSuperEffective;

        // sort by descending max combined stats
        combinedStatList.sort(new SortByCombinedStats());

        return combinedStatList.subList(0, 20);
    }

    static List<Pokedex> sortedMaxCP() {
        final ArrayList<Pokedex> maxCPList = pokesSuperEffective;

        // sort by descending max cp
        maxCPList.sort(new SortByMaxCP());

        // remove duplicates
        ArrayList<Pokedex> trimmedMaxCPList = removeDuplicates(maxCPList);

        return trimmedMaxCPList.subList(0, 20);
    }

    public static List<Pokedex> sortedMaxCPAndDamage() {
        final ArrayList<Pokedex> maxTotalList = pokesSuperEffective;

        // sort by descending max cp
        maxTotalList.sort(new SortByMaxCPDamageTotal());

        // remove duplicates
        ArrayList<Pokedex> trimmedMaxTotalList = removeDuplicates(maxTotalList);

        return trimmedMaxTotalList.subList(0, 20);
    }

    static public int combineStats(Pokedex poke) {
        return (poke.getPokeAttack() + poke.getPokeDefense() + poke.getPokeStamina());
    }

    static BigDecimal typeSwitchStatement(Type type) {
        BigDecimal
                bug = new BigDecimal("0.0"),
                dark = new BigDecimal("0.0"),
                dragon = new BigDecimal("0.0"),
                electric = new BigDecimal("0.0"),
                fairy = new BigDecimal("0.0"),
                fighting = new BigDecimal("0.0"),
                fire = new BigDecimal("0.0"),
                flying = new BigDecimal("0.0"),
                ghost = new BigDecimal("0.0"),
                grass = new BigDecimal("0.0"),
                ground = new BigDecimal("0.0"),
                ice = new BigDecimal("0.0"),
                normal = new BigDecimal("0.0"),
                poison = new BigDecimal("0.0"),
                psychic = new BigDecimal("0.0"),
                rock = new BigDecimal("0.0"),
                steel = new BigDecimal("0.0"),
                water = new BigDecimal("0.0");

        for (int i = 0; i < FetchBasedOnTypes.typesSuperEffective.size(); i++) {
            BigDecimal damage = FetchBasedOnTypes.damageSuperEffective.get(i);
            switch (FetchBasedOnTypes.typesSuperEffective.get(i).toString()) {
                case "POKEMON_TYPE_BUG":
                    bug = damage;
                    break;
                case "POKEMON_TYPE_DARK":
                    dark = damage;
                    break;
                case "POKEMON_TYPE_DRAGON":
                    dragon = damage;
                    break;
                case "POKEMON_TYPE_ELECTRIC":
                    electric = damage;
                    break;
                case "POKEMON_TYPE_FAIRY":
                    fairy = damage;
                    break;
                case "POKEMON_TYPE_FIGHTING":
                    fighting = damage;
                    break;
                case "POKEMON_TYPE_FIRE":
                    fire = damage;
                    break;
                case "POKEMON_TYPE_FLYING":
                    flying = damage;
                    break;
                case "POKEMON_TYPE_GHOST":
                    ghost = damage;
                    break;
                case "POKEMON_TYPE_GRASS":
                    grass = damage;
                    break;
                case "POKEMON_TYPE_GROUND":
                    ground = damage;
                    break;
                case "POKEMON_TYPE_ICE":
                    ice = damage;
                    break;
                case "POKEMON_TYPE_NORMAL":
                    normal = damage;
                    break;
                case "POKEMON_TYPE_POISON":
                    poison = damage;
                    break;
                case "POKEMON_TYPE_PSYCHIC":
                    psychic = damage;
                    break;
                case "POKEMON_TYPE_ROCK":
                    rock = damage;
                    break;
                case "POKEMON_TYPE_STEEL":
                    steel = damage;
                    break;
                case "POKEMON_TYPE_WATER":
                    water = damage;
                    break;
                default:
                    break;
            }
        }

        BigDecimal damageMultiplier = null;
        switch (type.toString()) {
            case "POKEMON_TYPE_BUG":
                damageMultiplier = bug;
                break;
            case "POKEMON_TYPE_DARK":
                damageMultiplier = dark;
                break;
            case "POKEMON_TYPE_DRAGON":
                damageMultiplier = dragon;
                break;
            case "POKEMON_TYPE_ELECTRIC":
                damageMultiplier = electric;
                break;
            case "POKEMON_TYPE_FAIRY":
                damageMultiplier = fairy;
                break;
            case "POKEMON_TYPE_FIGHTING":
                damageMultiplier = fighting;
                break;
            case "POKEMON_TYPE_FIRE":
                damageMultiplier = fire;
                break;
            case "POKEMON_TYPE_FLYING":
                damageMultiplier = flying;
                break;
            case "POKEMON_TYPE_GHOST":
                damageMultiplier = ghost;
                break;
            case "POKEMON_TYPE_GRASS":
                damageMultiplier = grass;
                break;
            case "POKEMON_TYPE_GROUND":
                damageMultiplier = ground;
                break;
            case "POKEMON_TYPE_ICE":
                damageMultiplier = ice;
                break;
            case "POKEMON_TYPE_NORMAL":
                damageMultiplier = normal;
                break;
            case "POKEMON_TYPE_POISON":
                damageMultiplier = poison;
                break;
            case "POKEMON_TYPE_PSYCHIC":
                damageMultiplier = psychic;
                break;
            case "POKEMON_TYPE_ROCK":
                damageMultiplier = rock;
                break;
            case "POKEMON_TYPE_STEEL":
                damageMultiplier = steel;
                break;
            case "POKEMON_TYPE_WATER":
                damageMultiplier = water;
                break;
            default:
                break;
        }
        return damageMultiplier;
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