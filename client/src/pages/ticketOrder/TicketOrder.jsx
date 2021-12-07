import React from "react";
import { useParams } from "react-router-dom";
import dateFormat from "dateformat";
import CinemaSessions from "../../components/cinemaSessions/cinemaSessions";

let localId = 0;
const mockSessions = [
    {
        id: localId++,
        name: "Cinema 1",
        sessions: [
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$17.29",
                isExistFreeSeats: true,
            },
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$10.1",
                isExistFreeSeats: false,
            },
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$8.2",
                isExistFreeSeats: true,
            },
        ],
    },
    {
        id: localId++,
        name: "Cinema 2",
        sessions: [
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$17.29",
                isExistFreeSeats: true,
            },
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$10.1",
                isExistFreeSeats: true,
            },
            {
                id: localId++,
                time: dateFormat(new Date(), "HH:MM"),
                price: "$8.2",
                isExistFreeSeats: false,
            },
        ],
    },
];

const TicketOrder = () => {
    const { movieId } = useParams();
    return (
        <div>
            {mockSessions.map((cinema) => (
                <CinemaSessions key={cinema.id} cinema={cinema} />
            ))}
        </div>
    );
};

export default TicketOrder;
