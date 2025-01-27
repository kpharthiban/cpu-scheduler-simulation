import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Main extends JFrame {
    private JFrame frame;
    private JPanel inputPanel;
    private JPanel resultButtonPanel;
    private JPanel inputTitle;

    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JTable processInfoTable;

    public static List<Process> processes = new ArrayList<>(); // Store Processes data

    public Main() {
        SwingUtilities.invokeLater(() -> {
            createMainWindow();
        });
    }
       
    private void createMainWindow () {
        frame = new JFrame();
        frame.setTitle("CPU Scheduling Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(new Dimension(850, 750));
        frame.setMinimumSize(new Dimension(850, 850));
    
        inputPanel = createInputPanel();

        resultButtonPanel = createResultButtonPanel(); // To add buttons that will display the results of each algorithm visualizer

        GridBagConstraints c = new GridBagConstraints();

        JLabel inputTitleLabel = new JLabel("CPU SCHEDULING VISUALIZER");
        inputTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inputTitleLabel.setForeground(new Color(26, 82, 118));
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

        // Create a panel to hold the table with padding
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add 20px padding (top, left, bottom, right)
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.5;
        c.fill = GridBagConstraints.BOTH;
        frame.add(tableContainer, c);

        updateTable();

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.4;
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
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113)); 
        addButton.setForeground(Color.WHITE); // White text
        addButton.setFocusPainted(false); // Remove focus border
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding

        
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

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(88, 214, 141)); // Lighter blue on hover
                addButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(46, 204, 113)); // Revert to original color
                addButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
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

        JButton rrButton = new JButton("Round Robin");
        rrButton.setFont(new Font("Arial", Font.BOLD, 14));
        rrButton.setBackground(new Color(59, 89, 182)); // Blue background
        rrButton.setForeground(Color.WHITE); // White text
        rrButton.setFocusPainted(false); // Remove focus border
        rrButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        JButton srtButton = new JButton("Shortest Remaining Time (SRT)");
        srtButton.setFont(new Font("Arial", Font.BOLD, 14));
        srtButton.setBackground(new Color(59, 89, 182)); // Blue background
        srtButton.setForeground(Color.WHITE); // White text
        srtButton.setFocusPainted(false); // Remove focus border
        srtButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        JButton sjnButton = new JButton("Shortest Job Next (SJN)");
        sjnButton.setFont(new Font("Arial", Font.BOLD, 14));
        sjnButton.setBackground(new Color(59, 89, 182)); // Blue background
        sjnButton.setForeground(Color.WHITE); // White text
        sjnButton.setFocusPainted(false); // Remove focus border
        sjnButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        JButton nppButton = new JButton("Non-Preemptive Priority");
        nppButton.setFont(new Font("Arial", Font.BOLD, 14));
        nppButton.setBackground(new Color(59, 89, 182)); // Blue background
        nppButton.setForeground(Color.WHITE); // White text
        nppButton.setFocusPainted(false); // Remove focus border
        nppButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        rrButton.addActionListener(e -> {
            int size;
            size = processes.size();
            if (size >= 3 && size <= 10) {
                String algo = "Round Robin";
                new CPUVisualizer(algo, processes);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });

        rrButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rrButton.setBackground(new Color(89, 119, 212)); // Lighter blue on hover
                rrButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rrButton.setBackground(new Color(59, 89, 182)); // Revert to original color
                rrButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });

        srtButton.addActionListener(e -> {
            int size;
            size = processes.size();
            if (size >= 3 && size <= 10) {
                String algo = "Shortest Remaining Time (SRT)";
                new CPUVisualizer(algo, processes);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });

        srtButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                srtButton.setBackground(new Color(89, 119, 212)); // Lighter blue on hover
                srtButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                srtButton.setBackground(new Color(59, 89, 182)); // Revert to original color
                srtButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });

        sjnButton.addActionListener(e -> {
            int size;
            size = processes.size();
            if (size >= 3 && size <= 10) {
                String algo = "Shortest Job Next (SJN)";
                new CPUVisualizer(algo, processes);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });

        sjnButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sjnButton.setBackground(new Color(89, 119, 212)); // Lighter blue on hover
                sjnButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sjnButton.setBackground(new Color(59, 89, 182)); // Revert to original color
                sjnButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });

        nppButton.addActionListener(e -> {
            int size;
            size = processes.size();
            if (size >= 3 && size <= 10) {
                String algo = "Non-Preemptive Priority";
                new CPUVisualizer(algo, processes);
            } else {
                JOptionPane.showMessageDialog(this, "At least 3 & at most 10 processes should be added.");
            }
        });

        nppButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nppButton.setBackground(new Color(89, 119, 212)); // Lighter blue on hover
                nppButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nppButton.setBackground(new Color(59, 89, 182)); // Revert to original color
                nppButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });
        
        JLabel selectionTitleLabel = new JLabel("Select an Algorithm to View Result");
        selectionTitleLabel.setForeground(new Color(59, 89, 182)); // Blue background
        selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        selectionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment inside the label

        JLabel emptyLabelLeft = new JLabel();
        JLabel emptyLabelRight = new JLabel();

        gbc.gridx = 0;
        gbc.weightx = 0.2;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        algoPanel.add(emptyLabelLeft, gbc);

        gbc.gridx = 2;
        algoPanel.add(emptyLabelRight, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 15, 10); // Adjust padding
        algoPanel.add(selectionTitleLabel, gbc);

        gbc.gridy = 1;
        gbc.ipadx = 200;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // Adjust padding
        // gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        algoPanel.add(rrButton, gbc);

        gbc.gridy = 2;
        // gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        algoPanel.add(srtButton, gbc);

        gbc.gridy = 3;
        // gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        algoPanel.add(sjnButton, gbc);

        gbc.gridy = 4;
        // gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        gbc.insets = new Insets(5, 10, 15, 10); // Adjust padding
        algoPanel.add(nppButton, gbc);

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
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setBackground(new Color(231, 76, 60)); // Blue background
        removeButton.setForeground(Color.WHITE); // White text
        removeButton.setFocusPainted(false); // Remove focus border
        removeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding

        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(203, 67, 53)); // Lighter blue on hover
                removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(231, 76, 60)); // Revert to original color
                removeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });

        JButton removeAllButton = new JButton("Remove All Process");
        removeAllButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeAllButton.setBackground(new Color(231, 76, 60)); // Blue background
        removeAllButton.setForeground(Color.WHITE); // White text
        removeAllButton.setFocusPainted(false); // Remove focus border
        removeAllButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding

        removeAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeAllButton.setBackground(new Color(203, 67, 53)); // Lighter blue on hover
                removeAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeAllButton.setBackground(new Color(231, 76, 60)); // Revert to original color
                removeAllButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor to default
            }
        });

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
        JTable processInfoTable = new JTable(tableModel);
        processInfoTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Set table font
        processInfoTable.setRowHeight(25); // Increase row height for better readability
        processInfoTable.setGridColor(Color.LIGHT_GRAY); // Set grid color
        processInfoTable.setEnabled(false);

        // Center-align data in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment
        for (int i = 0; i < processInfoTable.getColumnCount(); i++) {
            processInfoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Customize header
        JTableHeader header = processInfoTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Bold header font
        header.setBackground(new Color(60, 63, 65)); // Dark background
        header.setForeground(Color.WHITE); // White text for contrast
        header.setReorderingAllowed(false); // Prevent column reordering
        header.setResizingAllowed(true); // Allow resizing (optional)

        // Add zebra striping to rows
        processInfoTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                        boolean isSelected, boolean hasFocus,
                                                        int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(245, 245, 245)); // Light gray for even rows
                    } else {
                        c.setBackground(Color.WHITE); // White for odd rows
                    }
                } else {
                    c.setBackground(new Color(173, 216, 230)); // Light blue when selected
                }
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(processInfoTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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
        new Main();
    }
}
