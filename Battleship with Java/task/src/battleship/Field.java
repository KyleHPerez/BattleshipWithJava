package battleship;

public class Field {
    //CLASS FIELDS
    private static int playerNumber = 1;
    //INSTANCE FIELDS
    String playerName;
    Ship[] ships = new Ship[5];
    String[][] playerDisplay;
    String[][] opponentDisplay;
    boolean[][] hasShip = new boolean[11][11];
    private final String[][] shipID = new String[11][11];
    boolean[][] hasHit =  new boolean[11][11];
    boolean[][] hasMiss = new boolean[11][11];
    int shipsInitialized;
    int sinkCount;
    boolean hasWon = false;
    ///FIELD CONSTRUCTOR
    public Field() {
        this.playerName = playerNumber == 1 ? "Player 1" : "Player 2";
        Field.playerNumber++;
        this.playerDisplay = new String[11][11];
        this.opponentDisplay = new String[11][11];
        this.playerDisplay[0][0] = " ";
        this.opponentDisplay[0][0] = " ";
        for (int i = 1; i < playerDisplay[0].length; i++) { //Populate Column Headers
            this.playerDisplay[0][i] = "" + i;
            this.opponentDisplay[0][i] = "" + i;
        }
        for (int i = 1; i < playerDisplay.length; i++) { //Populate Row Headers
            this.playerDisplay[i][0] = "" + (char) (('A' - 1 ) + i);
            this.opponentDisplay[i][0] = "" + (char) (('A' - 1 ) + i);
        }
        populateFieldValues(); //Populates initial values of field (hits, misses, ships) (all false).
        resetDisplay(); //Populates rest of field with '~'.
    }
    //INSTANCE METHODS
    private void populateFieldValues() {
        this.shipsInitialized = 0;
        this.sinkCount = 0;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                this.hasShip[i][j] = false;
                this.shipID[j][i] = "";
                this.hasHit[i][j] = false;
                this.hasMiss[i][j] = false;
            }
        }
    }

    public void resetDisplay() {
        for (int i = 1; i < playerDisplay.length; i++) {
            for (int j = 1; j < playerDisplay[1].length; j++) {
                this.playerDisplay[i][j] = "~";
                this.opponentDisplay[i][j] = "~";
            }
        }
    }

    void printDisplay(String[][] display) {
        for (String[] row : display) {
            for (String cell: row) {
                System.out.print(cell + " ");
            }
            System.out.println(); //Newline for each row
        }
    }

    void printBothDisplays() {
        printDisplay(this.opponentDisplay);
        System.out.println("---------------------");
        printDisplay(this.playerDisplay);
        System.out.println();
    }

    public void placeShip(String[] parts, int shipTypeIndex) {
        this.ships[shipTypeIndex] = new Ship(parts, shipTypeIndex, this);
    }

    public String placeShot(String[] shotCoords, Field targetField) {
        int[] coords = Process.getRowAndColIndices(shotCoords);
        int x = coords[0];
        int y = coords[1];
        if (targetField.hasShip[x][y]) {
            targetField.hasHit[x][y] = true;
            targetField.playerDisplay[x][y] = "X";
            this.opponentDisplay[x][y] = "X";
            this.printBothDisplays();
            return targetField.getShipID(x, y);
        } else {
            this.hasMiss[x][y] = true;
            targetField.playerDisplay[x][y] = "M";
            this.opponentDisplay[x][y] = "M";
            System.out.println("you missed!");
            return "NA";
        }
    }

    //MUTATORS
    public String getShipID(int x, int y) {
        return shipID[x][y]; //For x, Row Header A = 1; for y, Col Header 1 = 1.
    }

    public void setShipID(int x, int y, String iD) {
        this.shipID[x][y] = iD;
    }
}
