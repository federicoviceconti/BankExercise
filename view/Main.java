package gestionale_banca.view;

import gestionale_banca.controller.ClienteHandler;
import gestionale_banca.model.Session;
import gestionale_banca.utility.Validator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final ClienteHandler handler = new ClienteHandler();
    static final Scanner scanner = new Scanner(System.in);
    private static Session.User userSession;

    public static void main(String[] args) {
        start();
    }

    static void start() {
        userSession = null;

        try {
            int choose;

            do {
                System.out.println("\n\n\n\n\n****BANCA POPOLARE DI CERIGNOLA****");
                System.out.println("Lista delle possibili operazioni:");
                System.out.println("- 1: Crea account\n- 2: Login");

                choose = scanner.nextInt();
                doAction(choose);
            } while (choose > 0 && choose < 3);
        } catch (IllegalArgumentException | InputMismatchException iae) {
            System.out.println("Input non valido.");
        }
    }

    private static void doAction(int choose) throws IllegalArgumentException {
        switch (choose) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static void login() {
        System.out.println("Inserisci mail: ");
        scanner.nextLine(); //Porkaround

        String mail = scanner.nextLine();
        System.out.println("Inserisci pin: ");
        String pin = scanner.nextLine();

        String emailToStore = handler.login(mail, pin);

        if (emailToStore != null && !emailToStore.isEmpty()) {
            userSession = new Session.User(emailToStore);
            Dashboard.start(userSession, handler);
        }
    }

    private static void register() {
        System.out.println("Inserisci nome: ");
        scanner.nextLine();

        String nome = scanner.nextLine();
        System.out.println("Inserisci cognome: ");
        String cognome = scanner.nextLine();
        System.out.println("Inserisci mail: ");
        String mail = scanner.nextLine();
        System.out.println("Inserisci data di nascita [yyyy-mm-dd]: ");
        String ddn = scanner.nextLine();

        if (Validator.Field.checkIsNotEmptyOrNull(nome, cognome, mail, ddn)) {
            if (handler.registra(nome, cognome, mail, ddn) != null) {
                System.out.println("Utente registrato!");
            } else {
                System.out.println("Riprova!");
            }
        } else {
            System.out.println("Campi non inseriti correttamente!");
        }
    }
}