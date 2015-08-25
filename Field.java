
public class Field {

    private Cell[][] cells = new Cell[3][3];

    public Field() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public boolean updateCell(int x, int y, int playersTurn) {
        Cell cell = cells[y][x];
        if (cell.getCellState() == Cell.CellState.X || cell.getCellState() == Cell.CellState.O) {
            return false;
        }
        if (playersTurn == 0) {
            cells[y][x].setCellState(Cell.CellState.X);
        } else {
            cells[y][x].setCellState(Cell.CellState.O);
        }
        return true;
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public Cell[][] getCells() {
        return cells;
    }
}
