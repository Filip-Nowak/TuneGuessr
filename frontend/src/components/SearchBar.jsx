import { IoIosSearch } from 'react-icons/io';
import { Button } from './Button';
import { useState } from 'react';

export function SearchBar() {
	const [searchValue, setSearchValue] = useState('');
	return (
		<div className='flex justify-center items-center gap-3 pt-36 px-4 md:justify-start md:pt-10'>
			<input
				onChange={e => setSearchValue(e.target.value)}
				className='px-4 py-3 w-full min-w-[200px] max-w-[500px] rounded-lg focus:outline-none'
				type='text'
				placeholder='Search your challange...'
			/>
			<button onClick={() => alert(searchValue)} className='p-4 rounded-lg bg-purple-400'>
				<IoIosSearch className='text-white' />
			</button>
		</div>
	);
}
