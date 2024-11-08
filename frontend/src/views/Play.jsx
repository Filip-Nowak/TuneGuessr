import { useEffect } from 'react';
import { Challange } from '../components/Challange';
import { SearchBar } from '../components/SearchBar';
import { BACKEND_URL } from '../constants/API_END-POINT';
import { getUserToken } from '../utils/getUserToken';

export function Play() {
	// TO-DO create a useEffect that returns challenges to the game
	const token = getUserToken();

	useEffect(() => {
		const fetchChallenge = async () => {
			try {
				const response = await fetch(`${BACKEND_URL}/challenge`, {
					method: 'GET',
					mode: 'no-cors',
					headers: {
						'Content-Type': 'application/json',
						Authorization: `Bearer ${token}`,
					},
				});

				if (!response.ok) {
					throw new Error(`Error: ${response.status} ${response.statusText}`);
				}

				const data = await response.json();
				console.log(data);
			} catch (error) {
				console.log('Fetch challenge error:', error);
			}
		};

		fetchChallenge();
	}, []);

	return (
		<div className='min-h-screen'>
			<SearchBar />

			<div className='grid grid-cols-1 md:overflow-y-scroll max-h-[90svh] md:grid-cols-2 xl:grid-cols-4 gap-5 px-8 py-10'>
				<Challange challangeId={'asoldjf902304'} />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
				<Challange />
			</div>
		</div>
	);
}
