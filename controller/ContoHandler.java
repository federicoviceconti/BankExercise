package gestionale_banca.controller;

import gestionale_banca.model.Conto;
import gestionale_banca.repository.OperazioneDaoImpl;

import java.math.BigDecimal;
import java.time.LocalTime;

public class ContoHandler {
    private OperazioneDaoImpl operazioneDao;

    public ContoHandler(Conto conto) {
        operazioneDao = new OperazioneDaoImpl(conto);
    }

    public boolean deposita(String nome, BigDecimal daDepositare) {
        String op;
        boolean result;

        if(operazioneDao.deposita(daDepositare)) {
            result = true;
            op = "Deposito effettuato a nome di " + nome +
                    "dell' importo di: " + daDepositare.toString();
        } else {
            result = false;
            op = "Deposito negato per saldo insufficente a " + nome +
                    "dell' importo di: " + daDepositare.toString();
        }

        Logger.writeLogTransazione(LocalTime.now(), op, nome);
        return result;
    }

    public boolean ritira(String nome, BigDecimal daRitirare) {
        String op;
        boolean result;

        if(operazioneDao.ritira(daRitirare)) {
            result = true;
            op = "Ritiro effettuato a nome di " + nome +
            "dell' importo di: " + daRitirare.toString();
        } else {
            result = false;
            op = "Ritiro negato per saldo insufficente a " + nome +
                    "dell' importo di: " + daRitirare.toString();
        }

        Logger.writeLogTransazione(LocalTime.now(), op, nome);
        return result;
    }
}
