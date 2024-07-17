import { Link, redirect } from 'react-router-dom';
import BURGER from '../assets/menu.svg';
import CLOSE from '../assets/close-outline.svg';
import { useState } from 'react';

export function MobileNav() {
	const [isShown, setIsShown] = useState(false);

	const handleCloseNav = e => {
		const test = e.target.textContent;
		console.log(test.trim());
		// window.location = `/${test.toLowerCase()}`;
		setIsShown(false);
	};

	return (
		<nav className='fixed md:hidden'>
			<button onClick={() => setIsShown(true)} className='w-32 p-6'>
				<img src={BURGER} alt='menu button' />
			</button>

			<ul
				className={`absolute top-0 left-0 flex flex-col justify-center items-center gap-16 h-screen w-screen text-2xl text-white bg-purple-700 transition-transform duration-300 ${
					isShown ? 'translate-x-0' : '-translate-x-full'
				}`}
			>
				<img
					onClick={() => setIsShown(false)}
					className='absolute top-0 left-0 w-32 p-6'
					src={CLOSE}
					alt='close navigation'
				/>
				<li>
					<Link onClick={handleCloseNav} className='p-4'>
						Play
					</Link>
				</li>
				<li>
					<Link onClick={handleCloseNav} className='p-4'>
						Custom Playslist
					</Link>
				</li>
				<li>
					<Link onClick={handleCloseNav} className='p-4'>
						FAQ
					</Link>
				</li>
				<li>
					<Link onClick={handleCloseNav} className='p-4'>
						Settings
					</Link>
				</li>
			</ul>
		</nav>
	);
}
