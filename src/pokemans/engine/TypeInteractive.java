/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package pokemans.engine;

import pokemans.core.Pokedex;
import pokemans.core.Type;

import java.util.Scanner;

public class TypeInteractive {
    static Scanner in;

    public static void main(String[] args) {
        String wantContinue;
        //do {
        askForFirstType();

        askForSecondType();

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        // calculates type effectivenesses. adds them to list
        FetchBasedOnTypes.againstTypes(FetchBasedOnTypes.userTypes);

        System.out.print("\nWhat would you like to see?:\nSuper-Effective types? (t) or Pokemon? (p) or both? (b): ");


        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        //in = new Scanner(System.in);
        //String userInput = in.nextLine().toUpperCase();


        String userInput = "A";

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        FetchBasedOnStats.createListOfSuperEffectivePokes();

        boolean quitLoop = false;
        //do {
        switch (userInput) {
            case "X" -> System.exit(0);
            case "T" -> {
                displayUsefulTypes();
                quitLoop = true;
            }
            case "P" -> {
                displayUsefulPokesByMaxCP();
                quitLoop = true;
            }
            case "C" -> {
                displayUsefulPokesByCpTimesDamage();
                quitLoop = true;
            }
            case "A" -> {
                displayUsefulTypes();
                //displayUsefulPokesByMaxCP();
                displayUsefulPokesByCpTimesDamage();
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
        System.out.print("\n\nPlease enter a type (x to exit): ");


        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        String userInput = "POKEMON_TYPE_ROCK";
        //String userInput = collectUserInputTypeAsString(in);

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////


        // convert userInput to Type
        Type userType = Type.stringToType(userInput);

        if (userInput.endsWith("TYPE_X")) {
            System.exit(0);
        }
        while (userType == null) {
            System.out.print("\n*Type not valid!* Please try again: ");
            userType = collectUserInputTypeAsType(in);
        }

        FetchBasedOnTypes.userTypes.add(userType);
    }

    public static void askForSecondType() {
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

        System.out.println("\n\nType to use against " +
                FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes) + ":");

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

    private static void displayUsefulPokesByMaxCP() {
        System.out.println("\n\nTop 20 pokes to use against " +
                FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes) + "\n" +
                "\t(sorted by Max CP)\n");

        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCP()) {
            System.out.println(String.format("%22s %12s", poke.getPokeName(), "cp" + Maths.calculateMaxCP(poke)));
        }

    }

    private static void displayUsefulPokesByCpTimesDamage() {
        System.out.println("\n\nTop 20 pokes to use against " +
                FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes) + "\n" +
                "\t(sorted by Max CP * Type Effectiveness)\n");

        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPAndDamage()) {
            System.out.println(String.format("%21s %12s", poke.getPokeName(), Maths.calculateCPTimesDamageForPoke(poke)));
        }
    }

    private static void displayPokesByCPTDAndMove() {
        System.out.println("\n\nTop 20 pokes to use against " +
                FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes) + "\n" +
                "\t(sorted by (MaxCP * TypeEffectiveness) * ChargeMoveDPE)\n");

        for (Pokedex poke : FetchBasedOnStats.createListSortedByMaxCPTDAndMoveDPE()) {
            System.out.println(String.format("%21s %14s %12s", poke.getPokeName(), Maths.getHighestValueMoveName(poke),
                    Maths.calculateCPTDAndMove(poke)));
        }
    }

    public static String collectUserInputTypeAsString(Scanner in) {
        in = new Scanner(System.in);
        return convertStringToFullName(in.nextLine().toUpperCase());
    }

    public static Type collectUserInputTypeAsType(Scanner in) {
        in = new Scanner(System.in);
        return Type.stringToType(convertStringToFullName(in.nextLine().toUpperCase()));
    }

    public static String convertStringToFullName(String userInput) {
        userInput = "POKEMON_TYPE_" + userInput;
        return userInput;
    }
}
