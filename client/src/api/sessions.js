import { privateApi, publicApi } from "./api";

export const getSessions = (cinemaMovieId) => publicApi.get(`sessions/${cinemaMovieId}`).json();

export const postSession = ({ cinemaMovieId, time, ticketsAmount, price }) =>
    privateApi.post("sessions", { json: { cinemaMovieId, time, ticketsAmount, price } });

export const putSession = ({ id, cinemaMovieId, time, ticketsAmount, price }) =>
    privateApi.put("sessions", { json: { id, cinemaMovieId, time, ticketsAmount, price } });

export const deleteSession = (id) => privateApi.delete(`sessions/${id}`);
