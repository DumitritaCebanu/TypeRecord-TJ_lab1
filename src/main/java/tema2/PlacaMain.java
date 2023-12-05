package tema2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlacaMain {

    public static List<Mobilier> read() {
        try {
            File file = new File("src/main/resources/mobilier.json");
            ObjectMapper mapper = new ObjectMapper();
            List<Mobilier> mobilierList = mapper.readValue(file, new TypeReference<>() {});
            if (mobilierList != null && !mobilierList.isEmpty()) {
                return mobilierList;
            } else {
                System.err.println("Fisierul este gol.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //a
    public static void show_items(List<Mobilier> mobilierList){
        if (mobilierList.isEmpty()) {
            System.out.println("Fisierul este gol.");
        } else {
            mobilierList.forEach(mobilier -> System.out.println("Nume mobilier: " + mobilier.nume() + ", Placi: " + mobilier.placi()));
        }
    }

    //b
    public static void show_item_component(List<Mobilier> mobilierList){
        if (mobilierList.isEmpty()){
            System.out.println("Fisierul este gol");
        }else {
            mobilierList.forEach(mobilier -> {
                System.out.println("Num mobiliere: " + mobilier.nume());
                mobilier.placi().forEach(placa -> System.out.println("  Placa: " + placa.descriere()));
            });
        }
    }

    //c
    public static void characteristics( List<Mobilier> mobilierList, String nume) {
        Optional<Mobilier> mobilierCautat = mobilierList.stream()
                .filter(mobilier -> mobilier.nume().equalsIgnoreCase(nume))
                .findFirst();
        mobilierCautat.ifPresent(mobilier -> {
            System.out.println("Caracteristicile piesei de mobilier " + mobilier.nume() + ":");
            mobilier.placi().forEach(placa -> {
                System.out.println("  Descriere: " + placa.descriere());
                System.out.println("  Lungime: " + placa.lungime());
                System.out.println("  Latime: " + placa.latime());
                System.out.println("  Orientare: " + placa.orientare());
                System.out.println("  Canturi: " + Arrays.toString(placa.canturi()));
                System.out.println("  Numar bucăți: " + placa.nr_bucati());
            });
        });
    }

    //d
    public static void estimated_number( List<Mobilier> mobilierList, String produs){
        Optional<Mobilier> mobilierItem = mobilierList.stream()
                .filter(mobilier -> mobilier.nume().equalsIgnoreCase(produs))
                .findFirst();

        mobilierItem.ifPresent(mobilier -> {
            int numarColiNecesari = mobilier.placi().stream()
                    .mapToInt(placa -> (int) Math.ceil((double) placa.lungime() * placa.latime() / (2800 * 2070)))
                    .sum();
            System.out.println("Estimare număr colilor de pal necesari pentru " + mobilier.nume() + ": " + numarColiNecesari);
        });
    }

    public static void main(String[] args) {
        List<Mobilier> mobilier = read();
        assert mobilier != null;
        show_items(mobilier);
        show_item_component(mobilier);
        characteristics(mobilier, "mobilier corp 1");
        estimated_number(mobilier, "dulap 1");

    }
}