package csci345;

import java.util.ArrayList;

public class TrailerRoom extends Room {
    public TrailerRoom(String name){
		super(name);
	}
    
    @Override
    public ArrayList<Role> getAllRoles() {
    	return new ArrayList<Role>();
    }
}
