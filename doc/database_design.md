# Database Design Documentation

## Part 1: Relational Schemas and Table Creation

### 1.1 Overview of Relational Schemas

This database consists of **7 relational schemas** that together support a travel-oriented platform allowing users to collect and relate attractions and hotels. The schemas include:

1. **User**  
   Stores the basic information of users including login credentials.

2. **Collection_File**  
   Represents a user's collection folder. A user can have multiple folders.

3. **Item**  
   An abstract representation of collectible entities. Each item can be either a hotel or an attraction.

4. **Attraction**  
   Contains detailed information about attractions, such as name, rating, image URL, description, and state.

5. **Hotel**  
   Similar to attractions, this table stores hotel-related information like name, rating, image URL, address, and description.

6. **Collections**  
   A many-to-many mapping between collection files and items. Each file can contain many items, and each item can appear in multiple files.

7. **Relation**  
   Represents the real-world relationship between attractions and nearby hotels. One attraction can be related to multiple hotels, and vice versa.

### 1.2 Table Creation Statements (DDL)
```sql
CREATE TABLE User (
    id INT PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(20),
    password VARCHAR(20)
);

CREATE TABLE Collection_File (
    file_id INT PRIMARY KEY,
    user_id INT,
    name VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Items (
    item_id INT PRIMARY KEY,
    type ENUM('attraction', 'hotel')
);

CREATE TABLE Attractions (
    id INT PRIMARY KEY,
    item_id INT,
    name VARCHAR(20),
    image_url VARCHAR(100),
    rating DECIMAL(3,2),
    description VARCHAR(200),
    state VARCHAR(20),
    FOREIGN KEY (item_id) REFERENCES Items(item_id)
);

CREATE TABLE Hotels (
    id INT PRIMARY KEY,
    item_id INT,
    name VARCHAR(20),
    image_url VARCHAR(100),
    rating DECIMAL(3,2),
    description VARCHAR(200),
    address VARCHAR(100),
    FOREIGN KEY (item_id) REFERENCES Items(item_id)
);

CREATE TABLE Collections (
    file_id INT,
    item_id INT,
    PRIMARY KEY (file_id, item_id),
    FOREIGN KEY (file_id) REFERENCES Collection_File(file_id),
    FOREIGN KEY (item_id) REFERENCES Items(item_id)
);

CREATE TABLE Relations (
    attraction_id INT,
    hotel_id INT,
    PRIMARY KEY (attraction_id, hotel_id),
    FOREIGN KEY (attraction_id) REFERENCES Attractions(id),
    FOREIGN KEY (hotel_id) REFERENCES Hotels(id)
);
```

### 1.3 Data Insertion
We ensured that the database is populated with **a rich set of data**, meeting the requirement of inserting **at least 1000 rows** into **3 or more tables**.

#### Tables with 1000+ rows:
- `Attraction`: Data was collected via web scraping from TripAdvisor, including real ratings, locations, and descriptions.
- `Hotel`: Similarly, real hotel data including addresses and images were extracted and cleaned.
- `Item`: Every attraction and hotel is linked to an `Item` row with type specified.
- `Relation`: Multiple hotels were associated with nearby attractions using geographic or logical proximity to populate over 1000 valid relationships.

#### 🔄 Supporting Data Generation:
- `User`: Mock names, emails, and passwords were randomly generated.
- `Collection_File`: Each user owns 1–3 folders.
- `Collections`: Each folder stores random sets of items, mimicking real user behavior.

---

## Part 2: Advanced SQL Queries

### 2.1 Query 1
**Query description**  
This SQL query retrieves each state’s total number of attractions, average attraction rating, total number of hotels, and average hotel rating. It filters out states with fewer than three attractions and orders the results by the highest average attraction rating, limiting the output to the top 15 rows.


**SQL Concepts Used**  
1. **SELECT**: Retrieves specific columns and applies aggregate functions (`COUNT`, `AVG`).
2. **JOIN**:  
   - Uses `Relations` as a bridge to connect `Attractions` (`a`) and `Hotels` (`h`).
