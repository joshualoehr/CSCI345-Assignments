package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public abstract class Room extends Observable {

	private static HashMap<String, Room> rooms = new HashMap<String, Room>();

	public static ArrayList<Room> getAllRooms() {
		return new ArrayList<Room>(rooms.values());
	}
	
	public static Room getRoom(String roomName) {
		return rooms.get(roomName);
	}
	
	public abstract ArrayList<Role> getAllRoles();

	public Room(String name){
		this.name = name;
		rooms.put(name,this);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Room)) {
			return false;
		}
		return name.equals(((Room) obj).name);
	}

	private String name;
	private ArrayList<Room> adjacentRooms = new ArrayList<Room>();

	public ArrayList<Room> getAdjacentRooms() {
		return adjacentRooms;
	}

	public void setAdjacentRooms(Room... rooms) {
		for (Room room : rooms) {
			setAdjacentRoom(room);
		}
	}
	
	public void setAdjacentRoom(Room neighbor) {
		adjacentRooms.add(neighbor);
	}
}
