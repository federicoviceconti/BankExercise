package gestionale_banca.model;

import gestionale_banca.utility.exception.conto.ContoNonEsistenteException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * User class --------------------------------------------------
 * I've created one constructor because all fields are mandatory
 */
public class Cliente implements Serializable{
    private int id;
    private String nome, cognome, email, pin;
    private LocalDate ddn;
    private Conto[] conti;

    public Cliente(int id, String nome, String cognome, String email, String pin, LocalDate ddn) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.pin = pin;
        this.ddn = ddn;
    }

    public boolean addConto(Conto conto) {
        if(conti == null) {
            conti = new Conto[2];
        }

        for(int i = 0; i < conti.length; i++) {
            if(conti[i] == null) {
                conti[i] = conto;
                return true;
            }
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPin() {
        return pin;
    }

    public Conto[] getConti() throws ContoNonEsistenteException {
        if(conti != null) {
            return conti;
        }

        throw new ContoNonEsistenteException("Non esistono conti per: " + getNome());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", conti=" + Arrays.toString(conti) +
                '}';
    }
}
