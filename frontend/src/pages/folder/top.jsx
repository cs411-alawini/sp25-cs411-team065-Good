import React, { useState, useEffect } from 'react';
import { Layout, Card, Row, Col, message } from 'antd';
import { useNavigate } from 'react-router-dom';

const { Content } = Layout;

const Top = () => {
  const [topStates, setTopStates] = useState([]);
  const [topAttractions, setTopAttractions] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('sessionId');
        const response = await fetch('http://35.226.211.97:8080/api/collection/analyze', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
        });
        const result = await response.json();
        
        if (result.code === "200") {
          setTopStates(result.data.topStates || []);
          setTopAttractions(result.data.topAttractions || []);
        } else {
          message.error('Failed to load data');
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        message.error('Network error');
      }
    };

    fetchData();
  }, []);

  const handleStateClick = (stateName) => {
    navigate(`/search?state=${encodeURIComponent(stateName)}`);
  };

  const handleAttractionClick = (locationId) => {
    navigate(`/attraction/${locationId}`);
  };

  return (
    <Content style={{ padding: '24px' }}>
      {/* Top States */}
      <h2>Top States</h2>
      <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap', marginBottom: '24px' }}>
        {topStates.map((state, index) => (
          <div
            key={index}
            onClick={() => handleStateClick(state)}
            style={{
              padding: '8px 16px',
              border: '1px solid #ccc',
              borderRadius: '8px',
              cursor: 'pointer',
              backgroundColor: '#fafafa',
              fontWeight: 'bold',
            }}
          >
            {state}
          </div>
        ))}
      </div>

      {/* Top Attractions */}
      <h2>Top Attractions</h2>
      <Row gutter={[16, 16]}>
        {topAttractions.map(attraction => (
          <Col key={attraction.locationId} xs={24} sm={12} md={8} lg={6}>
            <Card
              hoverable
              cover={
                <img
                  alt={attraction.name}
                  src={attraction.imageUrl}
                  style={{ height: '200px', objectFit: 'cover' }}
                />
              }
              onClick={() => handleAttractionClick(attraction.locationId)}
            >
              <Card.Meta title={attraction.name} />
            </Card>
          </Col>
        ))}
      </Row>
    </Content>
  );
};

export default Top;
