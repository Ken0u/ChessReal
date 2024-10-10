package com.chess;

import com.chess.piece.Piece;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Player extends Application{
    // Initializes the ImageView arrays, this array stores each pieces image inside each square
    private ImageView[][] squares;
    // you can set the size of the chessboard here
    private final int size = 8;
    public static String path = "C:\\Users\\enkra\\Documents\\ICS3U1\\ai\\ChessReal\\src\\main\\resources\\com\\chess\\Images\\";

    // Initializes chess board, this creates an array that stores every piece on there
    public static Board chessBoard = new Board();

    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        String input;
        boolean isValidInput;
        boolean isOnePlayer = true;

        isValidInput = false;
        do
        {
            System.out.println("[1] One player\n[2] Two player\n");
            System.out.print("Enter the game mode: ");
            input = sc.nextLine();

            switch (input)
            {
                case "1":
                    isValidInput = true;
                    break;
                case "2":
                    isOnePlayer = false;
                    isValidInput = true;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        } while (!isValidInput);

        if (isOnePlayer)
        {
            // run the one player turn method
            turn();
        }
        else
        {
            // Run the two player turn method.
            turn2P();
        }
    }

    // this is a method that modify the GUI, it places each image into each box according to the chessBoard array
    public void start(Stage primaryStage) {
        // Create the chess board
        squares = new ImageView[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = new ImageView();
            }
        }
        // Place the pieces on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getPieceAt(j, i) != null) {
                    placePiece(chessBoard.getPieceAt(j, i), i, j);
                    if((i + j) % 2 == 0){
                        Image image = new Image(path + "White_Square.png");
                        squares[i][j].setStyle("-fx-background-color: #818180;");
                    }else{
                        Image image = new Image(path + "Grey_Square.png");
                        squares[i][j].setStyle("-fx-background-color: #fcfffd;");
                    }
                }else if((i + j) % 2 == 0){
                    Image image = new Image(path + "White_Square.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitHeight(100);
                    squares[i][j].setImage(imageView.getImage());
                }else{
                    Image image = new Image(path + "Grey_Square.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    squares[i][j].setImage(imageView.getImage());
                }
            }
        }

        // Add the chess board to a GridPane layout container
        GridPane root = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                root.add(squares[i][j], i, j);
            }
        }

        // create the vertical box for the piece search text field
        VBox vbox = new VBox();
        TextField input = new TextField();
        vbox.getChildren().add(new Label("Search Coordinates:"));
        vbox.getChildren().add(input);
        Label pieceReturn = new Label("");
        vbox.getChildren().add(pieceReturn);
        vbox.setSpacing(10);
        root.add(vbox, 9, 0, 4, 6);

        // Create the scene and display the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Game");
        primaryStage.show();

        // this will run when the something is entered into the box and clicked the submit button
        input.setOnAction(event -> {
            String text = input.getText();
            String[] piece;
            if (text.length() == 3 && Character.isDigit(text.charAt(0)) && text.charAt(1) == ' ' && Character.isDigit(text.charAt(2))) {
                // will filter the input
                piece = text.split(" ");
                Piece chessPiece = chessBoard.getPieceAt(Integer.parseInt(piece[1]), Integer.parseInt(piece[0]));
                // prints out the result on the GUI
                if (chessPiece != null) {
                    String output = String.valueOf(chessPiece.getClass());
                    if (chessPiece.isWhite())
                        pieceReturn.setText("It is a White " + output.substring(22));
                    else
                        pieceReturn.setText("It is a Black " + output.substring(22));
                } else
                    pieceReturn.setText("There is no Piece on that box");
            } else {
                // return error message
                pieceReturn.setText("Enter a valid input");
            }

        });
        // start a thread to allow GUI runs on the background
        Thread taskThread = new Thread(new Runnable() {

            // this code override the run method, so this will keep running
            @Override
            public void run() {

                // this code will keep looping and refresh the GUI every 200ms
                while(true){

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // this code will read the chessBoard array, and refresh the image view array updating the piece images
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (chessBoard.getPieceAt(j, i) != null) {
                                placePiece(chessBoard.getPieceAt(j, i), i, j);
                            }else if((i + j) % 2 == 0){
                                Image image = new Image(path + "White_Square.png");
                                ImageView imageView = new ImageView(image);
                                imageView.setFitHeight(100);
                                imageView.setFitHeight(100);
                                squares[i][j].setImage(imageView.getImage());
                            }else{
                                Image image = new Image(path + "Grey_Square.png");
                                ImageView imageView = new ImageView(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                squares[i][j].setImage(imageView.getImage());
                            }
                        }
                    }
                    // this will add the images into the scene
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            try  {
                                root.add(squares[i][j], i, j);
                            }catch(Exception e){

                            }
                        }
                    }
                }
            }
        });

        // start the thread
        taskThread.start();

    }


    /*==============================================================================
    void placePiece(Piece piece, int x, int y)
    --------------------------------------------------------------------------------
    Piece piece - the piece to be placed
    int x - the x coordinate of the piece
    int y - the y coordinate of the piece
    --------------------------------------------------------------------------------
    Graphically places a piece.
    ==============================================================================*/

    public void placePiece(Piece piece, int x, int y) {
        // setting the array with the image of the piece
        squares[x][y].setImage(piece.getImage());
    }

    /*==============================================================================
    void turn()
    --------------------------------------------------------------------------------
    Controls one player turns.
    ==============================================================================*/
    public static void turn(){
        // initialize the variables
        Piece chessPiece;
        String[] piece;
        String[] destination;
        String response;
        MultiThread boardGUI = new MultiThread();
        Piece white_king = chessBoard.getPieceAt(7, 4);
        Piece black_king = chessBoard.getPieceAt(0, 4);
        Scanner sc = new Scanner(System.in);
        boolean isWhiteTurn = true;

        // print out the layout of the board
        System.out.println("< Board Layout >");
        System.out.println("|------|------|------|------|------|------|------|------|\n" +
                "| 0, 0 | 1, 0 | 2, 0 | 3, 0 | 4, 0 | 5, 0 | 6, 0 | 7, 0 |\n" +
                "| 0, 1 | 1, 1 | 2, 1 | 3, 1 | 4, 1 | 5, 1 | 6, 1 | 7, 1 |\n" +
                "| 0, 2 | 1, 2 | 2, 2 | 3, 2 | 4, 2 | 5, 2 | 6, 2 | 7, 2 |\n" +
                "| 0, 3 | 1, 3 | 2, 3 | 3, 3 | 4, 3 | 5, 3 | 6, 3 | 7, 3 |\n" +
                "| 0, 4 | 1, 4 | 2, 4 | 3, 4 | 4, 4 | 5, 4 | 6, 4 | 7, 4 |\n" +
                "| 0, 5 | 1, 5 | 2, 5 | 3, 5 | 4, 5 | 5, 5 | 6, 5 | 7, 5 |\n" +
                "| 0, 6 | 1, 6 | 2. 6 | 3, 6 | 4, 6 | 5, 6 | 6, 6 | 7, 6 |\n" +
                "| 0, 7 | 1, 7 | 2, 7 | 3, 7 | 4, 7 | 5, 7 | 6, 7 | 7, 7 |\n" );

        // wrap with try and catch for IO exception
        try {
            // create the log.txt file
            File myObj = new File("Log.txt");
            myObj.createNewFile();

            // this will delete the previous message
            BufferedWriter writer = new BufferedWriter(new FileWriter("Log.txt", false));
            writer.close();
            boardGUI.start();

            // run until one king get captured
            while ((white_king.getX() != -1) && (black_king.getX() != -1)) {
                if (isWhiteTurn) {
                    // for receiving good input
                    while (true) {
                        BufferedWriter writter = new BufferedWriter(new FileWriter("Log.txt", true));
                        // see if the king is in checked, will show an alert box if it is
                        if (chessBoard.isChecked(white_king, chessBoard)) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Check");
                                alert.setHeaderText("King is in check!");
                                alert.setContentText("They must get out of check, or they lose.");
                                alert.showAndWait();
                            });

                        }
                        // ask for the coordinates of the piece the player want to move
                        while (true) {
                            System.out.println("< White turn >");
                            System.out.println("Which piece do you want to move? Use Coordinates. Eg. 0 0");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                piece = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }
                        // set the chessPiece
                        chessPiece = chessBoard.getPieceAt(Integer.parseInt(piece[1]), Integer.parseInt(piece[0]));

                        // print out error message if there are no pieces on that box
                        if (chessPiece == null) {
                            System.out.println("There is no piece on the coordinates");
                            continue;
                        }

                        // print out error message if the piece is not yours
                        if (chessPiece.isWhite() != isWhiteTurn) {
                            System.out.println("This is not your piece");
                            continue;
                        }

                        // receive good input
                        while (true) {
                            System.out.println("Where do you want to move? Use Coordinates. Eg 0 2");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                destination = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }

                        // move the pieces, will go back to the start of the loop if it is an invalid move
                        if (chessBoard.movePiece(chessPiece, chessBoard, Integer.parseInt(destination[1]), Integer.parseInt(destination[0])) == true) {

                            // write the message into the log
                            writter.write("< White turn >\n");
                            writter.write(chessPiece.getClass() + " is moved from [" + piece[0] + ", " + piece[1] + "] to [" + destination[0] + ", " + destination[1] + "]\n");
                            writter.close();
                        } else {
                            System.out.println("Invalid Move");
                            continue;
                        }
                        writter.close();
                        break;
                    }
                    isWhiteTurn = false;
                } else {
                    // same thing but this is for black turn
                    while (true) {
                        AI ai = new AI();
                        ai.makeMove(chessBoard, isWhiteTurn);
                        break;
                    }
                    isWhiteTurn = true;
                }
            }
            // check if the king exist
            if (white_king.getX() != -1){
                // will print out white wins if the king doesn't exist on the board
                System.out.println("White wins");
                // alert box for game ends
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Game Ends");
                    alert.setTitle("Game Ends");
                    alert.setContentText("White wins");
                    alert.showAndWait();
                });
            }else if(black_king.getX() != -1){
                // similar to the white wins
                System.out.println("Black wins");
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Game Ends");
                    alert.setTitle("Game Ends");
                    alert.setContentText("Black wins");
                    alert.showAndWait();
                });
            }
        }catch(IOException e){
            System.out.println("Cannot Open file");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*==============================================================================
    void turn2P()
    --------------------------------------------------------------------------------
    Controls two player turns.
    ==============================================================================*/
    public static void turn2P(){
        // intialize the varaibles
        Piece chessPiece;
        String[] piece;
        String[] destination;
        String response;
        MultiThread boardGUI = new MultiThread();
        Piece white_king = chessBoard.getPieceAt(7, 4);
        Piece black_king = chessBoard.getPieceAt(0, 4);
        Scanner sc = new Scanner(System.in);
        boolean isWhiteTurn = true;

        // print out the layout of the board
        System.out.println("< Board Layout >");
        System.out.println("|------|------|------|------|------|------|------|------|\n" +
                "| 0, 0 | 1, 0 | 2, 0 | 3, 0 | 4, 0 | 5, 0 | 6, 0 | 7, 0 |\n" +
                "| 0, 1 | 1, 1 | 2, 1 | 3, 1 | 4, 1 | 5, 1 | 6, 1 | 7, 1 |\n" +
                "| 0, 2 | 1, 2 | 2, 2 | 3, 2 | 4, 2 | 5, 2 | 6, 2 | 7, 2 |\n" +
                "| 0, 3 | 1, 3 | 2, 3 | 3, 3 | 4, 3 | 5, 3 | 6, 3 | 7, 3 |\n" +
                "| 0, 4 | 1, 4 | 2, 4 | 3, 4 | 4, 4 | 5, 4 | 6, 4 | 7, 4 |\n" +
                "| 0, 5 | 1, 5 | 2, 5 | 3, 5 | 4, 5 | 5, 5 | 6, 5 | 7, 5 |\n" +
                "| 0, 6 | 1, 6 | 2. 6 | 3, 6 | 4, 6 | 5, 6 | 6, 6 | 7, 6 |\n" +
                "| 0, 7 | 1, 7 | 2, 7 | 3, 7 | 4, 7 | 5, 7 | 6, 7 | 7, 7 |\n" );

        // wrap with try and catch for IO exception
        try {
            // create the log.txt file
            File myObj = new File("Log.txt");
            myObj.createNewFile();

            // this will delete the previous message
            BufferedWriter writer = new BufferedWriter(new FileWriter("Log.txt", false));
            writer.close();
            boardGUI.start();

            // run until one king get captured
            while ((white_king.getX() != -1) && (black_king.getX() != -1)) {
                if (isWhiteTurn) {
                    // for receiving good input
                    while (true) {
                        BufferedWriter writter = new BufferedWriter(new FileWriter("Log.txt", true));
                        // see if the king is in checked, will show an alert box if it is
                        if (chessBoard.isChecked(white_king, chessBoard)) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Check");
                                alert.setHeaderText("King is in check!");
                                alert.setContentText("It must get out of check, or they lose the game.");
                                alert.showAndWait();
                            });

                        }
                        // ask for the coordinates of the piece the player want to move
                        while (true) {
                            System.out.println("< White turn >");
                            System.out.println("Which piece do you want to move? Use Coordinates. Eg. 0 0");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                piece = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }
                        // set the chessPiece
                        chessPiece = chessBoard.getPieceAt(Integer.parseInt(piece[1]), Integer.parseInt(piece[0]));

                        // print out error message if there are no pieces on that box
                        if (chessPiece == null) {
                            System.out.println("There is no piece on the coordinates");
                            continue;
                        }

                        // print out error message if the piece is not yours
                        if (chessPiece.isWhite() != isWhiteTurn) {
                            System.out.println("This is not your piece");
                            continue;
                        }

                        // receive good input
                        while (true) {
                            System.out.println("Where do you want to move? Use Coordinates. Eg 0 2");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                destination = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }

                        // move the pieces, will go back to the start of the loop if it is an invalid move
                        if (chessBoard.movePiece(chessPiece, chessBoard, Integer.parseInt(destination[1]), Integer.parseInt(destination[0])) == true) {

                            // write the message into the log
                            writter.write("< White turn >\n");
                            writter.write(chessPiece.getClass() + " is moved from [" + piece[0] + ", " + piece[1] + "] to [" + destination[0] + ", " + destination[1] + "]\n");
                            writter.close();
                        } else
                            continue;
                        writter.close();
                        break;
                    }
                    isWhiteTurn = false;
                } else {
                    // same thing but this is for black turn
                    while (true) {
                        BufferedWriter writter = new BufferedWriter(new FileWriter("Log.txt", true));
                        if (chessBoard.isChecked(black_king, chessBoard)) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Check");
                                alert.setHeaderText("Your king is in check!");
                                alert.setContentText("You must get out of check.");
                                alert.showAndWait();
                            });
                        }
                        while (true) {
                            System.out.println("< Black turn >");
                            System.out.println("Which piece do you want to move? Use Coordinates. Eg. 0 0");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                piece = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }
                        chessPiece = chessBoard.getPieceAt(Integer.parseInt(piece[1]), Integer.parseInt(piece[0]));
                        if (chessPiece == null) {
                            System.out.println("There is no piece on the coordinates");
                            continue;
                        }
                        if (chessPiece.isWhite() != isWhiteTurn) {
                            System.out.println("This is not your piece");
                            continue;
                        }
                        while (true) {
                            System.out.println("Where do you want to move? Use Coordinates. Eg 0 2");
                            response = sc.nextLine();
                            if (response.length() == 3 && Character.isDigit(response.charAt(0)) && response.charAt(1) == ' ' && Character.isDigit(response.charAt(2))) {
                                destination = response.split(" ");
                                break;
                            } else {
                                System.out.println("Invalid input.");
                                continue;
                            }
                        }

                        if (chessBoard.isChecked(black_king, chessBoard))
                            System.out.println("Your king is checked");

                        if (chessBoard.movePiece(chessPiece, chessBoard, Integer.parseInt(destination[1]), Integer.parseInt(destination[0])) == true) {

                            writter.write("< Black turn >\n");
                            writter.write(chessPiece.getClass() + " is moved from [" + piece[0] + ", " + piece[1] + "] to [" + destination[0] + ", " + destination[1] + "]\n");

                            writter.close();


                        } else
                            continue;
                        writter.close();
                        break;
                    }
                    isWhiteTurn = true;
                }
            }
            // check if the king exist
            if (white_king.getX() != -1){
                // will print out white wins if the king doesn't exist on the board
                System.out.println("White wins");
                // alert box for game ends
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Game Ends");
                    alert.setTitle("Game Ends");
                    alert.setContentText("White wins");
                    alert.showAndWait();
                });
            }else if(black_king.getX() != -1){
                // similar to the white wins
                System.out.println("Black wins");
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Game Ends");
                    alert.setTitle("Game Ends");
                    alert.setContentText("Black wins");
                    alert.showAndWait();
                });
            }
        }catch(IOException e){
            System.out.println("Cannot Open file");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*==============================================================================
    void GUI()
    --------------------------------------------------------------------------------
    Launches GUI.
    ==============================================================================*/

    public static void GUI() {
        launch();
    }
}