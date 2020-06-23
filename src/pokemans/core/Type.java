/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved.No usage without permission.
 */

 // **This file is auto-generated** 

package pokemans.core;

import java.math.BigDecimal;

public enum Type {
	// Is "this much" effective against: 
	// 0 = Normal	1 = Fighting	2 = Flying	3 = Poison	4 = Ground
	// 5 = Rock	 6 = Bug	7 = Ghost	 8 = Steel	9 = Fire
	// 10 = Water	11 = Grass	12 = Electric	13 = Psychic	14 = Ice
	//15 = Dragon	16 = Dark	17 = Fairy

	//								0      1      2      3    4
	POKEMON_TYPE_BUG("POKEMON_TYPE_BUG", new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("0.625")),
	POKEMON_TYPE_DARK("POKEMON_TYPE_DARK", new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625")),
	POKEMON_TYPE_DRAGON("POKEMON_TYPE_DRAGON", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.390625")),
	POKEMON_TYPE_ELECTRIC("POKEMON_TYPE_ELECTRIC", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.390625"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_FAIRY("POKEMON_TYPE_FAIRY", new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("1.6"), new BigDecimal("1.0")),
	POKEMON_TYPE_FIGHTING("POKEMON_TYPE_FIGHTING", new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("0.390625"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("0.625")),
	POKEMON_TYPE_FIRE("POKEMON_TYPE_FIRE", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("0.625"),
			new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_FLYING("POKEMON_TYPE_FLYING", new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_GHOST("POKEMON_TYPE_GHOST", new BigDecimal("0.390625"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0")),
	POKEMON_TYPE_GRASS("POKEMON_TYPE_GRASS", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("1.6"),
			new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_GROUND("POKEMON_TYPE_GROUND", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.390625"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_ICE("POKEMON_TYPE_ICE", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"),
			new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_NORMAL("POKEMON_TYPE_NORMAL", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("0.390625"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_POISON("POKEMON_TYPE_POISON", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.390625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6")),
	POKEMON_TYPE_PSYCHIC("POKEMON_TYPE_PSYCHIC", new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"),
			new BigDecimal("1.0"), new BigDecimal("0.390625"), new BigDecimal("1.0")),
	POKEMON_TYPE_ROCK("POKEMON_TYPE_ROCK", new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.625"),
			new BigDecimal("1.0"), new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0")),
	POKEMON_TYPE_STEEL("POKEMON_TYPE_STEEL", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("0.625"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6")),
	POKEMON_TYPE_WATER("POKEMON_TYPE_WATER", new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("1.6"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.6"),
			new BigDecimal("0.625"), new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"), new BigDecimal("1.0"),
			new BigDecimal("0.625"), new BigDecimal("1.0"), new BigDecimal("1.0"));

	private final BigDecimal NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG, GHOST, STEEL, FIRE,
			WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FAIRY;
	private final String TYPENAME;

	Type(String TYPENAME, BigDecimal NORMAL, BigDecimal FIGHTING, BigDecimal FLYING, BigDecimal POISON,
		 BigDecimal GROUND, BigDecimal ROCK, BigDecimal BUG, BigDecimal GHOST, BigDecimal STEEL, BigDecimal FIRE,
		 BigDecimal WATER, BigDecimal GRASS, BigDecimal ELECTRIC, BigDecimal PSYCHIC, BigDecimal ICE,
		 BigDecimal DRAGON, BigDecimal DARK, BigDecimal FAIRY) {
		this.TYPENAME = TYPENAME;
		this.NORMAL = NORMAL;
		this.FIGHTING = FIGHTING;
		this.FLYING = FLYING;
		this.POISON = POISON;
		this.GROUND = GROUND;
		this.ROCK = ROCK;
		this.BUG = BUG;
		this.GHOST = GHOST;
		this.STEEL = STEEL;
		this.FIRE = FIRE;
		this.WATER = WATER;
		this.GRASS = GRASS;
		this.ELECTRIC = ELECTRIC;
		this.PSYCHIC = PSYCHIC;
		this.ICE = ICE;
		this.DRAGON = DRAGON;
		this.DARK = DARK;
		this.FAIRY = FAIRY;
	}

	public String getTYPENAME() { return TYPENAME; }
	public BigDecimal getNORMAL() { return NORMAL; }
	public BigDecimal getFIGHTING() { return FIGHTING; }
	public BigDecimal getFLYING() { return FLYING; }
	public BigDecimal getPOISON() { return POISON; }
	public BigDecimal getGROUND() { return GROUND; }
	public BigDecimal getROCK() { return ROCK; }
	public BigDecimal getBUG() { return BUG; }
	public BigDecimal getGHOST() { return GHOST; }
	public BigDecimal getSTEEL() { return STEEL; }
	public BigDecimal getFIRE() { return FIRE; }
	public BigDecimal getWATER() { return WATER; }
	public BigDecimal getGRASS() { return GRASS; }
	public BigDecimal getELECTRIC() { return ELECTRIC; }
	public BigDecimal getPSYCHIC() { return PSYCHIC; }
	public BigDecimal getICE() { return ICE; }
	public BigDecimal getDRAGON() { return DRAGON; }
	public BigDecimal getDARK() { return DARK; }
	public BigDecimal getFAIRY() { return FAIRY; }


	public static Type stringToType(String inputType) {
		Type thisType = null;
		switch (inputType) {
			case "POKEMON_TYPE_BUG" -> thisType = Type.POKEMON_TYPE_BUG;
			case "POKEMON_TYPE_DARK" -> thisType = Type.POKEMON_TYPE_DARK;
			case "POKEMON_TYPE_DRAGON" -> thisType = Type.POKEMON_TYPE_DRAGON;
			case "POKEMON_TYPE_ELECTRIC" -> thisType = Type.POKEMON_TYPE_ELECTRIC;
			case "POKEMON_TYPE_FAIRY" -> thisType = Type.POKEMON_TYPE_FAIRY;
			case "POKEMON_TYPE_FIGHTING" -> thisType = Type.POKEMON_TYPE_FIGHTING;
			case "POKEMON_TYPE_FIRE" -> thisType = Type.POKEMON_TYPE_FIRE;
			case "POKEMON_TYPE_FLYING" -> thisType = Type.POKEMON_TYPE_FLYING;
			case "POKEMON_TYPE_GHOST" -> thisType = Type.POKEMON_TYPE_GHOST;
			case "POKEMON_TYPE_GRASS" -> thisType = Type.POKEMON_TYPE_GRASS;
			case "POKEMON_TYPE_GROUND" -> thisType = Type.POKEMON_TYPE_GROUND;
			case "POKEMON_TYPE_ICE" -> thisType = Type.POKEMON_TYPE_ICE;
			case "POKEMON_TYPE_NORMAL" -> thisType = Type.POKEMON_TYPE_NORMAL;
			case "POKEMON_TYPE_POISON" -> thisType = Type.POKEMON_TYPE_POISON;
			case "POKEMON_TYPE_PSYCHIC" -> thisType = Type.POKEMON_TYPE_PSYCHIC;
			case "POKEMON_TYPE_ROCK" -> thisType = Type.POKEMON_TYPE_ROCK;
			case "POKEMON_TYPE_STEEL" -> thisType = Type.POKEMON_TYPE_STEEL;
			case "POKEMON_TYPE_WATER" -> thisType = Type.POKEMON_TYPE_WATER;
			default -> { return thisType;
			}
		}
		return thisType;
	}
}