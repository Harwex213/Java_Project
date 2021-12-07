import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import MoviePoster from "../../components/moviePoster/MoviePoster";
import TicketOrder from "../ticketOrder/TicketOrder";
import dateFormat from "dateformat";
import "../../common/styles/list.css";
import { useQuery } from "react-query";
import cinemaMovies from "../../api/cinemaMovies";

const today = dateFormat(new Date(), "yyyy-mm-dd");
const DefaultMain = () => {
    const [date, setDate] = useState(today);
    const { isSuccess, data } = useQuery(["afisha", { date }], () =>
        cinemaMovies.getCinemaMoviesByDate(date)
    );
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

    const movies = data.map((obj) => <MoviePoster key={obj.id} movie={obj} />);
    const splitMovies = [];
    for (let i = 0; i < movies.length; i += 4) {
        splitMovies.push(
            <div key={i} className="list">
                {movies.slice(i, i + 4)}
            </div>
        );
    }

    return (
        <Routes>
            <Route
                path=""
                element={
                    <>
                        <input
                            onChange={(event) => {
                                setDate(dateFormat(event.target.value, "yyyy-mm-dd"));
                            }}
                            type="date"
                            value={date}
                            min={today}
                            max="2021-12-31"
                        />
                        {splitMovies}
                    </>
                }
            />
            <Route path=":movieId" element={<TicketOrder />} />
        </Routes>
    );
};

export default DefaultMain;
