package hhplus.ticketing.kafkaTest;

import io.swagger.v3.oas.annotations.headers.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListeners {
    @KafkaListener(topics = "${setting.topics}",groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> consumerRecord, @Header(KafkaHeaders.OFFSET) Long offset
            , Acknowledgment acknowledgment
            , KafkaProperties.Consumer<?, ?> consumer){
        try {

            log.info("Consumer Data = {}, Offset = {}, Header OffSet = {}, Partition = {}"
                    , consumerRecord.value(), consumerRecord.offset(),offset,consumerRecord.partition());
            //처리 후 커밋
            //해당 비지니스 로직 처리 후 커밋로직 작성
            consumer.commitAsync();
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

    }
}