package gestionale_banca.repository;

import java.math.BigDecimal;

/**
 * Operation to implement
 */
public interface OperazioneDao {
    /**
     * This method help user to put money into bank account
     * @param daDepositare money to put (if them are insufficient, an exception is thrown)
     */
    boolean deposita(BigDecimal daDepositare);

    /**
     * This method help user to retrieve money from bank account
     * @param daRitirare money to retrieve from account (if them are insufficient, an exception is throw)
     */
    boolean ritira(BigDecimal daRitirare);
}

