package com.group0611.uoftgame.activities;

import android.content.Intent;

import com.group0611.uoftgame.utilities.AppManager;

public interface GameActivity {
  Intent getCurrentIntent();

  void setCurrentIntent(Intent intent);

  Intent getToResultsPageIntent();

  void setToResultsPageIntent(Intent intent);

  void leaveGame(AppManager manager);
}
