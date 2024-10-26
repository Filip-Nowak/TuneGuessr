import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';

export function EditMode() {
	const [isAddNewSong, setIsAddNewSong] = useState(false);

	const { challangeId } = useParams();

	const ytRegex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com|youtu\.be)\/[^\s]+/;
	const challangeSongsList = [
		{ title: 'title', link: 'https://youtube.com/muzyka$2918234' },
		{ title: 'title', link: 'https://youtube.com/muzyka$29182342233' },
		{ title: 'title', link: 'https://www.youtube.com/watch?v=10swJdKcHNQ' },
		{ title: 'title', link: 'https://www.youtube.com/watch?v=KGFAVVAu5rg' },
	];

	return (
		<>
			<div className='flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12'>
				<div className='text-center space-y-4 lg:text-left'>
					<h1 className='text-6xl font-bold'>Challange Name</h1>
				</div>

				<Link
					to={`https://localhost:5137/${challangeId}`}
					className='px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer'
				>
					Delete
				</Link>
			</div>

			<div className='grid grid-cols-4 mt-10'>
				<div className='col-start-1 col-end-3 pl-12'>
					<button
						onClick={() => setIsAddNewSong(prevState => !prevState)}
						className='px-4 py-2 rounded-xl bg-black text-white hover:cursor-pointer'
					>
						+ Add song
					</button>

					{isAddNewSong && (
						<form className='my-5 space-x-3'>
							<label htmlFor='title'>Title</label>
							<input className='p-1 border' type='text' id='title' />
							<label htmlFor='link'>Link</label>
							<input className='p-1 border' type='text' id='link' />
							<button className='px-4 py-2 rounded-xl bg-black text-white'>
								Add Song
							</button>
						</form>
					)}

					<ul className='mt-8 space-y-6'>
						{challangeSongsList.map(({ title, link }) => (
							<li key={link} className='flex justify-between gap-5 max-w-[500px]'>
								<div>
									<h3 className='font-bold'>{title}</h3>
									<Link to={link} className='text-blue-600'>
										{link}
									</Link>
								</div>
								<button className='px-4 py-2 rounded-xl bg-black text-white'>
									remove
								</button>
							</li>
						))}
					</ul>
				</div>
				<p className=' col-start-3 col-end-5 px-4 py-12 lg:px-12 max-w-prose'>
					Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident assumenda
					tempora asperiores? Tempora enim dolorum commodi! Doloribus odit quia quos
					suscipit modi in soluta incidunt obcaecati voluptate sed possimus quae nam error
					cumque dolorem quo laborum delectus cum, dolores nobis cupiditate quidem fuga
					officiis? Iusto molestiae voluptas aut ad quos provident, facere dicta
					necessitatibus ut, doloremque ex fugit modi veritatis odit eaque, rerum illum.
				</p>
			</div>
		</>
	);
}
