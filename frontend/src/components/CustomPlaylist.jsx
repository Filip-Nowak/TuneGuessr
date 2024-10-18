import { useState } from 'react';
import { Challange } from './Challange';
import { createPortal } from 'react-dom';
import { Modal } from './Modal';

const customPlaylist = [
	{
		name: 'Title',
		description:
			'Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam repellat voluptatibus numquam nesciunt rem corporis voluptate explicabo. Rem facere fugit nostrum! Accusamus explicabo quas, nam hic in vel numquam cumque.',
	},
	{
		name: 'Title',
		description:
			'Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam repellat voluptatibus numquam nesciunt rem corporis voluptate explicabo. Rem facere fugit nostrum! Accusamus explicabo quas, nam hic in vel numquam cumque.',
	},
	{
		name: 'Title',
		description:
			'Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam repellat voluptatibus numquam nesciunt rem corporis voluptate explicabo. Rem facere fugit nostrum! Accusamus explicabo quas, nam hic in vel numquam cumque.',
	},
	{
		name: 'Title',
		description:
			'Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam repellat voluptatibus numquam nesciunt rem corporis voluptate explicabo. Rem facere fugit nostrum! Accusamus explicabo quas, nam hic in vel numquam cumque.',
	},
];

export function CustomPlaylist() {
	const [isModalShown, setIsModalShown] = useState(false);
	const modal = createPortal(<Modal onClose={() => setIsModalShown(false)} />, document.body);

	return (
		<>
			<div className='flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12'>
				<div className='text-center lg:text-left'>
					<h1 className='text-6xl font-bold'>Your custom playlist</h1>
				</div>

				{!isModalShown ? (
					<button
						onClick={() => setIsModalShown(true)}
						className='px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer'
					>
						Add new challange
					</button>
				) : (
					<div className='py-6 text-white'>_</div>
				)}
			</div>

			<div>
				{customPlaylist.length > 0 ? (
					<div className='grid grid-cols-1 mt-10 md:overflow-y-scroll max-h-[90svh] md:grid-cols-2 xl:grid-cols-4 gap-5 px-8 py-10'>
						{customPlaylist.map(({ name, description }) => (
							<Challange key={name} name={name} description={description} />
						))}
					</div>
				) : (
					<div className='mt-20'>
						<p className='text-4xl text-center'>Your custom playlist is empyt</p>
					</div>
				)}
			</div>

			{isModalShown && modal}
		</>
	);
}
