package gestionale_banca.repository;

import gestionale_banca.controller.Logger;
import gestionale_banca.model.Cliente;
import gestionale_banca.model.Conto;
import gestionale_banca.utility.Error;
import gestionale_banca.utility.FileOperation;
import gestionale_banca.utility.exception.cliente.ClienteNonEsistenteException;
import gestionale_banca.utility.exception.conto.ContoEsistenteException;
import gestionale_banca.utility.exception.conto.ContoNonEsistenteException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClienteDaoImpl implements ClienteDao {
    private List<Cliente> clienti;

    public ClienteDaoImpl() {
        this.clienti = FileOperation.ReadFromFile.read();
    }

    @Override
    public boolean registra(Cliente cliente) {
        if (cliente != null) {
            clienti.add(cliente);
            return FileOperation.WriteOnFile.write(clienti);
        }

        return false;
    }

    @Override
    public boolean login(String email, String pin) {
        return clienti.parallelStream()
                .filter(c -> c.getEmail().equals(email) && c.getPin().equals(pin))
                .findFirst().orElse(null) != null;
    }

    @Override
    public Conto aggiungiConto(Cliente cliente, String nomeConto) throws ClienteNonEsistenteException, ContoEsistenteException {
        Cliente c = clienti.parallelStream().filter(x -> x.getEmail().equals(cliente.getEmail())).findFirst().orElse(null);

        if (c != null) {
            Conto toAdd = new Conto(nomeConto);

            if (c.addConto(toAdd)) {
                return toAdd;
            } else {
                throw new ContoEsistenteException("Il conto già esiste o il numero massimo è stato superato, controlla il profilo!");
            }
        }

        throw new ClienteNonEsistenteException("Il cliente non esiste");
    }

    @Override
    public boolean rimuoviConto(Cliente cliente, String nome) {
        try {
            Conto[] conti = cliente.getConti();

            for (int i = 0; i < conti.length; i++) {
                if (conti[i].getNome().equals(nome)) {
                    conti[i] = null;
                    return true;
                }
            }
        } catch (ContoNonEsistenteException e) {
            Logger.write(LocalTime.now(), e.getMessage(), Error.CLIENTE);
            return false;
        }

        return false;
    }

    @Override
    public boolean rimuoviConto(Cliente cliente, Conto conto) {
        try {
            Conto[] conti = cliente.getConti();

            for (int i = 0; i < conti.length; i++) {
                if (conti[i] == conto) {
                    conti[i] = null;
                    return true;
                }
            }
        } catch (ContoNonEsistenteException e) {
            Logger.write(LocalTime.now(), e.getMessage(), Error.CLIENTE);
            return false;
        }

        return false;
    }

    @Override
    public int registerNewId() {
        //Return the maximum id of user list
        Cliente c = clienti.stream().max(Comparator.comparing(Cliente::getId)).orElse(null);

        if(c != null) {
            return c.getId() + 1;
        }

        return 1;
    }

    @Override
    public String registerNewPin() {
        int newPin = ((int) (Math.random() * 99999)) + 1;

        //Generated number cannot be less than 0
        assert newPin > 0;
        return fillPin(newPin);
    }

    @Override
    public Cliente searchUserByMail(String mail) {
        return clienti.parallelStream().filter(x -> x.getEmail().equals(mail)).findFirst().orElse(null);
    }

    @Override
    public Conto getContoByNomeConto(Cliente cliente, String nomeConto) throws ContoNonEsistenteException {
        return Arrays.stream(cliente.getConti()).filter(x -> x != null && x.getNome().equals(nomeConto)).findFirst().orElse(null);
    }

    @Override
    public void salva() throws IOException {
        if(!FileOperation.WriteOnFile.write(clienti)) {
            throw new IOException("File non salvato correttamente!");
        }
    }

    /**
     * This method is used to generate new pin for user, if digit are 5, I returned the pin directly
     * @param pin the integer value, greater than 0
     * @return
     */
    private String fillPin(int pin) {
        if(pin > 9999) {
            return String.valueOf(pin);
        }

        StringBuilder s = new StringBuilder(String.valueOf(pin));

        do {
            s.insert(0, "0");
        }while(s.length() < 5);

        return s.toString();
    }
}
