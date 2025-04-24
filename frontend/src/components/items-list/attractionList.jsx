import React, { useState, useRef, useCallback } from "react";
import Card from "../item-card/item-card";
import "./attractionList.css";

const BATCH_SIZE = 10;

const AttractionList = ({ data }) => {
  const [visibleCount, setVisibleCount] = useState(BATCH_SIZE);
  const observer = useRef(null);

  const lastItemRef = useCallback((node) => {
    //清除上一个监听，优化内存
    if (observer.current) observer.current.disconnect();
    //创建监听实例
    observer.current = new IntersectionObserver((entries) => { //entries是一个数组，包含了所有被观察的元素
      if (entries[0].isIntersecting && visibleCount < data.length) {
        setVisibleCount((prev) => Math.min(prev + BATCH_SIZE, data.length));
      }
    });
    //监听当前元素
    if (node) observer.current.observe(node);
  }, [visibleCount, data.length]);

  const visibleItems = data.slice(0, visibleCount);

  return (
    <div className="card-list">
      {visibleItems.map((item, index) =>
        index === visibleItems.length - 1 ? (
          <div ref={lastItemRef} key={item.itemId}>
            <Card item={item} />
          </div>
        ) : (
          <Card key={item.itemId} item={item} />
        )
      )}
    </div>
  );
};

export default AttractionList;
