
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginRegister from './Component/LoginRegister';
import TaskManager from './Component/TaskManager';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/home" element={<TaskManager />} />
        <Route path="/" element={<LoginRegister />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
