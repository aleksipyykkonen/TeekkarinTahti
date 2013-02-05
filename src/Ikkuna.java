

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


/**
 * Pelin pääluokka, vastaa graafisesta esityksestä.
 * 
 * @author aleksi
 *
 */
public class Ikkuna extends JFrame{
	private JFrame kehys;
	private JLabel alkuPohja;
	private JLabel silmaluku;
	private Color taustavari;
	private Color reunavari;
	private Container con;
	private Pelilauta pelilauta;
	private int kuvanLeveys;
	private int kuvanKorkeus;
	private int ikkunanLeveys;
	private int ikkunanKorkeus;
	private JPanel sivupaneeli;
	private JPanel alkupaneeli;
	private Peli peli;
	private JButton noppa;
	private JButton lopetus;
	private JButton lunastus;
	private List<Pelaajarivi> pelaajarivit;
	private JLabel tekstikentta;
	private int bottienMaara;
	private int pelaajaMaara;
	private List<String> tekstit;
	private JLabel pohjaKuva;
	private JLabel karttapohja;
	private JLabel otsikko;
	private JMenuItem alusta;

	private Pelaajavalitsin v1;
	private Pelaajavalitsin v2;
	private Pelaajavalitsin v3;
	private Pelaajavalitsin v4;

	private ImageIcon yksi;
	private ImageIcon kaksi;
	private ImageIcon kolme;
	private ImageIcon nelja;
	private ImageIcon viisi;
	private ImageIcon kuusi;
	private ImageIcon kartta;
	private ImageIcon pohja;




	public Ikkuna(){
		this.taustavari = Vari.TAUSTAVARI.annaVari();
		this.reunavari = new Color(124, 116, 71);

		this.kuvanLeveys= 541;
		this.kuvanKorkeus= 629;

		this.ikkunanLeveys=840;
		this.ikkunanKorkeus=629;

		this.tekstit = new ArrayList<String>();

	
		/*
		 * alustetaan ImageIconit
		 */
		this.yksi = new ImageIcon("files/noppa1.png");
		this.kaksi = new ImageIcon("files/noppa2.png");
		this.kolme = new ImageIcon("files/noppa3.png");
		this.nelja = new ImageIcon("files/noppa4.png");
		this.viisi = new ImageIcon("files/noppa5.png");
		this.kuusi = new ImageIcon("files/noppa6.png");
		this.kartta = new ImageIcon("files/kartta.png");
		this.pohja = new ImageIcon("files/alkupohja4.png");



		/*
		 * Luodaan kehys-JFrame, joka asetetaan halutun kokoiseksi ja -väriseksi.
		 * 
		 */
		this.kehys = new JFrame();
		this.kehys.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.kehys.setLayout(null);
		this.con = kehys.getContentPane();
		this.con.setBackground(this.taustavari);
		this.kehys.setSize(ikkunanLeveys, ikkunanKorkeus);
		
		
		/*
		 *Luodaan Valikkopalkki ja siihen halutut painikkeet ja näille
		 *kuuntelijat. Alusta aloittamiselle luodaan kuuntelija vasta pelin
		 *luonnin yhteydessä myöhemmin.
		 * 
		 */
		
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(taustavari);
		menubar.setBorderPainted(false);
		
		JMenu menu = new JMenu("Valikko");
		menu.setBorderPainted(false);
		menubar.add(menu);
		
		this.alusta = new JMenuItem("Aloita alusta");
		this.alusta.setBackground(taustavari);
		this.alusta.setBorderPainted(false);
		
		
		menu.add(alusta);
		
		JMenuItem lopeta = new JMenuItem("Lopeta peli");
		lopeta.setBackground(taustavari);
		lopeta.setBorderPainted(false);
		lopeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
			
		this.kehys.setJMenuBar(menubar);

		/*luodaan Jlabel alkuPohja, johon asetetaan alussa näytettävä
		 * alkuteksti.
		 * 
		*/
		this.alkuPohja = new JLabel(annaAlkuteksti(), JLabel.CENTER);
		//this.alkuPohja.settFont(Font.SERIF);
		this.alkuPohja.setBounds(0, 0, kuvanLeveys, kuvanKorkeus);
		this.alkuPohja.setHorizontalTextPosition(JLabel.CENTER);
		this.alkuPohja.setVerticalTextPosition(JLabel.CENTER);
		this.alkuPohja.setVisible(true);
		this.kehys.add(this.alkuPohja);


		//luodaan koko ikkunan kokoinen pohjakuva taustalle.
		this.pohjaKuva = new JLabel();

		this.pohjaKuva.setIcon(this.pohja);
		this.pohjaKuva.setBounds(0,-15, ikkunanLeveys, ikkunanKorkeus);
		this.kehys.add(this.pohjaKuva);


		//luodaan alkupaneeli ja lisätään se pohjakuvan oikeaan reunaan.
		this.alkupaneeli = new JPanel();
		this.alkupaneeli.setBounds(kuvanLeveys, 0, ikkunanLeveys-kuvanLeveys, ikkunanKorkeus);
		this.alkupaneeli.setBackground(Vari.LAPINAKYVA.annaVari());
		this.alkupaneeli.setOpaque(false);
		this.alkupaneeli.setLayout(null);
		
		this.otsikko = new JLabel("Valitse pelaajien määrä.");
		otsikko.setBounds(10, 10, 200, 30);
		this.alkupaneeli.add(otsikko);
		
		/*
		 * Luodaan ja asetetaan paikalleen pelaajaValitsimet, joilla voi valita
		 * pelaajien määräksi 1-4 kpl
		 * 
		 */
		
		v1 = new Pelaajavalitsin("1 Pelaaja", 1, this);
		v2 = new Pelaajavalitsin("2 Pelaajaa", 2, this);
		v3 = new Pelaajavalitsin("3 Pelaajaa", 3, this);
		v4 = new Pelaajavalitsin("4 Pelaajaa", 4, this);

		/*
		 * Asetetaan pelaajarivi aina 10 pikseliä edellisen alapuolelle.
		 */
		v1.setBounds(20, 40, v1.getWidth(), v1.getHeight());
		v2.setBounds(20, v1.getY()+v1.getHeight()+10, v2.getWidth(), v2.getHeight());
		v3.setBounds(20, v2.getY()+v2.getHeight()+10, v3.getWidth(), v3.getHeight());
		v4.setBounds(20, v3.getY()+v3.getHeight()+10, v4.getWidth(), v4.getHeight());

		this.alkupaneeli.add(v1);
		this.alkupaneeli.add(v2);
		this.alkupaneeli.add(v3);
		this.alkupaneeli.add(v4);



		//lisätään alkupaneeli pohjakuvaan
		this.pohjaKuva.add(alkupaneeli);

		//alustetaan pelaajarivi -lista
		this.pelaajarivit = new ArrayList<Pelaajarivi>();

		
		this.kehys.setVisible(true);






	}
	
