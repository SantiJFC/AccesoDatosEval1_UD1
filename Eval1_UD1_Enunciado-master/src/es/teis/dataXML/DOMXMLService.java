/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author maria
 */
public class DOMXMLService implements IXMLService {

    /**
     * *
     *
     * @param ruta ruta al fichero XML que se leerá
     *
     * @return ArrayList<Partido> que superan el umbral
     * @throws LecturaException en caso de que surja cualquier excepción durante
     * la lectura
     */
    @Override
    public ArrayList<Partido> leerPartidos(String ruta) throws LecturaException {
        throw new UnsupportedOperationException();

    }


    @Override
    public void escribir(ArrayList<Partido> partidos, String ruta) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "partidos", null);
            document.setXmlStandalone(true);

            Element raiz = document.getDocumentElement();

            for (Partido partido : partidos) {
                Element partidoElement = document.createElement("partido");
                partidoElement.setAttribute("id", String.valueOf(partido.getId()));
                raiz.appendChild(partidoElement);

                addElementConTexto(document, partidoElement, "votos_porciento", String.valueOf(partido.getPorcentaje()));
                addElementConTexto(document, partidoElement, "nombre", partido.getNombre());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(document), new StreamResult(new File(ruta)));
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        } catch (TransformerException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
    }
    
    private static void addElementConTexto(Document document, Node padre, String tag, String text) {
        //Creamos un nuevo nodo de tipo elemento desde document
        Node node = document.createElement(tag);
        //Creamos un nuevo nodo de tipo texto también desde document
        Node nodeTexto = document.createTextNode(text);
        //añadimos a un nodo padre el nodo elemento
        padre.appendChild(node);
        //Añadimos al nodo elemento su nodo hijo de tipo texto
        node.appendChild(nodeTexto);
    }

}
