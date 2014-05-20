package test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import csomag.Jatekos;
import csomag.Tabla;

public class TestDama {
	private Tabla t;
	private Jatekos vilagos;
	private Jatekos sotet;
	private static final String OSZLOPOK = "abcdefgh";
	private static final String SOROK = "12345678";
	
	@Before
	public void Inicializalas(){
		t = new Tabla();
		vilagos = new Jatekos(true);
		sotet = new Jatekos(false);
	}
	
	@Test
	public void kezdoLepesTeszt(){
		boolean sikeres = vilagos.lep("b3", "c4", t);
		
		assertEquals(true, sikeres);
		
		sikeres = vilagos.lep("c4", "d5", t);
		
		assertEquals(false, sikeres);
	}
	
	@Test
	public void utes(){
		boolean sikeres = vilagos.lep("b3", "c4", t);
				
		assertEquals(true, sikeres);
		
		sikeres = sotet.lep("a6", "b5", t);
		
		assertEquals(true, sikeres);
		
		sikeres = vilagos.lep("c4", "a6", t);
		
		assertEquals(true, sikeres);
	}
	
	@Test
	public void nemLephet(){
		boolean sikeres = sotet.lep("c6", "b5", t);
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("c6", "c6", t);
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("c5", "c6", t);
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("c6", "f9", t);
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("c6", "d9", t);
		assertEquals(false, sikeres);
	}
	
	@Test
	public void lepesKenyszer(){
		boolean sikeres = vilagos.lep("b3", "c4", t);
		
		assertEquals(true, sikeres);
		
		sikeres = sotet.lep("a6", "b5", t);
		
		assertEquals(true, sikeres);
		
		sikeres = vilagos.lep("h3", "g4", t);
		
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("f3", "g4", t);
				
		assertEquals(false, sikeres);
		
		sikeres = vilagos.lep("c4", "a6", t);
		
		assertEquals(true, sikeres);
	}
	
	@Test
	public void teljesJatekTeszt(){
		
		while(t.getSotetBabuSzam() > 0 && t.getVilagosBabuSzam() > 0){
			assertEquals(false, t.vege());
			if(t.isKovetkezo()){
				assertEquals(true, utes(vilagos) || lepes(vilagos));
			}
			else if(!t.isKovetkezo()){
				assertEquals(true, utes(sotet) || lepes(sotet));
			}
		}
		
		assertEquals(0, t.getSotetBabuSzam() > t.getVilagosBabuSzam() ? t.getVilagosBabuSzam() : t.getSotetBabuSzam());
		assertEquals(true, t.vege());
	}
	
	@Test
	public void masikTeljesJatekTeszt(){
		
		while(t.getSotetBabuSzam() > 0 && t.getVilagosBabuSzam() > 0){
			assertEquals(false, t.vege());
			if(t.isKovetkezo()){
				assertEquals(true, masikUtes(vilagos) || masikLepes(vilagos));
			}
			else if(!t.isKovetkezo()){
				assertEquals(true, masikUtes(sotet) || masikLepes(sotet));
			}
		}
		
		assertEquals(0, t.getSotetBabuSzam() > t.getVilagosBabuSzam() ? t.getVilagosBabuSzam() : t.getSotetBabuSzam());
		assertEquals(true, t.vege());
	}
	
	private boolean lepes(Jatekos jatekos){
		boolean lepett = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				
				if(t.lephet(i, j, i+1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i+2), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i+1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i+2), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i-1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i-1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i), t))
						lepett = true;
				}
				
			}
		}
		
		return lepett;
	}
	
	private boolean masikLepes(Jatekos jatekos){
		boolean lepett = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(t.lephet(i, j, i-1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i-1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i+1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i+2), t))
						lepett = true;
				}
				else if(t.lephet(i, j, i+1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i+2), t))
						lepett = true;
				}
			}
		}
		
		return lepett;
	}
	
	private boolean utes(Jatekos jatekos){
		boolean utott = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				
				if(t.lephet(i, j, i+2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i+3), t))
						utott = true;
				}
				else if(t.lephet(i, j, i+2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i+3), t))
						utott = true;
				}
				else if(t.lephet(i, j, i-2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i-1), t))
						utott = true;
				}
				else if(t.lephet(i, j, i-2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i-1), t))
						utott = true;
				}
				
			}
		}
		return utott;
	}
	
	private boolean masikUtes(Jatekos jatekos){
		boolean utott = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(t.lephet(i, j, i-2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i-1), t))
						utott = true;
				}
				else if(t.lephet(i, j, i-2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i-1), t))
						utott = true;
				}
				else if(t.lephet(i, j, i+2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i+3), t))
						utott = true;
				}
				else if(t.lephet(i, j, i+2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i+3), t))
						utott = true;
				}
				
			}
		}
		return utott;
	}
}
