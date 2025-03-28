import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RoutineListPage from "./pages/RoutineListPage.jsx";
import RoutineCreatePage from "./pages/RoutineCreatePage";
import MyPage from "./pages/MyPage";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<RoutineListPage />} />
        <Route path="/create" element={<RoutineCreatePage />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;
