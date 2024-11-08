import { useForm } from 'react-hook-form';
import CLOSE from '../assets/close-outline.svg';
import { BACKEND_URL } from '../constants/API_END-POINT';
import { getUserToken } from '../utils/getUserToken';

export function Modal({ onClose }) {
	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm();

	const token = getUserToken();

	const onSubmit = async ({ name, description }) => {
		try {
			const response = await fetch(`${BACKEND_URL}/challenge`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`,
				},
				body: JSON.stringify({
					name,
					description,
				}),
			});

			if (!response.ok) {
				throw new Error(`HTTP error! status: ${response.status}`);
			}

			const data = await response.json();

			if (data.errors) {
				console.log(data.errors);
			}

			console.log('Server response:', data.data);

			onClose();

			window.location.assign(`https://localhost:5173/challenge/edit-mode/${data.data.id}`);
		} catch (error) {
			throw new Error('Submit Error:', error);
		}
	};

	return (
		<>
			<div className='fixed bg-black inset-0 h-screen opacity-50'></div>
			<div className='fixed flex flex-col top-1/2 left-1/2 p-4 max-w-[800px] w-full h-[700px] bg-purple-600 -translate-x-1/2 -translate-y-1/2 md:rounded-lg'>
				<div className='flex justify-between items-center'>
					<h1 className='text-2xl uppercase font-bold'>Add new challange</h1>
					<button className='self-end' onClick={onClose}>
						<img className='w-16' src={CLOSE} alt='close modal button' />
					</button>
				</div>
				<form
					className='flex flex-col justify-between h-full'
					onSubmit={handleSubmit(onSubmit)}
				>
					<div className='flex flex-col gap-2'>
						<label htmlFor='name'>Name</label>
						<input
							className='p-2 rounded-lg'
							type='text'
							{...register('name', {
								required: 'The entered name must not be empty',
								minLength: {
									value: 1,
									message: 'The entered name must not be empty',
								},
							})}
						/>
						{errors.name && (
							<span className='text-md text-red-600'>{errors.name.message}</span>
						)}
						<label htmlFor='description'>Description</label>
						<textarea
							className='p-2 rounded-lg min-h-[300px] max-h-[400px]'
							type='text'
							{...register('description')}
						/>
					</div>
					<button className='px-6 py-3 bg-white w-full rounded-xl'>submit</button>
				</form>
			</div>
		</>
	);
}