3. **GROUP BY**: Groups rows by `a.state` to perform aggregate calculations on each state.
4. **DISTINCT**: Ensures that repeated location IDs and hotel IDs are only counted once.
5. **HAVING**: Filters groups (states) to include only those with at least three unique attractions.
6. **ORDER BY**: Sorts the results in descending order of average attraction rating.
7. **LIMIT**: Restricts the result set to 15 rows.


**SQL statement**  
```sql
SELECT
    a.state,
    COUNT(DISTINCT a.location_id) AS total_attractions,
    AVG(a.rating) AS avg_attraction_rating,
    COUNT(DISTINCT h.id) AS total_hotels,
    AVG(h.rating) AS avg_hotel_rating
FROM
    Attractions a
JOIN
    Relations r ON a.location_id = r.attraction_id
JOIN
    Hotels h ON r.hotel_id = h.id
GROUP BY
    a.state
HAVING
    COUNT(DISTINCT a.location_id) >= 3
ORDER BY
    avg_attraction_rating DESC
LIMIT 15;
```
**Query result**  
![URM](./imgs/Database_Design/query_1.png)

### 2.2 Query 2
**Query description**  
This query expands the basic user-collection lookup by also counting how many items exist in each collection. Given a specific username (e.g., `'user1'`), it joins `User` and `Collection_File`, then **LEFT JOINs** the `Collections` table to count how many `item_id` entries appear in each collection. This ensures we have both **JOIN** and **GROUP BY** to meet advanced query requirements.

**SQL concepts used**  
- **Multiple Joins**: Joins `User` → `Collection_File` → (optionally) `Collections`  
- **GROUP BY**: Groups results by `cf.file_id` to aggregate the total items per collection  
- **COUNT()**: Counts the number of items in each collection  
- **WHERE**: Filters by a specific username  
- **LIMIT**: Returns only the first 15 rows

**SQL statement**  
```sql
SELECT
    u.name AS username,
    cf.file_id,
    cf.name AS collection_name,
    COUNT(c.item_id) AS total_items
FROM User u
JOIN Collection_File cf 
    ON u.id = cf.user_id
LEFT JOIN Collections c
    ON cf.file_id = c.file_id
WHERE u.name = 'user1'
GROUP BY cf.file_id
ORDER BY total_items DESC
LIMIT 15;
```

**Query 2 Result Note**  
The result of this query includes only **2 rows**, instead of 15. This is because the user `'user1'` has relatively few saved collections. According to their usage pattern, they do not frequently browse or engage with the website, leading to fewer entries being retrieved in the query.
![URM](./imgs/Database_Design/query_2.png)

### 2.3 Query 3

**Query description**  
This query finds all hotels that are associated with the “highest-rated” attractions within each state. It uses a **subquery** to determine the maximum rating in that attraction’s state and filters any attractions whose rating matches that maximum value. Then, by joining `Hotels`, `Relations`, and `Attractions`, it retrieves the corresponding hotel names alongside those top-rated attractions.

**SQL concepts used**  
- **Multiple Joins**: (`Hotels` ↔ `Relations` ↔ `Attractions`)  
- **Subquery**: `SELECT MAX(a2.rating) FROM Attractions a2 WHERE a2.state = a.state`  
- **Filtering with a Subquery Result**: Compares the current row’s `a.rating` to the subquery’s maximum rating  
- **ORDER BY**: Sorts the final list by `AttractionName` and then `HotelName`

**SQL statement**  
```sql
SELECT
    a.name AS AttractionName,
    h.name AS HotelName
FROM Hotels h
JOIN Relations r
    ON h.id = r.hotel_id
JOIN Attractions a
    ON a.location_id = r.attraction_id
WHERE
    a.rating = (
        SELECT MAX(a2.rating)
        FROM Attractions a2
        WHERE a2.state = a.state
    )
ORDER BY AttractionName, HotelName
LIMIT 15;
```

**Query result**  
![URM](./imgs/Database_Design/query_3.png)

### 2.4 Query 4

**Query description**  
This query merges two result sets into a single list using the **UNION** operator. The first part selects hotels with ratings >= 4.0, and the second part selects attractions with ratings >= 4.5. Both sets share the same output columns (`type`, `place_name`, `rating`), allowing a combined “high-rated recommendation list.” The results are then ordered by rating in descending order and limited to the top 15 rows.

