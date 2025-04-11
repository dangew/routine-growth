import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";
import LoginPage from "./pages/LoginPage";
import RoutineListPage from "./pages/RoutineListPage.jsx";
import RoutineCreatePage from "./pages/RoutineCreatePage";
import MyPage from "./pages/MyPage";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import RegisterForm from "./components/RegisterForm.jsx";
import Header from "./components/Header.jsx";
import api from "./api/axios";

function AppRouter({ user, setUser }) {
  const [isLoggedIn, setIsLoggedIn] = useState(user ? true : false);

  const handleLogout = async () => {
    async function logout() {
      try {
        const response = await api.get("/api/auth/logout");
        console.log("logotu response : ", response);
        alert("로그아웃 성공");
      } catch (err) {
        console.error(err);
        alert("로그아웃 실패");
      }
    }
    await logout();
    setIsLoggedIn(false);
    location.replace("/");
  };

  return (
    <BrowserRouter>
      <Header isLoggedIn={isLoggedIn} onLogout={handleLogout} />
      <Routes>
        <Route path="/register" element={<RegisterForm />} />
        <Route
          path="/login"
          element={
            <LoginPage setUser={setUser} setIsLoggedIn={setIsLoggedIn} />
          }
        />
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
