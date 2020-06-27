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

  // calculate useful CP values
  public static BigDecimal calculateCPTimesDamageOneType(Pokedex poke, Type type) {
    BigDecimal damage = FetchBasedOnTypes.typeEffectivenessCalculator(type);
    return calculateMaxCP(poke).multiply(damage);
  }

  // calculate (max CP * type effectiveness) * moveDPE
  public static BigDecimal calculateCPTDAndMove(Pokedex poke) {

    BigDecimal pokeWithMoveValue = null;
    BigDecimal highestValue = new BigDecimal("0");

    ArrayList<MovesCinematic> thisPokesMovesCinematic = new ArrayList<>();
    Collections.addAll(thisPokesMovesCinematic, poke.getAllCinematicMoves());

    for (MovesCinematic move : thisPokesMovesCinematic) {
      if (poke.getType().length == 1) {
        if (move.getMoveType().equals(poke.getType1())) {
          // calculate with STAB
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          // calculate with no STAB
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
        }

      } else {
        if (move.getMoveType().equals(poke.getType1())) {
          // calculate with STAB
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else if (move.getMoveType().equals(poke.getType2())) {
          // calculate with STAB
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType2());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          // calculate with no STAB
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
        }
      }

      assert pokeWithMoveValue != null;
      if (pokeWithMoveValue.compareTo(highestValue) > 0) {
        highestValue = pokeWithMoveValue;
      }
    }

    return highestValue;
  }

  // get moveName based on: calculate (max CP * type effectiveness) * moveDPE
  public static String getHighestValueMoveName(Pokedex poke) {
    BigDecimal pokeWithMoveValue = null;
    BigDecimal highestValue = new BigDecimal("0");
    String moveName = null;

    ArrayList<MovesCinematic> thisPokesMovesCinematic = new ArrayList<>();
    Collections.addAll(thisPokesMovesCinematic, poke.getAllCinematicMoves());

    for (MovesCinematic move : thisPokesMovesCinematic) {
      if (poke.getType().length == 1) {
        if (move.getMoveType().equals(poke.getType1())) {
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
        }

      } else {
        if (move.getMoveType().equals(poke.getType1())) {
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else if (move.getMoveType().equals(poke.getType2())) {
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType2());
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (BigDecimal.valueOf(move.getPvpDPE())
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          BigDecimal pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, poke.getType1());
          pokeWithMoveValue =
                  pokeUsefulnessValue.multiply(BigDecimal.valueOf(move.getPvpDPE())).round(mc);
        }
      }

      assert pokeWithMoveValue != null;
      if (pokeWithMoveValue.compareTo(highestValue) > 0) {
        highestValue = pokeWithMoveValue;
        moveName = move.getMoveName();
      }
    }

    return moveName;
  }
}
