package observable.grahique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SelectLevel extends JDialog implements ActionListener{

	private final JPanel contentPanel;
	private final JPanel panCenter;
	private final JLabel labLevel;
	private JButton btnPlay;
	private JButton btnNext;
	private JButton btnPrec;
	//	private final JButton btnRetour;
	private final Menu2 frame;
	private int level = 1;


	/**
	 * Create the dialog.
	 */
	public SelectLevel(Menu2 fenetre) {
		this.frame=fenetre;
		this.contentPanel = new JPanel();
		this.setBounds(100, 100, 1200, 700);
		this.setTitle("Sélection du niveau");
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(new FlowLayout());
		this.contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setResizable(false);
		this.setModal(true);

		//		this.panNorth = new JPanel();
		//		this.panNorth.setPreferredSize(new Dimension(1200, 75));
		//		this.panNorth.setBackground(new Color(255, 255, 255));
		//		this.panNorth.setLayout(null);
		//
		//		this.panSouth = new JPanel();
		//		this.panSouth.setPreferredSize(new Dimension(1200, 75));
		//		this.panSouth.setBackground(new Color(255, 255, 255));
		//		this.panSouth.setLayout(null);

		this.panCenter = new JPanel();
		this.panCenter.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.panCenter.setBackground(new Color(255, 255, 255));
		this.panCenter.setLayout(null);

		this.labLevel = new JLabel("Basics");
		this.labLevel.setFont(new Font("Serif", Font.PLAIN, 25));
		this.labLevel.setBounds(this.getSize().width/2,0,182,34);

		//		this.panNorth.add(this.labLevel);

		this.setBtn();
		this.setBackgr();


		//		this.btnRetour = new JButton("Menu");
		//		this.btnRetour.setBackground(new Color(255, 255, 255));
		//		this.btnRetour.setBounds(15, 16, 109, 29);
		//		this.btnRetour.addActionListener(this);
		//		this.panSouth.add(this.btnRetour);

		//		this.contentPanel.add(this.panNorth,BorderLayout.NORTH);
		this.contentPanel.add(this.panCenter, BorderLayout.CENTER);
		//		this.contentPanel.add(this.panSouth,BorderLayout.SOUTH);


		this.setContentPane(this.contentPanel);

	}


	public void setBtn() {
		this.btnPlay = new JButton(""+this.level);
		this.btnPlay.setFont(new Font("Brush Script MT", Font.PLAIN, 45));
		this.btnPlay.setBounds(this.getWidth()/2-50, this.getHeight()/2, 100, 100);
		this.btnPlay.setBackground(new Color(255, 255, 255));
		this.btnPlay.addActionListener(this);
		this.panCenter.add(this.btnPlay);

		/*Bouton suivant, incrémente le niveau*/
		ImageIcon btnPlay = new ImageIcon("selectLvl/next.png");
		Image img = btnPlay.getImage() ;
		Image newimg = img.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ) ;
		btnPlay = new ImageIcon( newimg );
		this.btnNext = new JButton(btnPlay);
		this.btnNext.setBackground(new Color(255, 255, 255));
		this.btnNext.setBounds(this.getWidth()-250,this.getHeight()/2,100,100);
		this.btnNext.setOpaque(false);
		this.btnNext.setBorder(null);
		//		this.btnNext.setBorderPainted(false);
		//		this.btnNext.setContentAreaFilled(false);
		this.btnNext.addActionListener(this);
		this.panCenter.add(this.btnNext);

		/*Bouton précédent, décrémente le niveau*/
		ImageIcon btnPrec = new ImageIcon("selectLvl/prec.png");
		Image imgPrec = btnPrec.getImage() ;
		Image newimgPrec = imgPrec.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ) ;
		btnPrec = new ImageIcon( newimgPrec );
		this.btnPrec = new JButton(btnPrec);
		this.btnPrec.setBackground(new Color(255, 255, 255));
		this.btnPrec.setBounds(150,this.getHeight()/2,100,100);
		this.btnPrec.setOpaque(false);
		this.btnPrec.setBorder(null);
		//		this.btnNext.setBorderPainted(false);
		//		this.btnNext.setContentAreaFilled(false);
		this.btnPrec.addActionListener(this);
		this.panCenter.add(this.btnPrec);

	}


	public void setBackgr() {
		ImageIcon icon = new ImageIcon("selectLvl/back.jpg");
		Image img = icon.getImage() ;
		Image newimg = img.getScaledInstance( this.getWidth(), this.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon( newimg );
		JLabel backMenu1 = new JLabel(icon);
		backMenu1.setBounds(0, -10, this.getWidth(), this.getHeight());

		this.panCenter.add(backMenu1);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//		if(e.getSource()==this.btnRetour){
		//			this.dispose();
		//		}

		if(e.getSource()==this.btnPlay){
			this.dispose();
			Jeu game = new Jeu(this.level,this.frame);
		}
		if(e.getSource()==this.btnNext){
			this.level++;
			this.btnPlay.setText(""+this.level);
		}
		if(e.getSource()==this.btnPrec){
			this.level--;
			if(this.level<1)
				this.level =1;
			this.btnPlay.setText(""+this.level);
		}

	}

}
