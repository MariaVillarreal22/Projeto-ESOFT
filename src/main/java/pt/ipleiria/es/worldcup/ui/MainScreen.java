package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public class MainScreen extends JPanel {
    public MainScreen() {
        setBackground(AppTheme.BACKGROUND);
        setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));

        add(buildSidebar(), constraints(0, 0, 1, 1, GridConstraints.FILL_VERTICAL));
        add(buildMainArea(), constraints(0, 1, 1, 1, GridConstraints.FILL_BOTH));
    }

    private JPanel buildSidebar() {
        JPanel sidebar = panel(AppTheme.SIDEBAR, 15, 1, new Insets(14, 16, 14, 16), 0, 8);
        sidebar.setPreferredSize(new Dimension(256, 720));

        JLabel brand = label("FIFA\u00B0", AppTheme.TEXT, AppTheme.BRAND_FONT);
        sidebar.add(brand, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        addNavSection(sidebar, 1, "COMPETICOES", new String[]{"Fases", "Calendario", "Classificacoes", "Estatisticas"}, 0);
        addNavSection(sidebar, 3, "ENTIDADES", new String[]{"Equipas", "Arbitros", "Estadios"}, -1);
        addNavSection(sidebar, 5, "BILHETES", new String[]{"Comprar", "Tickets purchased"}, -1);
        addNavSection(sidebar, 7, "HOSPITALIDADE", new String[]{"Hoteis", "Locacoes"}, -1);

        sidebar.add(new Spacer(), constraints(14, 0, 1, 1, GridConstraints.FILL_BOTH));
        return sidebar;
    }

    private void addNavSection(JPanel parent, int row, String title, String[] links, int activeIndex) {
        JLabel heading = label(title, AppTheme.TEXT, AppTheme.SECTION_FONT);
        parent.add(heading, constraints(row, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel linksPanel = panel(AppTheme.SIDEBAR, links.length, 1, new Insets(0, 0, 12, 0), 0, 0);
        for (int i = 0; i < links.length; i++) {
            JLabel link = label(links[i], i == activeIndex ? AppTheme.TEXT : AppTheme.MUTED, AppTheme.BODY_FONT);
            linksPanel.add(link, constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        parent.add(linksPanel, constraints(row + 1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
    }

    private JPanel buildMainArea() {
        JPanel main = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 0);
        main.add(buildHeader(), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        main.add(buildContent(), constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return main;
    }

    private JPanel buildHeader() {
        JPanel header = panel(AppTheme.HEADER, 1, 4, new Insets(11, 30, 11, 30), 24, 0);
        header.setPreferredSize(new Dimension(900, 72));

        JButton menu = iconButton(AppIcons.menu());
        header.add(menu, constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));

        JPanel searchBox = whitePanel(1, 2, new Insets(0, 12, 0, 12), 8, 0);
        JLabel searchIcon = new JLabel(AppIcons.search());
        searchIcon.setForeground(new Color(0x111827));
        searchBox.add(searchIcon, constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        JTextField search = new JTextField("Pesquisar fase, equipa, estadio...");
        search.setForeground(new Color(0x111827));
        search.setBorder(BorderFactory.createEmptyBorder());
        searchBox.add(search, constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(searchBox, constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel team = whitePanel(1, 2, new Insets(0, 12, 0, 12), 10, 0);
        team.add(new JLabel(AppIcons.argentinaFlag()), constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        team.add(label("ARGENTINA", new Color(0x111827), AppTheme.BODY_FONT), constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(team, constraints(0, 2, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel date = whitePanel(1, 2, new Insets(0, 12, 0, 12), 10, 0);
        date.add(label("JUNE 2026", new Color(0x111827), AppTheme.BODY_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel calendar = new JLabel(AppIcons.calendar());
        calendar.setForeground(new Color(0x111827));
        date.add(calendar, constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        header.add(date, constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return header;
    }

    private JScrollPane buildContent() {
        JPanel content = panel(AppTheme.BACKGROUND, 4, 1, new Insets(16, 32, 42, 32), 0, 18);

        JPanel titleRow = panel(AppTheme.BACKGROUND, 1, 2, new Insets(0, 0, 0, 0), 16, 0);
        JPanel titleText = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titleText.add(label("ENGENHARIA DE SOFTWARE - PROJETO", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleText.add(label("FASES", AppTheme.TEXT, AppTheme.TITLE_FONT), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(titleText, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(primaryButton("Gerir calendario"), constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        content.add(titleRow, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        content.add(buildOverview(), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        content.add(new BracketPanel(), constraints(2, 0, 1, 1, GridConstraints.FILL_BOTH));
        content.add(buildModuleGrid(), constraints(3, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        return scrollPane;
    }

    private JPanel buildOverview() {
        JPanel overview = panel(AppTheme.BACKGROUND, 1, 4, new Insets(0, 0, 0, 0), 18, 0);
        String[][] cards = {
                {"Equipas", "48", "Participantes organizados por grupos e classificacoes."},
                {"Jogos", "104", "Calendario, estadios e equipas de arbitragem."},
                {"Bilhetes", "Venda", "Compra, reservas e historico de bilhetes comprados."},
                {"Logistica", "Ativa", "Alojamento e deslocacoes das equipas durante o evento."}
        };

        for (int i = 0; i < cards.length; i++) {
            overview.add(summaryCard(null, cards[i][0], cards[i][1], cards[i][2]), constraints(0, i, 1, 1, GridConstraints.FILL_BOTH));
        }
        return overview;
    }

    private JPanel buildModuleGrid() {
        JPanel grid = panel(AppTheme.BACKGROUND, 2, 3, new Insets(0, 0, 0, 0), 18, 18);
        Object[][] modules = {
                {AppIcons.module("CAL", new Color(0x1D5DDB)), "Calendario", "Definir jogos", "Datas, estadios, confrontos e fases do campeonato."},
                {AppIcons.module("EQ", new Color(0x61D394)), "Equipas", "Gerir participantes", "Grupos, classificacoes, vitorias, empates e pontos."},
                {AppIcons.module("VAR", new Color(0xF5C867)), "Arbitragem", "Atribuir equipas", "Arbitros principais, assistentes e VAR por jogo."},
                {AppIcons.module("TKT", new Color(0xEF6F6C)), "Bilhetes", "Venda e historico", "Compra, quantidade, termos e bilhetes adquiridos."},
                {AppIcons.module("BUS", new Color(0x7CA7FF)), "Deslocacoes", "Planeamento", "Viagens das equipas entre hoteis, estadios e cidades."},
                {AppIcons.module("HOT", new Color(0xA985FF)), "Alojamento", "Hoteis", "Reservas, distancia ao estadio e avaliacao do hotel."}
        };

        for (int i = 0; i < modules.length; i++) {
            grid.add(summaryCard((javax.swing.Icon) modules[i][0], (String) modules[i][1], (String) modules[i][2], (String) modules[i][3]), constraints(i / 3, i % 3, 1, 1, GridConstraints.FILL_BOTH));
        }
        return grid;
    }

    private JPanel summaryCard(javax.swing.Icon icon, String title, String value, String description) {
        JPanel card = panel(AppTheme.PANEL_SOFT, 1, icon == null ? 1 : 2, new Insets(16, 16, 16, 16), 14, 0);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 20)));

        if (icon != null) {
            JLabel image = new JLabel(icon);
            card.add(image, constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        }

        JPanel text = panel(AppTheme.PANEL_SOFT, 3, 1, new Insets(0, 0, 0, 0), 0, 7);
        text.add(label(title.toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        text.add(label(value, AppTheme.TEXT, new Font("Inter", Font.BOLD, 20)), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        text.add(label("<html><body style='width: 210px'>" + description + "</body></html>", AppTheme.MUTED, AppTheme.BODY_FONT), constraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(text, constraints(0, icon == null ? 0 : 1, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JButton iconButton(javax.swing.Icon icon) {
        JButton button = new JButton(icon);
        button.setForeground(AppTheme.TEXT);
        button.setBackground(AppTheme.HEADER);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        return button;
    }

    private JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppTheme.ACCENT);
        button.setForeground(new Color(0x08233C));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return button;
    }

    private JPanel whitePanel(int rows, int cols, Insets insets, int hGap, int vGap) {
        JPanel panel = panel(Color.WHITE, rows, cols, insets, hGap, vGap);
        panel.setPreferredSize(new Dimension(180, 40));
        return panel;
    }

    private JPanel panel(Color background, int rows, int cols, Insets insets, int hGap, int vGap) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(background);
        panel.setLayout(new GridLayoutManager(rows, cols, insets, hGap, vGap));
        return panel;
    }

    private JLabel label(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private GridConstraints constraints(int row, int col, int rowSpan, int colSpan, int fill) {
        return new GridConstraints(
                row,
                col,
                rowSpan,
                colSpan,
                GridConstraints.ANCHOR_CENTER,
                fill,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                null,
                null,
                null,
                0,
                false
        );
    }
}
