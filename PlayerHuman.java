
public class PlayerHuman extends Player {

    @Override
    public void waitForClick() {
        // ����� ��� ���� ����� ������� �� ������
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
