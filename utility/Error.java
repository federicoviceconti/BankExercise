package gestionale_banca.utility;

public enum Error {
    CLIENTE("cliente"), MASTER("master");

    private final String name;

    Error(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
