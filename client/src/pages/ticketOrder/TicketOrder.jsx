import React from "react";
import { useParams } from "react-router-dom";
import dateFormat from "dateformat";
import CinemaSessions from "../../components/cinemaSessions/cinemaSessions";
import { useQuery } from "react-query";
import PropTypes from "prop-types";
import { getCinemasByDateEventId } from "../../api/cinemas";

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

const TicketOrder = ({ date }) => {
    const { movieId } = useParams();
    const { isSuccess, data } = useQuery(["session", { date, movieId }], () =>
        getCinemasByDateEventId(date, movieId)
    );
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {data.map((cinema) => (
                <CinemaSessions key={cinema.id} cinema={cinema} />
            ))}
        </div>
    );
};

TicketOrder.propTypes = {
    date: PropTypes.object,
};

export default TicketOrder;
