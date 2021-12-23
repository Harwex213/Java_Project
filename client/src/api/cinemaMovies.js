import { privateApi, publicApi } from "./api";

const getCinemaMoviesByDate = (date) => {
    return publicApi.get(`cinema-movies/${date}`).json();
};

export const getCinemaMovies = (cinemaId) => publicApi.get(`cinema-movies/by-cinema/${cinemaId}`).json();

export const postCinemaMovie = ({ cinemaId, movieId }) =>
    privateApi.post("cinema-movies", { json: { cinemaId, movieId } });

export const deleteCinemaMovie = (id) => privateApi.delete(`cinema-movies/${id}`);

const cinemaMovies = {
    getCinemaMoviesByDate,
};

export default cinemaMovies;
