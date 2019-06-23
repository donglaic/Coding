import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class PrimeFinder2 extends JFrame implements Runnable, ActionListener {
    Thread go;
    JLabel howManyLabel = new JLabel("Quantity: ");
    JTextField howMany = new JTextField("400", 10);
    JButton display = new JButton("Display primes");
    JButton stop = new JButton("Stop");
    JTextArea primes = new JTextArea(8, 40);

    PrimeFinder2() {
        super("Find Prime Numbers");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout bord = new BorderLayout();
        setLayout(bord);
        display.addActionListener(this);
        stop.addActionListener(this);
        JPanel topPanel = new JPanel();
        topPanel.add(howManyLabel);
        topPanel.add(howMany);
        topPanel.add(display);
        stop.setEnabled(false);
        topPanel.add(stop);
        add(topPanel, BorderLayout.NORTH);
        primes.setLineWrap(true);
        JScrollPane textPane = new JScrollPane(primes);
        add(textPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == display) {
            display.setEnabled(false);
            stop.setEnabled(true);
            if (go == null) {
                go = new Thread(this);
                go.start();
            }
        } else {
            stop.setEnabled(false);
            if (go != null) {
                go = null;
            }
        }
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        int quantity = Integer.parseInt(howMany.getText());
        int numPrimes = 0;
        // candidate: the number that might be prime
        int candidate = 2;
        primes.append("First " + quantity + " primes:");
        while ((numPrimes < quantity) & (go == thisThread)) {
            if (isPrime(candidate)) {
                primes.append(candidate + " ");
                numPrimes++;
            }
            candidate++;
        }
        stop.setEnabled(false);
    }

    public static boolean isPrime(int checkNumber) {
        double root = Math.sqrt(checkNumber);
        for (int i = 2; i <= root; i++) {
            if (checkNumber % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] arguments) {
        PrimeFinder2 app = new PrimeFinder2();
    }
}