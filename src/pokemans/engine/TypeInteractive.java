/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.core.Type;
import pokemans.user.UserPokemon;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TypeInteractive {
    private static final List<UserPokemon> userPokes = new ArrayList<>(), topUserPokes = new ArrayList<>();
    private static final List<String> inputtedPokeNames = new ArrayList<>();
    private static Scanner in;
    private static boolean isPoke = false;
    
    static public void main(String[] args) {
        String wantContinue;
        //do {
        askForFirstEntry();
        
        if (!isPoke) {
            askForSecondType();
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        // calculates type effectivenesses. adds them to list
        FetchBasedOnTypes.againstTypes(FetchBasedOnTypes.userTypes);
        
        System.out.print("\nWhat would you like to see?:\nSuper-Effective types? (t) or Pokemon? (p) or both? (b): ");
        
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        //in = new Scanner(System.in);
        //String userInput = in.nextLine().toUpperCase();
        
        
        String userInput = "T";
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        FetchBasedOnStats.createListOfSuperEffectivePokes();
        //FetchBasedOnStats.createListOfSuperEffectiveUserPokes(userPokes);
        
        topUserPokes.addAll(FetchBasedOnStats.createUserListSortedByTotalValues(userPokes));
        
        printToUseAgainstMessage();
        
        boolean quitLoop = false;
        //do {
        switch (userInput) {
            case "X" -> System.exit(0);
            case "T" -> {
                //displayUsefulTypes();
                quitLoop = true;
            }
            case "P" -> {
                displayUserPokesJustByCP();
                //displayUsefulPokesByMaxCP();
                quitLoop = true;
            }
            case "C" -> {
                displayUserPokesByCpTimesDamageEffectiveness();
                //displayUsefulPokesByCpTimesDamageEffectiveness();
                quitLoop = true;
            }
            case "A" -> {
                displayUsefulTypes();
                //displayUsefulPokesByMaxCP();
                //displayUsefulPokesByCpTimesDamage();
                //displayPokesByCPTDAndMove();
                //displayUserPokesByCPTDAndMove();
                displayUserPokesByTotalValues();
                quitLoop = true;
            }
            default -> {
                System.out.print("Not a valid entry, please try again: ");
                userInput = in.nextLine().toUpperCase();
                quitLoop = false;
            }
        }
        
        BattlleCalculations.calculateUserAttackersVsOpponents(topUserPokes.get(0), inputtedPokeNames);
    
        // } while (!quitLoop);
    
        //      System.out.print("\nWould you like to try again? y/n: ");
        //      wantContinue = in.nextLine().toUpperCase();
    
        //  } while (wantContinue.equals("Y"));
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // get user input
    
    private static void askForFirstEntry() {
        FetchBasedOnTypes.userTypes.clear();
        List<String> userInputOpponents = new ArrayList<>();
        List<Type> userTypes = new ArrayList<>();
        
        // collect user input
        System.out.print("\n\nPlease enter a poke name or type (x to exit): ");
        
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        userInputOpponents.add("CLEFABLE");
        userInputOpponents.add("GIRATINA_ORIGIN");
        userInputOpponents.add("CATERPIE");
        //String userInput1 = "POKEMON_TYPE_ROCK";
        //String userInput1 = collectUserInputTypeAsString(in);
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        
        boolean exitLoop = false;
        while (!exitLoop) {
            
            // convert userInput1 to Type
            //Type userType = Type.stringToType(userInput1);
            //Type userType = Type.stringToType(userInputOpponents.get(0));
            for (String inputs : userInputOpponents) {
                userTypes.add(Type.stringToType(inputs));
                
                if (userInputOpponents.get(0).endsWith("TYPE_X")) {
                    System.exit(0);
                } else if (Pokedex.getAllPokeNames().contains(inputs)) {
                    
                    Pokedex userPoke = Pokedex.getPokeFromString(inputs);
                    assert userPoke != null;
                    inputtedPokeNames.add(userPoke.getPokeName());
                    FetchBasedOnTypes.userTypes.addAll(Arrays.asList(userPoke.getType()));
                    isPoke = true;
                    if (inputs.equals(userInputOpponents.get(userInputOpponents.size() - 1))) {
                        exitLoop = true;
                    }
                    
                } else if (!inputs.equals(null)) {
                    
                    FetchBasedOnTypes.userTypes.add(Type.stringToType(inputs));
                    exitLoop = true;
                    
                } else {
                    System.out.print("\n  *Not a valid entry!* Please try again: ");
                    //userType = collectUserInputTypeAsType(in);
                    //userInput = collectUserInputTypeAsString(in);
                    userInputOpponents.add(collectUserInputTypeAsString(in));
                }
            }
        }
    }
    
    private static void askForSecondType() {
        // collect user input
        System.out.print("\nSecond type: (n if none, x to exit): ");
        
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        String userInput = "POKEMON_TYPE_ICE";
        //String userInput = collectUserInputTypeAsString(in);
        
        boolean quitLoop = false;
        do {
            // convert userInput to Type
            Type userType = Type.stringToType(userInput);
            if (userInput.endsWith("TYPE_N")) {
                break;
            } else if (userInput.endsWith("TYPE_X")) {
                System.exit(0);
            } else if (FetchBasedOnTypes.userTypes.contains(userType)) {
                System.out.print("\n*Type already entered!* Please try again: ");
                userInput = collectUserInputTypeAsString(in);
                quitLoop = false;
            } else if (userType == null) {
                System.out.print("\n*Type not valid!* Please try again: ");
                userInput = collectUserInputTypeAsString(in);
                quitLoop = false;
            } else {
                quitLoop = true;
            }
        } while (!quitLoop);
    
        if (!userInput.endsWith("TYPE_N")) {
            FetchBasedOnTypes.userTypes.add(Type.stringToType(userInput));
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // user pokes
    
    // ranked by CP
    private static void displayUserPokesJustByCP() {
        System.out.println("\n\n\tTop 24 pokes\n(sorted by CP):\n");
        
        for (UserPokemon userPoke : FetchBasedOnStats.createListOfAllUserPokesSortedByCP(userPokes)) {
            System.out.println(String.format("%22s %12s", userPoke.getUserPokeName(), "cp" + userPoke.getUserPokeCP()));
        }
    }
    
    // ranked by CP * type effectiveness
    private static void displayUserPokesByCpTimesDamageEffectiveness() {
        System.out.println("\n\tTop 24 pokes sorted by:\n( CP * type effectiveness):\n");
        
        for (UserPokemon poke : FetchBasedOnStats.createUserListSortedByMaxCPTimesDamageEffectiveness(userPokes)) {
            System.out.println(String.format("%16s %10s %10s", poke.getUserPokeName(), poke.getUserPokeNickName(), Maths.calculateUserCPTimesDamage(poke).setScale(0, RoundingMode.DOWN)));
        }
    }
    
    // ranked by CP * type effectiveness * best charge move DPE
    private static void displayUserPokesByCPTDAndMove() {
        System.out.println("\n\n\tTop 24 pokes sorted by:\n(CP * type effectiveness * best charge move DPE):\n");
    
        System.out.println(String.format("%16s %5s %14s %14s %13s", "Name", "CP", "Charge Move", "Calc Value", "Nick Name"));
        for (UserPokemon poke : FetchBasedOnStats.createUserListSortedByMaxCPTDAndMoveDPE(userPokes)) {
            Object[] values = Maths.calculateUserPokeHighestMoveValue(poke);
            System.out.println(String.format("%16s %5s %14s %14s %13s", poke.getUserPokeName(), poke.getUserPokeCP(), values[1],
                    values[0], poke.getUserPokeNickName()));
        }
    }
    
    // ranked by CP * type effectiveness * best charge move DPE * fastMove DPT*EPT
    private static void displayUserPokesByTotalValues() {
        
        
        System.out.println("\n\n\tTop 24 pokes sorted by:\n(CP * statProduct * effectiveness * best chargeMove DPE * (fastMove DPT*EPT)):\n");
        
        System.out.println(String.format("%15s %5s %14s %16s %13s", "Name", "CP", "Charge Move", "Calc Value", "Nick Name"));
        for (UserPokemon poke : topUserPokes) {
            Object[] values = Maths.calcHighestValueTimesStatProducts(poke);
            
            System.out.println(String.format("%15s %5s %14s %16s %13s", poke.getUserPokeName(), poke.getUserPokeCP(), values[1],
                    values[0], poke.getUserPokeNickName()));
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // pokedex
    
    // ranked by Max CP
    private static void displayUsefulPokesByMaxCP() {
        System.out.println("\n\n\tTop 24 pokes\n(sorted by Max CP):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCP()) {
            System.out.println(String.format("%22s %12s", poke.getPokeName(), "cp" + Maths.calculateMaxCP(poke)));
        }
    }
    
    // ranked by Max CP * type effectiveness
    private static void displayUsefulPokesByCpTimesDamageEffectiveness() {
        System.out.println("\n\n\tTop 24 pokes sorted by:\n(Max CP * type effectiveness):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPTimesDamageEffectiveness()) {
            System.out.println(String.format("%21s %12s", poke.getPokeName(), Maths.calculateCPTimesDamageForPoke(poke)));
        }
    }
    
    // ranked by Max CP * type effectiveness * best charge move DPE
    private static void displayPokesByCPTDAndMove() {
        System.out.println("\n\n\tTop 24 pokes sorted by:\n(Max CP * type effectiveness * best charge move DPE):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPTDAndMoveDPE()) {
            Object[] values = Maths.getHighestMoveValues(poke);
            System.out.println(String.format("%21s %14s %12s", poke.getPokeName(), values[1],
                    values[0]));
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // functional
    
    static public void createLocalListOfUserPokesFromCSV(List<UserPokemon> pokes) {
        userPokes.addAll(pokes);
    }
    
    private static String collectUserInputTypeAsString(Scanner in) {
        in = new Scanner(System.in);
        return convertUserInputStringToFullTypeName(in.nextLine().toUpperCase());
    }
    
    private static Type collectUserInputTypeAsType(Scanner in) {
        in = new Scanner(System.in);
        return Type.stringToType(convertUserInputStringToFullTypeName(in.nextLine().toUpperCase()));
    }
    
    private static void displayUsefulTypes() {
        
        // print results
        System.out.println("\nSuper Effective Damage Against: ");
        for (int row = 0; row < FetchBasedOnTypes.typesSuperEffective.size(); row++) {
            
            // print table format
            System.out.println(String.format("%22s %12sx", FetchBasedOnTypes.typesSuperEffective.get(row),
                    FetchBasedOnTypes.damageSuperEffective.get(row)));
        }
        
        
        System.out.println("\nNot Very Effective Damage Against: ");
        
        
        for (int row = 0; row < FetchBasedOnTypes.typesNotEffective.size(); row++) {
            System.out.println(String.format("%22s %12sx", FetchBasedOnTypes.typesNotEffective.get(row),
                    FetchBasedOnTypes.damageNotEffective.get(row)));
        }
    }
    
    private static String convertUserInputStringToFullTypeName(String userInput) {
        userInput = "POKEMON_TYPE_" + userInput;
        return userInput;
    }
    
    private static void printToUseAgainstMessage() {
        if (isPoke) {
            System.out.print("\n\n--------------------------------------------------\n\t" +
                    "What to use against: ");
            for (String thisName : inputtedPokeNames) {
                System.out.print(thisName);
                if (!thisName.equals(inputtedPokeNames.get(inputtedPokeNames.size() - 1))) {
                    System.out.print(" & ");
                } else {
                    System.out.print("\n\tTyping: ");
                }
            }
            //System.out.println(FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes));
        } else {
            System.out.print("\n\n--------------------------------------------------\n\t" +
                    "What to use against\n\t" +
                    "Type(s): ");
        }
        System.out.println(FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes));
    }
}
