// Simple GUI Calculator in Java using Swing
// Supports basic arithmetic operations: addition, subtraction, multiplication, and division.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class BasicCalculator extends JFrame {
    private JTextField display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewInput = true;
    private DecimalFormat df = new DecimalFormat("#.########");

    public BasicCalculator() {
        // Window setup
        setTitle("Basic Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 400);
        setLocationRelativeTo(null); // Center on screen

        // Display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "CE", "←", "±"
        };

        // Create and add buttons
        for (String label : buttons) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(new ButtonListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("\\d") || command.equals(".")) {
                if (isNewInput) {
                    currentInput = "";
                    isNewInput = false;
                }
                currentInput += command;
                display.setText(currentInput);
            } else if (command.equals("=")) {
                if (!operator.isEmpty() && !currentInput.isEmpty()) {
                    double secondNumber = Double.parseDouble(currentInput);
                    double result = performCalculation(firstNumber, secondNumber, operator);
                    String resultStr = df.format(result);
                    display.setText(resultStr);
                    currentInput = resultStr;
                    operator = "";
                    firstNumber = 0;
                    isNewInput = true;
                }
            } else if (command.matches("[+\\-*/]")) {
                if (!currentInput.isEmpty()) {
                    firstNumber = Double.parseDouble(currentInput);
                    operator = command;
                    isNewInput = true;
                }
            } else if (command.equals("C")) {
                currentInput = "";
                operator = "";
                firstNumber = 0;
                isNewInput = true;
                display.setText("0");
            } else if (command.equals("CE")) {
                currentInput = "";
                isNewInput = true;
                display.setText("0");
            } else if (command.equals("←")) {
                if (!currentInput.isEmpty() && !isNewInput) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    display.setText(currentInput.isEmpty() ? "0" : currentInput);
                }
            } else if (command.equals("±")) {
                if (!currentInput.isEmpty()) {
                    double num = Double.parseDouble(currentInput);
                    currentInput = String.valueOf(-num);
                    display.setText(df.format(-num));
                }
            }
        }
    }

    private double performCalculation(double x, double y, String op) {
        switch (op) {
            case "+": return x + y;
            case "-": return x - y;
            case "*": return x * y;
            case "/":
                if (y == 0) {
                    display.setText("Error: Div/0");
                    return 0;
                }
                return x / y;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BasicCalculator().setVisible(true);
        });
    }
}
