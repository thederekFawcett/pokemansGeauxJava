/*
 * Copyright (c) 2020, Derek Fawcett. All rights reserved. No usage without permission.
 */

package pokemans.engine;

import pokemans.core.Type;

import java.util.Scanner;

public class TypeInteractive {
    static Scanner in;

    public static void main(String[] args) {
        String wantContinue;
        //do {
        askForFirstType();


        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        //askForSecondType();

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////


        // calculates type effectivenesses. adds them to list
        FetchBasedOnTypes.againstTypes(FetchBasedOnTypes.userTypes);

        System.out.print("\nWhat would you like to see?:\nSuper-Effective types? (t) or Pokemon? (p): ");


        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////

        //in = new Scanner(System.in);
        //String userInput = in.nextLine().toUpperCase();


        String userInput = "P";

        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////


        boolean quitLoop = false;
        //do {
        switch (userInput) {
            case "X" -> System.exit(0);
            case "T" -> {
                displayUsefulTypes();
                quitLoop = true;
            }
            case "P" -> {
                displayUsefulPokes();
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

        String userInput = "POKEMON_TYPE_FIGHTING";
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

        String userInput = "POKEMON_TYPE_NORMAL";
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
            System.out.println(String.format("%24s %12sx", FetchBasedOnTypes.typesSuperEffective.get(row),
                    FetchBasedOnTypes.damageSuperEffective.get(row)));
        }
        System.out.println("\nNot Very Effective Damage Against: ");
        for (int row = 0; row < FetchBasedOnTypes.typesNotEffective.size(); row++) {
            System.out.println(String.format("%24s %12sx", FetchBasedOnTypes.typesNotEffective.get(row),
                    FetchBasedOnTypes.damageNotEffective.get(row)));
        }
    }

    private static void displayUsefulPokes() {
        System.out.println("\n\nTop 20 pokes to use against " +
                FetchBasedOnTypes.listToString(FetchBasedOnTypes.userTypes) + "\n");
        FetchBasedOnTypes.sendToFetchBasedOnStats();
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
