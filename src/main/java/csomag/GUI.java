package csomag;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * A GUI-t reprezentáló osztály.
 * @author Czuczi
 *
 */
public class GUI implements ActionListener{
	private final JPanel GUI = new JPanel(new BorderLayout(3, 3));
	private JButton[][] negyzetek = new JButton[8][8];
	private JPanel sakkTabla;
	private static final String OSZLOPOK = "abcdefgh";
	private static final String SOROK = "12345678";
	private String gombNev1 = "", gombNev2 = "";
	
	private static Tabla t = new Tabla();
	private static Jatekos vilagos = new Jatekos(true);
	private static Jatekos sotet = new Jatekos(false);
	
	private static JFrame jf;
	
	/**
	 * A GUI létrehozásához szükséges konstruktor.
	 */
	public GUI() {
		initGUI();
	}

	/**
	 * Visszaadja a GUI alapjául szolgáló JPanel-t.
	 * @return Olyan JPanel-t ad vissza, ami a GUI alapja.
	 */
	public final JComponent getGUI(){
		return GUI;
	}
	
	/**
	 * A sakktábla alapjául szolgáló JPanel-t adja vissza.
	 * @return Olyan JPanelt ad vissza, ami a sakktábla alapja.
	 */
	public final JComponent getSakkTabla(){
		return sakkTabla;
	}
	
