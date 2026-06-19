package pt.ipleiria.es.worldcup.ui;

import java.util.ArrayList;
import java.util.List;

final class TicketStore {
    private static final List<PurchasedTicket> TICKETS = new ArrayList<>(List.of(
            new PurchasedTicket("Brasil", "Marrocos", "JUN /08/ 2026", "ATLANTA", "Fase de grupos", 1, "BRA", "MAR"),
            new PurchasedTicket("Argentina", "Argélia", "JUN /08/ 2026", "VANCOUVER", "Fase de grupos", 1, "ARG", "ALG"),
            new PurchasedTicket("Costa do Marfim", "Equador", "JUN /08/ 2026", "GUADALAJARA", "Fase de grupos", 1, "CIV", "ECU"),
            new PurchasedTicket("Espanha", "Uruguai", "JUN /08/ 2026", "MIAMI", "Fase de grupos", 1, "ESP", "URU"),
            new PurchasedTicket("Portugal", "Colômbia", "JUN /08/ 2026", "ATLANTA", "Fase de grupos", 1, "POR", "COL")
    ));

    private TicketStore() {
    }

    static List<PurchasedTicket> snapshot() {
        return new ArrayList<>(TICKETS);
    }

    static void add(PurchasedTicket ticket) {
        TICKETS.add(ticket);
    }

    static void remove(PurchasedTicket ticket) {
        TICKETS.remove(ticket);
    }

    record PurchasedTicket(String home, String away, String date, String stadium, String phase, int quantity, String homeFlag, String awayFlag) {
    }
}