	/**
	 * Luodaan pelaajaRivi, joka lisätään pelaajarivit -listaan sekä 
	 * asetetaan sivupaneeliin oikeaan kohtaan.
	 * 
	 * @param pelaaja
	 * @param n 
	 */
	public void luoPelaajaRivi(Pelaaja pelaaja, int n){
		Pelaajarivi rivi = new Pelaajarivi(pelaaja);
		rivi.setBounds(15,20+n*30 , rivi.getWidth(), rivi.getHeight());
		this.sivupaneeli.add(rivi);
		this.pelaajarivit.add(rivi);


	}
	public void naytaSaannot(){
		this.pohjaKuva.setVisible(true);
		this.alkuPohja.setVisible(true);
		this.kehys.add(alkuPohja);
		this.kehys.add(pohjaKuva);
	}
	
	public void asetaPelaajaMaara(int maara){
		this.pelaajaMaara = maara;

	}

	public void asetaBottiMaara(int maara){
		this.bottienMaara = maara;
	}
	
	/**
	 * Käsketään tiettyä pelaajariviä piirtämään TeekkarinTahti
	 * 
	 * @param pelaajanNumero
	 */
	public void naytaTahti(int pelaajanNumero){
		Pelaajarivi privi = pelaajarivit.get(pelaajanNumero);
		privi.piirraTahti();
	}
	
	/**
	 * Käsketään tiettyä pelaajariviä piirtämään Teekkarilakki
	 * 
	 * @param pelaajanNumero, jonka riville lakki tulee piirtää
	 */
	public void naytaLakki(int pelaajanNumero){
		Pelaajarivi privi = pelaajarivit.get(pelaajanNumero);
		privi.piirraLakki();
	}
	
