package com.chess.piece;

import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this is a subclass that inherits from the piece class, containing every method inside the piece class
public class Knight extends Piece {
    private ImageView knightView;
    private String path = Player.path;

    // when the object is created, it will set the color, x and y of the piece. Depends on the color, it will use different image
    public Knight(int y, int x, boolean isWhite) {
        super(isWhite);
        knightView = new ImageView();
        this.x = x;
        this.y = y;
        if (isWhite) {
            knightView.setImage(new Image(path+"white_knight.png"));
        } else {
            knightView.setImage(new Image(path+"black_knight.png"));
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
        return knightView.getImage();
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
        if((xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2)){
            if (board.isPieceAt(newY, newX)) {
                if (board.getPieceAt(newY, newX).isWhite != this.isWhite)
                    return true;
                else
                    return false;
            }else{
                return true;
            }
        }
        return false;
    }
}
