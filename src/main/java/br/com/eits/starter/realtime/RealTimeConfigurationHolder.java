package br.com.eits.starter.realtime;

import static java.util.Collections.emptySet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RealTimeConfigurationHolder
{
	private final ApplicationContext applicationContext;
	/**
	 *
	 */
	private EntityMode entityMode;
	/**
	 *
	 */
	private Set<Class<?>> entityList = emptySet();

	public RealTimeConfigurationHolder( ApplicationContext applicationContext )
	{
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void postConstruct()
	{
		Optional<EnableRealTimeUpdates> config =
				this.applicationContext.getBeansWithAnnotation( EnableRealTimeUpdates.class )
						.entrySet().stream().map( Map.Entry::getKey )
						.findFirst()
						.map( name -> this.applicationContext.findAnnotationOnBean( name, EnableRealTimeUpdates.class ) );
		if ( config.isPresent() )
		{
			Set<Class<?>> whitelist = config.map( EnableRealTimeUpdates::whitelist )
					.map( Arrays::asList ).map( HashSet::new ).orElse( new HashSet<>() );
			Set<Class<?>> blacklist = config.map( EnableRealTimeUpdates::blacklist )
					.map( Arrays::asList ).map( HashSet::new ).orElse( new HashSet<>() );
			if ( !whitelist.isEmpty() )
			{
				this.entityMode = EntityMode.WHITELIST;
				this.entityList = whitelist;
			}
			else
			{
				this.entityMode = EntityMode.BLACKLIST;
				this.entityList = blacklist;
			}
		}
		else
		{
			this.entityMode = EntityMode.BLACKLIST;
		}
	}

	/**
	 * A condicional abaixo foi simplificada pela IntelliJ.
	 *
	 * @param entity entidade que queremos enviar
	 * @return se devemos enviá-la ou não
	 */
	public boolean shouldBroadcast( Object entity )
	{
		return this.entityMode.equals( EntityMode.WHITELIST ) == this.entityList.contains( Hibernate.getClass( entity ) );
	}

	private enum EntityMode
	{
		WHITELIST,
		BLACKLIST
	}
}
