package br.com.eits.starter.realtime.hibernate;

import java.util.Map;

import br.com.eits.starter.realtime.messaging.RealTimeMessaging;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.PersistEvent;
import org.springframework.stereotype.Component;

@Log
@Component
public class PersistEventListener implements org.hibernate.event.spi.PersistEventListener
{
	private final RealTimeMessaging realTimeMessaging;

	public PersistEventListener( RealTimeMessaging realTimeMessaging )
	{
		this.realTimeMessaging = realTimeMessaging;
	}

	@Override
	public void onPersist( PersistEvent event ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getObject() );
	}

	@Override
	public void onPersist( PersistEvent event, Map createdAlready ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getObject() );
	}
}
