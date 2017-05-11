package csci345;

public class CastingRoom extends Room {

    public static int costUpgrade(int rankWanted, String currency){
        int cost = 0;
        if (currency.equals("$")) {//DOLLAR BILLS Y'ALL
            for (int i = 1; i < rankWanted; i++) {
                cost += i * 2;//NOTE NOT 100% on this math
            }
            cost = cost + ( 2 * rankWanted-1);
        } else if (currency.equals("cr") {
            cost = (rankWanted-1) * 5;
        }
        return cost;
    }
    
}
