package com.example.nepalappgroupb2.Domain;

import android.media.MediaPlayer;

public class Afspilning {
  private static MediaPlayer mp;

  public static void start(MediaPlayer mp1) {
    start(mp1, null);
  }

  public static void start(MediaPlayer mp1, final MediaPlayer.OnCompletionListener ocl) {
    stop();
    try {
      if (mp1 == null) return;
      mp = mp1;
      mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mpx) {
          try {
            if (ocl != null) ocl.onCompletion(null);
            mp.reset();
            mp.release();
            mp = null;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      mp.start();
    } catch (Exception e) {
      e.printStackTrace();  // fang alle fejl og log dem, men lad være at crashe app'en
    }
  }

  public static void stop() {
    if (mp != null) try {
      mp.stop(); // Stop tidligere afspilning
      mp.reset();
      mp.release();
      mp = null;
    } catch (Exception e) {
      e.printStackTrace();  // fang alle fejl og log dem, men lad være at crashe app'en
    }
  }
}
