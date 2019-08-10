package io.github.cepr0.demo.commons.event;

public interface OrderFailedEvent extends Event {
	long getOrderId();
}
