import { privateApi, publicApi } from "./api";

export const getCinemasByDateEventId = (date, eventId) => {
    return publicApi.get(`cinemas/${date}/${eventId}`).json();
};

export const getCinemas = () => publicApi.get("cinemas").json();

export const postCinema = ({ name, description, address }) =>
    privateApi.post("cinemas", { json: { name, description, address } });

export const putCinema = ({ id, name, description, address }) =>
    privateApi.put("cinemas", { json: { id, name, description, address } });

export const deleteCinema = (id) => privateApi.delete(`cinemas/${id}`);
