import React from "react";
import "./collectionCard.css"; // 引入样式
import { useNavigate } from "react-router-dom";

const CollectionCard = ({ item, deletionMode = false, selected = false, onSelectChange }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    if (!deletionMode) {
      navigate(`/attraction/${item.locationId}`);
    }
  };

  const handleCheckboxClick = (e) => {
    e.stopPropagation(); // 防止冒泡到卡片点击跳转
    onSelectChange?.(item.itemId, !selected);
  };

  return (
    <div className="card" onClick={handleCardClick} style={{ position: "relative" }}>
      {deletionMode && (
        <input
          type="checkbox"
          className="card-checkbox"
          checked={selected}
          onChange={handleCheckboxClick}
          style={{ position: "absolute", top: 8, right: 8, zIndex: 2 }}
        />
      )}

      {/* 图片 */}
      <figure className="card-image">
        <img src={item.imageUrl || "/img/default-image.jpg"} alt={item.name} />
      </figure>

      {/* 内容 */}
      <div className="card-content">
        <header className="card-header">
          <h2 className="title">{item.name}</h2>
          <p className="rating">Rating: {item.rating}</p>
        </header>
        <p className="description">{item.description || "No description available."}</p>
      </div>
    </div>
  );
};

export default CollectionCard;
