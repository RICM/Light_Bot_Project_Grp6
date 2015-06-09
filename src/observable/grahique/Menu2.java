package observable.grahique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

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
	private JPanel panCenter;

	private JButton btnFullScreen;
	private JButton btnJouer;

	private JLabel imageMenu;

	public int ImageLargeur = 600;
	public int ImageHauteur = 200;
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
		setBackgr();
		
		this.contentPane.add(this.panCenter, BorderLayout.CENTER);



		this.setContentPane(this.contentPane);
		this.setVisible(true);
	}

	public void setBackgr() {
		ImageIcon icon = new ImageIcon("menu/back1.jpg");
		Image img = icon.getImage() ;
		Image newimg = img.getScaledInstance( this.getWidth(), this.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon( newimg );
		JLabel backMenu1 = new JLabel(icon);
		backMenu1.setBounds(0, 0, this.getWidth(), this.getHeight());
		
		URL url;
		try {
			url = new URL("https://darylmcmahon.files.wordpress.com/2014/05/snow_falling2.gif");
			ImageIcon imageIcon = new ImageIcon(url);
			JLabel label = new JLabel(imageIcon);
			label.setBounds(0, 0, getWidth(), getHeight());
			panCenter.add(label);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		panCenter.add(backMenu1);
		
	}

	public void setPanels(){
//		this.panNorth = new JPanel();
//		this.panNorth.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/6));
//		this.panNorth.setBackground(new Color(255, 255, 255));
//		this.panNorth.setLayout(null);


		this.panCenter = new JPanel();
		this.panCenter.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/3));
		this.panCenter.setBackground(new Color(255, 255, 255));
		this.panCenter.setLayout(null);

//		this.panSouth = new JPanel();
//		this.panSouth.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height/6));
//		this.panSouth.setBackground(new Color(255, 255, 255));
//		this.panSouth.setLayout(null);
	}

	public void setComponents(){
		this.btnFullScreen = new JButton("Plein ecran");
		this.btnFullScreen.setBackground(new Color(255, 255, 255));
		this.btnFullScreen.setBounds(this.getSize().width/11, getSize().height/12-20, 150, 40);
		this.btnFullScreen.addActionListener(this);

//		ImageIcon icon = new ImageIcon("light_bot.jpg");
//		Image img = icon.getImage() ;
//		Image newimg = img.getScaledInstance( ImageLargeur, ImageHauteur,  java.awt.Image.SCALE_SMOOTH ) ;
//		icon = new ImageIcon( newimg );
//		this.imageMenu = new JLabel(icon);
//		this.imageMenu.setBounds(this.getSize().width/2-315, 0, ImageLargeur, ImageHauteur);

		ImageIcon btnPlay = new ImageIcon("menu/play.png");
//		Image img = btnPlay.getImage() ;
//		Image newimg = img.getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH ) ;
//		btnPlay = new ImageIcon( newimg );
		this.btnJouer = new JButton(btnPlay);
		this.btnJouer.setBackground(new Color(255, 255, 255));
		btnJouer.setOpaque(false);
		btnJouer.setBorder(null);
		btnJouer.setBorderPainted(false);
		btnJouer.setContentAreaFilled(false);

		this.btnJouer.setBounds(this.getSize().width/2-90, ImageHauteur, 200, 200);
		this.btnJouer.addActionListener(this);

//		this.panNorth.add(this.btnFullScreen);
//		this.panCenter.add(this.imageMenu);
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
