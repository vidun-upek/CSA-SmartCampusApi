<h1 align="center"> Smart Campus Sensor & Room Management API</h1>

<h3 align="center">Client-Server Coursework | JAX-RS REST API </h3>

<br>

<h2>Overview</h2>

<p>
This project implements a RESTful API for managing smart campus resources including 
<b>rooms, sensors, and sensor readings</b>.
</p>

<p>
The API is developed using <b>Java JAX-RS (Jersey)</b> and deployed on <b>Apache Tomcat</b>.
</p>

<ul>
  <li>RESTful architecture</li>
  <li>Stateless communication</li>
  <li>Proper HTTP methods and status codes</li>
  <li>Validation and business rules</li>
  <li>Sub-resource handling</li>
  <li>Structured error handling</li>
  <li>Request/response logging</li>
</ul>

---

<h2>Conceptual Report (Coursework Answers)</h2>

<h3>Part 1: Service Architecture & Setup</h3>

<p><b>Question:</b> In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.</p>

<p><b>Answer:</b><br>
In JAX-RS, resource classes are request scoped by default, meaning a new instance is created for each incoming HTTP request. This supports the stateless nature of REST, where each request is handled independently without relying on stored state in the resource object.
However, in this implementation, application data such as rooms, sensors, and readings are stored in shared in memory collections. These collections are not request scoped and are accessed by multiple threads simultaneously. As a result, even though the resource instance itself is isolated per request, the underlying data layer is shared.
This introduces the possibility of race conditions, especially during concurrent write operations such as creating sensors or adding readings. For example, two requests modifying the same collection at the same time could lead to inconsistent data.
To address this, careful control of how shared data is accessed is required. In a production system, thread safe collections or synchronization mechanisms would be used. In this coursework, the design ensures controlled updates and avoids unnecessary concurrent conflicts, but the limitation of in memory storage is acknowledged.
</p>

<p><b>Question:</b> Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?</p>

<p><b>Answer:</b><br>
Hypermedia, or HATEOAS, enhances RESTful APIs by allowing responses to include navigational links to related resources. This enables clients to discover available operations dynamically rather than relying entirely on external documentation.
In this system, the discovery endpoint provides links to primary resources such as rooms and sensors. This allows a client to understand how to interact with the API simply by inspecting the response, without needing prior knowledge of all endpoints.
Compared to static documentation, this approach reduces tight coupling between client and server. If the API structure changes, clients that rely on hypermedia can still function correctly by following updated links. It also improves usability, as developers can explore the API interactively. Overall, hypermedia makes the API more flexible, self descriptive, and easier to maintain.
</p>

---

<h3>Part 2: Room Management</h3>

<p><b>Question:</b> When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.</p>

<p><b>Answer:</b><br>
Returning only room IDs minimizes the size of the response payload, which is beneficial when working with large datasets or limited network bandwidth. Smaller responses lead to faster transmission and reduced server load.
However, this approach increases client side complexity. The client must make additional requests to retrieve full room details, resulting in more network calls and increased latency overall. This can negatively impact performance if many rooms need to be processed.
Returning full room objects increases the response size but provides complete information in a single request. This simplifies client side logic and reduces the number of API calls required. In this implementation, full room objects are returned because the dataset is relatively small and the design prioritizes ease of use and clarity over minimal payload size.
</p>

<p><b>Question:</b> Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.</p>

<p><b>Answer:</b><br>
Yes, the DELETE operation is idempotent in this implementation. When a DELETE request is sent for an existing room, the room is removed from the system if it meets the required conditions.
If the same DELETE request is sent again, the room no longer exists. The API will then return a not found response. Although the response differs, the state of the system does not change after the first successful deletion.
This behavior satisfies the definition of idempotency, as repeated requests do not produce additional side effects. The final state of the system remains consistent regardless of how many times the same DELETE request is executed.
</p>

---

<h3>Part 3: Sensor Operations & Linking</h3>

