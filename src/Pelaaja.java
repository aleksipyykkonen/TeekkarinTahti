import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


/**
 * 
 * Pelin pelaaja. Tietää sijaintinta, rahansa ja omistamansa esineet.
 *
 */
public class Pelaaja {
	private int rahat;
	private int siirtymisvuorot;
	private Peliruutu sijainti;
	private Set<Peliruutu> kulkukelpoiset;
	private Color vari;
	private Pelilauta pelilauta;
	private String nimi;
	private int numero;
	private boolean onTahti;
	private boolean onHattu;

	/**
	 * Luodaan pelaaja, jolla on parametrina pelilauta, nimi, sekä järjestysnumero
	 * 
	 * Asetetaan rahamääräksi 300, sijainniksi ruutu numero 129 ja asetetaan 
	 * väriksi pelaajan oma väri.
	 * 
	 * @param pelilauta
	 * @param nimi
	 * @param numero
	 */
	public Pelaaja(Pelilauta pelilauta, String nimi, int numero){
		this.rahat = 300;
		this.numero = numero;
		this.kulkukelpoiset = new HashSet<Peliruutu>();

		this.onTahti=false;
		this.onHattu = false;

		this.sijainti = pelilauta.annaRuutu(129);
		pelilauta.annaRuutu(129).pelaajaSaapui(this);
		this.pelilauta = pelilauta;
		this.nimi = nimi;
		if(numero==1){
			this.vari=Vari.PELAAJA1.annaVari();
		}
		else if(numero==2){
			this.vari=Vari.PELAAJA2.annaVari();
		}
		else if(numero==3){
			this.vari=Vari.PELAAJA3.annaVari();
		}
		else if(numero==4){
			this.vari=Vari.PELAAJA4.annaVari();
		}

	}


	public int annaRahat(){
		return this.rahat;
	}
	public int annaNumero(){
		return this.numero;
	}
	public Color annaVari(){
		return this.vari;
	}
	public String annaNimi(){
		return this.nimi;
	}
	public int annaSiirtymisvuorot(){
		return this.siirtymisvuorot;
	}
	public void muutaRahamaaraa(int muutos){
		this.rahat = this.annaRahat() + muutos;
		if(this.rahat<0){
			this.rahat=0;
		}

	}


	/**
	 * Asetetaan pelaajan siirtymisvuoroiksi, ts. askelmääräksi parametrina 
	 * annettu määrä.
	 * 
	 * @param maara
	 */
	public void asetaSiirtymisvuorot(int maara){
		this.siirtymisvuorot = maara;
	}
	/**
	 * Poistetaan pelaajalta kaikki rahat.
	 * 
	 */
	public void ryovaaRahat(){
		this.rahat = 0;
	}

	public Iterator<Peliruutu> maaritaKulkukelpoiset(int silmaluku){
		return this.pelilauta.maaritaKulkukelpoiset(this.annaSijainti(), silmaluku);
		
	}
	public void asetaSijainti(Peliruutu ruutu){
		this.sijainti=ruutu;
	}
	public Peliruutu annaSijainti(){
		return this.sijainti;
	}

	public void lisaaTahti(){
		this.onTahti = true;
	}
	public boolean onkoTahti(){
		return this.onTahti;
	}
	public void lisaaHattu(){
		this.onHattu = true;
	}
	public boolean onkoHattu(){
		return this.onHattu;
	}


	public String toString(){
		return this.nimi;
	}




}

