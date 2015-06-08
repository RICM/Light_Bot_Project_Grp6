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

	//Panel
	private final JPanel contentPane;
	private JPanel panNorth;
	private JPanel panCenter;
	private JPanel panSouth;

	private JButton btnFullScreen;
	private JButton btnJouer;

	private JLabel imageMenu;

	public int ImageLargeur = 600;
	public int ImageHauteur = 280;
	/**
	 * Create the frame.
	 */
	public Menu2() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Lightbot");
		this.setResizable(false);
		this.setBounds(100, 100, 1200, 700);
		this.setMinimumSize(new Dimension(1000,700));
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(5, 5));
		//this.contentPane.setBackground(new Color(255, 255, 255));

		setPanels();
		setComponents();

		this.contentPane.add(this.panNorth,BorderLayout.NORTH);
		this.contentPane.add(this.panCenter, BorderLayout.CENTER);
		this.contentPane.add(panSouth,BorderLayout.SOUTH);


		this.setContentPane(this.contentPane);
	}

	public void setPanels(){
		this.panNorth = new JPanel();
		this.panNorth.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/6));
		this.panNorth.setBackground(new Color(255, 255, 255));
		this.panNorth.setLayout(null);


		this.panCenter = new JPanel();
		this.panCenter.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/3));
		this.panCenter.setBackground(new Color(255, 255, 255));
		this.panCenter.setLayout(null);

		this.panSouth = new JPanel();
		this.panSouth.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/6));
		this.panSouth.setBackground(new Color(255, 255, 255));
		this.panSouth.setLayout(null);
	}

	public void setComponents(){
		this.btnFullScreen = new JButton("Plein ecran");
		this.btnFullScreen.setBackground(new Color(255, 255, 255));
		this.btnFullScreen.setBounds(this.getSize().width/11, getSize().height/12-20, 150, 40);
		this.btnFullScreen.addActionListener(this);

		ImageIcon icon = new ImageIcon("light_bot.jpg");
		Image img = icon.getImage() ;
		Image newimg = img.getScaledInstance( ImageLargeur, ImageHauteur,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon( newimg );
		this.imageMenu = new JLabel(icon);
		this.imageMenu.setBounds(this.getSize().width/2-315, 0, ImageLargeur, ImageHauteur);

		this.btnJouer = new JButton("Jouer");
		this.btnJouer.setBackground(new Color(255, 255, 255));
		this.btnJouer.setBounds(this.getSize().width/2-90, ImageHauteur + 50, 180, 44);
		this.btnJouer.addActionListener(this);

		this.panNorth.add(this.btnFullScreen);
		this.panCenter.add(this.imageMenu);
		this.panCenter.add(this.btnJouer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnFullScreen){
			if(this.getExtendedState()==this.MAXIMIZED_BOTH){
				this.setBounds(100, 100, 1200, 700);
				this.imageMenu.setBounds(this.getSize().width/2-315, 0, ImageLargeur, ImageHauteur);
				this.btnJouer.setBounds(this.getSize().width/2-90, ImageHauteur + 50, 180, 44);
				this.setLocationRelativeTo(null);
			}
			else{
				this.setExtendedState(this.MAXIMIZED_BOTH);
				this.imageMenu.setBounds(this.getSize().width/2-315, 0, 700, ImageHauteur*3/2);
				this.btnJouer.setLocation(this.getSize().width/2-90, 2*this.getSize().height/6+20);
			}
		}
		else if(e.getSource()==this.btnJouer){
			SelectLevel dialog = new SelectLevel(this);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
	}
}
