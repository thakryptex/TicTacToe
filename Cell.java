
public class Cell {

    public enum CellState {
        EMPTY, X, O;
    }

    private CellState cellState;

    public Cell() {
        cellState = CellState.EMPTY;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }
}
