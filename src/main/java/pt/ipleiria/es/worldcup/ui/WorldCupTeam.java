package pt.ipleiria.es.worldcup.ui;

import java.awt.Color;

public record WorldCupTeam(String name, String code, String confederation, Color primaryColor, Color secondaryColor) {
    @Override
    public String toString() {
        return code + " - " + name;
    }
}
