package model;
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


	//Reads in cards from given file and parses out individuals fields using DOM xml parsing
	//Then returns LinkedList of type Scene for distribution out to Rooms
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

	//Reads in board from boardFile parsing out and creating Rooms from the information
	//Both initializes and sets values for all rooms
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
									
									Node setChildN = setChildren.item(i);
									
									if (setChildN.getNodeName().equals("area")){
										Element setChildE = (Element) setChildN;
										int xOfRoom = Integer.parseInt(setChildE.getAttribute("x"));
										int yOfRoom = Integer.parseInt(setChildE.getAttribute("y"));
										int heightOfRoom = Integer.parseInt(setChildE.getAttribute("h"));
										int widthOfRoom = Integer.parseInt(setChildE.getAttribute("w"));
									}

									NodeList setGrandChildren = setChildren.item(i).getChildNodes();

									for (int j = 0; j < setGrandChildren.getLength(); j++) {
										Node setGrandChild = setGrandChildren.item(j);

										switch (setGrandChild.getNodeName()) {
											case "neighbor":
                                                if (((Element) setGrandChild).getAttribute("name").equals("office")){
                                                	
                                                    Room neighbor = Room.getRoom("Casting Office");
                                                    room.setAdjacentRoom(neighbor);
                                                }
                                                else if(((Element) setGrandChild).getAttribute("name").equals("trailer")){
                                                    Room neighbor = Room.getRoom("Trailers");
                                                    room.setAdjacentRoom(neighbor);
                                                }
                                                else {
                                                    Room neighbor = Room.getRoom(((Element) setGrandChild).getAttribute("name"));
                                                    room.setAdjacentRoom(neighbor);
                                                }
												break;
											case "part":
												Element part = ((Element) setGrandChild);
												ExtraRole currRole = new ExtraRole(
														part.getAttribute("name"),
														part.getTextContent().trim(),
														Integer.parseInt(part.getAttribute("level")));
												Node currPartArea = setGrandChild.getFirstChild();
												Element currPartAreaE = (Element) currPartArea;
												int xOfPart = Integer.parseInt(currPartAreaE.getAttribute("x"));
												int yOfPart = Integer.parseInt(currPartAreaE.getAttribute("y"));
												int heightOfPart = Integer.parseInt(currPartAreaE.getAttribute("h"));
												int widthOfPart = Integer.parseInt(currPartAreaE.getAttribute("w"));
												room.addExtraRole(currRole);
												break;
											case "take":
												if (room.getMaxShotCounter() == 0) {
													String numStr = ((Element) setGrandChild).getAttribute("number");
													int number = Integer.parseInt(numStr);
													room.initShotCounters(number);
												}
												else{
													NodeList takesList = setGrandChild.getChildNodes();
													for (int k=0; k< takesList.getLength(); k++){
														Node currTake = takesList.item(k);
														Element currTakeAsE = (Element) currTake;
														currTakeAsE.getAttribute("number");
														Element areaOfTake = (Element) currTake.getFirstChild();
														int xOfTake = Integer.parseInt(areaOfTake.getAttribute("x"));
														int yOfTake = Integer.parseInt(areaOfTake.getAttribute("y"));
														int heighOfTake = Integer.parseInt(areaOfTake.getAttribute("h"));
														int widthOfTake = Integer.parseInt(areaOfTake.getAttribute("w"));
														
													}
												}
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
			//Hard Coded in so that we would not have to handle another case
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
