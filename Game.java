import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {

    public static int numOfSteps; // общее количество совершенных шагов в игре
    public static Point prevHuman; // предыдущий шаг - здесь хран€тс€ значени€ последнего шага человека
    public static Point prevAI; // предыдущий шаг - здесь хран€тс€ значени€ последнего шага машины

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

        // GUI работает в main thread и ловит клики по мышке
        GUI gui = new GUI(game);

        // Ћогика игры запускаетс€ в другом потоке
        Thread thread = new Thread(game);
        thread.start();
    }

    private void playing() {
        numOfSteps = 0;
        // выполн€етс€ пока игру никто не выиграл или пока не наступила ничь€
        while (gamePlaying) {
            Point point;
            numOfSteps++;
            // если ход игрока
            if (playersTurn == 0) {
                player1.waitForClick();
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            // если ход компьютера
            } else if (playersTurn == 1) {
                while (true) {
                    point = player2.move();
                    boolean res = updateCell(point.x, point.y);
                    if (res) {
                        prevAI = point;
                        break;
                    }
                }
                gui.repaint();
                point = null;
            }
            // после 5ти первых шагов нужно делать проверки на выигрыш
            if (numOfSteps >= 5) {
                if (isAnybodyWin()) gamePlaying = false;
                // если после 9ти шагов никто не выиграл, то "ничь€"
                else if (numOfSteps == 9) {
                    gamePlaying = false; winner = "Nobody";
                }

                // если игра закончилась, то вызываетс€ диалоговое окно
                if (!gamePlaying) {
                    int que = JOptionPane.showOptionDialog(gui.getContentPane(), winner + " wins", "THE END OF THE GAME!", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Restart", "Exit"}, "Restart");
                    if (que == 1 || que == -1) System.exit(1);
                    if (que == 0) {
                        switch (winner) {
                            case "Crosses":
                                Main.CROSSES_WINS++;
                                break;
                            case "Noughts":
                                Main.NOUGHTS_WINS++;
                                break;
                            case "Nobody":
                                Main.NO_ONE_WINS++;
                                break;
                        }
                        gui.dispose();
                        System.gc();
                        Game.startGame();
                    }
                }
            }

            // передаЄт ход другому игроку
            playersTurn = playersTurn == 1 ? 0 : 1;
        }
    }

    public boolean updateCell(int x, int y) {
        return field.updateCell(x, y, playersTurn);
    }

    // проверка на выигрыш
    public boolean isAnybodyWin() {
        boolean find = false;
        Cell[][] grid = field.getCells();
        // проверка по столбцам и строкам
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == Cell.X && grid[i][1] == Cell.X && grid[i][2] == Cell.X) {
                find = true; winner = "Crosses";
            }
            if (grid[i][0] == Cell.O && grid[i][1] == Cell.O && grid[i][2] == Cell.O) {
                find = true; winner = "Noughts";
            }

            if (grid[0][i] == Cell.X && grid[1][i] == Cell.X && grid[2][i] == Cell.X) {
                find = true; winner = "Crosses";
            }
            if (grid[0][i] == Cell.O && grid[1][i] == Cell.O && grid[2][i] == Cell.O) {
                find = true; winner = "Noughts";
            }
        }

        // проверка по одной диагонали
        if (grid[0][0] == Cell.X && grid[1][1] == Cell.X && grid[2][2] == Cell.X) {
            find = true; winner = "Crosses";
        }
        if (grid[0][0] == Cell.O && grid[1][1] == Cell.O && grid[2][2] == Cell.O) {
            find = true; winner = "Noughts";
        }

        // проверка по другой диагонали
        if (grid[0][2] == Cell.X && grid[1][1] == Cell.X && grid[2][0] == Cell.X) {
            find = true; winner = "Crosses";
        }
        if (grid[0][2] == Cell.O && grid[1][1] == Cell.O && grid[2][0] == Cell.O) {
            find = true; winner = "Noughts";
        }

        return find;
    }

    public Cell getCellState(int x, int y) {
        return field.getCell(x, y);
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
