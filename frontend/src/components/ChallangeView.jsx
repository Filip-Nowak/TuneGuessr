import { Link, useParams } from 'react-router-dom';

export function ChallangeView() {
	const { challangeId } = useParams();

	return (
		<>
			<div className='flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12'>
				<div className='text-center space-y-4 lg:text-left'>
					<h1 className='text-6xl font-bold'>Challange Name</h1>
					<p>Author</p>
				</div>

				<Link
					to={`https://localhost:5137/${challangeId}`}
					className='px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer'
				>
					Play
				</Link>
			</div>

			<p className='px-4 py-12 lg:px-12 max-w-prose'>
				Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident assumenda tempora
				asperiores? Tempora enim dolorum commodi! Doloribus odit quia quos suscipit modi in
				soluta incidunt obcaecati voluptate sed possimus quae nam error cumque dolorem quo
				laborum delectus cum, dolores nobis cupiditate quidem fuga officiis? Iusto molestiae
				voluptas aut ad quos provident, facere dicta necessitatibus ut, doloremque ex fugit
				modi veritatis odit eaque, rerum illum.
			</p>
		</>
	);
}
