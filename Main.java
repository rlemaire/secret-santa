import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static final String SRC_FILENAME = "./couples.txt";

    public static void main(String[] args) throws IOException {
        List<String> participants = readParticipants();
        Map<String, String> couples = readCouples();
        Map<String, String> correspondance = new HashMap<>();
        Random random = new Random();
        for (String participant : participants) {
            List<String> receveursPossibles = new ArrayList<>(participants);
            // a participant cannot give a present to him or herself
            receveursPossibles.remove(participant);
            // a participant cannot receive or give a present from his boyfriend or girlfriend
            if (couples.containsKey(participant)) {
                receveursPossibles.remove(couples.get(participant));
            }
            // a participant cannot give its present to the participant who gave him his present
            if (correspondance.containsKey(participant)) {
                receveursPossibles.remove(correspondance.get(participant));
            }
            String receveur = receveursPossibles.get(random.nextInt(receveursPossibles.size()));
            correspondance.put(participant, receveur);
        }
        correspondance.forEach((a, b) -> {
            System.out.println(a + " -> " + b);
        });
    }

    private static Map<String, String> readCouples() throws IOException {
        Map<String, String> couples = new HashMap<>();
        Files.lines(Paths.get(SRC_FILENAME))
                .map(l -> l.split("/"))
                .filter(l -> l.length == 2)
                .forEach(line -> {
                    couples.put(line[0], line[1]);
                    couples.put(line[1], line[0]);
                });
        return couples;
    }

    private static List<String> readParticipants() throws IOException {
        List<String> participants = Files.lines(Paths.get(SRC_FILENAME))
                .flatMap(l -> Arrays.stream(l.split("/")))
                .toList();
        return participants;
    }
}
