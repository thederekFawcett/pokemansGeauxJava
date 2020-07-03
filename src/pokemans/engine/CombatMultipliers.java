/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission. */

// **This file is auto-generated**

package pokemans.engine;

import java.math.BigDecimal;

interface CombatMultipliers {
    
    BigDecimal blockedEffectiveFlyoutDurationTurns = new BigDecimal("5.0");
    BigDecimal changePokemonDurationSeconds = new BigDecimal("12.0");
    BigDecimal chargeAttackBonusMultiplier = new BigDecimal("1.3");
    BigDecimal chargeScoreBase = new BigDecimal("0.25");
    BigDecimal chargeScoreExcellent = new BigDecimal("1.0");
    BigDecimal chargeScoreGreat = new BigDecimal("0.75");
    BigDecimal chargeScoreNice = new BigDecimal("0.5");
    BigDecimal defenderMinigameMultiplier = new BigDecimal("1.0");
    BigDecimal defenseBonusMultiplier = new BigDecimal("1.0");
    BigDecimal fastAttackBonusMultiplier = new BigDecimal("1.3");
    BigDecimal maxEnergy = new BigDecimal("100.0");
    BigDecimal minigameBonusBaseMultiplier = new BigDecimal("1.0E-4");
    BigDecimal minigameBonusVariableMultiplier = new BigDecimal("1.0");
    BigDecimal minigameDurationSeconds = new BigDecimal("7.0");
    BigDecimal minigameSubmitScoreDurationSeconds = new BigDecimal("10.0");
    BigDecimal normalEffectiveFlyoutDurationTurns = new BigDecimal("5.0");
    BigDecimal notVeryEffectiveFlyoutDurationTurns = new BigDecimal("5.0");
    BigDecimal purifiedPokemonAttackMultiplierVsShadow = new BigDecimal("1.0");
    BigDecimal quickSwapCooldownDurationSeconds = new BigDecimal("60.0");
    BigDecimal roundDurationSeconds = new BigDecimal("270.0");
    BigDecimal sameTypeAttackBonusMultiplier = new BigDecimal("1.2");
    BigDecimal shadowPokemonAttackBonusMultiplier = new BigDecimal("1.2");
    BigDecimal shadowPokemonDefenseBonusMultiplier = new BigDecimal("0.8333333");
    BigDecimal superEffectiveFlyoutDurationTurns = new BigDecimal("5.0");
    BigDecimal turnDurationSeconds = new BigDecimal("0.5");
    BigDecimal[] attackBuffMultiplier = {new BigDecimal("0.5"), new BigDecimal("0.5714286"), new BigDecimal("0.6666667"), new BigDecimal("0.8"), new BigDecimal("1.0"), new BigDecimal("1.25"), new BigDecimal("1.5"), new BigDecimal("1.75"), new BigDecimal("2.0")};
    BigDecimal[] defenseBuffMultiplier = {new BigDecimal("0.5"), new BigDecimal("0.5714286"), new BigDecimal("0.6666667"), new BigDecimal("0.8"), new BigDecimal("1.0"), new BigDecimal("1.25"), new BigDecimal("1.5"), new BigDecimal("1.75"), new BigDecimal("2.0")};
    BigDecimal maximumStatStage = new BigDecimal("4.0");
    BigDecimal minimumStatStage = new BigDecimal("-4.0");
    
}