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

import java.util.*;
import java.awt.Rectangle;

public class InfoParser {

	private static HashMap<String,Rectangle> cardPartsPositions = new HashMap<String,Rectangle>();
	private static HashMap<String,Rectangle> roomPositions = new HashMap<String,Rectangle>();
	private static HashMap<String,Rectangle> extraPartsPositions = new HashMap<String,Rectangle>();
	private static HashMap<String,ArrayList<Rectangle>> takesPositions = new HashMap<String,ArrayList<Rectangle>>();

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
							
							NodeList holder = partElement.getElementsByTagName("area");
							Node nodeHolder = holder.item(0);
							Element eHolder = (Element) nodeHolder;
							
							int xOfPartC = Integer.parseInt(eHolder.getAttribute("x"));
							int yOfPartC = Integer.parseInt(eHolder.getAttribute("y"));
							int heightOfPartC = Integer.parseInt(eHolder.getAttribute("h"));
							int widthOfPartC = Integer.parseInt(eHolder.getAttribute("w"));
							
							Rectangle cardPartsRect = new Rectangle(xOfPartC,yOfPartC,widthOfPartC,heightOfPartC);
							
							cardPartsPositions.put(partElement.getAttribute("name"),cardPartsRect);
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
										
										Rectangle positonsRooms = new Rectangle(xOfRoom,yOfRoom,widthOfRoom,heightOfRoom);
										
										roomPositions.put(roomName, positonsRooms);
										//Room roomWPoints = new Room(xOfRoom,yOfRoom,heightOfRoom,widthOfRoom,room);
										//System.out.println("read area of "+roomName);
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
												NodeList holder = part.getElementsByTagName("area");
												Node nodeHolder = holder.item(0);
												Element eHolder = (Element) nodeHolder;
												
												int xOfPart = Integer.parseInt(eHolder.getAttribute("x"));
												int yOfPart = Integer.parseInt(eHolder.getAttribute("y"));
												int heightOfPart = Integer.parseInt(eHolder.getAttribute("h"));
												int widthOfPart = Integer.parseInt(eHolder.getAttribute("w"));
												
												Rectangle extraRect = new Rectangle(xOfPart,yOfPart,widthOfPart,heightOfPart);
												
												//System.out.println(part.getAttribute("name")+" "+xOfPart+" "+yOfPart+" "+heightOfPart+" "+widthOfPart);
												
												extraPartsPositions.put(part.getAttribute("name"),extraRect);
												
												room.addExtraRole(currRole);
												break;
											case "take":
												if (room.getMaxShotCounter() == 0) {
													String numStr = ((Element) setGrandChild).getAttribute("number");
													int number = Integer.parseInt(numStr);
													room.initShotCounters(number);
												}

												Element part2 = ((Element) setGrandChild);
												NodeList holder2 = part2.getElementsByTagName("area");
												Node nodeHolder2 = holder2.item(0);
												Element eHolder2 = (Element) nodeHolder2;
												
												int xOfTake = Integer.parseInt(eHolder2.getAttribute("x"));
												int yOfTake = Integer.parseInt(eHolder2.getAttribute("y"));
												int heightOfTake = Integer.parseInt(eHolder2.getAttribute("h"));
												int widthOfTake = Integer.parseInt(eHolder2.getAttribute("w"));
												
												Rectangle takePosRect = new Rectangle(xOfTake,yOfTake,heightOfTake,widthOfTake);
												
												
												if (takesPositions.get(roomName) == null){
													ArrayList<Rectangle> myArrayOfArrays = new ArrayList<Rectangle>();
													myArrayOfArrays.add(takePosRect);
													takesPositions.put(roomName,myArrayOfArrays);
												}
												else{
													takesPositions.get(roomName).add(takePosRect);
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
            
            Rectangle trailerArray = new Rectangle(991,248,201,194);
            
            roomPositions.put("trailer",trailerArray);
            
            TrailerRoom trailRoom = (TrailerRoom) Room.getRoom("Trailers");
            trailRoom.setAdjacentRoom(Room.getRoom("Main Street"));
            trailRoom.setAdjacentRoom(Room.getRoom("Saloon"));
            trailRoom.setAdjacentRoom(Room.getRoom("Hotel"));
            myArray.add(trailRoom);
            
            Rectangle officeArray = new Rectangle(9,459,209,208);
            
            roomPositions.put("office",officeArray);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return myArray;
	}
	
	public static HashMap<String,Rectangle> getCardPartsPositions(){
		return cardPartsPositions;
	}
	
	public static HashMap<String,Rectangle> getRoomPositions(){
		return roomPositions;
	}
	
	public static HashMap<String,Rectangle> getExtraPartsPositions(){
		return extraPartsPositions;
	}
	
	public static HashMap<String,ArrayList<Rectangle>> getTakesPositions(){
		return takesPositions;
	}

}
