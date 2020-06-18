/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FetchBasedOnTypes {
    static final ArrayList<Type>
            userTypes = new ArrayList<>();
    static final ArrayList<Type> typesSuperEffective = new ArrayList<>();
    static final ArrayList<Type> typesNotEffective = new ArrayList<>();
    static final ArrayList<BigDecimal>
            damageSuperEffective = new ArrayList<>();
    static final ArrayList<BigDecimal> damageNotEffective = new ArrayList<>();

    static void againstTypes(ArrayList<Type> userTypes) {
        BigDecimal againstThis = new BigDecimal("0.0"), one = new BigDecimal("1");
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
                if (!endingName1.equals("") && m.getName().startsWith("get") &&
                        m.getName().endsWith(endingName1) && m.getParameterTypes().length == 0) {
                    try {
                        final Object r = m.invoke(type);
                        againstThis = (BigDecimal) r;
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
                if (!endingName2.equals("") && m.getName().startsWith("get") &&
                        m.getName().endsWith(endingName2) && m.getParameterTypes().length == 0) {
                    try {
                        final Object r = m.invoke(type);
                        againstThis = (BigDecimal) r;
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
            calculateMathsForDuplicatesOpposites(typesNotEffective, damageNotEffective, typesSuperEffective, damageSuperEffective);
        } else {
            calculateMathsForDuplicatesOpposites(typesSuperEffective, damageSuperEffective, typesNotEffective, damageNotEffective);
        }

        // check if duplicates exists in super effective, OR not effective and do maths
        calculateMathsForDuplicatesSameSide(typesNotEffective, damageNotEffective);
        calculateMathsForDuplicatesSameSide(typesSuperEffective, damageSuperEffective);
    }

    public static void calculateMathsForDuplicatesOpposites
            (ArrayList<Type> smallerListType, ArrayList<BigDecimal> smallerListDamage,
             ArrayList<Type> biggerListType, ArrayList<BigDecimal> biggerListDamage) {
        int bigCount, smallCount;
        for (smallCount = 0; smallCount < smallerListType.size(); smallCount++) {
            if (biggerListType.contains(smallerListType.get(smallCount))) {
                // remove from damage lists first, because index is from type lists
                bigCount = biggerListType.indexOf(smallerListType.get(smallCount));
                if (smallerListDamage.get(smallCount).multiply(biggerListDamage.get(bigCount)).compareTo(new BigDecimal("1")) == 0) {
                    biggerListDamage.remove(biggerListType.indexOf(smallerListType.get(smallCount)));
                    biggerListType.remove(smallerListType.get(smallCount));
                } else {
                    biggerListDamage.set(bigCount, smallerListDamage.get(smallCount).multiply(biggerListDamage.get(bigCount)));
                    biggerListDamage.remove(bigCount + 1);
                    biggerListType.remove(bigCount + 1);
                }
                smallerListDamage.remove(smallCount);
                smallerListType.remove(smallCount);
                smallCount--;
            }
        }
    }

    public static void calculateMathsForDuplicatesSameSide
            (ArrayList<Type> listType, ArrayList<BigDecimal> listDamage) {
        for (int smallCount = 0; smallCount < listType.size(); smallCount++) {
            if (smallCount < (listType.size() - 1)) {
                if (listType.get(smallCount).compareTo(listType.get(smallCount + 1)) == 0) {
                    listDamage.set(smallCount, listDamage.get(smallCount).multiply(listDamage.get(smallCount + 1)));
                    listDamage.remove(smallCount + 1);
                    listType.remove(smallCount + 1);
                    smallCount--;
                }
            }
        }
    }

    public static void sendToFetchBasedOnStats() {
        FetchBasedOnStats.againstTypes(typesSuperEffective, damageSuperEffective);
    }

    static public String listToString(ArrayList<Type> type) {
        return type.toString().replace("[", "").replace("]", "");
    }
}
