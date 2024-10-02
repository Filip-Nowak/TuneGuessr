import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App.jsx';
import './index.css';
import { SuccessPage } from './components/SuccessPage.jsx';
import { LoginPage } from './views/LoginPage.jsx';
import { RegisterPage } from './views/RegisterPage.jsx';
import { ErrorPage } from './views/ErrorPage.jsx';
import { SettingsPage } from './components/SettingsPage.jsx';

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        errorElement: <ErrorPage />,
        children: [
            {
                path: '/',
                element: <Play />,
            },
            {
                path: '/settings',
                element: <SettingsPage />,
            },
        ],
    },
	{
		path: '/login',
		element: <LoginPage />,
	},
	{
		path: '/register',
		element: <RegisterPage />,
	},
	{
		path: '/success',
		element: <SuccessPage />,
	},
    {
        path: "/test",
        element: <TestPage />,
        children: [
            {
                path: "/test/game",
                element: <GamePage />,
            },
            {
                path: "/test/pick",
                element: <PickPage />,
            },
            {
                path: "/test/room",
                element: <RoomPage />,
            },
        ],
    },
]);

ReactDOM.createRoot(document.getElementById('root')).render(<RouterProvider router={router} />);
