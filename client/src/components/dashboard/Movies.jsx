import React, { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { deleteMovie as deleteMovieFetch, getMovies, postMovie, putMovie } from "../../api/movies";
import "../../common/styles/table.css";
import "./styles.css";
import PropTypes from "prop-types";

const movieSchema = Yup.object().shape({
    name: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    description: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
});

const MovieForm = ({ initialValues, handleSubmit, onStopEditing }) => {
    return (
        <Formik
            enableReinitialize
            initialValues={initialValues}
            validationSchema={movieSchema}
            onSubmit={handleSubmit}
        >
            {({ errors, touched, resetForm }) => (
                <>
                    <Form className="form">
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="name" placeholder="Movie name" />
                            {errors.name && touched.name ? <span>{errors.name}</span> : null}
                        </div>
                        <div className="form__field-wrapper">
                            <Field
                                component="textarea"
                                className="form__field"
                                name="description"
                                placeholder="Description"
                            />
                            {errors.description && touched.description ? (
                                <span>{errors.description}</span>
                            ) : null}
                        </div>
                        <div className="buttons">
                            <button className="buttons__button_margin" type="submit">
                                Submit
                            </button>
                            <button
                                className="buttons__button_margin"
                                onClick={() => {
                                    resetForm();
                                }}
                                type="button"
                            >
                                Reset form
                            </button>
                            <button
                                onClick={() => {
                                    onStopEditing();
                                }}
                                type="button"
                            >
                                Stop editing
                            </button>
                        </div>
                    </Form>
                </>
            )}
        </Formik>
    );
};

MovieForm.propTypes = {
    initialValues: PropTypes.object,
    handleSubmit: PropTypes.func,
    onStopEditing: PropTypes.func,
};

const Movies = () => {
    const queryClient = useQueryClient();
    const [initialValues, setInitialValues] = useState({
        name: "",
        description: "",
    });
    const [editingId, setEditingId] = useState(null);
    const { isSuccess, data } = useQuery("movies", getMovies);
    const createMovie = useMutation(postMovie, {
        onSuccess: () => queryClient.invalidateQueries("movies"),
    });
    const updateMovie = useMutation(putMovie, {
        onSuccess: () => queryClient.invalidateQueries("movies"),
    });
    const deleteMovie = useMutation(deleteMovieFetch, {
        onSuccess: () => queryClient.invalidateQueries("movies"),
    });
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

    const handleEditClick = (movie) => {
        setInitialValues(movie);
        setEditingId(movie.id);
    };

    const handleEditStopClick = () => {
        setInitialValues({
            name: "",
            description: "",
        });
        setEditingId(null);
    };

    const handleDeleteClick = (id) => {
        if (editingId === id) {
            handleEditStopClick();
        }
        deleteMovie.mutate(id);
    };

    const handleSubmit = (values) => {
        if (values.id) {
            updateMovie.mutate(values);
        } else {
            createMovie.mutate(values);
        }
    };

    return (
        <>
            <div>
                <div className="isEditing">
                    <h3 className="isEditing__title">
                        Is editing: {editingId ? `Yes, id ${editingId}` : "No"}
                    </h3>
                </div>
                <MovieForm
                    initialValues={initialValues}
                    handleSubmit={handleSubmit}
                    onStopEditing={handleEditStopClick}
                />
            </div>
            <table>
                <thead>
                    <tr>
                        <td>id</td>
                        <td>name</td>
                        <td>description</td>
                        <td>Edit</td>
                        <td>Delete</td>
                    </tr>
                </thead>
                <tbody>
                    {data.map((movie) => (
                        <tr key={movie.id}>
                            <td>{movie.id}</td>
                            <td>{movie.name}</td>
                            <td>{movie.description}</td>
                            <td>
                                <button onClick={() => handleEditClick(movie)}>Edit</button>
                            </td>
                            <td>
                                <button onClick={() => handleDeleteClick(movie.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
};

export default Movies;
