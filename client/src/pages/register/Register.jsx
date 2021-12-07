import React from "react";
import { Form, Field, Formik } from "formik";
import * as Yup from "yup";
import "../../common/styles/form.css";
import { useMutation } from "react-query";
import authApi from "../../api/auth";

const registrationSchema = Yup.object().shape({
    username: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    email: Yup.string().email("Must be valid email"),
    password: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    repeatedPassword: Yup.string()
        .oneOf([Yup.ref("password")], "Passwords must match")
        .required("Please, repeat your password"),
});

const Register = () => {
    const mutation = useMutation(({ username, email, password, repeatedPassword }) =>
        authApi.register({ username, email, password, repeatedPassword })
    );

    const handleSubmit = (values) => {
        mutation.mutate(values);
    };

    return (
        <div>
            <span>{mutation.isError ? "Error occurred, check your credentials." : null}</span>
            <Formik
                initialValues={{
                    username: "",
                    password: "",
                    repeatPassword: "",
                }}
                validationSchema={registrationSchema}
                onSubmit={handleSubmit}
            >
                {({ errors, touched }) => (
                    <Form className="form">
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="username" placeholder="Username" />
                            {errors.username && touched.username ? <span>{errors.username}</span> : null}
                        </div>
                        <div className="form__field-wrapper">
                            <Field className="form__field" type="email" name="email" placeholder="Email" />
                            {errors.email && touched.email ? <span>{errors.email}</span> : null}
                        </div>
                        <div className="form__field-wrapper">
                            <Field
                                className="form__field"
                                name="password"
                                placeholder="Password"
                                type="password"
                            />
                            {errors.password && touched.password ? <span>{errors.password}</span> : null}
                        </div>
                        <div className="form__field-wrapper">
                            <Field
                                className="form__field"
                                name="repeatedPassword"
                                placeholder="Repeat password"
                                type="password"
                            />
                            {errors.repeatedPassword && touched.repeatedPassword ? (
                                <span>{errors.repeatedPassword}</span>
                            ) : null}
                        </div>

                        <button type="submit">Submit</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default Register;
