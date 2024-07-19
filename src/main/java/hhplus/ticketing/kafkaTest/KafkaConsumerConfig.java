package hhplus.ticketing.kafkaTest;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${setting.autocommit}")
    private String autoCommit;

    @Value("${setting.earliest}")
    private String earliest;

    @Autowired
    private TaskExecutorConfig taskExecutorConfig;
    public Map<String, Object> ConsumerConfig(){
        Map<String,Object> props = new HashMap<>();
        props.put(KafkaConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 그룹 생성
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //오프셋 수동 관리
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, this.autoCommit);
        //
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.earliest );
        // poll 요청을 보내고, 다음 poll 요청을 보내는데 까지의 최대 시간 설정
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5000);
        return  props;
    }
    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(ConsumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(ConsumerFactory<String,String> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String,String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);// 하나의 리스너에 스레드 3개로 처리
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setListenerTaskExecutor(taskExecutorConfig.executor());

        return factory;
    }
}