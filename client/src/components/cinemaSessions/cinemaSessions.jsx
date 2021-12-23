import React from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import routePaths from "../../common/constants/routePaths";
import dateFormat from "dateformat";
import "./styles.css";
import { useMutation } from "react-query";
import { postTicket } from "../../api/tickets";
import { useAuthContext } from "../../app/AuthContext";

const CinemaSessions = ({ cinema }) => {
    const navigate = useNavigate();
    const orderTicket = useMutation(postTicket);
    const { isAuth, user } = useAuthContext();

    const handleTicketOrder = (session) => {
        if (isAuth) {
            orderTicket.mutate({ sessionId: session.id, userId: user.id });
            setTimeout(() => navigate("../.." + routePaths.myTickets), 500);
        }
    };

    return (
        <div className="cinema">
            {cinema.name}:
            <div className="cinema__sessions">
                {cinema.sessions.length > 0
                    ? cinema.sessions.map((session) => (
                          <div key={session.id} className="cinema__session">
                              <button
                                  disabled={!session.isExistFreeSeats}
                                  onClick={() => handleTicketOrder(session)}
                              >
                                  {session.price}
                              </button>
                              <span>{dateFormat(session.time, "HH:MM")}</span>
                          </div>
                      ))
                    : "Sessions missing"}
            </div>
        </div>
    );
};

CinemaSessions.propTypes = {
    cinema: PropTypes.object,
};

export default CinemaSessions;
