import { publicApi } from "./api";

const getCinemaMoviesByDate = (date) => {
    return publicApi.get(`cinema-movies/${date}`).json();
};

const cinemaMovies = {
    getCinemaMoviesByDate,
};

export default cinemaMovies;
