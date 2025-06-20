
import java.security.MessageDigest;
import java.time.LocalDateTime;

public class MiningSimulation {
    public static void main(String[] args) {
        Block block = new Block(1, "Mining Block", "0");
        int difficulty = 4;
        long startTime = System.currentTimeMillis();
        block.mineBlock(difficulty);
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - startTime) + " ms");
    }
}

class Block {
    public int index;
    public String timestamp;
    public String data;
    public String previousHash;
    public String hash;
    public int nonce;

    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
        this.previousHash = previousHash;
        this.hash = "";
        this.nonce = 0;
    }

    public void mineBlock(int difficulty) {
        String prefix = new String(new char[difficulty]).replace(' ', '0');
        int attempts = 0;
        while (true) {
            this.hash = calculateHash();
            attempts++;
            if (this.hash.substring(0, difficulty).equals(prefix)) {
                System.out.println("Block mined! Hash: " + this.hash);
                System.out.println("Nonce: " + this.nonce);
                System.out.println("Attempts: " + attempts);
                break;
            }
            this.nonce++;
        }
    }

    public String calculateHash() {
        String input = index + timestamp + data + previousHash + nonce;
        return applySha256(input);
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
