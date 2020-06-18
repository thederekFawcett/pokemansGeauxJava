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
    private static final BigDecimal
            cp = new BigDecimal("0");
    private static final BigDecimal cpMultiplier = new BigDecimal("0");
    private static final BigDecimal effectiveCPMultiplier = new BigDecimal("0");
    private static BigDecimal
            attackBase = new BigDecimal("0");
    private static BigDecimal defenseBase = new BigDecimal("0");
    private static BigDecimal staminaBase = new BigDecimal("0");
    private static BigDecimal
            actualAttack = new BigDecimal("0"), actualDefense = new BigDecimal("0"), actualStamina = new BigDecimal("0");

    public static void cpCalculation() {


        actualAttack = (attackBase.add(attackIV)).multiply(cpMultiplier);
        actualDefense = (defenseBase.add(defenseIV)).multiply(cpMultiplier);
        actualStamina = (staminaBase.add(staminaIV)).multiply(cpMultiplier);

    }

    public static BigDecimal calculateMaxCP(Pokedex poke) {

        // 0 = level 1, 45 = level 40
        BigDecimal thisLevel = CPMultiplier.levels[39];

        attackBase = BigDecimal.valueOf(poke.getPokeAttack());
        defenseBase = BigDecimal.valueOf(poke.getPokeDefense());
        staminaBase = BigDecimal.valueOf(poke.getPokeStamina());

        BigDecimal maxCP =
                (
                        (
                                (attackBase.add(attackIV))
                                        .multiply((defenseBase.add(defenseIV)).sqrt(mc))
                                        .multiply(((staminaBase.add(staminaIV)).sqrt(mc)))//.setScale(0, RoundingMode.DOWN))
                        )
                                .multiply(thisLevel.pow(2))
                                .divide(BigDecimal.valueOf(10), RoundingMode.FLOOR)).setScale(0, RoundingMode.DOWN);

        System.out.println(poke.getPokeName() + ": max CP: " + maxCP);

        return maxCP;
    }

    public BigDecimal halfLevelCPM(int lowerLevel, int higherLevel) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal lowerLevelCPM = CPMultiplier.levels[lowerLevel];
        BigDecimal higherLevelCPM = CPMultiplier.levels[higherLevel];

        return (lowerLevelCPM.add(higherLevelCPM)).divide(two, mc);
    }
}
