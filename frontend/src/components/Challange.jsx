import { Link } from "react-router-dom";

export const CHALLANGE_URL = "https://localhost:5173/challenge";

export function Challange({
  name = "Challange Title",
  description = "Challange description",
  challangeId,
  customChallange,
}) {
  return (
    <div className="flex flex-col gap-4 p-4 bg-purple-300 rounded-md">
      <h1 className="text-4xl">{name}</h1>
      <p className="font-bold">{description}</p>
      <p></p>
      <div className="flex flex-col md:flex-row items-center gap-6">
        <Link
          to={`${CHALLANGE_URL}/${challangeId}`}
          className="px-6 py-2 w-full rounded-xl text-center bg-black text-white"
        >
          Play
        </Link>
        {customChallange ? (
          <Link
            to={`${CHALLANGE_URL}/edit-mode/${challangeId}`}
            className="px-6 py-2 w-full rounded-xl text-center bg-black text-white"
          >
            Edit
          </Link>
        ) : (
          ""
        )}
      </div>
    </div>
  );
}
