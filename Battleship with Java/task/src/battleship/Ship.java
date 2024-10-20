package battleship;

public class Ship {
    //CLASS FIELDS
    ShipType shipType;
    private int hitCounter;
    private boolean sunk;

    public Ship(String[] parts, int shipTypeIndex, Field field) {
        //INSTANCE FIELDS
        this.shipType = ShipType.values()[shipTypeIndex];
        this.hitCounter = 0;
        this.sunk = false;
        for (String part : parts) {
            int[] coords = Process.getRowAndColIndices(Process.pointCoords(part));
            int x = coords[0];
            int y = coords[1];
            field.hasShip[x][y] = true;
            field.playerDisplay[x][y] = "O";
            field.setShipID(x, y, shipType.toString());
        }
        field.shipsInitialized++;
    }

    public void incrementHitCounter(Field shipOwner) {
        hitCounter++;
        if (hitCounter == this.shipType.getLength()) {
            sunk = true;
            shipOwner.sinkCount++;
            if (shipOwner.sinkCount == 5) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else {
                System.out.println("You sank a ship! Specify a new target:");
            }
        } else {
            System.out.println("You hit a ship!\n");
        }
    }
}
