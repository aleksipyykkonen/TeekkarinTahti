import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;


/**
 * 
 * Pelaajia ja esineitä sisällään pitävä JButton, joista pelilauta koostuu.
 *
 */
public class Peliruutu extends JButton{
	private List<Pelaaja> pelaajatRuudussa;
	private Set<Peliruutu> naapurit;
	private int jarjestysNumero;
	private Color vari;
	private int napinKoko;
	private Pelilauta pelilauta;
	private Timer timer;
	private Esine esine;

	public Peliruutu(int numero, final Pelilauta pelilauta){
		this.jarjestysNumero = numero;
		this.vari=Vari.RUUTU.annaVari();
		this.pelaajatRuudussa = new ArrayList<Pelaaja>(); 
		this.naapurit = new HashSet<Peliruutu>();
		this.napinKoko = 16;
		this.pelilauta = pelilauta;
		this.esine = null;
		this.setBackground(Vari.LAPINAKYVA.annaVari());
		


		this.setSize(this.napinKoko, this.napinKoko);
		this.setPreferredSize(new Dimension(this.napinKoko, this.napinKoko));
		this.setMinimumSize(new Dimension(this.napinKoko, this.napinKoko));
		this.setBorderPainted(false);
		this.setOpaque(false);

		ActionListener listener = new ActionListener() {

			/*
			 *Asetetaan väriksi pelaajatRuudussa seuraavana olevan pelaajan väri, 
			 *tai ensimmäisen pelaajan väri, jos senhetkinen väri on listan viimeisen
			 *pelaajan väri.
			 * 
			 */
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<pelaajatRuudussa.size();i++){
					if(vari==pelaajatRuudussa.get(i).annaVari()){
						if(i==pelaajatRuudussa.size()-1){
						
							asetaVari(pelaajatRuudussa.get(0).annaVari());
							if(pelaajatRuudussa.size()<=1){
								timer.stop();
							}

							return;
						}
						asetaVari(pelaajatRuudussa.get(i+1).annaVari());
						return;
					}

				}
				if(!onTyhja()){
				asetaVari(pelaajatRuudussa.get(0).annaVari());
				}
			}
		};
		this.timer = new Timer(1000, listener);

	}
	public Peliruutu annaRuutu(){
		return this;
	}
	
	/**
	 * 
	 * 
	 * @return true jos ruudussa on jokin esine, false jos ei.
	 */
	public boolean onEsine(){
		if(this.esine == null){
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * Sijoitetaan peliruutuun esine ja väritetään ruutu sen mukaisesti.
	 * 
	 * @param sijoitettava esine
	 */
	public void sijoitaEsine(Esine e){
		this.esine = e;
		this.vari = Vari.ESINERUUTU.annaVari();
		repaint();
	}
	
	
	/**
	 * Palautetaan ruudussa oleva esine, ja samalla poistetaan se ruudusta.
	 * 
	 */
	public Esine poimiEsine(){
		if(this.esine!=null){
			Esine e = this.esine;		
			this.esine = null;
			
			return e;
		}
		return null;
		
	}


	/**
	 * Pelaajan saapuessa ruutuun, tämä huomioidaan ja muutetaan
	 * ruudun väri pelaajan väriksi. Mikäli ruudussa on useampia pelaajia,
	 * käynnistetään timer, joka vilkuttaa ruudussa olevien pelaajien värejä
	 * 
	 */public void pelaajaSaapui(Pelaaja saapuva){
		 this.pelaajatRuudussa.add(saapuva);
		 this.asetaVari(saapuva.annaVari());
		
		 /*
		  * pysäytetään ja käynnistetään timer uudelleen, mikäli pelaajia on jo
		  * aiemminkin ollut ruudussa useampia. 
		  */
		 if(timer.isRunning()){
			 timer.stop();
			 timer.start();
		 }

		 if(this.pelaajatRuudussa.size()>1 && !timer.isRunning()){
			 timer.start();
			 System.out.println("käynnistettiin timer");

		 }

	 }
	 /**
	  * Ylikirjoitetaan paintComponent-metodi ja luodaan toteutus, joka piirtää peliruudusta
	  * pyöreän.
	  * 
	  */
	 public void paintComponent(Graphics g){
		 Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(vari);
		//piirretään tietyn värinen ympyrä.
		 g2d.fillOval(0, 0, 16, 16);
		 
		 this.repaint();

	 }
	 


	 /**
	  * Pelaajan poistuessa ruudusta poistetaan se listasta. Ruudun jäädessä 
	  * tyhjäksi asetetaan väriksi tyhjän ruudun väri ja timerin ollessa käynnissä
	  * pysäytetään se. Timer kuitenkin käynnistetään uudelleen, mikäli ruutuun
	  * jää useampi kuin 1 pelaaja.
	  * 
	  */
	 public void pelaajaPoistui(Pelaaja poistuva){
		 this.pelaajatRuudussa.remove(poistuva);
		 if(this.pelaajatRuudussa.isEmpty()){
			 this.asetaVari(Vari.RUUTU.annaVari());
		 }
		 else if(this.timer.isRunning()){
			 this.timer.stop();
			 if(this.pelaajatRuudussa.size()>0){
				 this.timer.start();
			 }

		 }

	 }
	 /**
	  * 
	  * @param ruutu
	  * @return true jos on naapuri, false jos ei
	  */
	 public boolean onNaapuri(Peliruutu ruutu){
		 return this.naapurit.contains(ruutu);

	 }

	 /**
	  * 
	  * 
	  * @return true, jos ruudussa ei ole pelaa, false jos on.
	  */
	 public boolean onTyhja(){
		 return this.pelaajatRuudussa.isEmpty();
	 }

	 /**
	  * Lisaa ruudun naapuriksi parametrina annetun ruudun
	  * 
	  * @param lisattava
	  */
	 public void lisaaNaapuri(Peliruutu lisattava){
		 if(onNaapuri(lisattava)){
			 return;
		 }
		 this.naapurit.add(lisattava); 
	 }
	 /**
	  * Poistaa peliruudun naapurilistasta halutun ruudun
	  * 
	  * @param poistettava
	  */
	 public void poistaNaapuri(Peliruutu poistettava){
		 this.naapurit.remove(poistettava);

	 }
	 public String annaNaapurit(){
		 return "Naapureina: " + naapurit.toString();
	 }

	 public String toString(){

		 return  Integer.toString(this.jarjestysNumero);
	 }
	
	 /**
	  * 
	  * @return Iteraattori peliruudun nappureista
	  */
	 public Iterator<Peliruutu> naapuriIteraattori(){
		 return naapurit.iterator();
	 }

	 /**
	  * Asetetaan peliruudulle haluttu väri.
	  * 
	  * @param c, haluttu vari
	  */
	 public void asetaVari(Color c){
		 this.vari = c;
		 repaint();
	 }

	 /**
	  * Siirretään pelaaja ko. peliruutuun ja poistetaan se ruudusta, jossa se 
	  * oli aiemmin.
	  * 
	  * @param pelaaja
	  */
	 public void siirraTahan(Pelaaja pelaaja){
		 if(pelaaja.annaSijainti()!=null){
			 pelaaja.annaSijainti().pelaajaPoistui(pelaaja);
		 }
		 pelaaja.asetaSijainti(this);
		 this.pelaajaSaapui(pelaaja);
		 return;



	 }



}
