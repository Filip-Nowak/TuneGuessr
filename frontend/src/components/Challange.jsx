import { Button } from './Button';

export function Challange() {
	return (
		<div className='flex flex-col gap-4 p-4 bg-purple-300 rounded-md'>
			<h1 className='text-4xl'>Challange Title</h1>
			<p className='font-bold'>Challange description:</p>
			<p>
				Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt autem nulla omnis quos
				atque error nihil suscipit nam praesentium ipsa!
			</p>
			<Button>Play</Button>
		</div>
	);
}
