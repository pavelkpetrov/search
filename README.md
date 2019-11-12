#Test Spring Demo to implement search exercise
Demo application was focused on the overall design. 
Used txt file for input data; 

#PreRequisites
Should be installed: 
- Docker
- docker-compose
- java 1.8
- maven

#Start elasticsearch
To start elasticsearch:
- Go to "ek" directory;
- Start command line;
- Run command: docker-compose up -d 

Kibana server url : http://localhost:5601/

ElasticSearch url : http://localhost:9200/

ElasticSearch rest url : http://localhost:9300/


#How to test
- Go to the root folder;
- If necessary change test.txt file;
- If necessary change \src\test\resources\application.properties; 
- Start command line;
- Run command: mvn test
