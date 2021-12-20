import React from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import routePaths from "../../common/constants/routePaths";
import "./styles.css";
import dateFormat from "dateformat";

const CinemaSessions = ({ cinema }) => {
    const navigate = useNavigate();

    const handleTicketOrder = () => {
        navigate("../../" + routePaths.myTickets);
    };

    return (
        <div className="cinema">
            {cinema.name}:
            <div className="cinema__sessions">
                {cinema.sessions.length > 0
                    ? cinema.sessions.map((session) => (
                          <div key={session.id} className="cinema__session">
                              <button disabled={!session.isExistFreeSeats} onClick={handleTicketOrder}>
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
