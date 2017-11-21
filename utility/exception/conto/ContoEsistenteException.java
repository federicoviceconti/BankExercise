package gestionale_banca.utility.exception.conto;

public class ContoEsistenteException extends Exception {
    private String mex;

    public ContoEsistenteException(String mex) {
        super(mex);
        this.mex = mex;
    }

    @Override
    public String toString() {
        return "Attenzione: " + mex;
    }
}
