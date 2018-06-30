import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class Driver {

	private static JPanel panelBoard;
	private static JTextField textN;
	private static JLabel message = new JLabel("Black = Queen");

	private static JRadioButton radioBFS;
	private static JRadioButton radioDFS;
	private static JRadioButton radioIDS;
	private static JRadioButton radioAStar;
	private static JRadioButton radioIDAStar;

	public static void main(String[] args) {

		JFrame mainFrame = new JFrame("COMP338 - Project 1");

		radioBFS = new JRadioButton("BFS");
		radioDFS = new JRadioButton("DFS");
		radioIDS = new JRadioButton("IDS");
		radioAStar = new JRadioButton("A*");
		radioIDAStar = new JRadioButton("IDA*");
		ButtonGroup radioG = new ButtonGroup();

		radioG.add(radioBFS);
		radioG.add(radioDFS);
		radioG.add(radioIDS);
		radioG.add(radioAStar);
		radioG.add(radioIDAStar);

		JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		panelRadio.add(radioBFS);
		panelRadio.add(radioDFS);
		panelRadio.add(radioIDS);
		panelRadio.add(radioAStar);
		panelRadio.add(radioIDAStar);

		JButton buttonSearch = new JButton("Find that sneaky solution!");
		textN = new JTextField(2);

		JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		panelControl.add(textN);
		panelControl.add(buttonSearch);
		panelControl.add(message);

		JPanel panelControlOverall = new JPanel(new BorderLayout());

		panelControlOverall.setBorder(new TitledBorder("Algorithm and N"));

		panelControlOverall.add(panelRadio, BorderLayout.NORTH);
		panelControlOverall.add(panelControl, BorderLayout.CENTER);

		panelBoard = new JPanel();

		panelBoard.setBorder(new TitledBorder("Board"));

		buttonSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// message.setText("Computing...");
				// mainFrame.repaint();

				try {

					Board.N = Integer.parseInt(textN.getText());

					Board initialBoard = new Board(0);
					
					if (Board.N < 4 || Board.N > 12)
						return;
					
					int selection = -1;

					if (radioBFS.isSelected())
						selection = 0;
					else if (radioDFS.isSelected())
						selection = 1;
					else if (radioIDS.isSelected())
						selection = 2;
					else if (radioAStar.isSelected())
						selection = 3;
					else if (radioIDAStar.isSelected())
						selection = 4;

					Board solutionBoard = null;

					switch (selection) {

					case 0:
						solutionBoard = BFS.BFSBoard(initialBoard);
						break;
					case 1:
						solutionBoard = DFS.DFSBoard(initialBoard);
						break;
					case 2:
						solutionBoard = IDS.IDSBoard(initialBoard);
						break;
					case 3:
						solutionBoard = AStar.AStarBoard(initialBoard);
						break;
					case 4:
						solutionBoard = IDAStar.IDAStarBoard(initialBoard);
						break;
					default:

					}

					panelBoard.setLayout(new GridLayout(Board.N, Board.N));

					boolean checker = false;
					boolean even = Board.N % 2 == 0;

					panelBoard.removeAll();

					for (int i = 0; i < Board.N; i++) {

						if (even)
							checker = !checker;

						for (int j = 0; j < Board.N; j++) {

							Label labelToAdd = new Label();

							if (solutionBoard.getPositions()[i] == j)
								labelToAdd.setBackground(Color.BLACK);
							else if (checker)
								labelToAdd.setBackground(Color.RED);
							else
								labelToAdd.setBackground(Color.BLUE);

							checker = !checker;

							labelToAdd.setSize(new Dimension(50, 50));

							panelBoard.add(labelToAdd);
							SwingUtilities.updateComponentTreeUI(mainFrame);
						}
					}
				} catch (Exception ex) {

				}
			}
		});

		JPanel panelMain = new JPanel(new BorderLayout());

		panelMain.add(panelControlOverall, BorderLayout.NORTH);
		panelMain.add(panelBoard, BorderLayout.CENTER);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(panelMain);
		mainFrame.setSize(500, 500);
		mainFrame.setLocationRelativeTo(null);
		// mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		/*
		 * Board b1 = new Board(0); System.out.println(b1 + " - " +
		 * b1.getCollisions());
		 * 
		 * long wait = System.currentTimeMillis();
		 * 
		 * Board b2 = BFS.BFSBoard(b1); System.out.print(b2 + " - " +
		 * b2.getCollisions() + " - ");
		 * System.out.println(System.currentTimeMillis() - wait);
		 * 
		 * wait = System.currentTimeMillis(); Board b3 = DFS.DFSBoard(b1);
		 * System.out.print(b3 + " - " + b3.getCollisions() + " - ");
		 * System.out.println(System.currentTimeMillis() - wait);
		 * 
		 * wait = System.currentTimeMillis(); Board b4 = AStar.AStarBoard(b1);
		 * System.out.print(b4 + " - " + b4.getCollisions() + " - ");
		 * System.out.println(System.currentTimeMillis() - wait);
		 * 
		 * wait = System.currentTimeMillis(); Board b5 = IDS.IDSBoard(b1);
		 * System.out.print(b5 + " - " + b5.getCollisions() + " - ");
		 * System.out.println(System.currentTimeMillis() - wait);
		 * 
		 * 
		 * wait = System.currentTimeMillis(); Board b6 =
		 * IDAStar.IDAStarBoard(b1); System.out.print(b6 + " - " +
		 * b6.getCollisions() + " - ");
		 * System.out.println(System.currentTimeMillis() - wait);
		 */

	}

}
