import React, { useState, useEffect } from 'react';
import { Layout, Card, Typography, Image, Row, Col, Tag, message, Modal, Button, List } from 'antd';
import { HeartOutlined, HeartFilled } from '@ant-design/icons';
import { useParams } from 'react-router-dom';
import TopHeader from '../header/header.jsx';
const { Title, Paragraph } = Typography;
import mockUrl from '@/assets/illinois.png';
import { useNavigate } from 'react-router-dom';

const DetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [isFavorited, setIsFavorited] = useState(false);
  const [attraction, setAttraction] = useState(null);
  const [nearbyHotels, setNearbyHotels] = useState([]);
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [folderModalVisible, setFolderModalVisible] = useState(false);
  const [folders, setFolders] = useState([]);
  const [selectedFolderId, setSelectedFolderId] = useState(null);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const token = localStorage.getItem('token');
  const handleUserClick = () => {
    if (!isLoggedIn) {
      navigate('/login');
    }
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  const handleSearch = (value) => {
  if (value.trim()) {
      navigate(`/search?state=${encodeURIComponent(value.trim())}`);
  }
  };

  const UserMenu = ({ onLogout }) => (
    <Menu>
      <Menu.Item key="1">
        <a href="/favorites">Favorite</a>
      </Menu.Item>
      <Menu.Item key="2" onClick={onLogout}>
        Logout
      </Menu.Item>
    </Menu>
  );

  
  const userId = 1;

  const fetchAttraction = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/attractions/${id}`);
      const whole = await res.json();
      const data = whole.data;
      setAttraction(data);
    } catch (err) {
      console.error('Failed to fetch attraction:', err);
    }
  };

  const fetchFavoriteStatus = async () => {
    // try {
    //   const res = await fetch(`http://localhost:8080/api/collection/items/{itemId}/exists`);
    //   const data = await res.json();
    //   setIsFavorited(data.favorited);
    // } catch (err) {
    //   console.error('Failed to fetch favorite status:', err);
    // }
    setIsFavorited(false);
  };

  const fetchNearbyHotels = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/hotels/by-attraction/${id}`);
      const whole = await res.json();
      const data = whole.data;        
      setNearbyHotels(data);
    } catch (err) {
      console.error('Failed to fetch hotels:', err);
    }
  };

  const fetchFolders = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/collection_file/files`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });      
      const data = await res.json();
      setFolders(data);
    } catch (err) {
      console.error('Failed to fetch folders:', err);
    }
    // setFolders([
    //   { folderId: 1, folderName: 'Mock Folder 1' },
    //   { folderId: 2, folderName: 'Mock Folder 2' },
    //   { folderId: 3, folderName: 'Mock Folder 3' }
    // ]);
  };

  const openFolderSelector = async (item) => {
    setSelectedHotel(item);
    await fetchFolders();
    setFolderModalVisible(true);
  };

  const confirmAddToFolder = async () => {
    if (!selectedFolderId) 
      return message.warning('Please select a folder');
    try {
      const itemId = selectedHotel?.itemId || attraction.itemId;
      await fetch(`/api/collection_file/files/${selectedFolderId}/items/${itemId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
    
      // 更新收藏状态
      if (selectedHotel) {
        // 是收藏酒店
        setNearbyHotels((prev) =>
          prev.map((h) =>
            h.itemId === selectedHotel.itemId ? { ...h, favorited: true } : h
          )
        );
      } else {
        setIsFavorited(true);
      }
    
      // 关闭弹窗
      setFolderModalVisible(false);
    
      // 成功提示
      message.success('Added to folder successfully');
    } catch (err) {
      console.error('Failed to add to folder:', err);
      message.error('Failed to update favorite status');
    }
  };

  const removeFromFolder = async (itemId) => {
    try {
      const itemId = selectedHotel?.itemId || attraction.itemId;
      await fetch(`/api/collection_file/files/${selectedFolderId}/items/${itemId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      return true;
    } catch (err) {
      console.error('Failed to remove from folder:', err);
      message.error('Fail to remove from folder');
      return false;
    }
  };
  

  useEffect(() => {
    fetchAttraction();
    fetchFavoriteStatus();
    fetchNearbyHotels();
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, [id]);

  if (!attraction) return <div>Loading...</div>;

  return (
    <Layout style={{ minHeight: '100vh', width: '100vw', overflowX: 'hidden', backgroundColor: '#f5f5f5' }}>
    <TopHeader
        isLoggedIn={isLoggedIn}
        handleLogout={handleLogout}
        handleUserClick={handleUserClick}
        UserMenu={UserMenu}
        handleSearch={handleSearch}
      />
    <div style={{ padding: '32px', backgroundColor:'#f5f2eb' }}>
      {/* 景点区域 */}
      <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '32px' }}>
        {/* 景点信息区域 */}
        <div style={{ marginBottom: '30px' }}>
          {/* 名称 + 收藏 */}
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
              <Title level={2} style={{ margin: 0 }}>
                {attraction.name}
                <Tag color="gold" style={{ marginLeft: 16, fontSize: 16 }}>{attraction.rating}</Tag>
              </Title>
            </div>
            <div>
            {isFavorited ? (
              <HeartFilled
                style={{ fontSize: 24, color: 'red', cursor: 'pointer' }}
                onClick={async () => {
                  const ok = await removeFromFolder(attraction.itemId);
                  if (ok) setIsFavorited(false);
                }}
              />
            ) : (
              <HeartOutlined
                onClick={() => openFolderSelector(null)}
                style={{ fontSize: 24, color: 'red', cursor: 'pointer' }}
              />
            )}
          </div>

          </div>

          {/* 描述 + 图片 */}
          <div style={{ display: 'flex', gap: '24px', marginTop: '16px' }}>
          <div style={{ flex: 1}}>
              <Paragraph style={{ fontSize: 20 }}>{attraction.description}</Paragraph>
            </div>
            <Image
              src={attraction.imageUrl}
              width={400}
              height={250}
              style={{ borderRadius: 4, objectFit: 'cover' }}
            />
          </div>
        </div>
      </div>


      {/* 酒店区域 */}
      <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '26px', backgroundColor: '#f8f8f8'}}>
      <Title level={4} style={{ marginTop: 16, marginBottom: 24, fontSize: 24 }}>Nearby Hotels</Title>


      {nearbyHotels.map((hotel) => (
        <Card key={hotel.itemId} style={{ marginBottom: 16 }}>
          <Row gutter={16} align="middle">
            <Col span={6}>
              <Image width={120} src={hotel.imageUrl} />
            </Col>
            <Col span={17}>
            <Title
              level={5}
              style={{
                margin: 0,
                lineHeight: '1',
                display: 'inline-block',
                verticalAlign: 'top',
              }}
            >
                {hotel.name}
                <span style={{ marginLeft: 16, fontSize: 16, color: '#666' }}>{hotel.rating}</span>
              </Title>
              <Paragraph style={{ marginBottom: 0 }}>{hotel.description}</Paragraph>
            </Col>
            <Col span={1} style={{ textAlign: 'right' }}>
            {hotel.favorited ? (
              <HeartFilled
                style={{ fontSize: 20, color: 'red', cursor: 'pointer' }}
                onClick={async () => {
                  const ok = await removeFromFolder(hotel.itemId);
                  if (ok) {
                    setNearbyHotels((prev) =>
                      prev.map((h) =>
                        h.itemId === hotel.itemId ? { ...h, favorited: false } : h
                      )
                    );
                  }
                }}
              />
            ) : (
              <HeartOutlined
                style={{ fontSize: 20, color: 'red', cursor: 'pointer' }}
                onClick={() => openFolderSelector(hotel)}
              />
            )}
            </Col>
          </Row>
        </Card>
      ))}

      {/* 收藏夹弹窗 */}
      <Modal
        title="Please choose a folder"
        visible={folderModalVisible}
        onCancel={() => setFolderModalVisible(false)}
        onOk={confirmAddToFolder}
        okText="Confirm"
        centered
        bodyStyle={{ maxHeight: 300, overflowY: 'auto' }}
      >
        <List
          dataSource={folders}
          renderItem={(folder) => (
            <List.Item
              onClick={() => setSelectedFolderId(folder.folderId)}
              style={{
                border: '1px solid #ccc',
                padding: '10px',
                cursor: 'pointer',
                backgroundColor: folder.folderId === selectedFolderId ? '#e6f7ff' : '#fff'
              }}
            >
              {folder.folderName}
            </List.Item>
          )}
        />
      </Modal>
      </div>
    </div>
    </Layout>
  );
};

export default DetailPage;
