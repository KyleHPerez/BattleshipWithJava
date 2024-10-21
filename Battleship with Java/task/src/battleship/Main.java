package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Field player1Field = new Field();
        Field player2Field = new Field();

        System.out.println("\nPlayer 1, place your ships on the game field.\n");
        player1Field.printDisplay(player1Field.playerDisplay);
        System.out.println();
        initializeFleet(scanner, player1Field);

        System.out.println("Press Enter and pass the game to the next player\n...\n");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field.\n");
        player2Field.printDisplay(player2Field.playerDisplay);
        System.out.println();
        initializeFleet(scanner, player2Field);

        do {
            passTheGame(scanner, player1Field);
            makeValidShot(scanner, player1Field, player2Field); //Player 1 places shots, may sink Player 2 ships.
            passTheGame(scanner, player2Field);
            makeValidShot(scanner, player2Field, player1Field); //Player 2 places shots, may sink Player 1 ships.
        } while (!player1Field.hasWon && !player2Field.hasWon);
        System.out.println("You sank the last ship.\nYou won. Congratulations!");
    }

    private static void passTheGame(Scanner scanner, Field nextPlayer) {
        System.out.println("Press Enter and pass the game to the other player.\n...\n");
        scanner.nextLine();
        nextPlayer.printBothDisplays();
        System.out.printf("%s, it's your turn:\n\n", nextPlayer.playerName);
    }

    private static void makeValidShot(Scanner scanner, Field playerField, Field opponentField) {
        boolean shotValid = false; //SHOOTING FUNCTIONALITY
        do {
            String[] shotCoords = Process.pointCoords(scanner.nextLine());
            if (Checks.checkShotCoords(shotCoords)) {
                switch (playerField.placeShot(shotCoords, opponentField)) {
                    case "Aircraft Carrier" : {
                        opponentField.ships[0].incrementHitCounter(opponentField); break;}
                    case "Battleship" : {
                        opponentField.ships[1].incrementHitCounter(opponentField); break;}
                    case "Submarine" : {
                        opponentField.ships[2].incrementHitCounter(opponentField); break;}
                    case "Cruiser" : {
                        opponentField.ships[3].incrementHitCounter(opponentField); break;}
                    case "Destroyer" : {
                        opponentField.ships[4].incrementHitCounter(opponentField); break;}
                    default : {} //If shot was a miss
                }
                shotValid = true;
            }
        } while (!shotValid);
    }

    private static void initializeFleet(Scanner scanner, Field playerField) {
        for (int i = 0; i < 5; i++) { //CREATE ALL PLAYER SHIPS
            boolean proceed = false;
            String shipType = ShipType.values()[i].toString();
            int shipLength = ShipType.values()[i].getLength();
            do {
                System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipType, shipLength);
                if (i == 0) {
                    System.out.println("Enter the word \"help\" for help with input.");
                }
                String rawInput = scanner.nextLine();
                if (rawInput.contains("help")) {
                    System.out.println("""
                                            \n
                                            Input ship start and end points in the following format:
                                            One capital letter from A to J followed by a number from 1 to 10,
                                            separated from another letter-number pair by one space " ".
                                            Ships must be placed horizontally or vertically, but not diagonally.
                                            For example: "A1 A5" or "J10 F10" (without quotation marks).
                                            \n
                            """);
                } else {
                    String[][] userCoords = new String[2][2];
                    if (Checks.checkBasic2CoordInput(rawInput)) {
                        userCoords = Process.inputToShipCoords(rawInput);
                    }
                    if (Checks.checkUserCoords(userCoords, i)) {
                        String[] parts = Process.shipCoordsToParts(userCoords, i);
                        if (Checks.checkRoomForShip(parts, playerField)) {
                            playerField.placeShip(parts, i);
                            playerField.printDisplay(playerField.playerDisplay);
                            System.out.println();
                            proceed = true;
                        }
                    }
                }
            } while (!proceed); //Won't proceed to next ship unless this ship is placed without errors
        }
    }
}

