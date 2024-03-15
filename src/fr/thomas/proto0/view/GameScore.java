package fr.thomas.proto0.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class GameScore extends JFrame {

    private GameController controller;
    private HashMap<Question, Answer> gameHistory;
    
    private static final long serialVersionUID = 1L;
   

    public GameScore(GameController controller) {
    	this.controller = controller;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JLabel ResultsWord = new JLabel("Résultats");
        ResultsWord.setFont(new Font("Tahoma", Font.PLAIN, 15));
        getContentPane().add(ResultsWord, BorderLayout.NORTH);
    }
    
    public void setGameHistory(HashMap<Question, Answer> gameHistory) {
		this.gameHistory = gameHistory;
		
		Object[][] data = new Object[gameHistory.size()][4];
		int pos = 0;
		
		for(Map.Entry<Question, Answer> entrySet: gameHistory.entrySet()) {
			data[pos] = new Object[] {entrySet.getKey().getLabel(), entrySet.getValue().getLabel(), entrySet.getValue().isCorrect()};
			pos++;
		}
        // Sample column names
        String[] columnNames = {"Libellé Question", "Réponse choisie", "Correct ?"};

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
