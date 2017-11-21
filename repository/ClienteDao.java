package gestionale_banca.repository;

import gestionale_banca.model.Cliente;
import gestionale_banca.model.Conto;
import gestionale_banca.utility.exception.cliente.ClienteNonEsistenteException;
import gestionale_banca.utility.exception.conto.ContoEsistenteException;
import gestionale_banca.utility.exception.conto.ContoNonEsistenteException;

import java.io.IOException;

public interface ClienteDao {
    /**
     * Permit to register an user
     * @param cliente user to register
     * @return if user will be registered
     */
    boolean registra(Cliente cliente);

    /**
     * Allow user to log into account
     * @param email unique email for user
     * @param pin unique pin for user
     * @return if user is logged on, return 'cliente'
     */
    boolean login(String email, String pin);

    /**
     * Add an account for user
     * We can insert only <b>two</b> accounts
     * @param cliente user account
     * @return
     */
    Conto aggiungiConto(Cliente cliente, String nomeConto) throws ClienteNonEsistenteException, ContoEsistenteException;

    /**
     * Remove an user's account by contoId
     * @param cliente selected user
     * @param nome unique name for account
     * @return
     */
    boolean rimuoviConto(Cliente cliente, String nome);

    /**
     * Remove an user's account by him account
     * @param cliente selected user
     * @param conto account object, to remove
     * @return
     */
    boolean rimuoviConto(Cliente cliente, Conto conto);

    /**
     * Return a new id, that is different from each other
     * @return return the new id
     */
    int registerNewId();

    /**
     * Return a new pin, that is compose by 5 digit
     * @return return the new pin
     */
    String registerNewPin();

    /**
     * Retrieve user by him email
     * @param mail selected user
     * @return return user
     */
    Cliente searchUserByMail(String mail);

    /**
     * Get account by him name
     * @param cliente selected user
     * @param nomeConto account string name
     * @return selected account
     * @throws ContoNonEsistenteException
     */
    Conto getContoByNomeConto(Cliente cliente, String nomeConto) throws ContoNonEsistenteException;

    /**
     * Call method into FileOperation to write into our fake db
     */
    void salva() throws IOException;
}

