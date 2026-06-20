import java.awt.*;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.sql.*;
import java.util.Map;
import javax.swing.*;

public class BUI extends JFrame {

    private JComboBox<String> subjectCodeBox;
    private JTextField linkField;
    private JButton linkButton;
    private JButton lookupButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/EDUSHARE?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Aditya@09022006";

    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_HOVER_COLOR = new Color(40, 40, 40);
    private static final Color INPUT_BG = Color.BLACK;

    private String currentLink = null;

    // Course code list
    private final String[] courseCodes = {
        "GAMAT101", "GAMAT201", "UCEST206", "UCEST105",
        "GXCYT122", "GXEST104", "GXEST203", "GAPHT121"
    };

    public BUI() {
        setTitle("🎓 EduShare Resource Portal");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== FONT STYLE =====
        Font baseFont = new Font("Segoe UI", Font.PLAIN, 16);
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) baseFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font underlinedFont = baseFont.deriveFont(attributes); // underlined font
        Font normalFont = new Font("Segoe UI", Font.PLAIN, 16); // normal (not underlined) font

        // ===== Background Image =====
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\ADMIN\\OneDrive\\Desktop\\ChatGPT Image Oct 17, 2025, 11_36_09 PM.png");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // ===== Components =====
        subjectCodeBox = new JComboBox<>(courseCodes);
        subjectCodeBox.setEditable(false);
        subjectCodeBox.setPreferredSize(new Dimension(220, 35));

        // Custom renderer
        subjectCodeBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setForeground(Color.BLACK);
                setBackground(Color.WHITE);
                setFont(normalFont);
                return this;
            }
        });

        linkField = new JTextField(35);
        linkField.setEditable(false);
        linkField.setForeground(TEXT_COLOR);
        linkField.setBackground(INPUT_BG);
        linkField.setFont(normalFont);
        linkField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        lookupButton = new JButton("LOOKUP");
        styleButton(lookupButton, normalFont);

        linkButton = new JButton("LINK");
        styleButton(linkButton, normalFont);
        linkButton.setEnabled(false);

        JLabel label = new JLabel("Enter Subject Code:");
        label.setForeground(Color.BLACK);
        label.setFont(normalFont);

        JLabel linkLabel = new JLabel("Resource Link:");
        linkLabel.setForeground(Color.BLACK);
        linkLabel.setFont(normalFont);

        // ===== Big Title Label with emoji support =====
        JLabel titleLabel = new JLabel("🎓 EDU SHARE RESOURCE PORTAL", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22)); // Emoji + Bold
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // ===== Layout =====
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(subjectCodeBox, gbc);

        gbc.gridx = 2;
        panel.add(lookupButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        panel.add(linkLabel, gbc);

        gbc.gridy = 3;
        panel.add(linkField, gbc);

        gbc.gridy = 4; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(linkButton, gbc);

        background.add(panel);

        // ===== Event Handling =====
        lookupButton.addActionListener(e -> lookupLink());
        linkButton.addActionListener(e -> openLinkInBrowser());
    }

    private void styleButton(JButton button, Font styledFont) {
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(styledFont);
        button.setPreferredSize(new Dimension(100, 35));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    private void lookupLink() {
        String selectedCode = (String) subjectCodeBox.getSelectedItem();

        String link = getResourceLink(selectedCode);
        linkField.setText(link != null ? link : "");
        if (link != null) {
            currentLink = link;
            linkButton.setEnabled(true);
        } else {
            currentLink = null;
            linkButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "No resource found for subject code: " + selectedCode,
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getResourceLink(String courseId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT resource_link FROM edutable WHERE course_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, courseId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("resource_link");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "MySQL JDBC Driver not found! Make sure the jar is in lib folder.",
                    "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + e.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void openLinkInBrowser() {
        if (currentLink != null) {
            try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome \"" + currentLink + "\""});
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", "Google Chrome", currentLink});
                } else {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(currentLink));
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Cannot open link. Copy manually:\n" + currentLink,
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Failed to open link: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new BUI().setVisible(true);
        });
    }
}
