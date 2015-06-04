import grahique.Menu2;

import java.awt.EventQueue;



public class Main {

	public static void main (String[] args){
		System.out.println("hello");
		System.out.println("yo");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu2 frame = new Menu2();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		/**LOL**/
		/**test**/
	}
}
