package csci345;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Room {

	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public static Room getRoom(String roomName) {
		return rooms.get(roomName);
	}

	public Room(String name){
		this.name = name;
		rooms.put(name,this);
	}

	/*public void putRoom(String name, Room newRoom) {
		this.name = name;
		rooms.put(name, newRoom);
	}*/

	@Override
	public String toString() {
		return name;
	}

	private String name;
	private ArrayList<Room> adjacentRooms = new ArrayList<Room>();

	public ArrayList<Room> getAdjacentRooms() {
		return adjacentRooms;
	}

	public void setAdjacentRoom(Room room){
		adjacentRooms.add(room);
		//room.setAdjacentRoom(room);
	}

	public ArrayList<Role> getAllRoles(){
		return getAllRoles();
	}

}
