package gestionale_banca.utility.exception.cliente;

public class ClienteNonMaggiorenne extends Throwable {
    private String mex;

    public ClienteNonMaggiorenne(String mex) {
        this.mex = mex;
    }

    @Override
    public String toString() {
        return "Attenzione: " + mex;
    }
}
