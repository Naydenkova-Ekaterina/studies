package shipping.dto.converter;

import org.modelmapper.ModelMapper;
import shipping.dto.OrderDTO;
import shipping.model.Order;

import java.time.LocalTime;

public class OrderConverter {

    private ModelMapper modelMapper;

    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO convertToDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        if (order.getWagon() != null) {
            order.getWagon().setShiftSize(LocalTime.parse(orderDTO.getWagon().getShiftSize()));
        }
        return order;
    }
}
