
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args)
    {
        // TODO
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.

        Scanner in = new Scanner(System.in);

        //Initialize the two players and the array to hold them
        String playerOne ="";
        String playerTwo = "";
        String [] playerNames = {playerOne,playerTwo};

        //Initialize the gameHistory saving area
        ArrayList<char [][]> gameHistorySaveArea = new ArrayList<>();


        //Main menu for the game
        char mainMenuChoice = emptySpaceSymbol;
        while( mainMenuChoice != 'Q')
        {
            System.out.println();
            System.out.println("Welcome to the game of Tic Tac Toe, choose one of the following options from below: ");
            System.out.println();
            System.out.println("1. Single player");
            System.out.println("2. Two player");
            System.out.println("D. Display last match");
            System.out.println("Q. Quit");
            System.out.print("What do you want to do: ");


            mainMenuChoice = in.next().charAt(0);

            //Two player game mode
            if (mainMenuChoice == '2' )
            {
                System.out.println();
                System.out.println("Enter player 1 name: ");
                playerOne = in.next();
                System.out.println("Enter player 2 name: ");
                playerTwo = in.next();
                playerNames[0] = playerOne;
                playerNames[1] = playerTwo;
                gameHistorySaveArea = runTwoPlayerGame(playerNames);

            }
            //One player game mode
            else if (mainMenuChoice == '1')
            {
                System.out.println(" ");
                System.out.println("Enter player 1 name: ");
                playerOne = in.next();
                playerNames[0] = playerOne;
                playerNames[1] = "Computer";
                gameHistorySaveArea = runOnePlayerGame(playerNames);

            }
            //To display the previous game
            else if (mainMenuChoice == 'D')
            {
                if(gameHistorySaveArea.isEmpty()){
                    System.out.println("No match found");
                } else{
                    runGameHistory(playerNames, gameHistorySaveArea);
                }
            }
            //To process any other input than the ones provided
            else if(mainMenuChoice != 'Q')
            {
                System.out.println("Invalid input please try again");
            }
        }
        System.out.println("Thank you for playing, Hope you had fun!");
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.

    //To take in a char [][] (state) and draw a board displaying the contents of the state
    private static String displayGameFromState(char[][] state)
    {
        // TODO
        // Hint: Make use of the newline character \n to get everything into one String
        // It would be best practice to do this with a loop, but since we hardcode the game to only use 3x3 boards
        // it's fine to do this without one.

        String boardDisplay = "";

        for (int i = 0; i < getInitialGameState().length; i++)
        {
            for (int j = 0; j < getInitialGameState().length; j++)
            {

                if (j == getInitialGameState().length -1)
                {
                    boardDisplay = boardDisplay.concat(String.valueOf(state[i][j])) ;
                }
                else
                {

                    boardDisplay = boardDisplay.concat(state[i][j] + " | " );

                }
            }

            if (i == getInitialGameState().length -1)
            {
                boardDisplay =  boardDisplay.concat((""));
            }
            else
            {
                boardDisplay = boardDisplay.concat("\n" + "----------" + "\n");
            }

        }
        boardDisplay = boardDisplay.concat("\n");

        return boardDisplay;
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState()
    {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                            {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                            {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory

    //Two player game mode
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        // TODO

        //Initialize a new state of the same length as the initial game state
        char [][] state = new char[getInitialGameState().length] [getInitialGameState()[0].length];

        //Make an array list to store the game data
        ArrayList<char[][]> gameHistory = new ArrayList<>();

        //Adding initial game state
        gameHistory.add(getInitialGameState());

        //Copy the contents of the initial game state to the current state before the match
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[i] = Arrays.copyOf(getInitialGameState()[i], getInitialGameState().length);
            }
        }

        //Tossing a coin to see who goes first
        System.out.println("Tossing a coin to decide who goes first!!!");
        Random chooseWhoIsFirst = new Random();
        int selectFirstChoice = chooseWhoIsFirst.nextInt(2);

        //Re-initializing the first selected choice to match the index storing the player names
        boolean gameOver = false;
        if (selectFirstChoice == 1){
            selectFirstChoice = 0;
        }else {
            selectFirstChoice = 1;
        }

        //Prints out who goes first
        System.out.println(playerNames[selectFirstChoice] + " gets to go first");

        //Display the starting game state
        System.out.println(displayGameFromState(state));

        //Game between the two players until a win or a draw is had
        while(!gameOver) {

            //if the first player is selected
            if (selectFirstChoice == 0) {
                state = runPlayerMove(playerNames[selectFirstChoice], playerOneSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state)){
                    System.out.println("It's a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 1;
            }
            //if the second player is selected
            else {
                state = runPlayerMove(playerNames[selectFirstChoice], playerTwoSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state)){
                    System.out.println("It's a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 0;
            }
        }

        //return the game history saved so far
        return gameHistory;
    }

    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory

    //One player game mode
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {
        // TODO

        //Initialize a new state of the same length as the initial game state
        char [][] state = new char[getInitialGameState().length] [getInitialGameState()[0].length];

        //Initializing a game history
        ArrayList<char[][]> gameHistory = new ArrayList<>();

        //Adding initial game state
        gameHistory.add(getInitialGameState());

        //Copy the contents of the initial game state to the current state before the match
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[i] = Arrays.copyOf(getInitialGameState()[i], getInitialGameState().length);
            }
        }



        //Tossing a coin to see who goes first
        System.out.println("Tossing a coin to decide who goes first!!!");
        Random chooseWhoIsFirst = new Random();
        int selectFirstChoice = chooseWhoIsFirst.nextInt(2);


        //Re-initializing the first selected choice to match the index storing the player names
        boolean gameOver = false;
        if (selectFirstChoice == 1){
            selectFirstChoice = 0;
        }else {
            selectFirstChoice = 1;
        }

        //Printing out the player going first
        System.out.println(playerNames[selectFirstChoice] + " gets to go first");

        //displaying the starting game state
        System.out.println(displayGameFromState(state));

        //Game between the two players until a win or a draw is had
        while(!gameOver) {

            //if the first player is selected
            if (selectFirstChoice == 0) {
                state = runPlayerMove(playerNames[selectFirstChoice], playerOneSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state)){
                    System.out.println("It's a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 1;
            }
            //if the second player is selected
            else {
                System.out.println(playerNames[selectFirstChoice] + "'s turn:");
                state = getCPUMove(state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state)){
                    System.out.println("It's a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 0;
            }


        }

        //Returning the game history saved so far
        return gameHistory;

    }

    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        // TODO

        //new state for the current state after being changed
        char [][] newCurrentState;
        int [] inBounds = getInBoundsPlayerMove(playerName);

        //while the given move is within bounds and is a valid move
        while(!(checkValidMove(inBounds, currentState))){
            System.out.println("That space is already taken. Try again.");
            inBounds = getInBoundsPlayerMove(playerName);
        }

        //set the new game state to the changed state
        newCurrentState = makeMove(inBounds, playerSymbol, currentState);

        return newCurrentState;
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {

        Scanner sc = new Scanner(System.in);
        // TODO

        //Initialize variables for player row and column
        int playerRow, playerCol;

        //Initializing a variable to store a valid move
        int [] validMove = new int [2];

        //Takes input for the row and colum of the current players turn
        System.out.println(playerName + "'s turn:");
        System.out.println(playerName + " enter a row: ");
        playerRow = sc.nextInt();
        System.out.println(playerName + " enter a col: ");
        playerCol = sc.nextInt();

        //Keeps prompting the user for a row and a column until within bounds of the board
        while((playerRow < 0  || playerRow > 2 || playerCol < 0 || playerCol > 2)) {
            System.out.println("Row or colum out of bounds. Try again.");
            System.out.println(playerName + " enter a row: ");
            playerRow = sc.nextInt();
            System.out.println(playerName + " enter a col: ");
            playerCol = sc.nextInt();
        }

        //stores the valid row and column inside the validMove array
        validMove[0] = playerRow;
        validMove[1] = playerCol;

        return validMove;
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        // TODO

        //checks if the provided coordinate within the current state is valid
        return (state[move[0]][move[1]] == emptySpaceSymbol);
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // TODO:
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array

        //Initializes a new state variable with the size of the current state
        char [][] newState = new char[currentState.length][currentState[0].length];

        //Copies the contents of the current state in the new state
        for (int i = 0; i < currentState.length; i++) {
            newState[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }

        //Changes the element within the new state in the given coordinate to the given symbol
        newState[move[0]][move[1]] = symbol;

        return newState;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        // TODO
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        //Condition is based on the horCheck, verCheck and diagCheck conditions
        boolean allCheck;

        //If there is a win horizontally
        boolean horCheck = false;
        //If there is a win vertically
        boolean verCheck = false;
        //If there is a win diagonally
        boolean diagCheck = false;

        // Checks Horizontal Symbols for a win
        for (int i = 0; i < state.length; i++) {
            char what = state[i][0];
            int count = 0;
            for (int j = 0; j < state[0].length; j++) {
                if(state[i][j] == what && state[i][j] != emptySpaceSymbol){
                    count++;
                }
            }

            //If 3 of the same symbols horizontally
            if(count == 3){
                horCheck = true;
                break;
            }
        }
        // Checks Vertical symbols for a win
        for (int i = 0; i < state.length; i++) {
            char what = state[0][i];
            int count = 0;
            for (int j = 0; j < state.length; j++) {
                if(state[j][i] == what && state[j][i] != emptySpaceSymbol){
                    count++;
                }
            }
            //If 3 of the same symbols vertically
            if(count == 3){
                verCheck = true;
                break;
            }
        }

        // Checks Diagonals symbols for a win
        for (int i = 0; i < state.length; i++) {
            char what = state[i][i];
            char what2 = state[i][(state[0].length - 1) - i];
            int count = 0;
            for (int j = 0; j < state[0].length; j++) {
                if(state[j][j] == what && state[j][j] != emptySpaceSymbol){
                    count++;
                }
            }
            //If 3 of the same symbols diagonally
            if(count == 3){
                diagCheck = true;
                break;
            } else {
                count = 0;
            }
            //Initialized a count for the changing sections of the board
            int count2 = 0;
            for (int j = state[0].length; j > 0; j--) {
                if(state[count2][j - 1] == what2 && state[count2][j - 1] != emptySpaceSymbol){
                    count++;
                    count2++;
                }
            }
            //If 3 of the same symbols diagonally
            if(count == 3){
                diagCheck = true;
                break;
            }

        }

        //Sets allCheck to true if horCheck, verCheck or diagCheck conditions come out to be true
        allCheck = (horCheck || verCheck || diagCheck);

        return allCheck;
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.

    private static boolean checkDraw(char[][] state) {
        // TODO

        //condition for an open space
        boolean openSpace = true;
        // Loops through the current board to check if all the spaces are taken
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if(state[i][j] == emptySpaceSymbol){
                    openSpace = false;
                    break;
                }
            }
        }
        return openSpace;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {
        // TODO

        // Hint: you can call makeMove() and not end up returning the result, in order to "test" a move
        // and see what would happen. This is one reason why makeMove() does not modify the state argument
        //call the move here


        // Determine all available spaces
        ArrayList<int[]> spaces = getValidMoves(gameState);

        //Initialize constant spaces on the board to compare to
        int[] centerSpace = {1, 1};
        int[] corner1 = {0, 0};
        int[] corner2 = {0, 2};
        int[] corner3 = {2, 0};
        int[] corner4 = {2, 2};

        //To check if the CPU got any moves
        boolean gotMove = false;

        // If there is a winning move available, make that move
        for (int [] space: spaces)  {
            if (checkWin(makeMove(space, playerTwoSymbol, gameState))) {
                gameState = makeMove(space, playerTwoSymbol, gameState);
                gotMove = true;
                break;
            }
        }
        ///If the cpu still hasn't got a move
        if (!gotMove) {
            // If not, check if opponent has a winning move, and if so, make a move there
            for (int [] space: spaces) {
                if ((checkWin(makeMove(space, playerOneSymbol, gameState)))) {
                    gameState = makeMove(space, playerTwoSymbol, gameState);
                    gotMove = true;
                    break;
                }
            }
        }
        if (!gotMove) {
            // If not, move on center space if possible
            for (int [] space: spaces) {
                if (Arrays.equals(space, centerSpace)) {
                    gameState = makeMove(space, playerTwoSymbol, gameState);
                    gotMove = true;
                    break;
                }
            }
        }
        if(!gotMove) {
            // If not, move on corner spaces if possible
            for (int [] space: spaces) {
                if (Arrays.equals(space, corner1) || Arrays.equals(space, corner2) || Arrays.equals(space, corner3) || Arrays.equals(space, corner4)) {
                    gameState = makeMove(space, playerTwoSymbol, gameState);
                    gotMove = true;
                    break;
                }
            }
        }
        // Otherwise, move in any available spot
        if(!gotMove) {
            gameState = makeMove(spaces.get(0), playerTwoSymbol, gameState);
            }

        return gameState;
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        // TODO

        //Initializing a new arrayList to store available moves
        ArrayList<int []> availableMoves = new ArrayList<>();

        // Loops through the array horizontally to check which spaces are still available
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[0].length; j++) {
                int[] space = {i, j};
                if (checkValidMove(space, gameState)) {
                    availableMoves.add(space);
                }
            }
        }

        return availableMoves;
    }


    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {
        // TODO
        // We have the names of the players in the format [playerOneName, playerTwoName]
        // Player one always gets 'X' while player two always gets 'O'
        // However, we do not know yet which player went first, but we'll need to know...
        // Hint for the above: which symbol appears after one turn is taken?

        //gets the first state of the board after displaying the initial
        char[][] move = gameHistory.get(1);

        //Stores the name of the current player on the board
        String currentPlayer = "";

        System.out.println(playerNames[0] + emptySpaceSymbol + "(" + playerOneSymbol + ")" +
                emptySpaceSymbol + "vs " + playerNames[1] + emptySpaceSymbol + "(" + playerTwoSymbol + ")");

        //get the first player
        for (int i = 0; i < move.length; i++) {
            for (int j = 0; j < move[0].length; j++) {
                if(move[i][j] == playerOneSymbol){
                    currentPlayer = playerNames[0];
                }
                else if(move[i][j] == playerTwoSymbol){
                    currentPlayer = playerNames[1];
                }
            }
        }


        for (int i = 0; i < gameHistory.size(); i++) {
            if(i == 0){
                System.out.println(displayGameFromState(gameHistory.get(i)));
            }
            else {
                System.out.println(currentPlayer + ":");
                System.out.println(displayGameFromState(gameHistory.get(i)));
                if (currentPlayer.equals(playerNames[0])) {
                    currentPlayer = playerNames[1];
                } else {
                    currentPlayer = playerNames[0];
                }
            }
        }
        if(checkWin(gameHistory.get(gameHistory.size() - 1))){
            if(currentPlayer.equals(playerNames[0])){
                System.out.println(playerNames[1] + " Wins!");
            } else {
                System.out.println(playerNames[0] + " Wins!");
            }
        }
        else if(checkDraw(gameHistory.get(gameHistory.size() - 1))){
            System.out.println("Its a draw!");
        }

        // Hint: iterate over gameHistory using a loop

    }
}
