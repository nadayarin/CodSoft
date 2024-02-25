import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class task1 extends JFrame implements ActionListener {
    private JTextField textField;
    private JLabel infoLabel;
    private JButton guessButton;
    private int secretNumber;
    private int attempts;
    private int score;

    public task1() {
        setTitle("Guess the Number");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        infoLabel = new JLabel("I'm thinking of a number between 1 and 100.");
        inputPanel.add(infoLabel);
        textField = new JTextField(10);
        inputPanel.add(textField);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        inputPanel.add(guessButton);
        add(inputPanel, BorderLayout.CENTER);

        newGame();

        setVisible(true);
    }

    private void newGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
        attempts = 5;
        score = 0;
        infoLabel.setText("I'm thinking of a number between 1 and 100. You have " + attempts + " attempts.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            int guess = Integer.parseInt(textField.getText());
            if (guess == secretNumber) {
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number in " + (6 - attempts) + " attempts.");
                score++;
                newGame();
            } else {
                attempts--;
                if (attempts == 0) {
                    JOptionPane.showMessageDialog(this, "Sorry, you've used all your attempts. The secret number was " + secretNumber);
                    newGame();
                } else if (guess < secretNumber) {
                    JOptionPane.showMessageDialog(this, "Too low! You have " + attempts + " attempts left.");
                } else {
                    JOptionPane.showMessageDialog(this, "Too high! You have " + attempts + " attempts left.");
                }
            }
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(task1::new);
    }
}

