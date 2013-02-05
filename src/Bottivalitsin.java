import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Bottivalitsin perii Pelaajavalitsimen. Toiminta muuten samanlainen, mutta
 * pelaajien sijaan luo tekoälypelaajat.
 * 
 * @author aleksi
 *
 */
public class Bottivalitsin extends Pelaajavalitsin{
	private Ikkuna ikkuna;
	private int pelaajaMaara;
	

	public Bottivalitsin(String teksti, final int pelaajaMaara,final Ikkuna ikkuna) {
		super(teksti, pelaajaMaara, ikkuna);
		this.ikkuna = ikkuna;
		this.pelaajaMaara = pelaajaMaara;
		this.setRolloverEnabled(false);
	}
	
	public void lisaaKuuntelija(){
		this.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				 ikkuna.asetaBottiMaara(pelaajaMaara);
				ikkuna.luoPeli();
				
				
			}
		});
		
	}
	
	

}
