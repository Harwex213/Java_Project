import React from "react";
import PropTypes from "prop-types";
import "./styles.css";

const CinemaSessions = ({ cinema }) => {
    return (
        <div className="cinema">
            {cinema?.name}
            <div className="cinema__sessions">
                {cinema?.sessions?.map((session) => (
                    <div key={session.id} className="cinema__session">
                        <button disabled={!session.isExistFreeSeats}>{session.price}</button>
                        <span>{session.time}</span>
                    </div>
                ))}
            </div>
        </div>
    );
};

CinemaSessions.propTypes = {
    cinema: PropTypes.object,
};

export default CinemaSessions;