<p><b>Question:</b> We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?</p>

<p><b>Answer:</b><br>
The @Consumes(MediaType.APPLICATION_JSON) annotation restricts the endpoint to accept only JSON formatted request bodies. When a client sends data in a different format, such as text/plain or application/xml, the JAX-RS runtime checks whether a suitable message body reader exists for that format.
If no compatible reader is found, the request is rejected and the server returns an HTTP 415 Unsupported Media Type response. This prevents the application from attempting to process data in an unsupported format.
This mechanism enforces a strict contract between client and server. It ensures that only valid, expected data formats are accepted and helps prevent runtime errors during deserialization. It also improves API reliability by rejecting invalid input early in the request lifecycle.
</p>

<p><b>Question:</b> You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?</p>

<p><b>Answer:</b><br>
Query parameters are specifically designed for filtering and searching within collections. In this case, /api/v1/sensors represents the collection, while ?type=CO2 refines the results based on a condition.
If the filter is included in the path, such as /sensors/type/CO2, it begins to represent a fixed sub resource rather than a flexible query. This approach becomes less scalable when multiple filters are required.
Query parameters allow multiple conditions to be applied easily, such as filtering by type, status, or location simultaneously. They also keep the URI structure clean and consistent with RESTful principles. For these reasons, query parameters are the preferred approach for filtering collections.
</p>

---

<h3>Part 4: Deep Nesting with Sub-Resources</h3>

<p><b>Question:</b> Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?</p>

<p><b>Answer:</b><br>
The Sub Resource Locator pattern improves API structure by delegating responsibility for nested resources to dedicated classes. In this implementation, sensor operations are handled by SensorResource file, while reading related operations are handled by a separate SensorReadingResource file.
This separation ensures that each class has a clear and focused responsibility. It avoids overloading a single resource class with too many concerns, which can make the code difficult to read and maintain.
In larger APIs, this pattern becomes even more important. Without it, a single class would need to handle multiple nested paths and operations, leading to complex and error prone code. By delegating logic to sub resource classes, the system becomes more modular, easier to extend, and better aligned with real world relationships between entities.
</p>

---

<h3>Part 5: Advanced Error Handling, Exception Mapping & Logging</h3>

<p><b>Question:</b> Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?</p>

<p><b>Answer:</b><br>
HTTP 422 is more appropriate because the request itself is valid and correctly formatted, but the server cannot process it due to a semantic issue in the data. In this case, the JSON payload is correct, but it references a resource that does not exist.
A 404 response would indicate that the endpoint itself cannot be found, which is not accurate in this scenario. The request reaches the correct endpoint successfully.
Therefore, 422 provides a more precise and meaningful response. It clearly indicates that the problem lies within the request data rather than the API structure.
</p>

<p><b>Question:</b> From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?</p>

<p><b>Answer:</b><br>
Exposing Java stack traces can reveal sensitive internal details about the system. This includes class names, package structure, method calls, and execution flow. Such information can help an attacker understand how the system is built.
An attacker could use this knowledge to identify potential weaknesses or target known vulnerabilities in specific frameworks or libraries. For example, knowing the exact technology stack can make it easier to exploit security flaws.
To prevent this, the API should not expose internal error details to clients. Instead, it should return structured and controlled error responses, while logging full details securely on the server side for debugging purposes.
</p>

<p><b>Question:</b> Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?</p>

<p><b>Answer:</b><br>
JAX-RS filters provide a centralized way to handle cross cutting concerns such as logging. They apply to all incoming requests and outgoing responses automatically, ensuring consistent behavior across the API.
This approach eliminates the need to add logging statements in every resource method, reducing code duplication and improving maintainability. It also ensures that no endpoint is missed when logging is required.
By separating logging from business logic, filters keep resource classes clean and focused. This leads to a more modular and scalable design compared to manually inserting logging statements throughout the codebase.
</p>

---

