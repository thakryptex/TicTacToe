import java.awt.*;
import java.util.Random;

public class PlayerAI extends Player {

    private Random random = new Random();
    private int tactic = 0;
    private Game game;

    public PlayerAI(Game game) {
        this.game = game;
    }

    @Override
    public Point move() {
        Point point = new Point();
        Cell[][] grid = game.getField().getCells();

        if (tactic == 0) {
            point = firstMove(grid);
            return point;
        }

        point.x = random.nextInt(3);
        point.y = random.nextInt(3);

        return point;
    }

    private Point firstMove(Cell [][] grid) {
        Point point = new Point();

        if(Game.numOfSteps % 2 == 1) {

        }

        if (grid[1][1] == Cell.X) {
            int x = random.nextInt(2)*2;
            int y = random.nextInt(2)*2;
            point.x = x;
            point.y = y;
            tactic = 1;
            return point;
        }

        for (int i = 0; i < 3; i+=2) {
            for (int j = 0; j < 3; j+=2) {
                if (grid[i][j] == Cell.X) {
                    switch (random.nextInt(2)) {
                        case 0:
                            point.x = j;
                            point.y = 1;
                            break;
                        case 1:
                            point.x = 1;
                            point.y = j;
                            break;
                    }
                    tactic = 2;
                    return point;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    if (grid[i][j] == Cell.X) {
                        point.x = 1;
                        point.y = 1;
                        tactic = 3;
                        return point;
                    }
                }
            }
        }
        return point;
    }



}
