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
            x = (int) Game.prevHuman.getX();
            y = (int) Game.prevHuman.getY();

            if (tactic == 0) { // ����� ��������� ����� ������
                // ���� ���������� ��� Human ��� � �����
                if (x == 1 && y == 1) {
                    for (int i = 0; i < 3; i+=2) {
                        for (int j = 0; j < 3; j+=2) {
                            if (grid[i][j] == Cell.O) {
                                x = (j == 0 ? 2 : 0);
                                y = (i == 0 ? 2 : 0);
                                return new Point(x, y);
                            }
                        }
                    }
                }

                // ���� ������� ����� � ���
                if (x == 1) {
                    y = (y == 0 ? 2 : 0);
                    x = (int) Game.prevAI.getX();
                    if (grid[y][x] == Cell.O) {
                        x = (x == 0 ? 2 : 0);
                    }
                    return new Point(x, y);
                }
                if (y == 1) {
                    x = (x == 0 ? 2 : 0);
                    y = (int) Game.prevAI.getY();
                    if (grid[y][x] == Cell.O) {
                        y = (y == 0 ? 2 : 0);
                    }
                    return new Point(x, y);
                }

                // ���� ������� ����� � ����
                if (x == 0 || x == 2) {
                    x = (x == 0 ? 2 : 0);
                    y = (x == 0 ? 2 : 0);
                    if (grid[y][x] == Cell.O) {
                        x = (x == 0 ? 2 : 0);
                    }
                }
                return new Point(x, y);
            }

            if (tactic == 1) { // ���� ������ ��� ������ ��� � �����
                x = (x == 0 ? 2 : 0);
                y = (y == 0 ? 2 : 0);
                int i = (int) Game.prevAI.getY();
                int j = (int) Game.prevAI.getX();
                // ���� ����� ����� � ���� ��������������� ������ �������� ����
                if (x == j && y == i) {
                    j = (j == 0 ? 2 : 0);
                    return new Point(j, i);
                }
            }

            if (tactic == 2) { // ���� ������ ��� ������ ��� � ����
                if (x == 1 && y == 1) {
                    int i = (int) Game.prevAI.getY();
                    int j = (int) Game.prevAI.getX();
                    j = (j == 0 ? 2 : 0);
                    return new Point(j, i);
                }
            }

            if (tactic == 3) { // ���� ������ ��� ������ ��� � ���
                // ���� ������ ��� ������ ��� � �������� ��������� ������������ ������� ����
                if ((x == 1 && y == 0) || (x == 1 && y == 2)) {
                    if (grid[1][0] == Cell.X) return new Point(0, y);
                    if (grid[1][2] == Cell.X) return new Point(2, y);
                }
                if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                    if (grid[0][1] == Cell.X) return new Point(x, 0);
                    if (grid[2][1] == Cell.X) return new Point(x, 2);
                }
            }

        }

        if (Game.numOfSteps == 5 || Game.numOfSteps == 6) {

            if (tactic == 0) {
                // ���� � ������� ��� ������� ������ � �����, �� ������ ������ ����������� �� �����
                // ���� ������� ������ � ��� ��� ����, ��...
                x = (int) Game.prevAI.getX();
                y = (int) Game.prevAI.getY();
                if (Game.prevHuman.getX() == x) {
                    x = (x == 0 ? 2 : 0);
                }
                else {
                    y = (y == 0 ? 2 : 0);
                }
                return new Point(x, y);
            }

            //tactic = 1 - ��������� ���� ����������� ��� �� �����

            //tactic = 2 - ��������� ���� ����������� ����� ����  �� �����

            if (tactic == 3) { // ���� ������ ��� ������ ��� � ���

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
        int x, y;

        // ���� ��������� ����� ������, �� ������ � ���� �� ����� (�� ������� = 0)
        // ���� ����� �������� ����� � �����, �� ����� � ���� (�� ������� = 1)
        if (grid[1][1] == Cell.X || Game.numOfSteps % 2 == 1) {
            x = random.nextInt(2)*2;
            y = random.nextInt(2)*2;
            tactic = Game.numOfSteps % 2 == 1 ? 0 : 1;
            return new Point(x, y);
        }

        // ���� ����� �������� ����� � ���� �� �����
        for (int i = 0; i < 3; i+=2) {
            for (int j = 0; j < 3; j+=2) {
                if (grid[i][j] == Cell.X) {
                    x = (j == 0 ? 2 : 0);
                    y = (i == 0 ? 2 : 0);
                    tactic = 2;
                    return new Point(x, y);
                }
            }
        }

        // ���� ����� �������� ����� � ���� �� �������
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    if (grid[i][j] == Cell.X) {
                        tactic = 3;
                        return new Point(1, 1);
                    }
                }
            }
        }
        return point;
    }



}
