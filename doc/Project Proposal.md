# TravelMate - A Personalized Travel Companion
## Summary  
TravelMate is a web application designed to help users discover and plan their trips effortlessly. The homepage features a selection of recommended tourist attractions, allowing users to explore popular destinations. Users can search for attractions by state name, leading them to a results page displaying various travel spots in that region. Clicking on an attraction provides detailed information, including a description of the place and nearby hotel recommendations.  

Additionally, users can register and log in to personalize their experience. They can save their favorite destinations for future reference, creating a travel wishlist. This platform simplifies travel planning by offering an intuitive interface with relevant travel insights.  

## Description  
This project aims to create a comprehensive travel planning web application that simplifies finding and saving travel destinations. Many travelers struggle with discovering suitable places and organizing their itineraries, especially when searching for attractions in unfamiliar regions.  

## Usefulness Description of the Travel Recommendation System

In today’s information-rich world, travelers often struggle to find suitable attractions and plan their trips efficiently and quickly. Our travel recommendation system aims to solve this problem by leveraging the TripAdvisor dataset to provide users with popular attraction recommendations and detailed travel information, helping them easily create well-structured travel plans.

### Core Features

- **Attraction Information Search**: Users can search for travel destinations and access key details such as descriptions, ratings, opening hours, and user reviews.
- **Popular Attraction Recommendations**: The system intelligently recommends attractions based on user preferences and current travel trends.
- **Accommodation Information (Optional)**: Users can explore nearby hotels, including pricing, ratings, and user reviews, to plan their stays conveniently.

### Key Differentiators

- **Personalized Recommendations**: Unlike generic ranking-based suggestions, our system tailors recommendations based on user interests and travel history.
- **Integrated Attractions & Accommodation**: Unlike many existing platforms focusing on attractions or lodging, our system combines both for seamless trip planning.
- **Smart Search & Filtering**: Users can quickly refine search results based on budget, activity type, and other criteria to find ideal attractions and accommodations.

### Target Users

- **Independent Travelers**: Easily discover interesting attractions and plan trips efficiently.
- **Families & Groups**: Find group-friendly attractions and accommodations.
- **Travel Bloggers**: Access trending attraction data for content creation.

## Creative Component

We will implement a personalized travel recommendation system to enhance user experience and differentiate TravelMate from other travel planning platforms. This system will provide users customized attraction suggestions based on their preferences and past interactions.

### Key Features

1.	 Personalized Recommendations
    - The system will analyze user behavior, such as previously viewed or saved attractions, to suggest relevant destinations.
    - It will combine user preferences with attraction metadata to generate recommendations tailored to individual interests.

2.	 Intelligent Tagging of Attractions
    - By processing attraction descriptions and user reviews, the system will categorize locations into meaningful groups (e.g., historical sites, nature spots, adventure activities).
    - This allows users to receive recommendations aligned with their interests and travel styles.


These features will make TravelMate more engaging and helpful, offering intelligent, data-driven recommendations instead of generic travel suggestions.

## Realness of data source 

The data is retrieved from the **TripAdvisor API**.

We will use:  
- `https://api.content.tripadvisor.com/api/v1/location/{locationId}/details` to fetch details of **Attractions** and **Hotels**.  
- `https://api.content.tripadvisor.com/api/v1/location/nearby_search` to determine the spatial relationships between hotels and attractions.  

### **Data Processing**  
1. First, we will call the API to retrieve and store the data as a **CSV file**.  
2. Then, we will load it into a **MySQL database** for further **CRUD operations**.  

### **Dataset Information**  

#### **Hotel Details Dataset**  
- Cardinality: **~1000 rows**  
- Attributes:  
  - `id`  
  - `name`  
  - `image_url`  
  - `rating`  
  - `description`  

#### **Attraction Details Dataset**  
- Cardinality: **~1000 rows**  
- Attributes:  
  - `id`  
  - `name`  
  - `image_url`  
  - `rating`  
  - `description`  

#### **Relationship Dataset**  
- Cardinality: **~1000 rows**  
- Attributes:  
  - `id`  
  - `attraction_id`  
  - `hotel_id`  

## Functionality  
The TravelMate web application offers the following core functionalities to enhance the user experience:  

### 1. Homepage with Recommended Attractions  
- Displays a selection of popular and recommended tourist attractions.  
- Provides an engaging interface with images and brief descriptions of each location.  

### 2. Search for Attractions by State  
- Users can enter a state name to search for travel destinations within that region.  
- The search results page presents a list of matching attractions and key details.  

### 3. Attraction Details Page  
- Clicking on an attraction shows a detailed page with:  
  - A full description of the place.  
  - Nearby hotel recommendations to assist with trip planning.  

### 4. User Authentication (Registration & Login)  
- New users can register an account with an email and password.  
- Existing users can log in to access personalized features.  

### 5. Favorite Attractions (Create, Delete, View)  
- Logged-in users can save attractions to their favorites list for future reference.  
- Users can view and manage their saved attractions (remove or revisit).

## Low-Fidelity UI Mockup of the Application

