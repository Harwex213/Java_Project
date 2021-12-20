import { publicApi } from "./api";

export const getCinemasByDateEventId = (date, eventId) => {
    return publicApi.get(`cinemas/${date}/${eventId}`).json();
};
