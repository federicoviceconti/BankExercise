package gestionale_banca.view;

import gestionale_banca.controller.ClienteHandler;
import gestionale_banca.controller.ContoHandler;
import gestionale_banca.controller.Logger;
import gestionale_banca.model.Cliente;
import gestionale_banca.model.Conto;
import gestionale_banca.model.Session;
import gestionale_banca.utility.FileOperation;
import gestionale_banca.utility.exception.cliente.ClienteNonEsistenteException;
import gestionale_banca.utility.exception.conto.ContoEsistenteException;
import gestionale_banca.utility.exception.conto.ContoNonEsistenteException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

class Dashboard {
    private static Session.User session;
    private static Cliente cliente;
    private static ClienteHandler mainHandler;

    static void start(Session.User userSession, ClienteHandler handler) {
        mainHandler = handler;
        
        try {
            Logger.writeLogTransazione(LocalTime.now(),
                    "Accesso effettuato",
                    mainHandler.restituisciClienteDaEmail(userSession.getMail()).getNome());
        } catch (ClienteNonEsistenteException e) {
            System.out.println("Il cliente non esiste!");
        }

        session = userSession;
        int choose = 0;

        do {
            try {
                System.out.println("\n\n\n\n\n\n****Benvenuto nella dashboard di " + userSession + "****");
                System.out.println("1 - Crea conto\n2 - Visualizza conti\n3 - Effettua operazioni su conto\n4 - Elimina conto");

                choose = new Scanner(System.in).nextInt();

                switch (choose) {
                    case 1:
                        creaConto();
                        break;
                    case 2:
                        viewConto();
                        break;
                    case 3:
                        viewProfile();
                        break;
                    case 4:
                        eliminaConto();
                        break;
                }

                mainHandler.salva();
            }catch (InputMismatchException ime) {
                System.out.println("Input non valido!");
            }
        }while (choose > 0 && choose < 5);

    }

    private static void eliminaConto() {
        System.out.println("Inserisci nome conto: ");
        String nome = Main.scanner.nextLine();

        try {
            if(mainHandler.rimuoviConto(mainHandler.restituisciClienteDaEmail(session.getMail()), nome)) {
                System.out.println("Conto rimosso!");
            } else {
                System.out.println("Conto non esiste o non rimuovibile!");
            }
        } catch (ClienteNonEsistenteException e) {
            System.out.println("Il cliente non esiste!");
        }
    }

    private static void creaConto() {
        try {
            cliente = mainHandler.restituisciClienteDaEmail(session.getMail());
            System.out.println("Inserisci nome conto: ");

            mainHandler.addConto(cliente, Main.scanner.nextLine());
        } catch (ClienteNonEsistenteException | ContoEsistenteException e) {
            System.out.println("Errore! Utente non esistente o conto già esistente!");
        }
    }

    private static void viewConto() {
        try {
            cliente = mainHandler.restituisciClienteDaEmail(session.getMail());
            Arrays.stream(cliente.getConti()).forEach(System.out::println);
        } catch (ContoNonEsistenteException e) {
            System.out.println("Nessun conto disponibile!");
        } catch (ClienteNonEsistenteException e) {
            System.out.println("Cliente non esistente!");
        }
    }

    private static void viewProfile() {
        System.out.println("Inserisci nome conto: ");

        String nomeConto = Main.scanner.nextLine();
        Conto conto = mainHandler.restituisciContoDaCliente(session.getMail(), nomeConto);

        if(conto != null) {
            try {
                Cliente cliente = mainHandler.restituisciClienteDaEmail(session.getMail());
                String path = cliente.getNome() + "_" + cliente.getId();

                ContoHandler contoHandler = new ContoHandler(conto);
                try {
                    System.out.println("****Operazioni****\n1 - Deposita\n2 - Ritira\n");
                    switch (Main.scanner.nextInt()) {
                        case 1:
                            System.out.println("Indica la cifra da depositare");

                            if(!contoHandler.deposita(path, BigDecimal.valueOf(Main.scanner.nextInt()))) {
                                System.out.println("Soglia superata!");
                            }
                            break;
                        case 2:
                            System.out.println("Indica la cifra da ritirare");

                            if(contoHandler.ritira(path, BigDecimal.valueOf(Main.scanner.nextInt()))) {
                                System.out.println("Soglia superata!");
                            }
                            break;
                    }
                }catch (IllegalArgumentException iae) {
                    System.out.println("Input non valido.");
                }
            } catch (ClienteNonEsistenteException e) {
                e.printStackTrace();
            }

            return;
        }

        System.out.println("Nessun conto " + nomeConto + " trovato per " + session.getMail() + "!");
    }
}
