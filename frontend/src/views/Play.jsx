import { Challange } from '../components/Challange';
import { SearchBar } from '../components/SearchBar';

export function Play() {
	return (
		<div className='min-h-screen'>
			<SearchBar />

			<div className='grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-5 px-8 py-10'>
				<Challange />
				<Challange />
				<Challange />
				<Challange />
			</div>
		</div>
	);
}
