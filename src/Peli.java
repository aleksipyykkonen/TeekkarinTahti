import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * 
 * Pelin toiminnallisuudesta vastaava luokka. Käsittelee pelaajia, niiden 
 * vuorollisuutta ja niiden toimintoja. 
 *
 */

public class Peli {
	private Pelilauta pelilauta;
	private List<Pelaaja> pelaajat;
	private List<Peliruutu> peliruudut;
	private List<Peliruutu> esineruudut;
	private boolean jatkuu;
	private boolean teekkarinTahtiLoydetty;
	private Random rand;
	private Pelaaja vuorossa;
	private Ikkuna ikkuna;
	private List<Esine> esineet;
	private boolean lunastetaanko;
	private int lunastusSilmaluku;

	/**
	 * 
	 * Asettaa pelilaudan ruuduilla kuuntelijat ja luo niistä itselleen listan.
	 * Luo listat myös pelaajista, jotta niiden vuorollisuutta voidaan vaihdella.
	 * 
	 * @param pelilauta
	 * @param pelaajienMaara
	 * @param bottienMaara
	 * @param ikkuna
	 */
	public Peli(Pelilauta pelilauta, int pelaajienMaara, int bottienMaara, Ikkuna ikkuna){
		this.pelilauta = pelilauta;
		this.pelaajat= new ArrayList<Pelaaja>();
		this.peliruudut = new ArrayList<Peliruutu>();
		this.esineruudut = new ArrayList<Peliruutu>();
		this.ikkuna = ikkuna;
		this.teekkarinTahtiLoydetty=false;

		this.lunastetaanko = false;


		this.rand = new Random();

		/*
		 * 
		 * Kopioi pelilaudan listat omaan listaansa, ja asettaa peliruuduille
		 * kuunelijat
		 */
		for(int i=0;i<pelilauta.annaPeliruudut().size();i++){

			this.peliruudut.add(pelilauta.annaPeliruudut().get(i));
			final Peliruutu nappi = this.peliruudut.get(i);

			nappi.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					Pelaaja pelaaja = annaVuorossaOleva();	
					Iterator<Peliruutu> it = pelaaja.maaritaKulkukelpoiset(pelaaja.annaSiirtymisvuorot());
					while (it.hasNext()){
						if(it.next().equals(nappi)){
							/*
							 * Mikäli vuorossa oleva pelaaja voi siirtyä painettuun 
							 * peliruutuun, se siirretään siihen.
							 */
							
							nappi.siirraTahan(pelaaja);
							pelaaja.asetaSiirtymisvuorot(0);
							paivitaNakyma();
							
							/*
							 *Ilmoitetaan, mikäli pelaaja on voittanut pelin siirrytyään
							 *ko. ruutuun.
							 */
							if(voittiko1()){
								String teksti = vuorossa +" palautti teekkarintähden " +
										"olkkarille ja voitti pelin!";
								JOptionPane.showMessageDialog(annaIkkuna(),teksti );
								Peli.this.ikkuna.asetaTeksti(teksti);
								lopetaPeli();
							}
							if(voittiko2()){
								String teksti = vuorossa +" vei Teekkarilakin " +
										"olkkarille ja voitti pelin!";
								JOptionPane.showMessageDialog(annaIkkuna(), teksti);
								Peli.this.ikkuna.asetaTeksti(teksti);
								lopetaPeli();
							}
						}
					}
				}

			});
		}


		this.esineet = new ArrayList<Esine>();
		luoKaikkiEsineet();
		sijoitaEsineet();
		/*
		 * luodaan ja sijoitetaan kaikki halutut esineet peliruutuihin
		 */
		this.jatkuu = true;

		
		/*
		 * Luodaan pelaajat ja botit, sekä niiden pelaajaRivit ja lisätään ne listaan. 
		 */
		for(int n=1;n<=pelaajienMaara+bottienMaara;n++){
			if(n>pelaajienMaara){
				AIPelaaja ap = new AIPelaaja(pelilauta, "Nobo"+(n-pelaajienMaara), n, this);
				this.pelaajat.add(ap);
				ikkuna.luoPelaajaRivi(ap, n);

			}
			else{
				/*
				 * kysytään luotaessa pelajan nimeä.
				 */
				String pelaajanNimi = JOptionPane.showInputDialog(this.ikkuna, "Anna pelaajan " + n + " nimi.", "Nimeä pelaaja.", JOptionPane.QUESTION_MESSAGE);
				if(pelaajanNimi==null || pelaajanNimi.equals("")){
					pelaajanNimi = "Pelaaja" + n;
				}
				Pelaaja p = new Pelaaja(pelilauta, pelaajanNimi, n);
				this.pelaajat.add(p);
				ikkuna.luoPelaajaRivi(p, n);
			}
		}

		this.vuorossa = pelaajat.get(0);
		this.ikkuna.disableLunastus();



	}

	public Pelaaja annaVuorossaOleva(){
		return this.vuorossa;
	}

	public boolean tahtiLoydetty(){
		return this.teekkarinTahtiLoydetty;
	}
	public Ikkuna annaIkkuna(){
		return this.ikkuna;
	}

	/**
	 * Mikäli pelaaja on maaliruudussa ja omistaa Tähden, hän on voittanut pelin
	 * @return
	 */
	public boolean voittiko1(){
		if(this.vuorossa.annaSijainti().equals(this.peliruudut.get(129))&&this.vuorossa.onkoTahti()){
			return true;
		}
		return false;
	}

	/**
	 * Mikäli pelaaja on tuonut maaliin Teekkarilakin tähden löytymisen jälkeen, 
	 * hän voittaa pelin.
	 * 
	 * @return
	 */
	public boolean voittiko2(){
		if(this.vuorossa.annaSijainti().equals(this.peliruudut.get(129))&&this.teekkarinTahtiLoydetty &&
				this.vuorossa.onkoHattu()){
			return true;
		}
		return false;
	}


	/**
	 * Arpoo nopan silmäluvuksi luvun 1-6 ja asettaa sen pelaajan siirtymis-
	 * vuorojen arvoksi. Päivittää kyseistä silmälukua vastaavan nopan kuvan
	 * Ikkunaan.
	 */
	public void heitaNoppaa(){
		int silmaluku = rand.nextInt(6)+1;
		this.ikkuna.paivitaSilmaluku(silmaluku);

		this.vuorossa.asetaSiirtymisvuorot(silmaluku);
		this.pelilauta.varitaKuljettavat(this.annaVuorossaOleva(), silmaluku);
		this.ikkuna.disableNoppa();


		return;
	}
	
	/**
	 * Lopettaa vuoron, asettaa tapahtunutta kuvaavan tekstin ikkunan tekstikenttään
	 * ja siirtää vuoron seuraavalle pelaajalle.
	 * 
	 */
	public void lopetaVuoro(){
		String lopettanut = this.vuorossa.toString();
		if(!jatkuu){
			this.ikkuna.asetaTeksti("Peli loppui.");
			return;
		}

		seuraava();
		this.ikkuna.asetaTeksti(lopettanut + " lopetti vuoronsa.");

		/*
		 * mikäli vuorossa oleva pelaaja on botti, suorittaa se oman vuoronsa
		 * automaattisesti.
		 */
		if(vuorossa instanceof AIPelaaja){
			((AIPelaaja) vuorossa).suoritaVuoro();
		}


	}

	public Iterator<Peliruutu> esineruutuIteraattori(){
		return this.esineruudut.iterator();
	}


	/**
	 * Lunastetaan esine vuorossa olevan pelaajan sijainnista. Poistetaan esine
	 * ruudusta ja poistetaan ruutu esineruutu-listasta.
	 * 
	 */
	public void lunastaEsine(){
		Esine poimittu;
		
		/*
		 * Mikäli pelaajalla on ainakin 100e, otetaan siltä lunastusmaksu 
		 * ja asetetaan ruudussa ollut esine poimituksi.
		 */
		if(this.vuorossa.annaRahat()>=100){
			this.vuorossa.muutaRahamaaraa(-100);
			poimittu = this.vuorossa.annaSijainti().poimiEsine();
		}
		else{
		
			/*
			 * botin koittaessa lunastaa merkkiä rahattomana, simuloidaan
			 *"nopan heittäminen" esineen lunastamiseksi
			 */
			if(this.vuorossa instanceof AIPelaaja){
				this.lunastusSilmaluku = rand.nextInt(6)+1;
				this.ikkuna.paivitaSilmaluku(lunastusSilmaluku);
			}
			else{
				/*
				 * Pelaajan ollessa rahaton, avataan dialogi, jossa kerrotaan
				 * tilanteesta ja käsketään pelajaan heittää noppaa lunastaakseen
				 * merkin.
				 */
				
				Object[] nappi = {"Heitä noppaa!"};
				JOptionPane.showOptionDialog(this.ikkuna,
						this.vuorossa + ":n rahat eivät riittäneet lunastamaan merkkiä. \n Mikäli pelaaja heittää " +
								"nopalla silmäluvun 4-6, saa hän\n kuitenkin lunastettua merkin ilmaiseksi.","Merkin lunastus",
								JOptionPane.OK_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,     
								nappi,  
								nappi[0]); 
				lunastusSilmaluku= rand.nextInt(6)+1;
				this.ikkuna.paivitaSilmaluku(lunastusSilmaluku);
			}

			if(lunastusSilmaluku>=4){
				poimittu= this.vuorossa.annaSijainti().poimiEsine();
			}
			else{
				poimittu =null;
				this.ikkuna.asetaTeksti(this.vuorossa + " ei onnistunut lunastamaan" +
						" merkkiä.");

			}
		}
		lunastusSilmaluku =0;
		this.ikkuna.disableLunastus();


		/*
		 *Toimitaan merkin tyypin mukaisesti. Lisätään tai poistetaan pelaajalta
		 *rahaa taikka annetaan tälle jokin esine(tähti tai lakki) 
		 * 
		 */
		if(poimittu!=null){
			this.esineruudut.remove(this.vuorossa.annaSijainti());
			Esinetyyppi tyyppi = poimittu.annaTyyppi();
			if(tyyppi.equals(Esinetyyppi.OPINTOTUKI)){

				this.vuorossa.muutaRahamaaraa(300);

				this.ikkuna.asetaTeksti(this.vuorossa + " sai ylimääräiset opintotuet, 300e");
			}
			else if(tyyppi.equals(Esinetyyppi.FYFFE)){

				this.vuorossa.muutaRahamaaraa(500);
				this.ikkuna.asetaTeksti(this.vuorossa + " löysi stipendirahat ja sai 500e");
			}
			else if(tyyppi.equals(Esinetyyppi.MASSIPAALIKKO)){
				this.vuorossa.muutaRahamaaraa(800);
				this.ikkuna.asetaTeksti(this.vuorossa + " löysi Metoksen pikkujoulukassan " +
						"ja sai 800e lisää.");
			}
			else if(tyyppi.equals(Esinetyyppi.ROSVO)){
				this.vuorossa.ryovaaRahat();
				this.ikkuna.asetaTeksti(this.annaVuorossaOleva() + " " +
						"kohtasi rosvon ja menetti tälle kaikki rahansa");
			}
			else if(tyyppi.equals(Esinetyyppi.TYHJA)){
				this.ikkuna.asetaTeksti(this.vuorossa + " lunasti merkin, mutta se " +
						"olikin tyhjä.");
			}
			else if(tyyppi.equals(Esinetyyppi.TEEKKARINTAHTI)){
				this.teekkarinTahtiLoydetty=true;
				this.vuorossa.lisaaTahti();
				//pelaajien numerointi alkaa numerosta 1, mutta pelaajalistassa
				//niiden indeksi on yhden pienempi
				this.ikkuna.naytaTahti(this.vuorossa.annaNumero()-1);

				this.ikkuna.asetaTeksti(this.vuorossa + " löysi Teekkarintähden!" +
						" Nyt kiirrellä viemään se kohti Olkkaria!");

			}
			else if(tyyppi.equals(Esinetyyppi.HEVOSENKENKA)){
				if(this.teekkarinTahtiLoydetty==true){
					this.vuorossa.lisaaHattu();
					this.ikkuna.naytaLakki(this.vuorossa.annaNumero()-1);

					this.ikkuna.asetaTeksti(vuorossa + " löysi " +
							"teekkarilakin  ja voi vielä voittaa viemällä sen " +
							"Olkkarille  ennen kuin Teekkarintähti viedään sinne! ");
				}
				else{
					this.ikkuna.asetaTeksti(vuorossa + " löysi teekkarilakin, " +
							"muttei tee sillä mitään.");
				}
			}
			this.ikkuna.paivitaPelaajarivit();
		}
	}

	/**
	 * Päivittää pelilaudan ruudut oikean värisiksi.
	 * 
	 */
	public void paivitaNakyma(){

		this.ikkuna.disableLunastus();
		this.ikkuna.paivitaPelaajarivit();
		this.vuorossa.asetaSiirtymisvuorot(0);
		if(this.vuorossa.annaSijainti().onEsine()){
			this.ikkuna.enableLunastus();
		}
		for(int n=0;n<this.peliruudut.size();n++){
			Peliruutu ruutu = this.peliruudut.get(n);
			if(ruutu.onTyhja()&& !this.peliruudut.get(n).onEsine()){
				ruutu.asetaVari(Vari.RUUTU.annaVari());
			}
			if(ruutu.onEsine() && ruutu.onTyhja()){
				ruutu.asetaVari(Vari.ESINERUUTU.annaVari());

			}
		}
	}





	/**
	 * Siirretään pelivuoro seuraavalle pelaajalle ja asetetaan tarvittavat
	 * painikkeet taas toimiviksi. Mikäli vuorossa oleva on listan viimeinen 
	 * pelaaja, pelivuoro siirtyy listan ensimmäiselle pelaajalle.
	 * 
	 */
	public void seuraava(){

		for(int n=0;n<this.pelaajat.size();n++){
			if(this.pelaajat.get(n).equals(this.vuorossa)){
				if(n==this.pelaajat.size()-1){
					this.vuorossa=this.pelaajat.get(0);
					this.paivitaNakyma();
					this.ikkuna.enableNoppa();
					return;
				}
				else{
					this.vuorossa = this.pelaajat.get(n+1);
					this.ikkuna.enableNoppa();
					this.paivitaNakyma();
					return;
				}
			}
		}

	}
	
	/*
	 * Luodana haluttu määrä halutuntyyppisiä esineitä.
	 */
	public void luoKaikkiEsineet(){
		luoEsineita(1, Esinetyyppi.TEEKKARINTAHTI);
		luoEsineita(3, Esinetyyppi.ROSVO);
		luoEsineita(4, Esinetyyppi.HEVOSENKENKA);
		luoEsineita(3, Esinetyyppi.OPINTOTUKI);
		luoEsineita(2, Esinetyyppi.MASSIPAALIKKO);
		luoEsineita(2, Esinetyyppi.FYFFE);
		luoEsineita(7, Esinetyyppi.TYHJA);	
	}


	/**
	 * arvotaan Esinelistasta sattumanvarainen esine, joka sijoitetaan haluttuun
	 * peliruutuun ja poistetaan sen jälkeen listasta.
	 */
	public void sijoitaEsineRuutuun(int ruudunNumero){

		int random = rand.nextInt(this.esineet.size());
		Esine randomEsine = this.esineet.get(random);
		this.peliruudut.get(ruudunNumero).sijoitaEsine(randomEsine);
		esineet.remove(random);
		this.esineruudut.add(this.peliruudut.get(ruudunNumero));
	}
	public void sijoitaEsineet(){
		sijoitaEsineRuutuun(74);
		sijoitaEsineRuutuun(126);
		sijoitaEsineRuutuun(12);
		sijoitaEsineRuutuun(7);
		sijoitaEsineRuutuun(3);
		sijoitaEsineRuutuun(17);
		sijoitaEsineRuutuun(24);
		sijoitaEsineRuutuun(29);
		sijoitaEsineRuutuun(33);
		sijoitaEsineRuutuun(41);
		sijoitaEsineRuutuun(50);
		sijoitaEsineRuutuun(89);
		sijoitaEsineRuutuun(46);
		sijoitaEsineRuutuun(108);
		sijoitaEsineRuutuun(84);
		sijoitaEsineRuutuun(81);
		sijoitaEsineRuutuun(66);
		sijoitaEsineRuutuun(61);
		sijoitaEsineRuutuun(54);
		sijoitaEsineRuutuun(57);
		sijoitaEsineRuutuun(78);
		sijoitaEsineRuutuun(101);
		//22 kpl  





	}

	/**
	 * Luodaan tietty määrä halutun tyyppisiä esineitä ja lisätään ne esine-listaan
	 * 
	 * @param maara
	 * @param tyyppi
	 */
	public void luoEsineita(int maara, Esinetyyppi tyyppi){
		for(int i=0; i<maara;i++){
			esineet.add(new Esine(tyyppi));
		}
	}

	/**
	 * Lopetetaan peli tekemällä napit toimimattomiksi sekä asettamalla
	 * jatkuu-attribuutin arvoksi false
	 * 
	 */
	public void lopetaPeli(){
		this.ikkuna.disableLunastus();
		this.ikkuna.disableNoppa();
		this.jatkuu = false;
		
	}



}
