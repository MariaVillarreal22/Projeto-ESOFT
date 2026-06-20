package pt.ipleiria.es.worldcup.ui;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private String confederation;
    private String cluster;
    private int matches;
    private int victories;
    private int ties;
    private int defeats;
    private int goalsFor;
    private int goalsAgainst;
    private int points;
    private List<Player> players;

    public Team(String name, String confederation, String cluster) {
        this.name = name;
        this.confederation = confederation;
        this.cluster = cluster;
        this.matches = 0;
        this.victories = 0;
        this.ties = 0;
        this.defeats = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.points = 0;
        this.players = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getConfederation() { return confederation; }
    public void setConfederation(String confederation) { this.confederation = confederation; }
    public String getCluster() { return cluster; }
    public void setCluster(String cluster) { this.cluster = cluster; }
    public int getMatches() { return matches; }
    public void setMatches(int matches) { this.matches = matches; }
    public int getVictories() { return victories; }
    public void setVictories(int victories) { this.victories = victories; }
    public int getTies() { return ties; }
    public void setTies(int ties) { this.ties = ties; }
    public int getDefeats() { return defeats; }
    public void setDefeats(int defeats) { this.defeats = defeats; }
    public int getGoalsFor() { return goalsFor; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public List<Player> getPlayers() { return players; }
    public void addPlayer(Player player) { this.players.add(player); }
    public int getBalance() { return goalsFor - goalsAgainst; }
}