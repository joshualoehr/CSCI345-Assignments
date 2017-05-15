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
        readBoard();
    }

    public static ArrayList<Scene> readCards() {

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

                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int budget = Integer.parseInt(eElement.getAttribute("budget"));
                    int sceneNumber = Integer.parseInt(((Element) eElement.getElementsByTagName("scene").item(0)).getAttribute("number"));
                    String name = eElement.getAttribute("name");
                    String description = eElement.getElementsByTagName("scene").item(0).getTextContent();
                    ArrayList<StarringRole> stars = null;
                    NodeList partList = eElement.getElementsByTagName("part");
                    for (int tempPart = 0; tempPart < partList.getLength(); tempPart++) {
                        Node partNode = partList.item(tempPart);
                        System.out.println("\nThis is a : " + partNode.getNodeName());
                        if (partNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element partElement = (Element) partNode;
                            String namePart = partElement.getAttribute("name");
                            String descriptionPart = partElement.getElementsByTagName("line").item(0).getTextContent();
                            int level = Integer.parseInt(partElement.getAttribute("level"));
                            StarringRole thisRole = new StarringRole(namePart, descriptionPart, level);
                            stars.add(thisRole);
                        }
                    }
                    Scene sceneToAdd = new Scene(budget, sceneNumber, name, description, stars);
                    myArray.add(sceneToAdd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myArray;
    }

    public static ArrayList<Room> readBoard() {

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

                    NodeList setList = eElement.getElementsByTagName("set");
                    int s;
                    for (s = 0; s < setList.getLength(); s++) {
                        Node n = setList.item(s);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element setElement = (Element) n;
                            String roomName = setElement.getAttribute("name");
                            new SceneRoom(roomName);
                        }
                    }

                    for (s = 0; s < setList.getLength(); s++) {
                        Node n = setList.item(s);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element setElement = (Element) n;
                            String roomName = setElement.getAttribute("name");
                            Room room = Room.getRoom(roomName);



                            NodeList setChildren = setElement.getChildNodes();

                            for (int i = 0; i < setChildren.getLength(); i++) {
                                NodeList setGrandChildren = setChildren.item(i).getChildNodes();

                                for (int j = 0; j < setGrandChildren.getLength(); j++) {
                                    Node setGrandChild = setGrandChildren.item(j);
                                    switch (setGrandChild.getNodeName()) {
                                        case "neighbor":
                                            Room neighbor = Room.getRoom(((Element) setGrandChild).getAttribute("name"));
                                            System.out.println(((Element) setGrandChild).getAttribute("name"));
                                            room.setAdjacentRoom(neighbor);
                                            break;
                                        case "parts":

                                            break;
                                        case "takes":
                                            break;
                                    }
                                }
                            }

                            /*
                            for (int tempN = 0; tempN < neighList.getLength(); tempN++) {
                                Node neighNode = neighList.item(tempN);
                                System.out.println("\nThese are neighbors to : " + setElement.getAttribute("name"));
                                if (neighNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element neighElement = (Element) neighNode;
                                    NodeList neighs = neighElement.getElementsByTagName("neighbor");
                                    for (int i = 0; i < neighs.getLength(); i++) {
                                        //Element
                                    }
                                }
                            }
                            */
                        }
                    }

                    for (int tempPart = 0; tempPart < setList.getLength(); tempPart++){
                       Node setNode = setList.item(tempPart);
                        if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element setElement = (Element) setNode;

                            NodeList neighList = setElement.getElementsByTagName("neighbors");
                            for (int tempN = 0; tempN < neighList.getLength(); tempN++){
                                Node neighNode = neighList.item(tempN);
                                System.out.println("\nThese are neighbors to : " + setElement.getAttribute("name"));
                                if (neighNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element neighElement = (Element) neighNode;
                                    NodeList neighs = neighElement.getElementsByTagName("neighbor");
                                    //ArrayList<Room> myAdjList = new ArrayList<Room>();
                                    for(int i = 0; i< neighs.getLength(); i++){
                                        Element testElement = (Element) neighs.item(i);
                                        //myAdjList.add(new SceneRoom(testElement.getAttribute("name")));
                                        System.out.println("Neighbor name : " + testElement.getAttribute("name"));
                                    }
                                }
                            }

                            NodeList takes = setElement.getElementsByTagName("takes");
                            for (int tempN = 0; tempN < takes.getLength(); tempN++){
                                Node takesNode = takes.item(tempN);
                                if (takesNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element takesElement = (Element) takesNode;
                                    NodeList takesList = takesElement.getElementsByTagName("take");
                                    Element testElement = (Element) takesList.item(0);
                                    System.out.println("\n Takes is : " + testElement.getAttribute("number"));
                                }
                            }

                            NodeList partsList = setElement.getElementsByTagName("parts");
                            for (int tempN = 0; tempN < partsList.getLength(); tempN++){
                                Node partsNode = partsList.item(tempN);
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
