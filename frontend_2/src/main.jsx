import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App.jsx";
import "./index.css";
import HomePage from "./pages/homePage/HomePage.jsx";
import SiteContainer from "./siteElements/siteContainer/SiteContainer.jsx";
import ChallengeViewPage from "./pages/challengeView/ChallengeViewPage.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <SiteContainer />,
        children: [
          {
            path: "/",
            element: <HomePage />,
          },
          {
            path: "/challenge/:id",
            element: <ChallengeViewPage />,
          },
        ],
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <RouterProvider router={router} />
);
