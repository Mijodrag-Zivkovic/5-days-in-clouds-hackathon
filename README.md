# 5 days in the cloud hackathon

My solution for "5 days in clouds" hackathon [task](https://5danauoblacima.com/wp-content/uploads/2021/12/5-dana-u-oblacima-challenge-zadatak.pdf).
For people who don't speak Serbian, the task goes like this:
- we are given a pricelist of certain cloud services
- those services are:
  1. Serverless function - FUNC </br>
     Commands: EXEC
     Payment: 
      - per number of executions
      - per amount of data that goes through the network
  2. Database - DB
     Commands: INSERT, SELECT, SOFT_DELETE </br>
     Payment:
      - per number of executions
      - per amount of data stored
  3. Object Storage - OS
     Commands: PUT, GET, SOFT_DELETE </br>
     Payment: 
      - per amount of data that goes through the network
      - per amount of data stored
  4. Virtual Machine - VM
     Commands: START, STOP </br>
     Payment:
      - tume based (for every second that VM spends in active state)
- we are tasked with making REST API that will support following actions:
  1. POST /actions endpoint - this request is sent whenever a user does some action on the cloud, such as starting VM for example. Each action has defined
     cost, so the app will calculate the cost for that action and store it in actions history.
     Json example of POST body:
     {
        "serviceType": "VM",
        "userId": 1,
        "actionType": "START",
        "timestamp": 1609500600,
        "payloadSizeMb": 1
     }
     
  2. GET /user/{userId}/costs endpoint - this requests returns total cost for selected user (per service) until timestamp (timestamp is a query parameter, in UNIX format) 
     GET request example: GET /user/1/costs?untilDate=1609502400

Each action has a free tier, after which real costs are being calculated.
