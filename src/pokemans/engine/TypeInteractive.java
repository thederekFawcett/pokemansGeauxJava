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
    private static final List<UserPokemon> userPokes = new ArrayList<>();
    private static Scanner in;
    private static boolean isPoke = false;
    private static String pokeName;
    
    static public void hello(List<UserPokemon> pokes) {
        userPokes.addAll(pokes);
    }
    
    static public void main(String[] args) {
        String wantContinue;
        //do {
        askForFirstType();
        
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
        
        
        String userInput = "C";
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        FetchBasedOnStats.createListOfSuperEffectivePokes();
        //FetchBasedOnStats.createListOfSuperEffectiveUserPokes(userPokes);
        
        printToUseAgainst();
        
        boolean quitLoop = false;
        //do {
        switch (userInput) {
            case "X" -> System.exit(0);
            case "T" -> {
                displayUsefulTypes();
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
    
                //FetchBasedOnTypes.checkPokeWeaknesses(Pokedex.MACHAMP);
    
                //displayUsefulPokesByMaxCP();
                //displayUsefulPokesByCpTimesDamage();
                displayPokesByCPTDAndMove();
                quitLoop = true;
            }
            default -> {
                System.out.print("Not a valid entry, please try again: ");
                userInput = in.nextLine().toUpperCase();
                quitLoop = false;
            }
        }
        
        
        // } while (!quitLoop);
        
        //      System.out.print("\nWould you like to try again? y/n: ");
        //      wantContinue = in.nextLine().toUpperCase();
        
        //  } while (wantContinue.equals("Y"));
    }
    
    private static void askForFirstType() {
        FetchBasedOnTypes.userTypes.clear();
        
        // collect user input
        System.out.print("\n\nPlease enter a poke name or type (x to exit): ");
        
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        String userInput = "GENGAR";
        //String userInput = "POKEMON_TYPE_ROCK";
        //String userInput = collectUserInputTypeAsString(in);
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        
        boolean exitLoop = false;
        while (!exitLoop) {
            
            // convert userInput to Type
            Type userType = Type.stringToType(userInput);
            
            if (userInput.endsWith("TYPE_X")) {
                System.exit(0);
            } else if (Pokedex.getAllPokeNames().contains(userInput)) {
                
                Pokedex userPoke = Pokedex.getPokeFromString(userInput);
                assert userPoke != null;
                pokeName = userPoke.getPokeName();
                FetchBasedOnTypes.userTypes.addAll(Arrays.asList(userPoke.getType()));
                isPoke = true;
                exitLoop = true;
                
            } else if (!userType.equals(null)) {
                
                FetchBasedOnTypes.userTypes.add(userType);
                exitLoop = true;
                
            } else {
                
                System.out.print("\n  *Not a valid entry!* Please try again: ");
                //userType = collectUserInputTypeAsType(in);
                userInput = collectUserInputTypeAsString(in);
                
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
    
    private static void displayUserPokesJustByCP() {
        System.out.println("\n\n\tTop pokes\n(sorted by CP):\n");
        
        for (UserPokemon userPoke : FetchBasedOnStats.createListOfAllUserPokesSortedByCP(userPokes)) {
            System.out.println(String.format("%22s %12s", userPoke.getUserPokeName(), "cp" + userPoke.getUserPokeCP()));
        }
    }
    
    private static void displayUsefulPokesByMaxCP() {
        System.out.println("\n\n\tTop 20 pokes\n(sorted by Max CP):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCP()) {
            System.out.println(String.format("%22s %12s", poke.getPokeName(), "cp" + Maths.calculateMaxCP(poke)));
        }
    }
    
    private static void displayUsefulPokesByCpTimesDamageEffectiveness() {
        System.out.println("\n\n\tTop 20 pokes\n(sorted by (Max CP * type effectiveness)):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPTimesDamageEffectiveness()) {
            System.out.println(String.format("%21s %12s", poke.getPokeName(), Maths.calculateCPTimesDamageForPoke(poke)));
        }
    }
    
    private static void displayUserPokesByCpTimesDamageEffectiveness() {
        System.out.println("\n\tTop 20 pokes\n(sorted by (Max CP * type effectiveness)):\n");
        
        for (UserPokemon poke : FetchBasedOnStats.createUserListSortedByMaxCPTimesDamageEffectiveness(userPokes)) {
            System.out.println(String.format("%21s %12s", poke.getUserPokeName(), Maths.calculateUserCPTimesDamage(poke).setScale(0, RoundingMode.DOWN)));
        }
    }
    
    private static void displayPokesByCPTDAndMove() {
        System.out.println("\n\n\tTop 20 pokes\n(sorted by (Max CP * type effectiveness * best charge move DPE))):\n");
        
        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPTDAndMoveDPE()) {
            System.out.println(String.format("%21s %14s %12s", poke.getPokeName(), Maths.getHighestValueMoveName(poke),
                    Maths.calculateCPTDAndMove(poke)));
        }
    }
    
    private static String collectUserInputTypeAsString(Scanner in) {
        in = new Scanner(System.in);
        return convertUserInputStringToFullTypeName(in.nextLine().toUpperCase());
    }
    
    private static Type collectUserInputTypeAsType(Scanner in) {
        in = new Scanner(System.in);
        return Type.stringToType(convertUserInputStringToFullTypeName(in.nextLine().toUpperCase()));
    }
    
    private static String convertUserInputStringToFullTypeName(String userInput) {
        userInput = "POKEMON_TYPE_" + userInput;
        return userInput;
    }
    
    private static void printToUseAgainst() {
        if (isPoke) {
            System.out.print("\n\n----------------------------------------\n\t" +
                    "What to use against\n\t" +
                    "Name: " + pokeName + "\n\tTyping: ");
        } else {
            System.out.print("\n\n----------------------------------------\n\t" +
                    "What to use against\n\t" +
                    "Type(s): ");
        }
        System.out.println(FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes));
    }
}
