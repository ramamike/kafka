1. sudo systemctl start zookeeper.service
2. sudo systemctl start kafka.service
3. sudo systemctl status kafka zookeeper.service
4. option: create topic 
   sudo /usr/local/kafka-server/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic theFirstTopic
5. check topic 
   sudo /usr/local/kafka-server/bin/kafka-topics.sh  --topic theFirstTopic --bootstrap-server localhost:9092
6. option: test producing
   sudo /usr/local/kafka-server/bin/kafka-console-producer.sh --topic theFirstTopic --bootstrap-server localhost:9092
7. option test consumer
   sudo /usr/local/kafka-server/bin/kafka-console-consumer.sh --topic theFirstTopic --from-begining --bootstrap-server localhost:9092
8. 