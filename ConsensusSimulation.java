
import java.util.*;

public class ConsensusSimulation {
    public static void main(String[] args) {
        List<Validator> validators = Arrays.asList(
            new Validator("Validator A"),
            new Validator("Validator B"),
            new Validator("Validator C")
        );

        simulatePoW(validators);
        simulatePoS(validators);
        simulateDPoS(validators);
    }

    static class Validator {
        String name;
        int power;
        int stake;

        Validator(String name) {
            this.name = name;
            this.power = new Random().nextInt(100) + 1;
            this.stake = new Random().nextInt(100) + 1;
        }
    }

    static void simulatePoW(List<Validator> validators) {
        Validator selected = Collections.max(validators, Comparator.comparingInt(v -> v.power));
        System.out.println("PoW selected: " + selected.name + " with power " + selected.power);
    }

    static void simulatePoS(List<Validator> validators) {
        Validator selected = Collections.max(validators, Comparator.comparingInt(v -> v.stake));
        System.out.println("PoS selected: " + selected.name + " with stake " + selected.stake);
    }

    static void simulateDPoS(List<Validator> validators) {
        String[] voters = {"Alice", "Bob", "Charlie", "Dave", "Eve"};
        Map<String, Integer> voteCount = new HashMap<>();
        for (Validator v : validators) {
            voteCount.put(v.name, 0);
        }

        Random rand = new Random();
        for (String voter : voters) {
            String vote = validators.get(rand.nextInt(validators.size())).name;
            voteCount.put(vote, voteCount.get(vote) + 1);
        }

        String selected = Collections.max(voteCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("DPoS selected: " + selected + " with votes " + voteCount.get(selected));
    }
}
