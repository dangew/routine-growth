import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RoutineListPage from "./pages/RoutineListPage.jsx";
import RoutineCreatePage from "./pages/RoutineCreatePage";
import MyPage from "./pages/MyPage";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import RegisterForm from "./components/RegisterForm.jsx";

function AppRouter({ user, setUser }) {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/login" element={<LoginPage setUser={setUser} />} />
        <Route
          path="/"
          element={
            <ProtectedRoute user={user} setUSer={setUser}>
              <RoutineListPage setUser={setUser} />
            </ProtectedRoute>
          }
        />
        <Route
          path="/create"
          element={
            <ProtectedRoute user={user} setUSer={setUser}>
              <RoutineCreatePage setUser={setUser} />
            </ProtectedRoute>
          }
        />
        <Route
          path="/mypage"
          element={
            <ProtectedRoute user={user} setUSer={setUser}>
              <MyPage setUser={setUser} />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;
