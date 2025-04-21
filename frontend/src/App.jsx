import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Search from './pages/search/Search.jsx';
import HomePage from './pages/homepage/Homepage.jsx';
import Attractions from './pages/attractions/Attractions.jsx';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/homepage" element={<HomePage />} />
        {/* url: /search?query=xxx */}
        <Route path="/search" element={<Search />} />
        <Route path="/attraction/:id" element={<Attractions />} />
      </Routes>
    </Router>
  );
}

export default App;
