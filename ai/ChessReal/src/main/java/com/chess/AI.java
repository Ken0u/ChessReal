package com.chess;

import com.chess.piece.Piece;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class AI {
    private Random rand;

    public AI() {
        rand = new Random();
    }

    public void makeMove(Board board, boolean white_turn) throws IOException, InterruptedException
    {
        Thread.sleep((int) (6 * Math.random()));

        // Generate random coordinates for the move
        int fromX = rand.nextInt(8);
        int fromY = rand.nextInt(8);
        int toX = rand.nextInt(8);
        int toY = rand.nextInt(8);
        BufferedWriter writter = new BufferedWriter(new FileWriter("Log.txt", true));

        // Check if the move is valid
        if (board.getPieceAt(fromY, fromX) != null) {
            Piece chessPiece = board.getPieceAt(fromY, fromX);
            if (chessPiece instanceof com.chess.piece.Bishop && chessPiece.isWhite() == white_turn && board.movePiece(chessPiece, board, toY, toX)) {

                System.out.print("< Black turn >\n");
                System.out.print(chessPiece.getClass() + " is moved from [" + fromX + ", " + fromY + "] to [" + toX + ", " + toY + "]\n");

                writter.write("< Black turn >\n");
                writter.write(chessPiece.getClass() + " is moved from [" + fromX + ", " + fromY + "] to [" + toX + ", " + toY + "]\n");
                writter.close();

            } else {
                // If the move is not valid, try again
                makeMove(board, white_turn);
            }
        }else
            makeMove(board, white_turn);
    }
}
