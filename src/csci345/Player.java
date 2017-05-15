package csci345;

public class Player {

    private String name;
    private Room currRoom;
    private Role role;
    private int rank;
    private int dollars;
    private int credits;
    private int rehearsalChips;

    public Player(String name, Room currRoom) {
        this.name = name;
        this.currRoom = currRoom;
        this.role = null;
        this.rank = 0;
        this.dollars = 0;
        this.credits = 0;
        this.rehearsalChips = 0;
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
    }

    public void rehearse() {
        rehearsalChips++;
    }

    public Payout act() {
    	Payout payout = role.act(getBudget(), rehearsalChips);
    	if (payout.wasSuccessful()) {
    		((SceneRoom) currRoom).decrementShotCounter();
    	}
    	addPayout(payout);
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
    }

    public void takeRole(Role roleToTake) {
        this.role = roleToTake;
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
}
