# PharmacyShop app - Backend

This is backend of PharmacyShop Web app (click [here](https://github.com/aleksandar95si/NST-Project-Frontend) for frontend). App was made for an exam project of subjects Advanced Software technologies 1 and Advanced Software technologies 2 on Faculty of Organization Sciences, University of Belgrade.

### App Architecture

Backend of application is based on microservice software architecture. It contains 7 microservices which are actually 7 Spring boot projects.

![Microservice architecture](https://i.imgur.com/7qkcJNK.png)

##### Discovery Server (registry-server)

Spring version of the Netflix solution - Spring Cloud Netflix Eureka Server - was used for implementation of the discovery server. Spring Cloud Netflix Eureka Server is implemented as a client side fon.master.nst.service discovery pattern, although it seems that it is a server side fon.master.nst.service discovery. All other microservices are registered as clients on this Eureka server that keeps information about them, and all communication between microservices takes place indirectly through this microservice.

##### API Gateway (api-gateway)

Spring Cloud Gateway was used for implementation of this microservice. The API Gateway, along with the previously described Discovery Server, is one of the basic patterns of microservice software architectures. It's role is handling requests, both from backend and frontend, and also easier maintenance of the entire software system. It represents a single entry point for all clients.

##### Oauth2 Server (oauth-server)

Oauth2 Server is an authentication and authorization server. Spring Cloud Security was used for the implementation. When user login on a server, he receives a token that will be sent with each request. It is not possible to access microservices without a valid token. 

##### User Service (user-fon.master.nst.service)

This microservice is responsible for registration of new users. User fon.master.nst.service and Oauth2 serve shares a common database.

##### Product Service (product-fon.master.nst.service)

The product microservice is responsible for reporting and modifying informations of available products. It contains it's own database which doesn't share with any other microservice.

##### Shopping Cart Service (shopping-cart)

This microservice is responsible for adding and deleting products into shopping cart, and also it stores information about state of users shopping cart. It contains it's own database which doesn't share with any other microservice.

##### Order Service (order-fon.master.nst.service)

This microservice has the role of ordering the product after the purchase is done, by sending a report to the pharmacy by e-mail. The report was made in the Jaspersoft studio, and in addition, FreeMarker was used to create a Template for sending e-mails.

In addition to the above, tests have been written for most services that contain business logic (40-50 tests). The JaCoCo plugin was used for code coverage reports. Also, couple of logs are added and configured with ELK Stack to gain basic knowledge about this excellent open source project.

### Next steps

Implementaion and comparative analysis of design patterns for data access in microservice software architectures
