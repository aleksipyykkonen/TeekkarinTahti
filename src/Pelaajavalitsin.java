import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class Pelaajavalitsin extends JButton{
	private int pelaajaMaara;
	private String teksti;
	private Ikkuna ikkuna;

	public Pelaajavalitsin(String teksti, final int pelaajaMaara, final Ikkuna ikkuna){
		this.pelaajaMaara = pelaajaMaara;
		this.teksti=teksti;
		this.ikkuna = ikkuna;
		this.setRolloverEnabled(false);
		
		this.setSize(115, 30);
		this.setPreferredSize(new Dimension(115, 30));

		
		this.setBackground(Vari.TAUSTAVARI.annaVari());
		this.lisaaKuuntelija();
		
		
	}
	
	public void lisaaKuuntelija(){
		this.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				ikkuna.asetaPelaajaMaara(pelaajaMaara);
				System.out.println("pelaajia on " + Pelaajavalitsin.this.pelaajaMaara);
				if(Pelaajavalitsin.this.pelaajaMaara==4){
					ikkuna.asetaBottiMaara(0);
					ikkuna.luoPeli();
					return;
				}
				ikkuna.kysyBottiMaara();
				
				
			}
		});
	}
	
	public void paint(Graphics g){
		g.setColor(Vari.TAUSTAVARI.annaVari());
		g.fillRoundRect(0, 0, 110, 25, 10, 10);
		
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, 110, 25, 10, 10);
		
		g.drawString(this.teksti, 15, 15);
	}
	
	public String annaTeksti(){
		return this.teksti;
	}
	public Ikkuna annaIkkuna(){
		return this.ikkuna;
	}
	
	
	
}
