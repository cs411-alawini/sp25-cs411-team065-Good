import React from 'react';
import { Layout, Input, Dropdown, Avatar } from 'antd';
import { SearchOutlined, UserOutlined } from '@ant-design/icons';
import logo from '@/assets/logo.png';
const { Header } = Layout;
const { Search } = Input;



const TopHeader = ({ isLoggedIn, handleLogout, handleUserClick, UserMenu, handleSearch}) => {
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
          <Dropdown overlay={<UserMenu onLogout={handleLogout} />} placement="bottomRight">
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
