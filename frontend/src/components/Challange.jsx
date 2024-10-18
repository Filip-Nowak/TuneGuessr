const CHALLANGE_URL = 'https://localhost:5173/challange';

export function Challange({
	name = 'Challange Title',
	description = 'Challange description',
	challangeId,
}) {
	return (
		<div className='flex flex-col gap-4 p-4 bg-purple-300 rounded-md'>
			<h1 className='text-4xl'>{name}</h1>
			<p className='font-bold'>{description}</p>
			<p>
				Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt autem nulla omnis quos
				atque error nihil suscipit nam praesentium ipsa!
			</p>
			<a
				href={`${CHALLANGE_URL}/${challangeId}`}
				className='px-6 py-2 rounded-xl text-center bg-black text-white'
			>
				Play
			</a>
		</div>
	);
}
