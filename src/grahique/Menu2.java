package grahique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Menu2 extends JFrame implements ActionListener{

	private final JPanel contentPane;
	private final JPanel panNorth;
	private final JPanel panCenter;
	private final JButton btnFullScreen;
	private final JLabel imageMenu;
	private final JButton btnJouer;

	/**
	 * Create the frame.
	 */
	public Menu2() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Lightbot");
		this.setBounds(100, 100, 1200, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(5, 5));
		//this.contentPane.setBackground(new Color(255, 255, 255));


		this.panNorth = new JPanel();
		this.panNorth.setPreferredSize(new Dimension(1200, 50));
		this.panNorth.setBackground(new Color(255, 255, 255));
		this.panNorth.setLayout(null);


		this.panCenter = new JPanel();
		this.panCenter.setPreferredSize(new Dimension(800, 400));
		this.panCenter.setBackground(new Color(255, 255, 255));
		this.panCenter.setLayout(null);

		this.btnFullScreen = new JButton("Plein écran");
		this.btnFullScreen.setBackground(new Color(255, 255, 255));
		this.btnFullScreen.setBounds(15, 16, 109, 29);
		this.btnFullScreen.addActionListener(this);

		ImageIcon icon = new ImageIcon("light_bot.jpg");
		Image img = icon.getImage() ;
		Image newimg = img.getScaledInstance( 450, 220,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon( newimg );
		this.imageMenu = new JLabel(icon);
		this.imageMenu.setBounds(320, 10, 634, 242);

		this.btnJouer = new JButton("Jouer");
		this.btnJouer.setBackground(new Color(255, 255, 255));
		this.btnJouer.setBounds(527, 300, 180, 44);
		this.btnJouer.addActionListener(this);

		this.panNorth.add(this.btnFullScreen);
		this.panCenter.add(this.imageMenu);
		this.panCenter.add(this.btnJouer);

		this.contentPane.add(this.panNorth,BorderLayout.NORTH);
		this.contentPane.add(this.panCenter, BorderLayout.CENTER);


		this.setContentPane(this.contentPane);
	}



	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnFullScreen){
			if(this.getExtendedState()==this.MAXIMIZED_BOTH){
				this.setBounds(100, 100, 1200, 700);
				this.setLocationRelativeTo(null);
			}
			else
				this.setExtendedState(this.MAXIMIZED_BOTH);
		}
		else if(e.getSource()==this.btnJouer){
			SelectLevel dialog = new SelectLevel();
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
	}
}
