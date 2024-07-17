import { Outlet } from 'react-router-dom';
import { Sidebar } from './components/Sidebar';
import { MobileNav } from './components/MobileNav';

function App() {
	return (
		<div className='flex'>
			<MobileNav />
			<Sidebar />
			<Outlet />
			<div className='h-screen bg-white'></div>
			<div className='h-screen bg-orange-400'></div>
		</div>
	);
}

export default App;
