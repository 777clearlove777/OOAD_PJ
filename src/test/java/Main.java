
import com.ooadpj.entity.product.AquaticProducts;
import com.ooadpj.entity.product.LivestockPoultryMeat;
import com.ooadpj.entity.product.Vegetables;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.entity.user.Expert;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");


            Transaction transaction = session.beginTransaction();

//            Expert user = new Expert();
//            user.setId(1);
//            user.setName("xxx");
//            session.save(user);
//            AgriculturalMarket agriculturalMarket = new AgriculturalMarket();
//            agriculturalMarket.setId(1);
//            agriculturalMarket.setName("yangpu");
//            session.save(agriculturalMarket);
            AquaticProducts aquaticProducts = new AquaticProducts();
            LivestockPoultryMeat livestockPoultryMeat = new LivestockPoultryMeat();
            Vegetables vegetables = new Vegetables();
            session.save(aquaticProducts);
            session.save(livestockPoultryMeat);
            session.save(vegetables);

            transaction.commit();
        } finally {
            session.close();
        }
    }
}