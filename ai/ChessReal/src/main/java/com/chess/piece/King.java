package com.chess.piece;

import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this is a subclass that inherits from the piece class, containing every method inside the piece class
public class King extends Piece {
    private ImageView kingView;
    private String path = Player.path;


    // when the object is created, it will set the color, x and y of the piece. Depends on the color, it will use different image
    public King(int y, int x, boolean isWhite) {
        super(isWhite);
        kingView = new ImageView();
        this.x = x;
        this.y = y;
        if (isWhite) {
            kingView.setImage(new Image(path+"white_king.png"));
        } else {
            kingView.setImage(new Image(path+"black_king.png"));
        }
    }

    /*==============================================================================
    Image getImage()
    --------------------------------------------------------------------------------
    returns Image - gets image of the piece.
    --------------------------------------------------------------------------------
    Gets image of the piece.
    ==============================================================================*/

    public Image getImage(){
        return kingView.getImage();
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

    public boolean isValidMove(int newY, int newX, Board board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);
        if((xDiff <= 1 && yDiff <= 1)){
            if (board.isPieceAt(newY, newX)) {
                if (board.getPieceAt(newY, newX).isWhite != this.isWhite)
                    return true;
                else
                    return false;
            }else{
                return true;
            }
        }
        if (isWhite){
            if (newX == 6 && newY == 7 && x == 4 && y == 7 && board.getPieceAt(7, 7).isRook) {
                if (!isPathBlocked(newY, newX, board)) {
                    // set the X and Y value of the variable
                    board.getPieceAt(7, 7).moveTo(7, 5);
                    board.setPieceAt(7, 5, board.getPieceAt(7, 7));
                    board.setPieceAt(7, 7, null);
                    return true;
                }
            }else if((newX == 2 && newY == 7 && x == 4 && y == 7 && board.getPieceAt(0, 7).isRook)){
                if (!isPathBlocked(newY, newX, board)) {
                    // set the X and Y value of the variable
                    board.getPieceAt(7, 7).moveTo(3, 7);
                    board.setPieceAt(7, 3, board.getPieceAt(7, 7));
                    board.setPieceAt(7, 0, null);
                    return true;
                }
            }
        } else{
            if (newX == 6 && newY == 0 && x == 4 && y == 0 && board.getPieceAt(7, 0).isRook) {
                if (!isPathBlocked(newY, newX, board)) {
                    board.getPieceAt(0, 7).moveTo(5, 0);
                    board.setPieceAt(0, 5, board.getPieceAt(7, 0));
                    board.setPieceAt(7, 0, null);
                    return true;
                }
            }else if((newX == 2 && newY == 0 && x == 4 && y == 0 && board.getPieceAt(0, 0).isRook)){
                if (!isPathBlocked(newY, newX, board)) {
                    board.getPieceAt(0, 0).moveTo(3, 0);
                    board.setPieceAt(0, 3, board.getPieceAt(0, 0));
                    board.setPieceAt(0, 0, null);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isPathBlocked(int newY, int newX, Board board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);
        int xStep = x < newX ? 1 : -1;
        int yStep = y < newY ? 1 : -1;
        // check if there's any piece blocking the path to the destination square
        if (newX == x) {
            for (int i = y + yStep; i != newY; i += yStep) {
                if (board.getPieceAt(i, x) != null) {
                    return true;
                }
            }
        } else if (newY == y) {
            for (int i = x + xStep; i != newX; i += xStep) {
                if (board.getPieceAt(y, i) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}

