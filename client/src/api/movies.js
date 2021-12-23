import { privateApi, publicApi } from "./api";

export const getMovies = () => publicApi.get("movies").json();

export const postMovie = ({ name, description }) =>
    privateApi.post("movies", { json: { name, description } });

export const putMovie = ({ id, name, description }) =>
    privateApi.put("movies", { json: { id, name, description } });

export const deleteMovie = (id) => privateApi.delete(`movies/${id}`);
