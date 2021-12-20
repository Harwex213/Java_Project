import React from "react";
import PropTypes from "prop-types";
import "./styles.css";
import { useNavigate } from "react-router-dom";
const mockImgSrc =
    "https://lh3.googleusercontent.com/zRRNbfQA6LiKwETJawfGFfP6dQDRlxL5zeqnywVVygzzARlZBy6YFjWWDXSHN6o_mGOXgy33rr562HkZRDP3HA=w843";

const MoviePoster = ({ movie }) => {
    const navigate = useNavigate();

    return (
        <div className="poster" onClick={() => navigate(`${movie?.movieId}`)}>
            <img src={mockImgSrc} alt="movie poster" />
            <div className="poster__info">
                <span>{movie?.movieName}</span>
                <span>{movie?.minPrice}</span>
            </div>
        </div>
    );
};

MoviePoster.propTypes = {
    movie: PropTypes.object,
    minPrice: PropTypes.number,
};

export default MoviePoster;
