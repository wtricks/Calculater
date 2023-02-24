import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Calculater {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculater");
        frame.add(new Panel());

        frame.setIconImage(new ImageIcon(Calculater.class.getResource("logo.png")).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
        frame.pack();
		frame.setVisible(true);
    }
}
