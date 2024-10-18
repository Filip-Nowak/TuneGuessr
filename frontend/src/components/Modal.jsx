import { useForm } from 'react-hook-form';
import CLOSE from '../assets/close-outline.svg';

export function Modal({ onClose }) {
	const {
		register,
		handleSubmit,
		formState: { errors },
	} = useForm();

	const onSubmit = async () => {
		try {
			console.log('form has been submitted');
		} catch (error) {
			throw new Error('POST Error', error);
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
				<form className='flex flex-col justify-between h-full' onSubmit={onSubmit}>
					<div className='flex flex-col gap-2'>
						<label htmlFor='name'>Name</label>
						<input
							className='p-2 rounded-lg'
							type='text'
							{...register('name', {
								minLength: 1,
							})}
						/>
						<label htmlFor='description'>Description</label>
						<textarea
							className='p-2 rounded-lg min-h-[300px] max-h-[400px]'
							type='text'
						/>
					</div>
					<button className='px-6 py-3 bg-white w-full rounded-xl' type='submit'>
						submit
					</button>
				</form>
			</div>
		</>
	);
}
