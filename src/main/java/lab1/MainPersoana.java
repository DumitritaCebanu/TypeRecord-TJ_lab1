package lab1;

public class MainPersoana {
    public static void main(String[] args) {
        Persoana p1 = new Persoana("Oana", 23);
        Persoana p2 = new Persoana("Dumi", 24);
        System.out.println(p1);
        System.out.println("Persoana cu numele " + p1.nume() + " are varsta " + p1.varsta());
        System.out.println("Persoana cu numele " + p2.nume() + " are varsta " + p2.varsta());
        System.out.println(p1.equals(p2));
    }
}
