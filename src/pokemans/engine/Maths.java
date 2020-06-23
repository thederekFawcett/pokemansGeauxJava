/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Maths {
    static final MathContext mc = new MathContext(9);
    private static final BigDecimal
            attackIV = new BigDecimal("15"), defenseIV = new BigDecimal("15"), staminaIV = new BigDecimal("15");
    private static final BigDecimal cpMultiplier = new BigDecimal("0");
    /*private static BigDecimal
            actualAttack = new BigDecimal("0"), actualDefense = new BigDecimal("0"), actualStamina = new BigDecimal("0");*/

    /////////////////////////////////////////////////

    public static BigDecimal calculateMaxCP(Pokedex poke) {
        return calculateCP(poke, CPMultiplier.levels[39]);
    }

    public static BigDecimal calculateCP(Pokedex poke, BigDecimal pokeLevel) {

        BigDecimal attackBase = BigDecimal.valueOf(poke.getPokeAttack());
        BigDecimal defenseBase = BigDecimal.valueOf(poke.getPokeDefense());
        BigDecimal staminaBase = BigDecimal.valueOf(poke.getPokeStamina());

// @formatter:off
        BigDecimal cp =
                (
                        (
                                (attackBase.add(attackIV))
                                        .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                                        .multiply(((staminaBase.add(staminaIV)).sqrt(mc)))//.setScale(0, RoundingMode.DOWN))
                        )
                                .multiply(pokeLevel.pow(2))
                                .divide(BigDecimal.valueOf(10), RoundingMode.FLOOR)
                ).setScale(0, RoundingMode.DOWN);
// @formatter:on

        return cp;
    }

    public static BigDecimal calculateCPTimesDamage(Pokedex poke) {
        BigDecimal damage;
        if (poke.getType().length == 1) {
            damage = FetchBasedOnStats.typeSwitchStatement(poke.getType1());
        } else {
            if (FetchBasedOnStats.typeSwitchStatement(poke.getType1()).compareTo
                    (FetchBasedOnStats.typeSwitchStatement(poke.getType2())) > 0) {
                damage = FetchBasedOnStats.typeSwitchStatement(poke.getType1());
            } else {
                damage = FetchBasedOnStats.typeSwitchStatement(poke.getType2());
            }
        }
        return calculateMaxCP(poke).multiply(damage);
    }

    public BigDecimal halfLevelCPM(int lowerLevel, int higherLevel) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal lowerLevelCPM = CPMultiplier.levels[lowerLevel];
        BigDecimal higherLevelCPM = CPMultiplier.levels[higherLevel];

        return (lowerLevelCPM.add(higherLevelCPM)).divide(two, mc);
    }
}