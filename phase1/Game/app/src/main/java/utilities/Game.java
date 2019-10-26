package utilities;

public class Game {

    private int score;

    public Game() {
        this.score = 0;
    }

    public int getScore() { return this.score; }

    public void setScore(int score) { this.score = score;  }

    public void createGameEnvironment() {} // takes code from XML

    public void startGame() {}

    public void timer() {
        long start = System.currentTimeMillis();
        long end = start + 60*1000; // 60 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            // update game timer
        }
        endGame();
    }

    public void endGame() {
        // end game

    }
}
