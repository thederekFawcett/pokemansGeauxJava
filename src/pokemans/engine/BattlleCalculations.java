package pokemans.engine;

import pokemans.core.MovesCinematic;
import pokemans.core.MovesFast;
import pokemans.core.Pokedex;
import pokemans.user.UserPokemon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattlleCalculations {
  
  public static void calculateUserAttackersVsOpponents(
          UserPokemon attackingPoke, List<String> opponentNames) {
    List<Pokedex> userOpponents = new ArrayList<>();
    MovesFast attackerFastMove = attackingPoke.getUserMoveFast();
    List<MovesCinematic> attackerChargeMoveList = new ArrayList<>(attackingPoke.getUserMovesCinematic());
    
    for (String name : opponentNames) {
      userOpponents.add(Pokedex.getPokeFromString(name));
    }
    
    System.out.println(
            "\nAAAA "
                    + attackingPoke.getUserPokeName()
                    + " using: "
                    + attackerFastMove.toString() + " & " + attackerChargeMoveList.get(0).toString());
    
    for (Pokedex defendingPoke : userOpponents) {
      List<MovesFast> defenderFastMove = new ArrayList<MovesFast>(Arrays.asList(defendingPoke.getAllFastMoves()));
      List<MovesCinematic> defenderChargeMoveList = new ArrayList<MovesCinematic>(Arrays.asList(defendingPoke.getAllCinematicMoves()));
      
      System.out.println(
              "\nDDDD "
                      + defendingPoke.getPokeName()
                      + " using: "
                      + defenderFastMove.get(0).toString() + " & " + defenderChargeMoveList.get(0).toString());
      
      int opponentHealth =
              Maths.getActualStamina(attackingPoke, defendingPoke, new BigDecimal("40"));
      int attackerHealth = attackingPoke.getUserPokeHealth().intValue();
      
      int turnCounter = 0,
              attackerFastMoveCounter = 0, attackerChargeMoveCounter = 0,
              defenderFastMoveCounter = 0, defenderChargeMoveCounter = 0;
      
      int attackerFastMoveDamage = 0, attackerChargeMoveDamage = 0, attackerEnergyBuilt = 0;
      int defenderFastMoveDamage = 0, defenderChargeMoveDamage = 0, defenderEnergyBuilt = 0;
      
      System.out.println(
              "\n\n\tDDDD **" + defendingPoke.getPokeName() + " starting health: " + opponentHealth + "**\n");
      
      do {
        turnCounter++;
        boolean chargeMoveUsed = false;
        
        if (attackerEnergyBuilt > (attackerChargeMoveList.get(0).getPvpEnergy() * -1) || defenderEnergyBuilt > (defenderChargeMoveList.get(0).getPvpEnergy() * -1)) {
          if (attackerEnergyBuilt > (attackerChargeMoveList.get(0).getPvpEnergy() * -1) &&
                  (Maths.getActualAttack(attackingPoke, defendingPoke, null)
                          .compareTo(Maths.getActualAttack(attackingPoke, defendingPoke, new BigDecimal("40")))) > 0) {
            attackerChargeMoveDamage =
                    Maths.calculateDamageForOneChargeMove(
                            attackingPoke, defendingPoke, attackerChargeMoveList.get(0));
            
            attackerEnergyBuilt += (int) attackerChargeMoveList.get(0).getPvpEnergy();
            opponentHealth -= attackerChargeMoveDamage;
            attackerChargeMoveCounter++;
            System.out.println("**** AAAA Attacker chargeMove at Turn: " + turnCounter + ", # of charMoves:" + attackerChargeMoveCounter + "\tdamage:" + attackerChargeMoveDamage + "\tenergy Built:" + attackerEnergyBuilt + " ****");
            System.out.println("**** AAAA Defender health after charge attack: " + opponentHealth);
            chargeMoveUsed = true;
          } else if (defenderEnergyBuilt > (defenderChargeMoveList.get(0).getPvpEnergy() * -1) &&
                  (Maths.getActualAttack(attackingPoke, defendingPoke, null)
                          .compareTo(Maths.getActualAttack(attackingPoke, defendingPoke, new BigDecimal("40")))) < 0) {
            defenderChargeMoveDamage =
                    Maths.calculateDamageForOneChargeMove(
                            attackingPoke, defendingPoke, defenderChargeMoveList.get(0));
            
            defenderEnergyBuilt += (int) defenderChargeMoveList.get(0).getPvpEnergy();
            attackerHealth -= defenderChargeMoveDamage;
            defenderChargeMoveCounter++;
            System.out.println(
                    "**** DDDD Defender chargeMove at Turn: \" + turnCounter + \", # of charMoves:"
                            + attackerChargeMoveCounter
                            + "\tdamage:"
                            + attackerChargeMoveDamage
                            + "\tenergy Built:"
                            + attackerEnergyBuilt
                            + " ****");
            System.out.println("**** DDDD Attacker health after charge attack: " + opponentHealth);
            chargeMoveUsed = true;
          }
        }
        
        if (turnCounter >= attackerFastMove.getPvpTurns() && !chargeMoveUsed) {
          attackerFastMoveDamage =
                  Maths.calculateDamageForOneFastMove(attackingPoke, defendingPoke, attackerFastMove);
          attackerEnergyBuilt += (int) attackerFastMove.getPvpEnergy();
          opponentHealth -= attackerFastMoveDamage;
          attackerFastMoveCounter++;
          
          System.out.println("\nAAAA !Attacker fastMove! at Turn: " + turnCounter);
          System.out.println("\tAAAA # of fastMoves:" + attackerFastMoveCounter + "\tdamage:" + attackerFastMoveDamage + "\tenergy Built:" + attackerEnergyBuilt);
          System.out.println("\tAAAA Defender health after fast attack: " + opponentHealth);
        }
        if (turnCounter >= defenderFastMove.get(0).getPvpTurns() && !chargeMoveUsed) {
          defenderFastMoveDamage =
                  Maths.calculateDamageForOneFastMove(attackingPoke, defendingPoke, defenderFastMove.get(0));
          defenderEnergyBuilt += (int) defenderFastMove.get(0).getPvpEnergy();
          attackerHealth -= defenderFastMoveDamage;
          defenderFastMoveCounter++;
          
          System.out.println("\nDDDD !Defender fastMove! at Turn: " + turnCounter);
          System.out.println("\tDDDD # of fastMoves:" + defenderChargeMoveCounter + "\tdamage:" + defenderFastMoveDamage + "\tenergy Built:" + defenderEnergyBuilt);
          System.out.println("\tDDDD Attacker health after fast attack: " + attackerHealth);
        }
        
        if (opponentHealth <= 0) {
          System.out.println("\n\t\tAAAA " + defendingPoke.getPokeName() + " is dead after " + attackerFastMoveCounter + " fastMoves & " + attackerChargeMoveCounter + " chargeMoves");
        }
        if (attackerHealth <= 0) {
          System.out.println("\n\t\tDDDD " + attackingPoke.getUserPokeName() + " is dead after " + defenderFastMoveCounter + " fastMoves & " + defenderChargeMoveCounter + " chargeMoves");
        }
      } while (opponentHealth > 0);
    }
  }
}
