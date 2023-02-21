package com.apistarwars.ApiStarWars.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apistarwars.ApiStarWars.entidades.Films;

public class XmlUtils {
	public static void filmToXML(Films pelicula) throws
	ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("film");
		doc.appendChild(rootElement);
		Element title = doc.createElement("title");
		title.setTextContent(pelicula.getTitle());
		rootElement.appendChild(title);
		Element episode_id = doc.createElement("episode_id");
		episode_id.setTextContent(""+pelicula.getEpisode_id());
		rootElement.appendChild(episode_id);
		Element opening_crawl = doc.createElement("opening_crawl");
		opening_crawl.setTextContent(""+pelicula.getOpening_crawl());
		rootElement.appendChild(opening_crawl);
		Element director = doc.createElement("director");
		director.setTextContent(""+pelicula.getDirector());
		rootElement.appendChild(director);
		Element producer = doc.createElement("producer");
		producer.setTextContent(""+pelicula.getProducer());
		rootElement.appendChild(producer);
		Element release_date = doc.createElement("release_date");
		release_date.setTextContent(""+pelicula.getRelease_date());
		rootElement.appendChild(release_date);
		Element characters = doc.createElement("characters");
		for (String c: pelicula.getCharacters()) {
			Element character = doc.createElement("character");
			character.setTextContent(c);
			characters.appendChild(character);
		}
		rootElement.appendChild(characters);
		Element planets = doc.createElement("planets");
		for (String c: pelicula.getPlanets()) {
			Element planet = doc.createElement("planet");
			planet.setTextContent(c);
			planets.appendChild(planet);
		}
		rootElement.appendChild(planets);
		Element starships = doc.createElement("starships");
		for (String c: pelicula.getStarships()) {
			Element starship = doc.createElement("starship");
			starship.setTextContent(c);
			starships.appendChild(starship);
		}
		rootElement.appendChild(starships);
		Element vehicles = doc.createElement("vehicles");
		for (String c: pelicula.getVehicles()) {
			Element vehicle = doc.createElement("vehicle");
			vehicle.setTextContent(c);
			vehicles.appendChild(vehicle);
		}
		rootElement.appendChild(vehicles);
		Element species = doc.createElement("species");
		for (String c: pelicula.getSpecies()) {
			Element specimen = doc.createElement("specimen");
			specimen.setTextContent(c);
			species.appendChild(specimen);
		}
		rootElement.appendChild(species);
		Element created = doc.createElement("created");
		created.setTextContent(""+pelicula.getCreated());
		rootElement.appendChild(created);
		Element edited = doc.createElement("edited");
		edited.setTextContent(""+pelicula.getEdited());
		rootElement.appendChild(edited);
		Element url = doc.createElement("url");
		url.setTextContent(""+pelicula.getUrl());
		rootElement.appendChild(url);
		try (FileOutputStream output = new FileOutputStream
				("C:\\Users\\alex_\\Documents\\resultado.xml")) {
			writeXml(doc, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);

	}
	public static void mostrarXmlDom(String rutaCompleta) {
		try {
			File inputFile = new File(rutaCompleta);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("film");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Titulo: " + eElement.getElementsByTagName("title").item(0).getTextContent());
					System.out.println(
							"Episodio: " + eElement.getElementsByTagName("episode_id").item(0).getTextContent());
					System.out.println("Texto prólogo: " + eElement.getElementsByTagName("opening_crawl").item(0).getTextContent());
					System.out
							.println("Director: " + eElement.getElementsByTagName("director").item(0).getTextContent());
					System.out
					.println("Productor: " + eElement.getElementsByTagName("producer").item(0).getTextContent());
					System.out
					.println("Personajes: " + eElement.getElementsByTagName("characters").item(0).getTextContent());
					System.out
					.println("Planetas: " + eElement.getElementsByTagName("planets").item(0).getTextContent());
					System.out
					.println("Naves: " + eElement.getElementsByTagName("starships").item(0).getTextContent());
					System.out
					.println("Vehículos: " + eElement.getElementsByTagName("vehicles").item(0).getTextContent());
					System.out
					.println("Especies: " + eElement.getElementsByTagName("species").item(0).getTextContent());
					System.out
					.println("Creado: " + eElement.getElementsByTagName("created").item(0).getTextContent());
					System.out
					.println("Editado: " + eElement.getElementsByTagName("edited").item(0).getTextContent());
					System.out
					.println("Url: " + eElement.getElementsByTagName("url").item(0).getTextContent());
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
