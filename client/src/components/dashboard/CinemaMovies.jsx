import React from "react";
import { Link, useParams } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import {
    getCinemaMovies,
    postCinemaMovie,
    deleteCinemaMovie as deleteCinemaMovieFetch,
} from "../../api/cinemaMovies";
import { getMovies } from "../../api/movies";
import { Field, Form, Formik } from "formik";

const CinemaMovies = () => {
    const queryClient = useQueryClient();
    const { cinemaId } = useParams();
    const cinemaMovies = useQuery(["cinemaMovies", cinemaId], () => getCinemaMovies(cinemaId));
    const movies = useQuery("movies", getMovies);
    const createCinemaMovie = useMutation(postCinemaMovie, {
        onSuccess: () => queryClient.invalidateQueries(["cinemaMovies", cinemaId]),
    });
    const deleteCinemaMovie = useMutation(deleteCinemaMovieFetch, {
        onSuccess: () => queryClient.invalidateQueries(["cinemaMovies", cinemaId]),
    });
    if (!cinemaMovies.isSuccess || !movies.isSuccess) {
        return <div>Loading...</div>;
    }

    const handleSubmit = (values) => {
        if (
            cinemaMovies.data.length > 0 &&
            cinemaMovies.data.findIndex(
                (cinemaMovie) => Number(cinemaMovie.movieId) === Number(values.movieId)
            ) !== -1
        ) {
            return;
        }
        createCinemaMovie.mutate({
            cinemaId: Number(cinemaId),
            movieId: Number(values.movieId),
        });
    };

    return (
        <>
            <div>
                <div className="isEditing">
                    <h3 className="isEditing__title">Movies at the cinema - id {cinemaId}</h3>
                </div>
                <Formik
                    enableReinitialize
                    initialValues={{
                        movieId: movies.data.length > 0 ? movies.data[0].id.toString() : "",
                    }}
                    onSubmit={handleSubmit}
                >
                    {({ errors, touched, resetForm }) => (
                        <>
                            <Form className="form">
                                <div className="form__field-wrapper">
                                    <Field
                                        component="select"
                                        className="form__field"
                                        name="movieId"
                                        placeholder="Movie"
                                    >
                                        {movies.data.map((movie) => (
                                            <option key={movie.id} value={movie.id}>
                                                {movie.name}
                                            </option>
                                        ))}
                                    </Field>
                                    {errors.description && touched.description ? (
                                        <span>{errors.description}</span>
                                    ) : null}
                                </div>
                                <div className="buttons">
                                    <button className="buttons__button_margin" type="submit">
                                        Submit
                                    </button>
                                </div>
                            </Form>
                        </>
                    )}
                </Formik>
            </div>
            <table>
                <thead>
                    <tr>
                        <td>id</td>
                        <td>movie name</td>
                        <td>View Sessions</td>
                        <td>Delete</td>
                    </tr>
                </thead>
                <tbody>
                    {cinemaMovies.data.map((cinemaMovie) => (
                        <tr key={cinemaMovie.id}>
                            <td>{cinemaMovie.id}</td>
                            <td>{cinemaMovie.movieName}</td>
                            <td>
                                <Link to={cinemaMovie.id.toString()}>View</Link>
                            </td>
                            <td>
                                <button onClick={() => deleteCinemaMovie.mutate(cinemaMovie.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
};

export default CinemaMovies;
