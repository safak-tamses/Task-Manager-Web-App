
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './component/LoginForm';
import Task from './component/Task';
function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/home" element={<Task />} />
        <Route path="/" element={<LoginForm />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
