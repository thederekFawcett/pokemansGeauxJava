/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.MovesCinematic;
import pokemans.core.Pokedex;
import pokemans.core.Type;
import pokemans.user.UserPokemon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maths {
  private static final MathContext mc = new MathContext(9);

  /////////////////////////////////////////////////////////////////////////////////////
  // user poke maths below

  public static BigDecimal calculateUserPokeCP(UserPokemon poke) {

    BigDecimal attackBase = BigDecimal.valueOf(poke.getUserPokeAttackBase());
    BigDecimal defenseBase = BigDecimal.valueOf(poke.getUserPokeDefenseBase());
    BigDecimal staminaBase = BigDecimal.valueOf(poke.getUserPokeStaminaBase());
    BigDecimal attackIV = poke.getUserPokeAttackIV();
    BigDecimal defenseIV = poke.getUserPokeDefenseIV();
    BigDecimal staminaIV = poke.getUserPokeStaminaIV();

    // @formatter:off
    BigDecimal cp =
            (((attackBase.add(attackIV))
                    .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                    .multiply(((staminaBase.add(staminaIV)).sqrt(mc))))
                    .multiply((poke.getUserPokeLevel()).pow(2))
                    .divide(BigDecimal.valueOf(10), RoundingMode.FLOOR))
                    .setScale(0, RoundingMode.DOWN);
    // @formatter:on
    return cp;
  }

  // calculate useful CP values
  public static BigDecimal calculateUserCPTimesDamage(UserPokemon poke) {
    if (poke.getUserPokeType().length == 1) {
      return poke.getUserPokeCP()
              .multiply(FetchBasedOnTypes.typeEffectivenessCalculator(poke.getUserPokeType1()));
    } else {
      return poke.getUserPokeCP()
              .multiply(
                      (FetchBasedOnTypes.typeEffectivenessCalculator(poke.getUserPokeType1()))
                              .max(FetchBasedOnTypes.typeEffectivenessCalculator(poke.getUserPokeType2())));
    }
  }
  
  // get values (value & move name) based on: calculate (max CP * type effectiveness) * moveDPE
  public static Object[] calculateUserPokeHighestMoveValue(UserPokemon poke) {
    BigDecimal pokeWithMoveValue = null, pokeUsefulnessValue;
    Object[] values = new Object[2];
    values[0] = new BigDecimal("0.0");
    values[1] = null;
    
    List<MovesCinematic> userPokeMovesCinematic = new ArrayList<>(poke.getUserMovesCinematic());
    
    if (!userPokeMovesCinematic.isEmpty()) {
      for (MovesCinematic move : userPokeMovesCinematic) {
        for (Type type : poke.getUserPokeType()) {
          if (move.getMoveType().equals(type)) {
            // calculate with STAB
            pokeUsefulnessValue =
                    (poke.getUserPokeCP())
                            .multiply(FetchBasedOnTypes.typeEffectivenessCalculator(type));
            pokeWithMoveValue =
                    pokeUsefulnessValue
                            .multiply(
                                    (BigDecimal.valueOf(move.getPvpDPE())
                                            .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                            .round(mc);
          } else {
            // calculate with no STAB
            pokeUsefulnessValue =
                    (poke.getUserPokeCP())
                            .multiply(FetchBasedOnTypes.typeEffectivenessCalculator(type));
            pokeWithMoveValue =
                    pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
          }
          assert pokeWithMoveValue != null;
          if (pokeWithMoveValue.compareTo((BigDecimal) values[0]) > 0) {
            values[0] = pokeWithMoveValue;
            values[1] = move.getMoveName();
          }
        }
      }
    }
    return values;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////
  // pokedex maths below
  
  // calculate CP's
  public static BigDecimal calculateMaxCP(Pokedex poke) {
    return calculatePokedexPossibleCP(poke, CPMultiplier.levels[39]);
  }
  
  public static BigDecimal calculatePokedexPossibleCP(Pokedex poke, BigDecimal pokeLevelCPM) {
    
    BigDecimal attackBase = BigDecimal.valueOf(poke.getPokeAttack());
    BigDecimal defenseBase = BigDecimal.valueOf(poke.getPokeDefense());
    BigDecimal staminaBase = BigDecimal.valueOf(poke.getPokeStamina());
    final BigDecimal attackIV = new BigDecimal("15"),
            defenseIV = new BigDecimal("15"),
            staminaIV = new BigDecimal("15");
    
    // @formatter:off
    BigDecimal cp =
            (((attackBase.add(attackIV))
                    .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                    .multiply(((staminaBase.add(staminaIV)).sqrt(mc))))
                    .multiply(pokeLevelCPM.pow(2))
                    .divide(BigDecimal.valueOf(10), RoundingMode.FLOOR))
                    .setScale(0, RoundingMode.DOWN);
    // @formatter:on
    return cp;
  }

  // calculate useful CP values
  public static BigDecimal calculateCPTimesDamageForPoke(Pokedex poke) {
    if (poke.getType().length == 1) {
      return calculateMaxCP(poke)
              .multiply(FetchBasedOnTypes.typeEffectivenessCalculator(poke.getType1()));
    } else {
      return calculateMaxCP(poke)
              .multiply(
                      (FetchBasedOnTypes.typeEffectivenessCalculator(poke.getType1()))
                              .max(FetchBasedOnTypes.typeEffectivenessCalculator(poke.getType2())));
    }
  }
  
  // calculate pokedex max CP * type effectiveness for one Type
  public static BigDecimal calculateCPTimesDamageOneType(Pokedex poke, Type type) {
    BigDecimal damage = FetchBasedOnTypes.typeEffectivenessCalculator(type);
    return calculateMaxCP(poke).multiply(damage);
  }
  
  // get moveName based on: calculate (max CP * type effectiveness) * moveDPE
  public static Object[] getHighestMoveValues(Pokedex poke) {
    BigDecimal pokeWithMoveValue = null, pokeUsefulnessValue;
    Object[] values = new Object[2];
    values[0] = new BigDecimal("0.0");
    values[1] = null;
    
    ArrayList<MovesCinematic> thisPokesMovesCinematic = new ArrayList<>();
    Collections.addAll(thisPokesMovesCinematic, poke.getAllCinematicMoves());
    
    for (MovesCinematic move : thisPokesMovesCinematic) {
      for (Type type : poke.getType()) {
        if (move.getMoveType().equals(type)) {
          // calculate with STAB
          pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, type);
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, type);
          pokeWithMoveValue =
                  pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
        }
        assert pokeWithMoveValue != null;
        if (pokeWithMoveValue.compareTo((BigDecimal) values[0]) > 0) {
          values[0] = pokeWithMoveValue;
          values[1] = move.getMoveName();
        }
      }
    }
    return values;
  }
}
