package com.group0611.uoftgame.games.ballgame;

import com.group0611.uoftgame.utilities.GameDifficulty;

public final class GameConstants {
  // Package internal constants
  static final double GRAVITY = 2.5;
  static final int TIME_LIMIT_EASY = 120;
  static final int TIME_LIMIT_MEDIUM = 90;
  static final int TIME_LIMIT_HARD = 60;
  static final int LIVES_EASY = 20;
  static final int LIVES_MEDIUM = 10;
  static final int LIVES_HARD = 5;
  static final int SCORE_PER_HIT = 5;
  static final int BONUS_POINTS_PER_LIFE = 5;
  // Used by BallGameActivity for initializing slider view max and default positions
  public static final int SHOT_STARTING_POWER = 30;
  public static final int SHOT_STARTING_ANGLE = 30;
  public static final int SHOT_MAX_POWER = 100;
  public static final int SHOT_MAX_ANGLE = 90;
}
