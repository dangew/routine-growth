import { useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function LoginPage({ setUser }) {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    if (!email) return alert("이메일을 입력하세요!");
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        { email, password },
        { withCredentials: true }
      );
      console.log("response : ", JSON.stringify(response));

      const user = response.data;
      localStorage.setItem("user", JSON.stringify(user));

      console.log("user : ", user);
      setUser(user);
      alert("로그인 성공");

      navigate("/mypage");
    } catch (err) {
      alert("로그인 실패");
      console.error(err);
    }
  };

  return (
    <div className="container mt-5">
      <h2>이메일로 로그인</h2>
      <input
        type="email"
        placeholder="example@email.com"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        className="form-control my-3"
      />
      <input
        type="password"
        placeholder="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="form-control my-3"
      />
      <button className="btn btn-primary" onClick={handleLogin}>
        로그인
      </button>
      <a href=""></a>
    </div>
  );
}

export default LoginPage;
