import { useState, useEffect } from "react";
import "./App.css";
import AppRouter from "./Router";
import api from "./api/axios";

function App() {
  const [user, setUser] = useState();
  const [loading, setLoading] = useState(true); // 로딩 제어 state

  useEffect(() => {
    async function getUser() {
      try {
        const response = await api.get("/api/auth");
        setUser(response.data);
      } catch (err) {
        console.log("유저 정보 확인 에러", err);
      } finally {
        setLoading(false);
      }
    }

    getUser();
  }, []);

  if (loading) return <div>로딩 중...</div>;

  return <AppRouter user={user} setUser={setUser} />;
}

export default App;
