/*
 * Copyright (c) 2020 Derek Fawcett (@the_derek). All Rights Reserved. No usage without permission.
 */

package pokemans.user;

import pokemans.core.MovesCinematic;
import pokemans.core.MovesFast;
import pokemans.core.Pokedex;
import pokemans.engine.TypeInteractive;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserInfo {

  public static void importUserCSV() {

    List<UserPokemon> userPokes = new ArrayList<>();
    String existingUserCSVFileName = "src\\dataFiles\\history_20200626_111620full.csv";

    // File tmpDir = new File(existingUserCSVFileName);
    // boolean fileExists = tmpDir.exists();

    try (BufferedReader br = Files.newBufferedReader(Paths.get(existingUserCSVFileName))) {

      String line = br.readLine();
      line = br.readLine();
      while (line != null) {
        String[] attributes = line.split(",");
        createPoke(attributes, userPokes);
        line = br.readLine();
      }

      TypeInteractive.createLocalListOfUserPokesFromCSV(userPokes);

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
    26 Form,
    27 Egg,
    28Lucky,
    29 BuddyBoosted,
    30 ShadowForm,
    31 Height (cm),
    32 Catch Date */
  
    String userPokeDexNum = null, userPokeName = null, userPokeNickName = null, move = null;
    BigDecimal userPokeLevel = null,
            userPokeCP = null,
            userPokeHealth = null,
            userPokeAttackIV = null,
            userPokeDefenseIV = null,
            userPokeStaminaIV = null;
    MovesFast userPokeFastMove = null;
    List<MovesCinematic> userPokeChargeMove = new ArrayList<>();
  
    for (int i = 2; i <= 20; i++) {
      if (!attributes[i].equals("-"))
        switch (Integer.toString(i)) {
          case "2": // dex num
            userPokeDexNum = attributes[2].replace("-", "").replace(" ", "_");
            break;
          case "3": // poke name
            if (!attributes[i].toUpperCase().contains("ALOLA")) {
              switch (attributes[i]) {
                case "Mewtwo Armored":
                  userPokeName = "MEWTWO_A";
                  break;
                case "Cherrim Sunshine":
                  userPokeName = "CHERRIM_SUNNY";
                  break;
                case "Mr. Mime":
                  userPokeName = "MR_MIME";
                  break;
                case "Nidoran♀":
                  userPokeName = "NIDORAN_FEMALE";
                  break;
                case "Nidoran♂":
                  userPokeName = "NIDORAN_MALE";
                  break;
                default:
                  if (!attributes[i].contains("'")) {
                    userPokeName = attributes[3].toUpperCase().replace("-", "_").replace(" ", "_");
                    break;
                  } else {
                    userPokeName = attributes[3].toUpperCase().replace(" ", "_").replace("'", "");
                  }
              }
            } else {
              assert userPokeName != null;
              userPokeName = removeLastChar(attributes[i].toUpperCase().replace(" ", "_"));
            }
            break;
          case "4": // user poke nickname
            userPokeNickName = attributes[4];
            break;
          case "6": // user poke level
            userPokeLevel = new BigDecimal(attributes[6].replace("-", ""));
            break;
          case "8": // user poke CP
            userPokeCP = new BigDecimal(attributes[8].replace("-", ""));
            break;
          case "9": // user poke health
            userPokeHealth = new BigDecimal(attributes[9].replace("-", ""));
            break;
          case "14": // attackIV
            userPokeAttackIV = new BigDecimal(attributes[14].replace("-", ""));
            break;
          case "15": // defenseIV
            userPokeDefenseIV = new BigDecimal(attributes[15].replace("-", ""));
            break;
          case "16": // stamina IV
            userPokeStaminaIV = new BigDecimal(attributes[16].replace("-", ""));
            break;
          case "18": // fast move
            if (!attributes[i].equals("- ")) {
              if (!attributes[i].equals(" - ")) {
                if (!attributes[i].contains("Hidden Power ")) {
                  move = attributes[18].toUpperCase().replace("-", "_").replace(" ", "_") + "_FAST";
                  userPokeFastMove = MovesFast.valueOf(move);
                } else if (attributes[i].contains("Hidden Power ")) {
                  move = "HIDDEN_POWER_" + attributes[i].substring(13).toUpperCase() + "_FAST";
                  userPokeFastMove = MovesFast.valueOf(move);
                }
              }
            }
            break;
          case "19": // charge move 1
            if (!attributes[i].equals("- ")) {
              switch (attributes[i]) {
                case "Future Sight":
                  userPokeChargeMove.add(MovesCinematic.valueOf("FUTURESIGHT"));
                  break;
                case "Vise Grip":
                  userPokeChargeMove.add(MovesCinematic.valueOf("VICE_GRIP"));
                  break;
                case "Weather Ball":
                  String pokeType =
                          (Pokedex.getPokeFromString(userPokeName).getType1().toString()).substring(13);
                  if (!pokeType.equals("NORMAL")) {
                    userPokeChargeMove.add(MovesCinematic.valueOf("WEATHER_BALL_" + pokeType));
                  } else {
                    userPokeChargeMove.add(MovesCinematic.valueOf("WEATHER_BALL_ROCK"));
                  }
                  break;
                case " - ":
                  break;
                default:
                  userPokeChargeMove.add(
                          MovesCinematic.valueOf(
                                  (attributes[19]).toUpperCase().replace(" ", "_").replace("-", "_")));
                  break;
              }
            }
            break;
          case "20": // charge move 2
            if (!attributes[i].equals("- ")) {
              switch (attributes[i]) {
                case "Future Sight":
                  userPokeChargeMove.add(MovesCinematic.valueOf("FUTURESIGHT"));
                  break;
                case "Vise Grip":
                  userPokeChargeMove.add(MovesCinematic.valueOf("VICE_GRIP"));
                  break;
                case " - ":
                  break;
                default:
                  userPokeChargeMove.add(
                          MovesCinematic.valueOf(
                                  (attributes[20]).toUpperCase().replace(" ", "_").replace("-", "_")));
                  break;
              }
            }
            break;
        }
    }
  
    userPokes.add(
            new UserPokemon(
                    userPokeDexNum,
                    userPokeName,
                    userPokeNickName,
                    userPokeLevel,
                    userPokeCP,
                    userPokeHealth,
                    userPokeAttackIV,
                    userPokeDefenseIV,
                    userPokeStaminaIV,
                    userPokeFastMove,
                    userPokeChargeMove));
  }
  
  private static String removeLastChar(String originalString) {
    return (originalString == null || originalString.length() == 0)
            ? originalString
            : (originalString.substring(0, originalString.length() - 1));
  }
}