	/**
	 * Aloittaa pelin alusta. Tyhjennetään pelaajarivit-lista, poistetan 
	 * vanha pelilauta sekä sivupaneeli ja luodaan uudet, joiden pohjalta
	 * luodaan uusi peli.
	 * 
	 */
	public void aloitaAlusta(){
		System.out.println("aloitettiin alusta");
		this.kehys.remove(this.sivupaneeli);
		this.pelaajarivit.clear();
		
		this.luoSivupaneeli();
		this.pelilauta.setVisible(false);
		this.karttapohja.remove(this.pelilauta);
		this.pelilauta = new Pelilauta();
		this.pelilauta.setBounds(new Rectangle(0,0,kuvanLeveys,kuvanKorkeus));
		this.pelilauta.setVisible(false);
		this.pelilauta.setVisible(true);
		this.karttapohja.add(this.pelilauta);
		
		this.peli = new Peli(this.pelilauta, this.pelaajaMaara,this.bottienMaara,  this);
		this.lisaaKuuntelijat();
		
		/*
		 * Luodaan tekstikenttä uudelleen ja asetetaan tekstiksi 5 riviä tyhjää
		 */
		this.luoTekstikentta();
		int i=0;
		while(i<5){
			this.asetaTeksti("");
			i++;
		}
		/*
		 * Päivitetään vielä pelaajarivit sekä pelinäkymä(pelilauta) uuden pelin
		 * aloittamiseksi.
		 */
		this.paivitaPelaajarivit();
		
		this.peli.paivitaNakyma();
		this.enableNoppa();
	}

	/**
	 * Piilotetaan pelaajavalitsimet ja luodaan tarvittavat Bottivalitsimet siten,
	 * että pelaajien yhteismääräksi tulee maksimissaan neljä.
	 * 
	 * 
	 */
	public void kysyBottiMaara(){
		this.otsikko.setText("Valitse bottien määrä.");
		v1.setVisible(false);
		v2.setVisible(false);
		v3.setVisible(false);
		v4.setVisible(false);

		this.alkupaneeli.remove(v1);
		this.alkupaneeli.remove(v2);
		this.alkupaneeli.remove(v3);
		this.alkupaneeli.remove(v4);
		
		/*
		 * Luodaan valitsimet ja asetetaan ne allekkain.
		 */
		Bottivalitsin valitsin0 = new Bottivalitsin("Ei botteja", 0, this);
		valitsin0.setBounds(20, 40, valitsin0.getWidth(), valitsin0.getHeight());
		this.alkupaneeli.add(valitsin0);

		if(pelaajaMaara<4){
			Bottivalitsin valitsin1 = new Bottivalitsin("1 Botti", 1, this);
			valitsin1.setBounds(20, valitsin0.getY()+valitsin0.getHeight()+5, valitsin1.getWidth(), valitsin1.getHeight());
			this.alkupaneeli.add(valitsin1);

			if(pelaajaMaara<3){
				Bottivalitsin valitsin2 = new Bottivalitsin("2 Bottia", 2, this);
				valitsin2.setBounds(20, valitsin1.getY()+valitsin1.getHeight()+5, valitsin2.getWidth(), valitsin2.getHeight());
				this.alkupaneeli.add(valitsin2);

				if(pelaajaMaara<2){
					Bottivalitsin valitsin3 = new Bottivalitsin("3 Bottia", 3, this);
					valitsin3.setBounds(20, valitsin2.getY()+valitsin2.getHeight()+5, valitsin3.getWidth(), valitsin3.getHeight());
					this.alkupaneeli.add(valitsin3);
				}
			}
		}
	


	}

	/**
	 * Päivittää pelaajarivit korostamalla vuorossa olevan pelaajan pelaajarivin
	 * ja asettamalla muut rivit mustiksi. Päivittää myös samalla rivin arvot, 
	 * kuten pelaajien rahamäärät.
	 * 
	 */
	public void paivitaPelaajarivit(){
		for(int i=0;i<this.pelaajarivit.size();i++){
			Pelaajarivi rivi = this.pelaajarivit.get(i);
			rivi.paivita();
			if(rivi.annaPelaaja().equals(this.peli.annaVuorossaOleva())){
				rivi.korosta();
			}
			else{
				rivi.asetaVari(Color.BLACK);
			}
		}

	}

