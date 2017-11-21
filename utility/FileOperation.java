package gestionale_banca.utility;

import gestionale_banca.model.Cliente;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {
    private static final String DB_PATH = "src/gestionale_banca/file/db/user.txt";
    private static final String LOG_TRANSAZIONI = "src/gestionale_banca/file/transazioni";
    public static final String LOG_PATH_CLIENTI = "src/gestionale_banca/file/log/clienti/log.txt";
    public static final String LOG_PATH_MASTER = "src/gestionale_banca/file/log/master/log.txt";

    public static class ReadFromFile {
        public static List<Cliente> read() {
            List<Cliente> result = new ArrayList<>();

            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(DB_PATH))) {

                result = (List<Cliente>) input.readObject();

                System.out.println("List: " + result);
            } catch (EOFException eofEx) {
                System.out.println("Fine file!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Attenzione: " + e.getMessage());
            }

            return result;
        }
    }

    public static class WriteOnFile {
        public static boolean write(List<Cliente> list) {
            try (ObjectOutputStream input = new ObjectOutputStream(
                    new FileOutputStream(Paths.get(DB_PATH).toFile()))) {
                input.writeObject(list);
            } catch (EOFException eofEx) {
                System.out.println("Fine file!");
                return false;
            } catch (IOException e) {
                System.out.println("Attenzione: " + e.getMessage());
                return false;
            }

            return true;
        }

        public static void writeLogCliente(String message) {
            if (Files.exists(Paths.get(LOG_PATH_CLIENTI))) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(Paths.get(LOG_PATH_CLIENTI).toFile(), true))) {
                    bufferedWriter.write(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void writeLogMaster(String message) {
            if (Files.exists(Paths.get(LOG_PATH_MASTER))) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(Paths.get(LOG_PATH_MASTER).toFile(), true))) {
                    bufferedWriter.write(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void writeLogTransazione(String message, String nomeCliente) {
            boolean toAppend = Files.exists(Paths.get(LOG_TRANSAZIONI + "/" + nomeCliente));

            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(Paths.get(LOG_TRANSAZIONI + "/" + nomeCliente).toFile(), toAppend))) {
                bufferedWriter.write(message + " | [ nomeCliente = " + nomeCliente  + "]\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
