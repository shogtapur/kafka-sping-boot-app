# Springboot-KafkaDeo

 Kafka demo app to store EPG details and retrive it 


# Requirements
 For building and running the application you need:
  JDK 1.8
  Maven 3
  Kafka


# Running the application locally
   * run locally Zookeeper and kafka server at port number 9092
   * port number for sping boot application is  8091	


# Api exposed in application 

  1) get all the EPG details
	http://localhost:8091/kafka/getEpgDetails
  2) Get programs by channel Name
	http://localhost:8091/kafka/getProgramsOfChannel?channelName=Sony%20Sab
  3) Get program details by program name
	  http://localhost:8091/kafka/getProgramDetails?programName=ice age
  4) Get program deatils by program scheduled time 
	http://localhost:8091/kafka/getProgramsByTimpstramp?programSceduledTime=2021-02-22T08:30:00
  5) Get progtam detials by program scheduled timestramp and channel name
	http://localhost:8091/kafka/getProgramsByTimpstrampAndChannelName?channelName=star                                                                                  movies&programSceduledTime=2021-02-22T08:30:00
	
	