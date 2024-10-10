package com.chess.piece;

import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this is a subclass that inherits from the piece class, containing every method inside the piece class
public class Bishop extends Piece {
    private ImageView bishopView;
    private String path = Player.path;

    // when the object is created, it will set the color, x and y of the piece. Depends on the color, it will use different image
    public Bishop(int x, int y, boolean isWhite) {
        // this set the boolean using a class in piece
        super(isWhite);
        bishopView = new ImageView();
        this.x = x;
        this.y = y;
        if (isWhite) {
            bishopView.setImage(new Image(path + "white_bishop.png"));
        } else {
            bishopView.setImage(new Image(path + "black_bishop.png"));
        }
    }

    /*==============================================================================
    boolean isValidMove(int newX, int newY, Board board)
    --------------------------------------------------------------------------------
    returns boolean - whether a move is valid
    --------------------------------------------------------------------------------
    int newX - the new x coordinate of piece
    int newY - the new y coordinate of piece
    Board board - the board
    --------------------------------------------------------------------------------
    Determines whether a move is valid.
    ==============================================================================*/

    public boolean isValidMove(int newX, int newY, Board board) {
        // check if the move is a diagonal move
        if (board.getPieceAt(newX, newY) != null ) {
            if (board.getPieceAt(newX, newY).isWhite() != this.isWhite()){
                return Math.abs(newY - x) == Math.abs(newX - y) && !isPathBlocked(newX, newY, board);

            }else{
                return false;
            }
        }else {
            return Math.abs(newY - x) == Math.abs(newX - y) && !isPathBlocked(newX, newY, board);
        }
    }

    /*==============================================================================
    boolean isPathBlocked(int newX, int newY, Board board)
    --------------------------------------------------------------------------------
    returns boolean - whether a piece path is blocked
    --------------------------------------------------------------------------------
    int newX - the new x coordinate of piece
    int newY - the new y coordinate of piece
    Board board - the board
    --------------------------------------------------------------------------------
    Determines whether the path is blocked.
    ==============================================================================*/

    private boolean isPathBlocked(int newY, int newX, Board board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);
        int xStep = x < newX ? 1 : -1;
        int yStep = y < newY ? 1 : -1;
        // check if there's any piece blocking the path to the destination square
        for (int i = 1; i < xDiff; i++) {
            if (board.getPieceAt(y + i * yStep, x + i * xStep) != null) {
                return true;
            }
        }
        return false;
    }

    /*==============================================================================
    Image getImage()
    --------------------------------------------------------------------------------
    returns Image - gets image of the piece.
    --------------------------------------------------------------------------------
    Gets image of the piece.
    ==============================================================================*/

    public Image getImage(){
        return bishopView.getImage();
    }
}