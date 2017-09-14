package br.com.eits.starter.realtime.hibernate;

import java.util.Map;

import br.com.eits.starter.realtime.messaging.RealTimeMessaging;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.MergeEvent;
import org.springframework.stereotype.Component;

@Log
@Component
public class MergeEventListener implements org.hibernate.event.spi.MergeEventListener
{
	private final RealTimeMessaging realTimeMessaging;

	public MergeEventListener( RealTimeMessaging realTimeMessaging )
	{
		this.realTimeMessaging = realTimeMessaging;
	}

	@Override
	public void onMerge( MergeEvent event ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getEntity() );
	}

	@Override
	public void onMerge( MergeEvent event, Map copiedAlready ) throws HibernateException
	{
		this.realTimeMessaging.sendEntity( event.getEntity() );
	}
}
