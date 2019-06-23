import javax.swing.*;
import java.awt.*;

public class ParentFrame extends JFrame {
		public ParentFrame() {
				super("Parent");
				setLookAndFeel();
				setSize(350, 100);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				ChildFrame cf = new ChildFrame();
				cf.setVisible(true);
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
				ParentFrame sal = new ParentFrame();
		}
}
