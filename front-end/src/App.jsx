import { useState, useEffect } from "react";
import "./App.css";
import AppRouter from "./Router";

function App() {
  const [user, setUser] = useState();

  // get logged-in user data from localstorage
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) setUser(user);
  }, []);

  return <AppRouter user={user} setUser={setUser} />;
}

export default App;
