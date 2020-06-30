/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All Rights Reserved. No usage without permission.
 */

package pokemans.user;

import pokemans.core.MovesCinematic;
import pokemans.core.MovesFast;
import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserPokemon {

  // info from CSV
  private final String userPokeDexNum;
  private final String userPokeName;
  private final String userPokeNickName;
  private final BigDecimal userPokeLevel;
  private final BigDecimal userPokeCP;
    private final BigDecimal userPokeHealth;
    private final BigDecimal userPokeAttackIV;
    private final BigDecimal userPokeDefenseIV;
    private final BigDecimal userPokeStaminaIV;
    private final MovesFast userPokeFastMove;
    private final int userPokeAttackBase;
    private final int userPokeDefenseBase;
    private final int userPokeStaminaBase;
    private final Type[] userPokeType;
    // poke base-info
    private final Pokedex pokeBase;
    private List<MovesCinematic> userPokeChargeMove = new ArrayList<>();
    
    public UserPokemon(
            String userPokeDexNum,
            String userPokeName,
            String userPokeNickName,
            BigDecimal userPokeLevel,
            BigDecimal userPokeCP,
            BigDecimal userPokeHealth,
            BigDecimal userPokeAttackIV,
            BigDecimal userPokeDefenseIV,
            BigDecimal userPokeStaminaIV,
            MovesFast userPokeFastMove,
            List<MovesCinematic> userPokeChargeMove) {
        this.userPokeDexNum = userPokeDexNum;
        this.userPokeName = userPokeName;
        this.userPokeNickName = userPokeNickName;
        this.userPokeLevel = userPokeLevel;
        this.userPokeCP = userPokeCP;
        this.userPokeHealth = userPokeHealth;
        this.userPokeAttackIV = userPokeAttackIV;
        this.userPokeDefenseIV = userPokeDefenseIV;
        this.userPokeStaminaIV = userPokeStaminaIV;
        this.userPokeFastMove = userPokeFastMove;
        this.userPokeChargeMove = userPokeChargeMove;
        
        pokeBase = Pokedex.getPokeFromString(userPokeName);
        assert pokeBase != null;
        
        this.userPokeAttackBase = pokeBase.getPokeAttack();
        this.userPokeDefenseBase = pokeBase.getPokeDefense();
        this.userPokeStaminaBase = pokeBase.getPokeStamina();
        this.userPokeType = pokeBase.getType();
    }
    
    public String getUserPokeDexNum() {
    return userPokeDexNum;
  }
    
    public String getUserPokeName() {
    return userPokeName;
  }
    
    public String getUserPokeNickName() {
    return userPokeNickName;
  }
    
    public BigDecimal getUserPokeLevel() {
    return userPokeLevel;
  }
    
    public BigDecimal getUserPokeCP() {
    return userPokeCP;
  }
    
    public BigDecimal getUserPokeHealth() {
    return userPokeHealth;
  }
    
    public BigDecimal getUserPokeAttackIV() {
    return userPokeAttackIV;
  }
    
    public BigDecimal getUserPokeDefenseIV() {
        return userPokeDefenseIV;
    }
    
    public BigDecimal getUserPokeStaminaIV() {
        return userPokeStaminaIV;
    }
    
    public BigDecimal getStaminaIVIV() {
        return userPokeStaminaIV;
    }
    
    public MovesFast getUserMoveFast() {
        return userPokeFastMove;
    }
    
    public List<MovesCinematic> getUserMovesCinematic() {
        return userPokeChargeMove;
    }
    
    public Pokedex getPokeBase() {
        return pokeBase;
    }
    
    public int getUserPokeAttackBase() {
        return userPokeAttackBase;
    }
    
    public int getUserPokeDefenseBase() {
    return userPokeDefenseBase;
  }
    
    public int getUserPokeStaminaBase() {
    return userPokeStaminaBase;
  }
    
    public Type[] getUserPokeType() {
    return userPokeType;
  }
    
    public Type getUserPokeType1() {
    return userPokeType[0];
  }
    
    public Type getUserPokeType2() {
    return userPokeType[1];
  }
}
