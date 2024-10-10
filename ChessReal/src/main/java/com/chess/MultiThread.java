package com.chess;


public class MultiThread extends Thread{

    /*==============================================================================
    void run()
    --------------------------------------------------------------------------------
    Allows GUI to run in the background.
    ==============================================================================*/

    @Override
    public void run(){
        Player player = new Player();
        // launch the GUI
        player.GUI();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

