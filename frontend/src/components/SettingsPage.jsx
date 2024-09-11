import { useNavigate } from 'react-router-dom';
import AVATAR from '../assets/avatar.png';
import { Button } from './Button';
import { SettingsInput } from './SettingsInput';
import { useEffect, useState } from 'react';
import { BACKEND_URL } from '../constants/API_END-POINT';
import { getUserToken } from '../utils/getUserToken';

export function SettingsPage() {
	const [userData, setUserData] = useState(Object);

	useEffect(() => {
		const token = getUserToken();

		fetch(`${BACKEND_URL}/user`, {
			headers: { Authorization: `Bearer ${token}` },
		})
			.then(res => {
				return res.json();
			})
			.then(({ data }) => {
				setUserData(data);
				console.log(data);
			})
			.catch(err => {
				console.log(err);
			});
	}, []);

	const navigate = useNavigate();

	const inputs = [
		{ name: 'Username', data: userData.nickname },
		{ name: 'Email', data: userData.email },
		{ name: 'Password', data: 'kanapka' },
	];

	const handleButtonClick = () => {
		localStorage.removeItem('token');
		navigate('/login');
	};
	return (
		<div className='flex flex-col items-center gap-8 w-full py-32 px-4 md:min-h-screen lg:flex-row lg:gap-16 xl:gap-32'>
			<div className='lg:self-start md:self-center md:mt-16'>
				<div className='image w-60 h-60 rounded-full overflow-hidden'>
					<img src={AVATAR} alt='debil' className='w-full bg-cover' />
				</div>
				<div className='hidden md:block username text-center mt-10'>
					<h1 className='text-4xl'>{userData.nickname}</h1>
					<h3>#0001</h3>
				</div>
				<div className='absolute top-10 right-10'>
					<Button onClick={handleButtonClick}>Logout</Button>
				</div>
			</div>

			<div className='user-info self-start w-full md:self-center'>
				<div className='username text-center mb-10 md:hidden'>
					<h1 className='text-4xl'>{userData.nickname}</h1>
					<h3>#0001</h3>
				</div>
				<div className='user-data flex flex-col gap-6 text-xl'>
					{inputs.map(input => (
						<SettingsInput
							key={input.name}
							inputName={input.name}
							inputValue={input.data}
						/>
					))}

					{/* <label className='font-bold' htmlFor='username'>
						username
					</label>
					<div className='flex justify-between items-center gap-1'>
						<input type='text' id='username' className='p-1 border rounded-lg' />
						<Button>
							<IoCheckmarkOutline className='text-2xl' />
						</Button>
						<Button>
							<IoClose className='text-2xl' />
						</Button>
					</div>
					<label className='font-bold' htmlFor='email'>
						email
					</label>
					<div className='flex justify-between items-center gap-1'>
						<p>test@gmail.com</p>
						<Button>
							<MdModeEdit className='text-2xl' />
						</Button>
					</div>

					<label className='font-bold' htmlFor='password'>
						password
					</label>
					<div className='flex justify-between items-center gap-1'>
						<p>********</p>
						<Button>
							<MdModeEdit className='text-2xl' />
						</Button>
					</div> */}
				</div>
			</div>
		</div>
	);
}
