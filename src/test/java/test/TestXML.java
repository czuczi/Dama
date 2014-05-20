package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import csomag.Babu;
import csomag.Jatekos;
import csomag.Tabla;
import csomag.XML;

public class TestXML {
	private Tabla t1;
	private Jatekos vilagos1;
	private Jatekos sotet1;
	
	private Tabla t2;
	private Jatekos vilagos2;
	private Jatekos sotet2;
	
	private Tabla t3;
	private Jatekos vilagos3;
	private Jatekos sotet3;
	
	private static final String OSZLOPOK = "abcdefgh";
	private static final String SOROK = "12345678";
	private XML xml;
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	@Before
	public void Inicializalas(){
		t1 = new Tabla();
		vilagos1 = new Jatekos(true);
		sotet1 = new Jatekos(false);
		
		t2 = new Tabla();
		vilagos2 = new Jatekos(true);
		sotet2 = new Jatekos(false);
		
		t3 = new Tabla();
		vilagos3 = new Jatekos(true);
		sotet3 = new Jatekos(false);
		
		xml = new XML();
	}

	@Test
	public void MentesBetoltesSokszor() throws IOException{
		
		while(t1.getSotetBabuSzam() > 0 && t1.getVilagosBabuSzam() > 0){
			if(t1.isKovetkezo()){
				assertEquals(true, utes(vilagos1) || lepes(vilagos1));
			}
			else if(!t1.isKovetkezo()){
				assertEquals(true, utes(sotet1) || lepes(sotet1));
			}
			
			xml.XMLkeszito(t1, vilagos1, sotet1, folder.getRoot() + "//teszt1.xml");
			xml.XMLkeszito(t1, sotet1, vilagos1, folder.getRoot() + "//teszt2.xml");
			
			File[] f = folder.getRoot().listFiles();
			
			xml.XMLbetolto(t2, vilagos2, sotet2, f[0].getAbsolutePath());
			xml.XMLbetolto(t3, sotet3, vilagos3, f[1].getAbsolutePath());
			
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(t1.getTabla()[i][j] instanceof Babu){
						assertEquals(t1.getTabla()[i][j].isVilagos(), t2.getTabla()[i][j].isVilagos());
						assertEquals(t1.getTabla()[i][j].isDama(), t2.getTabla()[i][j].isDama());
						
						assertEquals(t2.getTabla()[i][j].isVilagos(), t3.getTabla()[i][j].isVilagos());
						assertEquals(t2.getTabla()[i][j].isDama(), t3.getTabla()[i][j].isDama());
					}
				}
			}
			
			assertEquals(vilagos1.getElozoHovaX(), vilagos2.getElozoHovaX());
			assertEquals(vilagos1.getElozoHovaY(), vilagos2.getElozoHovaY());
			assertEquals(vilagos2.getElozoHovaX(), vilagos3.getElozoHovaX());
			assertEquals(vilagos2.getElozoHovaY(), vilagos3.getElozoHovaY());
		}
		
		assertEquals(0, t1.getSotetBabuSzam() > t1.getVilagosBabuSzam() ? t1.getVilagosBabuSzam() : t1.getSotetBabuSzam());
	}
	
	private boolean lepes(Jatekos jatekos){
		boolean lepett = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				
				if(t1.lephet(i, j, i+1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i+2), t1))
						lepett = true;
				}
				else if(t1.lephet(i, j, i+1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i+2), t1))
						lepett = true;
				}
				else if(t1.lephet(i, j, i-1, j-1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-1, j) + (i), t1))
						lepett = true;
				}
				else if(t1.lephet(i, j, i-1, j+1, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+1, j+2) + (i), t1))
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
				
				if(t1.lephet(i, j, i+2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i+3), t1))
						utott = true;
				}
				else if(t1.lephet(i, j, i+2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i+3), t1))
						utott = true;
				}
				else if(t1.lephet(i, j, i-2, j-2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j-2, j-1) + (i-1), t1))
						utott = true;
				}
				else if(t1.lephet(i, j, i-2, j+2, jatekos.isVilagos())){
					if(jatekos.lep(OSZLOPOK.substring(j, j+1) + (i+1), OSZLOPOK.substring(j+2, j+3) + (i-1), t1))
						utott = true;
				}
				
			}
		}
		return utott;
	}
}
