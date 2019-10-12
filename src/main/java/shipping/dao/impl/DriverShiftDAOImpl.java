package shipping.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import shipping.dao.DriverShiftDAO;
import shipping.exception.CustomDAOException;
import shipping.model.Driver;
import shipping.model.DriverShift;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Repository
public class DriverShiftDAOImpl implements DriverShiftDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addDriverShift(DriverShift driverShift) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(driverShift);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public void update(DriverShift driverShift) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(driverShift);
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<DriverShift> listDriverShifts() throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<DriverShift> driverShifts = session.createQuery("from DriverShift").list();
            return driverShifts;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }    }

    @Override
    public DriverShift getDriverShift(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            DriverShift driverShift = session.get(DriverShift.class, id);
            return driverShift;
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }    }

    @Override
    public void removeDriverShift(int id) throws CustomDAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            DriverShift driverShift = session.get(DriverShift.class, id);
            if (null != driverShift) {
                session.delete(driverShift);
            }
        } catch (Exception e) {
            throw new CustomDAOException(e);
        }
    }

    @Override
    public List<DriverShift> getDriverShiftInThisMonth(List<Driver> driverList) {
        if (driverList==null){
            //do smth
        }

        LocalDate firstDayOfMonth = YearMonth.now().atDay(1);
        LocalDate firstDayofNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT id FROM DriverShift WHERE driver_id IN :driverList AND ");

        List<DriverShift> driverShiftList = query.getResultList();

        return null;
    }
}
