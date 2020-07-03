/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission. */

// **This file is auto-generated**

package pokemans.engine;

import java.math.BigDecimal;

interface BattleMultipliers {
    
    BigDecimal attackServerInterval = new BigDecimal("5.0");
    BigDecimal bonusTimePerAllySeconds = new BigDecimal("10.0");
    BigDecimal dodgeDamageReductionPercent = new BigDecimal("0.75");
    BigDecimal dodgeDurationMs = new BigDecimal("500.0");
    BigDecimal enemyAttackInterval = new BigDecimal("1.5");
    BigDecimal energyDeltaPerHealthLost = new BigDecimal("0.5");
    BigDecimal maximumAttackersPerBattle = new BigDecimal("20.0");
    BigDecimal maximumEnergy = new BigDecimal("100.0");
    BigDecimal minimumPlayerLevel = new BigDecimal("5.0");
    BigDecimal minimumRaidPlayerLevel = new BigDecimal("5.0");
    BigDecimal purifiedPokemonAttackMultiplierVsShadow = new BigDecimal("1.0");
    BigDecimal retargetSeconds = new BigDecimal("0.5");
    BigDecimal roundDurationSeconds = new BigDecimal("99.0");
    BigDecimal sameTypeAttackBonusMultiplier = new BigDecimal("1.2");
    BigDecimal shadowPokemonAttackBonusMultiplier = new BigDecimal("1.2");
    BigDecimal shadowPokemonDefenseBonusMultiplier = new BigDecimal("0.8333333");
    BigDecimal swapDurationMs = new BigDecimal("1000.0");
}
