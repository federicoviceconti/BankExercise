package gestionale_banca.model;

import java.math.BigDecimal;

public class Ritiro extends Operazione {
    public Ritiro(BigDecimal cifra) {
        super(cifra);
        this.cifra = cifra;
    }

    public BigDecimal getcifra() {
        return cifra;
    }

    public void setcifra(BigDecimal cifra) {
        this.cifra = cifra;
    }
}
