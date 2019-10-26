package shipping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.dao.DriverDAO;
import shipping.dao.DriverShiftDAO;
import shipping.dao.WagonDAO;
import shipping.exception.CustomDAOException;
import shipping.exception.CustomServiceException;
import shipping.model.Driver;
import shipping.model.DriverShift;
import shipping.model.Order;
import shipping.model.Wagon;
import shipping.service.api.DriverService;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverServiceImpl implements DriverService {

    private Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    private DriverDAO driverDAO;

    private DriverShiftDAO driverShiftDAO;

    private WagonDAO wagonDAO;

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    @Transactional
    public void addDriver(Driver driver) throws CustomServiceException {

        //validate fields

        try {
            Driver checkDriver = driverDAO.getDriver(driver.getId());

            if (checkDriver != null) {
                throw new CustomServiceException("Selected id already exists");
            }

            driverDAO.addDriver(driver);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver) throws CustomServiceException {
        try {
            driverDAO.update(driver);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Driver> listDrivers() throws CustomServiceException {
        try {
            return driverDAO.listDrivers();
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public Driver getDriverById(int id) throws CustomServiceException {
        try {
            return driverDAO.getDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public void removeDriver(int id) throws CustomServiceException {
        try {
            Driver driver = driverDAO.getDriver(id);
            if (driver == null) {
                throw new CustomServiceException("Driver wasn't found.");
            } /*else if (driver.getOrder() != null) {                           // should be returned
                throw new CustomServiceException("Driver can't be removed, because he has an order.");
            } else if (driver.getWagon() != null) {
                // need or not
            }*/
            driverDAO.removeDriver(id);
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Integer> driversByWagon(String id) throws CustomServiceException {
        List<Integer> drivers = new ArrayList<>();
        for (Driver driver: listDrivers()) {
            if (driver.getWagon().getId().equals(id)) drivers.add(driver.getId());
        }
        return drivers;
    }

    @Override
    @Transactional
    public Map<Driver, LocalTime> getSuitableDrivers(Order order, LocalTime orderTime) throws CustomServiceException {
        try {
            Map<Driver, LocalTime> driversHoursMap = new HashMap<>();

            Wagon wagonForThisOrder = order.getWagon();
            List<Driver> availableDriversInTheSameCity = driverDAO.getAvailableDrivers(wagonForThisOrder.getCity());

         //   LocalTime wagonShiftSize = wagonForThisOrder.getShiftSize(); // сколько часов длится одна смена
          //  int numberOfShifts = orderTime.getHour() / wagonShiftSize.getHour(); // сколько смен нужно для доставки заказа

            LocalDate now = LocalDate.now();// текущая дата
            LocalDate firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth());  // дата 1-го дня след.месяца
            int hoursUntilMonthEnd = Period.between(now, firstDayOfNextMonth).getDays()*24;

            for (Driver driver: availableDriversInTheSameCity) {
                if (driver.getWorkedHours() == null) { // если водитель ещё нигде не работал
                    driversHoursMap.put(driver, LocalTime.of(0, 0)); // добавляем
                    continue;
                }
                LocalTime workedHours = driver.getWorkedHours(); // сколько отработано уже часов у водителя

                LocalTime hoursToWorkThisMonth = LocalTime.of(176, 0).minus(workedHours.getHour(), ChronoUnit.HOURS); // сколько осталось часов работать в этом месяце


                // если часов до конца месяца больше, чем рабочих часов, а время в пути меньше, то добавляем
                if (hoursUntilMonthEnd >= hoursToWorkThisMonth.getHour() && hoursToWorkThisMonth.getHour() >= orderTime.getHour() ||
                        now.atTime(LocalTime.now()).plusDays(orderTime.getHour()/24).isAfter(firstDayOfNextMonth.atStartOfDay())) {
                    driversHoursMap.put(driver, workedHours);
                    continue;
                }
                    // to do what?
                    // если часов до конца месяца меньше чем время в пути

            }

            return driversHoursMap;
        } catch (CustomDAOException e) {
            throw new CustomServiceException(e);
        }
    }
}
