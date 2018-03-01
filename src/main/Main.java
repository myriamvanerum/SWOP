package main;

import view.DiagramWindow;

public class Main {

    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(() -> {
            new DiagramWindow("My Canvas Window").show();
        });

    }
}
