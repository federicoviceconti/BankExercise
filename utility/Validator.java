package gestionale_banca.utility;

public class Validator {
    public static class Field {
        public static boolean checkIsNotEmptyOrNull(String... fields) {
            for (String field: fields) {
                if(field == null || field.isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }
}
