
import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.product.AquaticProducts;
import com.ooadpj.entity.product.LivestockPoultryMeat;
import com.ooadpj.entity.product.Vegetables;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.entity.user.Expert;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.List;

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
//        try {
//            final Session session = getSession();
            try {

                Transaction transaction = session.beginTransaction();
                List<AgriculturalProduct> agriculturalProducts =
                        session.createSQLQuery("select * from PRODUCT").addEntity(AgriculturalProduct.class).list();

                for(AgriculturalProduct agriculturalProduct: agriculturalProducts){
                    System.out.println(agriculturalProduct.getName()+agriculturalProduct.getId());
                }

                transaction.commit();
//                return agriculturalProducts;
            } finally {
                session.close();
            }

//    }
    }
}