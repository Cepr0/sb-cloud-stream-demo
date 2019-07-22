package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.event.Event;
import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.OrderFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutgoingHandler {

	private final Channels channels;

	public OutgoingHandler(Channels channels) {
		this.channels = channels;
	}

	public void send(Event event) {

		var message = new GenericMessage<>(event);

		if (event instanceof OrderCompleted) {
			channels.orderCompleted().send(message);
		}

		if (event instanceof OrderFailed) {
			channels.orderFailed().send(message);
		}

		log.info("[i] Sent: {}", event);
	}
}
