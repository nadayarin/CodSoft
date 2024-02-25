package task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            JOptionPane.showMessageDialog(null, "Deposit successful. Current balance: " + balance);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "Withdrawal successful. Current balance: " + balance);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds or invalid amount.");
            return false;
        }
    }
}

class ATM {
    private Map<String, BankAccount> accounts;

    public ATM() {
        accounts = new HashMap<>();
    }

    public void loadAccountsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String id = parts[1];
                double balance = Double.parseDouble(parts[2]);
                accounts.put(name + "-" + id, new BankAccount(balance));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + filename);
        }
    }

    public void saveAccountsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
                String[] parts = entry.getKey().split("-");
                String name = parts[0];
                String id = parts[1];
                double balance = entry.getValue().getBalance();
                writer.println(name + "," + id + "," + balance);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file: " + filename);
        }
    }

    public void deposit(String name, String id, double amount) {
        String key = name + "-" + id;
        BankAccount account = accounts.get(key);
        if (account != null) {
            account.deposit(amount);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public void withdraw(String name, String id, double amount) {
        String key = name + "-" + id;
        BankAccount account = accounts.get(key);
        if (account != null) {
            account.withdraw(amount);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    public void checkBalance(String name, String id) {
        String key = name + "-" + id;
        BankAccount account = accounts.get(key);
        if (account != null) {
            JOptionPane.showMessageDialog(null, "Current balance: " + account.getBalance());
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }
}

public class task4 {
    private ATM atm;
    private JTextField nameField;
    private JTextField idField;
    private JTextField amountField;

    public task4(ATM atm) {
        this.atm = atm;

        JFrame frame = new JFrame("ATM Interface");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositButtonListener());
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawButtonListener());
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.addActionListener(new BalanceButtonListener());

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(balanceButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    class DepositButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String id = idField.getText();
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(name, id, amount);
        }
    }

    class WithdrawButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String id = idField.getText();
            double amount = Double.parseDouble(amountField.getText());
            atm.withdraw(name, id, amount);
        }
    }

    class BalanceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String id = idField.getText();
            atm.checkBalance(name, id);
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.loadAccountsFromFile("accounts.txt");
        new task4(atm);
    }
}

