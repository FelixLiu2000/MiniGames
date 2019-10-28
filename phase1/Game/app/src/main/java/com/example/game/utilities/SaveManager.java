package com.example.game.utilities;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provides static methods for saving and loading, i.e., do not create an instance of
 * this class
 *
 * <p>Call loadPlayer with the player's name to load a player All of your classes must implement
 * Seralizeable to be saved
 */
public class SaveManager {
  static final String filePath = Environment.getExternalStorageDirectory() + "/" + "saves" + "/";

  /**
   * Saves a player.
   *
   * @param player
   * @return true if properly saved else false
   */
  public static boolean save(Player player) {
    File saveFolder = new File(filePath);
    if (!saveFolder.exists()) {
      saveFolder.mkdirs();
    }

    System.out.println(saveFolder.exists());

    File saveFile = new File(filePath + player.getUsername() + ".sav");

    FileOutputStream fileOutputStream;
    ObjectOutputStream objectOutputStream;
    try {
      saveFile.createNewFile();
      fileOutputStream = new FileOutputStream(saveFile);
      objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(player);
      objectOutputStream.close();
    } catch (Exception e) {
      System.out.println("Failed with exception: " + e);
      return false;
    }
    return true;
  }

  /**
   * This method will load a player, provided their username
   *
   * @param name
   * @return
   */
  public static Player loadPlayer(String name) {
    FileInputStream fileInputStream;
    ObjectInputStream objectInputStream;
    Player player = null;
    try {
      fileInputStream = new FileInputStream(filePath + name + ".sav");
      objectInputStream = new ObjectInputStream(fileInputStream);
      player = (Player) objectInputStream.readObject();
      objectInputStream.close();
    } catch (Exception e) {
      System.out.println("Failed with exception: " + e);
    }
    return player;
  }
}
