package tema2;

public record Placa(String descriere, int lungime, int latime, Orientare orientare, boolean[] canturi, int nr_bucati) {}

// Enumerarea optiunilr  pentru orientare
enum Orientare {
    LUNGIME,
    LATIME,
    ORICARE
}