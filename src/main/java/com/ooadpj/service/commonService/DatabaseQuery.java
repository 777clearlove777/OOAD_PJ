package com.ooadpj.service.commonService;

import com.ooadpj.entity.product.AgriculturalProduct;
import com.ooadpj.entity.user.AgriculturalMarket;
import com.ooadpj.entity.user.Expert;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * @author: 孟超
 * @date: 2021/1/8
 * @description: 数据库查询相关
 */
public class DatabaseQuery {
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

    public List<AgriculturalMarket> marketsQuery() throws Exception {
        final Session session = getSession();
        try {

            Transaction transaction = session.beginTransaction();
            List<AgriculturalMarket> agriculturalMarkets =
                    session.createSQLQuery("select * from MARKET").addEntity(AgriculturalMarket.class).list();

            transaction.commit();
            return agriculturalMarkets;
        } finally {
            session.close();
        }
    }

    public List<AgriculturalProduct> productQuery() throws Exception {
        final Session session = getSession();
        try {

            Transaction transaction = session.beginTransaction();
            List<AgriculturalProduct> agriculturalProducts =
                    session.createSQLQuery("select * from PRODUCT").addEntity(AgriculturalProduct.class).list();

            transaction.commit();
            return agriculturalProducts;
        } finally {
            session.close();
        }
    }

    public List<Expert> expertsQuery() throws Exception {
        final Session session = getSession();
        try {

            Transaction transaction = session.beginTransaction();
            List<Expert> expertList =
                    session.createSQLQuery("select * from EXPERT").addEntity(Expert.class).list();

            transaction.commit();
            return expertList;
        } finally {
            session.close();
        }
    }
}
