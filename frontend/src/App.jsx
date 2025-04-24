import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Search from './pages/search/Search.jsx';
import HomePage from './pages/homepage/Homepage.jsx';
import Attractions from './pages/attractions/Attractions.jsx';
import Login from './pages/login/login.jsx';
import Signup from './pages/signup/Signup.jsx';
import FolderPage from './pages/folder/folder.jsx';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/homepage" element={<HomePage />} />
        {/* url: /search?query=xxx */}
        <Route path="/search" element={<Search />} />
        <Route path="/attraction/:id" element={<Attractions />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/user/:id/folder" element={< FolderPage/>} />
        <Route path="/user/:id/folder/:folderId" element={< FolderPage/>} />
      </Routes>
    </Router>
  );
}

export default App;
