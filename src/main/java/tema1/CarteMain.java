package tema1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;


public class CarteMain {

    // a
    public static Map<Integer, Carte> readJSON() {
        Map<Integer, Carte> map_book = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            File books_file = new File("src/main/resources/carti.json");
            if (books_file.exists()) {
                TypeReference<Map<String, Carte>> typeRef = new TypeReference<>() {
                };
                Map<String, Carte> booksMap = objectMapper.readValue(books_file, typeRef);
                int id = 1;
                for (Carte carte : booksMap.values()) {
                    map_book.put(id++, carte);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map_book;
    }

    // d
    private static void writeJSON(Map<Integer, Carte> map_book) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            File file_books = new File("src/main/resources/carti.json");
            Map<String, Carte> booksMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Carte> entry : map_book.entrySet()) {
                booksMap.put(Integer.toString(entry.getKey()), entry.getValue());
            }
            objectMapper.writeValue(file_books, booksMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void saveToJSON(Map<Integer, Carte> books, String fileName) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//            try {
//                File file = new File(fileName);
//                objectMapper.writeValue(file, books);
//                System.out.println("Data saved to " + fileName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        }

    // a
    public static void display_collection(Map<Integer, Carte> books) {
        System.out.println("Book collection:");
        //books.forEach((id, carte) -> System.out.println(id + ": " + carte));
        for (Map.Entry<Integer, Carte> entry : books.entrySet()) {
            int id = entry.getKey();
            Carte book = entry.getValue();
            System.out.println(id + " : " + book.titlu() + " de " + book.autor() + " publicata in " + book.an());
        }
        System.out.println("\n");
    }

    // b
    public static void delete_from_collection(Map<Integer, Carte> books, int id) {
        if (books.containsKey(id)){
            books.entrySet().removeIf(integerCarteEntry -> integerCarteEntry.getKey().equals(id));
            System.out.println("Cartea cu id-ul - " + id + " a fost stearsa\n");
            display_collection(books);
            writeJSON(books);
        } else {
            System.out.println("Cartea cu id-ul - " + id + " nu exista in colectie\n");
            }
    }

    // c
    public static void add_book(HashMap<Integer, Carte> books, Carte book){
        int id = books.size() + 1;
        books.putIfAbsent(id, book);
        System.out.println("Cartea: " + book.titlu() + " de " + book.autor() + " a fost adaugata");
        writeJSON(books);
    }

    //e
    public static void author_book(Map<Integer, Carte> books, String author){
        Set<Carte> author_book = books.values().stream()
                .filter(book -> "Yuval Noah Harari".equals(book.autor()))
                .collect(Collectors.toSet());

        System.out.println("Cărțile autorului " + author + " :");
        author_book.forEach(book -> System.out.println(" - " + book.titlu() + " publicata in " + book.an()));
    }

    //f
    public static void sort_by_title(Map<Integer, Carte> books_json) {
        Set<Carte> set_books = new HashSet<>(books_json.values());
        System.out.println("Cărțile ordonate după titlu:");
        set_books.stream()
                .sorted(Comparator.comparing(Carte::titlu))
                .forEach(book -> System.out.println(" - " + book.titlu() + " de " + book.autor() + " publicata in " + book.an()));
    }

    //g
    public static void oldest_book(Map<Integer, Carte> books){
        Optional<Carte> oldest_book = books.values().stream()
                .min(Comparator.comparing(Carte::an));

        if (oldest_book.isPresent()) {
            System.out.println("\nCea mai veche carte din colecție:");
            System.out.println(oldest_book.get().titlu() + " de " + oldest_book.get().autor() + " publicata in anul " + oldest_book.get().an());
        } else {
            System.out.println("\nNu există cărți în colecție.");
        }
    }

    public static void main(String[] args){

        HashMap<Integer, Carte> books = (HashMap<Integer, Carte>) readJSON();
        display_collection(books);
        delete_from_collection(books, 7);
        add_book(books, new Carte("1984", "George Orwell", 1899));
        author_book(books, "Yuval Noah Harari");
        sort_by_title(books);
        oldest_book(books);

    }
}