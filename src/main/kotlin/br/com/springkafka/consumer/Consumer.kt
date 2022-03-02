package br.com.springkafka.consumer

import br.com.springkafka.Car
import br.com.springkafka.People
import br.com.springkafka.domain.Book
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Component
@KafkaListener(topics = ["\${topic.name}"])
class Consumer() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PersistenceContext
    lateinit var manager: EntityManager

    @KafkaHandler
    @Transactional
    fun consumerPoeple(record: ConsumerRecord<String, People>, ack: Acknowledgment) {

        val people = record.value()

        logger.info(people.toString())

        var p = br.com.springkafka.domain.People()

        p.name = people.name.toString()
        p.cpf = people.cpf.toString()
        p.books = people.books.map {
            Book(it.toString())
        }

        manager.persist(p)

        ack.acknowledge()
    }

    @KafkaHandler
    @Transactional
    fun consumerCar(record: ConsumerRecord<String, Car>, ack: Acknowledgment) {

        val car = record.value()

        logger.info(car.toString())

        var c = br.com.springkafka.domain.Car()

        c.name = car.name.toString()
        c.cpf = car.cpf.toString()
        c.brand = car.brand.toString()

        manager.persist(c)

        ack.acknowledge()
    }

    @KafkaHandler(isDefault = true)
    fun consumerDefault(obj: Any, ack: Acknowledgment) {
        logger.info("Message Received: $obj")
        ack.acknowledge()
    }
}