package gestionale_banca.model;

import java.math.BigDecimal;

public class Deposito extends Operazione {
    public Deposito(BigDecimal cifra) {
        super(cifra);
    }

    public BigDecimal getCifra() {
        return cifra;
    }

    public void setCifra(BigDecimal cifra) {
        this.cifra = cifra;
    }
}
