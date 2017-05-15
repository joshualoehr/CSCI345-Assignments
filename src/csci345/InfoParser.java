package csci345;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class InfoParser {

    public static void main(String[] args){
        readCards();
    }

    public static ArrayList<Scene> readCards(){

        ArrayList<Scene> myArray = new ArrayList<Scene>();

        try {
            File inputFile = new File("cards.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("card");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Scene sceneToAdd = new Scene();
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Scene name : " + eElement.getAttribute("name"));
                    System.out.println("Scene img : " + eElement.getAttribute("img"));
                    System.out.println("Scene budget : " + eElement.getAttribute("budget"));
                    Element dontWanna = (Element) eElement.getElementsByTagName("scene").item(0);
                    System.out.println("Scene number : " + ((Element) eElement.getElementsByTagName("scene").item(0)).getAttribute("number"));
                    System.out.println("Scene description : " + eElement.getElementsByTagName("scene").item(0).getTextContent());
                    NodeList partList = eElement.getElementsByTagName("part");
                    for (int tempPart = 0; tempPart < partList.getLength(); tempPart++){
                        Node partNode = partList.item(tempPart);
                        System.out.println("\nThis is a : " + partNode.getNodeName());
                        if (partNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element partElement = (Element) partNode;
                            System.out.println("Part name : " + partElement.getAttribute("name"));
                            System.out.println("Part level : " + partElement.getAttribute("level"));
                            //System.out.println("Part Line : " + partElement.
                            System.out.println("Part Line : " + partElement.getElementsByTagName("line").item(0).getTextContent());
                            //System.out.println("Part description : " + partElement.getElementsByTagName("part").item(0).getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myArray;
    }

    public static ArrayList<Room> readBoard(){

        ArrayList<Room> myArray = new ArrayList<Room>();

        try {
            File inputFile = new File("board.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("board");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Set name : " + eElement.getAttribute("name"));
                    //System.out.println("Scene img : " + eElement.getAttribute("img"));
                    //System.out.println("Scene budget : " + eElement.getAttribute("budget"));
                    //System.out.println("Scene description : " + eElement.getElementsByTagName("scene").item(0).getTextContent());
                    NodeList setList = eElement.getElementsByTagName("set");
                    for (int tempPart = 0; tempPart < setList.getLength(); tempPart++){
                       Node setNode = setList.item(tempPart);
                        //System.out.println("\nCurrent Element : " + setNode.getNodeName());
                        if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element setElement = (Element) setNode;
                            //System.out.println("Set name : " + setElement.getAttribute("name"));

                            NodeList neighList = setElement.getElementsByTagName("neighbors");
                            for (int tempN = 0; tempN < neighList.getLength(); tempN++){
                                Node neighNode = neighList.item(tempN);
                                System.out.println("\nThese are neighbors to : " + setElement.getAttribute("name"));
                                if (neighNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element neighElement = (Element) neighNode;
                                    NodeList neighs = neighElement.getElementsByTagName("neighbor");
                                    for(int i = 0; i< neighs.getLength(); i++){
                                        Element testElement = (Element) neighs.item(i);
                                        System.out.println("Neighbor name : " + testElement.getAttribute("name"));
                                    }

                                }
                            }
                            System.out.println("\nThese parts are in " + setElement.getAttribute("name"));
                            NodeList partsList = setElement.getElementsByTagName("parts");
                            for (int tempN = 0; tempN < partsList.getLength(); tempN++){
                                Node partsNode = partsList.item(tempN);
                                //System.out.println("\nElement Inside Part : " + partsNode.getNodeName());
                                if (partsNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element partsElement = (Element) partsNode;
                                    NodeList parts = partsElement.getElementsByTagName("part");
                                    for(int i = 0; i< parts.getLength(); i++){
                                        Element testElement = (Element) parts.item(i);
                                        System.out.println("\nPart name : " + testElement.getAttribute("name"));
                                        System.out.println("Part level : " + testElement.getAttribute("level"));
                                        System.out.println("Part description : " + parts.item(i).getTextContent());
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myArray;
    }

}