	/**
	 * A GUI inicializálására szolgál.
	 */
	public final void initGUI(){
		GUI.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar jtb = new JToolBar();
		jtb.setFloatable(false);
		GUI.add(jtb, BorderLayout.PAGE_END);
		
		JButton uj = new JButton("Új");
		JButton mentes = new JButton("Mentés");
		JButton betoltes = new JButton("Betöltés");
		
		uj.setName("új");
		mentes.setName("mentés");
		betoltes.setName("betöltés");
		
		uj.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				t = new Tabla();
				vilagos = new Jatekos(true);
				sotet = new Jatekos(false);
				gombNev1 = gombNev2 = "";
				
				frissit();
			}
		});
		
		mentes.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				XML xml = new XML();
				xml.XMLkeszito(t, vilagos, sotet, getClass().getClassLoader().getResource("mentes.xml").toString());
			}
		});
		
		betoltes.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				XML xml = new XML();
				xml.XMLbetolto(t, vilagos, sotet, getClass().getClassLoader().getResource("mentes.xml").toString());
				
				frissit();
			}
		});
		
		jtb.add(uj);
		jtb.add(mentes);
		jtb.add(betoltes);
		
		sakkTabla = new JPanel(new GridLayout(0, 9));
		
		GUI.add(sakkTabla);
		
		Insets insets = new Insets(0, 0, 0, 0);
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				JButton b = new JButton();
				
				b.setMargin(insets);
				
				if((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0))
					b.setBackground(Color.WHITE);
				else
					b.setBackground(Color.GRAY);
				
				negyzetek[i][j] = b;
				
				negyzetek[i][j].setName(OSZLOPOK.substring(j, j+1) + (8-i));
				
				b.setPreferredSize(new Dimension(68, 68));
				
				b.addActionListener(this);
			}
		}
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(j == 0)
					sakkTabla.add(new JLabel("" + (8-i)));
				sakkTabla.add(negyzetek[i][j]);
			}
		}
		
		sakkTabla.add(new JLabel(""));
		
		for(int i = 0; i < 8; i++){
			sakkTabla.add(new JLabel(OSZLOPOK.substring(i, i+1), SwingConstants.CENTER));
		}
		
	}
	
	/**
	 * A sakktáblán történő gomblenyomásokat figyelő metódus.
	 * @param e Egy esemény, ami itt egy gomb lenyomását jelenti.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			if(OSZLOPOK.contains(((JButton)e.getSource()).getName().substring(0, 1)) &&
			   SOROK.contains(((JButton)e.getSource()).getName().substring(1, 2)))
			{
				if(gombNev1.isEmpty())
					gombNev1 = ((JButton)e.getSource()).getName();
				else{
					gombNev2 = ((JButton)e.getSource()).getName();
					
					if(t.isKovetkezo()){
						vilagos.lep(gombNev1, gombNev2, t);
					}
					if(!t.isKovetkezo()){
						sotet.lep(gombNev1, gombNev2, t);
					}
					
					Jatekos.logger.info(t.isKovetkezo() ? "Világos következik" : "Sötét következik");
					gombNev1 = gombNev2 = "";
					
					frissit();
					
					if(t.vege()){
						JOptionPane.showMessageDialog(GUI, t.getVilagosBabuSzam() == 0 ? "Piros nyert!" : "Kék nyert!", "Végeredmény", 1);
						Jatekos.logger.info(t.getVilagosBabuSzam() == 0 ? "Piros nyert!" : "Kék nyert!");
					}
				}
			}
		}
	}
	
	/**
	 * A felhasználói felület újrarajzolására szolgál.
	 */
	public void frissit(){
		ImageIcon kekd = new ImageIcon(getClass().getClassLoader().getResource("kekd.png"));
		ImageIcon kek = new ImageIcon(getClass().getClassLoader().getResource("kek.png"));
		ImageIcon pirosd = new ImageIcon(getClass().getClassLoader().getResource("pirosd.png"));
		ImageIcon piros = new ImageIcon(getClass().getClassLoader().getResource("piros.png"));
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				negyzetek[7-i][j].setIcon(null);
				if(t.getTabla()[i][j] instanceof Babu){
					if(t.getTabla()[i][j].isVilagos()){
						if(t.getTabla()[i][j].isDama()){
							negyzetek[7-i][j].setIcon(kekd);
						}
						else{
							negyzetek[7-i][j].setIcon(kek);
						}
					}
					else{
						if(t.getTabla()[i][j].isDama()){
							negyzetek[7-i][j].setIcon(pirosd);
						}
						else{
							negyzetek[7-i][j].setIcon(piros);
						}
					}
				}
			}
		}
		jf.repaint();
	}
	
	/**
	 * A program main függvénye, grafikus és játékfelületet hoz létre.
	 * @param args Prancssori argumentumok listája.
	 */
	public static void main(String[] args) {
			Runnable r = new Runnable() {
			
			public void run() {
		
				GUI g = new GUI();
				
				jf = new JFrame();
				
				jf.add(g.getGUI());
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setLocationByPlatform(true);
				
				jf.pack();
				
				jf.setMinimumSize(jf.getSize());
				
				jf.setLocation(0, 0);
				
				jf.setResizable(false);
				
				jf.setVisible(true);
				
				ImageIcon kekd = new ImageIcon(getClass().getClassLoader().getResource("kekd.png"));
				ImageIcon kek = new ImageIcon(getClass().getClassLoader().getResource("kek.png"));
				ImageIcon pirosd = new ImageIcon(getClass().getClassLoader().getResource("pirosd.png"));
				ImageIcon piros = new ImageIcon(getClass().getClassLoader().getResource("piros.png"));
				
				for(int i = 0; i < 8; i++){
					for(int j = 0; j < 8; j++){
						if(t.getTabla()[i][j] instanceof Babu){
							if(t.getTabla()[i][j].isVilagos()){
								if(t.getTabla()[i][j].isDama()){
									g.negyzetek[7-i][j].setIcon(kekd);
								}
								else{
									g.negyzetek[7-i][j].setIcon(kek);
								}
							}
							else{
								if(t.getTabla()[i][j].isDama()){
									g.negyzetek[7-i][j].setIcon(pirosd);
								}
								else{
									g.negyzetek[7-i][j].setIcon(piros);
								}
							}
						}
					}
				}
				jf.repaint();
			}
		};
		
		SwingUtilities.invokeLater(r);
	}
}
