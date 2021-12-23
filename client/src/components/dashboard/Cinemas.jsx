import React, { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { deleteCinema as deleteCinemaFetch, getCinemas, postCinema, putCinema } from "../../api/cinemas";
import "../../common/styles/table.css";
import "./styles.css";
import PropTypes from "prop-types";
import { Link, Route, Routes } from "react-router-dom";
import CinemaMovies from "./CinemaMovies";
import Sessions from "./Sessions";

const cinemaSchema = Yup.object().shape({
    name: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    description: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    address: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
});

const CinemaForm = ({ initialValues, handleSubmit, onStopEditing }) => {
    return (
        <Formik
            enableReinitialize
            initialValues={initialValues}
            validationSchema={cinemaSchema}
            onSubmit={handleSubmit}
        >
            {({ errors, touched, resetForm }) => (
                <>
                    <Form className="form">
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="name" placeholder="Cinema name" />
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
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="address" placeholder="Address" />
                            {errors.address && touched.address ? <span>{errors.address}</span> : null}
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

CinemaForm.propTypes = {
    initialValues: PropTypes.object,
    handleSubmit: PropTypes.func,
    onStopEditing: PropTypes.func,
};

const Cinemas = () => {
    const queryClient = useQueryClient();
    const [initialValues, setInitialValues] = useState({
        name: "",
        description: "",
        address: "",
    });
    const [editingId, setEditingId] = useState(null);
    const { isSuccess, data } = useQuery("cinemas", getCinemas);
    const createCinema = useMutation(postCinema, {
        onSuccess: () => queryClient.invalidateQueries("cinemas"),
    });
    const updateCinema = useMutation(putCinema, {
        onSuccess: () => queryClient.invalidateQueries("cinemas"),
    });
    const deleteCinema = useMutation(deleteCinemaFetch, {
        onSuccess: () => queryClient.invalidateQueries("cinemas"),
    });
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

    const handleEditClick = (cinema) => {
        setInitialValues(cinema);
        setEditingId(cinema.id);
    };

    const handleEditStopClick = () => {
        setInitialValues({
            name: "",
            description: "",
            address: "",
        });
        setEditingId(null);
    };

    const handleDeleteClick = (id) => {
        if (editingId === id) {
            handleEditStopClick();
        }
        deleteCinema.mutate(id);
    };

    const handleSubmit = (values) => {
        if (values.id) {
            updateCinema.mutate(values);
        } else {
            createCinema.mutate(values);
        }
    };

    return (
        <div>
            <Routes>
                <Route
                    path=""
                    element={
                        <>
                            <div>
                                <div className="isEditing">
                                    <h3 className="isEditing__title">
                                        Is editing: {editingId ? `Yes, id ${editingId}` : "No"}
                                    </h3>
                                </div>
                                <CinemaForm
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
                                        <td>address</td>
                                        <td>Movies</td>
                                        <td>Edit</td>
                                        <td>Delete</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    {data.map((cinema) => (
                                        <tr key={cinema.id}>
                                            <td>{cinema.id}</td>
                                            <td>{cinema.name}</td>
                                            <td>{cinema.description}</td>
                                            <td>{cinema.address}</td>
                                            <td>
                                                <Link to={cinema.id.toString()}>View</Link>
                                            </td>
                                            <td>
                                                <button onClick={() => handleEditClick(cinema)}>Edit</button>
                                            </td>
                                            <td>
                                                <button onClick={() => handleDeleteClick(cinema.id)}>
                                                    Delete
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </>
                    }
                />
                <Route path=":cinemaId" element={<CinemaMovies />} />
                <Route path=":cinemaId/:cinemaMovieId" element={<Sessions />} />
            </Routes>
        </div>
    );
};

export default Cinemas;
