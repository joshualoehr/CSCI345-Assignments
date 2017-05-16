package csci345;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class InfoParser {

	public static LinkedList<Scene> readCards(String cardFile) {

		LinkedList<Scene> myArray = new LinkedList<Scene>();

		try {
			File inputFile = new File(cardFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("card");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int budget = Integer.parseInt(eElement.getAttribute("budget"));
					int sceneNumber = Integer.parseInt(((Element) eElement.getElementsByTagName("scene").item(0)).getAttribute("number"));
					String name = eElement.getAttribute("name");
					String description = eElement.getElementsByTagName("scene").item(0).getTextContent();
					ArrayList<StarringRole> stars = new ArrayList<StarringRole>();
					NodeList partList = eElement.getElementsByTagName("part");

					for (int tempPart = 0; tempPart < partList.getLength(); tempPart++) {
						Node partNode = partList.item(tempPart);

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

	public static ArrayList<Room> readBoard(String boardFile) {

		ArrayList<Room> myArray = new ArrayList<Room>();

		try {
			File inputFile = new File(boardFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("board");
            new CastingRoom("Casting Office");
            new TrailerRoom("Trailers");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList setList = eElement.getElementsByTagName("set");

					int s;
					for (s = 0; s < setList.getLength(); s++) {
						Node n = setList.item(s);

						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element setElement = (Element) n;
							String roomName = setElement.getAttribute("name");
							SceneRoom newRoom = new SceneRoom(roomName);
							myArray.add(newRoom);

						}
					}
					for (s = 0; s < setList.getLength(); s++) {
						Node n = setList.item(s);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element setElement = (Element) n;
							String roomName = setElement.getAttribute("name");

							if (!(roomName.equals("office") || roomName.equals("trailer"))) {
								SceneRoom room = (SceneRoom) Room.getRoom(roomName);
								NodeList setChildren = setElement.getChildNodes();

								for (int i = 0; i < setChildren.getLength(); i++) {
									NodeList setGrandChildren = setChildren.item(i).getChildNodes();

									for (int j = 0; j < setGrandChildren.getLength(); j++) {
										Node setGrandChild = setGrandChildren.item(j);

										switch (setGrandChild.getNodeName()) {
											case "neighbor":
                                                String neighborName = ((Element) setGrandChild).getNodeName();
											    if (neighborName.equals("office")){
                                                    Room neighbor = Room.getRoom("Casting Office");
                                                    room.setAdjacentRoom(neighbor);
                                                }
                                                else if(neighborName.equals("trailer")){
											        Room neighbor = Room.getRoom("Trailers");
                                                    room.setAdjacentRoom(neighbor);
                                                }
                                                else{
                                                    if (((Element) setGrandChild).getAttribute("name").equals("office")){
                                                        Room neighbor = Room.getRoom("Casting Office");
                                                        room.setAdjacentRoom(neighbor);
                                                    }
                                                    else if(((Element) setGrandChild).getAttribute("name").equals("trailer")){
                                                        Room neighbor = Room.getRoom("Trailers");
                                                        room.setAdjacentRoom(neighbor);
                                                    }
                                                    else{
                                                        Room neighbor = Room.getRoom(((Element) setGrandChild).getAttribute("name"));
                                                        room.setAdjacentRoom(neighbor);
                                                    }


                                                }
												break;
											case "part":
												Element part = ((Element) setGrandChild);
												ExtraRole currRole = new ExtraRole(
														part.getAttribute("name"),
														part.getTextContent().trim(),
														Integer.parseInt(part.getAttribute("level")));
												room.addExtraRole(currRole);
												break;
											case "takes":
												((Element) setGrandChild).getAttribute("number");
												break;
										}
									}
								}
								myArray.add(room);
							}
						}
					}
				}
			}
            CastingRoom castRoom = (CastingRoom) Room.getRoom("Casting Office");
            castRoom.setAdjacentRoom(Room.getRoom("Train Station"));
            castRoom.setAdjacentRoom(Room.getRoom("Ranch"));
            castRoom.setAdjacentRoom(Room.getRoom("Secret Hideout"));
            myArray.add(castRoom);
            TrailerRoom trailRoom = (TrailerRoom) Room.getRoom("Trailers");
            trailRoom.setAdjacentRoom(Room.getRoom("Main Street"));
            trailRoom.setAdjacentRoom(Room.getRoom("Saloon"));
            trailRoom.setAdjacentRoom(Room.getRoom("Hotel"));
            myArray.add(trailRoom);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return myArray;
	}

}
