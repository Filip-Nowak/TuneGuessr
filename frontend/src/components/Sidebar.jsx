import { Link } from 'react-router-dom';

import { FaPlay } from 'react-icons/fa';
import { FaFlask } from 'react-icons/fa6';
import { IoFlag, IoSettingsSharp } from 'react-icons/io5';
import { NavigationLinks } from '../constants/NavigationLinks';

export function Sidebar() {
	return (
		<nav className='hidden md:block fixed top-1/2 left-36 h-[95%] w-full max-w-64 bg-purple-700 rounded-xl -translate-x-1/2 -translate-y-1/2'>
			<ul className='flex flex-col justify-center gap-8 px-6 w-full h-full text-white'>
				<li>
					<Link
						to={NavigationLinks.play}
						className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'
					>
						<FaPlay className='text-2xl text-black' />
						Play
					</Link>
				</li>
				<li>
					<Link
						to={NavigationLinks.custonPlaylist}
						className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'
					>
						<FaFlask className='text-2xl text-black' />
						Custom Playslist
					</Link>
				</li>
				<li>
					<Link
						to={NavigationLinks.faq}
						className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'
					>
						<IoFlag className='text-2xl text-black' />
						FAQ
					</Link>
				</li>
				<li>
					<Link
						to={NavigationLinks.settings}
						className='flex items-center gap-5 p-4 w-full rounded-xl transition-colors duration-300 hover:bg-purple-800'
					>
						<IoSettingsSharp className='text-2xl text-black' />
						Settings
					</Link>
				</li>
			</ul>
		</nav>
	);
}
