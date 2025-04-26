import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Button, message } from "antd";
import CollectionList from "../../components/collection-list/collectionList";
import "./collectionPage.css"; // 引入样式
import TopHeader from '../header/header.jsx';
// import mockData from "./result.json"; // 引入本地数据

function CollectionPage() {
  const { folderId } = useParams(); // 对应收藏夹 ID
  const [attractions, setAttractions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [deletionMode, setDeletionMode] = useState(false);
  const [selectedIds, setSelectedIds] = useState([]);

  //   useEffect(() => {
  //     fetchAttractions();
  //   }, [folderId]);

  //   const fetchAttractions = async () => {
  //     setLoading(true);
  //     try {
  //       setAttractions(mockData.data); // 👈 本地数据，不请求后端
  //     } catch (err) {
  //       console.error("Load mock data error:", err);
  //       message.error("Load local data error");
  //     } finally {
  //       setLoading(false);
  //     }
  //   };

  useEffect(() => {
    fetchAttractions();
  }, [folderId]);

  const fetchAttractions = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(
        `http://localhost:8080/api/collection_file/files/${folderId}/items`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const result = await res.json();
      if (Array.isArray(result.data)) {
        setAttractions(result.data);
        console.log(result.data);
      } else {
        message.error("Unexpected response format");
      }
    } catch (err) {
      console.error("Fetch error:", err);
      message.error("Network error");
    } finally {
      setLoading(false);
    }
  };

  const handleSelectChange = (itemId, checked) => {
    setSelectedIds((prev) =>
      checked ? [...prev, itemId] : prev.filter((id) => id !== itemId)
    );
  };

  const handleBatchDelete = async () => {
    if (selectedIds.length === 0) return;
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(
        `http://localhost:8080/api/collection_file/files/${folderId}/items`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ items: selectedIds }),
        }
      );

      const result = await res.json();
      if (res.ok) {
        message.success("Items deleted");
        setSelectedIds([]);
        fetchAttractions();
      } else {
        message.error(result.msg || "Delete failed");
      }
    } catch (err) {
      console.error(err);
      message.error("Network error");
    }
  };

  return (
    <div>
      <TopHeader/>
    <div className="search-page">
      <h1 className="search-title">My Favorite Items</h1>
      <div style={{ marginBottom: 16 }}>
        <Button onClick={() => setDeletionMode(!deletionMode)}>
          {deletionMode ? "Cancel" : "Batch Delete"}
        </Button>
        {deletionMode && selectedIds.length > 0 && (
          <Button danger onClick={handleBatchDelete} style={{ marginLeft: 8 }}>
            Confirm Delete ({selectedIds.length})
          </Button>
        )}
      </div>

      {loading ? (
        <p>Loading...</p>
      ) : (
        <CollectionList
          data={attractions}
          deletionMode={deletionMode}
          selectedIds={selectedIds}
          onSelectChange={handleSelectChange}
        />
      )}
    </div>
    </div>
  );
}

export default CollectionPage;
