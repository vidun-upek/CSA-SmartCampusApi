<h1 align="center">📘 Smart Campus Sensor & Room Management API</h1>

<h3 align="center">Client-Server Coursework | JAX-RS REST API 🚀</h3>

<br>

<h2> Overview</h2>

<p>
This project implements a RESTful API for managing smart campus resources including 
<b>rooms, sensors, and sensor readings</b>.
</p>

<p>
The API is developed using <b>Java JAX-RS (Jersey)</b> and deployed on <b>Apache Tomcat</b>.
</p>

<p>
The system demonstrates:
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

<h2> Technologies Used</h2>

<ul>
  <li>Java 8+</li>
  <li>JAX-RS (Jersey)</li>
  <li>Maven</li>
  <li>Apache Tomcat</li>
  <li>JSON (Jackson)</li>
</ul>

---

<h2> Project Structure</h2>

<pre>
com.smartcampus.api

├── resources
│   ├── DiscoveryResource.java
│   ├── RoomResource.java
│   ├── SensorResource.java
│   └── SensorReadingResource.java
│
├── model
│   ├── Room.java
│   ├── Sensor.java
│   └── SensorReading.java
│
├── store
│   └── InMemoryStore.java
│
├── exception
│   ├── RoomNotEmptyException.java
│   ├── LinkedResourceNotFoundException.java
│   └── SensorUnavailableException.java
│
├── mapper
│   ├── RoomNotEmptyExceptionMapper.java
│   ├── LinkedResourceNotFoundExceptionMapper.java
│   ├── SensorUnavailableExceptionMapper.java
│   └── GenericExceptionMapper.java
│
└── filter
    └── LoggingFilter.java
</pre>

---

<h2> Base URL</h2>

<pre>
http://localhost:8081/SmartCampusAPI/api/v1
</pre>

---

<h2> API Endpoints</h2>

<h3> Discovery</h3>
<pre>GET /api/v1</pre>
<p>Returns API metadata and available resources.</p>

---

<h3> Room Management</h3>

<table border="1">
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/rooms</td>
<td>Get all rooms</td>
</tr>

<tr>
<td>POST</td>
<td>/rooms</td>
<td>Create a room</td>
</tr>

<tr>
<td>GET</td>
<td>/rooms/{roomId}</td>
<td>Get room by ID</td>
</tr>

<tr>
<td>DELETE</td>
<td>/rooms/{roomId}</td>
<td>Delete room</td>
</tr>
</table>

---

<h3> Sensor Management</h3>

<table border="1">
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/sensors</td>
<td>Get all sensors</td>
</tr>

<tr>
<td>GET</td>
<td>/sensors?type=CO2</td>
<td>Filter sensors</td>
</tr>

<tr>
<td>POST</td>
<td>/sensors</td>
<td>Create sensor</td>
</tr>

<tr>
<td>GET</td>
<td>/sensors/{sensorId}</td>
<td>Get sensor by ID</td>
</tr>
</table>

---

<h3> Sensor Readings (Sub-Resource)</h3>

<table border="1">
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>

<tr>
<td>GET</td>
<td>/sensors/{id}/readings</td>
<td>Get readings</td>
</tr>

<tr>
<td>POST</td>
<td>/sensors/{id}/readings</td>
<td>Add reading</td>
</tr>
</table>

---

<h2> Business Rules</h2>

<ul>
  <li>A room <b>cannot be deleted</b> if it contains sensors</li>
  <li>A sensor must be linked to a <b>valid room</b></li>
  <li>A sensor in <b>MAINTENANCE</b> state cannot accept readings</li>
  <li>Adding a reading updates the sensor’s <b>currentValue</b></li>
</ul>

---

<h2> Error Handling</h2>

<table border="1">
<tr>
<th>Scenario</th>
<th>Status Code</th>
</tr>

<tr>
<td>Room has sensors</td>
<td>409 Conflict</td>
</tr>

<tr>
<td>Invalid room reference</td>
<td>422 Unprocessable Entity</td>
</tr>

<tr>
<td>Sensor in maintenance</td>
<td>403 Forbidden</td>
</tr>

<tr>
<td>Unexpected error</td>
<td>500 Internal Server Error</td>
</tr>
</table>

<h3>Example Response</h3>

<pre>
{
  "status": 422,
  "code": "LINKED_RESOURCE_NOT_FOUND",
  "message": "The specified roomId does not exist."
}
</pre>

---

<h2> Logging</h2>

<p>
A global logging filter is implemented using:
</p>

<ul>
  <li>ContainerRequestFilter</li>
  <li>ContainerResponseFilter</li>
</ul>

<p>Example:</p>

<pre>
Incoming Request -> Method: POST, URI: /sensors
Outgoing Response -> Status: 201
</pre>

---

<h2> Running the Project</h2>

<ol>
  <li>Clone the repository</li>
  <li>Open in NetBeans</li>
  <li>Run Maven build</li>
</ol>

<pre>
mvn clean install
</pre>

<p>Deploy on Tomcat and test using Postman.</p>

---

<h2> Testing</h2>

<p>
All endpoints were tested using Postman, covering:
</p>

<ul>
  <li>Valid requests</li>
  <li>Invalid inputs</li>
  <li>Error scenarios</li>
  <li>Business rules</li>
</ul>

---

<h2> Notes</h2>

<ul>
  <li>Data is stored in-memory</li>
  <li>Data resets when server restarts</li>
  <li>Designed for coursework demonstration</li>
</ul>

---

<h2> Author</h2>

<p><b>Vidun Shanuka</b></p>

<br>

