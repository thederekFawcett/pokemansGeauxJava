/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package pokemans.engine;

import pokemans.core.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FetchBasedOnTypes {
  // list of user-entered type(s)
  static final ArrayList<Type> userTypes = new ArrayList<>();

  // list of all effectiveness
  static final ArrayList<Type> allEffectiveTypes = new ArrayList<>();
  static final ArrayList<BigDecimal> allEffectiveDamages = new ArrayList<>();

  // lists of effective types
  static final ArrayList<Type> typesSuperEffective = new ArrayList<>();
  static final ArrayList<Type> typesNotEffective = new ArrayList<>();

  // lists of damage effectivenesses
  static final ArrayList<BigDecimal> damageSuperEffective = new ArrayList<>();
  static final ArrayList<BigDecimal> damageNotEffective = new ArrayList<>();

  static void againstTypes(ArrayList<Type> userTypes) {
    BigDecimal againstThis, one = new BigDecimal("1");
    typesSuperEffective.clear();
    typesNotEffective.clear();
    damageSuperEffective.clear();
    damageNotEffective.clear();
    Type[] types = Type.values();
    String typeName1, typeName2, endingName1, endingName2 = "";

    // isolate name of Type
    if (userTypes.size() == 1) {
      typeName1 = userTypes.get(0).getTYPENAME();
      endingName1 = typeName1.substring(13);
    } else {
      typeName1 = userTypes.get(0).getTYPENAME();
      endingName1 = typeName1.substring(13);
      typeName2 = userTypes.get(1).getTYPENAME();
      endingName2 = typeName2.substring(13);
    }

    // check against each type
    for (Type type : types) {
      // check each "get type" method for each type
      for (Method m : type.getClass().getMethods()) {
        if (!endingName1.equals("")
                && m.getName().startsWith("get")
                && m.getName().endsWith(endingName1)
                && m.getParameterTypes().length == 0) {
          try {
            final Object damageFactor = m.invoke(type);
            againstThis = (BigDecimal) damageFactor;

            allEffectiveTypes.add(type);
            allEffectiveDamages.add(againstThis);

            if ((againstThis.compareTo(one)) > 0) {
              typesSuperEffective.add(type);
              damageSuperEffective.add(againstThis);
            } else if (againstThis.compareTo(one) < 0) {
              typesNotEffective.add(type);
              damageNotEffective.add(againstThis);
            }
          } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
        }
        if (!endingName2.equals("")
                && m.getName().startsWith("get")
                && m.getName().endsWith(endingName2)
                && m.getParameterTypes().length == 0) {
          try {
            final Object damageFactor = m.invoke(type);
            againstThis = (BigDecimal) damageFactor;

            allEffectiveTypes.add(type);
            allEffectiveDamages.add(againstThis);

            if ((againstThis.compareTo(one)) > 0) {
              typesSuperEffective.add(type);
              damageSuperEffective.add(againstThis);
            } else if (againstThis.compareTo(one) < 0) {
              typesNotEffective.add(type);
              damageNotEffective.add(againstThis);
            }
          } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
    }

    // check if duplicates exists in super effective, AND not effective and do maths
    if (typesSuperEffective.size() > typesNotEffective.size()) {
      // bigger list first two, smaller list second two
      calculateMathsForDuplicatesOpposites(
              typesNotEffective, damageNotEffective, typesSuperEffective, damageSuperEffective);
    } else {
      calculateMathsForDuplicatesOpposites(
              typesSuperEffective, damageSuperEffective, typesNotEffective, damageNotEffective);
    }

    // check if duplicates exists in super effective, OR not effective and do maths
    calculateMathsForDuplicatesSameSide(typesNotEffective, damageNotEffective);
    calculateMathsForDuplicatesSameSide(typesSuperEffective, damageSuperEffective);
  }

  public static void calculateMathsForDuplicatesOpposites(
          ArrayList<Type> smallerListType,
          ArrayList<BigDecimal> smallerListDamage,
          ArrayList<Type> biggerListType,
          ArrayList<BigDecimal> biggerListDamage) {
    int bigCount, smallCount;
    for (smallCount = 0; smallCount < smallerListType.size(); smallCount++) {
      if (biggerListType.contains(smallerListType.get(smallCount))) {
        // remove from damage lists first, because index is from type lists
        bigCount = biggerListType.indexOf(smallerListType.get(smallCount));
        if (smallerListDamage
                .get(smallCount)
                .multiply(biggerListDamage.get(bigCount))
                .compareTo(new BigDecimal("1"))
                == 0) {
          biggerListDamage.remove(biggerListType.indexOf(smallerListType.get(smallCount)));
          biggerListType.remove(smallerListType.get(smallCount));
        } else {
          biggerListDamage.set(
                  bigCount, smallerListDamage.get(smallCount).multiply(biggerListDamage.get(bigCount)));
          biggerListDamage.remove(bigCount + 1);
          biggerListType.remove(bigCount + 1);
        }
        smallerListDamage.remove(smallCount);
        smallerListType.remove(smallCount);
        smallCount--;
      }
    }
  }

  public static void calculateMathsForDuplicatesSameSide(
          ArrayList<Type> listType, ArrayList<BigDecimal> listDamage) {
    for (int smallCount = 0; smallCount < listType.size(); smallCount++) {
      if (smallCount < (listType.size() - 1)) {
        if (listType.get(smallCount).compareTo(listType.get(smallCount + 1)) == 0) {
          listDamage.set(
                  smallCount, listDamage.get(smallCount).multiply(listDamage.get(smallCount + 1)));
          listDamage.remove(smallCount + 1);
          listType.remove(smallCount + 1);
          smallCount--;
        }
      }
    }
  }

  public static String listToString(ArrayList<Type> type) {
    return type.toString().replace("[", "").replace("]", "");
  }

  static BigDecimal typeEffectivenessCalculator(Type type) {
    BigDecimal bug = new BigDecimal("1.0"),
            dark = new BigDecimal("1.0"),
            dragon = new BigDecimal("1.0"),
            electric = new BigDecimal("1.0"),
            fairy = new BigDecimal("1.0"),
            fighting = new BigDecimal("1.0"),
            fire = new BigDecimal("1.0"),
            flying = new BigDecimal("1.0"),
            ghost = new BigDecimal("1.0"),
            grass = new BigDecimal("1.0"),
            ground = new BigDecimal("1.0"),
            ice = new BigDecimal("1.0"),
            normal = new BigDecimal("1.0"),
            poison = new BigDecimal("1.0"),
            psychic = new BigDecimal("1.0"),
            rock = new BigDecimal("1.0"),
            steel = new BigDecimal("1.0"),
            water = new BigDecimal("1.0");

    for (int i = 0; i < typesSuperEffective.size(); i++) {
      BigDecimal damage = damageSuperEffective.get(i);
      switch (typesSuperEffective.get(i).toString()) {
        case "POKEMON_TYPE_BUG":
          bug = damage;
          break;
        case "POKEMON_TYPE_DARK":
          dark = damage;
          break;
        case "POKEMON_TYPE_DRAGON":
          dragon = damage;
          break;
        case "POKEMON_TYPE_ELECTRIC":
          electric = damage;
          break;
        case "POKEMON_TYPE_FAIRY":
          fairy = damage;
          break;
        case "POKEMON_TYPE_FIGHTING":
          fighting = damage;
          break;
        case "POKEMON_TYPE_FIRE":
          fire = damage;
          break;
        case "POKEMON_TYPE_FLYING":
          flying = damage;
          break;
        case "POKEMON_TYPE_GHOST":
          ghost = damage;
          break;
        case "POKEMON_TYPE_GRASS":
          grass = damage;
          break;
        case "POKEMON_TYPE_GROUND":
          ground = damage;
          break;
        case "POKEMON_TYPE_ICE":
          ice = damage;
          break;
        case "POKEMON_TYPE_NORMAL":
          normal = damage;
          break;
        case "POKEMON_TYPE_POISON":
          poison = damage;
          break;
        case "POKEMON_TYPE_PSYCHIC":
          psychic = damage;
          break;
        case "POKEMON_TYPE_ROCK":
          rock = damage;
          break;
        case "POKEMON_TYPE_STEEL":
          steel = damage;
          break;
        case "POKEMON_TYPE_WATER":
          water = damage;
          break;
        default:
          break;
      }
    }

    BigDecimal damageMultiplier = null;
    switch (type.toString()) {
      case "POKEMON_TYPE_BUG":
        damageMultiplier = bug;
        break;
      case "POKEMON_TYPE_DARK":
        damageMultiplier = dark;
        break;
      case "POKEMON_TYPE_DRAGON":
        damageMultiplier = dragon;
        break;
      case "POKEMON_TYPE_ELECTRIC":
        damageMultiplier = electric;
        break;
      case "POKEMON_TYPE_FAIRY":
        damageMultiplier = fairy;
        break;
      case "POKEMON_TYPE_FIGHTING":
        damageMultiplier = fighting;
        break;
      case "POKEMON_TYPE_FIRE":
        damageMultiplier = fire;
        break;
      case "POKEMON_TYPE_FLYING":
        damageMultiplier = flying;
        break;
      case "POKEMON_TYPE_GHOST":
        damageMultiplier = ghost;
        break;
      case "POKEMON_TYPE_GRASS":
        damageMultiplier = grass;
        break;
      case "POKEMON_TYPE_GROUND":
        damageMultiplier = ground;
        break;
      case "POKEMON_TYPE_ICE":
        damageMultiplier = ice;
        break;
      case "POKEMON_TYPE_NORMAL":
        damageMultiplier = normal;
        break;
      case "POKEMON_TYPE_POISON":
        damageMultiplier = poison;
        break;
      case "POKEMON_TYPE_PSYCHIC":
        damageMultiplier = psychic;
        break;
      case "POKEMON_TYPE_ROCK":
        damageMultiplier = rock;
        break;
      case "POKEMON_TYPE_STEEL":
        damageMultiplier = steel;
        break;
      case "POKEMON_TYPE_WATER":
        damageMultiplier = water;
        break;
      default:
        break;
    }
    return damageMultiplier;
  }
}
