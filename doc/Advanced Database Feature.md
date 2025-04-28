# **Advanced Database Programs Documentation**

This file documents the transaction, stored procedure, and triggers implemented in the TravelMate project.

## **1. Transaction: Transfer Collection Items**

Implemented as a stored procedure to ensure atomicity and consistency when moving favorite items between collection folders.

```sql
DELIMITER //

CREATE PROCEDURE transfer_collection_items(
    IN sourceFileId INT,
    IN targetFileId INT,
    IN itemIds TEXT
)
BEGIN
    DECLARE itemList TEXT;
    
    SET itemList = itemIds;

    START TRANSACTION;

    -- Insert selected items into the target collection folder
    SET @insertSql = CONCAT('INSERT IGNORE INTO Collections (file_id, item_id) ',
                            'SELECT ', targetFileId, ', item_id FROM Collections ',
                            'WHERE file_id = ', sourceFileId, ' AND item_id IN (', itemList, ')');
    PREPARE stmt FROM @insertSql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- Remove the selected items from the source collection folder
    SET @deleteSql = CONCAT('DELETE FROM Collections ',
                            'WHERE file_id = ', sourceFileId, ' AND item_id IN (', itemList, ')');
    PREPARE stmt2 FROM @deleteSql;
    EXECUTE stmt2;
    DEALLOCATE PREPARE stmt2;

    COMMIT;
END;
//

DELIMITER ;
```

## **2. Stored Procedure: Analyze User Favorites**

Analyzes a user’s collection to find their top 3 most collected attractions and top 3 most collected states.

```sql
DELIMITER //

CREATE PROCEDURE analyze_user_favorites(IN userId INT)
BEGIN
    -- Query 1: Top 3 most collected attractions with full information
    SELECT 
        a.id,
        a.item_id,
        a.name,
        a.image_url,
        a.rating,
        a.description,
        a.state
    FROM 
        Collections c
    JOIN 
        Collection_File cf ON c.file_id = cf.file_id
    JOIN 
        Attractions a ON c.item_id = a.item_id
    WHERE 
        cf.user_id = userId
    GROUP BY 
        a.id, a.item_id, a.name, a.image_url, a.rating, a.description, a.state
    ORDER BY 
        COUNT(*) DESC
    LIMIT 3;

    -- Query 2: Top 3 states by collection counts
    SELECT 
        a.state
    FROM 
        Collections c
    JOIN 
        Collection_File cf ON c.file_id = cf.file_id
    JOIN 
        Attractions a ON c.item_id = a.item_id
    WHERE 
        cf.user_id = userId
    GROUP BY 
        a.state
    ORDER BY 
        COUNT(*) DESC
    LIMIT 3;
END;
//

DELIMITER ;
```

## **3. Triggers: Maintain Collection Counters**

Triggers are used to automatically maintain the collection count (couter) in the Items table.

### **Trigger 1: After Insert**

When a new item is added to a collection, increment the counter.

```sql
DELIMITER //

CREATE TRIGGER trg_collections_insert
AFTER INSERT ON Collections
FOR EACH ROW
BEGIN
   UPDATE Items
   SET couter = IFNULL(couter, 0) + 1
   WHERE item_id = NEW.item_id;
END;
//

DELIMITER ;
```

### **Trigger 2: After Delete**

When an item is removed from a collection, decrement the counter if possible.

```sql
DELIMITER //

CREATE TRIGGER trg_collections_delete
AFTER DELETE ON Collections
FOR EACH ROW
BEGIN
   UPDATE Items
   SET couter = CASE
       WHEN IFNULL(couter, 0) > 0 THEN couter - 1
       ELSE 0
   END
   WHERE item_id = OLD.item_id;
END;
//

DELIMITER ;
```

