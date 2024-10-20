package battleship;

import java.util.regex.Pattern;

public class Checks {

    public static boolean checkBasic2CoordInput(String input) {
        if (5 == input.length()) {
            if (input.charAt(2) != ' ') {
                return false;
            }
        } else if (6 == input.length() || 7 == input.length()) {
            if (input.charAt(2) != ' ' && input.charAt(3) != ' ') {
                return false;
            }
        }
        int spaceCounter = 0;
        for (char c : input.toCharArray()) {
            spaceCounter += c == ' ' ? 1 : 0;
        }
        if (spaceCounter != 1) {
            return false;
        }
        return true;
    }

    public static boolean checkUserCoords(String[][] userCoords, int shipTypeIndex) {
        for (String[] userCoord : userCoords) {
            if (Pattern.compile("[^A-J]").matcher(userCoord[0]).matches()) {
                System.out.println("\nError! Non A-J symbol provided for row!");
                return false;
            }
        }
        for (String[] userCoord : userCoords) {
            for (int j = 0; j < userCoord[1].length(); j++) {
                if (!Character.isDigit((userCoord[1].charAt(j)))) {
                    System.out.println("\nError! Non-digit provided for column!");
                    return false;
                }
            }
        }
        if (!userCoords[0][0].equals(userCoords[1][0])) {
            if (!(userCoords[0][1].equals(userCoords[1][1]))) {
                System.out.println("\nError! Ship must be placed horizontally or vertically!");
                return false;
            }
        }
        if (10 < Integer.parseInt(userCoords[0][1]) ||
                10 < Integer.parseInt(userCoords[1][1]) ||
                1 > Integer.parseInt(userCoords[0][1]) ||
                1 > Integer.parseInt(userCoords[1][1])) {
            System.out.println("\nError! Column is not 1-10");
            return false;
        }
        int userInputLength = userCoords[0][0].equals(userCoords[1][0]) ?
                Math.max(Integer.parseInt(userCoords[0][1]), Integer.parseInt(userCoords[1][1])) -
                        Math.min(Integer.parseInt(userCoords[0][1]), Integer.parseInt(userCoords[1][1])) + 1 :
                Math.max(((userCoords[0][0]).charAt(0)), (userCoords[1][0]).charAt(0)) -
                        Math.min(userCoords[0][0].charAt(0), userCoords[1][0].charAt(0)) + 1;
        if (userInputLength != ShipType.values()[shipTypeIndex].getLength()) {
            System.out.printf("\nError! Wrong length of the %s! Try again:\n", ShipType.values()[shipTypeIndex].toString());
            return false;
        }
        return true;
    }

    public static boolean checkRoomForShip(String[] parts, Field field) {
        for (String part : parts) {
            int[] coord = Process.getRowAndColIndices(Process.pointCoords(part));
            int x = coord[0], y = coord[1];
            if (field.hasShip[x][y]) {
                System.out.println("\nError! The coordinates selected already contain a ship!");
                return false;
            } else { //Check adjacent cells
                for (int i = x - 1; i <= x + 1; i++) {
                    if (i < 1 || i > 10) {
                        continue;
                    }
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (j < 1 || j > 10) {
                            continue;
                        }
                        if (i == x && j == y) {
                            continue;
                        }
                        if (field.hasShip[i][j]) {
                            System.out.println("\nError! You placed it too close to another one.\nTry again:");
                            return false;
                        }
                    }
                }
            }
        } return true;
    }

    public static boolean checkShotCoords(String[] shotCoords) {
        char row = shotCoords[0].charAt(0);
        int col = Integer.parseInt(shotCoords[1]);
        if (Pattern.compile("[^A-J]").matcher("" + row).matches()) {
            System.out.println("\nError! Non-A-J symbol provided for row!");
            return false;
        }
        if (10 < col || 1 > col) {
            System.out.println("\nError! Column is not 1-10");
            return false;
        }
        return true;
    }
}