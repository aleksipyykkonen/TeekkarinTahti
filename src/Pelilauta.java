import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * Luokka joka luo ja asettaa peliruudut paikalleen ja hoitaa niiden
 * naapuruussuhteet
 *
 */
public class Pelilauta extends JPanel{
	private List<Peliruutu> peliruudut;
	private static int ruudunNumero =0;


	public Pelilauta(){

		this.setOpaque(false);
		this.peliruudut = new ArrayList<Peliruutu>();

		this.setLayout(null);

		this.asetaRuudut();
		this.risteysNaapurit();
		ruudunNumero=0;


	}


	public int annaruudunNumero(){
		return this.ruudunNumero;
	}


	public List<Peliruutu> annaPeliruudut(){
		return this.peliruudut;
	}

	public Peliruutu annaRuutu(int indeksi){
		if(indeksi>=0 && indeksi <this.peliruudut.size()){
			return this.peliruudut.get(indeksi);
		}
		return null;
	}

	/**
	 * Lisätään pelilaudalle uusi peliruutu. Parametreina annetut x ja y kerrotaan
	 * kahdeksalla, jolloin ne asetetaan lähinnä ideatasolla olemassa olevaan 
	 * ruudukkoon, jota on käytetty pelilaudan taustakuvan luomisessa ja peliruutujen
	 * sijoittelussa. Kuitenkaan varsinaista taulukkoa ja satoja tyhjiä alkioita
	 * ei luoda.
	 * 
	 * @param x
	 * @param y
	 */
	public void luoRuutu(int x, int y){
		final Peliruutu nappi = new Peliruutu(this.annaruudunNumero(), this);
		nappi.setLocation(x*8-4, y*8-4);


		this.peliruudut.add(nappi);
		if(this.ruudunNumero!=0){
			nappi.lisaaNaapuri(this.peliruudut.get(this.ruudunNumero-1));
			this.peliruudut.get(this.ruudunNumero-1).lisaaNaapuri(nappi);
		}
		this.add(nappi);
		

		this.ruudunNumero++;
	}





