import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class NewMailWriter extends JFrame implements ActionListener {
    JTextField to = new JTextField(24);
    JTextField subject = new JTextField(24);
    JTextArea message = new JTextArea(4, 22);

    NewMailWriter() {
        super("Write an E-Mail");
        setSize(370, 270);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
        setLayout(flow);
        JPanel row1 = new JPanel();
        JLabel toLabel = new JLabel("To:");
        row1.add(toLabel);
        row1.add(to);
        add(row1);
        JPanel row2 = new JPanel();
        JLabel subjectLabel = new JLabel("Subject:");
        row2.add(subjectLabel);
        row2.add(subject);
        add(row2);
        JPanel row3 = new JPanel();
        JLabel messageLabel = new JLabel("Message:");
        row3.add(messageLabel);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(message,
           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        row3.add(scroll);
        add(row3);
        JPanel row4 = new JPanel();
        JButton send = new JButton("Send");
        send.addActionListener(this);
        row4.add(send);
        add(row4);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("To: " + to.getText());
        System.out.println("Subject: " + subject.getText());
        System.out.println("Message: " + message.getText());
    }

    public static void main(String[] arguments) {
        NewMailWriter mail = new NewMailWriter();
    }
}