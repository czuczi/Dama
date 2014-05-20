package csomag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A játékost reprezentáló osztály.
 * @author Czuczi
 *
 */
public class Jatekos{
	private boolean vilagos;
	private int elozoHovaX, elozoHovaY;
	
	static Logger logger = LoggerFactory.getLogger(Jatekos.class);
	
	/**
	 * Játékos létrehozásához szükséges konstruktor, ami megkap egy logikai értéket.
	 * @param vilagos Logikai érték, amivel a játékos színét állítjuk be {@code true} ha a játékos világos (kék) {@code false} egyébként.
	 */
	public Jatekos(boolean vilagos) {
		this.vilagos = vilagos;
		this.elozoHovaX = this.elozoHovaY = -1;
	}

	/**
	 * Visszaadja, hogy az adott játékos világos-e.
	 * @return {@code True} értékkel tér vissza, ha a játékos világos (kék), {@code false} értékkel egyébként.
	 */
	public boolean isVilagos() {
		return vilagos;
	}

	/**
	 * Visszaadja, hogy az adott játékos előzőleg melyik X koordinátára lépett.
	 * @return Egész értéket ad vissza, ami megadja, hogy az adott játékos előzőleg melyik X koordinátára lépett.
	 */
	public int getElozoHovaX() {
		return elozoHovaX;
	}

	/**
	 * Beállítja, hogy az adott játékos előzőleg melyik X koordinátára lépett.
	 * @param elozoHovaX Egész érték, ami azt adja meg, hogy az adott játékos előzőleg melyik X koordinátára lépett.
	 */
	public void setElozoHovaX(int elozoHovaX) {
		this.elozoHovaX = elozoHovaX;
	}

	/**
	 * Visszaadja, hogy az adott játékos előzőleg melyik Y koordinátára lépett.
	 * @return Egész értéket ad vissza, ami megadja, hogy az adott játékos előzőleg melyik Y koordinátára lépett.
	 */
	public int getElozoHovaY() {
		return elozoHovaY;
	}

	/**
	 * Beállítja, hogy az adott játékos előzőleg melyik Y koordinátára lépett.
	 * @param elozoHovaY Egész érték, ami azt adja meg, hogy az adott játékos előzőleg melyik Y koordinátára lépett.
	 */
	public void setElozoHovaY(int elozoHovaY) {
		this.elozoHovaY = elozoHovaY;
	}

	/**
	 * Ha az adott játékos léphet az adott koordinátkról az adott célkoordinátákra, akkor elvégzi a lépést, és {@code true} értékkel tér vissza, egyébként {@code false} értékkel.
	 * Illetve teszteli azt is, hogy, ha fennáll ütéskényszer, akkor ne lehessen, illetve ne kelljen több bábuval is lépni, amire érvényes az ütéskényszer.
	 * @param honnan A kiinduló koordináták {@code String} reprezentációja.
	 * @param hova A kiinduló koordináták {@code String} reprezentációja.
	 * @param t A tábla, amelyen a lépést végre szeretnénk hajtani.
	 * @return {@code True} értékkel tér vissza, ha a lépés sikeres volt, {@code False} értékkel egyébként.
	 */
	public boolean lep(String honnan, String hova, Tabla t) {
		int honnanX, honnanY, hovaX, hovaY;
		honnanX = Character.getNumericValue(honnan.charAt(1)) - 1;
		honnanY = (honnan.charAt(0)) - 'a';
		hovaX = Character.getNumericValue(hova.charAt(1)) - 1;
		hovaY = (hova.charAt(0)) - 'a';
		
		Babu[][] allas = t.getTabla();
		
		if(t.lephet(honnanX, honnanY, hovaX, hovaY, this.vilagos)){
			boolean utesK = false;
			if(t.isKovetkezo() && t.utesKenyszer(true)){
				if((elozoHovaX != honnanX || elozoHovaY != honnanY) && !(elozoHovaX == -1 && elozoHovaY == -1))
					return false;
				utesK = true;
			}
			else if(t.isKovetkezo() && !t.utesKenyszer(true)){
				if(this.vilagos)
					t.setKovetkezo(false);
			}
			else if (!t.isKovetkezo() && t.utesKenyszer(false)) {
				if((elozoHovaX != honnanX || elozoHovaX != honnanY) && !(elozoHovaX == -1 && elozoHovaY == -1))
					return false;
				utesK = true;
			}
			else if(!t.isKovetkezo() && !t.utesKenyszer(false)){
				if(!this.vilagos)
					t.setKovetkezo(true);
			}
			
			allas[hovaX][hovaY] = allas[honnanX][honnanY];
			if(hovaX == 7 || hovaX == 0)
				allas[hovaX][hovaY].setDama(true);
			if(Math.abs(hovaY -honnanY) + Math.abs(hovaX - honnanX) == 4){
				if(this.vilagos == true)
					t.setSotetBabuSzam(t.getSotetBabuSzam()-1);
				else
					t.setVilagosBabuSzam(t.getVilagosBabuSzam()-1);
				allas[(honnanX+hovaX)/2][(honnanY+hovaY)/2] = null;
			}
			allas[honnanX][honnanY] = null;
			t.setTabla(allas);
			
			if(t.isKovetkezo() && utesK){
				if(this.vilagos){
					if(!t.utesKenyszer(true) || !t.uthet(hovaX, hovaY, this.vilagos)){
						elozoHovaX = elozoHovaY = -1;
						t.setKovetkezo(false);
						
						Jatekos.logger.info("\n" + t.toString());
						return true;
					}
				}
			}
			
			if(!t.isKovetkezo() && utesK){
				if(!this.vilagos){
					if(!t.utesKenyszer(false) || !t.uthet(hovaX, hovaY, this.vilagos)){
						elozoHovaX = elozoHovaY = -1;
						t.setKovetkezo(true);
						
						Jatekos.logger.info("\n" + t.toString());
						return true;
					}
				}
			}
			
			Jatekos.logger.info("\n" + t.toString());
			
			return true;
		}
		Jatekos.logger.info("\n" + t.toString());
		
		return false;
	}

	

}
