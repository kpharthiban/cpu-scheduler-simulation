import java.awt.*;

import javax.swing.*;

public class MainCPUSchedulingVisualizer extends JFrame {
    private JFrame frame;
    private JPanel panel;

    public MainCPUSchedulingVisualizer() {
        SwingUtilities.invokeLater(() -> {
            createMainWindow();
        });
    }

    private void createMainWindow() {
        frame = new JFrame();
        frame.setTitle("CPU Scheduling Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(450, 400);
        frame.setVisible(true);
    
        GridBagConstraints c = new GridBagConstraints();
    
        // Title Label
        JLabel inputTitleLabel = new JLabel("CPU Scheduling Visualizer");
        inputTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inputTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment inside the label
    
        // Subtitle Label
        JLabel inputSubtitleLabel = new JLabel("Select an algorithm");
        inputSubtitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputSubtitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment inside the label
    
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    
        // Title Constraints
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 1, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(inputTitleLabel, c);
    
        // Subtitle Constraints
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(1, 10, 20, 10); // Adjust padding
        c.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal stretching
        c.anchor = GridBagConstraints.CENTER; // Center alignment
        panel.add(inputSubtitleLabel, c);
    
        // Add the panel to the frame
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH; // Fill the entire space
        c.anchor = GridBagConstraints.CENTER; // Center the panel
        frame.add(panel, c);

        JButton fcfsButton = new JButton("First-Come First-Served (FCFS)");
        fcfsButton.setFont(new Font("Arial", Font.BOLD, 14));
        fcfsButton.addActionListener(e -> new CPUVisualizer("FCFS"));
        c.gridx = 0;
        c.gridy = 1;
        // c.weightx = 0.1;
        c.ipadx = 125;
        c.insets = new Insets(5, 10, 5, 10); // Adjust padding
        c.fill = GridBagConstraints.HORIZONTAL; // Fill the entire space
        frame.add(fcfsButton, c);

        JButton srtButton = new JButton("Shortest Remaining Time (SRT)");
        srtButton.setFont(new Font("Arial", Font.BOLD, 14));
        srtButton.addActionListener(e -> new CPUVisualizer("SRT"));
        c.gridx = 0;
        c.gridy = 2;
        // c.weightx = 0.1;
        frame.add(srtButton, c);

        JButton sjnButton = new JButton("Shortest Job Next (SJN)");
        sjnButton.setFont(new Font("Arial", Font.BOLD, 14));
        sjnButton.addActionListener(e -> new CPUVisualizer("SJN"));
        c.gridx = 0;
        c.gridy = 3;
        // c.weightx = 0.1;
        frame.add(sjnButton, c);

        JButton nonppButton = new JButton("Non-Preemptive Priority");
        nonppButton.setFont(new Font("Arial", Font.BOLD, 14));
        nonppButton.addActionListener(e -> new CPUVisualizer("Non-Preemptive Priority"));
        c.gridx = 0;
        c.gridy = 4;
        // c.weightx = 0.1;
        frame.add(nonppButton, c);

        JButton rrButton = new JButton("Round Robin");
        rrButton.setFont(new Font("Arial", Font.BOLD, 14));
        rrButton.addActionListener(e -> new CPUVisualizer("Round Robin"));
        c.gridx = 0;
        c.gridy = 5;
        // c.weightx = 0.1;
        c.insets = new Insets(5, 10, 20, 10); // Adjust padding
        frame.add(rrButton, c);
    }
    

    public static void main(String[] args) {
        new MainCPUSchedulingVisualizer();
    }
}
