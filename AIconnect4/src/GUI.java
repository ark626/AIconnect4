import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import ki.KI;
import ki.KICluster;


public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	static 	public Thread t;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 236, 46, 14);
		frame.getContentPane().add(label);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblSettings.setBounds(170, 11, 73, 32);
		frame.getContentPane().add(lblSettings);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Human", "Algorithm", "KI"}));
		comboBox.setBounds(88, 157, 89, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(10, 163, 46, 14);
		frame.getContentPane().add(lblType);
		
		JLabel lblPlayerA = new JLabel("Player a");
		lblPlayerA.setBounds(88, 132, 46, 14);
		frame.getContentPane().add(lblPlayerA);
		
		JLabel lblPlayerB = new JLabel("Player b");
		lblPlayerB.setBounds(235, 132, 46, 14);
		frame.getContentPane().add(lblPlayerB);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Human", "Algorithm", "KI"}));
		comboBox_1.setBounds(235, 157, 73, 20);
		frame.getContentPane().add(comboBox_1);
		
		JLabel lblStrength = new JLabel("Strength");
		lblStrength.setBounds(10, 200, 60, 14);
		frame.getContentPane().add(lblStrength);
		
	
		
		JLabel lblGamesettings = new JLabel("Gamesettings");
		lblGamesettings.setBounds(170, 54, 73, 14);
		frame.getContentPane().add(lblGamesettings);
		
		JLabel lblRunden = new JLabel("Runden");
		lblRunden.setBounds(10, 79, 46, 14);
		frame.getContentPane().add(lblRunden);
		
		textField = new JTextField();
		textField.setBounds(154, 79, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JCheckBox chckbxShowNeurons = new JCheckBox("Show Neurons");
		chckbxShowNeurons.setSelected(true);
		chckbxShowNeurons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		chckbxShowNeurons.setBounds(259, 78, 115, 23);
		frame.getContentPane().add(chckbxShowNeurons);
		
		JCheckBox chckbxOnlyTakeBest = new JCheckBox("Only take best KI");
		chckbxOnlyTakeBest.setBounds(235, 227, 121, 23);
		frame.getContentPane().add(chckbxOnlyTakeBest);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setBounds(91, 197, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("0");
		textField_2.setBounds(235, 197, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JCheckBox chckbxOnlyTakeBest_1 = new JCheckBox("Only take best KI");
		chckbxOnlyTakeBest_1.setBounds(10, 227, 138, 23);
		frame.getContentPane().add(chckbxOnlyTakeBest_1);
		
		
	JButton btnStart = new JButton("Start");
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//System.out.println("LOL");
				if(GUI.t != null){

					t.interrupt();
				}
				
					String typeb =comboBox_1.getSelectedObjects().toString();
					String typea = comboBox.getSelectedItem().toString();
					int typeaa = 0;
					int typebb = 0;
					int runden = Integer.parseInt(textField.getText());
					int strengtha = Integer.parseInt(textField_1.getText());
					int strengthb = Integer.parseInt(textField_2.getText());
			//		int strengtha = 
					if(typea.equals("Human")){
						typeaa = 2;
					}
					if(typea.equals("Algorithm")){
						typeaa = 1;
					}
					if(typeb.equals("Human")){
						typebb = 2;
					}
					if(typeb.equals("Algorithm")){
						typebb = 1;
					}
					Player a = new Player(typeaa,strengtha);
					Player b = new Player(typebb,strengthb);
					KICluster test = new KICluster(40,3,8,2);
					try{
						test = KICluster.load("/tmp/GLaDoS.ki");
						}
						catch (Exception e){
							
						}
					if(typeaa == 0){
						a = new Player();
					}
					if(typebb == 0){
						b = new Player();
						
					}
					Game g = new Game(a,b);

					b.ki = test.best();
					
					//KI shower = b.ki.copy();
					//Game2.main(b.ki,new JFrame());
					gameDisplay dis = null;
					if(chckbxShowNeurons.isSelected()){
					 dis = gameDisplay.main(b.ki);
					}
					
					Running r = new Running(g,runden,test,dis);
					GUI.t = new Thread(r);
					t.start();
	//				TODO: ADD save routine
				
			}
		});
		btnStart.setBounds(154, 236, 73, 23);
		frame.getContentPane().add(btnStart);
	}
}
