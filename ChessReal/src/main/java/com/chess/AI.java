package com.chess;

import java.util.Random;

class AI {
    private Random rand;

    public AI() {
        rand = new Random();
    }

    public void makeMove(Board board) {
        // Generate random coordinates for the move
        int fromX = rand.nextInt(8);
        int fromY = rand.nextInt(8);
        int toX = rand.nextInt(8);
        int toY = rand.nextInt(8);

        // Check if the move is valid
        if (board.movePiece(board.getPieceAt(fromY, fromX), board, toY, toX)) {

        } else {
            // If the move is not valid, try again
            makeMove(board);
        }
    }
}
