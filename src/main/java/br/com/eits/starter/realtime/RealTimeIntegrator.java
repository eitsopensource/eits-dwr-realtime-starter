package br.com.eits.starter.realtime;

import javax.persistence.EntityManagerFactory;

import br.com.eits.starter.realtime.hibernate.DeleteEventListener;
import br.com.eits.starter.realtime.hibernate.MergeEventListener;
import br.com.eits.starter.realtime.hibernate.PersistEventListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class RealTimeIntegrator
{
	public RealTimeIntegrator(
			EntityManagerFactory entityManagerFactory,
			MergeEventListener mergeEventListener,
			DeleteEventListener deleteEventListener,
			PersistEventListener persistEventListener )
	{
		EventListenerRegistry eventListenerRegistry = entityManagerFactory.unwrap( SessionFactoryImpl.class ).getServiceRegistry()
				.getService( EventListenerRegistry.class );
		eventListenerRegistry.appendListeners( EventType.MERGE, mergeEventListener );
		eventListenerRegistry.appendListeners( EventType.DELETE, deleteEventListener );
		eventListenerRegistry.appendListeners( EventType.PERSIST, persistEventListener );
	}
}
