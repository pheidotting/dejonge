//package nl.dias.commons;
//
//import ch.vorburger.exec.ManagedProcessException;
//import ch.vorburger.mariadb4j.DB;
//import ch.vorburger.mariadb4j.DBConfigurationBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class MariaDBServer {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MariaDBServer.class);
//
//    public MariaDBServer() {
//    }
//
//    public static void main(String[] args) {
//        new MariaDBServer().init();
//    }
//
//    public void init() {
//        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
//        configBuilder.setPort(3306); // OR, default: setPort(0); => autom. detect free port
//        //        configBuilder.setDataDir("/home/theapp/db"); // just an example
//        try {
//            LOGGER.info("Startin MariaDB");
//            DB db = DB.newEmbeddedDB(3306);
//            db.start();
//            LOGGER.debug("Db started");
//        } catch (ManagedProcessException mpe) {
//            LOGGER.error("Fout ");
//            LOGGER.error(mpe.getMessage());
//            LOGGER.error("{}", mpe);
//        }
//    }
//}
