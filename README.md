🚌 TTC Express: Spring MVC Lifecycle Demo
A deep-dive technical project illustrating how a request travels from Trudy & Finch to Union Station. This project demonstrates the difference between Servlet Filters and Spring Interceptors using a Toronto-themed analogy.

🗺️ System Architecture: The TTC Analogy
The Presto Tap (OncePerRequestFilter): Infrastructure level. Validates your "Fare" (API Token) before you even see the bus.

Finch Station (DispatcherServlet): The central hub that routes your request to the correct line.

The Driver (HandlerInterceptor): Application level. Checks for priority seating (preHandle) and traffic signals (postHandle).

The Destination (@RestController): The business logic—dropping you off at Union Station.

🚀 Getting Started
Prerequisites

Java 21

Maven 3.9+

Docker (Optimized for Apple Silicon/M4)

Running with Docker

Bash
# Build the image
docker build -t ttc-express-service .

# Run the container
docker run -p 8080:8080 ttc-express-service
🧪 Testing the Lifecycle
Scenario	Command	Expected Result

No Fare (Filter Block)	curl -i http://localhost:8080/api/v1/ttc/destination/union-station	402 Payment Required
Valid Fare (Full Flow)	curl -i -H "X-Presto-Token: VALID" http://localhost:8080/api/v1/ttc/destination/union-station	200 OK + Arrival Msg
check if bull is full and locale   curl -H "X-Presto-Token: MY_CARD" -H "Accept-Language: fr" http://localhost:8080/api/v1/ttc/destination/union-station
Create a Bus :  curl -X POST http://localhost:8080/api/v1/ttc/bus \                                                                            
-H "Content-Type: application/json" \
-H "X-Presto-Token: MY_CARD" \
-d '{
"routeName": "501 Queen",
"driverName": "Srinivas",
"internalDepotCode": "TTC-Lakeshore",
"lastServiceDate": "2025-12-19T10:00:00",
"active": true
}'
Create Passenger:

http POST :8080/api/v1/ttc/bus/501/passengers \
X-Presto-Token:MY_CARD \
Accept-Language:fr \
name="Jean-Talon" \
ticketId="TTC-9986" \
boardingStop="York Dale"

Get List of passengers pagination - slicing
http :8080/api/v1/ttc/bus/501/passengers page==0 size==5 X-Presto-Token:MY_CARD Accept-Language:fr
-------------------------------
##Internal Reading
N+1 resolution FetchType.Lazy and left fetch jon/EntityGraph

```http :8080/api/sql/buses X-Presto-Token:MY_CARD Accept-Language:fr```

Creat a Bus with Engine SQL
```
http POST :8080/api/sql/buses \                                   
engineSerialNumber="M4-HYBRID-2025" \
X-Presto-Token:MY_CARD \
Accept-Language:fr
```
🛠️ Tech Stack
Java 21 / Spring Boot 3+

Servlet API (Filters)

Spring Web (Interceptors/Controllers)

Docker (Multi-stage builds)

Actuator (Health & Metrics)