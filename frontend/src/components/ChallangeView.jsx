import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ChallangeView() {
  const { challengeId } = useParams();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [author, setAuthor] = useState("");
  useEffect(() => {
    fetch(`https://localhost:8080/api/challenge/${challengeId}`)
      .then((res) => {
        console.log(res);
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setTitle(data.data.name);
        setDescription(data.data.description);
        setAuthor(data.data.author);
      });
  });
  return (
    <>
      <div className="flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12">
        <div className="text-center space-y-4 lg:text-left">
          <h1 className="text-6xl font-bold">{title}</h1>
          <p>{author}</p>
        </div>

        <Link
          to={`https://localhost:5137/${challengeId}`}
          className="px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer"
        >
          Play
        </Link>
      </div>

      <p className="px-4 py-12 lg:px-12 max-w-prose">{description}</p>
    </>
  );
}
