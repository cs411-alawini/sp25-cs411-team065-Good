import React, { useState, useEffect } from 'react';
import { Layout, Menu, Input, Card, Row, Col } from 'antd';
import TopHeader from '../header/header.jsx';
import { useNavigate } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay } from 'swiper/modules';
import 'swiper/css';
import "./Homepage.css";

import homepage1 from '@/assets/homepage1.png';
import homepage2 from '@/assets/homepage2.png';
import homepage3 from '@/assets/homepage3.png';

import california from '@/assets/california.png';
import texas from '@/assets/texas.png';
import alabama from '@/assets/alabama.png';
import kansas from '@/assets/kansas.png';
import florida from '@/assets/florida.png';
import arizona from '@/assets/arizona.png';
import missouri from '@/assets/missouri.png';
import wisconsin from '@/assets/wisconsin.png';
import illinois from '@/assets/illinois.png';
import virginia from '@/assets/virginia.png';
import oregon from '@/assets/oregon.png';

const { Content } = Layout;

const initialStates = [
  { name: 'California', count: null, img: california },
  { name: 'Texas', count: null, img: texas },
  { name: 'Alabama', count: null, img: alabama },
  { name: 'Kansas', count: null, img: kansas },
  { name: 'Florida', count: null, img: florida },
  { name: 'Arizona', count: null, img: arizona },
  { name: 'Missouri', count: null, img: missouri },
  { name: 'Wisconsin', count: null, img: wisconsin },
  { name: 'Illinois', count: null, img: illinois },
  { name: 'Virginia', count: null, img: virginia },
  { name: 'Oregon', count: null, img: oregon },
];

const slides = [
  { id: 1, img: homepage1, link: '/spot/1' },
  { id: 2, img: homepage2, link: '/spot/2' },
  { id: 3, img: homepage3, link: '/spot/3' },
];

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

const HomePage = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [states, setStates] = useState(initialStates);
  const navigate = useNavigate();

  const handleUserClick = () => {
    if (!isLoggedIn) {
      navigate('/login');
    }
  };

  const handleFavorite = () => {
    navigate('/user/:id/folder')
  }

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

    const handleSearch = (value) => {
    if (value.trim()) {
        navigate(`/search?state=${encodeURIComponent(value.trim())}`);
    }
    };

  useEffect(() => {
    const fetchCounts = async () => {
      const updated = await Promise.all(
        states.map(async (state) => {
          try {
            const res = await fetch(`http://localhost:8080/api/attractions/count?state=${encodeURIComponent(state.name)}`);
            const whole = await res.json();
            const data = whole.data; 
            return { ...state, count: data };
          } catch (err) {
            console.error(`Failed to fetch count for ${state.name}:`, err);
            return { ...state, count: 0 };
          }
        })
      );
      setStates(updated);
    };
    fetchCounts();
    const token = localStorage.getItem("sessionId");
    console.log(token);
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <Layout style={{ minHeight: '100vh', width: '100vw', overflowX: 'hidden', backgroundColor: '#f5f5f5' }}>
      <TopHeader
        isLoggedIn={isLoggedIn}
        handleLogout={handleLogout}
        handleFavorite={handleFavorite}
        handleUserClick={handleUserClick}
        UserMenu={UserMenu}
        handleSearch={handleSearch}
      />

      <Content style={{ padding: 0, margin: 0 }}>
        <div style={{ width: '100vw', height: 'calc(100vw * 0.25)', overflow: 'hidden' }}>
          <Swiper
            modules={[Autoplay]}
            autoplay={{ delay: 3000 }}
            loop={true}
            slidesPerView={1}
            spaceBetween={0}
            style={{ height: '100%', width: '100%' }}
          >
            {slides.map((slide) => (
              <SwiperSlide key={slide.id}>
                <img
                  src={slide.img}
                  alt={`slide-${slide.id}`}
                  style={{
                    width: '100%',
                    height: '100%',
                    objectFit: 'cover',
                    display: 'block',
                    cursor: 'pointer'
                  }}
                  onClick={() => navigate(slide.link)}
                />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>

        <div style={{ padding: '36px' }}>
          <Row gutter={[16, 16]}>
            {states.map((state, index) => (
              <Col xs={24} sm={12} md={6} key={index}>
                <Card
                  hoverable
                  cover={<img alt={state.name} src={state.img} style={{ height: 150, objectFit: 'cover' }} />}
                  onClick={() => navigate(`/search?query=${state.name}`)}
                >
                  <Card.Meta
                    title={<div style={{ display: 'flex', justifyContent: 'space-between' }}>
                      <span>{state.name}</span>
                      <span>{state.count !== null ? `${state.count} Spots` : 'Loading...'}</span>
                    </div>}
                  />
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      </Content>
    </Layout>
  );
};

export default HomePage;