The project proposal includes a detailed low-fidelity UI mockup to illustrate the structure and layout of the TravelMate web application. These wireframes provide a visual representation of the core pages and user interactions.

### **Homepage**
The homepage displays a selection of recommended tourist attractions. Users can search for attractions using the search bar and filter results by category.

![Homepage Mockup](./imgs/Project Proposal/UI%20mockup%20image1.png)

### **Search Results Page**
After searching for a location, users are presented with a list of attractions matching their query. Each result includes an image, name, and rating.

![Search Results Mockup](./imgs/Project Proposal/UI%20mockup%20image2.png)

### **Attraction Details Page**
Clicking on an attraction opens a detailed page with a full description, images, and hotel recommendations.

![Attraction Details Mockup](./imgs/Project Proposal/UI%20mockup%20image3.png)

### **User Favorites Page**
Registered users can save attractions to their favorites list for future reference. They can view and manage their saved destinations here.

![User Favorites Mockup](./imgs/Project Proposal/UI%20mockup%20image4.png)

These mockups guide the UI design, ensuring a user-friendly experience and an intuitive navigation structure.

## Project Team Responsibilities

### Fangyang: Frontend Architecture & Backend API Integration
Fangyang is primarily responsible for designing the overall front-end architecture and ensuring seamless interaction between the front-end and back-end APIs, enabling dynamic data loading and display. Additionally, Fangyang is accountable for implementing four front-end pages.

#### Specific Responsibilities

##### Frontend Architecture Design
- Select an appropriate front-end framework (**React**).
- Plan the project's component structure to ensure modular, reusable, and maintainable front-end code.
- Organize frontend routing management, allowing smooth navigation between different pages (e.g., the homepage, the attraction details page, and the user favorites list).
- Handle frontend state management (**Redux**) to manage search queries, recommended attractions, and user favorites.

##### Frontend & Backend API Integration
- Implement API calls from the front end to retrieve attraction details, hotel recommendations, and user favorites.
- Parse backend responses and render the data on the front end (e.g., display popular attractions and search results).
- Process user inputs (e.g., search queries, login/registration) and submit them to the backend API for processing.

---

### Zixuan: CSS, HTML, UI Design, & Frontend
Zixuan mainly handles UI design, HTML & CSS development, and the implementation of two frontend pages.

#### Specific Responsibilities

##### Frontend UI Design
- Design and implement the homepage to display recommended attractions with images, names, ratings, and other essential details.
- Design the search results page, ensuring users can easily browse matching attractions.
- Design the attraction details page, presenting complete information and nearby hotel recommendations.
- Design the user favorites page, allowing users to view and manage their saved attractions.

##### HTML & CSS Development
- Write the HTML structure, ensuring a well-organized and semantic page layout.
- Apply CSS styling optimizations to enhance the website’s appearance and user experience.
- Use CSS frameworks (e.g., Bootstrap, Tailwind CSS) to accelerate development and improve adaptability.
- Implement a responsive design to ensure compatibility across both desktop and mobile devices.

---

### Yanjun: Data Processing & Database Design
Yanjun is responsible for processing, cleaning, and modeling data while collaborating with Haipeng to design the database, ensuring efficient and structured data storage and retrieval.

#### Specific Responsibilities

##### Data Processing
- Parsed TripAdvisor's dataset, conducting data preprocessing (e.g., deduplication, noise reduction, formatting).
- Process geographic location data to facilitate efficient location matching between front-end and back-end APIs.
- Categorize data based on popularity, ratings, attraction type, etc., to support recommendation features.

##### Database Design
- Design the attraction information table, storing attraction names, descriptions, ratings, and user reviews.
- Design the user table, storing user registration details and preference data.
- Design the user favorites table, recording users' saved attractions with support for create, update, and delete operations.
- Design the hotel information table, storing recommended hotels near attractions.

##### Data Optimization
- Optimize database queries through indexing to improve search and recommendation response times.
- Perform data cleaning and transformation to support backend API requests and structured responses.

---

### Haipeng: SQL CRUD, Backend Server Architecture, & API Development
Haipeng is responsible for designing backend APIs, managing the database, and setting up the server architecture to ensure smooth communication between the front and back end.

#### Specific Responsibilities

##### Backend Server Architecture
- Select an appropriate backend framework (e.g., **Spring Boot**).
- Design RESTful APIs to ensure efficient access to attraction details, user data, and recommendations.
- Implement user authentication.

##### Database Operations (SQL CRUD)
- Manage database operations, including creating, reading, updating, and deleting (**CRUD**) attraction and user data.
- Optimize database queries to enhance retrieval efficiency and prevent performance bottlenecks.
- Develop an API for user favorites, allowing users to add, remove, and view saved attractions.

##### API Development
- Implement a **search attraction API**, returning matching attractions based on user input.
- Implement a **popular attractions API**, returning top-recommended destinations.
- Implement an **attraction details API**, providing comprehensive information about attractions and nearby hotels.
- Implement a **user favorites API**, enabling users to manage their saved attractions.
