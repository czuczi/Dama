package csomag;
import java.io.File;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * A XML állományok kezelését megvalósító osztály.
 * @author Czuczi
 *
 */
public class XML {
	/**
	 * Egy XML dokumentumba menti el a játék állását.
	 * @param tabla Egy bábukat tartalmazó mátrix.
	 * @param jatekos1 Az egyik játékos.
	 * @param jatekos2 A másik játékos.
	 * @param allomanyNev Az állomány neve.
	 */
	public void XMLkeszito(Tabla tabla, Jatekos jatekos1, Jatekos jatekos2, String allomanyNev){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.newDocument();
			
			Element root = doc.createElement("játékállás");
			doc.appendChild(root);
			
			Element tablaAllas = doc.createElement("tábla");
			root.appendChild(tablaAllas);
			
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(tabla.getTabla()[i][j] instanceof Babu){
						Element sor = doc.createElement("sor");
						sor.setAttribute("érték", String.valueOf(i));
						tablaAllas.appendChild(sor);
						
						Element oszlop = doc.createElement("oszlop");
						oszlop.setAttribute("érték", String.valueOf(j));
						sor.appendChild(oszlop);
						
						Element szin = doc.createElement("szín");
						szin.appendChild(doc.createTextNode(tabla.getTabla()[i][j].isVilagos() ? "világos" : "sötét"));
						oszlop.appendChild(szin);
						
						Element dama = doc.createElement("dáma");
						dama.appendChild(doc.createTextNode(tabla.getTabla()[i][j].isDama() ? "igen" : "nem"));
						oszlop.appendChild(dama);
					}
				}
			}
			
			Element babuSzam = doc.createElement("bábuszám");
			tablaAllas.appendChild(babuSzam);
			
			Element vilagosSzam = doc.createElement("világosszám");
			babuSzam.appendChild(vilagosSzam);
			vilagosSzam.appendChild(doc.createTextNode(String.valueOf(tabla.getVilagosBabuSzam())));
			
			Element sotetSzam = doc.createElement("sötétszám");
			babuSzam.appendChild(sotetSzam);
			sotetSzam.appendChild(doc.createTextNode(String.valueOf(tabla.getSotetBabuSzam())));
			
			Element kovetkezo = doc.createElement("következő");
			tablaAllas.appendChild(kovetkezo);
			kovetkezo.appendChild(doc.createTextNode(tabla.isKovetkezo() ? "világos" : "sötét"));
			
			Element jatekosok = doc.createElement("Játékosok");
			root.appendChild(jatekosok);
			
			{
				Element jatekos = doc.createElement("játékos");
				jatekosok.appendChild(jatekos);
				
				Element szin = doc.createElement("szín");
				jatekos.appendChild(szin);
				szin.appendChild(doc.createTextNode(jatekos1.isVilagos() ? "világos" : "sötét"));
				
				Element elozoHovaX = doc.createElement("előzőhováx");
				jatekos.appendChild(elozoHovaX);
				elozoHovaX.appendChild(doc.createTextNode(String.valueOf(jatekos1.getElozoHovaX())));
				
				Element elozoHovaY = doc.createElement("előzőhováy");
				jatekos.appendChild(elozoHovaY);
				elozoHovaY.appendChild(doc.createTextNode(String.valueOf(jatekos1.getElozoHovaY())));
			}
			
			{
				Element jatekos = doc.createElement("játékos");
				jatekosok.appendChild(jatekos);
				
				Element szin = doc.createElement("szín");
				jatekos.appendChild(szin);
				szin.appendChild(doc.createTextNode(jatekos2.isVilagos() ? "világos" : "sötét"));
				
				Element elozoHovaX = doc.createElement("előzőhováx");
				jatekos.appendChild(elozoHovaX);
				elozoHovaX.appendChild(doc.createTextNode(String.valueOf(jatekos2.getElozoHovaX())));
				
				Element elozoHovaY = doc.createElement("előzőhováy");
				jatekos.appendChild(elozoHovaY);
				elozoHovaY.appendChild(doc.createTextNode(String.valueOf(jatekos2.getElozoHovaY())));
			}
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(allomanyNev);
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Egy XML dokumentumból tölti be a játék állását.
	 * @param tabla Egy bábukat tartalmazó mátrix.
	 * @param jatekos1 Az egyik játékos.
	 * @param jatekos2 A másik játékos.
	 * @param allomanyNev Az állomány neve.
	 */
	public void XMLbetolto(Tabla tabla, Jatekos jatekos1, Jatekos jatekos2, String allomanyNev){
		try {
			
			Babu[][] segedTabla = new Babu[8][8];
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(allomanyNev);
			
			NodeList sorok = doc.getElementsByTagName("sor");
			
			for(int i = 0; i < sorok.getLength(); i++){
				
				Element sor = (Element) sorok.item(i);

				int sorSzam = Integer.parseInt(sor.getAttribute("érték"));
				
				Element oszlop = (Element) sor.getElementsByTagName("oszlop").item(0);
				
				int oszlopSzam = Integer.parseInt(oszlop.getAttribute("érték"));
				
				boolean vilagos = oszlop.getElementsByTagName("szín").item(0).getTextContent().equals("világos") ? true : false;
				
				boolean dáma = oszlop.getElementsByTagName("dáma").item(0).getTextContent().equals("igen") ? true : false;
				
				segedTabla[sorSzam][oszlopSzam] = new Babu(vilagos);
				segedTabla[sorSzam][oszlopSzam].setDama(dáma);

			}
			
			Element babuSzam = (Element) doc.getElementsByTagName("bábuszám").item(0);
			
			int vilagosSzam = Integer.parseInt(babuSzam.getElementsByTagName("világosszám").item(0).getTextContent());
			int sotetSzam = Integer.parseInt(babuSzam.getElementsByTagName("sötétszám").item(0).getTextContent());
			boolean kovetkezo = doc.getElementsByTagName("következő").item(0).getTextContent().equals("világos") ? true : false;
			
			tabla.setTabla(segedTabla);
			tabla.setVilagosBabuSzam(vilagosSzam);
			tabla.setSotetBabuSzam(sotetSzam);
			tabla.setKovetkezo(kovetkezo);
			
			
			NodeList jatekosok = doc.getElementsByTagName("játékos");
			
			for(int i = 0; i < jatekosok.getLength(); i++){
				Element jatekos = (Element) jatekosok.item(i);
				
				if(jatekos.getElementsByTagName("szín").item(0).getTextContent().equals("világos")){
					if(jatekos1.isVilagos()){
						jatekos1.setElozoHovaX(Integer.parseInt(jatekos.getElementsByTagName("előzőhováx").item(0).getTextContent()));
						jatekos1.setElozoHovaY(Integer.parseInt(jatekos.getElementsByTagName("előzőhováy").item(0).getTextContent()));
					}
					
					if(jatekos2.isVilagos()){
						jatekos2.setElozoHovaX(Integer.parseInt(jatekos.getElementsByTagName("előzőhováx").item(0).getTextContent()));
						jatekos2.setElozoHovaY(Integer.parseInt(jatekos.getElementsByTagName("előzőhováy").item(0).getTextContent()));
					}
				}
				
				if(jatekos.getElementsByTagName("szín").item(0).getTextContent().equals("sötét")){
					if(!jatekos1.isVilagos()){
						jatekos1.setElozoHovaX(Integer.parseInt(jatekos.getElementsByTagName("előzőhováx").item(0).getTextContent()));
						jatekos1.setElozoHovaY(Integer.parseInt(jatekos.getElementsByTagName("előzőhováy").item(0).getTextContent()));
					}
					
					if(!jatekos2.isVilagos()){
						jatekos2.setElozoHovaX(Integer.parseInt(jatekos.getElementsByTagName("előzőhováx").item(0).getTextContent()));
						jatekos2.setElozoHovaY(Integer.parseInt(jatekos.getElementsByTagName("előzőhováy").item(0).getTextContent()));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
