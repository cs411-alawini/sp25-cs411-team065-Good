import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Button, message } from "antd";
import CollectionList from "../../components/collection-list/collectionList";
import "./collectionPage.css";
import TopHeader from "../header/header.jsx";

function CollectionPage() {
  const { folderId } = useParams();
  const [attractions, setAttractions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [deletionMode, setDeletionMode] = useState(false);
  const [selectedIds, setSelectedIds] = useState([]);
  const [folders, setFolders] = useState([]);
  const [selectedFolderId, setSelectedFolderId] = useState(null);

  useEffect(() => {
    fetchAttractions();
    fetchFolders();
  }, [folderId]);

  const fetchAttractions = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(
        `http://35.226.211.97:8080/api/collection_file/files/${folderId}/items`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const result = await res.json();
      if (Array.isArray(result.data)) {
        setAttractions(result.data);
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

  const fetchFolders = async () => {
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(`http://35.226.211.97:8080/api/collection_file/files`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const result = await res.json();
      if (Array.isArray(result.data)) {
        setFolders(result.data.filter(f => f.fileId !== parseInt(folderId)));
      }
    } catch (err) {
      console.error("Fetch folders error:", err);
    }
  };

  const handleSelectChange = (itemId, checked) => {
    setSelectedIds(prev =>
      checked ? [...prev, itemId] : prev.filter(id => id !== itemId)
    );
  };

  const handleBatchDelete = async () => {
    if (selectedIds.length === 0) return;
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(
        `http://35.226.211.97:8080/api/collection_file/files/${folderId}/items`,
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

  const handleBatchMove = async () => {
    if (selectedIds.length === 0 || !selectedFolderId) return;
    try {
      const token = localStorage.getItem("sessionId");
      const res = await fetch(
        `http://35.226.211.97:8080/api/collection_file/files/${folderId}/${selectedFolderId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ itemIds: selectedIds }),
        }
      );
      const result = await res.json();
      if (res.ok) {
        message.success("Items moved");
        setSelectedIds([]);
        fetchAttractions();
      } else {
        message.error(result.msg || "Move failed");
      }
    } catch (err) {
      console.error(err);
      message.error("Network error");
    }
  };

  return (
    <div>
      <TopHeader />
      <div className="search-page">
        <h1 className="search-title">My Favorite Items</h1>
        <div style={{ marginBottom: 16 }}>
          <Button onClick={() => setDeletionMode(!deletionMode)}>
            {deletionMode ? "Cancel" : "Batch Operation"}
          </Button>
          {deletionMode && selectedIds.length > 0 && (
            <>
              <Button danger onClick={handleBatchDelete} style={{ marginLeft: 8 }}>
                Confirm Delete ({selectedIds.length})
              </Button>

              <select
                value={selectedFolderId || ""}
                onChange={(e) => setSelectedFolderId(e.target.value)}
                style={{ marginLeft: 8 }}
              >
                <option value="">Select folder</option>
                {folders.map((f) => (
                  <option key={f.fileId} value={f.fileId}>
                    {f.name}
                  </option>
                ))}
              </select>

              <Button type="primary" onClick={handleBatchMove} style={{ marginLeft: 8 }}>
                Move Items
              </Button>
            </>
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
