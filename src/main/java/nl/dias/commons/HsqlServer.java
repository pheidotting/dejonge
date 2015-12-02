package nl.dias.commons;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;
import org.hsqldb.server.ServerConstants;

import java.io.IOException;
import java.util.Properties;

public class HsqlServer {

    private String database;
    private String dbName;
    private int port = ServerConstants.SC_DEFAULT_HSQL_SERVER_PORT;
    private Properties databaseProperties = new Properties();

    public HsqlServer() {
    }

    public void init() {
        try {
            Server server = new Server();

            // Default properties
            HsqlProperties serverProperties = new HsqlProperties();
            StringBuilder databaseUrlBuilder = new StringBuilder(this.database);

            for (String propertyName : this.databaseProperties.stringPropertyNames()) {
                String propertyValue = this.databaseProperties.getProperty(propertyName);
                databaseUrlBuilder.append(";");
                databaseUrlBuilder.append(propertyName);
                databaseUrlBuilder.append("=");
                databaseUrlBuilder.append(propertyValue);
            }

            serverProperties.setProperty("server.database.0", databaseUrlBuilder.toString());
            serverProperties.setProperty("server.dbname.0", this.dbName);
            serverProperties.setProperty("server.remote_open", true);
            serverProperties.setProperty("server.port", this.port);

            server.setProperties(serverProperties);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (ServerAcl.AclFormatException afe) {
            throw new RuntimeException(afe);
        }
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Properties getDatabaseProperties() {
        return this.databaseProperties;
    }

    public void setDatabaseProperties(Properties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }
}

