package pt.ipleiria.es.worldcup.ui;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

final class WorldCupFixtures {
    private static final String EASTERN = "America/New_York";
    private static final String CENTRAL = "America/Chicago";
    private static final String PACIFIC = "America/Los_Angeles";
    private static final String VANCOUVER = "America/Vancouver";
    private static final String TORONTO = "America/Toronto";
    private static final String MEXICO = "America/Mexico_City";

    private static final CalendarGroup[] GROUPS = {
            group("Group A",
                    result("2026-06-11", "13:00", "Mexico vs South Africa", "Estadio Azteca", "Mexico City", MEXICO, 2, 0),
                    result("2026-06-11", "20:00", "South Korea vs Czech Republic", "Estadio Akron", "Zapopan", MEXICO, 2, 1),
                    result("2026-06-18", "12:00", "Czech Republic vs South Africa", "Mercedes-Benz Stadium", "Atlanta", EASTERN, 1, 1),
                    result("2026-06-18", "19:00", "Mexico vs South Korea", "Estadio Akron", "Zapopan", MEXICO, 1, 0),
                    match("2026-06-24", "19:00", "Czech Republic vs Mexico", "Estadio Azteca", "Mexico City", MEXICO),
                    match("2026-06-24", "19:00", "South Africa vs South Korea", "Estadio BBVA", "Guadalupe", MEXICO)),
            group("Group B",
                    result("2026-06-12", "15:00", "Canada vs Bosnia and Herzegovina", "BMO Field", "Toronto", TORONTO, 1, 1),
                    result("2026-06-13", "12:00", "Qatar vs Switzerland", "Levi's Stadium", "Santa Clara", PACIFIC, 1, 1),
                    result("2026-06-18", "12:00", "Switzerland vs Bosnia and Herzegovina", "SoFi Stadium", "Inglewood", PACIFIC, 4, 1),
                    result("2026-06-18", "15:00", "Canada vs Qatar", "BC Place", "Vancouver", VANCOUVER, 6, 0),
                    match("2026-06-24", "12:00", "Switzerland vs Canada", "BC Place", "Vancouver", VANCOUVER),
                    match("2026-06-24", "12:00", "Bosnia and Herzegovina vs Qatar", "Lumen Field", "Seattle", PACIFIC)),
            group("Group C",
                    result("2026-06-13", "18:00", "Brazil vs Morocco", "MetLife Stadium", "East Rutherford", EASTERN, 1, 1),
                    result("2026-06-13", "21:00", "Haiti vs Scotland", "Gillette Stadium", "Foxborough", EASTERN, 0, 1),
                    match("2026-06-19", "18:00", "Scotland vs Morocco", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-19", "20:30", "Brazil vs Haiti", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-24", "18:00", "Scotland vs Brazil", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-24", "18:00", "Morocco vs Haiti", "Mercedes-Benz Stadium", "Atlanta", EASTERN)),
            group("Group D",
                    result("2026-06-12", "18:00", "United States vs Paraguay", "SoFi Stadium", "Inglewood", PACIFIC, 4, 1),
                    result("2026-06-13", "21:00", "Australia vs Turkey", "BC Place", "Vancouver", VANCOUVER, 2, 0),
                    match("2026-06-19", "12:00", "United States vs Australia", "Lumen Field", "Seattle", PACIFIC),
                    match("2026-06-19", "20:00", "Turkey vs Paraguay", "Levi's Stadium", "Santa Clara", PACIFIC),
                    match("2026-06-25", "19:00", "Turkey vs United States", "SoFi Stadium", "Inglewood", PACIFIC),
                    match("2026-06-25", "19:00", "Paraguay vs Australia", "Levi's Stadium", "Santa Clara", PACIFIC)),
            group("Group E",
                    result("2026-06-14", "12:00", "Germany vs Curacao", "NRG Stadium", "Houston", CENTRAL, 7, 1),
                    result("2026-06-14", "19:00", "Ivory Coast vs Ecuador", "Lincoln Financial Field", "Philadelphia", EASTERN, 1, 0),
                    match("2026-06-20", "16:00", "Germany vs Ivory Coast", "BMO Field", "Toronto", TORONTO),
                    match("2026-06-20", "19:00", "Ecuador vs Curacao", "Arrowhead Stadium", "Kansas City", CENTRAL),
                    match("2026-06-25", "16:00", "Curacao vs Ivory Coast", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-25", "16:00", "Ecuador vs Germany", "MetLife Stadium", "East Rutherford", EASTERN)),
            group("Group F",
                    result("2026-06-14", "15:00", "Netherlands vs Japan", "AT&T Stadium", "Arlington", CENTRAL, 2, 2),
                    result("2026-06-14", "20:00", "Sweden vs Tunisia", "Estadio BBVA", "Guadalupe", MEXICO, 5, 1),
                    match("2026-06-20", "12:00", "Netherlands vs Sweden", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-20", "22:00", "Tunisia vs Japan", "Estadio BBVA", "Guadalupe", MEXICO),
                    match("2026-06-25", "18:00", "Japan vs Sweden", "AT&T Stadium", "Arlington", CENTRAL),
                    match("2026-06-25", "18:00", "Tunisia vs Netherlands", "Arrowhead Stadium", "Kansas City", CENTRAL)),
            group("Group G",
                    result("2026-06-15", "12:00", "Belgium vs Egypt", "Lumen Field", "Seattle", PACIFIC, 1, 1),
                    result("2026-06-15", "18:00", "Iran vs New Zealand", "SoFi Stadium", "Inglewood", PACIFIC, 2, 2),
                    match("2026-06-21", "12:00", "Belgium vs Iran", "SoFi Stadium", "Inglewood", PACIFIC),
                    match("2026-06-21", "18:00", "New Zealand vs Egypt", "BC Place", "Vancouver", VANCOUVER),
                    match("2026-06-26", "20:00", "Egypt vs Iran", "Lumen Field", "Seattle", PACIFIC),
                    match("2026-06-26", "20:00", "New Zealand vs Belgium", "BC Place", "Vancouver", VANCOUVER)),
            group("Group H",
                    result("2026-06-15", "12:00", "Spain vs Cape Verde", "Mercedes-Benz Stadium", "Atlanta", EASTERN, 0, 0),
                    result("2026-06-15", "18:00", "Saudi Arabia vs Uruguay", "Hard Rock Stadium", "Miami Gardens", EASTERN, 1, 1),
                    match("2026-06-21", "12:00", "Spain vs Saudi Arabia", "Mercedes-Benz Stadium", "Atlanta", EASTERN),
                    match("2026-06-21", "18:00", "Uruguay vs Cape Verde", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-26", "19:00", "Cape Verde vs Saudi Arabia", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-26", "18:00", "Uruguay vs Spain", "Estadio Akron", "Zapopan", MEXICO)),
            group("Group I",
                    result("2026-06-16", "15:00", "France vs Senegal", "MetLife Stadium", "East Rutherford", EASTERN, 3, 1),
                    result("2026-06-16", "18:00", "Iraq vs Norway", "Gillette Stadium", "Foxborough", EASTERN, 1, 4),
                    match("2026-06-22", "17:00", "France vs Iraq", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-22", "20:00", "Norway vs Senegal", "MetLife Stadium", "East Rutherford", EASTERN),
                    match("2026-06-26", "15:00", "Norway vs France", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-26", "15:00", "Senegal vs Iraq", "BMO Field", "Toronto", TORONTO)),
            group("Group J",
                    result("2026-06-16", "20:00", "Argentina vs Algeria", "Arrowhead Stadium", "Kansas City", CENTRAL, 3, 0),
                    result("2026-06-16", "21:00", "Austria vs Jordan", "Levi's Stadium", "Santa Clara", PACIFIC, 3, 1),
                    match("2026-06-22", "12:00", "Argentina vs Austria", "AT&T Stadium", "Arlington", CENTRAL),
                    match("2026-06-22", "20:00", "Jordan vs Algeria", "Levi's Stadium", "Santa Clara", PACIFIC),
                    match("2026-06-27", "21:00", "Algeria vs Austria", "Arrowhead Stadium", "Kansas City", CENTRAL),
                    match("2026-06-27", "21:00", "Jordan vs Argentina", "AT&T Stadium", "Arlington", CENTRAL)),
            group("Group K",
                    result("2026-06-17", "12:00", "Portugal vs DR Congo", "NRG Stadium", "Houston", CENTRAL, 1, 1),
                    result("2026-06-17", "20:00", "Uzbekistan vs Colombia", "Estadio Azteca", "Mexico City", MEXICO, 1, 3),
                    match("2026-06-23", "12:00", "Portugal vs Uzbekistan", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-23", "20:00", "Colombia vs DR Congo", "Estadio Akron", "Zapopan", MEXICO),
                    match("2026-06-27", "19:30", "Colombia vs Portugal", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-27", "19:30", "DR Congo vs Uzbekistan", "Mercedes-Benz Stadium", "Atlanta", EASTERN)),
            group("Group L",
                    result("2026-06-17", "15:00", "England vs Croatia", "AT&T Stadium", "Arlington", CENTRAL, 4, 2),
                    result("2026-06-17", "19:00", "Ghana vs Panama", "BMO Field", "Toronto", TORONTO, 1, 0),
                    match("2026-06-23", "16:00", "England vs Ghana", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-23", "19:00", "Panama vs Croatia", "BMO Field", "Toronto", TORONTO),
                    match("2026-06-27", "17:00", "Panama vs England", "MetLife Stadium", "East Rutherford", EASTERN),
                    match("2026-06-27", "17:00", "Croatia vs Ghana", "Lincoln Financial Field", "Philadelphia", EASTERN))
    };

    private WorldCupFixtures() {
    }

    static CalendarGroup[] groups() {
        return GROUPS.clone();
    }

    static MatchStatus status(CalendarMatch match) {
        if (match.score() != null) {
            return MatchStatus.PLAYED;
        }
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime kickoff = match.kickoff();
        if (now.isBefore(kickoff.minusMinutes(30))) {
            return MatchStatus.TO_PLAY;
        }
        if (now.isBefore(kickoff.plusHours(2))) {
            return MatchStatus.LIVE;
        }
        return MatchStatus.NEEDS_RESULT;
    }

    static StandingRow[] standingsFor(CalendarGroup group) {
        Map<String, StandingAccumulator> table = new LinkedHashMap<>();
        for (String teamName : group.teamNames()) {
            table.put(teamName, new StandingAccumulator(teamFor(teamName)));
        }

        for (CalendarMatch match : group.matches()) {
            MatchScore score = match.score();
            if (score != null) {
                StandingAccumulator home = table.get(match.home());
                StandingAccumulator away = table.get(match.away());
                if (home != null && away != null) {
                    home.addMatch(score.homeGoals(), score.awayGoals());
                    away.addMatch(score.awayGoals(), score.homeGoals());
                }
            }
        }

        List<StandingRow> rows = new ArrayList<>();
        for (StandingAccumulator standing : table.values()) {
            rows.add(standing.toRow());
        }
        rows.sort(Comparator
                .comparingInt(StandingRow::points).reversed()
                .thenComparing(Comparator.comparingInt(StandingRow::goalDifference).reversed())
                .thenComparing(Comparator.comparingInt(StandingRow::goalsFor).reversed())
                .thenComparingInt(row -> standingPriority(row.team().name())));
        return rows.toArray(new StandingRow[0]);
    }

    static WorldCupTeam teamFor(String name) {
        WorldCupTeam team = WorldCupData.findByName(name);
        if (team != null) {
            return team;
        }
        String code = name.length() >= 3 ? name.substring(0, 3).toUpperCase(Locale.ENGLISH) : name.toUpperCase(Locale.ENGLISH);
        return new WorldCupTeam(name, code, "TBD", AppTheme.ACCENT, AppTheme.MUTED);
    }

    private static CalendarGroup group(String name, CalendarMatch... matches) {
        return new CalendarGroup(name, matches);
    }

    private static CalendarMatch match(String date, String time, String teams, String venue, String city, String zoneId) {
        return match(date, time, teams, venue, city, zoneId, null);
    }

    private static CalendarMatch result(String date, String time, String teams, String venue, String city, String zoneId, int homeGoals, int awayGoals) {
        return match(date, time, teams, venue, city, zoneId, new MatchScore(homeGoals, awayGoals));
    }

    private static CalendarMatch match(String date, String time, String teams, String venue, String city, String zoneId, MatchScore score) {
        String[] sides = teams.split(" vs ", 2);
        String home = sides.length > 0 ? sides[0] : teams;
        String away = sides.length > 1 ? sides[1] : "TBD";
        return new CalendarMatch(LocalDate.parse(date), LocalTime.parse(time), home, away, venue, city, zoneId, score);
    }

    private static int standingPriority(String teamName) {
        return switch (teamName) {
            case "Mexico" -> 1;
            case "South Korea" -> 2;
            case "Czech Republic" -> 3;
            case "South Africa" -> 4;
            case "Canada" -> 5;
            case "Switzerland" -> 6;
            case "Bosnia and Herzegovina" -> 7;
            case "Qatar" -> 8;
            case "Scotland" -> 9;
            case "Morocco" -> 10;
            case "Brazil" -> 11;
            case "Haiti" -> 12;
            case "United States" -> 13;
            case "Australia" -> 14;
            case "Turkey" -> 15;
            case "Paraguay" -> 16;
            case "Germany" -> 17;
            case "Ivory Coast" -> 18;
            case "Ecuador" -> 19;
            case "Curacao" -> 20;
            case "Sweden" -> 21;
            case "Netherlands" -> 22;
            case "Japan" -> 23;
            case "Tunisia" -> 24;
            case "Belgium" -> 25;
            case "Egypt" -> 26;
            case "Iran" -> 27;
            case "New Zealand" -> 28;
            case "Uruguay" -> 29;
            case "Saudi Arabia" -> 30;
            case "Spain" -> 31;
            case "Cape Verde" -> 32;
            case "Norway" -> 33;
            case "France" -> 34;
            case "Senegal" -> 35;
            case "Iraq" -> 36;
            case "Argentina" -> 37;
            case "Austria" -> 38;
            case "Jordan" -> 39;
            case "Algeria" -> 40;
            case "Colombia" -> 41;
            case "Portugal" -> 42;
            case "DR Congo" -> 43;
            case "Uzbekistan" -> 44;
            case "England" -> 45;
            case "Ghana" -> 46;
            case "Panama" -> 47;
            case "Croatia" -> 48;
            default -> 1000;
        };
    }

    record CalendarGroup(String name, CalendarMatch[] matches) {
        String[] teamNames() {
            LinkedHashSet<String> names = new LinkedHashSet<>();
            for (CalendarMatch match : matches) {
                names.add(match.home());
                names.add(match.away());
            }
            return names.toArray(new String[0]);
        }
    }

    record CalendarMatch(LocalDate date, LocalTime time, String home, String away, String venue, String city, String zoneId, MatchScore score) {
        ZonedDateTime kickoff() {
            return ZonedDateTime.of(date, time, ZoneId.of(zoneId));
        }
    }

    record MatchScore(int homeGoals, int awayGoals) {
    }

    record StandingRow(WorldCupTeam team, int played, int wins, int draws, int losses, int goalsFor, int goalsAgainst) {
        int points() {
            return wins * 3 + draws;
        }

        int goalDifference() {
            return goalsFor - goalsAgainst;
        }
    }

    enum MatchStatus {
        PLAYED("Jogado", new Color(0x61D394)),
        LIVE("Em jogo", new Color(0xF8D12F)),
        NEEDS_RESULT("Resultado pendente", new Color(0xEF6F6C)),
        TO_PLAY("Por jogar", new Color(0xB9C8D8));

        private final String label;
        private final Color color;

        MatchStatus(String label, Color color) {
            this.label = label;
            this.color = color;
        }

        String label() {
            return label;
        }

        Color color() {
            return color;
        }
    }

    private static final class StandingAccumulator {
        private final WorldCupTeam team;
        private int played;
        private int wins;
        private int draws;
        private int losses;
        private int goalsFor;
        private int goalsAgainst;

        private StandingAccumulator(WorldCupTeam team) {
            this.team = team;
        }

        private void addMatch(int scored, int conceded) {
            played++;
            goalsFor += scored;
            goalsAgainst += conceded;
            if (scored > conceded) {
                wins++;
            } else if (scored == conceded) {
                draws++;
            } else {
                losses++;
            }
        }

        private StandingRow toRow() {
            return new StandingRow(team, played, wins, draws, losses, goalsFor, goalsAgainst);
        }
    }
}
