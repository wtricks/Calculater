import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Panel extends JPanel implements ActionListener {
    private final int S_HEIGHT = 500;
    private final int S_WIDTH = 300;
    private final int B_SIZE = 60;
    private JPanel keyBoard;
    private JLabel[] label = new JLabel[2];
    private JButton[] buttons = new JButton[20];
    private String lastNum = "";
    private String calc = "";
    private char oprater = '\u0000';

    // Colors 
    private final Color mainColor = new Color(251, 102, 59);
    
    // Light Colors
    private final Color backColor = new Color(255, 255, 255);
    private final Color cardColor = new Color(245, 245, 245);
    private final Color buttonColor = new Color(228, 228, 228);
    private final Color textColor = new Color(0, 0, 0);

    // Dark Colors
    // private final Color backColor = new Color(54, 71, 81);
    // private final Color cardColor = new Color(24, 33, 42);
    // private final Color buttonColor = new Color(24, 24, 24);
    // private final Color textColor = new Color(255, 255, 255);

    Panel() {
        setPreferredSize(new Dimension(S_WIDTH, S_HEIGHT));
	    setBackground(backColor);
	    setFocusable(true);
        setLayout(null);
        
        keyBoard = new JPanel();
        keyBoard.setBounds(0, 120 , S_WIDTH, S_HEIGHT - 120);
        keyBoard.setLayout(null);
        keyBoard.setBackground(cardColor);
        add(keyBoard);

        // creating label
        label[0] = createLabel(0);
        label[1] = createLabel(60);

        // adding buttons
        buttons[0] = createButton("AC", 12, 15, buttonColor);
        buttons[1] = createButton("<=", 84, 15, buttonColor);
        buttons[2] = createButton("%", 156, 15, buttonColor);
        buttons[3] = createButton("/", 228, 15, mainColor);

        buttons[4] = createButton("7", 12, 88, buttonColor);
        buttons[5] = createButton("8", 84, 88, buttonColor);
        buttons[6] = createButton("9", 156, 88, buttonColor);
        buttons[7] = createButton("*", 228, 88, mainColor);

        buttons[8] = createButton("4", 12, 160, buttonColor);
        buttons[9] = createButton("5", 84, 160, buttonColor);
        buttons[10] = createButton("6", 156, 160, buttonColor);
        buttons[11] = createButton("-", 228, 160, mainColor);

        buttons[12] = createButton("1", 12, 233, buttonColor);
        buttons[13] = createButton("2", 84, 233, buttonColor);
        buttons[14] = createButton("3", 156, 233, buttonColor);
        buttons[15] = createButton("+", 228, 233, mainColor);

        buttons[16] = createButton(".", 12, 305, buttonColor);
        buttons[17] = createButton("0", 84, 305, buttonColor);
        buttons[18] = createButton("00", 156, 305, buttonColor);
        buttons[19] = createButton("=", 228, 305, mainColor);
    }

    private JButton createButton(String name, int x, int y, Color c) {
        JButton button = new JButton(name);
        button.setBorder(null);
        button.setBounds(x, y, B_SIZE, B_SIZE);
        button.setFocusable(false);
        button.setForeground(c == mainColor ? new Color(255, 255, 255) : textColor);
        button.setBackground(c);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        button.addActionListener(this);
        keyBoard.add(button);
        return button;
    }

    private JLabel createLabel(int y) {
        JLabel label = new JLabel("");
        label.setBounds(4, y, S_WIDTH-8, 60);
        label.setForeground(textColor);
        label.setBackground(backColor);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 42));
        add(label);
        return label;
    }

    private void addChacter(String c) {
        char first = c.charAt(0);

        if (first == '+' || first == '-' || first == '*' || first == '/' || first == '%' || first == '=') {
            if (lastNum.equals("")) {
                oprater = first == '=' ? '\u0000' : first;
                return;
            }
            
            setText(calc = (oprater == '\u0000') ? lastNum : compute());
            oprater = first == '=' ? '\u0000' : first;   
            lastNum = "";
            return;
        }

        if (lastNum.length() == 16) return;
        if (lastNum.equals("")) {
            setText(lastNum = c);
            return;
        }

        if (first == '.' && lastNum.indexOf('.') != -1) {
            return;
        }

        setText(lastNum += c);
    }

    private String removeExtra(String s) {
        if (s.endsWith(".0"))
            return s.substring(0, s.length()-2);
        return s;    
    }

    private void setText(String s) {
        s = removeExtra(s);
        int length = s.length();
        if (length > 11) {
            label[0].setText(s.substring(0, length - 11 + 1));
            label[1].setText(s.substring(length - 11, length));
        } else {
            label[0].setText("");
            label[1].setText(s);
        }
    }

    private String compute() {
        double a =  Double.parseDouble(calc);
        double b =  Double.parseDouble(lastNum);

        switch(oprater) {
            case '+': return String.valueOf(a + b);
            case '-': return String.valueOf(a - b);
            case '/': return String.valueOf(a / b);
            case '*': return String.valueOf(a * b);
            default: return String.valueOf(a % b);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (byte i = 0; i < 20; i++) {
            if (buttons[i] == e.getSource()) {
                String str = buttons[i].getText();
                
                if (str == "AC") {
                    label[0].setText("");
                    label[1].setText("");
                    lastNum = calc = "";
                    oprater = '\u0000';
                } else if (str == "<=") {
                    if (lastNum.length() > 0) {
                        setText(lastNum = lastNum.substring(0, lastNum.length()-1));
                    }
                } else {
                    addChacter(str);
                }

                break;
            }
        }
    }
}
