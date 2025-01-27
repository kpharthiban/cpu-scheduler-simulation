import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class CPUVisualizer extends JFrame {

    private List<Process> processes = new ArrayList<>();
    private List<String[]> ganttChart = new ArrayList<>();
    private JFrame frame;

    private JPanel inputTitle;
    private JPanel ganttPanel;
    private DefaultTableModel tableModel; // Declare tableModel here

    public CPUVisualizer(String algo, List<Process> processes) {
        this.processes = processes;
        SwingUtilities.invokeLater(() -> {
            createGUI(algo);
        });
    }

    private void createGUI(String algo) {
        frame = new JFrame();
        frame.setTitle(algo + " Algorithm Scheduling Visualizer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ganttChart.clear();
            }
        });
    
        GridBagConstraints c = new GridBagConstraints();
    
        // Title Section
        JLabel inputTitleLabel = new JLabel(algo + " - Algorithm Visualizer");
        inputTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inputTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputTitle = new JPanel(new BorderLayout());
        inputTitle.add(inputTitleLabel, BorderLayout.CENTER);
    
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frame.add(inputTitle, c);

        JLabel ganttTitle = new JLabel("Gantt Chart");
        ganttTitle.setFont(new Font("Arial", Font.BOLD, 16)); 

        // Set a border with padding
        ganttTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 0), // Outer border
            BorderFactory.createEmptyBorder(10, 10, 10, 10)    // Inner padding
        ));

        // Center-align the text
        ganttTitle.setHorizontalAlignment(SwingConstants.CENTER);
        ganttTitle.setVerticalAlignment(SwingConstants.CENTER);
            
        // Gantt Chart Section
        ganttPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGanttChart(g, algo);
            }
        };
        ganttPanel.setBackground(Color.WHITE);
        ganttPanel.setPreferredSize(new Dimension(800, 500));
        ganttPanel.setLayout(new BorderLayout());
        ganttPanel.add(ganttTitle, BorderLayout.NORTH);
        JScrollPane ganttScrollPane = new JScrollPane(ganttPanel);
        ganttScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ganttScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.6;
        c.fill = GridBagConstraints.BOTH;
        frame.add(ganttScrollPane, c);
    
        // Metrics Table Section
        String[] columnNames = {"Process", "Arrival Time", "Burst Time", "Finishing Time", "Turnaround Time", "Waiting Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // JTable metricsTable = new JTable(tableModel);
        // JScrollPane tableScrollPane = new JScrollPane(metricsTable);
    
        JPanel metricsPanel = new JPanel(new BorderLayout());
        metricsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 0),
            "Result Table",
            TitledBorder.CENTER,  // Align the title in the center
            TitledBorder.DEFAULT_POSITION, // You can adjust the position if needed
            new Font("Arial", Font.BOLD, 16)
        ));

        // Create and style the JTable
        JTable metricsTable = new JTable(tableModel);
        metricsTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Set table font
        metricsTable.setRowHeight(25); // Increase row height for better readability
        metricsTable.setGridColor(Color.LIGHT_GRAY); // Set grid color
        metricsTable.setEnabled(false);

        // Center-align data in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment
        for (int i = 0; i < metricsTable.getColumnCount(); i++) {
            metricsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Customize header
        JTableHeader header = metricsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Bold header font
        header.setBackground(new Color(60, 63, 65)); // Dark background
        header.setForeground(Color.WHITE); // White text for contrast
        header.setReorderingAllowed(false); // Prevent column reordering
        header.setResizingAllowed(true); // Allow resizing (optional)

        // Add zebra striping to rows
        metricsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Wrap table in JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(metricsTable);
        // tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        // Create a panel to hold the table with padding
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add 20px padding (top, left, bottom, right)
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);

        // Add tableContainer to the metricsPanel
        metricsPanel.add(tableContainer, BorderLayout.CENTER);
    
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 0, 0); // Add spacing above the panel
        c.fill = GridBagConstraints.BOTH;
        frame.add(metricsPanel, c);

        // Finalize Frame
        visualize(algo); // Update the Gantt chart and table
    
        // Average Values Section
        double avgTurnaroundTime = calculateAverageTurnaroundTime();
        double avgWaitingTime = calculateAverageWaitingTime();
    
        JLabel avgTurnaroundLabel = new JLabel("Average Turnaround Time: " + String.format("%.2f", avgTurnaroundTime));
        JLabel avgWaitingLabel = new JLabel("Average Waiting Time: " + String.format("%.2f", avgWaitingTime));
        avgTurnaroundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        avgWaitingLabel.setFont(new Font("Arial", Font.BOLD, 16));
    
        avgTurnaroundLabel.setForeground(new Color(34, 139, 34)); // Green text
        avgWaitingLabel.setForeground(new Color(34, 139, 34)); // Green text
    
        JPanel avgPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        avgPanel.setBorder(new EmptyBorder(10, 30, 10, 10));
        avgPanel.add(avgTurnaroundLabel);
        avgPanel.add(avgWaitingLabel);
    
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.1;
        c.insets = new Insets(10, 0, 20, 0); // Add extra space beneath
        c.fill = GridBagConstraints.BOTH;
        frame.add(avgPanel, c);
    
        frame.setSize(800, 700);
        frame.setVisible(true);
    }
    

    private void visualize(String algo) {
        // List<Process> processesCopy = new ArrayList<>();
        List<Process> processesCopy = new ArrayList<>(processes);


        if (algo.equals("Shortest Remaining Time (SRT)")) {
            SRTScheduler scheduler = new SRTScheduler(processesCopy);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart();
        } else if (algo.equals("Shortest Job Next (SJN)")) {
            SJNScheduler scheduler = new SJNScheduler(processesCopy);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart();
        } else if (algo.equals("Non-Preemptive Priority")) {
            NonPPScheduler scheduler = new NonPPScheduler(processesCopy);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart();
        } else if (algo.equals("Round Robin")) {
            RRScheduler scheduler = new RRScheduler(processesCopy);
            scheduler.schedule();
            ganttChart = scheduler.getGanttChart();
        }

        int totalWidth = ganttChart.stream()
            .mapToInt(segment -> (Integer.parseInt(segment[1]) - Integer.parseInt(segment[0])) * 50)
            .sum();
        int panelHeight = 100;
        ganttPanel.setPreferredSize(new Dimension(totalWidth, panelHeight));

        ganttPanel.revalidate();
        ganttPanel.repaint();

        // Update the metrics table
        updateMetricsTable();
    }

    private void updateMetricsTable() {
        tableModel.setRowCount(0); // Clear existing rows

        for (Process process : processes) {
            int turnaroundTime = process.getCompletionTime() - process.getArrivalTime();
            int waitingTime = turnaroundTime - process.getBurstTime();

            tableModel.addRow(new Object[]{
                process.getId(),
                process.getArrivalTime(),
                process.getBurstTime(),
                process.getCompletionTime(),
                turnaroundTime,
                waitingTime
            });

            System.out.println("Process: " + process.getId() + ", Completion Time: " + process.getCompletionTime());
            
            System.out.println("Process: " + process.getId() + ", Completion Time: " + process.getCompletionTime() +
                ", Arrival Time: " + process.getArrivalTime() + ", Burst Time: " + process.getBurstTime());
            
            System.out.println("Turnaround Time for " + process.getId() + ": " + turnaroundTime);
            System.out.println("Waiting Time for " + process.getId() + ": " + waitingTime);
        }
    }

    private double calculateAverageTurnaroundTime() {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += (process.getCompletionTime() - process.getArrivalTime());
        }
        return (double) totalTurnaroundTime / processes.size();
    }

    private double calculateAverageWaitingTime() {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += (process.getCompletionTime() - process.getArrivalTime() - process.getBurstTime());
        }
        return (double) totalWaitingTime / processes.size();
    }

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

        if (ganttChart.size() > 1000) {
            System.err.println("Gantt chart is too large. Truncating to prevent memory issues.");
            ganttChart = ganttChart.subList(0, 1000);
        }

        if (end != 0) {
            g.drawString(String.valueOf(end), x, y + height + 15);
        }
    }
}