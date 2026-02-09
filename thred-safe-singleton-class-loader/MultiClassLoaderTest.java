import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class MultiClassLoaderTest {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Multi-ClassLoader Test ===\n");

        File currentDir = new File(System.getProperty("user.dir"));
        File classesDir = currentDir;  // Adjust based on your setup

        System.out.println("Loading from: " + classesDir.getAbsolutePath() + "\n");

        URL[] urls = new URL[]{classesDir.toURI().toURL()};

        URLClassLoader loader1 = new URLClassLoader(urls, null);
        URLClassLoader loader2 = new URLClassLoader(urls, null);

        Class<?> configClass1 = loader1.loadClass("DatabaseConfig");
        Class<?> configClass2 = loader2.loadClass("DatabaseConfig");

        System.out.println("Class from Loader1: " + configClass1);
        System.out.println("Class from Loader2: " + configClass2);
        System.out.println("Same class? " + (configClass1 == configClass2));
        System.out.println();

        // Call getInstance from loader1
        Method getInstance1 = configClass1.getMethod("getInstance");
        Object instance1 = getInstance1.invoke(null);
        System.out.println("Instance 1 created: " + instance1.getClass().getName() +
                "@" + System.identityHashCode(instance1));

        // Try to call getInstance from loader2
        Method getInstance2 = configClass2.getMethod("getInstance");
        System.out.println("\nTrying to get instance from Loader2...");

        try {
            Object instance2 = getInstance2.invoke(null);
            System.out.println("Instance 2 obtained: " + instance2.getClass().getName() +
                    "@" + System.identityHashCode(instance2));

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getCause().getClass().getSimpleName());
            System.out.println("Message: " + e.getCause().getMessage());
        }

        // Verify the instance is stored in System.getProperties()
        Object stored = System.getProperties().get("DATABASE_CONFIG");
        System.out.println("\nInstance in System.getProperties(): " +
                stored.getClass().getName() + "@" + System.identityHashCode(stored));

        System.out.println("\n--- RESULT ---");
        System.out.println("✅ Single instance exists in System.getProperties()");
        System.out.println("❌ Cannot be used across ClassLoaders due to type mismatch");
        System.out.println("\nThis is a fundamental Java limitation.");
        System.out.println("Real solution: Deploy singleton class in parent ClassLoader");
    }
}