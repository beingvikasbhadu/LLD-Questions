import java.io.*;

public class SerializationTest {

    public static void main(String[] args) {
        System.out.println("=== Serialization Test ===\n");

        try {
            // Get instance
            DatabaseConfig instance1 = DatabaseConfig.getInstance();
            instance1.setDb_url("production-db");
            instance1.setPort("5432");
            System.out.println("Original: " + instance1.getInstanceInfo());

            // Serialize
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(instance1);
            oos.close();

            // Deserialize
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            DatabaseConfig instance2 = (DatabaseConfig) ois.readObject();
            ois.close();

            System.out.println("Deserialized: " + instance2.getInstanceInfo());

            // Verify
            boolean sameInstance = (instance1 == instance2);
            boolean sameHashCode =
                    (System.identityHashCode(instance1) == System.identityHashCode(instance2));
            boolean sameData =
                    instance1.getDb_url().equals(instance2.getDb_url()) &&
                            instance1.getPort().equals(instance2.getPort());

            System.out.println("\nSame object reference: " + sameInstance);
            System.out.println("Same hashCode: " + sameHashCode);
            System.out.println("Same data: " + sameData);
            System.out.println("Test Result: " + (sameInstance ? "✅ PASS" : "❌ FAIL"));

        } catch (Exception e) {
            System.out.println("❌ FAIL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}