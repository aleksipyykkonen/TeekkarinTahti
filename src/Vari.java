import java.awt.Color;


public enum Vari {

	PELAAJA1, PELAAJA2, PELAAJA3, PELAAJA4, ESINERUUTU, RUUTU, KULKUKELPOINEN, 
	TAUSTAVARI, KULJETTAVAESINE, LAPINAKYVA, KULKUKELPOINENESINE;
	
	public Color annaVari(){
		Color lapi = new Color(255, 255, 255, 1);
		
		switch(this){
		case PELAAJA1: return Color.BLUE;
		case PELAAJA2: return Color.YELLOW;
		case PELAAJA3: return Color.MAGENTA;
		case PELAAJA4: return Color.CYAN;
		case ESINERUUTU: return Color.RED;
		case RUUTU: return Color.BLACK;
		case KULKUKELPOINEN: return Color.DARK_GRAY;
		case TAUSTAVARI: return new Color(181,171,119);
		case LAPINAKYVA: return new Color(255, 255, 255, 1);
		case KULKUKELPOINENESINE: return new Color(255,0,0,150);
		default: return Color.BLACK;
		}
	}
	
}
