package gestionale_banca.model;

public class Session {
    public static class User {
        private String mail;

        public User(String mail) {
            this.mail = mail;
        }

        public String getMail() {
            return mail;
        }

        @Override
        public String toString() {
            return mail;
        }
    }
}
