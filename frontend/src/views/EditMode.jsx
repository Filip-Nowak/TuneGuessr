import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { BACKEND_URL } from "../constants/API_END-POINT";
import { getUserToken } from "../utils/getUserToken";
import { useForm } from "react-hook-form";

export function EditMode() {
  const [isAddNewSong, setIsAddNewSong] = useState(false);

  const { challengeId } = useParams();
  const navigate = useNavigate();
  const token = getUserToken();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const ytRegex =
    /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com|youtu\.be)\/[^\s]+/;
  const [challengeSongsList, setChallengeSongsList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`${BACKEND_URL}/challenge/${challengeId}`);

        const { data } = await response.json();

        setChallengeSongsList(data.songs);
        setTitle(data.name);
        setDescription(data.description);
      } catch (error) {
        console.log("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  const handleDeleteSong = async () => {
    try {
      const response = await fetch(`${BACKEND_URL}/challenge/${challengeId}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        console.log("DELETE challenge error");
        return;
      }

      console.log("DELETE challenge successful!");
      navigate("/custom-playlist", { replace: true });
    } catch (error) {
      console.log("DELETE challenge error:", error);
    }
  };

  const onSubmit = (e) => {
    try {
      const { title, url } = e;
      console.log(title);
      console.log(url);

      fetch(`${BACKEND_URL}/challenge/${challengeId}/song`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          title,
          artist: "test",
          url,
        }),
      });

      reset();
    } catch (error) {
      console.log(error);
    }
  };
  console.log(challengeSongsList);

  return (
    <>
      <div className="flex flex-col lg:flex-row justify-between items-center gap-8 pt-36 md:pt-12 px-12">
        <div className="text-center space-y-4 lg:text-left">
          <h1 className="text-6xl font-bold">{title}</h1>
        </div>

        <button
          onClick={handleDeleteSong}
          className="px-20 py-6 rounded-xl bg-black text-white hover:cursor-pointer"
        >
          Delete
        </button>
      </div>

      <div className="grid grid-cols-4 mt-10">
        <div className="col-start-1 col-end-3 pl-12">
          <button
            onClick={() => setIsAddNewSong((prevState) => !prevState)}
            className="px-4 py-2 rounded-xl bg-black text-white hover:cursor-pointer"
          >
            + Add song
          </button>

          {isAddNewSong && (
            <form
              onSubmit={handleSubmit(onSubmit)}
              style={{
                display: "flex",
                flexDirection: "column",
                border: "1px solid black",
                padding: "1rem",
              }}
              className="my-5 space-x-3"
            >
              <label htmlFor="title">Title</label>
              <input
                className="p-1 border"
                type="text"
                id="title"
                {...register("title", {
                  required: "The entered name must not be empty",
                })}
              />
              <label htmlFor="artist">Artist</label>
              <input
                className="p-1 border"
                type="text"
                id="artist"
                {...register("artist", {
                  required: "The entered name must not be empty",
                })}
              />
              <label htmlFor="url">URL</label>
              <input
                className="p-1 border"
                type="text"
                id="url"
                {...register("url", {
                  required: "The entered name must not be empty",
                  validate: (link) =>
                    ytRegex.test(link) ||
                    "The link must be a valid YouTube URL",
                })}
              />
              <button className="px-4 py-2 rounded-xl bg-black text-white">
                Add Song
              </button>
            </form>
          )}
          {errors.text && (
            <p className="text-md text-red-600">{errors.text.message}</p>
          )}
          {errors.url && (
            <p className="text-md text-red-600">{errors.url.message}</p>
          )}

          <ul className="mt-8 space-y-6">
            {challengeSongsList.map(({ title, url }, id) => (
              <li
                key={url}
                className="flex justify-between gap-5 max-w-[500px]"
              >
                <div>
                  <h3 className="font-bold">{title}</h3>
                  <Link target="_blank" to={url} className="text-blue-600">
                    {url}
                  </Link>
                </div>
                <button
                  onClick={() =>
                    fetch(
                      `${BACKEND_URL}/challenge/${challengeId}/song/${id + 1}`,
                      {
                        method: "DELETE",
                        headers: {
                          "Content-Type": "application/json",
                          Authorization: `Bearer ${token}`,
                        },
                      }
                    ).then(() => {
                      setChallengeSongsList((prevState) =>
                        prevState.filter((song) => song.url !== url)
                      );
                    })
                  }
                  className="px-4 py-2 rounded-xl bg-black text-white"
                >
                  remove
                </button>
              </li>
            ))}
          </ul>
        </div>
        <p className=" col-start-3 col-end-5 px-4 py-12 lg:px-12 max-w-prose">
          {description}
        </p>
      </div>
    </>
  );
}
