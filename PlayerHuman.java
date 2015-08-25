import java.awt.*;

public class PlayerHuman extends Player {

    @Override
    public void waitForClick() {
        while (true) {
            synchronized (this) {
                if (GUI.point != null) break;
            }
        }

//        Point point = new Point(GUI.point.x, GUI.point.y);

        synchronized (this) {
            GUI.point = null;
        }
//        return point;
    }

}
