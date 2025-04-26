import React, { useState, useEffect } from 'react';
import { Layout, Input, Dropdown, Avatar } from 'antd';
import { SearchOutlined, UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import logo from '@/assets/logo.png';
const { Header } = Layout;
const { Search } = Input;

const userMenuItems = [
  {
    key: 'favorite',
    label: 'Favorite'
  },
  {
    key: 'logout',
    label: 'Logout',
  }
];

const TopHeader = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  
  useEffect(() => {
    const token = localStorage.getItem('sessionId');
    setIsLoggedIn(!!token);
    console.log(token);
  }, []);

  const navigate = useNavigate();

  const handleUserClick = () => {
      if (!isLoggedIn) {
        navigate('/login');
      }
  };
  
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    setIsLoggedIn(false);
    navigate('/homepage');
  };
  
  const handleFavorite = () => {
    const userId = localStorage.getItem('userId');
    if (userId) {
      navigate(`/user/${userId}/folder`);
    } else {
      navigate('/login');
    }
  };
  

  const handleSearch = (value) => {
  if (value.trim()) {
      navigate(`/search?state=${encodeURIComponent(value.trim())}`);
  }
  };

  const handleMenuClick = ({ key }) => {
    if (key === 'logout') {
      handleLogout();
    }
    else if (key == 'favorite') {
      handleFavorite();
    }
  };

  return (
    <Header
      style={{
        display: 'flex',
        alignItems: 'center',
        backgroundColor: '#fff',
        padding: '20px 32px',
        gap: '30px',
      }}
    >
      <div style={{ display: 'flex', alignItems: 'center', flexShrink: 0 }}>
        <img src={logo} alt="logo" className="logo-img" />
        <span className="title">Travel Mate</span>
      </div>

      <div style={{ flex: 1, display: 'flex', justifyContent: 'center' }}>
        <Search
          placeholder="search a state to begin your journey"
          enterButton={<SearchOutlined />}
          onSearch={handleSearch}
          style={{ width: '60%' }}
        />
      </div>

      <div style={{ flexShrink: 0 }}>
      {isLoggedIn ? (
        <Dropdown
          menu={{
            items: userMenuItems,
            onClick: handleMenuClick,
          }}
          placement="bottomRight"
        >
          <Avatar icon={<UserOutlined />} style={{ cursor: 'pointer' }} />
        </Dropdown>
      ) : (
        <Avatar icon={<UserOutlined />} onClick={handleUserClick} style={{ cursor: 'pointer' }} />
      )}
    </div>
    </Header>
  );
};

export default TopHeader;
