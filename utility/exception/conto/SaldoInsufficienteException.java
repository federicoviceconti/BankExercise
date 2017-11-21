package gestionale_banca.utility.exception.conto;

public class SaldoInsufficienteException extends RuntimeException {
    private String mex;

    public SaldoInsufficienteException(String mex) {
        this.mex = mex;
    }

    @Override
    public String toString() {
        return "Attenzione: " + mex;
    }
}
