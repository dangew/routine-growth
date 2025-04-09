// components/RegisterForm.jsx

import { useState } from "react";
import axios from "axios";

function RegisterForm() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    passwordCheck: "",
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    const newErrors = {};

    // 이메일 유효성 검사
    if (!formData.email) {
      newErrors.email = "이메일을 입력하세요.";
    } else if (!/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(formData.email)) {
      newErrors.email = "올바른 이메일 형식이 아닙니다.";
    }

    // 비밀번호 유효성 검사
    if (!formData.password) {
      newErrors.password = "비밀번호를 입력하세요.";
    } else if (formData.password.length < 8) {
      newErrors.password = "비밀번호는 8자 이상이어야 합니다.";
    }

    // 비밀번호 확인 유효성 검사
    if (!formData.passwordCheck) {
      newErrors.passwwordCheck = "비밀번호 확인을 입력하세요.";
    } else if (formData.password !== formData.passwordCheck) {
      newErrors.passwordCheck = "비밀번호가 일치하지 않습니다.";
    }

    setErrors(newErrors);
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const validationErrors = validateForm();

    if (Object.keys(validationErrors).length > 0) {
      console.error("유효성 검사 실패:", validationErrors);
      alert("유효성 검사 실패");
      Object.keys(validationErrors).forEach((key) => {
        alert(validationErrors[key]);
      });
      return;
    }

    console.log("유효성 검사 통과:", formData);
    // 여기서 백엔드 API에 POST 요청을 보내는 로직을 추가할 수 있습니다.

    // 예시: axios.post("http://localhost:8080/api/users", formData)
    //   .then((response) => {
    //     console.log("회원가입 성공:", response.data);
    //   })
    //   .catch((error) => {
    //     console.error("회원가입 실패:", error);
    //   });

    axios
      .post("http://localhost:8080/api/user/register", formData)
      .then((response) => {
        console.log("회원가입 성공:", response.data);
        alert("회원가입 성공");
      })
      .catch((error) => {
        console.error("회원가입 실패:", error);
        alert("회원가입 실패");
      });
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="max-w-md mx-auto mt-10 p-6 bg-white rounded-2xl shadow"
    >
      <h2 className="text-2xl font-bold mb-6">회원가입</h2>

      <label className="block mb-2">
        <span className="text-gray-700">이메일</span>
        <input
          type="text"
          name="email"
          value={formData.email}
          onChange={handleChange}
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
          required
        />
      </label>
      <label className="block mb-2">
        <span className="text-gray-700">비밀번호</span>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
          required
        />
      </label>

      <label className="block mb-2">
        <span className="text-gray-700">비밀번호 확인</span>
        <input
          type="password"
          name="passwordCheck"
          value={formData.passwordCheck}
          onChange={handleChange}
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
          required
        />
      </label>

      <button
        type="submit"
        className="w-full py-2 px-4 bg-blue-600 text-white rounded-md hover:bg-blue-700"
      >
        가입하기
      </button>
    </form>
  );
}

export default RegisterForm;
