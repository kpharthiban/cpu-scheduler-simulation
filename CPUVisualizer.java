import javax.swing.*;
// import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CPUVisualizer extends JFrame {

    private List<Process> processes = new ArrayList<>(); // Store Processes data
    private List<PProcess> p_processes = new ArrayList<>(); // Store Processes data
    private List<RRProcess> rr_processes = new ArrayList<>(); // Store Processes data
    private List<String[]> ganttChart = new ArrayList<>(); // Store Gantt chart data
    private JFrame frame;

    private JPanel inputTitle;
    private JPanel inputPanel;
    
    // private JPanel processTableText;
    private JPanel tablePanel;
    private JTable processInfoTable; // Table for the Processes information

    // private JPanel ganttText;
    private JPanel ganttPanel;
    private DefaultTableModel tableModel;

    public CPUVisualizer(String algo) {
        SwingUtilities.invokeLater(() -> {
            createGUI(algo);
        });
    }

    private void createGUI (String algo) {
        frame = new JFrame();
        frame.setTitle("CPU Scheduling Visualizer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
    
        inputPanel = createInputPanel(algo);

        ganttPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGanttChart(g, algo); // Use only Graphics object here
            }
        };

        GridBagConstraints c = new GridBagConstraints();

        JLabel inputTitleLabel = new JLabel(algo + " - Algorithm Visualizer");
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

        JLabel ganttLabel = new JLabel("Gantt Chart");
        ganttLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ganttLabel.setBorder(new EmptyBorder(10,0,10,0));

        ganttPanel.setBackground(Color.WHITE);
        ganttPanel.setPreferredSize(new Dimension(800, 200));
        ganttPanel.add(ganttLabel, BorderLayout.NORTH);

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

        updateTable(algo);

        // JButton visualizeButton = new JButton("Visualize");
        // visualizeButton.addActionListener(e -> visualize());

        // ganttPanel.add(visualizeButton);

        JScrollPane scrollPane = new JScrollPane(ganttPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // No vertical scrolling needed unless required
        scrollPane.getViewport().revalidate(); // Ensure viewport updates to reflect the new size

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.75;
        c.fill = GridBagConstraints.BOTH;
        frame.add(scrollPane, c);

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private JPanel createInputPanel(String algo) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        JTextField processIdField = new JTextField();
        JTextField arrivalField = new JTextField();
        JTextField burstField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField quantumField = new JTextField("3");
        
        JButton addButton = new JButton("Add Process");

        addButton.addActionListener(e -> {
            if (algo == "Non-Preemptive Priority") {
                try {
                    String id = processIdField.getText();
                    int arrival = Integer.parseInt(arrivalField.getText());
                    int burst = Integer.parseInt(burstField.getText());
                    int priority = Integer.parseInt(priorityField.getText());
                    int quantum = 0;
        
                    addProcess(algo, id, arrival, burst, priority, quantum);
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!.");
                }
            }
            else if (algo == "Round Robin") {
                try {
                    String id = processIdField.getText();
                    int arrival = Integer.parseInt(arrivalField.getText());
                    int burst = Integer.parseInt(burstField.getText());
                    int priority = 0;
                    int quantum = Integer.parseInt(quantumField.getText());
        
                    addProcess(algo, id, arrival, burst, priority, quantum);
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!.");
                }
            }
            else {
                try {
                    String id = processIdField.getText();
                    int arrival = Integer.parseInt(arrivalField.getText());
                    int burst = Integer.parseInt(burstField.getText());
                    int priority = 0;
                    int quantum = 0;
        
                    addProcess(algo, id, arrival, burst, priority, quantum);
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!.");
                }
            }
            
        });

        GridBagConstraints ic = new GridBagConstraints();

        JPanel emptyPanel = new JPanel();

        ic.insets = new Insets(10, 0, 10, 0); //adds padding
        ic.gridx = 0;
        ic.gridy = 0;
        ic.weightx = 0.5;
        panel.add(emptyPanel, ic);

        if (algo == "Non-Preemptive Priority") {
            // Add buttons for Non-Preemptive Priority algorithm
            ic.gridx = 1;
            ic.gridy = 0;
            ic.weightx = 0.2;
            ic.weighty = 1.0;
            ic.anchor = GridBagConstraints.EAST;

            panel.add(new JLabel("Process ID:"), ic);

            ic.gridx = 3;
            panel.add(new JLabel("Arrival Time:"), ic);

            ic.gridx = 5;
            panel.add(new JLabel("Burst Time:"), ic);

            ic.gridx = 7;
            panel.add(new JLabel("Priority:"), ic);

            ic.gridx = 2;
            ic.weightx = 0.5;
            //ic.ipadx = 100;
            ic.insets = new Insets(0, 5, 0, 10); //adds padding
            ic.fill = GridBagConstraints.HORIZONTAL;
            panel.add(processIdField, ic);

            ic.gridx = 4;
            panel.add(arrivalField, ic);
            
            ic.gridx = 6;
            panel.add(burstField, ic);

            ic.gridx = 8;
            panel.add(priorityField, ic);

            JPanel emptyPanel2 = new JPanel();
            ic.gridx = 9;
            ic.gridy = 0;
            ic.weightx = 0.5;
            ic.ipadx = 0;
            panel.add(emptyPanel2, ic);
        }
        else if (algo == "Round Robin") {
            // Add buttons for RR algorithm
            ic.gridx = 1;
            ic.gridy = 0;
            ic.weightx = 0.2;
            ic.weighty = 1.0;
            ic.anchor = GridBagConstraints.EAST;

            panel.add(new JLabel("Process ID:"), ic);

            ic.gridx = 3;
            panel.add(new JLabel("Arrival Time:"), ic);

            ic.gridx = 5;
            panel.add(new JLabel("Burst Time:"), ic);

            ic.gridx = 7;
            panel.add(new JLabel("Quantum Time:"), ic);

            ic.gridx = 2;
            ic.weightx = 0.5;
            //ic.ipadx = 100;
            ic.insets = new Insets(0, 5, 0, 10); //adds padding
            ic.fill = GridBagConstraints.HORIZONTAL;
            panel.add(processIdField, ic);

            ic.gridx = 4;
            panel.add(arrivalField, ic);
            
            ic.gridx = 6;
            panel.add(burstField, ic);

            ic.gridx = 8;
            panel.add(quantumField, ic);

            JPanel emptyPanel2 = new JPanel();
            ic.gridx = 9;
            ic.gridy = 0;
            ic.weightx = 0.5;
            ic.ipadx = 0;
            panel.add(emptyPanel2, ic);
        }
        else {
            // Add buttons for other algorithms
            ic.gridx = 1;
            ic.gridy = 0;
            ic.weightx = 0.2;
            ic.weighty = 1.0;
            ic.anchor = GridBagConstraints.EAST;

            panel.add(new JLabel("Process ID:"), ic);

            ic.gridx = 3;
            panel.add(new JLabel("Arrival Time:"), ic);

            ic.gridx = 5;
            panel.add(new JLabel("Burst Time:"), ic);

            ic.gridx = 2;
            ic.weightx = 0.5;
            //ic.ipadx = 100;
            ic.insets = new Insets(0, 5, 0, 10); //adds padding
            ic.fill = GridBagConstraints.HORIZONTAL;
            panel.add(processIdField, ic);

            ic.gridx = 4;
            panel.add(arrivalField, ic);
            
            ic.gridx = 6;
            panel.add(burstField, ic);

            JPanel emptyPanel2 = new JPanel();
            ic.gridx = 7;
            ic.gridy = 0;
            ic.weightx = 0.5;
            ic.ipadx = 0;
            panel.add(emptyPanel2, ic);
        }

        ic.insets = new Insets(10, 0, 10, 0); //adds padding
        ic.gridx = 0;
        ic.gridy = 0;
        // ic.weightx = 0.5;
        ic.fill = GridBagConstraints.NONE;
        ic.ipadx = 30;
        ic.anchor = GridBagConstraints.EAST;
        // ic.insets = new Insets(0, 15, 0, 10); //adds padding
        buttonPanel.add(addButton, ic);

        ic.anchor = GridBagConstraints.CENTER;
        JButton visualizeButton = new JButton("Visualize");

        visualizeButton.addActionListener(e -> {
            int size;

            if (algo == "Non-Preemptive Priority") {
                size = p_processes.size();
            }
            else if (algo == "Round Robin") {
                size = rr_processes.size();
            }
            else {
                size = processes.size();
            }
            
            if (size >= 3 && size <= 10) {
                visualize(algo);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });
        
        ic.gridx = 1;
        ic.weightx = 0.1;
        buttonPanel.add(visualizeButton, ic);

        JButton resultButton = new JButton("Result Table");
        // resultButton.addActionListener(e -> visualize());
        ic.gridx = 2;
        ic.weightx = 0.5;
        ic.anchor = GridBagConstraints.WEST;
        buttonPanel.add(resultButton, ic);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void addProcess(String algo, String id, int arrival, int burst, int priority, int quantum) {
        try {
            // Checks if the process ID already exists
            boolean idExists = processes.stream().anyMatch(p -> p.getId().equals(id));

            if (!idExists) {
                if (algo == "Non-Preemptive Priority") {
                    p_processes.add(new PProcess(id, arrival, burst, priority));
                }
                else if (algo == "Round Robin") {
                    rr_processes.add(new RRProcess(id, arrival, burst, quantum));
                }
                else {
                    processes.add(new Process(id, arrival, burst));
                }
                
                // Calling updateTable to update the process info
                updateTable(algo);
                // JOptionPane.showMessageDialog(this, "Process " + id + " is added!");
            }
            else {
                JOptionPane.showMessageDialog(this, "ERROR: Process " + id + " already exists!");
            }

            createInputPanel(algo);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void visualize(String algo) {
        if (algo == "FCFS") {
            FCFSScheduler scheduler = new FCFSScheduler(processes, p_processes, rr_processes);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart(); // Store the Gantt chart data
        }
        else if (algo == "SRT") {
            SRTScheduler scheduler = new SRTScheduler(processes, p_processes, rr_processes);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart(); // Store the Gantt chart data
        }
        else if (algo == "SJN") {
            SJNScheduler scheduler = new SJNScheduler(processes, p_processes, rr_processes);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart(); // Store the Gantt chart data
        }
        else if (algo == "Non-Preemptive Priority") {
            NonPPScheduler scheduler = new NonPPScheduler(processes, p_processes, rr_processes);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart(); // Store the Gantt chart data
        }
        else if (algo == "Round Robin") {
            RRScheduler scheduler = new RRScheduler(processes, p_processes, rr_processes);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart(); // Store the Gantt chart data
        }

        // Dynamically adjust ganttPanel size based on chart content
        int totalWidth = ganttChart.stream()
            .mapToInt(segment -> (Integer.parseInt(segment[1]) - Integer.parseInt(segment[0])) * 50)
            .sum();
        int panelHeight = 100; // Fixed height for Gantt chart
        ganttPanel.setPreferredSize(new Dimension(totalWidth, panelHeight));

        ganttPanel.revalidate(); // Refresh the panel
        ganttPanel.repaint(); // Redraw the Gantt chart with updated data
    }

    // try to delete the String algo parameter if not needed later
    private void drawGanttChart(Graphics g, String algo) {
        int x = 50, y = 50, height = 50;
        int start = 0, end = 0;

        for (String[] segment : ganttChart) {
            start = Integer.parseInt(segment[0]);
            end = Integer.parseInt(segment[1]);
            String processId = segment[2];

            int width = (end - start) * 40;

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawString(processId, x + width / 2 - 10, y + height / 2);
            g.drawString(String.valueOf(start), x - 10, y + height + 15);
            x += width;
        }

        if (end != 0) {
            g.drawString(String.valueOf(end), x, y + height + 15);
        }
    }

    private void updateTable(String algo) {
        String[] columnNames = {"Process", "Arrival Time", "Burst Time"};
        String[] npp_columnNames = {"Process", "Arrival Time", "Burst Time", "Priority"};
        String[] rr_columnNames = {"Process", "Arrival Time", "Burst Time", "Quantum Time"};
        // String[][] data = new String[processes.size()][columnNames.length];

        JButton removeButton = new JButton("Remove Process");
        JButton removeAllButton = new JButton("Remove All Process");

        if (algo == "Non-Preemptive Priority") {
            tableModel = new DefaultTableModel(npp_columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // All cells are non-editable
                }
            };
    
            for (PProcess process : p_processes) {
                String[] row = {
                    process.getId(),
                    String.valueOf(process.getArrivalTime()),
                    String.valueOf(process.getBurstTime()),
                    String.valueOf(process.getPriority())
                };
                tableModel.addRow(row);
            }
        }
        else if (algo == "Round Robin") {
            tableModel = new DefaultTableModel(rr_columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // All cells are non-editable
                }
            };
    
            for (RRProcess process : rr_processes) {
                String[] row = {
                    process.getId(),
                    String.valueOf(process.getArrivalTime()),
                    String.valueOf(process.getBurstTime()),
                    String.valueOf(process.getQuantumTime())
                };
                tableModel.addRow(row);
            }
        }
        else {
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // All cells are non-editable
                }
            };
    
            for (Process process : processes) {
                String[] row = {
                    process.getId(),
                    String.valueOf(process.getArrivalTime()),
                    String.valueOf(process.getBurstTime())
                };
                tableModel.addRow(row);
            }
        }

        removeButton.addActionListener(e -> {
            int selectedRow = processInfoTable.getSelectedRow();
            if (selectedRow != -1) {
                String removedProcessId = (String) processInfoTable.getValueAt(selectedRow, 0); //problem here
                if (algo == "Non-Preemptive Priority") {
                    p_processes.remove(selectedRow);
                }
                else if (algo == "Round Robin") {
                    rr_processes.remove(selectedRow);
                }
                else {
                    processes.remove(selectedRow);
                }
                
                updateTable(algo); // Update the table after removing a process
                JOptionPane.showMessageDialog(this, "Process " +  removedProcessId + " removed!");
            } else {
                JOptionPane.showMessageDialog(this, "ERROR: No process selected!");
            }

            updateTable(algo); // Update the table after removing a process
        });

        removeAllButton.addActionListener(e -> {
            if (processes.isEmpty() && p_processes.isEmpty() && rr_processes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No processes to remove!");
            } else {
                if (algo == "Non-Preemptive Priority") {
                    p_processes.clear(); // Clear all processes
                }
                else if (algo == "Round Robin") {
                    rr_processes.clear(); // Clear all processes
                }
                else {
                    processes.clear(); // Clear all processes
                }
                
                updateTable(algo); // Refresh the table to show an empty state
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

    // public static void main(String[] args) {
    //     new CPUVisualizer();
    // }
}
