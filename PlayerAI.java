import java.awt.*;
import java.util.Random;

public class PlayerAI extends Player {

    private Random random = new Random();
    private int tactic = -1;
    private Game game;
    private Cell[][] grid;

    public PlayerAI(Game game) {
        this.game = game;
    }

    @Override
    public Point move() {
        Point point = new Point();
        grid = game.getField().getCells();

        // ������ ��� ����������
        if (Game.numOfSteps == 1 || Game.numOfSteps == 2) {
            return firstMove();
        }

        Point result;

        // ����� 4� ����� ��������� ����� �� AI ��� Human �������� ������� ��� ��������� �����.
        if (Game.numOfSteps >= 4) {
            // ���� AI ����� �������� ������� �����, �� �� ������ ���
            result = canIWin();
            if (result != null) {
                return result;
            }
            // ���� Human ����� �������� ��������� �����, �� ������ ��� ���
            result = canHeWin();
            if (result != null) {
                return result;
            }
        }

        result = tacticMove();
        if (result != null) {
            return result;
        }


        point.x = random.nextInt(3);
        point.y = random.nextInt(3);

        return point;
    }

    private Point tacticMove() {
        int x, y;
        if (Game.numOfSteps == 3 || Game.numOfSteps == 4) {

            if (tactic == 0) {
                x = (int) Game.prevStep.getX();
                y = (int) Game.prevStep.getY();

                // ���� ���������� ��� Human ��� � �����
                if (x == 1 && y == 1) {
                    for (int i = 0; i < 3; i+=2) {
                        for (int j = 0; j < 3; j+=2) {
                            if (grid[i][j] == Cell.O) {
                                x = (j == 0 ? 2 : 0);
                                y = (i == 0 ? 2 : 0);
                                return new Point(x, y); //TODO ����� ����� ��������� ������ ��� � ������ ����
                            }
                        }
                    }
                }

            }

            if (tactic == 1) {

            }

            if (tactic == 2) {

            }

            if (tactic == 3) {

            }

        }

        if (Game.numOfSteps == 5 || Game.numOfSteps == 6) {

            if (tactic == 0) {
                x = (int) Game.prevStep.getX();
                y = (int) Game.prevStep.getY();

                if (x == 0 || x == 2) {
                    x = (x == 0 ? 2 : 0);
                    y = (x == 0 ? 2 : 0);
                }
                if (x == 1){
                    y = (y == 0 ? 2 : 0);
                    for (int i = 0; i < 3; i+=2) {
                        if (grid[y][i] == Cell.EMPTY)
                            x = i;
                    }
                }
                return new Point(x, y);
            }

        }

        return null;
    }

    private Point canIWin() {
        for (int i = 0; i < 3; i++) {
            int countHor = 0;
            int countVer = 0;
            // �������� �����������
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == Cell.X) {countHor = 0; break; }
                if (grid[i][j] == Cell.O) countHor++;
            }
            // �������� ���������
            for (int j = 0; j < 3; j++) {
                if (grid[j][i] == Cell.X) {countVer = 0; break; }
                if (grid[j][i] == Cell.O) countVer++;
            }
            // ���� ��, ���������� ����� �� �����������, �� ������� ����
            if (countHor == 2) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
                }
            }
            // ���� ��, ���������� ����� �� ���������, �� ������� ����
            if (countVer == 2) {
                for (int j = 0; j < 3; j++) {
                    if (grid[j][i] == Cell.EMPTY) return new Point(i, j);
                }
            }
        }

        int diagon = 0;
        // �������� ��������� [0,0], [1,1], [2,2]
        for (int i = 0, j = 0; i < 3 && j < 3; i++, j++) {
            if (grid[i][j] == Cell.X) {diagon = 0; break; }
            if (grid[i][j] == Cell.O) diagon++;
        }
        // ���� ��, ���������� ����� �� ���������, �� ������� ����
        if (diagon == 2) {
            for (int i = 0, j = 0; i < 3 && j < 3; i++, j++) {
                if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
            }
        }

        diagon = 0;
        // �������� ��������� [0,2], [1,1], [2,0]
        for (int i = 2, j = 0; i >= 0 && j < 3; i--, j++) {
            if (grid[i][j] == Cell.X) {diagon = 0; break; }
            if (grid[i][j] == Cell.O) diagon++;
        }
        // ���� ��, ���������� ����� �� ���������, �� ������� ����
        if (diagon == 2) {
            for (int i = 2, j = 0; i >= 0 && j < 3; i--, j++) {
                if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
            }
        }

        return null;
    }

    private Point canHeWin() {
        for (int i = 0; i < 3; i++) {
            int countHor = 0;
            int countVer = 0;
            // �������� �����������
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == Cell.O) {countHor = 0; break; }
                if (grid[i][j] == Cell.X) countHor++;
            }
            // �������� ���������
            for (int j = 0; j < 3; j++) {
                if (grid[j][i] == Cell.O) {countVer = 0; break; }
                if (grid[j][i] == Cell.X) countVer++;
            }
            // ���� ��, ���������� ����� �� �����������, �� ������� ����
            if (countHor == 2) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
                }
            }
            // ���� ��, ���������� ����� �� ���������, �� ������� ����
            if (countVer == 2) {
                for (int j = 0; j < 3; j++) {
                    if (grid[j][i] == Cell.EMPTY) return new Point(i, j);
                }
            }
        }

        int diagon = 0;
        // �������� ��������� [0,0], [1,1], [2,2]
        for (int i = 0, j = 0; i < 3 && j < 3; i++, j++) {
            if (grid[i][j] == Cell.O) {diagon = 0; break; }
            if (grid[i][j] == Cell.X) diagon++;
        }
        // ���� ��, ���������� ����� �� ���������, �� ������� ����
        if (diagon == 2) {
            for (int i = 0, j = 0; i < 3 && j < 3; i++, j++) {
                if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
            }
        }

        diagon = 0;
        // �������� ��������� [0,2], [1,1], [2,0]
        for (int i = 2, j = 0; i >= 0 && j < 3; i--, j++) {
            if (grid[i][j] == Cell.O) {diagon = 0; break; }
            if (grid[i][j] == Cell.X) diagon++;
        }
        // ���� ��, ���������� ����� �� ���������, �� ������� ����
        if (diagon == 2) {
            for (int i = 2, j = 0; i >= 0 && j < 3; i--, j++) {
                if (grid[i][j] == Cell.EMPTY) return new Point(j, i);
            }
        }

        return null;
    }

    private Point firstMove() {
        Point point = new Point();

        // ���� ��������� ����� ������, �� ������ � ���� �� ����� (�� ������� = 0)
        // ���� ����� �������� ����� � �����, �� ����� � ���� (�� ������� = 1)
        if (grid[1][1] == Cell.X || Game.numOfSteps % 2 == 1) {
            int x = random.nextInt(2)*2;
            int y = random.nextInt(2)*2;
            point.x = x;
            point.y = y;
            tactic = Game.numOfSteps % 2 == 1 ? 0 : 1;
            return point;
        }

        // ���� ����� �������� ����� � ���� �� �����
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

        // ���� ����� �������� ����� � ���� �� �������
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
