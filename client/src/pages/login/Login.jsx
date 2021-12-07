import React from "react";
import { Form, Field, Formik } from "formik";
import * as Yup from "yup";
import "../../common/styles/form.css";
import { useMutation } from "react-query";
import authApi from "../../api/auth";

const loginSchema = Yup.object().shape({
    username: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
    password: Yup.string().min(4, "Too Short!").max(50, "Too Long!").required("Required"),
});

const Login = () => {
    const mutation = useMutation(({ username, password }) => authApi.login({ username, password }));

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
                }}
                validationSchema={loginSchema}
                onSubmit={handleSubmit}
            >
                {({ errors, touched }) => (
                    <Form className="form">
                        <div className="form__field-wrapper">
                            <Field className="form__field" name="username" placeholder="Username" />
                            {errors.username && touched.username ? <span>{errors.username}</span> : null}
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

                        <button type="submit">Submit</button>
                    </Form>
                )}
            </Formik>
        </div>
    );
};

export default Login;
