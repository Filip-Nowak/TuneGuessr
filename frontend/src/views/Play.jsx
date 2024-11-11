import { useEffect, useState } from "react";
import { Challange } from "../components/Challange";
import { SearchBar } from "../components/SearchBar";
import { BACKEND_URL } from "../constants/API_END-POINT";
import { getUserToken } from "../utils/getUserToken";

export function Play() {
  // TO-DO create a useEffect that returns challenges to the game
  const token = getUserToken();
  const [challenges, setChallenges] = useState([]);
  useEffect(() => {
    fetch(`${BACKEND_URL}/home-challenges`)
      .then((res) => res.json())
      .then((data) => {
        setChallenges(data.data);
      });
  }, []);
  console.log(challenges);
  return (
    <div className="min-h-screen">
      <SearchBar />

      <div className="grid grid-cols-1 md:overflow-y-scroll max-h-[90svh] md:grid-cols-2 xl:grid-cols-4 gap-5 px-8 py-10">
        {challenges.map(({ id, name, description }) => (
          <Challange
            challangeId={id}
            key={name}
            name={name}
            description={description}
          />
        ))}
      </div>
    </div>
  );
}
