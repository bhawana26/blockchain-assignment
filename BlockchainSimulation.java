
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        this.nonce = 0;
        this.hash = calculateHash();
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

    public String toString() {
        return "Block " + index + "\nTimestamp: " + timestamp + "\nData: " + data +
               "\nPrevious Hash: " + previousHash + "\nHash: " + hash + "\n";
    }
}

public class BlockchainSimulation {
    public static void main(String[] args) {
        ArrayList<Block> blockchain = new ArrayList<>();

        Block genesis = new Block(0, "Genesis Block", "0");
        blockchain.add(genesis);

        for (int i = 1; i <= 2; i++) {
            Block newBlock = new Block(i, "Block " + i + " Data", blockchain.get(i - 1).hash);
            blockchain.add(newBlock);
        }

        for (Block block : blockchain) {
            System.out.println(block);
        }

        System.out.println("Tampering Block 1...");
        blockchain.get(1).data = "Tampered Data";
        blockchain.get(1).hash = blockchain.get(1).calculateHash();

        System.out.println("Rechecking Blockchain Validity...");
        for (Block block : blockchain) {
            System.out.println(block);
        }
    }
}
