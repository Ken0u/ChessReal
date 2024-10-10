package com.chess.piece;


import com.chess.Board;
import com.chess.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// this is a subclass that inherits from the piece class, containing every method inside the piece class
public class Pawn extends Piece {
    private ImageView pawnView;
    private String path = Player.path;

    // when the object is created, it will set the color, x and y of the piece. Depends on the color, it will use different image
    public Pawn(int x, int y, boolean isWhite) {
        super(isWhite);
        pawnView = new ImageView();
        this.x = x;
        this.y = y;
        if (isWhite) {
            pawnView.setImage(new Image(path+"white_pawn.png"));
        } else {
            pawnView.setImage(new Image(path+"black_pawn.png"));
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
        return pawnView.getImage();
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
        Piece piece = board.getPieceAt(newY, newX);
        boolean valid = false;
        if (isWhite) {
            if(y == 6 && newX == x && newY == y - 2 && piece == null && !board.isPieceAt(y-1, x)){
                valid = true;
            }else if (newX == x && newY == y - 1 && !board.isPieceAt(y-1, x)) {
                valid = true;
            }else if(y == newY + 1 && (x == newX + 1 || x == newX - 1) && piece != null && piece.isWhite != this.isWhite()){
                valid = true;
            }

        } else {
            if(y == 1 && newX == x && newY == y + 2 && piece == null && !board.isPieceAt(y+1, x)){
                valid = true;
            }else if (newX == x && newY == y + 1 && !board.isPieceAt(y+1, x)) {
                valid = true;
            }else if(y == newY - 1 && (x == newX + 1 || x == newX - 1) && piece != null && piece.isWhite != this.isWhite()){
                valid = true;
            }
        }
        if(isWhite && valid && newY == 0){
            board.setPieceAt(y, x, new Queen(x, y, isWhite));
        }else if(!isWhite && valid && newY == 7){
            board.setPieceAt(y, x, new Queen(x, y, isWhite));
        }
        return valid;

    }
}
