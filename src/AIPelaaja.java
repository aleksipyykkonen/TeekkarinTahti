import java.util.Iterator;




/**
 * Pelin tekoälyvastustaja, perii Pelaajan.
 * 
 *
 */
public class AIPelaaja extends Pelaaja{
	private Peli peli;
	private Pelilauta pelilauta;
	private Peliruutu lahin;

	public AIPelaaja(Pelilauta pelilauta, String nimi, int numero, Peli peli) {
		super(pelilauta, nimi, numero);
		this.peli = peli;
		this.pelilauta = pelilauta;

		lahin = this.annaSijainti();
	}

	
	/**
	 * Suorittaa tekoälypelaajan vuoron. Mikäli pelaajalla on mahdollista lunastaa
	 * esine, se lunastaa sen. Muussa tapauksessa pelaaja heittää noppaa ja
	 * etenee parhaaksi katsomaansa peliruutuun ja lopuksi lopettaa vuoronsa.
	 * 
	 * 
	 */
	public void suoritaVuoro(){
		boolean lunastettu =false;
		if(super.annaSijainti().onEsine()){
			peli.lunastaEsine();
			lunastettu = true;
		}
		else{
			peli.heitaNoppaa();
			
			if(super.onkoTahti() || (this.peli.tahtiLoydetty() &&this.onkoHattu())){
				etsiReittiRuutuun(this.pelilauta.annaRuutu(129)).doClick();
				
				peli.lopetaVuoro();
				return;
			}

			/*
			 * Noppaa heittämällä pelaajalle määritetään kulkukelpoiset ruudut, 
			 * eli ne ruudut, joihin heitetyllä silmäluvulla voi kulkea. Mikäli 
			 * joissain näistä ruuduista on jokin esine, pelaaja siirtyy siihen.
			 * Muussa tapauksessa pelaaja siirtyy kohti lähintä esinettä.
			 */
			Iterator<Peliruutu> it = super.maaritaKulkukelpoiset(super.annaSiirtymisvuorot());
			while(it.hasNext()){
				Peliruutu ruutu = it.next();
				if(ruutu.onEsine()){
					ruutu.doClick();
					break;
				}
				if(!it.hasNext()){
					this.etsiReittiRuutuun(this.etsiLahinEsine()).doClick();
					break;
				}
			}

		}
		
		/*
		 * Jos pelaaja päätyy ruutuun, jossa on lunastettava merkki, eikä se 
		 * vielä vuoronsa aikana ole koittanut lunastaa mitään, se lunastaa nyt 
		 * merkin.
		 */
		if(super.annaSijainti().onEsine() && !lunastettu){
			peli.lunastaEsine();
		}
		
		peli.lopetaVuoro();

	}



	/**
	 * Etsii pelaajan sijaintiin nähden lähimmän esineen. Iteroidaan Peli-luokan
	 * esineruutuIteraattorilla pelin esineruudut ja lasketaan matka niihin pelaajan
	 * omasta sijainnista. Mikäli matka iteroitavaan ruutuun on lyhyempi kuin
	 * sen hetkiseen lahin -ruudun arvoon, asetetaan iteroitava ruutu lähimmäksi.
	 * 
	 * @return Peliruutu, joka on lähimpänä pelaajan sijaintia.
	 */
	public Peliruutu etsiLahinEsine(){
		Iterator<Peliruutu> ite = this.peli.esineruutuIteraattori();

		while(ite.hasNext()){
			Peliruutu ruutu = ite.next();
			if(this.pelilauta.maaritaEtaisyys(this.annaSijainti(), ruutu)<
					this.pelilauta.maaritaEtaisyys(this.annaSijainti(), lahin)){
				this.lahin = ruutu;
			}
			if(lahin.equals(this.annaSijainti())){
				this.lahin = ruutu;
			}
		}
		return lahin;
	}



	/**
	 * Käydään läpi iteraattorilla ruudut, joihin pelaaja voi siirtyä heittämällään
	 * silmäluvulla. Palautetaan lopulta se kulkukelpoinen ruutu, josta on lyhin
	 * matka parametrina annettuun ruutuun.
	 * 
	 * @param peliruutu
	 * @return Peliruutu, joka on lähimpänä parametrina annettua ruutua.
	 */
	public Peliruutu etsiReittiRuutuun(Peliruutu peliruutu){
		Peliruutu paras =this.annaSijainti();
		Iterator<Peliruutu> it = this.maaritaKulkukelpoiset(this.annaSiirtymisvuorot());
		while(it.hasNext()){
			Peliruutu ruutu = it.next();
			if(ruutu.equals(peliruutu)){
				return ruutu;
			}
			if(this.pelilauta.maaritaEtaisyys(paras,peliruutu)>
			this.pelilauta.maaritaEtaisyys(ruutu,peliruutu)){
				paras = ruutu;
			}


		}
		return paras;
	}





}
