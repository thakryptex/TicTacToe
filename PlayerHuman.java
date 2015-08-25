
public class PlayerHuman extends Player {

    @Override
    public void waitForClick() {
        // поток ждёт пока игрок кликнет на клетку
        while (true) {
            synchronized (this) {
                if (GUI.point != null) break;
            }
        }

        synchronized (this) {
            GUI.point = null;
        }
    }

}
