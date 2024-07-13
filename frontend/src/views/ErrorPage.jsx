import { Link } from 'react-router-dom';

export function ErrorPage() {
	return (
		<div className='flex flex-col justify-center items-center h-screen text-white bg-purple-700'>
			<h1 className='text-[156px]'>404</h1>
			<h2 className='text-3xl'>Oops! Page not found</h2>
			<p className='mt-5 text-xl'>The page you are lookin probably doesn't exist</p>
			<p className=' text-xl'>Come back to Home Page</p>
			<Link
				to='/'
				className='px-8 py-4 mt-4 w-48 text-xl text-center bg-white text-purple-700 rounded-lg tranistion-color duration-300 hover:bg-slate-200'
			>
				Come Back
			</Link>
		</div>
	);
}
