package br.com.eits.starter.realtime.hibernate;

import java.util.Set;

import br.com.eits.starter.realtime.messaging.RealTimeMessaging;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.DeleteEvent;
import org.springframework.stereotype.Component;

@Log
@Component
public class DeleteEventListener implements org.hibernate.event.spi.DeleteEventListener
{
	private final RealTimeMessaging realTimeMessaging;

	public DeleteEventListener( RealTimeMessaging realTimeMessaging )
	{
		this.realTimeMessaging = realTimeMessaging;
	}

	@Override
	public void onDelete( DeleteEvent event ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getObject() );
	}

	@Override
	public void onDelete( DeleteEvent event, Set transientEntities ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getObject() );
	}
}
