import { privateApi } from "./api";

export const getTickets = () => privateApi.get("tickets").json();

export const postTicket = ({ sessionId, userId }) =>
    privateApi.post("tickets", { json: { sessionId, userId } }).json();

export const deleteTicket = (id) => privateApi.delete(`tickets/${id}`);
