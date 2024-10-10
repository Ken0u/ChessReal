package com.chess.piece;

import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;

// this is an abstract class, for the pieces to inherit
public abstract class Piece {
    // initialize the variable
    private String path = Player.path;
    protected boolean isWhite;
    protected int x, y;
    protected boolean isRook = false;

    // this is called by super(isWhite), it will let the program know the color of the chess
    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    // an abstract method is a method that can be override by the pieces
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

    public abstract boolean isValidMove(int newY, int newX, Board board);

    /*==============================================================================
    Image getImage()
    --------------------------------------------------------------------------------
    returns Image - gets image of the piece.
    --------------------------------------------------------------------------------
    Returns the image of the piece.
    ==============================================================================*/

    public abstract Image getImage();

    /*==============================================================================
    boolean isWhite()
    --------------------------------------------------------------------------------
    returns boolean - whether a piece is white
    --------------------------------------------------------------------------------
    Determines whether a piece is white.
    ==============================================================================*/

    public boolean isWhite() {
        return isWhite;
    }

    /*==============================================================================
    int getX()
    --------------------------------------------------------------------------------
    returns int - x coordinate of the piece
    --------------------------------------------------------------------------------
    Returns the x coordinate of the piece.
    ==============================================================================*/

    public int getX() {
        return x;
    }

    /*==============================================================================
    int getY()
    --------------------------------------------------------------------------------
    returns int - y coordinate of the piece
    --------------------------------------------------------------------------------
    Returns the y coordinate of the piece.
    ==============================================================================*/

    public int getY() {
        return y;
    }

    /*==============================================================================
    void moveTo(int x, int y)
    --------------------------------------------------------------------------------
    int x - the x coordinate to be moved to
    int y - the y coordinate to be moved to
    --------------------------------------------------------------------------------
    Sets the moved piece coordinates to new coordinates.
    ==============================================================================*/

    public void moveTo(int y, int x) {
        this.x = x;
        this.y = y;
    }
}