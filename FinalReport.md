# Final Project Report

## 1. Changes from Original Proposal

The original proposal focused on developing a **personalized travel recommendation system**.  
However, during actual development, the recommendation feature was simplified to **analyzing user collections** (via stored procedures that generate Top 3 attractions and states).

Additionally, we introduced **folder management features** for user collections (custom folders, renaming, deletion, transferring items), which were **not included in the original proposal** but significantly enhanced the system’s data organization capabilities.

## 2. Application Usefulness

**TravelMate** successfully provides basic travel planning functionalities, including:

- Searching attractions
- Displaying detailed attraction information
- Recommending nearby hotels
- Managing user collections through folders

The system is helpful for **independent travelers and families** who need to quickly organize their trip itineraries.  
However, the application did not fully achieve **intelligent personalized recommendations** (e.g., dynamically suggesting destinations based on user interests), which slightly limits its smartness and adaptability.

## 3. Changes to Data Source or Schema

There were **no changes** to the data source or the database schema.

## 4. Changes to ER Diagram and Table Implementations

- We **added a `couter` attribute** to the `Items` table to record the number of times an item is collected.
- We **added `ON UPDATE CASCADE` and `ON DELETE CASCADE`** to all foreign key constraints to maintain referential integrity automatically.
- No major structural changes were made to the core entities (`User`, `Items`, `Collections`, `Collection_File`, `Attractions`, `Hotels`, `Relations`).

These changes improved the **robustness and consistency** of the database operations compared to the original design.

## 5. Functionalities Added or Removed

### Added:

- **Folder management** (create, rename, delete, transfer items)
- **Collection transfer functionality** (using transactions to ensure data integrity)
- **Folder analysis feature** (using stored procedures to analyze most collected attractions and states)

### Removed/Simplified:

- **Dynamic personalized recommendation** based on user preferences
- **Hotel search filtering** (currently only recommends nearby hotels without advanced sorting or filtering)

## 6. How Advanced Database Programs Complement the Application

- **Transaction:**
  - Ensures consistency when transferring collection items between folders.
- **Stored Procedure:**
  - Analyzes a user’s collection to find the most popular attractions and states.
- **Trigger:**
  - Automatically maintains the `couter` field in the `Items` table during collection insertions and deletions.

These advanced database features greatly enhanced **the consistency, automation, and reliability** of the backend operations.

## 7. Technical Challenge from Each Member

**Haipeng (Backend & Database):**

The main challenge was designing a **multi-table transactional operation** to ensure data consistency.  
Initially, MyBatis `<update>` tags were used to manage database operations, but based on project requirements, the logic was later rewritten using **JDBC Template** or **stored procedures** to handle transactions manually.  
This change was time-consuming during the later development phase.

**Advice to future teams:**  
> **Decide early** whether you need manual transaction control or stored procedures.  
> Retroactively changing the transaction mechanism after the system is partially completed can be extremely difficult and error-prone.

---

### Fangyang (Frontend)

The main challenge was handling **dynamic UI state synchronization** when users operated on collections and folders.  
Since operations like creating, renaming, or transferring folders would change the underlying data structure, React components had to re-render correctly without causing unnecessary reloading or inconsistent display states.  
We had to carefully manage **React component state** and **API interaction timing** to avoid UI flickering or stale data after operations.

**Advice to future teams:**  
> **Centralize state management** early using tools like Context API or Redux if your frontend needs to handle complex interactions.  
> Avoid redundant API calls by designing clear local state updates after successful backend operations.

---

### Zixuan (Frontend UI/UX)

The main challenge was ensuring **responsive and adaptive layout design** across different devices and screen sizes.  
Because travel planning interfaces involve search bars, lists, detailed panels, and popup dialogs, keeping the user experience consistent and user-friendly required fine-tuning **CSS flexbox/grid layouts** and handling edge cases like overflow or dynamic resizing.

**Advice to future teams:**  
> **Design mobile-first** or plan a responsive structure from the beginning.  
> Rely on standardized design systems or UI libraries to save time, and conduct early testing for different screen widths to avoid major redesigns later.

---

### Yanjun (Database & Backend Support)

The main challenge was **managing referential integrity and automatic counter updates** across multiple tables, especially under concurrent operations.  
While triggers were used to automatically maintain `couter` values in the `Items` table, ensuring that triggers, transactions, and foreign key cascades all work harmoniously required careful schema design and comprehensive testing.

**Advice to future teams:**  
> **Test database triggers and foreign key constraints thoroughly under realistic concurrent scenarios.**  
> Simulate multi-user environments early to catch potential race conditions, especially when using triggers that update aggregated fields like counters.


## 8. Other Changes Compared to the Original Proposal

- **Cancelled** the plan to build a machine learning-based recommendation engine.
- **Simplified** the user preference system.
- **Focused** on building a robust collection management and basic attraction browsing experience.

## 9. Future Work

- **Intelligent folder classification** (e.g., automatically categorize attractions into "Food", "History", etc.)
- **Behavior-based personalized recommendation** (e.g., suggest attractions based on previous browsing and collection activities)
- **Enhanced hotel search and filtering** (by price, rating, distance, etc.)

## 10. Division of Labor and Teamwork

| Team Member | Contributions |
|-------------|---------------|
| **Haipeng** | Backend architecture design (Spring Boot + MySQL), API development, Redis login authentication, backend deployment |
| **Fangyang** | Frontend React structure setup, search and collection management page implementation, details page development |
| **Zixuan** | Frontend UI/UX design (layout, CSS), page implementation, frontend interaction optimization |
| **Yanjun** | Data preprocessing and cleaning, MySQL database schema design and optimization, Transaction/Stored Procedure/Trigger implementation, partial backend
