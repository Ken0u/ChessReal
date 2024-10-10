package com.chess.piece;


import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this is a subclass that inherits from the piece class, containing every method inside the piece class
public class Queen extends Piece {
    private ImageView queenView;
    private String path = Player.path;

    // when the object is created, it will set the color, x and y of the piece. Depends on the color, it will use different image
    public Queen(int x, int y, boolean isWhite) {
        super(isWhite);
        queenView = new ImageView();
        this.x = x;
        this.y = y;
        if (isWhite) {
            queenView.setImage(new Image(path + "white_queen.png"));
        } else {
            queenView.setImage(new Image(path + "black_queen.png"));
        }
    }

    /*==============================================================================
    Image getImage()
    --------------------------------------------------------------------------------
    returns Image - gets image of the piece.
    --------------------------------------------------------------------------------
    Gets image of the piece.
    ==============================================================================*/

    public Image getImage() {
        return queenView.getImage();
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
        if (board.getPieceAt(newY, newX) != null) {
            if (board.getPieceAt(newY, newX).isWhite() != this.isWhite()) {
                return !isPathBlocked(newX, newY, board);
            } else {
                return false;
            }
        } else {
            return !isPathBlocked(newX, newY, board);
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
    Determines whether a piece path is blocked.
    ==============================================================================*/

    private boolean isPathBlocked(int newX, int newY, Board board) {
        int xDiff = Math.abs(newX - this.x);
        int yDiff = Math.abs(newY - this.y);
        int xStep = x < newX ? 1 : -1;
        int yStep = y < newY ? 1 : -1;
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
        } else if (xDiff == yDiff) {
            for (int i = 1; i < xDiff; i++) {
                if (board.getPieceAt(x + i * xStep, y + i * yStep) != null) {
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }
}
