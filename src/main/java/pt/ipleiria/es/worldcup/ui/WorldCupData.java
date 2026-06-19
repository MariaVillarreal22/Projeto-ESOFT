package pt.ipleiria.es.worldcup.ui;

import java.awt.Color;

final class WorldCupData {
    private static final WorldCupTeam[] TEAMS = {
            team("Canada", "CAN", "CONCACAF", 0xD80621, 0xFFFFFF),
            team("Mexico", "MEX", "CONCACAF", 0x006847, 0xCE1126),
            team("United States", "USA", "CONCACAF", 0x3C3B6E, 0xB22234),
            team("Japan", "JPN", "AFC", 0xFFFFFF, 0xBC002D),
            team("New Zealand", "NZL", "OFC", 0x00247D, 0xCC142B),
            team("Iran", "IRN", "AFC", 0x239F40, 0xDA0000),
            team("Argentina", "ARG", "CONMEBOL", 0x75AADB, 0xFFFFFF),
            team("Uzbekistan", "UZB", "AFC", 0x1EB6E7, 0x009B3A),
            team("Jordan", "JOR", "AFC", 0x007A3D, 0xCE1126),
            team("South Korea", "KOR", "AFC", 0xFFFFFF, 0xC60C30),
            team("Australia", "AUS", "AFC", 0x012169, 0xFFCD00),
            team("Brazil", "BRA", "CONMEBOL", 0x009B3A, 0xFFDF00),
            team("Ecuador", "ECU", "CONMEBOL", 0xFFD100, 0x034EA2),
            team("Paraguay", "PAR", "CONMEBOL", 0xD52B1E, 0x0038A8),
            team("Uruguay", "URU", "CONMEBOL", 0x0038A8, 0xFFFFFF),
            team("Colombia", "COL", "CONMEBOL", 0xFCD116, 0x003893),
            team("Morocco", "MAR", "CAF", 0xC1272D, 0x006233),
            team("Tunisia", "TUN", "CAF", 0xE70013, 0xFFFFFF),
            team("Egypt", "EGY", "CAF", 0xCE1126, 0x000000),
            team("Algeria", "ALG", "CAF", 0x006233, 0xFFFFFF),
            team("Ghana", "GHA", "CAF", 0xFCD116, 0x006B3F),
            team("Cape Verde", "CPV", "CAF", 0x003893, 0xCF2027),
            team("Qatar", "QAT", "AFC", 0x8A1538, 0xFFFFFF),
            team("Saudi Arabia", "KSA", "AFC", 0x006C35, 0xFFFFFF),
            team("Senegal", "SEN", "CAF", 0x00853F, 0xFDEF42),
            team("South Africa", "RSA", "CAF", 0x007A4D, 0xFFB612),
            team("Ivory Coast", "CIV", "CAF", 0xF77F00, 0x009E60),
            team("England", "ENG", "UEFA", 0xFFFFFF, 0xC8102E),
            team("France", "FRA", "UEFA", 0x0055A4, 0xEF4135),
            team("Croatia", "CRO", "UEFA", 0xFF0000, 0xFFFFFF),
            team("Portugal", "POR", "UEFA", 0x006600, 0xFF0000),
            team("Norway", "NOR", "UEFA", 0xBA0C2F, 0x00205B),
            team("Germany", "GER", "UEFA", 0x000000, 0xFFCC00),
            team("Netherlands", "NED", "UEFA", 0xAE1C28, 0x21468B),
            team("Switzerland", "SUI", "UEFA", 0xFF0000, 0xFFFFFF),
            team("Scotland", "SCO", "UEFA", 0x005EB8, 0xFFFFFF),
            team("Spain", "ESP", "UEFA", 0xAA151B, 0xF1BF00),
            team("Austria", "AUT", "UEFA", 0xED2939, 0xFFFFFF),
            team("Belgium", "BEL", "UEFA", 0x000000, 0xFAE042),
            team("Panama", "PAN", "CONCACAF", 0x005293, 0xD21034),
            team("Curacao", "CUW", "CONCACAF", 0x002B7F, 0xF9E814),
            team("Haiti", "HAI", "CONCACAF", 0x00209F, 0xD21034),
            team("Bosnia and Herzegovina", "BIH", "UEFA", 0x002395, 0xFECB00),
            team("Sweden", "SWE", "UEFA", 0x006AA7, 0xFECC00),
            team("Turkey", "TUR", "UEFA", 0xE30A17, 0xFFFFFF),
            team("Czech Republic", "CZE", "UEFA", 0xD7141A, 0x11457E),
            team("DR Congo", "COD", "CAF", 0x007FFF, 0xF7D618),
            team("Iraq", "IRQ", "AFC", 0xCE1126, 0x000000)
    };

    private WorldCupData() {
    }

    static WorldCupTeam[] teams() {
        return TEAMS.clone();
    }

    static WorldCupTeam defaultTeam() {
        return TEAMS[6];
    }

    static WorldCupTeam findByName(String name) {
        for (WorldCupTeam team : TEAMS) {
            if (team.name().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    private static WorldCupTeam team(String name, String code, String confederation, int primary, int secondary) {
        return new WorldCupTeam(name, code, confederation, new Color(primary), new Color(secondary));
    }
}
