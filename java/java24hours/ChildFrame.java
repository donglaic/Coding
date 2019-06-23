import javax.swing.*;
import java.awt.*;

public class ChildFrame extends JFrame {
		public ChildFrame() {
				super("Child");
				setLookAndFeel();
				setSize(350, 100);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				ChildFrame sal = new ChildFrame();
		}
}
