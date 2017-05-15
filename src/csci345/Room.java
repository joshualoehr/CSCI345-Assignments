package csci345;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Room {

	private static HashMap<String, Room> rooms;

	public static Room getRoom(String roomName) {
		return rooms.get(roomName);
	}

	public Room(String name){
		this.name = name;
		rooms.put(name,this);
	}
	
	@Override
	public String toString() {
		return name;
	}

	private String name;
	private ArrayList<Room> adjacentRooms;

	public ArrayList<Room> getAdjacentRooms() {
		return null;
	}

	public void setAdjacentRooms(Room... rooms){
		adjacentRooms = (ArrayList<Room>)Arrays.asList(rooms);
	}

}