	/**
	 * Luodana itse peli. Poistetaan ja piilotetaan alkupaneeli ja luodaan 
	 * pelilauta ja sen pohjakuva peliä varten. Peli luodaan aiemmin annettujen
	 * pelaajamäärien mukaisesti.
	 * 
	 */
	public void luoPeli(){
		/*
		 * piilotetaan alkupaneeli ja poistetaan kehyksestä alkuPohja
		 */
		this.alkupaneeli.setVisible(false);
		this.alkupaneeli.setFocusable(false);
		
		this.kehys.remove(alkuPohja);
		
		/*
		 * Luodaan pelilaudalle karttapohja -jlabel, johon sijoitetaan kartan kuva.
		 * Pidetään kartta näkymättömänä ennen Pelin luontia.
		 */
		this.karttapohja = new JLabel(kartta, JLabel.LEFT);
		this.karttapohja.setBounds(-25,-20,this.kuvanLeveys, this.kuvanKorkeus);
		this.karttapohja.setVisible(false);
		this.kehys.add(this.karttapohja);

		/*
		 * luodaan pelilauta ja asetetaan se paikoilleen karttapohjaan
		 */
		this.pelilauta = new Pelilauta();
		this.pelilauta.setBounds(new Rectangle(0,0,kuvanLeveys,kuvanKorkeus));
		this.pelilauta.setVisible(true);
		this.karttapohja.add(this.pelilauta);
		/*
		 * Luodaan sivupaneeli
		 */
		this.luoSivupaneeli();

		/*
		 * Luodana itse peli annettujen pelaajamäärien pohjalta.
		 */
		this.peli = new Peli(this.pelilauta, this.pelaajaMaara,this.bottienMaara,  this);
		
		/*
		 * Kun peli on luotu, piilotetaan pohjakuva ja asetetaan karttapohja
		 * ja samalla pelilauta näkyviksi.
		 */
		this.luoTekstikentta();
		this.pohjaKuva.setVisible(false);
		this.kehys.remove(pohjaKuva);
		this.karttapohja.setVisible(true);
		
		//lisätään vielä sivupaneelin nappien kuuntelijat ja päivitetään pelaajarivit
		this.lisaaKuuntelijat();
		this.paivitaPelaajarivit();
		
		/*
		 * Lisätään valikon alusta-nappiin kuuntelija vasta pelin luonnin jälkeen
		 */
		this.alusta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aloitaAlusta();
				
			}
		});

		

	}

	/**
	 * Luo sivupaneelin ikkunan oikeaan laitaan.
	 */
	public void luoSivupaneeli(){
		//luodaan sivupaneeli
		this.sivupaneeli = new JPanel();
		this.sivupaneeli.setBounds(kuvanLeveys, 0, ikkunanLeveys-kuvanLeveys, ikkunanKorkeus);
		this.sivupaneeli.setBackground(Vari.TAUSTAVARI.annaVari());
		this.sivupaneeli.setLayout(null);
		this.sivupaneeli.setVisible(true);
		//this.sivupaneeli.

		JLabel otsikkorivi = new JLabel();
		otsikkorivi.setText("Pelaajat");
		otsikkorivi.setBounds(15,10,150,30);
		this.sivupaneeli.add(otsikkorivi);
		
		/*
		 * Luodaan sivupaneelin painikkeet, joita käytetään pelaamisessa.
		 */
		this.noppa = new JButton();
		noppa.setBounds(15, 420,150, 30);
		noppa.setText("Heitä noppaa");
		noppa.setBackground(taustavari);
		noppa.setBorder(BorderFactory.createLineBorder(reunavari, 2));
		this.sivupaneeli.add(noppa);

		
		this.lopetus = new JButton();
		lopetus.setBounds(15, noppa.getY()+noppa.getHeight()+20, 150, 30);
		lopetus.setText("Lopeta vuoro");
		lopetus.setBorder(BorderFactory.createLineBorder(reunavari, 2));
		lopetus.setBackground(taustavari);
		
		this.sivupaneeli.add(lopetus);

		
		this.lunastus = new JButton();
		lunastus.setBounds(15, lopetus.getY()+lopetus.getHeight()+20, 150, 30);
		lunastus.setText("Lunasta esine");
		lunastus.setBorder(BorderFactory.createLineBorder(reunavari, 2));
		lunastus.setBackground(taustavari);
		
		this.sivupaneeli.add(lunastus);

		/*
		 * Luodana noppa-napin viereen nopan kuva, joka näyttää heitetyn silmäluvun
		 * graafisesti.
		 */
		this.silmaluku = new JLabel(new ImageIcon("noppa5.png"));
		silmaluku.setBounds(noppa.getX()+noppa.getWidth()+10, noppa.getY(), 30, 30);

		this.sivupaneeli.add(this.silmaluku);

		//lopuksi lisätään sivupaneeli kehykseen.
		this.kehys.add(sivupaneeli);
	}
	/**
	 * Lisätään sivupaneelin painikkeille kuuntelijat
	 * 
	 */
	public void lisaaKuuntelijat(){

		noppa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peli.heitaNoppaa();

			}
		});
		lopetus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				peli.lopetaVuoro();
			}
		});
		lunastus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peli.lunastaEsine();

			}
		});

	}

	/**
	 * Päivitetään silmäluku-labeliin heitettyä silmälukua vastaava nopan kuva.
	 * 
	 * @param luku
	 */
	public void paivitaSilmaluku(int luku){
		if(luku==1){
			this.silmaluku.setIcon(this.yksi);
		}
		if(luku==2){
			this.silmaluku.setIcon(this.kaksi);
		}
		if(luku==3){
			this.silmaluku.setIcon(this.kolme);
		}
		if(luku==4){
			this.silmaluku.setIcon(this.nelja);
		}
		if(luku==5){
			this.silmaluku.setIcon(this.viisi);
		}
		if(luku==6){
			this.silmaluku.setIcon(this.kuusi);
		}

	}

	
	
	/**
	 * Luodaan tekstikenttä viimeisen pelaajarivin alapuolelle.
	 */
	public void luoTekstikentta(){
		Pelaajarivi rivi = this.pelaajarivit.get(pelaajarivit.size()-1);
		this.tekstikentta = new JLabel();
		int y = rivi.getY()+rivi.getHeight()+10;
		this.tekstikentta.setBounds(10, y, this.sivupaneeli.getWidth()-20, this.noppa.getY()- y-5);
		this.tekstikentta.setBorder(BorderFactory.createLineBorder(reunavari, 3));

		this.sivupaneeli.add(this.tekstikentta);

	}

	/**
	 * Lisätään tekstikenttään haluttu teksti. Kenttä voi näyttää maksimissaan
	 * 5 eri tekstiä allekain. Jokaisen tekstin väliin jätetään yksi tyhjä rivi
	 * selkeyttämiseksi.
	 * 
	 * @param teksti
	 */
	public void asetaTeksti(String teksti){

		if(tekstit.size()>=5){
			tekstit.remove(0);
			//System.out.println("poistettiin vanhin");
		}
		this.tekstit.add(teksti);

		StringBuilder kokoTeksti = new StringBuilder();

		for(int i =0;i<tekstit.size();i++){
			kokoTeksti.append(tekstit.get(i) + "<br>"+" " + "<br>");
		}
		this.tekstikentta.setText("<html>"+kokoTeksti.toString()+"</html>");



	}

	/*
	 * Metodit nopan ja lunastus-napin käyttöä varten. Voidaan asettaa toimivaksi
	 * tai toimimattomaksi pelin tilanteen mukaisesti.
	 */
	public void disableNoppa(){
		this.noppa.setEnabled(false);
	}
	public void enableNoppa(){
		this.noppa.setEnabled(true);
	}
	public void enableLunastus(){
		this.lunastus.setEnabled(true);
	}
	public void disableLunastus(){
		this.lunastus.setEnabled(false);
	}



	

	/**
	 * Palauttaa pelin alussa näytettävän tekstin, jossa kerrotaan säännöistä.
	 * 
	 * @return Pelin alussa näytettävä teksti
	 */
	public String annaAlkuteksti(){
		String teksti =  "Tervetuloa pelaamaan Teekkarintähteä!<br>" +
				"<br>" +
				"Tehtävänä on etsiä Otaniemeen kätketty Teekkarintähti ja <br>" +
				"palauttaa se takaisin lähtöpaikkaan, Olkkarille. Mikäli joku <br>" +
				"pelaajista on jo löytänyt Teekkarintähden, niin kuka tahansa <br>" +
				"voi vielä voittaa pelin löytämällä Teekkarilakin ja viemällä <br>" +
				"sen Olkkarille ennen Tähteä.<br>" +
				"<br>" +
				"Vuoronsa aikana pelaaja voi noppaa heittämällä siirtyä eteenpäin<br>" +
				"ja ollessaan esineen sisältämässä(punaisessa) ruudussa, koittaa<br>" +
				"lunastaa esineen. Esineen lunastaminen maksaa 100e. Pelaaja voi <br>" +
				"lunastaa esineen myös heittämällä nopan silmäluvuksi 4-6, mikäli<br>" +
				"rahat eivät riitä maksamaan lunastusmaksua. <br>" +
				"Vuoronsa aikana pelaaja voi siirtyä ja lunastaa merkin vain kerran.<br>" +
				"Pelivuoronsa voi lopettaa \"Lopeta vuoro\"-napilla<br>" +
				"<br>" +
				"Voit aloittaa pelin valitsemalla pelaajien määrän, sekä sen <br>" +
				"jälkeen bottivastustajien määrän.<br>" +
				"<br>" +
				"Onnea matkaan!"
				;
		
		
		return "<html>"+ teksti + "</html>";
	}
	
	public static void main (String[] args){
		new Ikkuna();
	}




}
