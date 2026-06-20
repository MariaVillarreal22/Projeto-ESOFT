package pt.ipleiria.es.worldcup.ui;

public class Player {
    private String name;
    private String country;
    private int goals;
    private int yellowCards;
    private int redCards;

    public Player(String name, String country) {
        this.name = name;
        this.country = country;
        this.goals = 0;
        this.yellowCards = 0;
        this.redCards = 0;
    }

    public Player(String name, String country, int goals, int yellowCards, int redCards) {
        this.name = name;
        this.country = country;
        this.goals = goals;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }
    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }
    public int getRedCards() { return redCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }
}