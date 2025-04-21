import React, { useState, useEffect } from 'react';
import { Card, Typography, Image, Row, Col, Tag, message } from 'antd';
import { HeartOutlined, HeartFilled } from '@ant-design/icons';
import { useParams } from 'react-router-dom';

const { Title, Paragraph } = Typography;

const Attractions = () => {
  const { id } = useParams();
  const [isFavorited, setIsFavorited] = useState(false);
  const [attraction, setAttraction] = useState(null);
  const [nearbyHotels, setNearbyHotels] = useState([]);

  const fetchAttraction = async () => {
    try {
      const res = await fetch(`/api/attractions?id=${id}`);
      const data = await res.json();
      setAttraction(data);
      setIsFavorited(data.favorited || false);
    } catch (err) {
      console.error('Failed to fetch attraction:', err);
    }
  };

  const fetchNearbyHotels = async () => {
    try {
      const res = await fetch(`/api/attractions/nearbyhotel?id=${id}`);
      const data = await res.json();
      setNearbyHotels(data);
    } catch (err) {
      console.error('Failed to fetch hotels:', err);
    }
  };

  const toggleFavorite = async () => {
    try {
      await fetch('/api/user/folder/addItem', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ itemId: attraction.itemId, folderId: 1 })
      });
      setIsFavorited((prev) => !prev);
    } catch (err) {
      console.error('Failed to toggle favorite:', err);
      message.error('Failed to update favorite status');
    }
  };

  useEffect(() => {
    fetchAttraction();
    fetchNearbyHotels();
  }, [id]);

  if (!attraction) return <div>Loading...</div>;

  return (
    <div style={{ padding: '32px' }}>
      {/* 景点区域 */}
      <Row gutter={32}>
        <Col span={16}>
          <Title level={2}>
            {attraction.name}
            <Tag color="gold" style={{ marginLeft: 16, fontSize: 16 }}>{attraction.rating}</Tag>
          </Title>
          <Paragraph>{attraction.desc}</Paragraph>
        </Col>
        <Col span={8} style={{ textAlign: 'right' }}>
          <Image width={200} src={attraction.url} />
          <div style={{ marginTop: 12 }}>
            {isFavorited ? (
              <HeartFilled onClick={toggleFavorite} style={{ fontSize: 24, color: 'red' }} />
            ) : (
              <HeartOutlined onClick={toggleFavorite} style={{ fontSize: 24, color: 'red' }} />
            )}
          </div>
        </Col>
      </Row>

      {/* 酒店区域 */}
      <Title level={4} style={{ marginTop: 48 }}>Nearby Hotels</Title>

      {nearbyHotels.map((hotel) => (
        <Card key={hotel.itemId} style={{ marginBottom: 16 }}>
          <Row gutter={16} align="middle">
            <Col span={6}>
              <Image width={120} src={hotel.url} />
            </Col>
            <Col span={17}>
              <Title level={5} style={{ marginBottom: 4 }}>
                {hotel.name}
                <span style={{ marginLeft: 16, fontSize: 16, color: '#666' }}>{hotel.rating}</span>
              </Title>
              <Paragraph style={{ marginBottom: 0 }}>{hotel.desc}</Paragraph>
            </Col>
            <Col span={1} style={{ textAlign: 'right' }}>
              {/* 你可以加是否收藏状态控制 */}
              <HeartOutlined style={{ fontSize: 20, color: 'red' }} />
            </Col>
          </Row>
        </Card>
      ))}
    </div>
  );
};

export default Attractions;
