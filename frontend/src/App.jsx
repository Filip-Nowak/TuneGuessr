import { Outlet } from 'react-router-dom';
import { Sidebar } from './components/Sidebar';
import { MobileNav } from './components/MobileNav';

function App() {
	return (
		<div>
			<MobileNav />
			<Sidebar />
			<div className=' pl-72'>
				<Outlet />
			</div>
			<div className='h-screen bg-white'></div>
			<div className='h-screen bg-orange-400'></div>
		</div>
	);
}

export default App;
