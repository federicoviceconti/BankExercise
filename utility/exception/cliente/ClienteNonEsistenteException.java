package gestionale_banca.utility.exception.cliente;

public class ClienteNonEsistenteException extends Exception {
    private String mex;

    public ClienteNonEsistenteException(String mex) {
        this.mex = mex;
    }

    @Override
    public String toString() {
        return "Attenzione: " + mex;
    }
}
