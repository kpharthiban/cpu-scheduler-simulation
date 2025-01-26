import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MainCPUSchedulingVisualizer extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JPanel inputPanel;
    private JPanel resultButtonPanel;
    private JPanel inputTitle;

    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JTable processInfoTable;

    private List<Process> processes = new ArrayList<>(); // Store Processes data

    public MainCPUSchedulingVisualizer() {
        SwingUtilities.invokeLater(() -> {
            createMainWindow();
        });
    }

    // private void createMainWindow() {
    //     frame = new JFrame();
    //     frame.setTitle("CPU Scheduling Visualizer");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setLayout(new GridBagLayout());
    //     frame.setSize(450, 400);
    //     frame.setVisible(true);
    
    //     GridBagConstraints c = new GridBagConstraints();
    
    //     // Title Label
    //     JLabel inputTitleLabel = new JLabel("CPU Scheduling Visualizer");
    //     inputTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    //     inputTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment inside the label
    
    //     // Subtitle Label
    //     JLabel inputSubtitleLabel = new JLabel("Select an algorithm");
    //     inputSubtitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    //     inputSubtitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment inside the label
    
    //     panel = new JPanel();
    //     panel.setLayout(new GridBagLayout());
    
    //     // Title Constraints
    //     c.gridx = 0;
    //     c.gridy = 0;
    //     c.insets = new Insets(0, 10, 1, 10);
    //     c.fill = GridBagConstraints.HORIZONTAL;
    //     c.anchor = GridBagConstraints.CENTER;
    //     panel.add(inputTitleLabel, c);
    
    //     // Subtitle Constraints
    //     c.gridx = 0;
    //     c.gridy = 1;
    //     c.insets = new Insets(1, 10, 20, 10); // Adjust padding
    //     c.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal stretching
    //     c.anchor = GridBagConstraints.CENTER; // Center alignment
    //     panel.add(inputSubtitleLabel, c);
    
    //     // Add the panel to the frame
    //     c.gridx = 0;
    //     c.gridy = 0;
    //     c.fill = GridBagConstraints.BOTH; // Fill the entire space
    //     c.anchor = GridBagConstraints.CENTER; // Center the panel
    //     frame.add(panel, c);

    //     JButton fcfsButton = new JButton("First-Come First-Served (FCFS)");
    //     fcfsButton.setFont(new Font("Arial", Font.BOLD, 14));
    //     fcfsButton.addActionListener(e -> new CPUVisualizer("FCFS"));
    //     c.gridx = 0;
    //     c.gridy = 1;
    //     // c.weightx = 0.1;
    //     c.ipadx = 125;
    //     c.insets = new Insets(5, 10, 5, 10); // Adjust padding
    //     c.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
    //     frame.add(fcfsButton, c);

    //     JButton srtButton = new JButton("Shortest Remaining Time (SRT)");
    //     srtButton.setFont(new Font("Arial", Font.BOLD, 14));
    //     srtButton.addActionListener(e -> new CPUVisualizer("SRT"));
    //     c.gridx = 0;
    //     c.gridy = 2;
    //     // c.weightx = 0.1;
    //     frame.add(srtButton, c);

    //     JButton sjnButton = new JButton("Shortest Job Next (SJN)");
    //     sjnButton.setFont(new Font("Arial", Font.BOLD, 14));
    //     sjnButton.addActionListener(e -> new CPUVisualizer("SJN"));
    //     c.gridx = 0;
    //     c.gridy = 3;
    //     // c.weightx = 0.1;
    //     frame.add(sjnButton, c);

    //     JButton nonppButton = new JButton("Non-Preemptive Priority");
    //     nonppButton.setFont(new Font("Arial", Font.BOLD, 14));
    //     nonppButton.addActionListener(e -> new CPUVisualizer("Non-Preemptive Priority"));
    //     c.gridx = 0;
    //     c.gridy = 4;
    //     // c.weightx = 0.1;
    //     frame.add(nonppButton, c);

    //     JButton rrButton = new JButton("Round Robin");
    //     rrButton.setFont(new Font("Arial", Font.BOLD, 14));
    //     rrButton.addActionListener(e -> new CPUVisualizer("Round Robin"));
    //     c.gridx = 0;
    //     c.gridy = 5;
    //     // c.weightx = 0.1;
    //     c.insets = new Insets(5, 10, 20, 10); // Adjust padding
    //     frame.add(rrButton, c);
    // }
    
    
    private void createMainWindow () {
        frame = new JFrame();
        frame.setTitle("CPU Scheduling Visualizer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
    
        inputPanel = createInputPanel();

        resultButtonPanel = createResultButtonPanel(); // To add buttons that will display the results of each algorithm visualizer

        GridBagConstraints c = new GridBagConstraints();

        JLabel inputTitleLabel = new JLabel("CPU Scheduling Visualizer");
        inputTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inputTitleLabel.setBorder(new EmptyBorder(10,0,10,0));

        inputTitle = new JPanel();
        inputTitle.add(inputTitleLabel, BorderLayout.NORTH);

        JLabel processTableLabel = new JLabel("Process Information Table");
        processTableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        processTableLabel.setBorder(new EmptyBorder(10,0,10,0));

        tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(new Dimension(800, 200));
        tablePanel.add(processTableLabel, BorderLayout.NORTH);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        frame.add(inputTitle, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        frame.add(inputPanel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        tablePanel.add(processTableLabel, c);

        JScrollPane tableScrollPane = new JScrollPane(tablePanel);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        frame.add(tableScrollPane, c);

        updateTable();

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        frame.add(resultButtonPanel, c);

        frame.setSize(800, 800);
        frame.setVisible(true);
    }


    // Input Panel
    private JPanel createInputPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        JTextField processIdField = new JTextField();
        JTextField burstField = new JTextField();
        JTextField arrivalField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField quantumField = new JTextField("3");
        
        JButton addButton = new JButton("Add Process");

        // addButton.addActionListener(e -> {
        //     if (algo == "Non-Preemptive Priority") {
        //         try {
        //             String id = processIdField.getText();
        //             int arrival = Integer.parseInt(arrivalField.getText());
        //             int burst = Integer.parseInt(burstField.getText());
        //             int priority = Integer.parseInt(priorityField.getText());
        //             int quantum = 0;
        
        //             addProcess(algo, id, arrival, burst, priority, quantum);
        //         } 
        //         catch (NumberFormatException ex) {
        //             JOptionPane.showMessageDialog(null, "Invalid input!.");
        //         }
        //     }
        //     else if (algo == "Round Robin") {
        //         try {
        //             String id = processIdField.getText();
        //             int arrival = Integer.parseInt(arrivalField.getText());
        //             int burst = Integer.parseInt(burstField.getText());
        //             int priority = 0;
        //             int quantum = Integer.parseInt(quantumField.getText());
        
        //             addProcess(algo, id, arrival, burst, priority, quantum);
        //         } 
        //         catch (NumberFormatException ex) {
        //             JOptionPane.showMessageDialog(null, "Invalid input!.");
        //         }
        //     }
        //     else {
        //         try {
        //             String id = processIdField.getText();
        //             int arrival = Integer.parseInt(arrivalField.getText());
        //             int burst = Integer.parseInt(burstField.getText());
        //             int priority = 0;
        //             int quantum = 0;
        
        //             addProcess(algo, id, arrival, burst, priority, quantum);
        //         } 
        //         catch (NumberFormatException ex) {
        //             JOptionPane.showMessageDialog(null, "Invalid input!.");
        //         }
        //     }
            
        // });
        
        addButton.addActionListener(e -> {
            try {
                String id = processIdField.getText();
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());
                int priority = Integer.parseInt(priorityField.getText());
                int quantum = Integer.parseInt(quantumField.getText());
    
                addProcess(id, arrival, burst, priority, quantum);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input!.");
            }
        });


        GridBagConstraints ic = new GridBagConstraints();

        JPanel emptyPanel = new JPanel();

        ic.insets = new Insets(10, 0, 10, 0); //adds padding
        ic.gridx = 0;
        ic.gridy = 0;
        ic.weightx = 0.5;
        panel.add(emptyPanel, ic);

        // Add buttons for adding data into table (Process ID, Burst Time, Arrival Time, Priority, Quantum Time)
        ic.gridx = 1;
        ic.gridy = 0;
        ic.weightx = 0.2;
        ic.weighty = 1.0;
        ic.anchor = GridBagConstraints.EAST;

        panel.add(new JLabel("Process ID:"), ic);

        ic.gridx = 3;
        panel.add(new JLabel("Burst Time:"), ic);

        ic.gridx = 5;
        panel.add(new JLabel("Arrival Time:"), ic);

        ic.gridx = 7;
        panel.add(new JLabel("Priority:"), ic);

        ic.gridx = 9;
        panel.add(new JLabel("Quantum Time:"), ic);

        ic.gridx = 2;
        ic.weightx = 0.5;
        //ic.ipadx = 100;
        ic.insets = new Insets(0, 5, 0, 10); //adds padding
        ic.fill = GridBagConstraints.HORIZONTAL;
        panel.add(processIdField, ic);

        ic.gridx = 4;
        panel.add(burstField, ic);
        
        ic.gridx = 6;
        panel.add(arrivalField, ic);

        ic.gridx = 8;
        panel.add(priorityField, ic);

        ic.gridx = 10;
        panel.add(quantumField, ic);

        JPanel emptyPanel2 = new JPanel();
        ic.gridx = 11;
        ic.gridy = 0;
        ic.weightx = 0.5;
        ic.ipadx = 0;
        panel.add(emptyPanel2, ic);

        ic.insets = new Insets(10, 0, 10, 0); //adds padding
        ic.gridx = 0;
        ic.gridy = 0;
        // ic.weightx = 0.5;
        ic.fill = GridBagConstraints.NONE;
        ic.ipadx = 30;
        ic.anchor = GridBagConstraints.CENTER;
        // ic.insets = new Insets(0, 15, 0, 10); //adds padding
        buttonPanel.add(addButton, ic);

        ic.anchor = GridBagConstraints.CENTER;
        // JButton visualizeButton = new JButton("Visualize");

        // visualizeButton.addActionListener(e -> {
        //     int size;

        //     if (algo == "Non-Preemptive Priority") {
        //         size = p_processes.size();
        //     }
        //     else if (algo == "Round Robin") {
        //         size = rr_processes.size();
        //     }
        //     else {
        //         size = processes.size();
        //     }
            
        //     if (size >= 3 && size <= 10) {
        //         visualize(algo);
        //     } else {
        //         JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
        //     }
        // });
        
        // ic.gridx = 1;
        // ic.weightx = 0.1;
        // buttonPanel.add(visualizeButton, ic);

        // JButton resultButton = new JButton("Result Table");
        // // resultButton.addActionListener(e -> visualize());
        // ic.gridx = 2;
        // ic.weightx = 0.5;
        // ic.anchor = GridBagConstraints.WEST;
        // buttonPanel.add(resultButton, ic);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createResultButtonPanel() {
        JPanel algoPanel = new JPanel();
        algoPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 0, 10, 0); //adds padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        // ic.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 30;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton sjnButton = new JButton("Shortest Job Next (SJN)");

        sjnButton.addActionListener(e -> {
            int size;
            size = processes.size();
            if (size >= 3 && size <= 10) {
                String algo = "Test";
                // visualize(algo);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });
        
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.gridy = 1;
        gbc.ipadx = 125;
        gbc.insets = new Insets(5, 10, 5, 10); // Adjust padding
        sjnButton.setFont(new Font("Arial", Font.BOLD, 14));
        // gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        algoPanel.add(sjnButton, gbc);

        // JButton resultButton = new JButton("Result Table");
        // // resultButton.addActionListener(e -> visualize());
        // ic.gridx = 2;
        // ic.weightx = 0.5;
        // ic.anchor = GridBagConstraints.WEST;
        // buttonPanel.add(resultButton, ic);

        return algoPanel;
    }

    private void addProcess(String id, int arrival, int burst, int priority, int quantum) {
        try {
            // Checks if the process ID already exists
            boolean idExists = processes.stream().anyMatch(p -> p.getId().equals(id));

            if (!idExists)
            {
                processes.add(new Process(id, burst, arrival, priority, quantum));
                updateTable();
            }
            else {
                JOptionPane.showMessageDialog(this, "ERROR: Process " + id + " already exists!");
            }

            createInputPanel();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }


    private void updateTable() {
        String[] columnNames = {"Process", "Burst Time", "Arrival Time", "Priority", "Quantum Time"};
        // String[][] data = new String[processes.size()][columnNames.length];

        JButton removeButton = new JButton("Remove Process");
        JButton removeAllButton = new JButton("Remove All Process");

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };

        for (Process process : processes) {
            String[] row = {
                process.getId(),
                String.valueOf(process.getBurstTime()),
                String.valueOf(process.getArrivalTime()),
                String.valueOf(process.getPriority()),
                String.valueOf(process.getQuantumTime())
            };
            tableModel.addRow(row);
        }

        removeButton.addActionListener(e -> {
            int selectedRow = processInfoTable.getSelectedRow();
            if (selectedRow != -1) {
                String removedProcessId = (String) processInfoTable.getValueAt(selectedRow, 0);
                processes.remove(selectedRow);
                
                updateTable(); // Update the table after removing a process
                JOptionPane.showMessageDialog(this, "Process " +  removedProcessId + " removed!");
            } else {
                JOptionPane.showMessageDialog(this, "ERROR: No process selected!");
            }

            updateTable(); // Update the table after removing a process
        });

        removeAllButton.addActionListener(e -> {
            if (processes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No processes to remove!");
            } else {
                processes.clear(); // Clear all processes
                
                updateTable(); // Refresh the table to show an empty state
                JOptionPane.showMessageDialog(this, "All processes removed!");
            }
        });

        processInfoTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(processInfoTable);
        //tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        tablePanel.removeAll(); // Clear existing content
        tablePanel.setLayout(new GridBagLayout());

        GridBagConstraints tc = new GridBagConstraints();

        JLabel processTableLabel = new JLabel("Process Information Table");
        processTableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        processTableLabel.setBorder(new EmptyBorder(10,0,10,0));

        removeButton.setPreferredSize(new Dimension(100, 30)); // Example width: 100px, height: 30px
        removeAllButton.setPreferredSize(new Dimension(120, 30)); // Example width: 120px, height: 30px

        tc.gridx = 0;
        tc.gridy = 0;
        tc.gridwidth = 3;
        tc.fill = GridBagConstraints.NORTH;
        tc.anchor = GridBagConstraints.CENTER;
        tablePanel.add(processTableLabel, tc);

        tc.gridx = 0;
        tc.gridy = 1;
        tc.gridwidth = 2;
        tc.weightx = 1.0;
        tc.weighty = 1.0;
        tc.fill = GridBagConstraints.VERTICAL;
        tc.ipadx = 600;
        tablePanel.add(tableScrollPane, tc);
        //tablePanel.add(processInfoTable, tc);

        JLabel removeInfoLabel = new JLabel("Removing a Process: Select the row of the process to be removed and press 'Remove Process' button.");
        JLabel processInfoLabel = new JLabel("Minimum 3 processes and maximum 10 processes are required to show Gantt Chart and Result Table.");
        removeInfoLabel.setForeground(new Color(150, 0, 0));
        processInfoLabel.setForeground(Color.gray);

        tc.ipadx = 0;
        tc.gridx = 0;
        tc.gridy = 2;
        tc.gridwidth = 3; // Span only one column
        // tc.weightx = 0.1;
        tc.weighty = 0; // No vertical expansion
        tc.anchor = GridBagConstraints.CENTER; // Center the buttons
        tc.fill = GridBagConstraints.NONE; // Don't stretch the button
        // tc.insets = new Insets(10, 10, 10, 10); //adds padding
        tablePanel.add(removeInfoLabel, tc);

        tc.gridy = 3;
        tablePanel.add(processInfoLabel, tc);

        tc.gridx = 0;
        tc.gridy = 4;
        tc.gridwidth = 1; // Span only one column
        tc.weightx = 0.5;
        tc.weighty = 0; // No vertical expansion
        tc.anchor = GridBagConstraints.EAST; // Center the buttons
        tc.fill = GridBagConstraints.NONE; // Don't stretch the button
        tc.insets = new Insets(10, 10, 10, 10); //adds padding
        tablePanel.add(removeButton, tc);

        tc.gridx = 1; // Place the second button in the next column
        tc.gridy = 4;
        tc.gridwidth = 1; // Span only one column
        tc.weightx = 0.5;
        tc.weighty = 0; // No vertical expansion
        tc.anchor = GridBagConstraints.WEST; // Center the buttons
        tc.fill = GridBagConstraints.NONE; // Don't stretch the button
        tablePanel.add(removeAllButton, tc);

        //tablePanel.add(removeButton, BorderLayout.SOUTH);
        //tablePanel.add(tableScrollPane, BorderLayout.CENTER); // Add the updated table
        tablePanel.revalidate();
        tablePanel.repaint();

        // createInputPanel();
    }

    public static void main(String[] args) {
        new MainCPUSchedulingVisualizer();
    }
}
