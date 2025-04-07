import React from "react";
import "./search-card.css";

const Card = ({ item }) => {
  return (
    <div className="card">
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

// // 类型检查
// Card.propTypes = {
//   item: PropTypes.shape({
//     locationId: PropTypes.number.isRequired,
//     name: PropTypes.string.isRequired,
//     imageUrl: PropTypes.string,
//     rating: PropTypes.number.isRequired,
//     description: PropTypes.string,
//   }).isRequired,
// };

export default Card;