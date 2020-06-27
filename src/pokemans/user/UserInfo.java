/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All Rights Reserved. No usage without permission.
 */

package pokemans.user;

import pokemans.core.MovesCinematic;
import pokemans.core.MovesFast;
import pokemans.engine.TypeInteractive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserInfo {
  
  public static void importUserCSV() {
    
    List<UserPokemon> userPokes = new ArrayList<>();
    String existingUserCSVFileName = "src\\dataFiles\\history_20200626_111620spare.csv";
    
    File tmpDir = new File(existingUserCSVFileName);
    boolean fileExists = tmpDir.exists();
    
    try (BufferedReader br = Files.newBufferedReader(Paths.get(existingUserCSVFileName))) {
      
      String line = br.readLine();
      line = br.readLine();
      while (line != null) {
        String[] attributes = line.split(",");
        createPoke(attributes, userPokes);
        line = br.readLine();
      }
      
      for (UserPokemon poke : userPokes) {
        System.out.println(
                "\n\nDex Num: " + poke.getUserPokeDexNum() + "\nName: " + poke.getUserPokeName());
      }
      
      TypeInteractive.hello(userPokes);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void createPoke(String[] attributes, List<UserPokemon> userPokes) {
    /*
    0 Ancestor?,
    1 Scan date,
    2 Nr,                 (pokeDexNum)
    3 Name,               (pokeName)
    4 Nickname,           (pokeNickName)
    5 Gender,
    6 Level,              (pokeLevel)
    7 possibleLevels,
    8 CP,                 (cp)
    9 HP,                 (pokeHealth)
    10 Dust cost,
    11 min IV%,
    12 ØIV%,
    13 max IV%,
    14 ØATT IV,           (attackIV)
    15 ØDEF IV,           (defenseIV)
    16 ØHP IV,            (staminaIV)
    17 Unique?,
    18 Fast move,         (fastMove)
    19 Special move,      (chargeMove)
    20 Special move 2,    (chargeMove)
    21 DPS,
    22 Box,
    23 Custom1,
    24 Custom2,
    25 Saved,
    26 Form,              (pokeForm)
    27 Egg,
    28Lucky,
    29 BuddyBoosted,
    30 ShadowForm,
    31 Height (cm),
    32 Catch Date */
    
    String userPokeDexNum = null,
            userPokeName = null,
            userPokeNickName = null,
            move = null,
            userPokeForm = null;
    BigDecimal userPokeLevel = null,
            userPokeCP = null,
            userPokeHealth = null,
            userPokeAtackIV = null,
            userPokeDefenseIV = null,
            userPokeStaminaIV = null;
    MovesFast userPokeFastMove = null;
    ArrayList<MovesCinematic> userPokeChargeMove = new ArrayList<>();
    
    for (int i = 2; i < 26; i++) {
      if (!attributes[i].equals("-"))
        switch (Integer.toString(i)) {
          case "2":
            userPokeDexNum = attributes[2].replace("-", "").replace(" ", "_");
            break;
          case "3":
            userPokeName = attributes[3].toUpperCase().replace("-", "").replace(" ", "_");
            break;
          case "4":
            userPokeNickName = attributes[4];
            break;
          case "6":
            userPokeLevel = new BigDecimal(attributes[6].replace("-", ""));
            break;
          case "8":
            userPokeCP = new BigDecimal(attributes[8].replace("-", ""));
            break;
          case "9":
            userPokeHealth = new BigDecimal(attributes[9].replace("-", ""));
            break;
          case "14":
            userPokeAtackIV = new BigDecimal(attributes[14].replace("-", ""));
            break;
          case "15":
            userPokeDefenseIV = new BigDecimal(attributes[15].replace("-", ""));
            break;
          case "16":
            userPokeStaminaIV = new BigDecimal(attributes[16].replace("-", ""));
            break;
          case "18":
            if (!attributes[i].equals("- ")) {
              move = attributes[18].toUpperCase().replace("-", "").replace(" ", "_");
              move = move + "_FAST";
              userPokeFastMove = MovesFast.valueOf(move);
            }
            break;
          case "19":
            if (!attributes[i].equals("- ")) {
              userPokeChargeMove.add(
                      MovesCinematic.valueOf(
                              (attributes[19])
                                      .toUpperCase()
                                      .replace("-", "")
                                      .replace(" ", "_")));
            }
            break;
          case "20":
            if (!attributes[i].equals("- ")) {
              userPokeChargeMove.add(
                      MovesCinematic.valueOf(
                              (attributes[20])
                                      .toUpperCase()
                                      .replace("-", "")
                                      .replace(" ", "_")));
            }
            break;
          case "26":
            userPokeForm = attributes[26].replace("-", "").replace(" ", "_");
            break;
        }
    }
    
    userPokes.add(
            new UserPokemon(
                    userPokeDexNum,
                    userPokeName,
                    userPokeNickName,
                    userPokeForm,
                    userPokeLevel,
                    userPokeCP,
                    userPokeHealth,
                    userPokeAtackIV,
                    userPokeDefenseIV,
                    userPokeStaminaIV,
                    userPokeFastMove,
                    userPokeChargeMove));
  }
}
