package src.metier;

public class FonctionAux {
    
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        return true;
    }

    public static boolean isStringVide(String s) {
        if(s.equals("")) {
            return true;
        }
        return false;
    } 
    
}
