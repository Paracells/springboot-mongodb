### Simple Spring Boot MongoDB project, just for learning.

We have 

Model **`Hotel`**, **`Address`** and **`Review`**.

Project have some fake DATA in **DbSeeder.java**

First you need configure `application.properties`
You can install MongoDB to your PC: Docker, from official website, or use cloud

Some simple request

``````
`/hotelsall` - get all Hotels from DB, this method have POST, PUT, GET

`/hotel/id` - DELETE by id

`/hotel/id` - GET by id

`/price/{maxPrice}` - GET with maxPrice

`/country/{country}` - customize mapping (see src HotelController.java)

`/recommended` - customize mapping
