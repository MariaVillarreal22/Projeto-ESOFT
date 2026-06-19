package pt.ipleiria.es.worldcup.ui;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

final class AppIcons {
    private AppIcons() {
    }

    static Icon search() {
        return new LineIcon(18, "search");
    }

    static Icon calendar() {
        return new LineIcon(18, "calendar");
    }

    static Icon menu() {
        return new LineIcon(28, "menu");
    }

    static Icon argentinaFlag() {
        return new ArgentinaFlagIcon(32, 24);
    }

    static Icon teamFlag(String code) {
        return new TeamFlagIcon(32, 24, code);
    }

    static Icon teamFlag(String code, int width, int height) {
        return new TeamFlagIcon(width, height, code);
    }

    static Icon module(String text, Color background) {
        return new ModuleIcon(42, text, background);
    }

    private static final class LineIcon implements Icon {
        private final int size;
        private final String type;

        private LineIcon(int size, String type) {
            this.size = size;
            this.type = type;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setColor(c.getForeground());
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            if ("search".equals(type)) {
                g2.drawOval(x + 2, y + 2, size - 8, size - 8);
                g2.drawLine(x + size - 6, y + size - 6, x + size - 1, y + size - 1);
            } else if ("calendar".equals(type)) {
                g2.drawRoundRect(x + 2, y + 4, size - 4, size - 5, 4, 4);
                g2.drawLine(x + 2, y + 9, x + size - 2, y + 9);
                g2.drawLine(x + 6, y + 1, x + 6, y + 6);
                g2.drawLine(x + size - 6, y + 1, x + size - 6, y + 6);
            } else {
                g2.drawLine(x + 2, y + 6, x + size - 2, y + 6);
                g2.drawLine(x + 2, y + size / 2, x + size - 2, y + size / 2);
                g2.drawLine(x + 2, y + size - 6, x + size - 2, y + size - 6);
            }

            g2.dispose();
        }
    }

    private static final class ArgentinaFlagIcon implements Icon {
        private final int width;
        private final int height;

