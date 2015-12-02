package nl.dias.commons;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

public class MariaDBServer {
    public MariaDBServer() {
    }

    public static void main(String[] args) {
        new MariaDBServer().init();
    }

    public void init() {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(3306); // OR, default: setPort(0); => autom. detect free port
        //        configBuilder.setDataDir("/home/theapp/db"); // just an example
        try {
            DB db = DB.newEmbeddedDB(3306);
            db.start();
            //            db.createDB("djfc");
        } catch (ManagedProcessException mpe) {
            System.out.println("Fout ");
            System.out.println(mpe.getMessage());
            System.out.println(mpe.getStackTrace());
        }
    }
}
