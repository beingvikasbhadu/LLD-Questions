import java.lang.reflect.Constructor;

public class ReflectionAttackTest {

    public static void main(String[] args) {
        System.out.println("=== Reflection Attack Test ===\n");

        // Get normal instance
        DatabaseConfig instance1 = DatabaseConfig.getInstance();
        System.out.println("Instance 1: " + instance1.getInstanceInfo());

        // Try to create second instance via reflection
        try {
            Constructor<DatabaseConfig> constructor =
                    DatabaseConfig.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            DatabaseConfig instance2 = constructor.newInstance();

            System.out.println("❌ FAIL: Reflection attack succeeded!");
            System.out.println("Instance 2: " + instance2.getInstanceInfo());

        } catch (Exception e) {
            System.out.println("✅ PASS: Reflection attack prevented!");
            System.out.println("Exception: " + e.getCause().getMessage());
        }
    }
}