        private ArgentinaFlagIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            ShapePainter.round(g2, x, y, width, height, 10, Color.WHITE);
            g2.setClip(new RoundRectangle2D.Double(x, y, width, height, 10, 10));
            g2.setColor(new Color(0x75AADB));
            g2.fillRect(x, y, width, height / 3);
            g2.fillRect(x, y + (height / 3) * 2, width, height / 3 + 1);
            g2.setColor(new Color(0xF5C867));
            g2.fill(new Ellipse2D.Double(x + width / 2.0 - 3, y + height / 2.0 - 3, 6, 6));
            g2.setClip(null);
            g2.setColor(new Color(0, 0, 0, 35));
            g2.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            g2.dispose();
        }
    }

    private static final class TeamFlagIcon implements Icon {
        private final int width;
        private final int height;
        private final String code;

        private TeamFlagIcon(int width, int height, String code) {
            this.width = width;
            this.height = height;
            this.code = code;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            ShapePainter.round(g2, x, y, width, height, 10, Color.WHITE);
            g2.setClip(new RoundRectangle2D.Double(x, y, width, height, 10, 10));
            paintFlag(g2, x, y);
            g2.setClip(null);
            g2.setColor(new Color(0, 0, 0, 35));
            g2.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            g2.dispose();
        }

        private void paintFlag(Graphics2D g2, int x, int y) {
            switch (code) {
                case "CAN" -> canada(g2, x, y);
                case "MEX" -> vertical(g2, x, y, new Color(0x006847), Color.WHITE, new Color(0xCE1126));
                case "USA" -> usa(g2, x, y);
                case "JPN" -> circleFlag(g2, x, y, Color.WHITE, new Color(0xBC002D));
                case "NZL" -> blueWithCantonStars(g2, x, y, new Color(0x00247D));
                case "IRN" -> horizontal(g2, x, y, new Color(0x239F40), Color.WHITE, new Color(0xDA0000));
                case "ARG" -> argentina(g2, x, y);
                case "UZB" -> uzbekistan(g2, x, y);
                case "JOR" -> jordan(g2, x, y);
                case "KOR" -> korea(g2, x, y);
                case "AUS" -> australia(g2, x, y);
                case "BRA" -> brazil(g2, x, y);
                case "ECU" -> ecuador(g2, x, y);
                case "PAR" -> horizontal(g2, x, y, new Color(0xD52B1E), Color.WHITE, new Color(0x0038A8));
                case "URU" -> uruguay(g2, x, y);
                case "COL" -> ecuador(g2, x, y);
                case "MAR" -> starFlag(g2, x, y, new Color(0xC1272D), new Color(0x006233));
                case "TUN" -> tunisia(g2, x, y);
                case "EGY" -> horizontal(g2, x, y, new Color(0xCE1126), Color.WHITE, Color.BLACK);
                case "ALG" -> algeria(g2, x, y);
                case "GHA" -> ghana(g2, x, y);
                case "CPV" -> capeVerde(g2, x, y);
                case "QAT" -> qatar(g2, x, y);
                case "KSA" -> saudiArabia(g2, x, y);
                case "SEN" -> senegal(g2, x, y);
                case "RSA" -> southAfrica(g2, x, y);
                case "CIV" -> vertical(g2, x, y, new Color(0xF77F00), Color.WHITE, new Color(0x009E60));
                case "ENG" -> england(g2, x, y);
                case "FRA" -> vertical(g2, x, y, new Color(0x0055A4), Color.WHITE, new Color(0xEF4135));
                case "CRO" -> croatia(g2, x, y);
                case "POR" -> portugal(g2, x, y);
                case "NOR" -> nordic(g2, x, y, new Color(0xBA0C2F), Color.WHITE, new Color(0x00205B));
                case "GER" -> horizontal(g2, x, y, Color.BLACK, new Color(0xDD0000), new Color(0xFFCE00));
                case "NED" -> horizontal(g2, x, y, new Color(0xAE1C28), Color.WHITE, new Color(0x21468B));
                case "SUI" -> swiss(g2, x, y);
                case "SCO" -> scotland(g2, x, y);
                case "ESP" -> spain(g2, x, y);
                case "AUT" -> horizontal(g2, x, y, new Color(0xED2939), Color.WHITE, new Color(0xED2939));
                case "BEL" -> vertical(g2, x, y, Color.BLACK, new Color(0xFAE042), new Color(0xED2939));
                case "PAN" -> panama(g2, x, y);
                case "CUW" -> curacao(g2, x, y);
                case "HAI" -> haiti(g2, x, y);
                case "BIH" -> bosnia(g2, x, y);
                case "SWE" -> nordic(g2, x, y, new Color(0x006AA7), new Color(0xFECC00), new Color(0xFECC00));
                case "TUR" -> turkey(g2, x, y);
                case "CZE" -> czechia(g2, x, y);
                case "COD" -> drCongo(g2, x, y);
                case "IRQ" -> iraq(g2, x, y);
                default -> horizontal(g2, x, y, new Color(0x75AADB), Color.WHITE, new Color(0x75AADB));
            }
        }

        private void horizontal(Graphics2D g2, int x, int y, Color top, Color middle, Color bottom) {
            g2.setColor(top);
            g2.fillRect(x, y, width, height / 3 + 1);
            g2.setColor(middle);
            g2.fillRect(x, y + height / 3, width, height / 3 + 1);
            g2.setColor(bottom);
            g2.fillRect(x, y + (height / 3) * 2, width, height / 3 + 1);
        }

        private void vertical(Graphics2D g2, int x, int y, Color left, Color middle, Color right) {
            g2.setColor(left);
            g2.fillRect(x, y, width / 3 + 1, height);
            g2.setColor(middle);
            g2.fillRect(x + width / 3, y, width / 3 + 1, height);
            g2.setColor(right);
            g2.fillRect(x + (width / 3) * 2, y, width / 3 + 2, height);
        }

        private void canada(Graphics2D g2, int x, int y) {
            vertical(g2, x, y, new Color(0xD80621), Color.WHITE, new Color(0xD80621));
            star(g2, x + width / 2.0, y + height / 2.0, 5.5, 2.6, new Color(0xD80621));
        }

        private void usa(Graphics2D g2, int x, int y) {
            for (int i = 0; i < 13; i++) {
                g2.setColor(i % 2 == 0 ? new Color(0xB22234) : Color.WHITE);
                g2.fillRect(x, y + i * height / 13, width, height / 13 + 1);
            }
            g2.setColor(new Color(0x3C3B6E));
            g2.fillRect(x, y, width / 2, height / 2 + 1);
            g2.setColor(Color.WHITE);
            for (int i = 0; i < 6; i++) {
                g2.fillOval(x + 3 + i * 4, y + 3 + (i % 2) * 4, 2, 2);
            }
        }

        private void argentina(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, new Color(0x75AADB), Color.WHITE, new Color(0x75AADB));
            g2.setColor(new Color(0xF5C867));
            g2.fill(new Ellipse2D.Double(x + width / 2.0 - 3, y + height / 2.0 - 3, 6, 6));
        }

        private void uzbekistan(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, new Color(0x1EB6E7), Color.WHITE, new Color(0x009B3A));
            g2.setColor(new Color(0xCE1126));
            g2.fillRect(x, y + height / 3 - 1, width, 2);
            g2.fillRect(x, y + (height / 3) * 2 - 1, width, 2);
            crescent(g2, x + 7, y + 6, 5, Color.WHITE, new Color(0x1EB6E7));
        }

        private void jordan(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, Color.BLACK, Color.WHITE, new Color(0x007A3D));
            triangle(g2, x, y, x, y + height, x + 14, y + height / 2, new Color(0xCE1126));
        }

        private void korea(Graphics2D g2, int x, int y) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(new Color(0xCD2E3A));
            g2.fillArc(x + 11, y + 6, 10, 10, 0, 180);
            g2.setColor(new Color(0x0047A0));
            g2.fillArc(x + 11, y + 6, 10, 10, 180, 180);
            g2.setColor(Color.BLACK);
            g2.fillRect(x + 5, y + 5, 7, 2);
            g2.fillRect(x + 20, y + 17, 7, 2);
        }

        private void australia(Graphics2D g2, int x, int y) {
            blueWithCantonStars(g2, x, y, new Color(0x012169));
            star(g2, x + 24, y + 17, 4, 1.8, Color.WHITE);
        }

        private void blueWithCantonStars(Graphics2D g2, int x, int y, Color blue) {
            g2.setColor(blue);
            g2.fillRect(x, y, width, height);
            unionJack(g2, x, y, 15, 11);
            g2.setColor(new Color(0xCC142B));
            g2.fillOval(x + 22, y + 6, 4, 4);
            g2.fillOval(x + 25, y + 14, 4, 4);
        }

        private void brazil(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x009B3A));
            g2.fillRect(x, y, width, height);
            diamond(g2, x + width / 2, y + height / 2, 13, 8, new Color(0xFFDF00));
            g2.setColor(new Color(0x002776));
            g2.fillOval(x + 11, y + 7, 10, 10);
        }

        private void ecuador(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0xFFD100));
            g2.fillRect(x, y, width, height / 2);
            g2.setColor(new Color(0x034EA2));
            g2.fillRect(x, y + height / 2, width, height / 4 + 1);
            g2.setColor(new Color(0xED1C24));
            g2.fillRect(x, y + height * 3 / 4, width, height / 4 + 1);
        }

        private void uruguay(Graphics2D g2, int x, int y) {
            for (int i = 0; i < 9; i++) {
                g2.setColor(i % 2 == 0 ? Color.WHITE : new Color(0x0038A8));
                g2.fillRect(x, y + i * height / 9, width, height / 9 + 1);
            }
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, 13, 13);
            g2.setColor(new Color(0xFCD116));
            g2.fillOval(x + 4, y + 4, 5, 5);
        }

        private void circleFlag(Graphics2D g2, int x, int y, Color background, Color circle) {
            g2.setColor(background);
            g2.fillRect(x, y, width, height);
            g2.setColor(circle);
            g2.fillOval(x + 10, y + 6, 12, 12);
        }

        private void starFlag(Graphics2D g2, int x, int y, Color background, Color starColor) {
            g2.setColor(background);
            g2.fillRect(x, y, width, height);
            star(g2, x + width / 2.0, y + height / 2.0, 6, 2.4, starColor);
        }

        private void tunisia(Graphics2D g2, int x, int y) {
            circleFlag(g2, x, y, new Color(0xE70013), Color.WHITE);
            crescent(g2, x + 16, y + 12, 5, new Color(0xE70013), Color.WHITE);
            star(g2, x + 18, y + 12, 3, 1.4, new Color(0xE70013));
        }

        private void algeria(Graphics2D g2, int x, int y) {
            vertical(g2, x, y, new Color(0x006233), Color.WHITE, Color.WHITE);
            crescent(g2, x + 17, y + 12, 6, new Color(0xD21034), Color.WHITE);
            star(g2, x + 20, y + 12, 3, 1.4, new Color(0xD21034));
        }

        private void ghana(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, new Color(0xCE1126), new Color(0xFCD116), new Color(0x006B3F));
            star(g2, x + width / 2.0, y + height / 2.0, 4, 1.8, Color.BLACK);
        }

        private void capeVerde(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x003893));
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y + 13, width, 4);
            g2.setColor(new Color(0xCF2027));
            g2.fillRect(x, y + 15, width, 2);
            g2.setColor(new Color(0xF7D116));
            for (int i = 0; i < 5; i++) {
                g2.fillOval(x + 7 + i * 3, y + 8 + (i % 2) * 2, 2, 2);
            }
        }

        private void qatar(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x8A1538));
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, 8, height);
            for (int i = 0; i < 6; i++) {
                triangle(g2, x + 8, y + i * 4, x + 8, y + i * 4 + 4, x + 13, y + i * 4 + 2, Color.WHITE);
            }
        }

        private void saudiArabia(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x006C35));
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 8, y + 15, 17, 2);
            g2.fillRect(x + 10, y + 9, 12, 2);
        }

        private void senegal(Graphics2D g2, int x, int y) {
            vertical(g2, x, y, new Color(0x00853F), new Color(0xFDEF42), new Color(0xE31B23));
            star(g2, x + width / 2.0, y + height / 2.0, 4, 1.8, new Color(0x00853F));
        }

        private void southAfrica(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0xDE3831));
            g2.fillRect(x, y, width, height / 2);
            g2.setColor(new Color(0x002395));
            g2.fillRect(x, y + height / 2, width, height / 2);
            triangle(g2, x, y, x, y + height, x + 16, y + height / 2, Color.BLACK);
            triangle(g2, x + 2, y + 3, x + 2, y + height - 3, x + 17, y + height / 2, new Color(0xFFB612));
            triangle(g2, x + 5, y + 5, x + 5, y + height - 5, x + 20, y + height / 2, new Color(0x007A4D));
        }

        private void england(Graphics2D g2, int x, int y) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(new Color(0xC8102E));
            g2.fillRect(x + width / 2 - 2, y, 4, height);
            g2.fillRect(x, y + height / 2 - 2, width, 4);
        }

        private void croatia(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, new Color(0xFF0000), Color.WHITE, new Color(0x171796));
            g2.setColor(new Color(0xFF0000));
            g2.fillRect(x + 13, y + 8, 3, 3);
            g2.fillRect(x + 18, y + 11, 3, 3);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 16, y + 8, 3, 3);
            g2.fillRect(x + 13, y + 11, 3, 3);
        }

        private void portugal(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x006600));
            g2.fillRect(x, y, 13, height);
            g2.setColor(new Color(0xFF0000));
            g2.fillRect(x + 13, y, width - 13, height);
            g2.setColor(new Color(0xFFCC00));
            g2.fillOval(x + 10, y + 8, 7, 7);
        }

        private void nordic(Graphics2D g2, int x, int y, Color base, Color outerCross, Color innerCross) {
            g2.setColor(base);
            g2.fillRect(x, y, width, height);
            g2.setColor(outerCross);
            g2.fillRect(x + 9, y, 6, height);
            g2.fillRect(x, y + 9, width, 6);
            g2.setColor(innerCross);
            g2.fillRect(x + 11, y, 2, height);
            g2.fillRect(x, y + 11, width, 2);
        }

        private void swiss(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0xFF0000));
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 14, y + 5, 4, 14);
            g2.fillRect(x + 9, y + 10, 14, 4);
        }

        private void scotland(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x005EB8));
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            Path2D a = new Path2D.Double();
            a.moveTo(x, y);
            a.lineTo(x + 5, y);
            a.lineTo(x + width, y + height - 5);
            a.lineTo(x + width, y + height);
            a.lineTo(x + width - 5, y + height);
            a.lineTo(x, y + 5);
            a.closePath();
            g2.fill(a);
            Path2D b = new Path2D.Double();
            b.moveTo(x + width, y);
            b.lineTo(x + width, y + 5);
            b.lineTo(x + 5, y + height);
            b.lineTo(x, y + height);
            b.lineTo(x, y + height - 5);
            b.lineTo(x + width - 5, y);
            b.closePath();
            g2.fill(b);
        }

        private void spain(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0xAA151B));
            g2.fillRect(x, y, width, 6);
            g2.fillRect(x, y + 18, width, 6);
            g2.setColor(new Color(0xF1BF00));
            g2.fillRect(x, y + 6, width, 12);
            g2.setColor(new Color(0xAA151B));
            g2.fillOval(x + 8, y + 10, 4, 5);
        }

        private void panama(Graphics2D g2, int x, int y) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(new Color(0x005293));
            g2.fillRect(x + width / 2, y, width / 2, height / 2);
            g2.setColor(new Color(0xD21034));
            g2.fillRect(x, y + height / 2, width / 2, height / 2);
            star(g2, x + 8, y + 6, 3, 1.3, new Color(0x005293));
            star(g2, x + 24, y + 18, 3, 1.3, new Color(0xD21034));
        }

        private void curacao(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x002B7F));
            g2.fillRect(x, y, width, height);
            g2.setColor(new Color(0xF9E814));
            g2.fillRect(x, y + 15, width, 4);
            star(g2, x + 8, y + 7, 3, 1.3, Color.WHITE);
            star(g2, x + 12, y + 10, 2, 1, Color.WHITE);
        }

        private void haiti(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x00209F));
            g2.fillRect(x, y, width, height / 2);
            g2.setColor(new Color(0xD21034));
            g2.fillRect(x, y + height / 2, width, height / 2);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 12, y + 9, 8, 6);
        }

        private void bosnia(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x002395));
            g2.fillRect(x, y, width, height);
            triangle(g2, x + 13, y, x + width, y, x + width, y + height, new Color(0xFECB00));
            for (int i = 0; i < 5; i++) {
                star(g2, x + 11 + i * 4, y + 3 + i * 4, 2.3, 1, Color.WHITE);
            }
        }

        private void turkey(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0xE30A17));
            g2.fillRect(x, y, width, height);
            crescent(g2, x + 14, y + 12, 7, Color.WHITE, new Color(0xE30A17));
            star(g2, x + 20, y + 12, 3, 1.3, Color.WHITE);
        }

        private void czechia(Graphics2D g2, int x, int y) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height / 2);
            g2.setColor(new Color(0xD7141A));
            g2.fillRect(x, y + height / 2, width, height / 2);
            triangle(g2, x, y, x, y + height, x + 15, y + height / 2, new Color(0x11457E));
        }

        private void drCongo(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x007FFF));
            g2.fillRect(x, y, width, height);
            diagonal(g2, x, y, new Color(0xF7D618), 11);
            diagonal(g2, x, y, new Color(0xCE1021), 7);
            star(g2, x + 8, y + 6, 4, 1.8, new Color(0xF7D618));
        }

        private void iraq(Graphics2D g2, int x, int y) {
            horizontal(g2, x, y, new Color(0xCE1126), Color.WHITE, Color.BLACK);
            g2.setColor(new Color(0x007A3D));
            g2.fillRect(x + 12, y + 11, 9, 2);
        }

        private void unionJack(Graphics2D g2, int x, int y, int w, int h) {
            g2.setColor(new Color(0x012169));
            g2.fillRect(x, y, w, h);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + w / 2 - 2, y, 4, h);
            g2.fillRect(x, y + h / 2 - 2, w, 4);
            g2.setColor(new Color(0xC8102E));
            g2.fillRect(x + w / 2 - 1, y, 2, h);
            g2.fillRect(x, y + h / 2 - 1, w, 2);
        }

        private void crescent(Graphics2D g2, int cx, int cy, int radius, Color color, Color cutout) {
            g2.setColor(color);
            g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
            g2.setColor(cutout);
            g2.fillOval(cx - radius / 2, cy - radius, radius * 2, radius * 2);
        }

        private void diamond(Graphics2D g2, int cx, int cy, int rx, int ry, Color color) {
            Path2D path = new Path2D.Double();
            path.moveTo(cx, cy - ry);
            path.lineTo(cx + rx, cy);
            path.lineTo(cx, cy + ry);
            path.lineTo(cx - rx, cy);
            path.closePath();
            g2.setColor(color);
            g2.fill(path);
        }

        private void diagonal(Graphics2D g2, int x, int y, Color color, int thickness) {
            Path2D path = new Path2D.Double();
            path.moveTo(x + width, y);
            path.lineTo(x + width, y + thickness);
            path.lineTo(x, y + height);
            path.lineTo(x, y + height - thickness);
            path.closePath();
            g2.setColor(color);
            g2.fill(path);
        }

        private void triangle(Graphics2D g2, int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.closePath();
            g2.setColor(color);
            g2.fill(path);
        }

        private void star(Graphics2D g2, double cx, double cy, double outer, double inner, Color color) {
            Path2D path = new Path2D.Double();
            for (int i = 0; i < 10; i++) {
                double angle = Math.PI / 2 + i * Math.PI / 5;
                double radius = i % 2 == 0 ? outer : inner;
                double px = cx + Math.cos(angle) * radius;
                double py = cy - Math.sin(angle) * radius;
                if (i == 0) {
                    path.moveTo(px, py);
                } else {
                    path.lineTo(px, py);
                }
            }
            path.closePath();
            g2.setColor(color);
            g2.fill(path);
        }
    }

    private static final class ModuleIcon implements Icon {
        private final int size;
        private final String text;
        private final Color background;

        private ModuleIcon(int size, String text, Color background) {
            this.size = size;
            this.text = text;
            this.background = background;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            ShapePainter.round(g2, x, y, size, size, 12, background);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Inter", Font.BOLD, 13));
            FontMetrics metrics = g2.getFontMetrics();
            int textX = x + (size - metrics.stringWidth(text)) / 2;
            int textY = y + (size + metrics.getAscent()) / 2 - 3;
            g2.drawString(text, textX, textY);
            g2.dispose();
        }
    }

    private static Graphics2D prepare(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    private static final class ShapePainter {
        private ShapePainter() {
        }

        static void round(Graphics2D g2, int x, int y, int width, int height, int radius, Color color) {
            g2.setColor(color);
            g2.fill(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        }
    }
}
