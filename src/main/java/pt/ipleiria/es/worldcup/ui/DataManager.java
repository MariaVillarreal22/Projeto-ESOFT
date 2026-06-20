package pt.ipleiria.es.worldcup.ui;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Team> teams;
    private List<Player> allPlayers;

    private DataManager() {
        teams = new ArrayList<>();
        allPlayers = new ArrayList<>();
        loadDefaultData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadDefaultData() {
        // Equipos de ejemplo
        Team spain = new Team("Spain", "UEFA", "B");
        spain.setMatches(5);
        spain.setVictories(4);
        spain.setTies(1);
        spain.setDefeats(0);
        spain.setGoalsFor(21);
        spain.setGoalsAgainst(9);
        spain.setPoints(13);
        teams.add(spain);

        Team portugal = new Team("Portugal", "UEFA", "F");
        portugal.setMatches(6);
        portugal.setVictories(3);
        portugal.setTies(2);
        portugal.setDefeats(1);
        portugal.setGoalsFor(19);
        portugal.setGoalsAgainst(10);
        portugal.setPoints(11);
        teams.add(portugal);

        Team france = new Team("France", "UEFA", "D");
        france.setMatches(5);
        france.setVictories(2);
        france.setTies(3);
        france.setDefeats(0);
        france.setGoalsFor(20);
        france.setGoalsAgainst(12);
        france.setPoints(9);
        teams.add(france);

        // Jugadores de ejemplo
        Player messi = new Player("Lionel Andrés Messi Cuccittini", "Argentina", 17, 2, 0);
        Player mbappe = new Player("Kilian Sanmi Mbappé Lottin", "France", 17, 1, 0);
        Player vitor = new Player("Vítor Machado Ferreira", "Portugal", 12, 3, 0);
        Player pedro = new Player("Pedro González López", "Spain", 20, 2, 0);
        Player michael = new Player("Michael Akpovi O Olise", "France", 17, 1, 0);

        allPlayers.add(messi);
        allPlayers.add(mbappe);
        allPlayers.add(vitor);
        allPlayers.add(pedro);
        allPlayers.add(michael);
    }

    // ==================== EQUIPOS ====================
    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Team getTeamByName(String name) {
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    // ==================== JUGADORES ====================
    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public void addPlayer(Player player) {
        allPlayers.add(player);
    }

    // ==================== ESTADÍSTICAS ====================
    public int getTotalGoals() {
        int total = 0;
        for (Team t : teams) {
            total += t.getGoalsFor();
        }
        return total;
    }

    public double getAverageGoals() {
        if (teams.isEmpty()) return 0;
        return (double) getTotalGoals() / teams.size();
    }

    public int getTotalYellowCards() {
        int total = 0;
        for (Player p : allPlayers) {
            total += p.getYellowCards();
        }
        return total;
    }

    public int getTotalRedCards() {
        int total = 0;
        for (Player p : allPlayers) {
            total += p.getRedCards();
        }
        return total;
    }
}