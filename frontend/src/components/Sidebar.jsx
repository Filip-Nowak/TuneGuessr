import { Link } from 'react-router-dom';
import FLAG from '../assets/flag.svg';
import FLASK from '../assets/flask.svg';
import PLAY from '../assets/play.svg';
import SETTINGS from '../assets/settings.svg';

export function Sidebar() {
	return (
		<nav className='hidden md:block fixed top-1/2 left-36 h-[95%] w-full max-w-64 bg-purple-700 rounded-xl -translate-x-1/2 -translate-y-1/2'>
			<ul className='flex flex-col justify-center gap-8 px-6 w-full h-full'>
				<li>
					<Link className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'>
						<img className='w-8' src={PLAY} alt='Play button' />
						Play
					</Link>
				</li>
				<li>
					<Link className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'>
						<img className='w-8' src={FLASK} alt='Custom challange button' />
						Custom Playslist
					</Link>
				</li>
				<li>
					<Link className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'>
						<img className='w-8' src={FLAG} alt='FAQ button' />
						FAQ
					</Link>
				</li>
				<li>
					<Link
						to={'/settings'}
						className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'
					>
						<img className='w-8' src={SETTINGS} alt='Settings button' />
						Settings
					</Link>
				</li>
			</ul>
		</nav>
	);
}
