import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import aiAlgorithmes.hyperneat.HyperNeat;
import aiAlgorithmes.neat.Pool;
import aiAlogrithmes.ki.KICluster;
import game.Game;
import game.Player;
import game.Running;
import game.gameDisplay;


public class GUI {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    static public Thread t;
    private JTextField textField_3;
    private JTextField txtCtmp;
    private JTextField txtCtmp_1;



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
            public void actionPerformed(ActionEvent e) {}
        });
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Human", "Algorithm", "KI", "Neat", "HyperNeat"}));
        comboBox.setBounds(88, 157, 89, 20);
        frame.getContentPane().add(comboBox);

        JLabel lblType = new JLabel("Type");
        lblType.setBounds(20, 160, 46, 14);
        frame.getContentPane().add(lblType);

        JLabel lblPlayerA = new JLabel("Player a");
        lblPlayerA.setBounds(88, 132, 46, 14);
        frame.getContentPane().add(lblPlayerA);

        JLabel lblPlayerB = new JLabel("Player b");
        lblPlayerB.setBounds(235, 132, 46, 14);
        frame.getContentPane().add(lblPlayerB);

        final JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Human", "Algorithm", "KI", "Neat", "HyperNeat"}));
        comboBox_1.setBounds(235, 157, 73, 20);
        frame.getContentPane().add(comboBox_1);

        JLabel lblStrength = new JLabel("Strength");
        lblStrength.setBounds(20, 188, 60, 14);
        frame.getContentPane().add(lblStrength);



        JLabel lblGamesettings = new JLabel("Gamesettings");
        lblGamesettings.setBounds(170, 39, 73, 14);
        frame.getContentPane().add(lblGamesettings);

        JLabel lblRunden = new JLabel("min. Fitness");
        lblRunden.setBounds(154, 64, 104, 14);
        frame.getContentPane().add(lblRunden);

        textField = new JTextField();
        textField.setText("0");
        textField.setBounds(35, 89, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        final JCheckBox chckbxShowNeurons = new JCheckBox("Show Neurons");
        chckbxShowNeurons.setSelected(true);
        chckbxShowNeurons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        chckbxShowNeurons.setBounds(294, 50, 115, 23);
        frame.getContentPane().add(chckbxShowNeurons);

        JCheckBox chckbxOnlyTakeBest = new JCheckBox("Only take best KI");
        chckbxOnlyTakeBest.setBounds(235, 236, 121, 23);
        frame.getContentPane().add(chckbxOnlyTakeBest);

        textField_1 = new JTextField();
        textField_1.setText("0");
        textField_1.setBounds(88, 188, 86, 20);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setText("0");
        textField_2.setBounds(232, 188, 86, 20);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JCheckBox chckbxOnlyTakeBest_1 = new JCheckBox("Only take best KI");
        chckbxOnlyTakeBest_1.setBounds(10, 236, 138, 23);
        frame.getContentPane().add(chckbxOnlyTakeBest_1);


        JButton btnStart = new JButton("Start");

        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                // System.out.println("LOL");
                if (GUI.t != null) {

                    t.interrupt();
                }

                String typeb = comboBox_1.getSelectedItem().toString();
                String typea = comboBox.getSelectedItem().toString();
                int typeaa = 0;
                int typebb = 0;
                int runden = Integer.parseInt(textField.getText());
                int strengtha = Integer.parseInt(textField_1.getText());
                int strengthb = Integer.parseInt(textField_2.getText());
                // int strengtha =
                if (typea.equals("Human")) {
                    typeaa = 2;
                }
                if (typea.equals("Algorithm")) {
                    typeaa = 1;
                }
                if (typeb.equals("Human")) {
                    typebb = 2;
                }
                if (typeb.equals("Algorithm")) {
                    typebb = 1;
                }
                if (typea.equals("Neat")) {
                    typeaa = 3;
                }
                if (typeb.equals("Neat")) {
                    typebb = 3;

                }
                if (typea.equals("HyperNeat")) {
                    typeaa = 4;
                }
                if (typeb.equals("HyperNeat")) {
                    typebb = 4;
                }
                Player a = new Player(typeaa, strengtha);
                Player b = new Player(typebb, strengthb);
                HyperNeat hyper1 = null;
                HyperNeat hyper2 = null;
                System.out.println(a.Playertype + " " + a.Playerstrength);
                System.out.println(b.Playertype + " " + b.Playerstrength);


                if (typeaa == 0) {
                    a = new Player();
                }
                if (typebb == 0) {
                    b = new Player();

                }
                Game g = new Game(a, b);


                // Initialize KIs and Pools
                Pool p = new Pool(42, 3);
                Pool p2 = new Pool(42, 3);
               
                KICluster test = new KICluster(40, 3, 4, 1);



                // Loading saved data
                int load1 = 0;
                int load2 = 0;

                if (a.Playertype == 3 && !txtCtmp.getText().equals("")) {
                    try {
                        p = Pool.load("C:/tmp/Neat/Shodan1.ki");
                        load1 = 3;
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // p = new Pool(42,3);
                        // p.save("C:/Neat/Shodan1.ki", 0);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (p == null) {
                        p = new Pool(42, 3);
                    }
                }


                if (b.Playertype == 3 && !txtCtmp_1.getText().equals("")) {
                    try {
                        p2 = Pool.load("C:/tmp/Neat/Shodan2.ki");
                        load2 = 3;
                        // System.out.println("Loaded SHodan2 "+p2.generation);
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // p = new Pool(42,3);
                        // p.save("C:/Neat/Shodan2.ki", 0);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (p2 == null) {
                        p2 = new Pool(42, 3);
                    }

                }
                // hyperneat
                if (a.Playertype == 4 && !txtCtmp.getText().equals("")) {
                    p = new Pool(4, 1);
                    try {
                        p = Pool.load("C:/tmp/Hyper/generator.ki");
                        load1 = 4;
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (p == null) {
                        p = new Pool(4, 1);
                    }
                    hyper1 = new HyperNeat(42, 3, 0, 7, 6, p);
                }

                if (a.Playertype == 4 && txtCtmp.getText().equals("")) {
                    p = new Pool(4, 1);
                    hyper1 = new HyperNeat(42, 3, 0, 7, 6, p);
                }


                if (b.Playertype == 4 && !txtCtmp.getText().equals("")) {
                    p2 = new Pool(4, 1);
                    try {
                        p2 = Pool.load("C:/tmp/Hyper/generator.ki");
                        load2 = 4;
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (p2 == null) {
                        p2 = new Pool(4, 1);
                    }
                    hyper2 = new HyperNeat(42, 3, 0, 7, 6, p2);
                }

                if (b.Playertype == 4 && txtCtmp.getText().equals("")) {
                    p2 = new Pool(4, 1);
                    hyper2 = new HyperNeat(42, 3, 0, 7, 6, p2);
                }

                if (a.Playertype == 0 && !txtCtmp.getText().equals("")) {
                    try {
                        test = KICluster.load("C:/tmp/Normal/GLaDoS.ki");
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    load1 = 1;
                }


                if (b.Playertype == 0 && !txtCtmp_1.getText().equals("")) {
                    try {
                        test = KICluster.load("C:/tmp/Normal/GLaDoS.ki");
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    load2 = 1;
                }

                // KI shower = b.ki.copy();
                // Game2.main(b.ki,new JFrame());
                gameDisplay dis = null;
                if (chckbxShowNeurons.isSelected() && (b.Playertype == 0)) {
                    dis = gameDisplay.main(b.ki);
                }
                if (chckbxShowNeurons.isSelected() && (a.Playertype == 0)) {
                    dis = gameDisplay.main(a.ki);
                }

                if (a.Playertype == 3) {
                    a.pool = p;
                    dis = gameDisplay.main(p.Species.get(p.currentSpecies - 1).Genomes.get(p.currentGenome - 1), p.generation);
                    a.ki = null;

                }
                if (b.Playertype == 3) {
                    b.pool = p2;
                    dis = gameDisplay.main(p2.Species.get(p2.currentSpecies - 1).Genomes.get(p2.currentGenome - 1), p2.generation);
                    b.ki = null;
                }
                if (a.Playertype == 4) {
                    a.pool = p;
                    dis = gameDisplay.main(p.Species.get(p.currentSpecies - 1).Genomes.get(p.currentGenome - 1), p.generation);
                    a.ki = null;

                }
                if (b.Playertype == 4) {
                    b.pool = p2;
                    dis = gameDisplay.main(p2.Species.get(p2.currentSpecies - 1).Genomes.get(p2.currentGenome - 1), p2.generation);
                    b.ki = null;
                }


                int minfit = Integer.parseInt(textField_3.getText());

                Running r = new Running(g, runden, test, p, p2, load1, load2, dis, minfit, hyper1, hyper2);
                g.showgui = 1;
                GUI.t = new Thread(r);
                t.start();
                // TODO: ADD save routine

            }
        });
        btnStart.setBounds(154, 236, 73, 23);
        frame.getContentPane().add(btnStart);

        JLabel label_1 = new JLabel("Runden");
        label_1.setBounds(35, 64, 46, 14);
        frame.getContentPane().add(label_1);

        textField_3 = new JTextField();
        textField_3.setText("0");
        textField_3.setBounds(157, 89, 86, 20);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        JLabel lblLoadpath = new JLabel("Loadpath");
        lblLoadpath.setBounds(20, 215, 60, 14);
        frame.getContentPane().add(lblLoadpath);

        txtCtmp = new JTextField();
        txtCtmp.setText("C:\\tmp\\");
        txtCtmp.setBounds(88, 213, 86, 20);
        frame.getContentPane().add(txtCtmp);
        txtCtmp.setColumns(10);

        txtCtmp_1 = new JTextField();
        txtCtmp_1.setText("C:\\tmp\\");
        txtCtmp_1.setBounds(232, 212, 86, 20);
        frame.getContentPane().add(txtCtmp_1);
        txtCtmp_1.setColumns(10);
    }
}
