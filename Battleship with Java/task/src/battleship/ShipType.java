package battleship;

public enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String type;
    private final int length;

    ShipType(String type, int length) {
        this.type = type;
        this.length = length;
    }

     public String toString() {
        return this.type;
     }

     public int getLength() {
        return this.length;
     }
}
