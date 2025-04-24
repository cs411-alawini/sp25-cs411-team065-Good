import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Card, Modal, Input, Button, Dropdown, Menu, Popconfirm, Row, Col, message } from "antd";
import { EllipsisOutlined, FolderAddOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";

const FolderPage = () => {
  const { userId } = useParams();
  const [folders, setFolders] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalMode, setModalMode] = useState("create"); // 'create' or 'rename'
  const [currentFolder, setCurrentFolder] = useState(null);
  const [inputValue, setInputValue] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchFolders();
  }, [userId]);

  const fetchFolders = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/collection_file/files?userId=${userId}`);
      const result = await res.json();
      if (Array.isArray(result)) {
        // result 是 List<CollectionFileVO>，格式可能是 [{ fileId, name }, ...]
        const mapped = result.map(item => ({ id: item.fileId, name: item.name }));
        setFolders(mapped);
      } else {
        message.error("Unexpected response format");
      }
    } catch (err) {
      console.error(err);
      message.error("Network error while loading folders");
    }
  };

  const openCreate = () => {
    setModalMode("create");
    setInputValue("");
    setModalVisible(true);
  };

  const openRename = (folder) => {
    setModalMode("rename");
    setCurrentFolder(folder);
    setInputValue(folder.name);
    setModalVisible(true);
  };

  const handleModalOk = async () => {
    if (!inputValue.trim()) {
      message.warning("Folder name cannot be empty");
      return;
    }

    try {
      if (modalMode === "create") {
        const res = await fetch("http://localhost:8080/api/collection_file/files", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ userId: parseInt(userId), name: inputValue }),
        });
        const result = await res.json();
        if (result.code === 200 || res.ok) {
          message.success("Folder created");
          fetchFolders();
        } else {
          message.error(result.msg || "Create failed");
        }
      } else if (modalMode === "rename") {
        const res = await fetch(`http://localhost:8080/api/collection_file/files/${currentFolder.id}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ name: inputValue }),
        });
        const result = await res.json();
        if (result.code === 200 || res.ok) {
          message.success("Folder renamed");
          fetchFolders();
        } else {
          message.error(result.msg || "Rename failed");
        }
      }
    } catch (err) {
      console.error(err);
      message.error("Network error");
    }

    setModalVisible(false);
    setInputValue("");
  };

  const deleteFolder = async (folderId) => {
    try {
      const res = await fetch(`http://localhost:8080/api/collection_file/files/${folderId}`, {
        method: "DELETE",
      });
      if (res.ok) {
        message.success("Folder deleted");
        fetchFolders();
      } else {
        const result = await res.json();
        message.error(result.msg || "Delete failed");
      }
    } catch (err) {
      console.error(err);
      message.error("Network error");
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 24 }}>
        <h2>My Folders</h2>
        <Button icon={<FolderAddOutlined />} type="primary" onClick={openCreate}>
          New Folder
        </Button>
      </div>

      <Row gutter={[16, 16]}>
        {folders.map((folder) => {
          const menu = (
            <Menu>
              <Menu.Item onClick={() => openRename(folder)}>Rename</Menu.Item>
              <Menu.Item>
                <Popconfirm
                  title="Are you sure you want to delete this folder?"
                  onConfirm={() => deleteFolder(folder.id)}
                >
                  Delete
                </Popconfirm>
              </Menu.Item>
            </Menu>
          );

          return (
            <Col xs={24} sm={12} md={8} lg={6} key={folder.id}>
              <Card
                title={folder.name}
                extra={<Dropdown overlay={menu}><EllipsisOutlined style={{ cursor: "pointer" }} /></Dropdown>}
                onClick={() => navigate(`user/${userId}/folder/${folder.id}`)}
              >
                <p>This is a folder.</p>
              </Card>
            </Col>
          );
        })}
      </Row>

      <Modal
        title={modalMode === "create" ? "Create New Folder" : "Rename Folder"}
        open={modalVisible}
        onOk={handleModalOk}
        onCancel={() => setModalVisible(false)}
        okText={modalMode === "create" ? "Create" : "Rename"}
      >
        <Input
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder="Enter folder name"
        />
      </Modal>
    </div>
  );
};

export default FolderPage;
