import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {

    public static int numOfSteps;

    private boolean gamePlaying;
    private int playersTurn;
    private Field field;
    private Player player1, player2;
    private GUI gui;
    private String winner;

    public Game() {
        gamePlaying = true;
        field = new Field();
        player1 = new PlayerHuman();
        player2 = new PlayerAI(this);
    }

    public static void startGame() {
        Game game = new Game();

        GUI gui = new GUI(game);

        Thread thread = new Thread(game);
        thread.start();
    }

    private void playing() {
        numOfSteps = 0;
        while (gamePlaying) {
            Point point;
            numOfSteps++;
            if (playersTurn == 0) {
                player1.waitForClick();
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (playersTurn == 1) {
                while (true) {
                    point = player2.move();
                    boolean res = updateCell(point.x, point.y);
                    if (res) break;
                }
                gui.repaint();
                point = null;
            }
            if (numOfSteps >= 5) {
                if (isAnybodyWin()) gamePlaying = false;
                if (numOfSteps == 9) {
                    gamePlaying = false; winner = "Nobody";
                }

                if (!gamePlaying) {
                    int que = JOptionPane.showOptionDialog(gui.getContentPane(), winner + " wins", "THE END OF THE GAME!", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Restart", "Exit"}, "Restart");
                    if (que == 1 || que == -1) System.exit(1);
                    if (que == 0) {
                        gui.dispose();
                        System.gc();
                        Game.startGame();
                    }
                }
            }

            playersTurn = playersTurn == 1 ? 0 : 1;
        }
    }

    public boolean updateCell(int x, int y) {
        return field.updateCell(x, y, playersTurn);
    }


    public boolean isAnybodyWin() {
        boolean find = false;
        Cell[][] grid = field.getCells();
        for (int i = 0; i < 3; i++) {
            if (grid[i][0].getCellState() == Cell.CellState.X && grid[i][1].getCellState() == Cell.CellState.X && grid[i][2].getCellState() == Cell.CellState.X) {
                find = true; winner = "Crosses";
            }
            if (grid[i][0].getCellState() == Cell.CellState.O && grid[i][1].getCellState() == Cell.CellState.O && grid[i][2].getCellState() == Cell.CellState.O) {
                find = true; winner = "Noughts";
            }

            if (grid[0][i].getCellState() == Cell.CellState.X && grid[1][i].getCellState() == Cell.CellState.X && grid[2][i].getCellState() == Cell.CellState.X) {
                find = true; winner = "Crosses";
            }
            if (grid[0][i].getCellState() == Cell.CellState.O && grid[1][i].getCellState() == Cell.CellState.O && grid[2][i].getCellState() == Cell.CellState.O) {
                find = true; winner = "Noughts";
            }
        }

        if (grid[0][0].getCellState() == Cell.CellState.X && grid[1][1].getCellState() == Cell.CellState.X && grid[2][2].getCellState() == Cell.CellState.X) {
            find = true; winner = "Crosses";
        }
        if (grid[0][0].getCellState() == Cell.CellState.O && grid[1][1].getCellState() == Cell.CellState.O && grid[2][2].getCellState() == Cell.CellState.O) {
            find = true; winner = "Noughts";
        }

        if (grid[0][2].getCellState() == Cell.CellState.X && grid[1][1].getCellState() == Cell.CellState.X && grid[2][0].getCellState() == Cell.CellState.X) {
            find = true; winner = "Crosses";
        }
        if (grid[0][2].getCellState() == Cell.CellState.O && grid[1][1].getCellState() == Cell.CellState.O && grid[2][0].getCellState() == Cell.CellState.O) {
            find = true; winner = "Noughts";
        }

        return find;
    }

    public Cell.CellState getCellState(int x, int y) {
        return field.getCell(x, y).getCellState();
    }

    @Override
    public void run() {
        playing();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public Field getField() {
        return field;
    }

    public boolean isGamePlaying() {
        return gamePlaying;
    }

    public void setPlayersTurn(int playersTurn) {
        this.playersTurn = playersTurn;
    }
}
