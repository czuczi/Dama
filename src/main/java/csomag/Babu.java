package csomag;
/**
 * Bábukat reprezentáló osztály.
 * @author Czuczi
 *
 */
public class Babu {
	private boolean vilagos;
	private boolean dama;
	
	/**
	 * Bábu létrehozásához szükséges konstruktor, ami megkap egy logikai értéket.
	 * @param vilagos Logikai érték, amivel a bábu színét állítjuk be.
	 */
	public Babu(boolean vilagos) {
		this.vilagos = vilagos;
		this.dama = false;
	}

	/**
	 * Függvény, ami visszaadja, hogy az adott bábu Dáma-e.
	 * @return {@code True} értéket ad vissza, ha dáma, {@code false} értéket, hogyha nem.
	 */
	public boolean isDama() {
		return dama;
	}
	
	/**
	 * Eljárás, amivel beállítható, hogy az adott bábu dáma-e.
	 * @param dama Logikai érték, amivel beállítjuk, hogy egy bábu dáma-e.
	 */
	public void setDama(boolean dama) {
		this.dama = dama;
	}
	
	/**
	 * Függvény, ami visszaadja, hogy az adott bábu világos-e.
	 * @return {@code True} értéket ad vissza, ha világos, {@code false} értéket, hogyha nem.
	 */
	public boolean isVilagos() {
		return vilagos;
	}
	
	/**
	 * Bábu kiíratásához használt metódus.
	 * @return Ha egy adott bábu világos és dáma, "w"-t ad vissza, ha csak világos, "v"-t.
	 * Ha egy adott bábu sötét és dáma, "$"-t ad vissza, ha csak sötét, "s"-t.
	 */
	@Override
	public String toString() {
		if(this.vilagos)
			return this.dama ? "w" : "v";
		return this.dama ? "$" : "s";
	}

}
