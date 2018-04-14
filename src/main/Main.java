package main;

import view.MainWindow;

/**
 * The Main class creates a window for the user to be able to use the application
 * @author groep 03
 */
public class Main {

    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow("My Canvas Window").show();
        });

    }
}