**SQL concepts used**  
- **SET Operator (UNION)**: Combines rows from two SELECT statements into one result set  
- **WHERE**: Filters results based on the hotel or attraction ratings  
- **ORDER BY**: Sorts all combined rows by rating  
- **LIMIT**: Restricts the result to 15 rows

**SQL statement**  
```sql
SELECT 'Hotel' AS type, h.name AS place_name, h.rating
FROM Hotels h
WHERE h.rating >= 4.0

UNION

SELECT 'Attraction' AS type, a.name AS place_name, a.rating
FROM Attractions a
WHERE a.rating >= 4.5
ORDER BY rating DESC
LIMIT 15;
```

**Query result**  
![URM](./imgs/Database_Design/query_4.png)

## Part 3: Indexing and Optimization

### 3.1 Index Analysis for Query 1

![URM](./imgs/Database_Design/index1_0.png)

#### Baseline

![URM](./imgs/Database_Design/index1_1.png)

#### Index on Attractions(state)

![URM](./imgs/Database_Design/index1_2.png)

##### Explanation

The index on state provides a minor improvement by slightly accelerating the GROUP BY operation. However, due to low selectivity and full-table grouping, the overall benefit is marginal.

#### Index on Attractions(state, rating)

![URM](./imgs/Database_Design/index1_3.png)

##### Explanation

This compound index provides access to both the state and rating columns, allowing MySQL to optimize the GROUP BY state and aggregation (AVG(rating)) together. Compared to the single-column state index, this index enables a **covering index scan**, which improves locality and reduces data access overhead. While the gain is modest, this index design is useful for aggregation-heavy queries involving both grouping and value computation on the same table.

#### Index on Hotels(rating)

![URM](./imgs/Database_Design/index1_4.png)

##### Explanation

This index degraded performance due to the overhead of using the rating index in an aggregation-only context.   Since no filtering is applied on rating, and the dataset is small, this index introduces unnecessary complexity.

### 3.2 Index Analysis for Query 2

- Same structure as above

### 3.3 Index Analysis for Query 3
#### Baseline
![URM](./imgs/Database_Design/index3_1.png)

#### Index on Attractions(name)
![URM](./imgs/Database_Design/index3_2.png)
##### Explanation

It does not have a significant improvement. Although the leaf nodes of a B+ Tree form a linked list, which is generally efficient for ordered retrieval, the execution analyze shows that the optimizer did not use the index for sorting. This is likely because the number of output rows is relatively small, making the cost of using the index higher than simply performing a sort in memory.

#### Index on Attractions(state)
![URM](./imgs/Database_Design/index3_3.png)
##### Explanation

It significantly reduces the search cost. Without the index, the subquery must perform a full table scan on the Attractions table. With the index, the executor can quickly locate the matching state values, narrowing down the number of rows to scan.

#### Index on Attractions(state, rating)
![URM](./imgs/Database_Design/index3_4.png)
##### Explanation

We ultimately chose to use a composite index. By including rating in the index, it enables a covering index lookup for the subquery. This allows the executor to retrieve the required rating values directly from the index, without accessing the primary B+ tree, which improves the efficiency of the MAX() aggregation.

### 3.4 Index Analysis for Query 4
#### Baseline
![URM](./imgs/Database_Design/index4_1.png)

#### Index on Attractions(rating)
![URM](./imgs/Database_Design/index4_2.png)
##### Explanation

Adding Attractions.rating only may cause the worst performance, the presence of the index caused the planner to overestimate the number of qualifying rows, leading to higher estimated costs for deduplication and sorting in the union operation.

#### Index on Hotel(rating)
![URM](./imgs/Database_Design/index4_3.png)
##### Explanation

Indexing Hotels.rating resulted in the best performance. It is used very effectively, as the filtering condition was very selective, significantly reduced the number of rows early in the execution. 

#### Index on Hotel(rating, name)
![URM](./imgs/Database_Design/index4_4.png)
##### Explanation

We ultimately chose to use this composite index. Beside the benefit of indexing Hotel.rating, it also provides the covering index lookup for name, so the executor does not have the need to accessing the primary B+ tree, improving the efficiency.

---

## Part 4: Appendix

### 4.1 Database Deployment Screenshot
- Screenshot of terminal showing database creation locally or on GCP

