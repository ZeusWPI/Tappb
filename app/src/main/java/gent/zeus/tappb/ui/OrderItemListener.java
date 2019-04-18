package gent.zeus.tappb.ui;

import gent.zeus.tappb.entity.OrderProduct;

public interface OrderItemListener {
    void onClick(OrderProduct orderProduct);

    void onIncreaseClicked(OrderProduct orderProduct);

    void onDecreaseClicked(OrderProduct orderProduct);
}
