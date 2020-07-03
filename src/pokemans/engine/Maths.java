/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.MovesCinematic;
import pokemans.core.MovesFast;
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
  private static final MathContext mc = new MathContext(14);
  static Pokedex dexPoke;
  
  /////////////////////////////////////////////////////////////////////////////////////
  // functional maths below
  
  // enter ACTUAL poke level, methods convert to CPM index level
  public static BigDecimal getActualAttack(UserPokemon poke, Pokedex dexPoke, BigDecimal level) {
    if (level == null) {
      int userLevel = ((poke.getUserPokeLevel()).multiply(new BigDecimal("2"))).intValue() - 2;
      return (((poke.getUserPokeAttackIV()).add(poke.getUserPokeAttackBase()))
              .multiply(CPMultiplier.levels[userLevel]))
              .round(mc);
    } else {
      int dexLevel = ((level.multiply(new BigDecimal("2"))).intValue()) - 2;
      return (((new BigDecimal("15")).add(new BigDecimal(String.valueOf(dexPoke.getPokeAttack()))))
              .multiply(CPMultiplier.levels[dexLevel]))
              .round(mc);
    }
  }
  
  public static BigDecimal getActualDefense(UserPokemon poke, Pokedex dexPoke, BigDecimal level) {
    if (level == null) {
      int userLevel = ((poke.getUserPokeLevel()).multiply(new BigDecimal("2"))).intValue() - 2;
      return (((poke.getUserPokeDefenseIV()).add(poke.getUserPokeDefenseBase()))
              .multiply(CPMultiplier.levels[userLevel]))
              .round(mc);
    } else {
      int dexLevel = ((level.multiply(new BigDecimal("2"))).intValue()) - 2;
      return (((new BigDecimal("15")).add(new BigDecimal(String.valueOf(dexPoke.getPokeDefense()))))
              .multiply(CPMultiplier.levels[dexLevel]))
              .round(mc);
    }
  }
  
  public static int getActualStamina(UserPokemon poke, Pokedex dexPoke, BigDecimal level) {
    if (level == null) {
      int userLevel = ((poke.getUserPokeLevel()).multiply(new BigDecimal("2"))).intValue() - 2;
      return (((poke.getUserPokeStaminaIV().add(poke.getUserPokeStaminaBase()))
              .multiply(CPMultiplier.levels[userLevel]))
              .setScale(0, RoundingMode.FLOOR))
              .intValue();
    } else {
      int dexLevel = ((level.multiply(new BigDecimal("2"))).intValue()) - 2;
      return (((new BigDecimal("15").add(new BigDecimal(String.valueOf(dexPoke.getPokeStamina()))))
              .multiply(CPMultiplier.levels[dexLevel]))
              .setScale(0, RoundingMode.FLOOR))
              .intValue();
    }
  }
  
  public static int calculateDamageForOneFastMove(
          UserPokemon attackingPoke, Pokedex defendingPoke, MovesFast fastMove) {
    BigDecimal halfTimesPower = (new BigDecimal("0.5")).multiply(new BigDecimal(fastMove.getPvpPower().toString()));
    BigDecimal atkDIVDefense = (getActualAttack(attackingPoke, defendingPoke, null)
            .divide(getActualDefense(attackingPoke, defendingPoke, new BigDecimal("40")), mc));
    BigDecimal multipliers = FetchBasedOnTypes.stabCalculator(attackingPoke.getUserPokeType(), fastMove, null)
            .multiply(CombatMultipliers.fastAttackBonusMultiplier);
    return (((halfTimesPower.multiply(atkDIVDefense).multiply(multipliers))
            .setScale(0, RoundingMode.DOWN))
            .add(new BigDecimal("1.0")))
            .intValue();
  }
  
  public static int calculateDamageForOneChargeMove(
          UserPokemon attackingPoke, Pokedex defendingPoke, MovesCinematic chargeMove) {
    BigDecimal halfTimesPower = (new BigDecimal("0.5")).multiply(new BigDecimal(chargeMove.getPvpPower().toString()));
    BigDecimal atkDIVDefense = (getActualAttack(attackingPoke, defendingPoke, null)
            .divide(getActualDefense(attackingPoke, defendingPoke, new BigDecimal("40")), mc));
    BigDecimal multipliers = FetchBasedOnTypes.stabCalculator(attackingPoke.getUserPokeType(), null, chargeMove)
            .multiply(CombatMultipliers.chargeAttackBonusMultiplier);
    
    return (((halfTimesPower.multiply(atkDIVDefense).multiply(multipliers))
            .setScale(0, RoundingMode.DOWN))
            .add(new BigDecimal("1.0")))
            .intValue();
  }
  
  /////////////////////////////////////////////////////////////////////////////////////
  // user poke maths below
  
  public static BigDecimal calculateUserPokeCP(UserPokemon poke) {
    
    BigDecimal attackBase = poke.getUserPokeAttackBase();
    BigDecimal defenseBase = poke.getUserPokeDefenseBase();
    BigDecimal staminaBase = poke.getUserPokeStaminaBase();
    BigDecimal attackIV = poke.getUserPokeAttackIV();
    BigDecimal defenseIV = poke.getUserPokeDefenseIV();
    BigDecimal staminaIV = poke.getUserPokeStaminaIV();
    
    // @formatter:off
    BigDecimal cp =
            (((attackBase.add(attackIV))
                    .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                    .multiply(((staminaBase.add(staminaIV)).sqrt(mc))))
                    .multiply((poke.getUserPokeLevel()).pow(2))
                    .divide(new BigDecimal("10"), RoundingMode.FLOOR))
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
    BigDecimal pokeWithMoveValue = null, usefulnessValue;
    Object[] values = new Object[2];
    values[0] = new BigDecimal("0.0");
    values[1] = null;

    List<MovesCinematic> userPokeMovesCinematic = new ArrayList<>(poke.getUserMovesCinematic());

    if (!userPokeMovesCinematic.isEmpty()) {
      for (MovesCinematic move : userPokeMovesCinematic) {
        for (Type type : poke.getUserPokeType()) {
          usefulnessValue =
                  (poke.getUserPokeCP())
                          .multiply(FetchBasedOnTypes.typeEffectivenessCalculator(move.getMoveType()))
                          .multiply(new BigDecimal(move.getPvpDPE().toString()));
          if (move.getMoveType().equals(type)) {
            // calculate with STAB
            pokeWithMoveValue =
                    (usefulnessValue.multiply(BattleMultipliers.sameTypeAttackBonusMultiplier))
                            .round(mc);
          } else {
            // calculate with no STAB
            pokeWithMoveValue = usefulnessValue.round(mc);
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
  
  // get values (value & move name) based on: calculate (max CP * type effectiveness) * moveDPE
  public static Object[] calcHighestValueTimesStatProducts(UserPokemon poke) {
    Object[] chargeMoveValues = calculateUserPokeHighestMoveValue(poke),
            combinedValues = new Object[2];
    
    BigDecimal bestChargeMove = (BigDecimal) chargeMoveValues[0], bestFastMove;
    BigDecimal statProduct =
            getActualAttack(poke, dexPoke, null)
                    .multiply(getActualDefense(poke, dexPoke, null))
                    .multiply(new BigDecimal(String.valueOf(getActualStamina(poke, dexPoke, null))));
    
    combinedValues[0] = new BigDecimal("0.0");
    combinedValues[1] = chargeMoveValues[1];
    
    if (poke.getUserMoveFast() != null) {
      BigDecimal fastMoveDPTEPT = new BigDecimal(poke.getUserMoveFast().getPvpDPTEPT().toString());
      for (Type type : poke.getUserPokeType()) {
        if ((poke.getUserMoveFast().getMoveType()).equals(type)) {
          // calculate with STAB
          bestFastMove =
                  ((fastMoveDPTEPT.multiply(BattleMultipliers.sameTypeAttackBonusMultiplier))
                          .multiply(bestChargeMove))
                          .round(mc);
        } else {
          // no STAB
          bestFastMove = (fastMoveDPTEPT.multiply(bestChargeMove)).round(mc);
        }
        assert bestFastMove != null;
        if (bestFastMove.compareTo((BigDecimal) combinedValues[0]) > 0) {
          combinedValues[0] = (bestFastMove.multiply(statProduct)).round(mc);
        }
      }
    }
    return combinedValues;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////
  // pokedex maths below
  
  // calculate CP's
  public static BigDecimal calculateMaxCP(Pokedex poke) {
    return calculatePokedexPossibleCP(poke, CPMultiplier.levels[39]);
  }
  
  public static BigDecimal calculatePokedexPossibleCP(Pokedex poke, BigDecimal pokeLevelCPM) {
    
    BigDecimal attackBase = new BigDecimal(String.valueOf(poke.getPokeAttack()));
    BigDecimal defenseBase = new BigDecimal(String.valueOf(poke.getPokeDefense()));
    BigDecimal staminaBase = new BigDecimal(String.valueOf(poke.getPokeStamina()));
    final BigDecimal attackIV = new BigDecimal("15"),
            defenseIV = new BigDecimal("15"),
            staminaIV = new BigDecimal("15");
    
    // @formatter:off
    BigDecimal cp =
            (((attackBase.add(attackIV))
                    .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                    .multiply(((staminaBase.add(staminaIV)).sqrt(mc))))
                    .multiply(pokeLevelCPM.pow(2))
                    .divide(new BigDecimal("10"), RoundingMode.FLOOR))
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
        pokeUsefulnessValue = calculateCPTimesDamageOneType(poke, move.getMoveType());
        if (move.getMoveType().equals(type)) {
          // calculate with STAB
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(
                                  (new BigDecimal(String.valueOf(move.getPvpDPE()))
                                          .multiply(BattleMultipliers.sameTypeAttackBonusMultiplier)))
                          .round(mc);
        } else {
          pokeWithMoveValue =
                  pokeUsefulnessValue
                          .multiply(new BigDecimal(String.valueOf(move.getPvpDPE())))
                          .round(mc);
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
