import React, { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import {
    deleteSession as deleteSessionFetch,
    getSessions,
    postSession,
    putSession,
} from "../../api/sessions";
import "../../common/styles/table.css";
import "./styles.css";
import PropTypes from "prop-types";
import { useParams } from "react-router-dom";
import dateFormat from "dateformat";

const sessionSchema = Yup.object().shape({
    time: Yup.string().required(),
    ticketsAmount: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    price: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
});

const SessionForm = ({ initialValues, handleSubmit, onStopEditing }) => {
    return (
        <Formik
            enableReinitialize
            initialValues={initialValues}
            validationSchema={sessionSchema}
            onSubmit={handleSubmit}
        >
            {({ errors, touched, resetForm }) => (
                <>
                    <Form className="form">
                        <div className="form__field-wrapper">
                            <Field
                                as="number"
                                className="form__field"
                                name="name"
                                placeholder="Session name"
                            />
                            {errors.name && touched.name ? <span>{errors.name}</span> : null}
                        </div>
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="time" placeholder="time" />
                            {errors.time && touched.time ? <span>{errors.time}</span> : null}
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

SessionForm.propTypes = {
    initialValues: PropTypes.object,
    handleSubmit: PropTypes.func,
    onStopEditing: PropTypes.func,
};

const Sessions = () => {
    const queryClient = useQueryClient();
    const [initialValues, setInitialValues] = useState({
        time: dateFormat(new Date(), "yyyy-mm-dd, HH:MM"),
        ticketsAmount: 0,
        price: 0,
    });
    const [editingId, setEditingId] = useState(null);
    const { cinemaMovieId } = useParams();
    const { isSuccess, data } = useQuery(["sessions", cinemaMovieId], () => getSessions(cinemaMovieId));
    const createSession = useMutation(postSession, {
        onSuccess: () => queryClient.invalidateQueries("sessions"),
    });
    const updateSession = useMutation(putSession, {
        onSuccess: () => queryClient.invalidateQueries("sessions"),
    });
    const deleteSession = useMutation(deleteSessionFetch, {
        onSuccess: () => queryClient.invalidateQueries("sessions"),
    });
    if (!isSuccess) {
        return <div>Loading...</div>;
    }

    const handleEditClick = (session) => {
        setInitialValues(session);
        setEditingId(session.id);
    };

    const handleEditStopClick = () => {
        setInitialValues({
            time: new Date(),
            ticketsAmount: 0,
            price: 0,
        });
        setEditingId(null);
    };

    const handleDeleteClick = (id) => {
        if (editingId === id) {
            handleEditStopClick();
        }
        deleteSession.mutate(id);
    };

    const handleSubmit = (values) => {
        if (values.id) {
            updateSession.mutate(values);
        } else {
            createSession.mutate(values);
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
                <SessionForm
                    initialValues={initialValues}
                    handleSubmit={handleSubmit}
                    onStopEditing={handleEditStopClick}
                />
            </div>
            <table>
                <thead>
                    <tr>
                        <td>id</td>
                        <td>time</td>
                        <td>price</td>
                        <td>ticketsAmount</td>
                        <td>Edit</td>
                        <td>Delete</td>
                    </tr>
                </thead>
                <tbody>
                    {data.map((session) => (
                        <tr key={session.id}>
                            <td>{session.id}</td>
                            <td>{dateFormat(session.time, "yyyy-mm-dd, HH:MM")}</td>
                            <td>{session.price}</td>
                            <td>{session.ticketsAmount}</td>
                            <td>
                                <button onClick={() => handleEditClick(session)}>Edit</button>
                            </td>
                            <td>
                                <button onClick={() => handleDeleteClick(session.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
};

export default Sessions;
