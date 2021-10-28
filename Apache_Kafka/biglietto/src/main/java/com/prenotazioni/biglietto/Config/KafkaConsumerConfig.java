package com.prenotazioni.biglietto.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    //config for new or upset or delete User data
    @Bean
	public ConsumerFactory<String, UserConfig> userConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "biglietto-0");
        JsonDeserializer<UserConfig> deserializer = new JsonDeserializer<>(UserConfig.class);
        deserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserConfig> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UserConfig> factory = new ConcurrentKafkaListenerContainerFactory<String, UserConfig>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
    }

    //config for Spettacolo data
    @Bean
	public ConsumerFactory<String, SpettacoloConfig> spettacoloConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "biglietto-1");
        JsonDeserializer<SpettacoloConfig> deserializer = new JsonDeserializer<>(SpettacoloConfig.class);
        deserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), deserializer);
    }

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, SpettacoloConfig> spettacoloKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, SpettacoloConfig> factory = new ConcurrentKafkaListenerContainerFactory<String, SpettacoloConfig>();
		factory.setConsumerFactory(spettacoloConsumerFactory());
		return factory;
	}
}