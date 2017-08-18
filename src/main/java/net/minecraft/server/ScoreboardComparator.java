package net.minecraft.server;

import java.util.Comparator;



final class ScoreboardComparator
  implements Comparator
{
  public int a(ScoreboardScore paramScoreboardScore1, ScoreboardScore paramScoreboardScore2)
  {
    if (paramScoreboardScore1.getScore() > paramScoreboardScore2.getScore())
      return 1;
    if (paramScoreboardScore1.getScore() < paramScoreboardScore2.getScore()) {
      return -1;
    }
    return 0;
  }
}
