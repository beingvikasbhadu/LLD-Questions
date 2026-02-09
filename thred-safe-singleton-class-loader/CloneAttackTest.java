public class CloneAttackTest {

    public static void main(String[] args) {
        System.out.println("=== Clone Attack Test ===\n");

        try {
            DatabaseConfig instance1 = DatabaseConfig.getInstance();
            System.out.println("Original: " + instance1.getInstanceInfo());

            // Try to clone
            DatabaseConfig instance2 = (DatabaseConfig) instance1.clone();

            System.out.println("❌ FAIL: Clone attack succeeded!");
            System.out.println("Cloned: " + instance2.getInstanceInfo());

        } catch (CloneNotSupportedException e) {
            System.out.println("✅ PASS: Clone attack prevented!");
            System.out.println("Exception: " + e.getMessage());
        }
    }
}