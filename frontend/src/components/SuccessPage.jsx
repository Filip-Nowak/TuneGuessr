export function SuccessPage() {
	return (
		<div className='flex flex-col justify-center items-center gap-4 min-h-[90svh] text-center'>
			<img src='/check-circle.svg' alt='check icon' className='w-[200px]' />
			<h1 className='text-6xl'>Thanks for register</h1>
			<a href='/' className='text-xl'>
				Back to main page
			</a>
		</div>
	);
}
