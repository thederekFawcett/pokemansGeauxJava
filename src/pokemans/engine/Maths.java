/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package pokemans.engine;

import pokemans.core.MovesCinematic;
import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Maths {
  private static final MathContext mc = new MathContext(9);
  private static final BigDecimal attackIV = new BigDecimal("15"),
          defenseIV = new BigDecimal("15"),
          staminaIV = new BigDecimal("15");
  private static final BigDecimal cpMultiplier = new BigDecimal("0");

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // calculate actual stats
  public static BigDecimal calculateActualAttack(Pokedex poke, BigDecimal pokeLevelCPM) {
    return ((BigDecimal.valueOf(poke.getPokeAttack()).add(attackIV)).multiply(pokeLevelCPM));
  }

  public static BigDecimal calculateActualDefense(Pokedex poke, BigDecimal pokeLevelCPM) {
    return ((BigDecimal.valueOf(poke.getPokeDefense()).add(attackIV)).multiply(pokeLevelCPM));
  }

  public static BigDecimal calculateActualStamina(Pokedex poke, BigDecimal pokeLevelCPM) {
    return ((BigDecimal.valueOf(poke.getPokeStamina()).add(attackIV)).multiply(pokeLevelCPM));
  }

  // calculate CP's
  public static BigDecimal calculateMaxCP(Pokedex poke) {
    return calculateCP(poke, CPMultiplier.levels[39]);
  }

  public static BigDecimal calculateCP(Pokedex poke, BigDecimal pokeLevelCPM) {

    BigDecimal attackBase = BigDecimal.valueOf(poke.getPokeAttack());
    BigDecimal defenseBase = BigDecimal.valueOf(poke.getPokeDefense());
    BigDecimal staminaBase = BigDecimal.valueOf(poke.getPokeStamina());

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
              .multiply((FetchBasedOnTypes.typeEffectivenessCalculator(poke.getType1()))
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

  public BigDecimal halfLevelCPM(int lowerLevel, int higherLevel) {
    BigDecimal two = new BigDecimal("2");
    BigDecimal lowerLevelCPM = CPMultiplier.levels[lowerLevel];
    BigDecimal higherLevelCPM = CPMultiplier.levels[higherLevel];

    return (lowerLevelCPM.add(higherLevelCPM)).divide(two, mc);
  }
}
