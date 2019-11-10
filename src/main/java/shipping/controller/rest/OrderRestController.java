package shipping.controller.rest;

import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shipping.dto.rest.OrderDTOrest;
import shipping.exception.CustomDAOException;
import shipping.service.api.OrderService;

import java.util.List;

@RestController
@RequestMapping("/rest/order")
public class OrderRestController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Loggable
    @GetMapping("/all")
    public List<OrderDTOrest> getOrders() throws CustomDAOException {
        return orderService.listOrdersDTOrest();
    }

}
