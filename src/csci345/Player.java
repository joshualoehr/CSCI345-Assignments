package csci345;
import java.util.Random;

public class Player {

    private String name;
    private Room currRoom;
    private Role role;
    private int rank;
    private int dollars;
    private int credits;
    private int rehearsalChips;

    public Player(String name, Room currRoom){
        this.name = name;
        this.currRoom = currRoom;
        this.role = null;
        this.rank = 0;
        this.dollars = 0;
        this.credits = 0;
        this.rehearsalChips = 0;
    }

    public void move(Room toMoveTo) {
        this.currRoom = toMoveTo;
    }

    public void rehearse() {
        rehearsalChips++;
    }

    public void act() {
        SceneRoom mySceneRoom = (SceneRoom) currRoom;
        Random randNum = new Random();
        int diceRoll = randNum.nextInt(6) + 1;
        if ((diceRoll + this.rehearsalChips) >= mySceneRoom.getScene().getBudget()) {//success
            if (role instanceof StarringRole) {
                mySceneRoom.decrementShotCounter();
                this.credits +=2;
            } else {
                mySceneRoom.decrementShotCounter();
                this.credits++;
                this.dollars++;
            }
        } else {//Failed to act only extra gets paid
            if (role instanceof ExtraRole) {
                this.dollars++;
            }
        }
    }

    public void upgrade(int rankWanted, String currency) {
        int cost = 0;
        if (this.rank + 1 != 6) {
            if (currency.equals("dollars")) {//DOLLAR BILLS Y'ALL
                for (int i = 1; i < rankWanted; i++) {
                    cost += i * 2; //NOTE NOT 100% on this math
                }
                cost = cost + ( 2 * rankWanted - 1);
                this.dollars = this.dollars - cost;
            }
            else {
                cost = (rankWanted - 1) * 5;
                this.credits = this.credits - cost;
            }
        }

    }

    public void takeRole(Role roleToTake) {
        this.role = roleToTake;
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

    public void increaseDollars(int dollarsGain){
        this.dollars += dollarsGain;
    }

    public void increaseCredits(int creditsGain){
        this.credits += creditsGain;
    }

}
