# Simulation_theater_reservations

In this project I tried to use two different ways of comunication between microserivces. 
The first one is a simple RPI where an Api-gatway controls all the comunication (synchronous), the second one, instead, is based on Apache kafka (asynchronous).

The application is a theater reservations where are also implemented an pdf for the ticket and a simple payment on a virtual card.

For the database, I created them on the fly using MongoGB and for the graphical view I used Swagger UI.
Moreover, it is possible to test all with the interface of Swagger UI.

To analyze the test I used JaCoCo.
