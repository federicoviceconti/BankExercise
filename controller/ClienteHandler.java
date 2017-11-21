package gestionale_banca.controller;

import gestionale_banca.model.Cliente;
import gestionale_banca.model.Conto;
import gestionale_banca.repository.ClienteDao;
import gestionale_banca.repository.ClienteDaoImpl;
import gestionale_banca.utility.Error;
import gestionale_banca.utility.FileOperation;
import gestionale_banca.utility.Validator;
import gestionale_banca.utility.exception.cliente.ClienteNonEsistenteException;
import gestionale_banca.utility.exception.cliente.ClienteNonMaggiorenne;
import gestionale_banca.utility.exception.conto.ContoEsistenteException;
import gestionale_banca.utility.exception.conto.ContoNonEsistenteException;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClienteHandler {
    private ClienteDao clienteDao;

    public ClienteHandler() {
        clienteDao = new ClienteDaoImpl();
    }

    public Cliente restituisciClienteDaEmail(String email) throws ClienteNonEsistenteException {
        Cliente cliente = clienteDao.searchUserByMail(email);

        if (cliente != null) {
            return cliente;
        }

        throw new ClienteNonEsistenteException("Cliente non trovato!");
    }

    public boolean rimuoviConto(Cliente cliente, String nomeConto) {
        return clienteDao.rimuoviConto(cliente, nomeConto);
    }

    public Conto restituisciContoDaCliente(String email, String conto) {
        try {
            Cliente cliente = restituisciClienteDaEmail(email);
            return clienteDao.getContoByNomeConto(cliente, conto);
        } catch (ClienteNonEsistenteException | ContoNonEsistenteException e) {
            Logger.write(LocalTime.now(), e.getMessage(), Error.CLIENTE);
        }

        return null;
    }

    /**
     * This method allow to register user
     * @param nome
     * @param cognome
     * @param email
     * @param ddn
     * @return unique user's mail, to keep into the current session or null if not found
     */
    public String registra(String nome, String cognome, String email, String ddn) {
        if(Validator.Field.checkIsNotEmptyOrNull(nome, cognome, email, ddn)
                && clienteDao.searchUserByMail(email) == null) {
            int id = clienteDao.registerNewId();
            String pin = clienteDao.registerNewPin();

            nome = nome.trim();
            cognome = cognome.trim();
            email = email.trim();
            LocalDate dataNascita = LocalDate.parse(ddn.trim());

            try {
                if((LocalDate.now().getYear() - dataNascita.getYear()) < 18) {
                    throw new ClienteNonMaggiorenne("Cliente non maggiorenne!");
                }

                Cliente cliente = new Cliente(id, nome, cognome, email, pin, dataNascita);
                System.out.println("Pin: " + pin);

                if(clienteDao.registra(cliente)) {
                    Logger.writeLogTransazione(LocalTime.now(),
                            "Utente " + cliente.getNome() + " registrato",
                            cliente.getNome() + "_" + cliente.getId());
                    return cliente.getEmail();
                }
            } catch (DateTimeException ex) {
                System.out.println("Data non valida!");
            } catch (ClienteNonMaggiorenne clienteNonMaggiorenne) {
                System.out.println(clienteNonMaggiorenne.toString());
            }

            return null;
        }

        System.out.println("Campi non riempiti correttamente o email già registrata");
        return null;
    }

    /**
     * This method allow to register into account
     * @return unique user's email or null if not found
     */
    public String login(String mail, String pin) {
        return Validator.Field.checkIsNotEmptyOrNull(mail, pin) && clienteDao.login(mail, pin) ? mail : null;
    }

    public void addConto(Cliente cliente, String nome) throws ClienteNonEsistenteException, ContoEsistenteException {
        clienteDao.aggiungiConto(cliente, nome);
        Logger.writeLogTransazione(
                LocalTime.now(),
                "Conto aggiunto a nome di " + nome,
                cliente.getNome() + "_" + cliente.getId());

    }

    public void salva() {
        try {
            clienteDao.salva();
        } catch (IOException e) {
            FileOperation.WriteOnFile.writeLogMaster(e.getMessage());
        }
    }
}
