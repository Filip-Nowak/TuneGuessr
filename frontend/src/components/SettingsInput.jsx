import { useState } from 'react';
import { Button } from './Button';
import { MdModeEdit } from 'react-icons/md';
import { IoClose } from 'react-icons/io5';
import { IoCheckmarkOutline } from 'react-icons/io5';

const changeInputValueToPassword = inputValue => {
	let password = '';
	for (let i = 0; i < inputValue.length; i++) {
		password += '*';
	}

	return password;
};

export function SettingsInput({ inputName, inputValue }) {
	const [isEditMode, setIsEditMode] = useState(false);
	return (
		<>
			<label className='font-bold' htmlFor={inputName}>
				{inputName}
			</label>
			<div className='flex justify-between items-center gap-1 md:justify-normal'>
				{isEditMode ? (
					<>
						<input
							type='text'
							value={inputValue}
							id={inputName}
							className='p-1 border rounded-lg w-full lg:w-[70%]'
						/>
						<Button onClick={() => setIsEditMode(false)}>
							<IoCheckmarkOutline className='text-2xl' />
						</Button>
						<Button onClick={() => setIsEditMode(false)}>
							<IoClose className='text-2xl' />
						</Button>
					</>
				) : (
					<>
						<p className='w-full lg:w-[70%]'>
							{inputName === 'Password'
								? changeInputValueToPassword(inputValue)
								: inputValue}
						</p>
						<Button onClick={() => setIsEditMode(true)}>
							<MdModeEdit className='text-2xl' />
						</Button>
					</>
				)}
			</div>
		</>
	);
}
