package gestionale_banca.utility.exception.conto;

public class ContoNonEsistenteException extends NullPointerException {
    private String mex;

    public ContoNonEsistenteException(String mex) {
        this.mex = mex;
    }

    @Override
    public String toString() {
        return "Attenzione: " + mex;
    }
}
