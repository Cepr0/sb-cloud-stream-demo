package io.github.cepr0.demo.service.product;

import io.github.cepr0.demo.commons.event.Event;
import io.github.cepr0.demo.commons.event.OrderCompleted;
import io.github.cepr0.demo.commons.event.ProductEnded;
import io.github.cepr0.demo.commons.event.ProductNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
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

		MessageChannel channel = null;

		if (event instanceof OrderCompleted) {
			channel = channels.orderCompleted();
		}

		if (event instanceof ProductNotFound) {
			channel = channels.productNotFound();
		}

		if (event instanceof ProductEnded) {
			channel = channels.productEnded();
		}

		if (channel != null) {
			channel.send(message);
			log.info("[i] Sent: {}", event);
		} else {
			log.info("[w] Event '{}' doesn't match any channel", event);
		}
	}
}
