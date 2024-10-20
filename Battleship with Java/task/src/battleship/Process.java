package battleship;

public class Process {

    static String[] pointCoords(String inputCoords) {
        return inputCoords.split("", 2);
    }

    static int[] getRowAndColIndices(String[] coord) {
        int rowIndex = (coord[0].charAt(0)) - 64;
        int colIndex = Integer.parseInt(coord[1]);
        return new int[] {rowIndex, colIndex};
    }

    static String[][] inputToShipCoords(String inputCoords) {
        String[] shipCoords = inputCoords.split(" ", 2);
        String[] startComponents = shipCoords[0].split("", 2);
        String[] endComponents = shipCoords[1].split("", 2);
        return new String[][] {startComponents, endComponents};
    }

    static String[] shipCoordsToParts(String[][] userCoords, int shipTypeIndex) {
        char startRow = userCoords[0][0].charAt(0); //START COORDINATES
        int startCol = Integer.parseInt(userCoords[0][1]);
        char endRow = userCoords[1][0].charAt(0); //END COORDINATES
        int endCol = Integer.parseInt(userCoords[1][1]);
        int length = ShipType.values()[shipTypeIndex].getLength(); //SHIP LENGTH BY DEFINITION
        String[] parts = new String[length]; //RETURN VARIABLE
        if (startRow == endRow) { //HORIZONTAL PLACEMENT
            for (int i = 0; i < length; i++) {
                parts[i] = startRow + "" + (Math.min(startCol, endCol) + i);
            }
        } else { //VERTICAL PLACEMENT
            char bottom = (char) Math.min(startRow, endRow);
            for (int i = 0; i < parts.length; i++) {
                parts[i] = "" + (char) (bottom + i) + startCol;
            }
        }
        return parts;
    }
}