	/**
	 * Asetetaan pelilaudalle kaikki peliruudut.
	 * 
	 */
	public void asetaRuudut(){
		this.luoRuutu(53,59);
		this.luoRuutu(53,62);
		this.luoRuutu(53,65);
		this.luoRuutu(51,67);
		this.luoRuutu(49,69);
		this.luoRuutu(47,71);
		this.luoRuutu(44,71);
		this.luoRuutu(41,71);
		this.luoRuutu(39,69);
		this.luoRuutu(37,67);
		this.luoRuutu(35,65);
		this.luoRuutu(36,63);
		this.luoRuutu(38,61);
		this.luoRuutu(40,59);
		this.luoRuutu(43,58);
		this.luoRuutu(46,57);
		this.luoRuutu(49,56);
		this.luoRuutu(53,56);
		this.luoRuutu(53,53);
		this.luoRuutu(53,50);
		this.luoRuutu(53,47);
		this.luoRuutu(54,44);
		this.luoRuutu(56,42);
		this.luoRuutu(58,39);
		this.luoRuutu(60,36);
		this.luoRuutu(61,33);
		this.luoRuutu(61,30);
		this.luoRuutu(61,27);
		this.luoRuutu(60,24);
		this.luoRuutu(58,22);
		this.luoRuutu(56,21);
		this.luoRuutu(56,18);
		this.luoRuutu(55,15);
		this.luoRuutu(53,12);
		this.luoRuutu(51,15);
		this.luoRuutu(48,16);
		this.luoRuutu(45,17);
		this.luoRuutu(45,19);
		this.luoRuutu(45,22);
		this.luoRuutu(44,25);
		this.luoRuutu(46,27);
		this.luoRuutu(48,29);
		this.luoRuutu(50,30);
		this.luoRuutu(53,32);
		this.luoRuutu(55,35);
		this.luoRuutu(54,38);
		this.luoRuutu(51,40);
		this.luoRuutu(48,41);
		this.luoRuutu(44,41);
		this.luoRuutu(45,39);
		this.luoRuutu(45,36);
		this.luoRuutu(44,33);
		this.luoRuutu(42,30);
		this.luoRuutu(40,28);
		this.luoRuutu(38,26);
		this.luoRuutu(39,23);
		this.luoRuutu(39,20);
		this.luoRuutu(39,17);
		this.luoRuutu(36,18);
		this.luoRuutu(34,21);
		this.luoRuutu(32,23);
		this.luoRuutu(30,25);
		this.luoRuutu(28,27);
		this.luoRuutu(26,29);
		this.luoRuutu(23,29);
		this.luoRuutu(20,28);
		this.luoRuutu(17,28);
		this.luoRuutu(17,31);
		this.luoRuutu(16,33);
		this.luoRuutu(15,35);
		this.luoRuutu(13,37);
		this.luoRuutu(12,40);
		this.luoRuutu(12,43);
		this.luoRuutu(11,45);
		this.luoRuutu(10,47);
		this.luoRuutu(13,49);
		this.luoRuutu(15,50);
		this.luoRuutu(17,52);
		this.luoRuutu(19,54);
		this.luoRuutu(21,51);
		this.luoRuutu(22,49);
		this.luoRuutu(24,46);
		this.luoRuutu(25,44);
		this.luoRuutu(27,42);
		this.luoRuutu(28,40);
		this.luoRuutu(30,41);
		this.luoRuutu(32,44);
		this.luoRuutu(34,47);
		this.luoRuutu(36,49);
		this.luoRuutu(38,50);
		this.luoRuutu(40,47);
		this.luoRuutu(42,44);
		this.luoRuutu(36,53);
		this.luoRuutu(35,56);
		this.luoRuutu(36,59);
		this.luoRuutu(20,32);
		this.luoRuutu(22,34);
		this.luoRuutu(25,35);
		this.luoRuutu(27,37);
		this.luoRuutu(29,38);
		this.luoRuutu(30,36);
		this.luoRuutu(31,34);
		this.luoRuutu(31,31);
		this.luoRuutu(33,28);
		this.luoRuutu(35,26);
		this.luoRuutu(33,36);
		this.luoRuutu(35,38);
		this.luoRuutu(37,40);
		this.luoRuutu(39,42);
		this.luoRuutu(41,40);
		this.luoRuutu(43,37);
		this.luoRuutu(42,18);
		this.luoRuutu(46,31);
		this.luoRuutu(50,27);
		this.luoRuutu(53,26);
		this.luoRuutu(55,24);
		this.luoRuutu(59,27);
		this.luoRuutu(57,29);
		this.luoRuutu(56,32);
		this.luoRuutu(57,36);

		this.luoRuutu(15,41);
		this.luoRuutu(17,42);
		this.luoRuutu(19,43);
		this.luoRuutu(21,45);
		this.luoRuutu(21,56);
		this.luoRuutu(23,58);
		this.luoRuutu(26,60);
		this.luoRuutu(29,62);
		this.luoRuutu(32,63);

		this.luoRuutu(10,39);


	}
	public void risteysNaapurit(){
		asetaNaapurit(annaRuutu(0), annaRuutu(17));

		asetaNaapurit(annaRuutu(91), annaRuutu(48));
		poistaNaapurit(annaRuutu(92), annaRuutu(48));

		asetaNaapurit(annaRuutu(92), annaRuutu(89));
		poistaNaapurit(annaRuutu(92), annaRuutu(91));

		asetaNaapurit(annaRuutu(12), annaRuutu(94));

		asetaNaapurit(annaRuutu(95), annaRuutu(67));
		poistaNaapurit(annaRuutu(95), annaRuutu(94));

		asetaNaapurit(annaRuutu(104), annaRuutu(54));
		poistaNaapurit(annaRuutu(104), annaRuutu(105));

		asetaNaapurit(annaRuutu(101), annaRuutu(105));

		asetaNaapurit(annaRuutu(110), annaRuutu(50));
		poistaNaapurit(annaRuutu(110), annaRuutu(111));

		asetaNaapurit(annaRuutu(111), annaRuutu(57));
		asetaNaapurit(annaRuutu(111), annaRuutu(36));

		asetaNaapurit(annaRuutu(112), annaRuutu(51));
		asetaNaapurit(annaRuutu(112), annaRuutu(41));
		poistaNaapurit(annaRuutu(112), annaRuutu(111));

		asetaNaapurit(annaRuutu(113), annaRuutu(41));
		poistaNaapurit(annaRuutu(113), annaRuutu(112));

		asetaNaapurit(annaRuutu(116), annaRuutu(27));
		poistaNaapurit(annaRuutu(116), annaRuutu(115));

		asetaNaapurit(annaRuutu(115), annaRuutu(30));

		asetaNaapurit(annaRuutu(118), annaRuutu(44));
		poistaNaapurit(annaRuutu(118), annaRuutu(119));

		asetaNaapurit(annaRuutu(119), annaRuutu(24));
		asetaNaapurit(annaRuutu(119), annaRuutu(44));

		asetaNaapurit(annaRuutu(120), annaRuutu(71));
		poistaNaapurit(annaRuutu(120), annaRuutu(119));

		asetaNaapurit(annaRuutu(124), annaRuutu(78));
		poistaNaapurit(annaRuutu(124), annaRuutu(123));

		asetaNaapurit(annaRuutu(123), annaRuutu(81));
		asetaNaapurit(annaRuutu(128), annaRuutu(10));
		asetaNaapurit(annaRuutu(84), annaRuutu(99));

		asetaNaapurit(annaRuutu(129), annaRuutu(71));
		poistaNaapurit(annaRuutu(128), annaRuutu(129));
	}

