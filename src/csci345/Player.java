package csci345;

public class Player {

	public static final int MOVED = 0;
	public static final int REHEARSED = 1;
	public static final int ACTED = 2;
	public static final int UPGRADED = 3;
	public static final int TAKEN_ROLE = 4;
	public static final int DONE_ANYTHING = 5;
	
    private String name;
    private Room currRoom;
    private Role role;
    private int rank;
    private int dollars;
    private int credits;
    private int rehearsalChips;
    
    private boolean[] completed;

    public Player(String name, Room currRoom) {
        this.name = name;
        this.currRoom = currRoom;
        this.role = null;
        this.rank = 1;
        this.dollars = 0;
        this.credits = 0;
        this.rehearsalChips = 0;
        
        this.completed = new boolean[]{false,false,false,false,false};
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%s ($%d, %dcr)", name, dollars, credits));
    	if (role != null)
    		sb.append(String.format(" working %s", role));
    	return sb.toString();
    }

    public void move(Room toMoveTo) {
        this.currRoom = toMoveTo;
        completed[MOVED] = true;
    }

    public void rehearse() {
        rehearsalChips++;
        completed[REHEARSED] = true;
    }

    public Payout act() {
    	Payout payout = role.act(getBudget(), rehearsalChips);
    	addPayout(payout);
    	completed[ACTED] = true;
    	return payout;
    }

    public int getBudget() {
        if (currRoom instanceof SceneRoom){
            return ((SceneRoom)currRoom).getScene().getBudget();
        } else {
            return -1;
        }
    }

    public void upgrade(int rankWanted, String currency) {
        int cost = CastingRoom.costUpgrade(rankWanted, currency);
        if (currency.equals("$")){
            dollars = dollars - cost;
        } else if (currency.equals("cr")){
            credits = credits - cost;
        }
        rank = rankWanted;
        completed[UPGRADED] = true;
    }

    public void takeRole(Role roleToTake) {
        this.role = roleToTake;
        if (role != null) {
    		role.setPlayer(this);
    		role.setOccupied(true);
    		completed[TAKEN_ROLE] = true;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Room getRoom() {
        return this.currRoom;
    }

    public Role getRole() {
        return this.role;
    }

    public int getRank() {
        return this.rank;
    }

    public int getDollars() {
        return this.dollars;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getRehearsalChips() {
        return this.rehearsalChips;
    }

    public void addPayout(Payout payout) {
    	dollars += payout.getDollars();
    	credits += payout.getCredits();
    }
    
    public boolean has(int... types) {
    	boolean has = false;
    	for (int type : types) {
    		if (completed[type]) has = true;
    	}
    	return has;
    }
    
    public void startTurn() {
    	this.completed = new boolean[]{false,false,false,false,false};
    }
}
