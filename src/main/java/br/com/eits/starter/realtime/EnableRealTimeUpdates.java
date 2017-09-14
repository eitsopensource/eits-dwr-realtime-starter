package br.com.eits.starter.realtime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Anotação que habilita a atualização em tempo real de entidades.
 * <p>
 * Se nenhum parâmetro for passado, o padrão é todas as entidades sejam atualizadas.
 * <p>
 * Se dois parâmetros forem passados, teremos uma whitelist (tenderemos a ser mais restritivos).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RealTimeConfiguration.class)
public @interface EnableRealTimeUpdates
{
	/**
	 * Classes de entidades que SERÃO atualizadas em tempo real.
	 */
	Class<?>[] whitelist() default {};

	/**
	 * Classes de entidades que NÃO SERÃO atualizadas em tempo real.
	 */
	Class<?>[] blacklist() default {};
}
