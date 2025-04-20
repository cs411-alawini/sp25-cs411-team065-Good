import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Search from './pages/search/Search.jsx'; // 引入搜索页面
import HomePage from './pages/homepage/Homepage.jsx';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/homepage" element={<HomePage />} />
        {/* url: /search?query=xxx */}
        <Route path="/search" element={<Search />} />
      </Routes>
    </Router>
  );
}

export default App;