	/**
	 * Väritetään kulkukelpoiset ruudut sen mukaan, onko niissä esinettä.
	 * Mikäli ruudussa on jo joku toinen pelaaja, sitä ei väritetä erikseen.
	 * 
	 * @param pelaaja
	 * @param silmaluku
	 */
	public void varitaKuljettavat(Pelaaja pelaaja, int silmaluku){

		/*
		 * Aluksi väritetään perusvärillä kaikki ruudut, joissa ei ole esinettä eikä pelaajaa
		 */
		for(int n =0; n<this.peliruudut.size();n++){
			if(this.peliruudut.get(n).onTyhja()&&!peliruudut.get(n).onEsine()){
				this.peliruudut.get(n).asetaVari(Vari.RUUTU.annaVari());
			}
		}

		/*
		 * Tämän jälkeen väritetään kulkukelpoiset halutulla värillä.
		 */
		Iterator<Peliruutu> it = pelaaja.maaritaKulkukelpoiset(silmaluku); 
		while(it.hasNext()){
			Peliruutu ruutu = it.next();
			if(ruutu.onTyhja()){
				if(ruutu.onEsine()){
					ruutu.asetaVari(Vari.KULKUKELPOINENESINE.annaVari());
				}
				else{
					ruutu.asetaVari(Vari.KULKUKELPOINEN.annaVari());
				}
			}

		}

	}

	/**
	 * Asettaa 2 annettua ruutua toistensa naapureiksi. Käytetään risteyksissä,
	 * jolloin ruudulla on muitakin naapureita, kuin listassa edellisenä ja 
	 * seuraavana olevat ruudut.
	 * 
	 * @param ruutu1
	 * @param ruutu2
	 */
	public void asetaNaapurit(Peliruutu ruutu1, Peliruutu ruutu2){
		ruutu1.lisaaNaapuri(ruutu2);
		ruutu2.lisaaNaapuri(ruutu1);
	}

	/**
	 * Poistaa 2 ruutua toistensa naapurilistoista.
	 * 
	 * @param ruutu1
	 * @param ruutu2
	 */
	public void poistaNaapurit(Peliruutu ruutu1, Peliruutu ruutu2){
		ruutu1.poistaNaapuri(ruutu2);
		ruutu2.poistaNaapuri(ruutu1);
	}

