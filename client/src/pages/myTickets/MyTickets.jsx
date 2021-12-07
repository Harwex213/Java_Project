import React from "react";
import dateFormat from "dateformat";
import "../../common/styles/table.css";

let localId = 0;
const tickets = [
    {
        id: localId++,
        cinemaName: "Cinema 1",
        movieName: "Movie 1",
        time: dateFormat(new Date(), "HH:MM"),
        price: "$7.29",
    },
    {
        id: localId++,
        cinemaName: "Cinema",
        movieName: "Movie",
        time: dateFormat(new Date(), "HH:MM"),
        price: "$17.29",
    },
];

const MyTickets = () => {
    return (
        <table>
            <thead>
                <tr>
                    <td>cinema</td>
                    <td>movie</td>
                    <td>time</td>
                    <td>price</td>
                    <td>Return ticket back</td>
                </tr>
            </thead>
            <tbody>
                {tickets.map((ticket) => (
                    <tr key={ticket.id}>
                        <td>{ticket.cinemaName}</td>
                        <td>{ticket.movieName}</td>
                        <td>{ticket.time}</td>
                        <td>{ticket.price}</td>
                        <td>
                            <button>Return</button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default MyTickets;
