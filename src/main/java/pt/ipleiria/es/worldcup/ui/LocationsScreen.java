package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationsScreen extends JPanel {
    private JPanel designerPanel;
    private static final String SEARCH_PLACEHOLDER = "Pesquisar país, estádio ou cidade...";

    private JTextField searchInput;
    private JComboBox<String> countryFilter;
    private JPanel locationsGrid;

    public LocationsScreen() {
        setLayout(new BorderLayout());
        setBackground(FifaUiKit.BACKGROUND);
        add(FifaUiKit.sidebar("Locações"), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);
    }

    private JPanel createWorkspace() {
        searchInput = new JTextField();
        countryFilter = FifaUiKit.combo(new String[]{"Todos os países", "Canadá", "Estados Unidos", "México"});
        countryFilter.addActionListener(event -> renderLocations());
        FifaUiKit.onTextChange(searchInput, this::renderLocations);

        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(FifaUiKit.BACKGROUND);
        workspace.add(FifaUiKit.topbar(searchInput, countryFilter, SEARCH_PLACEHOLDER), BorderLayout.NORTH);
        workspace.add(createContent(), BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(FifaUiKit.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(14, 22, 20, 22));

        JLabel title = FifaUiKit.label("LOCAÇÕES DO CAMPEONATO DO MUNDO", 16, Font.BOLD, FifaUiKit.TEXT);
        content.add(title, BorderLayout.NORTH);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(FifaUiKit.PANEL_SOFT);
        card.setBorder(BorderFactory.createCompoundBorder(new FifaUiKit.RoundedBorder(16, FifaUiKit.LINE), BorderFactory.createEmptyBorder(20, 28, 20, 28)));

        locationsGrid = new JPanel(new GridBagLayout());
        locationsGrid.setOpaque(false);
        card.add(locationsGrid, BorderLayout.CENTER);
        content.add(card, BorderLayout.CENTER);
        renderLocations();
        return content;
    }

    private void renderLocations() {
        if (locationsGrid == null) {
            return;
        }

        locationsGrid.removeAll();
        List<LocationGroup> groups = filteredGroups();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 14, 0, 14);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;

        if (groups.isEmpty()) {
            JLabel empty = FifaUiKit.label("Não foram encontradas locações.", 14, Font.BOLD, FifaUiKit.MUTED);
            locationsGrid.add(empty);
        } else {
            for (int i = 0; i < groups.size(); i++) {
                gbc.gridx = i;
                locationsGrid.add(locationColumn(groups.get(i)), gbc);
            }
        }

        locationsGrid.revalidate();
        locationsGrid.repaint();
    }

    private JPanel locationColumn(LocationGroup group) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 8));
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new Dimension(292, preferredColumnHeight(group)));

        JLabel heading = FifaUiKit.label(group.country().toUpperCase(Locale.ROOT), 14, Font.BOLD, FifaUiKit.TEXT);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        wrapper.add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(FifaUiKit.CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(new FifaUiKit.RoundedBorder(10, new Color(0x3C1A7A)), BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        JLabel flag = new JLabel(FifaUiKit.flag(group.flagCode(), 220, 104));
        flag.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(flag, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        for (Location location : group.locations()) {
            list.add(locationRow(location));
            list.add(Box.createRigidArea(new Dimension(0, 12)));
        }
        panel.add(list, BorderLayout.CENTER);
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }

    private int preferredColumnHeight(LocationGroup group) {
        return Math.min(520, Math.max(330, 170 + group.locations().size() * 46));
    }

    private JPanel locationRow(Location location) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel marker = new JLabel(FifaUiKit.markerIcon());
        marker.setVerticalAlignment(SwingConstants.TOP);
        row.add(marker, BorderLayout.WEST);

        JLabel text = FifaUiKit.label("<html><b>" + location.stadium() + "</b><br>" + location.city() + "<br>" + location.capacity() + " lugares</html>", 11, Font.PLAIN, FifaUiKit.TEXT);
        row.add(text, BorderLayout.CENTER);
        return row;
    }

    private List<LocationGroup> filteredGroups() {
        String selected = String.valueOf(countryFilter.getSelectedItem());
        String query = searchText();
        List<LocationGroup> result = new ArrayList<>();

        for (LocationGroup group : mockGroups()) {
            if (!"Todos os países".equals(selected) && !group.country().equals(selected)) {
                continue;
            }

            List<Location> locations = group.locations().stream()
                    .filter(location -> query.isBlank()
                            || group.country().toLowerCase(Locale.ROOT).contains(query)
                            || location.stadium().toLowerCase(Locale.ROOT).contains(query)
                            || location.city().toLowerCase(Locale.ROOT).contains(query))
                    .toList();
            if (!locations.isEmpty()) {
                result.add(new LocationGroup(group.country(), group.flagCode(), locations));
            }
        }
        return result;
    }

    private String searchText() {
        String text = searchInput.getText().trim().toLowerCase(Locale.ROOT);
        return SEARCH_PLACEHOLDER.toLowerCase(Locale.ROOT).equals(text) ? "" : text;
    }

    private List<LocationGroup> mockGroups() {
        return List.of(
                new LocationGroup("Canadá", "CAN", List.of(
                        new Location("BC Place", "Vancouver, Colúmbia Britânica", "48 821"),
                        new Location("BMO Field", "Toronto, Ontário", "44 315")
                )),
                new LocationGroup("Estados Unidos", "USA", List.of(
                        new Location("MetLife Stadium", "East Rutherford, Nova Jérsia", "78 576"),
                        new Location("Lumen Field", "Seattle, Washington", "65 123"),
                        new Location("Mercedes-Benz Stadium", "Atlanta, Geórgia", "67 302"),
                        new Location("AT&T Stadium", "Arlington, Texas", "94 000"),
                        new Location("NRG Stadium", "Houston, Texas", "68 311"),
                        new Location("Levi's Stadium", "Santa Clara, Califórnia", "69 901"),
                        new Location("SoFi Stadium", "Inglewood, Califórnia", "69 650"),
                        new Location("Hard Rock Stadium", "Miami Gardens, Florida", "64 091"),
                        new Location("Gillette Stadium", "Foxborough, Massachusetts", "63 815"),
                        new Location("Arrowhead Stadium", "Kansas City, Missouri", "67 513"),
                        new Location("Lincoln Financial Field", "Filadélfia, Pensilvânia", "65 827")
                )),
                new LocationGroup("México", "MEX", List.of(
                        new Location("Estádio Azteca", "Cidade do México", "72 766"),
                        new Location("Estádio Akron", "Zapopan, Jalisco", "44 330"),
                        new Location("Estádio BBVA", "Guadalupe, Nuevo León", "50 113")
                ))
        );
    }

    private record LocationGroup(String country, String flagCode, List<Location> locations) {
    }

    private record Location(String stadium, String city, String capacity) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Mantém o look and feel padrão caso o do sistema não esteja disponível.
            }
            JFrame frame = new JFrame("FIFA - Locações");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.setSize(1366, 768);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new LocationsScreen());
            frame.setVisible(true);
        });
    }
}
