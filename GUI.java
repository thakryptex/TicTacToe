import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUI extends JFrame {

    private Game game;
    public static Point point;

    public GUI(Game game) {
        super("Tic Tac Toe");

        this.game = game;
        this.game.setGui(this);

        int choice = JOptionPane.showOptionDialog(getContentPane(), "Who will be first", "Start of the Game", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Player", "Computer"}, "Player");

        if (choice == -1) System.exit(1);

        this.game.setPlayersTurn(choice);

        GUIpanel panel = new GUIpanel();

        add(panel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!game.isGamePlaying()) return;
                int x = e.getX() - 10;
                int y = e.getY() - 36;
                // проверка, что X и Y нажатия находятся в рамках игрового поля
                if (x > 0 && y > 0 && y < 300 && x < 300) {
                    int i, j;
                    i = y / 100;
                    j = x / 100;
                    // если нажатие одобрено и клетка изменилась, то записывается point и даётся сигнал второму thread, что другой игрок делает шаг
                    if (game.updateCell(j, i)) {
                        Game.prevHuman = new Point(j, i);
                        point = new Point(j, i);
                        repaint();
                    }
                }
            }
        });

        setSize(new Dimension(327, 350));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    private class GUIpanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    g.setColor(Color.black);
                    g.drawRect((j * 100)+10, (i * 100)+10, 100, 100);
                    switch (game.getCellState(j, i)) {
                        case X:
                            g.drawLine((j * 100)+30, (i * 100)+30, (j * 100)+90, (i * 100)+90);
                            g.drawLine((j * 100)+30, (i * 100)+90, (j * 100)+90, (i * 100)+30);
                            break;
                        case O:
                            g.drawOval((j * 100)+30, (i * 100)+30, 60, 60);
                            break;
                    }
                }
            }
        }
    }
}
