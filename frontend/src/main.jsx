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
]);

ReactDOM.createRoot(document.getElementById('root')).render(<RouterProvider router={router} />);
