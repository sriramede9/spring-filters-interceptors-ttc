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

No Fare (Filter Block)	curl -i http://localhost:8080/api/ttc/destination/union-station	402 Payment Required
Valid Fare (Full Flow)	curl -i -H "X-Presto-Token: VALID" http://localhost:8080/api/ttc/destination/union-station	200 OK + Arrival Msg
check if bull is full and locale   curl -H "X-Presto-Token: MY_CARD" -H "Accept-Language: fr" http://localhost:8080/api/ttc/destination/union-station

🛠️ Tech Stack
Java 21 / Spring Boot 3+

Servlet API (Filters)

Spring Web (Interceptors/Controllers)

Docker (Multi-stage builds)

Actuator (Health & Metrics)