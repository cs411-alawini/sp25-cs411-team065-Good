import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Search from './pages/search/Search.jsx'; // 引入搜索页面

function App() {
  return (
    <Router>
      <Routes>
        {/* url: /search?query=xxx */}
        <Route path="/search" element={<Search />} />
      </Routes>
    </Router>
  );
}

export default App;
