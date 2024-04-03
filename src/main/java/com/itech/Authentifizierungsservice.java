
package com.itech;

import java.util.Optional;

public class Authentifizierungsservice {
    private DatenbankManager dbManager;

    public Authentifizierungsservice(DatenbankManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean existiertBenutzer(String benutzername) {
        return dbManager.holeBenutzerkonto(benutzername).isPresent();
    }

    public void registriereBenutzer(String benutzername) {
        if (!existiertBenutzer(benutzername)) {
            dbManager.speichereBenutzerkonto(new Benutzerkonto(benutzername, 0)); // Initialisiere Punkte mit 0
        }
    }

    public int holePunkte(String benutzername) {
        Optional<Benutzerkonto> benutzerkonto = dbManager.holeBenutzerkonto(benutzername);
        if (benutzerkonto.isPresent()) {
            return benutzerkonto.get().getPunkte();
        } else {
            return 0;
        }
    }
}

