package fr.thomas.proto0.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.thomas.proto0.controller.GameController;
import java.awt.Font;

public class GameScore extends JFrame {

    private GameController controller;

    private static final long serialVersionUID = 1L;
    
    //TODO Remove
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameScore frame = new GameScore();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GameScore() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JLabel ResultsWord = new JLabel("Résultats");
        ResultsWord.setFont(new Font("Tahoma", Font.PLAIN, 15));
        getContentPane().add(ResultsWord, BorderLayout.NORTH);

        // Sample data for the table
        // TODO Remove
        Object[][] data = {
                {"1", "Les poules ont-elles des dents ?", "Non", "Correct"},
                {"2", "Est-ce que ceci est une question ?", "La réponse D", "Incorrect"}
        };

        // Sample column names
        String[] columnNames = {"ID", "Libellé Question", "Réponse choisie", "Correct ?"};

        JTable table = new JTable(data, columnNames);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                System.out.println("Row " + row + " clicked.");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
