# kafka-sping-boot-app

project name -KafkaDemo
Kafka demo app to store EPG details and retrive it 


Requirements
For building and running the application you need:

JDK 1.8
Maven 3


Running the application locally
run locally Zookeeper and kafka server at port number 9092
port number for sping boot application is  8091	


Api exposed in application 

1) Store EPG program Details:
    ex:http://localhost:8092/kafka/StoreProgramAndSchedule?programId=17&programName=Baby Boss 2&channelName=HBO&programSceduledTime=2021-02-19T08:30:00
2) get all the EPG details
     ex:http://localhost:8091/kafka/getEpgDetails
3) Get programs by channel Name
	 ex: http://localhost:8091/kafka/getProgramsOfChannel?channelName=Sony%20Sab
4) Get program details by program name
	 ex: http://localhost:8091/kafka/getProgramDetails?programName=ice age
5) Get program deatils by program scheduled time 
	 ex: http://localhost:8091/kafka/getProgramsByTimpstramp?programSceduledTime=2021-02-22T08:30:00
6) Get progtam detials by program scheduled timestramp and channel name
	 ex: http://localhost:8091/kafka/getProgramsByTimpstrampAndChannelName?channelName=star movies&programSceduledTime=2021-02-22T08:30:00
	
	
	
