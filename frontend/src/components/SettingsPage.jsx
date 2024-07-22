import { useNavigate } from 'react-router-dom';

export function SettingsPage() {
	const navigate = useNavigate();

	const handleButtonClick = () => {
		localStorage.removeItem('token');
		navigate('/login');
	};
	return (
		<button
			onClick={handleButtonClick}
			className='px-6 py-2 self-start rounded-xl bg-black text-white'
		>
			Logout
		</button>
	);
}
