import React from "react";

export const UserDataContext = React.createContext({
    userData: null,
    setUserData: () => {},
    refreshed: true,
    setRefreshed: () => {},
});