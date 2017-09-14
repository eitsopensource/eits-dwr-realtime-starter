package br.com.eits.starter.realtime.messaging;

import br.com.eits.starter.realtime.RealTimeConfigurationHolder;
import lombok.extern.java.Log;
import org.hibernate.Hibernate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Log
@Component
public class RealTimeMessaging
{

	/**
	 *
	 */
	private static final String ENTITIES_TOPIC = "/topic/entities";
	/**
	 *
	 */
	private final SimpMessagingTemplate template;

	/**
	 *
	 */
	private final RealTimeConfigurationHolder holder;

	public RealTimeMessaging( SimpMessagingTemplate template, RealTimeConfigurationHolder holder )
	{
		this.template = template;
		this.holder = holder;
	}

	public void sendEntity( Object entity )
	{
		if ( holder.shouldBroadcast( entity ) )
		{
			this.template.convertAndSend( ENTITIES_TOPIC, Hibernate.getClass( entity ).getCanonicalName() );
		}
	}
}