	/**
	 * Maarittaa etäisyyden kahden ruudun välillä. Maksimiarvoksi määrätty 40.
	 * 
	 * @param lahtoruutu
	 * @param maaranpaa
	 * @return
	 */
	public int maaritaEtaisyys(Peliruutu lahtoruutu, Peliruutu maaranpaa){
		/*
		 * Käydään läpi peliruutuja askelmäärällä 1. Tätä käydään ruudut-listan 
		 * jokaiselle ruudulle niin kauan, että jokin naapureista viimein on 
		 * määränpäänä annettu ruutu. Tällöin saadaaan selville ruutujen välinen
		 * etäisyys.
		 */
		List<Peliruutu> ruudut = new ArrayList<Peliruutu>();
		ruudut.add(lahtoruutu);

		for(int i=1;i<40;i++){
			int koko = ruudut.size();

			for(int n=0;n<koko;n++){
				Iterator<Peliruutu> it = maaritaKulkukelpoiset(ruudut.get(n), 1);
				while(it.hasNext()){
					Peliruutu ruutu = it.next();
					if(ruutu.equals(maaranpaa)){
						return i;
					}
					if(!ruudut.contains(ruutu)){
						ruudut.add(ruutu);
					}
				}
			}
		}
		return 40;
	}


	/**
	 * Palauttaa Iteraattorin kulkukelpoisista peliruuduista, joihin annetulla silmäluvulla
	 * voi parametrina annetusta lähtöruudusta kulkea.
	 * @param lahtoruutu
	 * @param silmaluku
	 * @return
	 */
	public Iterator<Peliruutu> maaritaKulkukelpoiset(Peliruutu lahtoruutu, int silmaluku){
		List<Peliruutu> kulkukelpoiset = new ArrayList<Peliruutu>();
		kulkukelpoiset.clear();
		kulkukelpoiset.add(lahtoruutu);

		/*
		 * Aluksi käydään läpi lähtöruudun naapurit. Jos naapuri ei ole jo 
		 * kulkukelpoisten listassa, se lisätään siihen. Mikäli silmäluku on 
		 * tarpeeksi suuri, käydään myös tämän lisätyn naapurin naapurit läpi.
		 * Sama toistuu niin kauan, kun silmäluku on tarpeeksi suuri.
		 * 
		 */

		//silmäluku1
		if(silmaluku>0){
			Iterator<Peliruutu> it = lahtoruutu.naapuriIteraattori();
			while(it.hasNext()){
				Peliruutu uusi1 = it.next();
				if(!kulkukelpoiset.contains(uusi1)){
					kulkukelpoiset.add(uusi1);					

					//silmäluku2		
					if(silmaluku>1){
						Iterator<Peliruutu> it2 = uusi1.naapuriIteraattori();
						while(it2.hasNext()){
							Peliruutu uusi2 = it2.next();
							if(!kulkukelpoiset.contains(uusi2)){
								kulkukelpoiset.add(uusi2);

								//silmäluku3
								if(silmaluku>2){
									Iterator<Peliruutu> it3 = uusi2.naapuriIteraattori();
									while(it3.hasNext()){
										Peliruutu uusi3 = it3.next();
										if(!kulkukelpoiset.contains(uusi3)){
											kulkukelpoiset.add(uusi3);

											//silmaluku4
											if(silmaluku>3){
												Iterator<Peliruutu> it4 = uusi3.naapuriIteraattori();
												while(it4.hasNext()){
													Peliruutu uusi4 = it4.next();
													if(!kulkukelpoiset.contains(uusi4)){
														kulkukelpoiset.add(uusi4);

														//silmäluku5
														if(silmaluku>4){
															Iterator<Peliruutu> it5 = uusi4.naapuriIteraattori();
															while(it5.hasNext()){
																Peliruutu uusi5 = it5.next();
																if(!kulkukelpoiset.contains(uusi5)){
																	kulkukelpoiset.add(uusi5);

																	//silmäluku6
																	if(silmaluku>5){
																		Iterator<Peliruutu> it6 = uusi5.naapuriIteraattori();
																		while(it6.hasNext()){
																			Peliruutu uusi6 = it6.next();
																			if(!kulkukelpoiset.contains(uusi6)){
																				kulkukelpoiset.add(uusi6);
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

				}
			}
		}

		kulkukelpoiset.remove(lahtoruutu);	
		return kulkukelpoiset.iterator();

	}


}
