package gestionale_banca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Operazione implements Serializable {
    private LocalDateTime dataOperazione;
    protected BigDecimal cifra;

    public Operazione(BigDecimal cifra) {
        dataOperazione = LocalDateTime.now();
        this.cifra = cifra;
    }

    public BigDecimal getCifra() {
        return cifra;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"[Importo: " + cifra + ", Data operazione: " + dataOperazione + "]\n";
    }
}
