package com.db.ar.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Configura o gerenciador de cache do Redis (RedisCacheManager).
     * <p>
     * Esta configuração resolve problemas críticos de integração e serialização:
     * <ul>
     *   <li><b>Suporte ao Java 8:</b> Registra o {@code JavaTimeModule} para permitir a serialização e
     *       desserialização de tipos de data/hora modernos como {@code LocalDateTime} e {@code LocalDate}.</li>
     *   <li><b>Preservação de Tipos (Polimorfismo):</b> Ativa a tipagem padrão no Jackson para injetar a
     *       propriedade {@code @class} no JSON gravado no Redis, evitando erros de conversão genérica (como
     *       {@code LinkedHashMap}) ao recuperar o objeto de volta para o DTO correto.</li>
     *   <li><b>Políticas de Retenção:</b> Define um tempo de vida padrão (TTL) de 30 segundos para as chaves
     *       e impede o armazenamento de valores nulos (erros ou respostas vazias) no Redis.</li>
     * </ul>
     * </p>
     *
     * @param connectionFactory Fábrica de conexões do Redis injetada pelo Spring Boot.
     * @return RedisCacheManager configurado com suporte a JSON e tipos Java 8.
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY
                                          );
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(30))
                    .disableCachingNullValues()
                    .serializeValuesWith(RedisSerializationContext
                                 .SerializationPair.fromSerializer(serializer));

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(config).build();
    }
}
