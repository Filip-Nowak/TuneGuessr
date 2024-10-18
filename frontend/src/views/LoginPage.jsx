import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { BACKEND_URL } from '../constants/API_END-POINT';
import { useState } from 'react';

export function LoginPage() {
	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm();
	const [errorMessage, setErrorMessage] = useState('');
	const navigate = useNavigate();

	const onSubmit = async ({ email, password }) => {
		try {
			const response = await fetch(`${BACKEND_URL}/auth/authenticate`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					password,
					email,
				}),
			});
			// const { token } = await response.json();
			const data = await response.json();

			if (data.status % 10 !== 0) {
				throw new Error('Network response was not ok ' + data.message);
			}

			console.log(data);

			localStorage.setItem('token', data.token);
			navigate('/');
		} catch (error) {
			setErrorMessage('Your password or email is wrong');
			console.log('Fetch error', error);
		}
	};

	// const onSubmit = data => console.log(data);

	return (
		<div className='flex justify-center items-center h-screen max-h-[800px] md:p-8 md:border rounded-xl border-purple-900 m-10'>
			<div className='px-8 w-full md:w-1/2'>
				<h1 className='text-6xl text-center md:text-start md:text-7xl text-purple-900'>
					Login
				</h1>
				<form
					onSubmit={handleSubmit(onSubmit)}
					className='flex flex-col gap-6 mt-8 md:pl-4'
				>
					<div className='relative z-0 w-full mb-5 group'>
						<input
							type='email'
							id='email'
							className='block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:border-purple-700 peer'
							placeholder=' '
							{...register('email', {
								required: 'Type your email',
								validate: email =>
									/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email) ||
									'Type corect email adress',
							})}
						/>
						<label
							htmlFor='email'
							className='peer-focus:font-medium absolute text-sm text-stone-400 duration-300 transform -translate-y-8 scale-70 top-4 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-purple-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-70 peer-focus:-translate-y-9'
						>
							Email address
						</label>
						{errors.email && (
							<span className='text-md text-red-600'>{errors.email.message}</span>
						)}
						{errorMessage && (
							<span className='text-md text-red-600'>{errorMessage}</span>
						)}
					</div>

					<div className='relative z-0 w-full mb-5 group'>
						<input
							type='password'
							id='password'
							className='block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-400 appearance-none focus:outline-none focus:border-purple-700 peer'
							placeholder=' '
							{...register('password', {
								required: { value: true, message: 'Type password' },
								minLength: { value: 6, message: 'Your password is too short' },
								maxLength: { value: 40, message: 'Your password is too long' },
							})}
						/>
						<label
							htmlFor='password'
							className='peer-focus:font-medium absolute text-sm text-stone-400 duration-300 transform -translate-y-8 scale-70 top-4 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-purple-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-70 peer-focus:-translate-y-9'
						>
							Password
						</label>
						{errors.password && (
							<span className='text-md text-red-600'>{errors.password.message}</span>
						)}
						{errorMessage && (
							<span className='text-md text-red-600'>{errorMessage}</span>
						)}
					</div>
					<button className='mx-auto my-2 px-4 py-2 w-[80%] bg-black rounded-lg text-white'>
						Login
					</button>
				</form>
				<p className='mt-4 text-center md:text-start'>
					Don't have an account?{' '}
					<Link className='font-bold text-purple-500' to={'/register'}>
						Craete Account
					</Link>
				</p>
			</div>
			<div className='relative hidden w-1/2 md:block'>
				<img
					src='https://img.freepik.com/free-vector/access-control-system-abstract-concept_335657-3180.jpg?t=st=1720620274~exp=1720623874~hmac=0d0624eaac408896346dc0bda43d69c6aecae2ae21f7d57d58fcfd13cb87176d&w=740'
					alt=''
				/>
			</div>
		</div>
	);
}
