export function Button({ children, onClick }) {
	return (
		<button onClick={onClick} className='px-6 py-2 rounded-xl bg-black text-white'>
			{children}
		</button>
	);
}
