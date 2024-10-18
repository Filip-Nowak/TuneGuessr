import { Challange } from '../components/Challange';
import { SearchBar } from '../components/SearchBar';

export function Play() {
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
