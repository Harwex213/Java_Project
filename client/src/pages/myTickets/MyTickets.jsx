import React from "react";
import dateFormat from "dateformat";
import "../../common/styles/table.css";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { deleteTicket, getTickets } from "../../api/tickets";

const MyTickets = () => {
    const queryClient = useQueryClient();
    const returnTicket = useMutation(deleteTicket);
    const { isSuccess, data } = useQuery("tickets", getTickets);
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

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
                {data.map((ticket) => (
                    <tr key={ticket.id}>
                        <td>{ticket.cinemaName}</td>
                        <td>{ticket.movieName}</td>
                        <td>{dateFormat(ticket.time, "yyyy-mm-dd, HH:MM")}</td>
                        <td>{ticket.price}</td>
                        <td>
                            <button
                                onClick={() =>
                                    returnTicket.mutate(ticket.id, {
                                        onSuccess: () => queryClient.invalidateQueries("tickets"),
                                    })
                                }
                            >
                                Return
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default MyTickets;
