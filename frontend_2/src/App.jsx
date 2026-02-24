import { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import { UserDataContext } from "./utils/contexts";
import { getHeaderUserData } from "./utils/http/userData";
import { apiClient } from "./infrastructure/api/ApiClient";
import { webSocketClient } from "./infrastructure/ws/WebSocketClient";
import { userService } from "./domain/UserService";

function App() {
  const [userData, setUserData] = useState(null);
  const [refreshed, setRefreshed] = useState(true);
  useEffect(() => {
    userService.connect();
  }, []);
  useEffect(() => {
    checkUserData();
  }, []);
  useEffect(() => {
    if (refreshed === false) {
      setRefreshed(true);
      checkUserData();
    }
  }, [userData, refreshed]);
  const checkUserData = async () => {
    const response = await getHeaderUserData();
    console.log(response);
    if (response === null) {
      console.log("not logged in");
      setUserData(null);
    } else {
      console.log("logged in");
      setUserData(response.data);
    }
  };
  return (
    <div>
      <UserDataContext.Provider
        value={{ userData, setUserData, refreshed, setRefreshed }}
      >
        <Outlet />
      </UserDataContext.Provider>
    </div>
  );
}

export default App;
