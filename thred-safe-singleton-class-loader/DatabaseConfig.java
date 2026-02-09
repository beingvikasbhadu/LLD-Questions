import javax.xml.crypto.Data;
import java.io.Serializable;

public class DatabaseConfig implements Serializable {
//    private volatile static DatabaseConfig instance;

    // for version comptability
    private static final long serialVersionUID=1L;
    private static final String INSTANCE_KEY="DATABASE_CONFIG";
    private  String db_url="db-url1";
    private  String port="8080";


    // to prevent reflection attack
    private DatabaseConfig(){
        if(System.getProperties().get(INSTANCE_KEY)!=null)
        {
            throw new IllegalStateException("Instance Already Exists!");
        }
    }

    @Override
    protected DatabaseConfig clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException("Singleton cannot be cloned");

    }
    public  void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public  void setPort(String port) {
        this.port = port;
    }

    public String getDb_url() {
        return db_url;
    }

    public String getPort() {
        return port;
    }

    public static DatabaseConfig getInstance()
    {
        Object instance= System.getProperties().get(INSTANCE_KEY);
        if(instance==null)
        {
            synchronized (DatabaseConfig.class){
                 instance= System.getProperties().get(INSTANCE_KEY);

                if(instance==null)
                {
                    instance=new DatabaseConfig();
                    System.getProperties().put(INSTANCE_KEY,instance);
                }
            }

        }
        return (DatabaseConfig) instance;
    }


    // prevent new instance creation during deserialization
    protected Object readResolve()
    {
        return System.getProperties().get(INSTANCE_KEY);
    }

    // For debugging
    public String getInstanceInfo() {
        return "DatabaseConfig@" + System.identityHashCode(this) +
                " [db_url=" + db_url + ", port=" + port + "]";
    }

}
