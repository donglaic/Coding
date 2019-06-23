import javax.swing.*;
import java.awt.*;

public class Playback extends JFrame {
		public Playback() {
				super("Playback");
				setLookAndFeel();
				setSize(600, 300);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FlowLayout flo = new FlowLayout();
				setLayout(flo);
				JButton play = new JButton("Play");
				JButton stop = new JButton("Stop");
				JButton pause = new JButton("Pause");
				JLabel pageLabel = new JLabel("Web page address: ",JLabel.LEFT);
				JTextField pageAddress = new JTextField(20);
				//add(play);
				//add(stop);
				//add(pause);
				add(pageLabel);
				add(pageAddress);
				setVisible(true);
		}

		private void setLookAndFeel() {
				try {
						UIManager.setLookAndFeel(
										"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
										);
				} catch (Exception exc) {
						//ignore error
				}
		}

		public static void main(String[] args) {
				Playback sal = new Playback();
		}
}
