package gestionale_banca.controller;

import gestionale_banca.utility.Error;
import gestionale_banca.utility.FileOperation;
import java.time.LocalTime;

public class Logger {
    public static void write(LocalTime now, String message, Error auth) {
        FileOperation.WriteOnFile.writeLogCliente("Errore " + auth.getName() + " alle ore: "
                + now + " | Testo: " + message + "\n");
    }

    public static void writeLogTransazione(LocalTime now, String message, String nome) {
        FileOperation.WriteOnFile.writeLogTransazione("Data: " + now + ", Messaggio: " + message, nome);
    }
}
