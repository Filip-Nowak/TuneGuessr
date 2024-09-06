import { useState } from 'react';

export function GamePanel() {
	const [answer, setAnswer] = useState('');

	const handleAnswerClick = e => {
		const targetOption = e.currentTarget.textContent;
		setAnswer(targetOption);
	};

	console.log(answer);

	return (
		<div className='flex flex-col justify-evenly items-center h-screen'>
			<iframe
				width='560'
				height='315'
				src='https://www.youtube.com/embed/SwIHZnBttFs?si=qe5WdqWbgi4026oU'
				title='YouTube video player'
				frameBorder='0'
				allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share'
				referrerPolicy='strict-origin-when-cross-origin'
				allowfullscreen
			></iframe>

			<div className='grid grid-cols-2 gap-8 w-1/2'>
				<button
					onClick={handleAnswerClick}
					className='px-2 py-4 bg-black text-white rounded-lg'
				>
					OKI
				</button>
				<button
					onClick={handleAnswerClick}
					className='px-2 py-4 bg-black text-white rounded-lg'
				>
					Sobel
				</button>
				<button
					onClick={handleAnswerClick}
					className='px-2 py-4 bg-black text-white rounded-lg'
				>
					Taco Hemingway
				</button>
				<button
					onClick={handleAnswerClick}
					className='px-2 py-4 bg-black text-white rounded-lg'
				>
					Mata
				</button>
			</div>
		</div>
	);
}
