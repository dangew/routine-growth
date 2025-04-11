const Header = ({ isLoggedIn, onLogout }) => {
  return (
    <header style={styles.header}>
      <h1 style={styles.title}>My App</h1>
      {isLoggedIn ? (
        <button style={styles.button} onClick={onLogout}>
          Logout
        </button>
      ) : (
        <a href="/login">Login</a>
      )}
      <a href="/register">Register</a>
    </header>
  );
};

const styles = {
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "10px 20px",
    backgroundColor: "#f4f4f4",
    borderBottom: "1px solid #ddd",
  },
  title: {
    margin: 0,
    fontSize: "1.5rem",
  },
  button: {
    padding: "8px 16px",
    fontSize: "1rem",
    cursor: "pointer",
    border: "1px solid #ccc",
    borderRadius: "4px",
    backgroundColor: "#fff",
  },
};

export default Header;
