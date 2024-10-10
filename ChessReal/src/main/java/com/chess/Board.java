package com.chess;

import com.chess.piece.*;

public class Board {
    private Piece[][] board;
    private int size = 8;

    // this will run when the Player class create the Board object, it will first create an array that store the pieces on each box. After it will set the board up.
    public Board(){
        board = new Piece[size][size];
        defaultBoard();

    }

    /*==============================================================================
    boolean isPieceAt(int x, int y)
    --------------------------------------------------------------------------------
    returns boolean - whether a piece exists at the coordinates
    --------------------------------------------------------------------------------
    int x - the x coordinate
    int y - the y coordinate
    --------------------------------------------------------------------------------
    Determines whether a piece exists at the coordinates.
    ==============================================================================*/

    public boolean isPieceAt(int x, int y) {
        return board[x][y] != null;
    }

    /*==============================================================================
    Piece isPieceAt(int x, int y)
    --------------------------------------------------------------------------------
    returns Piece - piece type at the coordinates
    --------------------------------------------------------------------------------
    int x - the x coordinate
    int y - the y coordinate
    --------------------------------------------------------------------------------
    Determines the piece object at the coordinates.
    ==============================================================================*/

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }

    /*==============================================================================
    void setPieceAt(int x, int y, Piece piece)
    --------------------------------------------------------------------------------
    int x - the x coordinate
    int y - the y coordinate
    Piece piece - the type of piece to be set
    --------------------------------------------------------------------------------
    Sets a piece at the coordinates.
    ==============================================================================*/

    public void setPieceAt(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    /*==============================================================================
    boolean movePiece(Piece piece, Board board, int newX, int newY)
    --------------------------------------------------------------------------------
    returns boolean - whether a piece move is legal
    --------------------------------------------------------------------------------
    Piece piece - the type of piece to be moved
    Board board - the board
    int newX - the new x coordinate of piece
    int newY - the new y coordinate of piece
    --------------------------------------------------------------------------------
    Determines whether a piece move is legal.
    ==============================================================================*/

    public boolean movePiece(Piece piece, Board board, int newX, int newY){
        // check for the valid move
        if (piece.isValidMove(newX, newY, board)) {
            Piece newPiece = board.getPieceAt(piece.getY(), piece.getX());
            setPieceAt(newPiece.getY(), newPiece.getX(), null);
            // set the X and Y value of the variable
            newPiece.moveTo(newY, newX);
            // if a piece is captured, it will set the coordinates to -1, -1 for the program to determine if the piece is still on the board
            if (board.isPieceAt(newX, newY))
                board.getPieceAt(newX, newY).moveTo(-1, -1);
            setPieceAt(newX, newY, newPiece);
            return true;
        } else {
            System.out.println("Invalid Move");
            return false;
        }
    }

    /*==============================================================================
    void defaultBoard()
    --------------------------------------------------------------------------------
    Sets the default board at the start of a game.
    ==============================================================================*/

    public void defaultBoard(){
        board[0][0] = new Rook(0, 0, false);
        board[0][1] = new Knight(1, 0, false);
        board[0][2] = new Bishop(2, 0, false);
        board[0][3] = new Queen(3, 0, false);
        board[0][4] = new King(4, 0, false);
        board[0][5] = new Bishop(5, 0, false);
        board[0][6] = new Knight(6, 0, false);
        board[0][7] = new Rook(7, 0, false);
        for (int i = 0; i < 8; i ++){
            board[1][i] = new Pawn(i, 1, false);
            board[6][i] = new Pawn(i, 6, true);
        }
        board[7][0] = new Rook(0, 7, true);
        board[7][1] = new Knight(1, 7, true);
        board[7][2] = new Bishop(2, 7, true);
        board[7][3] = new Queen(3, 7, true);
        board[7][4] = new King(4, 7, true);
        board[7][5] = new Bishop(5, 7, true);
        board[7][6] = new Knight(6, 7, true);
        board[7][7] = new Rook(7, 7, true);
    }

    /*==============================================================================
    boolean isChecked(Piece king, Board board)
    --------------------------------------------------------------------------------
    returns boolean - whether a king is under check
    --------------------------------------------------------------------------------
    Piece king - the king that may be under check
    Board board - the board
    --------------------------------------------------------------------------------
    Determines whether a king is under check.
    ==============================================================================*/

    public boolean isChecked(Piece king, Board board){
        // check every box, if there is a piece at the box. It will use the validMove method to see if the piece can go to the king spot
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getPieceAt(i, j) != null){
                    Piece piece = board.getPieceAt(i, j);
                    if((piece.isWhite() != king.isWhite()) && piece.isValidMove(king.getY(), king.getX(), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
