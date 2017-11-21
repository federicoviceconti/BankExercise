package gestionale_banca.model;

import gestionale_banca.utility.exception.conto.SaldoInsufficienteException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Conto implements Serializable{
    private int id;
    private String nome;
    private BigDecimal saldo;
    private List<Operazione> operazioni;

    public Conto(String nome) {
        this.nome = nome;
        this.saldo = BigDecimal.ZERO;
        operazioni = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void addToSaldo(BigDecimal toAdd) {
        this.saldo = this.saldo.add(toAdd);
        operazioni.add(new Deposito(toAdd));
    }

    public void ritiroDaSaldo(BigDecimal daRitirare) throws SaldoInsufficienteException {
        if(saldo.subtract(daRitirare).intValue() < 0) {
            throw new SaldoInsufficienteException("Denaro insufficiente, transazione negata!");
        }

        this.saldo = this.saldo.add(daRitirare);
        operazioni.add(new Ritiro(daRitirare));
    }

    public List<Operazione> getOperazioni() {
        return operazioni;
    }

    @Override
    public String toString() {
        return "***** CONTO *****" +
                "\nNome: " + nome +
                "\nSaldo: " + saldo.toString() +
                "\nOperazioni:\n" + operazioni;
    }
}
