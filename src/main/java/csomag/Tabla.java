package csomag;
/**
 * A táblát reprezentáló osztály.
 * @author Czuczi
 *
 */
public class Tabla {
	private Babu[][] tabla = new Babu[8][8];
	private int vilagosBabuSzam;
	private int sotetBabuSzam;
	private boolean kovetkezo;
	
	/**
	 * A tábla létrehozásához szükséges konstruktor.
	 * Beállítja a tábla alaphelyzetét, hogy a világos (kék) játékos következik, illetve azt, hogy hány világos (kék)/sötét (piros) bábu van a táblán.
	 */
	public Tabla() {
		kovetkezo = true;
		vilagosBabuSzam = sotetBabuSzam = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 8; j++){
				if((i+j)%2 != 0){
					this.tabla[i][j] = new Babu(true);
					vilagosBabuSzam++;
				}
			}
		}
		
		for(int i = 5; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if((i+j)%2 != 0){
					this.tabla[i][j] = new Babu(false);
					sotetBabuSzam++;
				}
			}
		}
	}
	
	/**
	 * Visszaadja, hogy milyen színű játékos következik.
	 * @return {@code True} értéket ad vissza, ha a világos (kék) játékos következik, {@code false} értéket, ha a sötét (piros).
	 */
	public boolean isKovetkezo() {
		return kovetkezo;
	}
	
	/**
	 * Beállítja, hogy melyik játékos következik.
	 * @param kovetkezo {@code True} értéket vár, ha a világos (kék) következik, {@code false} értéket, ha a sötét (piros).
	 */
	public void setKovetkezo(boolean kovetkezo) {
		this.kovetkezo = kovetkezo;
	}

	/**
	 * Visszaadja, hogy hány világos (kék) bábu van a táblán.
	 * @return A világos (kék) bábuk számát adja vissza.
	 */
	public int getVilagosBabuSzam() {
		return vilagosBabuSzam;
	}
	
	/**
	 * A világos (kék) bábuk számának beállítására szolgál.
	 * @param vilagosBabu egész érték, amivel beállítjuk, hogy hány világos (kék) bábu van a táblán.
	 */
	public void setVilagosBabuSzam(int vilagosBabu){
		this.vilagosBabuSzam = vilagosBabu;
	}
	
	/**
	 * Visszaadja, hogy hány sötét (piros) bábu van a táblán.
	 * @return A sötét (piros) bábuk számát adja vissza.
	 */
	public int getSotetBabuSzam() {
		return sotetBabuSzam;
	}

	/**
	 * A sötét (piros) bábuk számának beállítására szolgál.
	 * @param sotetBabu Egész érték, amivel beállítjuk, hogy hány sötét (piros) bábu van a táblán.
	 */
	public void setSotetBabuSzam(int sotetBabu) {
		this.sotetBabuSzam = sotetBabu;
	}
	
	/**
	 * Függvény, ami visszaadja a tábla aktuális állapotát.
	 * @return A tábla aktuális állapotát adja vissza, Bábukat tartalmazó mátrix formájában.
	 */
	public Babu[][] getTabla() {
		return tabla;
	}

	/**
	 * Eljárás, ami a tábla aktuális állapotának beállításásra szolgál.
	 * @param tabla Egy bábukat tartalmazó mátrix, amire átállítjuk a tábla állapotát.
	 */
	public void setTabla(Babu[][] tabla) {
		this.tabla = tabla;
	}
	
	/**
	 * A tábla kiíratásához szükséges metódus, ami a bábuknál definiált toString-et is felhasználja.
	 * @return A tábla {@code String} reprezentációját adja vissza.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 7; i > -1; i--){
			StringBuilder sor = new StringBuilder();
			for(int j = 0; j < 7; j++){
				sor.append((this.tabla[i][j] == null ? "0" : this.tabla[i][j]) + " ");
			}
			sor.append(this.tabla[i][7] == null ? "0" : this.tabla[i][7]);
			sb.append(i+1 + " " + sor + "\n");
		}
		sb.append("  a b c d e f g h");
		return sb.toString();
	}
	
	/**
	 * Visszaadja, hogy az adott színű játékos, adott koordinátákon lévő bábuja üthet-e.
	 * @param honnanX Egész érték, ami a bábu X koordinátáját tartalmazza.
	 * @param honnanY Egész érték, ami a bábu Y koordinátáját tartalmazza.
	 * @param jatekosSzin Logikai érték, ami {@code true} értéket kell felvegyen, ha a játékos világos (kék), {@code false} értéket, ha a játékos sötét (piros).
	 * @return {@code True} értékkel tér vissza, ha az adott játékos az adott bábuval üthet, egyébként {@code false} értékkel.
	 */
	public boolean uthet(int honnanX, int honnanY, boolean jatekosSzin){
		if(lephet(honnanX, honnanY, honnanX+2, honnanY+2, jatekosSzin)){
			return true;
		}
		if(lephet(honnanX, honnanY, honnanX-2, honnanY-2, jatekosSzin)){
			return true;
		}
		if(lephet(honnanX, honnanY, honnanX-2, honnanY+2, jatekosSzin)){
			return true;
		}
		if(lephet(honnanX, honnanY, honnanX+2, honnanY-2, jatekosSzin)){
			return true;
		}
		return false;
	}
	
	/**
	 * Visszaadja, hogy az adott színű játékos, adott koordinátákon lévő bábuja léphet-e a megadott koordinátákra.
	 * @param honnanX Egész érték, ami a bábu jelenlegi X koordinátáját tartalmazza.
	 * @param honnanY Egész érték, ami a bábu jelenlegi Y koordinátáját tartalmazza.
	 * @param hovaX Egész érték, ami a bábu várt X koordinátáját tartalmazza.
	 * @param hovaY Egész érték, ami a bábu várt Y koordinátáját tartalmazza.
	 * @param jatekosSzin Logikai érték, ami {@code true} értéket kell felvegyen, ha a játékos világos (kék), {@code false} értéket, ha a játékos sötét (piros).
	 * @return {@code True} értékkel tér vissza, ha az adott játékos az adott bábuval léphet, egyébként {@code false} értékkel.
	 */
	public boolean lephet(int honnanX, int honnanY, int hovaX, int hovaY, boolean jatekosSzin) {
		if(kovetkezo != jatekosSzin)
			return false;
		if(honnanX == hovaX)
			return false;
		if(honnanY == hovaY)
			return false;
		if(hovaX > 7 || hovaX < 0)
			return false;
		if(hovaY > 7 || hovaY < 0)
			return false;
		if(this.utesKenyszer(jatekosSzin)){
			if(Math.abs(hovaY -honnanY) + Math.abs(hovaX - honnanX) != 4)
				return false;
		}
		if(!(this.tabla[honnanX][honnanY] instanceof Babu))
			return false;
		if(this.tabla[honnanX][honnanY].isVilagos() != jatekosSzin)
			return false;
		if(this.tabla[hovaX][hovaY] instanceof Babu)
			return false;
		if(Math.abs(hovaX - honnanX) > 2)
			return false;
		if(Math.abs(hovaY - honnanY) > 2)
			return false;
		if(Math.abs(hovaY -honnanY) + Math.abs(hovaX - honnanX) == 3)
			return false;
		if(Math.abs(hovaY -honnanY) + Math.abs(hovaX - honnanX) == 4){
			if(!(this.tabla[(honnanX+hovaX)/2][(honnanY+hovaY)/2] instanceof Babu))
				return false;
			else if(this.tabla[(honnanX+hovaX)/2][(honnanY+hovaY)/2].isVilagos() == jatekosSzin)
				return false;
		}
		if(!this.tabla[honnanX][honnanY].isDama()){
			if(jatekosSzin){
				if(hovaX <= honnanX)
					return false;
			}
			else {
				if(hovaX >= honnanX)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Megvizsgálja, hogy az adott játékosnak van-e olyan bábuja, ami üthet, ilyenkor érvényben van az ütéskényszer, és {@code true} értéket ad vissza, egyébként {@code false} értéket.
	 * @param jatekosSzin Az adott játékos színe {@code true}, ha az adott játékos világos (kék) {@code false} egyébként.
	 * @return {@code True} értékkel tér vissza, ha az adott játékosnak van olyan bábuja, amire érvényes az ütéskényszer.
	 */
	public boolean utesKenyszer(boolean jatekosSzin){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				
				if(this.tabla[i][j] instanceof Babu){
					if(jatekosSzin){
						if(this.tabla[i][j].isVilagos() == jatekosSzin){
							if(i < 6 && j < 6){
								if(this.tabla[i+1][j+1] instanceof Babu){
									if(this.tabla[i+1][j+1].isVilagos() != jatekosSzin){
										if(!(this.tabla[i+2][j+2] instanceof Babu)){
											return true;
										}
									}
								}
							}
							if(i < 6 && j > 1){
								if(this.tabla[i+1][j-1] instanceof Babu){
									if(this.tabla[i+1][j-1].isVilagos() != jatekosSzin){
										if(!(this.tabla[i+2][j-2] instanceof Babu)){
											return true;
										}
									}
								}
							}
							if(this.tabla[i][j].isDama()){
								if(i > 1 && j > 1){
									if(this.tabla[i-1][j-1] instanceof Babu){
										if(this.tabla[i-1][j-1].isVilagos() != jatekosSzin){
											if(!(this.tabla[i-2][j-2] instanceof Babu)){
												return true;
											}
										}
									}
								}
								if(i > 1 && j < 6){
									if(this.tabla[i-1][j+1] instanceof Babu){
										if(this.tabla[i-1][j+1].isVilagos() != jatekosSzin){
											if(!(this.tabla[i-2][j+2] instanceof Babu)){
												return true;
											}
										}
									}
								}
							}
						}
					}
					else{
						if(this.tabla[i][j].isVilagos() == jatekosSzin){
							if(i > 1 && j > 1){
								if(this.tabla[i-1][j-1] instanceof Babu){
									if(this.tabla[i-1][j-1].isVilagos() != jatekosSzin){
										if(!(this.tabla[i-2][j-2] instanceof Babu)){
											return true;
										}
									}
								}
							}
							if(i > 1 && j < 6){
								if(this.tabla[i-1][j+1] instanceof Babu){
									if(this.tabla[i-1][j+1].isVilagos() != jatekosSzin){
										if(!(this.tabla[i-2][j+2] instanceof Babu)){
											return true;
										}
									}
								}
							}
							if(this.tabla[i][j].isDama()){
								if(i < 6 && j < 6){
									if(this.tabla[i+1][j+1] instanceof Babu){
										if(this.tabla[i+1][j+1].isVilagos() != jatekosSzin){
											if(!(this.tabla[i+2][j+2] instanceof Babu)){
												return true;
											}
										}
									}
								}
								if(i < 6 && j > 1){
									if(this.tabla[i+1][j-1] instanceof Babu){
										if(this.tabla[i+1][j-1].isVilagos() != jatekosSzin){
											if(!(this.tabla[i+2][j-2] instanceof Babu)){
												return true;
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
		return false;
	}
	
	/**
	 * Függvény, ami megmondja, hogy a játék végetért-e.
	 * @return {@code True} értéket ad vissza, ha az egyik fél bábuinak száma 0, egyébként {@code false} értéket.
	 */
	public boolean vege(){
		if(vilagosBabuSzam == 0 || sotetBabuSzam == 0)
			return true;
		return false;
	}
	
}
