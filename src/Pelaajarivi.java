import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Pelaajarivi, joka perii JLabelin. Näyttää pelaajan tilan, eli nimen,
 * rahamäärän ja tämän hallussa mahdollisesti olevat esineet.
 * 
 *
 */
public class Pelaajarivi extends JLabel{
	private String nimi;
	private String rahat;
	private Pelaaja pelaaja;
	private Color tekstiVari;
	private boolean piirretaanTahti;
	private boolean piirretaanLakki;
	private Image tahti;
	private Image lakki;
	
	/**
	 * Luodaan pelaajarivi parametrina annetun pelaajan pohjalta.
	 * @param pelaaja
	 */
	public Pelaajarivi(Pelaaja pelaaja){
		this.rahat=Integer.toString(pelaaja.annaRahat());
		this.nimi=pelaaja.annaNimi();
		this.pelaaja = pelaaja;
		this.setSize(210, 25);
		this.tekstiVari = Color.BLACK;
		this.piirretaanTahti = false;
		try {
			this.tahti = ImageIO.read(new File("files/tahti.png"));
			this.lakki = ImageIO.read(new File("files/lakki.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
				
	}


	/**
	 * Ylikirjoitettu PaintComponent. Piirretään pelaajan värin mukainen symboli
	 * rivin vasempaan laitaan, Pelajan nimi keskelle ja mahdollisesti oikealle
	 * kuva pelaajan hankkimasta TeekkarinTahdesta taikka Teekkarilakista.
	 */
	public void paintComponent(Graphics g){
		//piirretään pelaajan värin mukainen symboli
		g.setColor(this.pelaaja.annaVari());
		g.fillOval(0, 0, 15, 15);

	//piirretään pelajaan nimi sekä rahamäärä	
		g.setColor(this.tekstiVari);
		g.drawString(this.toString(), 20, 12);

		if(piirretaanTahti){
			g.drawImage(this.tahti,this.getWidth()-40,0,null);
		}
		if(piirretaanLakki){
			g.drawImage(this.lakki,this.getWidth()-40,0,null);
		}

	}


	public void paivita(){
		repaint();
	}

	public void korosta(){
		this.tekstiVari = pelaaja.annaVari();
		repaint();
	}
	public void asetaVari(Color vari){
		this.tekstiVari = vari;
		repaint();
	}
	public void piirraTahti(){
		this.piirretaanTahti = true;
	}
	
	public void piirraLakki(){
		this.piirretaanLakki = true;
	}

	public String annaRahat(){
		return Integer.toString(this.pelaaja.annaRahat());
	}
	public Pelaaja annaPelaaja(){
		return this.pelaaja;
	}




	public String toString(){
		return nimi + ":  Rahaa "+ this.annaRahat();
	}
}
