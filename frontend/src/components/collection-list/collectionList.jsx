import React, { useState, useRef, useCallback } from "react";
import CollectionCard from "../collection-card/collectionCard";
import "./collectionList.css"; // 引入样式

const BATCH_SIZE = 10;

const CollectionList = ({ data, deletionMode = false, selectedIds = [], onSelectChange }) => {
  const [visibleCount, setVisibleCount] = useState(BATCH_SIZE);
  const observer = useRef(null);

  const lastItemRef = useCallback(
    (node) => {
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && visibleCount < data.length) {
          setVisibleCount((prev) => Math.min(prev + BATCH_SIZE, data.length));
        }
      });
      if (node) observer.current.observe(node);
    },
    [visibleCount, data.length]
  );

  const visibleItems = data.slice(0, visibleCount);

  return (
    <div className="card-list">
      {visibleItems.map((item, index) => {
        const card = (
          <CollectionCard
            item={item}
            deletionMode={deletionMode}
            selected={selectedIds.includes(item.itemId)}
            onSelectChange={onSelectChange}
          />
        );

        return index === visibleItems.length - 1 ? (
          <div ref={lastItemRef} key={item.itemId}>
            {card}
          </div>
        ) : (
          <div key={item.itemId}>{card}</div>
        );
      })}
    </div>
  );
};

export default CollectionList;
