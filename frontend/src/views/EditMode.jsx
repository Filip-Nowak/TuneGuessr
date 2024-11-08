import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { BACKEND_URL } from '../constants/API_END-POINT';
import { getUserToken } from '../utils/getUserToken';
import { useForm } from 'react-hook-form';

export function EditMode() {
	const [isAddNewSong, setIsAddNewSong] = useState(false);

	const { challengeId } = useParams();
	const navigate = useNavigate();
	const token = getUserToken();
	const {
		register,
		handleSubmit,
		reset,
		formState: { errors },
	} = useForm();

	const ytRegex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com|youtu\.be)\/[^\s]+/;
	const [challengeSongsList, setChallengeSongsList] = useState([]);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await fetch(`${BACKEND_URL}/challenge/${challengeId}`);

				const { data } = await response.json();

				setChallengeSongsList(data.songs);
			} catch (error) {
				console.log('Error fetching data:', error);
			}
		};

		fetchData();
	}, []);

	const handleDeleteSong = async () => {
		try {
			const response = await fetch(`${BACKEND_URL}/challenge/${challengeId}`, {
				method: 'DELETE',
				headers: {
					Authorization: `Bearer ${token}`,
				},
			});

			if (!response.ok) {
				console.log('DELETE challenge error');
				return;
			}

			console.log('DELETE challenge successful!');
			navigate('/custom-playlist', { replace: true });
		} catch (error) {
			console.log('DELETE challenge error:', error);
		}
	};

	const onSubmit = e => {
		try {
			const { title, url } = e;
			console.log(title);
			console.log(url);

			fetch(`${BACKEND_URL}/challenge/${challengeId}/song`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`,
				},
				body: JSON.stringify({
					title,
					artist: 'test',
					url,
				}),
			});

			reset();
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<>
			<div className='flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12'>
				<div className='text-center space-y-4 lg:text-left'>
					<h1 className='text-6xl font-bold'>Challange Name</h1>
				</div>

				<button
					onClick={handleDeleteSong}
					className='px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer'
				>
					Delete
				</button>
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
						<form onSubmit={handleSubmit(onSubmit)} className='my-5 space-x-3'>
							<label htmlFor='title'>Title</label>
							<input
								className='p-1 border'
								type='text'
								id='title'
								{...register('title', {
									required: 'The entered name must not be empty',
								})}
							/>
							<label htmlFor='url'>URL</label>
							<input
								className='p-1 border'
								type='text'
								id='url'
								{...register('url', {
									required: 'The entered name must not be empty',
									validate: link =>
										ytRegex.test(link) ||
										'The link must be a valid YouTube URL',
								})}
							/>
							<button className='px-4 py-2 rounded-xl bg-black text-white'>
								Add Song
							</button>
						</form>
					)}
					{errors.text && <p className='text-md text-red-600'>{errors.text.message}</p>}
					{errors.url && <p className='text-md text-red-600'>{errors.url.message}</p>}

					<ul className='mt-8 space-y-6'>
						{challengeSongsList.map(({ title, url }) => (
							<li key={url} className='flex justify-between gap-5 max-w-[500px]'>
								<div>
									<h3 className='font-bold'>{title}</h3>
									<Link target='_blank' to={url} className='text-blue-600'>
										{url}
									</Link>
								</div>
								<button
									onClick={() =>
										setChallengeSongsList(prevList =>
											prevList.filter(song => song.url !== url)
										)
									}
									className='px-4 py-2 rounded-xl bg-black text-white'
								>
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
